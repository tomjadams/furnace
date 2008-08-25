package com.googlecode.furnace.invoke

import analyse.OutputFormat._
import analyse.blast.BlastConfiguration._
import analyse.blast.CommandLineBlast._
import sequence.GeneSequence
import util.io.FilePath

final class LocalBlastInvoker extends Invoker {
  def invoke(identifier: String, database: FilePath, sequence: GeneSequence) = {
    // TODO Write sequence to file...
    val config = blastConfig(database, "/tmp/input.txt")
    !blast(identifier, config)
  }
}
