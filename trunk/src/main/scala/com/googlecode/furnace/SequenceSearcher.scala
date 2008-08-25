package com.googlecode.furnace

import java.io.{File, FileInputStream}
import java.lang.Integer.parseInt
import invoke.LocalBlastInvoker
import scalaz.OptionW._
import scalaz.javas.InputStream._
import sequence.GeneSequence
import parse.FastaParser
import util.io.FilePath
import util.io.FilePath._

object SequenceSearcher {
  val defaultSliceSize = 40

  def main(args: Array[String]): Unit = args match {
    case Array(path) => run(path, defaultSliceSize)
    case Array(path, sliceSize) => run(path, parseInt(sliceSize))
    case _ => error("Usage: java -cp <classpath> SequenceSearcher <input_sequence_file> [<bases_per_search_slice>]")
  }

  def run(inputSequence: FilePath, sliceSize: Int) {
    println("Processing sequence file: " + filePathToString(inputSequence) + ", slice size: " + sliceSize)
    val in = new FileInputStream(inputSequence)
    try {
      FastaParser.parse(in, sliceSize).fold(println("No sequences were found in the input file"), ((sequences: Iterator[GeneSequence]) => {
        println("The following sequences were found in the input file")
//        sequences.foreach(println)
        new LocalBlastInvoker().invoke(sequences.next)
        println("Fin.")
      }))
    } finally {
      in.close
    }
  }
}
