package com.technophobia.substeps.lexer

import scala.util.parsing.combinator.token.Tokens

trait SubstepsTokens extends Tokens {

  case object FeatureToken extends Token {

    val chars = "Feature:"
    override def toString = chars
  }

  case object ScenarioToken extends Token {

    val chars = "Scenario:"
    override def toString = chars
  }

  case object ScenarioOutlineToken extends Token {

    val chars = "ScenarioOutline:"
    override def toString = chars
  }

  case object ExamplesToken extends Token {

    val chars = "Examples:"
    override def toString = chars
  }

  case object TagsToken extends Token {

    val chars = "Tags:"
    override def toString = chars
  }

  case class TextToken(chars: String) extends Token {

    override def toString = s"Text ${chars}"
  }

  case class ParameterToken(chars: String) extends Token {

    override def toString = s"<${chars}>"
  }

  case class CommentToken(chars: String) extends Token {

    override def toString = s"Comment ${chars}"
  }

  case object NewLineToken extends Token {

    val chars = "\n"
    override def toString = chars
  }

  case object WhiteSpaceToken extends Token {

    val chars = " "
    override def toString = chars

  }
}
