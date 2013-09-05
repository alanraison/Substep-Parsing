package com.technophobia.subteps

import com.technophobia.subteps.nodes.{Substep, Feature, Scenario}

class FeatureFileParser extends AbstractParser {

  def featureFile: Parser[Feature] = (tagDef <~ rep1(eol)) ~ (featureDef <~ rep1(eol)) ~ (rep(scenario) <~ rep(eol)) ^^ {

    case (tags ~ featureName ~ scenarios) => Feature(featureName, tags, scenarios)
  }

  def tagDef: Parser[List[String]] = "Tags:" ~> opt(space) ~> repsep(tag, space)

  def space: Parser[Any] = """( )+""".r //TOOD Is there a predefined parser?

  def tag: Parser[String]   = """[1-9A-Za-z-_~]+""".r

  def featureDef: Parser[String] = "Feature:" ~> opt(space) ~> """[^\r\n]+""".r

  def scenario: Parser[Scenario] = basicScenario // | scenarioOutline

  def basicScenario: Parser[Scenario] = (opt(tagDef <~ eol) ~ scenarioDef <~ eol) ~ repsep(substep, eol) <~ rep(eol) ^^ {

    case (Some(tags) ~ scenarioName ~ substeps) => Scenario(scenarioName, tags, substeps)
    case (None ~ scenarioName ~ substeps) => Scenario(scenarioName, Nil, substeps)
  }

  def scenarioDef: Parser[String] = "Scenario:" ~> opt(space) ~> """[^\n\r]+""".r

}