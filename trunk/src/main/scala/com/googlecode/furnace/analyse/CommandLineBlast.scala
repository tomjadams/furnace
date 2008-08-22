package com.googlecode.furnace.analyse

import System._
import util.process.Process._
import util.process.CommandLineProcess
import util.process.CommandLineProcess._

// file:///opt/blast-2.2.18/doc/index.html
// blastall -p blastn  -e 1.0 -d ecoli.nt -i test.txt -o test.out.
object CommandLineBlast {
  // TODO Parameterise the analyse directory
  lazy val blastHome = getenv("BLAST_HOME")
  lazy val blastAllBinary = blastHome + "/bin/blastall"

  def blast() {
    val c = command("/opt/blast/bin/blastall")("-f")("-o")("-p blastn  -e 1.0 -d ecoli.nt -i test.txt -o test.out")
    val p = c.executeInDir(blastHome)
    println(p.output)
  }
}
