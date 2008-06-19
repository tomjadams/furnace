package au.id.adams.mesh

import file.io.FilePath._
import file.io.ReadableFile._
import file.io.{FilePath, ReadableFile}
import java.io.File
import java.lang.Integer.parseInt
import sequence.GeneSequence._
import parse.GeneSequenceParser

object SequenceSearcher {
  val defaultSliceSize = 40

  def main(args: Array[String]): Unit = args match {
    case Array(path) => Main.run(path, defaultSliceSize)
    case Array(path, sliceSize) => Main.run(path, parseInt(sliceSize))
    case _ => error("Usage: java -cp <classpath> SequenceSearcher <input_sequence_file> [<bases_per_search_slice>]")
  }
}

object Main {
  import scalaz.io.File
  import scalaz.io.File._
  import sequence.Base
  import sequence.Base._
  import sequence.GeneSequence
  import sequence.GeneSequence._

  def run(inputSequence: FilePath, sliceSize: Int) {
    println("Processing sequence file: " + filePathToString(inputSequence) + ", slice size: " + sliceSize)
    val sequences = foo((inputSequence: File), sliceSize)

    //    val sequences = GeneSequenceParser.parse((inputSequence: File).slurp, sliceSize)
    println(sequences.size + " sequences")
    //    sequences.foreach(println)
    //    val completeSequences = sequences.filter(_.sequence.size == sliceSize)
    //    println("Found " + sequences.size + " sequences in input file, discarding " + (sequences.size - completeSequences.size) + " partial trailing sequence")
    //    println(completeSequences.size + " complete sequences")
    //    completeSequences.foreach(println)
    println("Fin.")
  }

  def foo(f: File, sliceSize: Int) = {
    val sequence = f.read(streamToGeneSequence)
    List(sequence)
  }

  lazy val newLines = List('\n', '\r')
  // TODO Improve this...
  def streamToGeneSequence(s: Stream[Byte]): GeneSequence = geneSequence(readSequence(s, 40).foldLeft(Nil: Seq[Base])(_ + byteToBase(_)))

  private def readSequence(s: Stream[Byte], howMany: Int): Stream[Byte] = removeHeader(s).filter(!newLines.contains(_)).take(howMany)

  private def removeHeader(s: Stream[Byte]) = s
}
