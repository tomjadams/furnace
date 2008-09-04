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

import java.util.{Set, AbstractSet, List => JavaList, AbstractMap, AbstractList}
import analyse.{AnalysisResult, SequenceIdentifier}
import java.util.Map.Entry
import org.gridgain.grid.{GridTaskSplitAdapter, GridNode, GridJobResult, GridTaskAdapter, GridJob}
import sequence.GeneSequence

final class DoesNotWorkGeneSequenceGridTask extends GridTaskSplitAdapter[Iterator[GeneSequence], List[AnalysisResult]] {
  def split(gridSize: Int, inputSequences: Iterator[GeneSequence]) = new AbstractList[GridJob] {
    override def isEmpty = !inputSequences.hasNext
    override def iterator = error("Show me an iterator!")
    override def size = error("Cannot call size() on this list")
    override def get(index: Int) = error("Cannot call get() on this list")
  }

  def reduce(results: JavaList[GridJobResult]) = error("I'm not really a list")
}

final class GeneSequenceGridTask extends GridTaskAdapter[Iterator[GeneSequence], List[AnalysisResult]] {

  def map(subgrid: JavaList[GridNode], arg: Iterator[GeneSequence]) =
    new AbstractMap[GridJob, GridNode] {
      override def entrySet: Set[Entry[GridJob, GridNode]] = {
        println(">>>>>>>>> entrySet called")
        new AbstractSet[Entry[GridJob, GridNode]] {
          override def iterator: java.util.Iterator[Entry[GridJob, GridNode]] = error("Show me an iterator!")
          override def size = error("Cannot call size() on this set")
        }
      }
    }

  //  def split(gridSize: Int, inputSequences: Iterator[GeneSequence]) = new AbstractList[GridJob] {
  //    override def isEmpty = !inputSequences.hasNext
  //    override def iterator = error("Show me an iterator!")
  //    override def size = error("Cannot call size() on this list")
  //    override def get(index: Int) = error("Cannot call get() on this list")
  //  }

  def reduce(results: JavaList[GridJobResult]) = error("I'm not really a list")
}

object Gridity {
  import GridBootstrapper._
  import org.gridgain.grid.GridTaskFuture
  import scalaz.list.NonEmptyList, NonEmptyList._
  import sequence.GeneSequence._
  import sequence.Base

  def main(args: Array[String]): Unit = {
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
