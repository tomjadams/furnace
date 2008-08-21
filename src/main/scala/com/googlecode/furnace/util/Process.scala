package com.googlecode.furnace.util

object Process {
  implicit def pimpedProcess(p: java.lang.Process) = new {
    import scalaz.javas.InputStream.InputStreamByteStream

    def output = p.getInputStream.map(_.toChar).mkString
  }
}
