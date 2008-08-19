package com.googlecode.furnace.parse

import Bytes._
import parse.FastaParser._
import sequence.GeneSequence._
import java.io.ByteArrayInputStream
import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import com.googlecode.instinct.integrate.junit4.InstinctRunner
import org.junit.runner.RunWith

final class AFastaParserWithNoSequenceToParse {
  private val noSequence = byteIterator("")

  @Specification
  def returnsAnEmptySequence {
    val sequences = parse(noSequence, 10)
    expect that sequences.hasNext isEqualTo false
    expect that sequences.next isEqualTo geneSequence(baseSeq(""))
  }
}

final class AFastaParserWithoutAHeader {
  private val sequence = byteIterator("ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG")

  @Specification
  def turnsAnIteratorOfBytesIntoAnIteratorOfGeneSequences {
    val sequences = parse(sequence, 10)
    expect that sequences.hasNext isEqualTo true
    expect that sequences.next isEqualTo geneSequence(baseSeq("ATGACAAAGC"))
    while (sequences.hasNext) {
      println(">>> Parsed sequence: " + sequences.next)
    }
    //    val sequences = parse(sequence, 10).toList
    //    expect that(sequences.size) isEqualTo 8
  }
}

final class AFastaParserWithAHeader {
  @Specification
  def turnsAnIteratorOfBytesIntoAnIteratorOfGeneSequences {

  }
}

object Bytes {
  import sequence.Base, Base._

  def byteIterator(s: String): Iterator[Byte] = s.map(_.toByte).elements
  def baseSeq(s: String): Seq[Base] = s.map(_.toByte: Base)
}