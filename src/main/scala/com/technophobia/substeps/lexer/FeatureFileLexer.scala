package com.technophobia.substeps.lexer

import scala.util.parsing.combinator.lexical.Lexical
import scala.util.matching.Regex
import scala.util.parsing.input.CharSequenceReader

class FeatureFileLexer extends Lexical with SubstepsTokens {

  def token: Parser[Token] =
    (  rep1(char) <~ ":" ^^ { case letters => Keyword(letters mkString "")}
      |"<" ~> rep(char) <~ ">"   ^^ { case x => Parameter(x mkString "") }
      |eol                         ^^ { case _ => NewLine()}
      |rep1(char)                 ^^ { case list => Text(list.mkString(""))}
      |whitespaceParser            ^^ { case _ => WhiteSpace()}
      )

  def eol: Parser[Any]                       = """\r?\n""".r

  def char = chrExcept(':', ' ', '\t', '\n', '\r', '<', '>')

  def whitespaceParser : Parser[Any] = """[ \t]+""".r

  //override val whitespace: Parser[Any] = failure("whitespace must match explicitly")
  //override val whitespace: Parser[Any] = """[ \t]+""".r

  override def whitespace = ""

  def createScanner(chars: CharSequence, startOffset: Int) = new Scanner(new CharSequenceReader(chars, startOffset))

  implicit def regex(r: Regex): Parser[String] = new Parser[String] {
    def apply(in: Input) = {
      val source = in.source
      val offset = in.offset
      val start = offset
      (r findPrefixMatchOf (source.subSequence(start, source.length))) match {
        case Some(matched) =>
          Success(source.subSequence(start, start + matched.end).toString,
            in.drop(start + matched.end - offset))
        case None =>
          val found = if (start == source.length()) "end of source" else "`"+source.charAt(start)+"'"
          Failure("string matching regex `"+r+"' expected but "+found+" found", in.drop(start - offset))
      }
    }
  }

  implicit def literal(s: String): Parser[String] = new Parser[String] {
    def apply(in: Input) = {
      val source = in.source
      val offset = in.offset
      val start = offset
      var i = 0
      var j = start
      while (i < s.length && j < source.length && s.charAt(i) == source.charAt(j)) {
        i += 1
        j += 1
      }
      if (i == s.length)
        Success(source.subSequence(start, j).toString, in.drop(j - offset))
      else  {
        val found = if (start == source.length()) "end of source" else "`"+source.charAt(start)+"'"
        Failure("`"+s+"' expected but "+found+" found", in.drop(start - offset))
      }
    }
  }

}
