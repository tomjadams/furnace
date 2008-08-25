package com.googlecode.furnace.analyse.blast

import analyse.OutputFormat._
import java.io.File
import java.io.File._
import util.Logger._
import util.io.FilePath
import util.io.FilePath._
import util.process.Process._
import util.process.CommandLineProcess
import util.process.CommandLineProcess._

sealed trait CommandLineBlast {
  def unary_!(): AnalysisResult = this match {
    case CommandLineBlast_(name, config) => execute(name, config)
  }

  private def execute(name: String, config: BlastConfiguration) = {
    val outputFile = createTempFile("BlastReport_" + name.replaceAll(" ", "_"), config.outputFormat.fileExtension)
    val executable = config.blastHome + "/bin/" + config.searchUtility.name
    val c = command(executable)("-p", config.program.name)("-e", config.expectation.toString)("-d", config.database)("-i", config.inputSequence)("-o", outputFile)("-T", config.outputFormat.isHtml)
    info("Invoking BLAST using command: " + c.commandLine)
    val p = c.executeInDir(config.blastHome)
    BlastAnalysisResult(name, outputFile, config.outputFormat)
  }

  private implicit def fileToString(f: File): String = f: FilePath

  private implicit def booleanToBlastCommandString(b: Boolean): String = if (b) "T" else "F"
}

private final case class CommandLineBlast_(name: String, config: BlastConfiguration) extends CommandLineBlast

object CommandLineBlast {
  def blast(name: String, config: BlastConfiguration): CommandLineBlast = CommandLineBlast_(name: String, config: BlastConfiguration)
}
