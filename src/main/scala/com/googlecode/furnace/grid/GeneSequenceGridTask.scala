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

import org.gridgain.grid.resources.GridLoadBalancerResource
import org.gridgain.grid.{GridTaskSplitAdapter, GridLoadBalancer, GridNode, GridJobResult, GridTaskAdapter, GridJob}
import java.util.{Set, AbstractSet, Iterator => JavaIterator, List => JavaList, AbstractMap, AbstractList}
import analyse.{AnalysisResult, SequenceIdentifier}
import java.util.Map.Entry
import sequence.GeneSequence

final class GeneSequenceGridTask extends GridTaskAdapter[Iterator[GeneSequence], List[AnalysisResult]] {
//  @GridLoadBalancerResource
  var balancer: GridLoadBalancer = null

  def setBalancer(balancer: GridLoadBalancer) = { this.balancer = balancer }

  def map(subgrid: JavaList[GridNode], inputSequences: Iterator[GeneSequence]) =
    new AbstractMap[GridJob, GridNode] {
      override def entrySet: Set[Entry[GridJob, GridNode]] = new AbstractSet[Entry[GridJob, GridNode]] {
        override def iterator: JavaIterator[Entry[GridJob, GridNode]] = new JavaIterator[Entry[GridJob, GridNode]] {
          override def hasNext = inputSequences.hasNext

          override def next: Entry[GridJob, GridNode] = {
            val job = SequenceGridJob(inputSequences.next)
            val b = balancer.getBalancedNode(job)
            JobEntry(job, b)
          }

          override def remove = error("Removal not supported")
        }

        override def isEmpty = !inputSequences.hasNext

        override def size = 1
      }
    }

  def reduce(results: JavaList[GridJobResult]) = error("I'm not really a list")
}

final case class JobEntry(job: GridJob, node: GridNode) extends Entry[GridJob, GridNode] {
  def getKey = job
  def getValue = node
  def setValue(value: GridNode) = error("Not supported")
}

final case class SequenceGridJob(sequence: GeneSequence) extends GridJob {
  import analyse.{AnalysisResult, OutputFormat, SequenceIdentifier}
  import analyse.OutputFormat._
  import analyse.SequenceIdentifier._
  import analyse.blast.BlastAnalysisResult
  import java.io.Serializable
  import util.io.FilePath
  import util.io.FilePath._

  override def cancel { }

  override def execute = BlastAnalysisResult(id("/foo/bar.in", 0, 0), "/foo/bar.out", text)
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
