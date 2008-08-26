package com.googlecode.furnace

import analyse.AnalysisResult
import analyse.SequenceIdentifier._
import java.io.{File, FileInputStream}
import java.lang.Integer.parseInt
import invoke.LocalBlastInvoker
import scalaz.OptionW._
import scalaz.javas.InputStream._
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
        sequences.zipWithIndex.foreach(sequenceIdPair => {
          val result = invoker.invoke(id(inputSequence, sliceSize, sequenceIdPair._2), database, sequenceIdPair._1)
          info("Completed processing split sequence " + result.identifier.inputSequenceName + "; split: " + result.identifier.splitId +
              "; slice size: " + result.identifier.sliceSize + "; output: " + result.analysisOutput)
        })
        info("Fin.")
      }))
    } finally {
      in.close
    }
  }
}
