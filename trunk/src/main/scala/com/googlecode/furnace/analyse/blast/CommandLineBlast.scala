package com.googlecode.furnace.analyse.blast

import analyse.OutputFormat._
import java.io.File
import java.io.File._
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
    if (config.blastHome.isEmpty) {
      error("BLAST_HOME is not defined. It must point to the root of the BLAST installation directory, e.g. BLAST_HOME=/opt/blast")
    } else {
      val outputFile = createTempFile("BlastRun_" + name, ".txt")
      val executable = config.blastHome + "/bin/" + config.searchUtility.name
      val c = command(executable)("-p", config.program.name)("-e", config.expectation.toString)("-d", config.database)("-i", config.inputSequence)("-o", outputFile)
      val p = c.executeInDir(config.blastHome)
      BlastAnalysisResult(name, outputFile, config.outputFormat)
    }
  }

  private implicit def fileToString(f: File): String = f: FilePath
}

private final case class CommandLineBlast_(name: String, config: BlastConfiguration) extends CommandLineBlast

object CommandLineBlast {
  def blast(name: String, config: BlastConfiguration): CommandLineBlast = CommandLineBlast_(name: String, config: BlastConfiguration)
}
