package com.googlecode.furnace.analyse

// blastall -p blastn  -e 1.0 -d ecoli.nt -i test.txt -o test.out.
object CommandLineBlast {
  // TODO Parameterise the analyse directory
  val blastHome = "/opt/blast"
  val blastAllBinary = blastHome + "/bin/blastall"

  def blast() {
//    val command = blastAllBinary + ""
//    val analyse = Runtime.exec(command, null, blastHome)
//    println(analyse)
  }
}
