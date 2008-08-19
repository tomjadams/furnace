package com.googlecode.furnace.parse

import Bytes._
import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import java.io.ByteArrayInputStream
import parse.FastaParser._
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.GeneSequence._

final class AFastaParserWithNoSequenceToParse {
  private val noSequence = byteIterator("")

  @Specification
  def returnsNone {
    val sequences = parse(noSequence, 10)
    expect that sequences.isEmpty isEqualTo true
  }
}

final class AFastaParserWithoutAHeader {
  private val sequence = byteIterator("ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG")

  @Specification
  def turnsAnIteratorOfBytesIntoAnIteratorOfGeneSequences {
    val sequences = parse(sequence, 10)
    expect that sequences.get.hasNext isEqualTo true
    expect that sequences.get.next isEqualTo geneSequence(baseSeq("ATGACAAAGC"))
    while (sequences.get.hasNext) {
      println(">>> Parsed sequence: " + sequences.get.next)
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

final class AFastaParserWithALotOfData {
  import file.io.FilePath
  import file.io.FilePath._
  import java.io.{File, FileInputStream}
  import scalaz.javas.InputStream._

  @Specification
  def isFastAndDoesNotBlowMemory {
    val file = "/Users/atom/Projects/OpenSource/furnace/src/spec/data/sequences/NC_003103_r.conorii.fasta"
    val sequences = parse(new FileInputStream(file), 40).get
    while (sequences.hasNext) {
      println(">>> Parsed sequence: " + sequences.next)
    }
  }
}

object Bytes {
  import sequence.Base, Base._

  def byteIterator(bases: String): Iterator[Byte] = bases.map(_.toByte).elements
  def baseSeq(bases: String): NonEmptyList[Base] = list(bases.map(_.toByte: Base).toList)
}