package au.id.adams.mesh.sequence

sealed trait Base {
  def code: Char
}

private case object A extends Base {
  override def code = 'A'
}

private case object C extends Base {
  override def code = 'C'
}

private case object G extends Base {
  override def code = 'G'
}

private case object T extends Base {
  override def code = 'T'
}

object Base {
  import Character._

  implicit def baseToChar(b: Base) = b.code

  implicit def charToBase(c: Char): Base = c.toUpperCase match {
    case 'A' => A
    case 'C' => C
    case 'G' => G
    case 'T' => T
  }

  implicit def byteToBase(b: Byte): Base = b.toChar

  // TODO Why do you need to invoke the implicit explicitly?
  implicit def seqCharToSeqBase(cs: Seq[Char]): Seq[Base] = cs.map(charToBase(_))
}
