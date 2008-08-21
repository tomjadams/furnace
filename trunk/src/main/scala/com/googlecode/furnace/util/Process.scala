package com.googlecode.furnace.util

final case class Process(p: java.lang.Process) {
  import scalaz.javas.InputStream.InputStreamByteStream

  def output = p.getInputStream.map(_.toChar).mkString
}

object Process {
  implicit def processToProcess(p: java.lang.Process): Process = Process(p)
}
