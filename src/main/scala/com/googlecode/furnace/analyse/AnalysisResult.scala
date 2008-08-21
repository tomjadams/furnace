package com.googlecode.furnace.analyse

import file.io.FilePath

trait AnalysisResult {
  def analysisOutput: FilePath
}