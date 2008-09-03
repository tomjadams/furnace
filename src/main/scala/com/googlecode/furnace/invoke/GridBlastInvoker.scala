/*
 * Copyright 2006-2008 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.furnace.invoke

import org.gridgain.grid.{GridJob, GridJobResult, GridTaskSplitAdapter}
import analyse.{AnalysisResult, SequenceIdentifier}
import java.util.{List => JavaList}
import java.util.{Collection => JavaCollection}
import java.util.Collections
import sequence.GeneSequence
import util.io.FilePath

// implicit from this to GridJob?
final class GridBlastInvoker extends Invoker {
  def invoke(identifier: SequenceIdentifier, database: FilePath, sequence: GeneSequence) = {
    //
    //    val input = sequence.write(identifier)
    //    val config = blastConfig(database, input)
    //    !blast(identifier, config)
    error("Foo")
  }
}

final class ATask extends GridTaskSplitAdapter[Iterator[GeneSequence], List[AnalysisResult]] {
  def split(gridSize: Int, inputSequences: Iterator[GeneSequence]): JavaCollection[_ <: GridJob] = {
    error("")
  }

  def reduce(results: JavaList[GridJobResult]): List[AnalysisResult] = error("")

}



