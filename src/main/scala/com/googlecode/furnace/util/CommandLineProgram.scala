package com.googlecode.furnace.util

import file.io.FilePath

sealed trait Argument

private final case class ArgumentWithoutValue(option: String) extends Argument {
  override def toString = option
}

private final case class ArgumentWithValue(option: String, value: String) extends Argument {
  override def toString = option + " " + value
}

object Argument {
  def argument(option: String): Argument = ArgumentWithoutValue(option)
  def argument(option: String, value: String): Argument = ArgumentWithValue(option, value)
}

sealed trait Program {
  import Argument._

  def arg(option: String): Program = this match {
    case Program_(path, args) => Program_(path, args + argument(option))
  }

  def arg(option: String, value: String): Program = this match {
    case Program_(path, args) => Program_(path, args + argument(option, value))
  }

  def executable: FilePath

  def args: List[Argument]

  def commandLine: String

  def execute: Unit
}

private final case class Program_(executable: FilePath, args: List[Argument]) extends Program {
  import file.io.FilePath._

  def commandLine = executable + (args match {
    case Nil => ""
    case a => " " + a.mkString(" ")
  })

  def execute: Unit = error("TODO")
}

object CommandLineProgram {
  def command(executable: FilePath): Program = Program_(executable, Nil)
}
