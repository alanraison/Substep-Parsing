package com.technophobia.subteps

import com.technophobia.subteps.nodes.{SubstepUsage, Substep}
import com.technophobia.subteps.node.factory.SubstepNodeFactory

class SubstepsFileParser extends AbstractParser[List[Substep]] {

  protected override def entryPoint = substepsFile;

  private def substepsFile: Parser[List[Substep]] = rep(eol) ~> repsep(substepDef, rep1(eol)) <~ rep(eol)

  private def substepDef: Parser[Substep] = (substepNameLine <~ rep1(eol)) ~ rep1sep(substep, eol) ^^ {

    case(substepsName ~ substeps) => SubstepNodeFactory.defineFromFile(substepsName, substeps)

  }

  def substep: Parser[Substep] = """([^:\r\n])+""".r ^^ ((x) => SubstepNodeFactory.find(x.trim))

  private def substepNameLine: Parser[String] = "Define:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r ^^ ((x) => x.trim)
}
