package com.googlecode.furnace.util

import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import file.io.FilePath._
import CommandLineProgram._

final class ACommandLineProgram {
  @Specification
  def canBuildCommandLinesWithoutArguments {
    val p = command("/opt/blast/bin/blastall")
    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall")
  }

  @Specification
  def canBuildCommandLinesWithArguments {
    val p = command("/opt/blast/bin/blastall").arg("-f").arg("-o").arg("-bar", "baz")
    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall -f -o -bar baz")
  }
}
