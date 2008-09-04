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

package com.googlecode.furnace.grid

import org.gridgain.grid.{GridTaskSplitAdapter, GridJobResult, GridJob}
import analyse.{AnalysisResult, SequenceIdentifier}
import java.util.{List => JavaList}
import java.util.{Collection => JavaCollection}
import sequence.GeneSequence

final class GeneSequenceGridTask extends GridTaskSplitAdapter[Iterator[GeneSequence], List[AnalysisResult]] {
  def split(gridSize: Int, inputSequences: Iterator[GeneSequence]): JavaCollection[_ <: GridJob] = {
    error("")
  }

  def reduce(results: JavaList[GridJobResult]): List[AnalysisResult] = error("")
}

object Gridity {
  import GridBootstrapper._
  import org.gridgain.grid.GridTaskFuture
  import scalaz.list.NonEmptyList, NonEmptyList._
  import sequence.GeneSequence._
  import sequence.Base


  def main(args: Array[String]) {
    startMasterNode
    try {
      val future: GridTaskFuture[List[AnalysisResult]] = masterNode.execute(classOf[GeneSequenceGridTask], sequences("ACGT"));
      val results: List[AnalysisResult] = future.get();
    } finally {
      stopMasterNode
    }
  }

  private def sequences(sequence: String) = List(geneSequence(baseSeq(sequence))).elements

  private def baseSeq(bases: String): NonEmptyList[Base] = list(bases.map(_.toByte: Base).toList)
}
