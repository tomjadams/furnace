package com.googlecode.furnace.analyse

import util.io.FilePath

trait AnalysisResult {
  def name: String
  def analysisOutput: FilePath
  def outputFormat: OutputFormat
}

sealed trait OutputFormat {
  def isHtml: Boolean
  def fileExtension: String
}

object OutputFormat {
  def text: OutputFormat = Text
  def html: OutputFormat = Html
}

private final case object Text extends OutputFormat {
  def isHtml = false
  def fileExtension = ".txt"
}

private final case object Html extends OutputFormat {
  def isHtml = true
  def fileExtension = ".html"
}
