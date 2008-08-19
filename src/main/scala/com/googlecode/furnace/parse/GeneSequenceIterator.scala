package com.googlecode.furnace.parse

import scala.collection.mutable.{ArrayBuffer => MutableArray}
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.{Base, GeneSequence}
import sequence.Base._
import sequence.GeneSequence._

final class GeneSequenceIterator(input: Iterator[Byte], bytesToRead: Int) extends Iterator[GeneSequence] {
  private lazy val newLines = List('\n', '\r')

  def hasNext = input.hasNext

  def next = {
    val bases = new MutableArray[Base]
    var byteRead = input.next
    var i = 0;
    while (byteRead != -1 && i < bytesToRead) {
      if (!newLines.contains(byteRead)) {
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
