package com.googlecode.furnace.parse

import Bytes._
import parse.FastaParser._
import java.io.ByteArrayInputStream
import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import com.googlecode.instinct.integrate.junit4.InstinctRunner
import org.junit.runner.RunWith

final class AFastaParserWithoutAHeader {
  private val noSequence = bytes("")
  private val sequence = bytes("ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG")

  @Specification
  def turnsAnIteratorOfBytesIntoAnIteratorOfGeneSequences {
    val sequences = parse(sequence, 10)
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
  def bytes(s: String): Iterator[Byte] = s.map(_.toByte).elements
}