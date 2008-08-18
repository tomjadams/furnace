package com.googlecode.furnace

import sequence.{AGeneSequence}
import parse.{AFastaParserWithoutAHeader, AFastaParserWithAHeader}
import instinct.runner.TextRunner

object SpecificationRunner {
  def main(args: Array[String]) {
    val contexts = Array(classOf[AGeneSequence], classOf[AFastaParserWithoutAHeader], classOf[AFastaParserWithAHeader])
    TextRunner.runContexts(contexts: _*)
  }
}
