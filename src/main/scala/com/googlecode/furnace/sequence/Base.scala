package com.googlecode.furnace.sequence

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

object Base {
  import Character._

  def bases = List(A, C, G, T)

  implicit def baseToChar(b: Base) = b.code

  implicit def charToBase(c: Char): Base = c.toUpperCase match {
    case 'A' => A
    case 'C' => C
    case 'G' => G
    case 'T' => T
    case code => error("Unknown base: '" + code + "'")
  }

  implicit def byteToBase(b: Byte): Base = b.toChar
}
