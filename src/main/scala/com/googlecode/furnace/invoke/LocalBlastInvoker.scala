package com.googlecode.furnace.invoke

import analyse.OutputFormat._
import analyse.blast.BlastConfiguration._
import analyse.blast.CommandLineBlast._
import sequence.GeneSequence

// /opt/blast/bin/blastall -p blastn -d /opt/blast/data/human3UTR.fas -i /tmp/input.txt -o test.out
final class LocalBlastInvoker extends Invoker {
  def invoke(sequence: GeneSequence) = {
    // TODO Write sequence to file...
    // TODO Expose database
    val config = blastConfig("/opt/blast/data/human3UTR.fas", "/tmp/input.txt")
    !blast("A test run", config)
  }
}
