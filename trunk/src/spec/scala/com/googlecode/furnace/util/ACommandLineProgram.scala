package com.googlecode.furnace.util

import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import file.io.FilePath._
import CommandLineProgram._

final class ACommandLineProgram {
  @Specification
  def canBuildCommandLinesWithoutArguments {
    val p1 = command("/opt/blast/bin/blastall")
    expect.that(p1.commandLine).isEqualTo("/opt/blast/bin/blastall")
  }
}
