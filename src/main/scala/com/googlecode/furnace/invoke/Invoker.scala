package com.googlecode.furnace.invoke

import analyse.{AnalysisResult, SequenceIdentifier}
import sequence.GeneSequence
import util.io.FilePath

trait Invoker {
  def invoke(identifier: SequenceIdentifier, database: FilePath, sequence: GeneSequence): AnalysisResult
}
