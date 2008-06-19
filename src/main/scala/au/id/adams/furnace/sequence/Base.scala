package au.id.adams.furnace.sequence

sealed trait Base {
  def code: Char

  override def toString = code.toString
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

private case class Other(code: Char) extends Base

object Base {
  import Character._

  implicit def baseToChar(b: Base) = b.code

  implicit def charToBase(c: Char): Base = c.toUpperCase match {
    case 'A' => A
    case 'C' => C
    case 'G' => G
    case 'T' => T
    //    case code => error("Unknown base: '" + code + "'")
    case b => Other(b)
  }

  implicit def byteToBase(b: Byte): Base = b.toChar

  // TODO Why do you need to invoke the implicit explicitly?
  implicit def seqByteToSeqBase(cs: Seq[Byte]): Seq[Base] = cs.map(byteToBase(_))
}
