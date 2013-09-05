package com.technophobia.subteps

import scala.util.parsing.combinator.RegexParsers

class ProblemParser extends RegexParsers {

  override val skipWhitespace = false
  override val whiteSpace                    = """[ \t]+""".r

  def eol: Parser[Any]                       = """\r?\n""".r

  def sections : Parser[Any] = rep(section) ~ rep(eol)

  def section : Parser[Any] = specificLine ~ rep(line)

  def specificLine : Parser[Any] = "Specific:" <~ line

  def line : Parser[Any] = """[^:\n\r]+""".r ~ rep1(eol)

  //  def specificLine : Parser[Any] = """Specific:[^:\n\r]+""".r ~ eol

}
