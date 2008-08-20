package com.googlecode.furnace.parse

import Bytes._
import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import java.io.ByteArrayInputStream
import parse.FastaParser._
import scalaz.OptionW._
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

final class AFastaParserWithASequenceContainingASingleLineOfBases {
  private val sequence = byteIterator("""
      >gi|15891923|ref|NC_003103.1| Rickettsia conorii str. Malish 7, complete genome
      ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG""")

  @Specification
  def turnsAnIteratorOfBytesIntoAnIteratorOfGeneSequences {
    parse(sequence, 10).fold(error("No sequences found"), (_.foreach(sequence => {
      expect.that(sequence.bases.length).isEqualTo(10)
    })))
  }
}

final class AFastaParserWithALotOfData {
  import file.io.FilePath
  import file.io.FilePath._
  import java.io.{File, FileInputStream}
  import scalaz.javas.InputStream._
  import com.googlecode.instinct.marker.annotate.Specification.SpecificationState._

  @Specification {val state = PENDING}
  def isFastAndDoesNotBlowMemory {
    val file = "/Users/atom/Projects/OpenSource/furnace/src/spec/data/sequences/NC_003103_r.conorii.fasta"
    val in = new FileInputStream(file)
    try {
      parse(in, 40).fold(error("No sequences found"), (s => s.foreach(_)))
    } finally {
      in.close
    }
  }
}

object Bytes {
  import sequence.Base, Base._

  def byteIterator(bases: String): Iterator[Byte] = bases.map(_.toByte).elements
  def baseSeq(bases: String): NonEmptyList[Base] = list(bases.map(_.toByte: Base).toList)
}