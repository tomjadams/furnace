package au.id.adams.mesh.parse

import java.lang.System._
import java.util.regex.Pattern._
import sequence.Base
import sequence.Base._
import sequence.GeneSequence
import sequence.GeneSequence._

// TODO Rename to FastaParser
object GeneSequenceParser {
  lazy val header = compile("^>.*$", MULTILINE)
  lazy val newline = compile(getProperty("line.separator"))
  lazy val nothing = ""

  // TODO Make input a lazy byte[]
  def parse(input: String, sliceSize: Int) = {
    error("")
//    val rawContent = newline.matcher(header.matcher(input).replaceFirst(nothing)).replaceAll(nothing)
//    slice(rawContent.toCharArray.toList, sliceSize)
  }

  // TODO Can this be done using flatMap?
//  private def slice(bases: List[Char], sliceSize: Int): List[GeneSequence] = bases.splitAt(sliceSize) match {
//    case (h, Nil) => List(h)
//    case (h, t) => h :: slice(t, sliceSize)
//  }
}
