package com.googlecode.furnace.parse

import scala.collection.mutable.{ArrayBuffer => MutableArray}
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.{Base, GeneSequence}
import sequence.Base._
import sequence.GeneSequence._

object FastaParser {
  private lazy val newLines = List('\n', '\r')

  def parse(input: Iterator[Byte], sliceSize: Int): Option[Iterator[GeneSequence]] =
    if (input.hasNext) {
      Some(new GeneSequenceIterator(input))
    } else {
      None
    }

  private def read(limit: Int, current: Int): Seq[Byte] = error("Not baked yet")

  private final class GeneSequenceIterator(input: Iterator[Byte]) extends Iterator[GeneSequence] {
    def hasNext = input.hasNext

    def next = {
      val bases = new MutableArray[Base]
      val base = input.next
      bases += base
      //        println(">>> Base: " + base)
      geneSequence(list(bases.toList))
    }
  }
}
