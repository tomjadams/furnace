package com.googlecode.furnace.analyse.blast

import analyse.OutputFormat
import util.io.FilePath

final case class BlastAnalysisResult(identifier: SequenceIdentifier, analysisOutput: FilePath, outputFormat: OutputFormat) extends AnalysisResult
