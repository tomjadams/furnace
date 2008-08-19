package com.googlecode.furnace

import instinct.locate.ContextFinderImpl
import instinct.runner.TextRunner
import java.io.File

object SpecificationRunner {
  private lazy val contextFinder = new ContextFinderImpl(SpecificationRunner.getClass)

  def main(args: Array[String]) {
    val classes = contextFinder.getContextNames().map(name => Class.forName(name.getFullyQualifiedName))
    TextRunner.runContexts(classes: _*)
  }
}
