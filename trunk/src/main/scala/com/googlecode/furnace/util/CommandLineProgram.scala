package com.googlecode.furnace.util

import java.io.File
import file.io.FilePath

sealed trait CommandLineProgram {
  import Argument._

  def apply(option: String): CommandLineProgram = this match {
    case CommandLineProgram_(path, args) => CommandLineProgram_(path, args ::: List(argument(option)))
  }

  def arg(option: String): CommandLineProgram = apply(option)

  def apply(option: String, value: String): CommandLineProgram = this match {
    case CommandLineProgram_(path, args) => CommandLineProgram_(path, args ::: List(argument(option, value)))
  }

  def arg(option: String, value: String): CommandLineProgram = apply(option, value)

  def executable: FilePath

  def args: List[Argument]

  def commandLine: String

  def execute: Process = executeInDir(null)

  def executeInDir(workingDir: FilePath): Process
}

private final case class CommandLineProgram_(executable: FilePath, args: List[Argument]) extends CommandLineProgram {
  import file.io.FilePath._

  lazy val runtime = Runtime.getRuntime

  def commandLine = executable + (args match {
    case Nil => ""
    case a => " " + a.mkString(" ")
  })

  def executeInDir(workingDir: FilePath) = runtime.exec(commandLine, null, workingDir)
}

object CommandLineProgram {
  def command(executable: FilePath): CommandLineProgram = CommandLineProgram_(executable, Nil)
}
