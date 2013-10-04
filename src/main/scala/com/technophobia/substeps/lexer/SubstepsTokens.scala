package com.technophobia.substeps.lexer

import scala.util.parsing.combinator.token.Tokens

trait SubstepsTokens extends Tokens {

  case class Keyword(chars: String) extends Token {

    override def toString = s"'${chars}'"
  }

  case class Text(chars: String) extends Token {

    override def toString = s"text ${chars}"
  }

  case class Parameter(chars: String) extends Token {

    override def toString = s"<${chars}>"
  }

  case class Comment(chars: String) extends Token {

    override def toString = s"Comment ${chars}"
  }

  case class NewLine() extends Token {

    val chars = "\n"
    override def toString = chars
  }

  case class WhiteSpace() extends Token {

    val chars = " "
    override def toString = chars

  }
}
