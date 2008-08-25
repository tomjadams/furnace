package com.googlecode.furnace

import java.io.{File, FileInputStream}
import java.lang.Integer.parseInt
import invoke.LocalBlastInvoker
import scalaz.OptionW._
import scalaz.javas.InputStream._
import sequence.GeneSequence
import parse.FastaParser
import util.Logger, Logger._
import util.io.FilePath
import util.io.FilePath._

object SequenceSearcher {
  lazy val invoker = new LocalBlastInvoker
  val defaultSliceSize = 40

  def main(args: Array[String]): Unit = args match {
    case Array(path, database) => run(path, database, defaultSliceSize)
    case Array(path, database, sliceSize) => run(path, database, parseInt(sliceSize))
    case _ => error("Usage: java -cp <classpath> SequenceSearcher <input_sequence_file> <path_to_database> [<bases_per_search_slice>]")
  }

  def run(inputSequence: FilePath, database: FilePath, sliceSize: Int) {
    info("Processing sequence file: " + filePathToString(inputSequence) + ", slice size: " + sliceSize)
    val in = new FileInputStream(inputSequence)
    try {
      FastaParser.parse(in, sliceSize).fold(Logger.error("No sequences were found in the input file"), (sequences => {
        info("Processing parsed sequences...")
        sequences.zipWithIndex.foreach(sequenceIdPair => {
          val name = inputSequence.getName + "_s" + sliceSize + "_id" + sequenceIdPair._2
          invoker.invoke(name, database, sequenceIdPair._1)
        })
        info("Fin.")
      }))
    } finally {
      in.close
    }
  }
}
