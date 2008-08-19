package com.googlecode.furnace

import file.io.FilePath
import file.io.FilePath._

object SequenceSearcher {
  import java.lang.Integer.parseInt

  val defaultSliceSize = 40

  def main(args: Array[String]): Unit = args match {
    case Array(path) => Main.run(path, defaultSliceSize)
    case Array(path, sliceSize) => Main.run(path, parseInt(sliceSize))
    case _ => error("Usage: java -cp <classpath> SequenceSearcher <input_sequence_file> [<bases_per_search_slice>]")
  }
}

object Main {
  import java.io.{File, FileInputStream}
  import scalaz.javas.InputStream._
  import sequence.GeneSequence
  import sequence.GeneSequence._
  import parse.FastaParser._

  def run(inputSequence: FilePath, sliceSize: Int) {
    println("Processing sequence file: " + filePathToString(inputSequence) + ", slice size: " + sliceSize)
    val result = parse(new FileInputStream(inputSequence), sliceSize)
    if (result.isEmpty) {
      println("No sequences found in source file")
    } else {
      val sequences = result.get
      while (sequences.hasNext) {
        println(sequences.next())
      }
//      println(sequences.get.toList.size + " sequences")
//      sequences.foreach(println)
      //    val completeSequences = sequences.filter(_.sequence.size == sliceSize)
      //    println("Found " + sequences.size + " sequences in input file, discarding " + (sequences.size - completeSequences.size) + " partial trailing sequence")
      //    println(completeSequences.size + " complete sequences")
      //    completeSequences.foreach(println)
      println("Fin.")

    }
  }
}
