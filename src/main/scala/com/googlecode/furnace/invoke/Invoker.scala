package com.googlecode.furnace.invoke

import analyse.AnalysisResult
import sequence.GeneSequence
import util.io.FilePath

trait Invoker {
  def invoke(identifier: String, database: FilePath, sequence: GeneSequence): AnalysisResult
}
