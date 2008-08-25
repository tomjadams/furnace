package com.googlecode.furnace.analyse

import util.io.FilePath

trait AnalysisResult {
  def name: String
  def analysisOutput: FilePath
  def outputFormat: OutputFormat
}

sealed trait OutputFormat {
  def html: Boolean
}

object OutputFormat {
  def text: OutputFormat = Text
  def html: OutputFormat = Html
}

private final case object Text extends OutputFormat {
  def html = false
}

private final case object Html extends OutputFormat {
  def html = true
}
