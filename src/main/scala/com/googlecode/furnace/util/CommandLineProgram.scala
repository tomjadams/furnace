package com.googlecode.furnace.util

import java.io.File
import file.io.FilePath

sealed trait CommandLineProcess {
  import Argument._

  def apply(option: String): CommandLineProcess = this match {
    case CommandLineProcess_(path, args) => CommandLineProcess_(path, args ::: List(argument(option)))
  }

  def arg(option: String): CommandLineProcess = apply(option)

  def apply(option: String, value: String): CommandLineProcess = this match {
    case CommandLineProcess_(path, args) => CommandLineProcess_(path, args ::: List(argument(option, value)))
  }

  def arg(option: String, value: String): CommandLineProcess = apply(option, value)

  def executable: FilePath

  def args: List[Argument]

  def commandLine: String

  def execute: Process = executeInDir(null)

  def executeInDir(workingDir: FilePath): Process
}

private final case class CommandLineProcess_(executable: FilePath, args: List[Argument]) extends CommandLineProcess {
  import file.io.FilePath._

  lazy val runtime = Runtime.getRuntime

  def commandLine = executable + (args match {
    case Nil => ""
    case a => " " + a.mkString(" ")
  })

  def executeInDir(workingDir: FilePath) = runtime.exec(commandLine, null, workingDir)
}

object CommandLineProcess {
  def command(executable: FilePath): CommandLineProcess = CommandLineProcess_(executable, Nil)
}
