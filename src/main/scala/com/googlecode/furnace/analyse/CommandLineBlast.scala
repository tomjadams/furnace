package com.googlecode.furnace.analyse

import System._
import util.process.Process._
import util.process.CommandLineProcess
import util.process.CommandLineProcess._

/*
blastall -p blastn -d database.fas -i inputfile.fas -o outputfile.out

where:
-p = name of the subprogram to use i.e. blastn for nucleotide rather than
blastp for protein
-d = is the database (name of the original fasta file before reformatting -
formatdb output files just have additional extensions)
-i = query (fasta format file. Might be better to use a string here but not
sure how to do it)
-o = name of the output file


/opt/blast/bin/blastall -p blastn -d /opt/blast/data/human3UTR.fas -i /tmp/input.txt -o test.out

 */

object CommandLineBlast {
  lazy val blastHome = getenv("BLAST_HOME")
  lazy val tmpDir = getProperty("java.io.tmpdir")

  def blast(name: String, config: CompleteBlastConfiguration): AnalysisResult = {
    if (blastHome == null) {
      error("BLAST_HOME is not defined. It must point to the BLAST installation directory, e.g. BLAST_HOME=/opt/blast")
    } else {
      val executable = blastHome + "/bin/" + config.searchUtility.name
      val c = command(executable)("-p", config.program.name)("-e 1.0")("-d ecoli.nt")("-i test.txt")("-o test.out")
      val p = c.executeInDir(blastHome)
      println(p.output)
      BlastAnalysisResult("Huzzah!")
    }
  }
}
