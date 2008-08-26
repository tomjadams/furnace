package com.googlecode.furnace.util.process

import CommandLineProcess._
import Process._
import instinct.expect.Expect._
import instinct.marker.annotate.Specification
import util.io.FilePath._

final class ACommandLineProcess {
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

  @Specification
  def canBuildCommandLinesWithArgumentsUsingApply {
    val p = command("/opt/blast/bin/blastall")("-f")("-o")("-bar", "baz")
    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall -f -o -bar baz")
  }

  @Specification
  def runsSimpleCommands {
    val ls = command("/bin/ls").arg("-l").arg("-a").arg("/")
    val output = ls.execute.output
    expect.that(output).containsString("tmp")
    expect.that(output).containsString("usr")
  }

  @Specification
  def runsSimpleCommandsInAWorkingDirectory {
    val ls = command("/bin/ls").arg("-l")
    val output = ls.executeInDir("/").output
    expect.that(output).containsString("tmp")
    expect.that(output).containsString("usr")
  }
}