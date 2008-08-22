package com.googlecode.furnace.analyse

import util.io.FilePath

sealed trait InCompleteBlastConfiguration {
  def apply(searchUtility: BlastSearchUtility): InCompleteBlastConfiguration = this match {
    case InCompleteBlastConfiguration_(s, p, e, d, i, o) => InCompleteBlastConfiguration_(Some(searchUtility), p, e, d, i, o)
  }

  def apply(program: BlastProgram): InCompleteBlastConfiguration = this match {
    case InCompleteBlastConfiguration_(s, p, e, d, i, o) => InCompleteBlastConfiguration_(s, Some(program), e, d, i, o)
  }

  def apply(expectation: Double): InCompleteBlastConfiguration = this match {
    case InCompleteBlastConfiguration_(s, p, e, d, i, o) => InCompleteBlastConfiguration_(s, p, Some(expectation), d, i, o)
  }

  def apply(database: String): InCompleteBlastConfiguration = this match {
    case InCompleteBlastConfiguration_(s, p, e, d, i, o) => InCompleteBlastConfiguration_(s, p, e, Some(database), i, o)
  }

  def apply(inputSequence: FilePath): InCompleteBlastConfiguration = this match {
    case InCompleteBlastConfiguration_(s, p, e, d, i, o) => InCompleteBlastConfiguration_(s, p, e, d, Some(inputSequence), o)
  }

  def apply(outputFormat: OutputFormat): InCompleteBlastConfiguration = this match {
    case InCompleteBlastConfiguration_(s, p, e, d, i, o) => CompleteBlastConfiguration_(s, p, e, d, i, Some(outputFormat))
  }
}

private final case class InCompleteBlastConfiguration_(searchUtility: Option[BlastSearchUtility], program: Option[BlastProgram], expectation: Option[Double], database: Option[String], inputSequence: Option[FilePath], outputFormat: Option[OutputFormat]) extends InCompleteBlastConfiguration

sealed trait CompleteBlastConfiguration {
  def searchUtility: BlastSearchUtility

  def program: BlastProgram

  def expectation: Double

  def database: String

  def inputSequence: FilePath

  def outputFormat: OutputFormat
}

private final case class CompleteBlastConfiguration_(searchUtility: BlastSearchUtility, program: BlastProgram, expectation: Double, database: String, inputSequence: FilePath, outputFormat: OutputFormat) extends CompleteBlastConfiguration

object BlastConfiguration {
  def blastConfig: InCompleteBlastConfiguration = InCompleteBlastConfiguration_(Some(BlastAll), Some(BlastN), Some(10.0), None, None, Some(Text))
}

sealed trait BlastSearchUtility {
  def name: String
  override def toString = name.toString
}

private final case object BlastAll extends BlastSearchUtility {
  override def name = "blastall"
}

object BlastSearchUtility {
  def blastall: BlastSearchUtility = BlastAll
}

sealed trait BlastProgram {
  def name: String
}

private final case object BlastP extends BlastProgram {
  override def name = "blastp"
}

private final case object BlastN extends BlastProgram {
  override def name = "blastn"
}

private final case object BlastX extends BlastProgram {
  override def name = "blastx"
}

private final case object TBlastN extends BlastProgram {
  override def name = "tblastn"
}

private final case object TBlastX extends BlastProgram {
  override def name = "tblastx"
}

object BlastProgram {
  def blastp: BlastProgram = BlastP
  def blastn: BlastProgram = BlastN
  def blastx: BlastProgram = BlastX
  def tblastn: BlastProgram = TBlastN
  def tblastx: BlastProgram = TBlastX
}

sealed trait OutputFormat {
  def html: Boolean
}

private final case object Text extends OutputFormat {
  def html = false
}

private final case object Html extends OutputFormat {
  def html = true
}
