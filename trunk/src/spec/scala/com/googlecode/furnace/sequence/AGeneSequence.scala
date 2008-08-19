package com.googlecode.furnace.sequence

import GeneSequence._
import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import scalaz.list.NonEmptyList._

final class TwoEqualGeneSequences {
  private val seq1 = geneSequence(nel('A'))
  private val seq2 = geneSequence(nel('A'))

  @Specification
  def areEqual {
    expect that seq1 isEqualTo seq2
  }
}

final class TwoUnEqualGeneSequences {
  private val seq1 = geneSequence(nel('A'))
  private val seq2 = geneSequence(nel('B'))

  @Specification
  def areNotEqual {
    expect that seq1 isNotEqualTo seq2
  }
}
