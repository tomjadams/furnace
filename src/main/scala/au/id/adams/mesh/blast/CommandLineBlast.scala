package au.id.adams.mesh.blast

// blastall -p blastn -d ecoli.nt -i test.txt -o test.out.
object CommandLineBlast {
  // TODO Parameterise the blast directory
  val blastHome = "/opt/blast"
  val blastAllBinary = blastHome + "/bin/blastall"

  def blast {
//    val command = blastAllBinary + ""
//    val blast = Runtime.exec(command, null, blastHome)
//    println(blast)
  }
}
