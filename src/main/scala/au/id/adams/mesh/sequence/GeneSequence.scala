package au.id.adams.mesh.sequence

import Base._

sealed trait GeneSequence {
  def sequence: Seq[Base]
}

private case class GeneSequence_(sequence: Seq[Base]) extends GeneSequence {
  override def toString = sequence.mkString
}

object GeneSequence {
  implicit def geneSequence(s: Seq[Base]): GeneSequence = GeneSequence_(s)
}
