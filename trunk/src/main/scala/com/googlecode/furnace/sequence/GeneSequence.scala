package com.googlecode.furnace.sequence

import Base._

sealed trait GeneSequence {
  def sequence: Seq[Base]
}

private final case object EmptySequence extends GeneSequence {
  override def sequence: Seq[Base] = Nil
  override def toString = ""
}

private final case class GeneSequence_(sequence: Seq[Base]) extends GeneSequence {
  override def toString = sequence.mkString
}

object GeneSequence {
  def emptySequence: GeneSequence = EmptySequence
  implicit def geneSequence(s: Seq[Base]): GeneSequence = GeneSequence_(s)
}
