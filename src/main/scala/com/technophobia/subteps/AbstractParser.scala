package com.technophobia.subteps

import scala.util.parsing.combinator.RegexParsers
import com.technophobia.subteps.nodes.Substep

abstract class AbstractParser extends RegexParsers  {

  override val skipWhitespace = false
  override val whiteSpace                    = """[ \t]+""".r

  def substep: Parser[Substep] = """([^:\r\n])+""".r ^^ ((x) => Substep(x))

  def eol: Parser[Any]                       = """(\r?\n)+""".r

}
