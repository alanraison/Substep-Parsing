package com.technophobia.substeps

import com.technophobia.substeps.nodes.{UnresolvedSubstepUsage, SubstepUsage, Substep}
import com.technophobia.substeps.node.factory.SubstepNodeFactory

class SubstepsFileParser extends AbstractParser[List[Substep]] {

  protected override def entryPoint = substepsFile;

  private def substepsFile: Parser[List[Substep]] = rep(eol) ~> repsep(substepDef, rep1(eol)) <~ rep(eol)

  private def substepDef: Parser[Substep] = (substepNameLine <~ rep1(eol)) ~ rep1sep(substepUsage, eol) ^^ {

    case(substepsName ~ substepUsages) => SubstepNodeFactory.defineFromFile(substepsName, substepUsages)
  }

  def substepUsage: Parser[SubstepUsage] = """([^:\r\n])+""".r ^^ ((x) => UnresolvedSubstepUsage(x.trim))

  private def substepNameLine: Parser[String] = "Define:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r ^^ ((x) => x.trim)
}
