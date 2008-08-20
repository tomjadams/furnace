package com.googlecode.furnace.parse

import scala.collection.mutable.{ArrayBuffer => MutableArray}
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.{Base, GeneSequence}
import sequence.Base._
import sequence.GeneSequence._

// Note. Input must not contain the FASTA header.
final class FastaSequenceIterator(input: Iterator[Byte], bytesToRead: Int) extends Iterator[GeneSequence] {
  private lazy val validBases = bases.map(_.code.toByte)

  def hasNext = input.hasNext

  def next = {
    val bs = input.filter(validBases.contains(_)).map(byteToBase(_)).zipWithIndex.takeWhile(_._2 < bytesToRead).map(_._1).toList
    geneSequence(list(bs))
  }
}
