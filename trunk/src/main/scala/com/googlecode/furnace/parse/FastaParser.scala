package com.googlecode.furnace.parse

import sequence.{Base, GeneSequence}
import sequence.Base._
import sequence.GeneSequence._

object FastaParser {
  private lazy val newLines = List('\n', '\r')

  def parse(input: Iterator[Byte], sliceSize: Int): Iterator[GeneSequence] = new Iterator[GeneSequence] {
    def hasNext = input.hasNext

    def next = getNext

    private def getNext =
      if (input.hasNext) {
        val x = input.next
        println(">>> " + x)
        val sss = geneSequence(List(x))
        println(">>> " + sss.toString)
        sss
      } else {
        emptySequence
      }


    //    var i = input.next
    //
    //    println(i)
    //
    //    def next = {
    //      if (i == -1)
    //        error("Iterator.next (no more elements)")
    //      else {
    //        val r = i
    //        i = input.next
    //        r.toByte
    //        geneSequence(List('C'))
    //      }
    //    }
    //
  }
}
