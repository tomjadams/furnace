package com.googlecode.furnace.parse

import scala.collection.mutable.{ArrayBuffer => MutableArray}
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.{Base, GeneSequence}
import sequence.Base._
import sequence.GeneSequence._

final class FastaSequenceIterator(input: Iterator[Byte], bytesToRead: Int) extends Iterator[GeneSequence] {
  private lazy val lineSeparators = List('\r'.toByte, '\n'.toByte)

  def hasNext = input.hasNext

  // TODO Tom: Use the idiom of takeWhile (in.map(_.toChar).takeWhile(lineSeparators.contains(_))) to remove the header
  // Re-write this using takeWhile, where you take while the index is less than max

  def next = {
    val bases = new MutableArray[Base]
    var byteRead = input.next
    var i = 0;
    while (byteRead != -1 && i < bytesToRead) {
      if (!lineSeparators.contains(byteRead)) {
        bases += byteRead
      }
      if (input.hasNext) {
        byteRead = input.next
      }
      i += 1;
    }
    geneSequence(list(bases.toList))
  }
}
