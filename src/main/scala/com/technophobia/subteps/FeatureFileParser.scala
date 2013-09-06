package com.technophobia.subteps

import com.technophobia.subteps.nodes.{ScenarioOutline, BasicScenario, Feature, Scenario}
import java.io.Reader

class FeatureFileParser extends AbstractParser {

  def parse(reader: Reader) = parseAll(featureFile, reader)

  private def featureFile: Parser[Feature] = opt((tagDef <~ rep1(eol))) ~ (featureDef <~ rep1(eol)) ~ (rep(scenario) <~ rep(eol)) ^^ {

    case (Some(tags) ~ featureName ~ scenarios) => Feature(featureName, tags, scenarios)
    case (None ~ featureName ~ scenarios) => Feature(featureName, Nil, scenarios)
  }

  private def tagDef: Parser[List[String]] = "Tags:" ~> opt(whiteSpace) ~> repsep(tag, whiteSpace)

  private def tag: Parser[String]   = """[1-9A-Za-z-_~]+""".r

  private def featureDef: Parser[String] = "Feature:" ~> opt(whiteSpace) ~> """[^\r\n]+""".r

  private def scenario: Parser[Scenario] = basicScenario | scenarioOutline

  private def basicScenario: Parser[BasicScenario] = (opt(tagDef <~ eol) ~ scenarioDef <~ eol) ~ rep1sep(substep, eol) <~ rep(eol) ^^ {

    case (Some(tags) ~ scenarioName ~ substeps) => BasicScenario(scenarioName, tags, substeps)
    case (None ~ scenarioName ~ substeps) => BasicScenario(scenarioName, Nil, substeps)
  }

  private def scenarioDef: Parser[String] = "Scenario:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r

  private def scenarioOutline: Parser[ScenarioOutline] = (opt(tagDef <~ rep1(eol))) ~ (scenarioOutlineDef <~ rep1(eol)) ~ (rep1sep(substep, eol) <~ rep(eol)) ~ exampleSection <~ rep(eol) ^^ {

    case (Some(tags) ~ scenarioName ~ substeps ~ examples) => ScenarioOutline(scenarioName, tags, substeps, examples)
    case (None ~ scenarioName ~ substeps ~ examples) => ScenarioOutline(scenarioName, Nil, substeps, examples)

  }

  private def scenarioOutlineDef: Parser[String] = "Scenario Outline:" ~> opt(whiteSpace) ~> """[^\n\r]+""".r

    private def exampleSection: Parser[List[Map[String, String]]] = ("Examples:" ~ opt(whiteSpace) ~ rep1(eol)) ~> (lineOfCells <~ rep1(eol)) ~ repsep(lineOfCells, eol) ^^ {

    case (headings ~ examples) => for(example <- examples; examplesWithHeading = headings.zip(example)) yield Map(examplesWithHeading:_*)
  }

  private def lineOfCells: Parser[List[String]] = rep(cellSeparator ~> """[^|\n\r ]*(?=([ ]*\|))""".r) <~ cellSeparator

  private def cellSeparator: Parser[Any] = opt(whiteSpace) ~ "|" ~ opt(whiteSpace)
}