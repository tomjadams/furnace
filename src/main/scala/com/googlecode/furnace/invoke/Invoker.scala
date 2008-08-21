package com.googlecode.furnace.invoke

import analyse.AnalysisResult
import sequence.GeneSequence

trait Invoker {
  def invoke(sequence: GeneSequence): AnalysisResult
}
