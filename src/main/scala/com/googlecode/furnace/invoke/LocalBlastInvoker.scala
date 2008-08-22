package com.googlecode.furnace.invoke

import sequence.GeneSequence
import analyse.blast

final class LocalBlastInvoker extends Invoker {
  def invoke(sequence: GeneSequence) = CommandLineBlast.blast()
}
