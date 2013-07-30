package com.technophobia.subteps

import scala.util.parsing.combinator.{PackratParsers, RegexParsers}
import scala.util.parsing.combinator.PackratParsers.PackratParser

class ProblemParser extends RegexParsers with PackratParsers {

  override val skipWhitespace = false
  override val whiteSpace                    = """[ \t]+""".r

  def eol: Parser[Any]                       = """(\r?\n)+""".r

  def sections : Parser[Any] = repsep(section, eol)

  def section : Parser[Any] = specificLine ~ lines

  def lines : Parser[Any] = repsep(line, eol)

  def specificLine : Parser[Any] = "Specific:" ~> line

  def line : Parser[Any] = """[^:]+""".r

}
