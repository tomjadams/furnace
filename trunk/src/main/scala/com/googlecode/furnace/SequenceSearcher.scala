package com.googlecode.furnace

import file.io.FilePath
import file.io.FilePath._
import java.io.{File, FileInputStream}
import java.lang.Integer.parseInt
import scalaz.javas.InputStream._
import scalaz.OptionW._
import sequence.GeneSequence
import sequence.GeneSequence._
import parse.FastaParser

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
      FastaParser.parse(in, sliceSize).fold(println("No sequences were found in the input file"), (sequences => {
        println("The following sequences were found in the input file")
        sequences.foreach(println)
        println("Fin.")
      }))
    } finally {
      in.close
    }
  }
}
