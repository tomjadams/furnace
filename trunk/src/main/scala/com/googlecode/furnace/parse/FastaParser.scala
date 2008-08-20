package com.googlecode.furnace.parse

import sequence.GeneSequence

/**
 * A <a href="http://en.wikipedia.org/wiki/Fasta_format">FASTA</a> parser.
 * The parser returns an iterator of sequences, where each sequence is <var>sliceSize</var> long, the header is ignored (if present).
 * Note that the parser is lazy, in that the underlying <code>input</code> will not be read until <code>next()</code> is called on the returned
 * iterator.
 * @param input An iterator of bytes, representing a sequence to be parsed (it may contain a header).
 * @param sliceSize The size of each sequence to return.
 * @return <code>None</code> if the <var>input</var> is empty, or a <code>Some</code> containing an iterator of gene sequences.
 */
object FastaParser {
  private lazy val lineSeparators = List('\r'.toByte, '\n'.toByte)

  def parse(input: Iterator[Byte], sliceSize: Int): Option[Iterator[GeneSequence]] =
    if (input.hasNext) {
      Some(new FastaSequenceIterator(input.dropWhile(!lineSeparators.contains(_)), sliceSize))
    } else {
      None
    }
}
