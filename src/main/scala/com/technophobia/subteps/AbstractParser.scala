package com.technophobia.subteps

import scala.util.parsing.combinator.RegexParsers
import com.technophobia.subteps.nodes.{SubstepUsage, Substep}
import java.io.Reader
import com.technophobia.subteps.node.factory.SubstepNodeFactory

abstract class AbstractParser[T] extends RegexParsers  {

  override val skipWhitespace = false
  override val whiteSpace                    = """[ \t]+""".r

  def substepUsage: Parser[SubstepUsage] = """([^:\r\n])+""".r ^^ ((x) => SubstepUsage(x.trim))

  def eol: Parser[Any]                       = """\r?\n""".r

  protected def entryPoint: Parser[T];

  def parse(reader: Reader) = parseAll(entryPoint, reader)

}
