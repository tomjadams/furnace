package com.googlecode.furnace.analyse

import util.io.FilePath

trait AnalysisResult {
  def analysisOutput: FilePath
}