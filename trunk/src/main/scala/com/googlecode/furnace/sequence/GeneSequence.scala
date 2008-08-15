package com.googlecode.furnace.sequence

import Base._

sealed trait GeneSequence {
  def sequence: Seq[Base]

//  def ::(base: Base) = this match {
//    case GeneSequence_(sequence) => geneSequence()
//  }

//  (a :: (b :: c))
//  c.::(b.::(a))
}

private final case class GeneSequence_(sequence: Seq[Base]) extends GeneSequence {
  override def toString = sequence.mkString
}

object GeneSequence {
  implicit def geneSequence(s: Seq[Base]): GeneSequence = GeneSequence_(s)
}
