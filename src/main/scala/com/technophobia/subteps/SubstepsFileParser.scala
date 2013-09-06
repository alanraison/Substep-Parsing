package com.technophobia.subteps

import com.technophobia.subteps.nodes.Substep
import com.technophobia.subteps.node.factory.SubstepNodeFactory

class SubstepsFileParser extends AbstractParser[List[Substep]] {

  protected override def entryPoint = substepsFile;

  private def substepsFile: Parser[List[Substep]] = rep(eol) ~> repsep(substepDef, rep1(eol)) <~ rep(eol)

  private def substepDef: Parser[Substep] = (substepNameLine <~ rep1(eol)) ~ rep1sep(substepUsage, eol) ^^ {

    case(substepsName ~ substepUsages) => SubstepNodeFactory.defineFromFile(substepsName, substepUsages)

  }

  private def substepNameLine: Parser[String] = "Define:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r ^^ ((x) => x.trim)
}
