package com.googlecode.furnace

import instinct.runner.TextRunner
import sequence.AGeneSequence

object SpecificationRunner {
  def main(args: Array[String]) {
    val contexts = Array(classOf[AGeneSequence])
    TextRunner.runContexts(contexts: _*)
  }
}
