package com.googlecode.furnace.invoke

import System._
import analyse.OutputFormat._
import analyse.SequenceIdentifier
import analyse.blast.BlastConfiguration._
import analyse.blast.CommandLineBlast._
import java.io.{BufferedOutputStream, File, FileOutputStream}
import sequence.GeneSequence
import util.io.FilePath
import util.io.FilePath._

final class LocalBlastInvoker extends Invoker {
  private lazy val tempDir = getProperty("java.io.tmpdir")

  def invoke(identifier: SequenceIdentifier, database: FilePath, sequence: GeneSequence) = {
    val input = writeSequenceToFile(identifier, sequence)
    val config = blastConfig(database, input)
    !blast(identifier, config)
  }

  private def writeSequenceToFile(identifier: SequenceIdentifier, sequence: GeneSequence): FilePath = {
    val inputFile = new File(tempDir, "InputSplit_" + identifier + ".fasta")
    val out = new BufferedOutputStream(new FileOutputStream(inputFile))
    try {
      (">Source=" + identifier.inputSequence + ",slice=" + identifier.sliceSize + ",split=" + identifier.splitId).foreach(out.write(_))
      out.write('\n')
      sequence.toString.foreach(out.write(_))
      out.write('\n')
      out.flush
    } finally {
      out.close
    }
    inputFile
  }
}
