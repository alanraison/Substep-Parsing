package com.technophobia.subteps

import com.technophobia.subteps.nodes.{Substep, Feature, Scenario}

class FeatureFileParser extends AbstractParser {

  def featureFile: Parser[Feature] = tagDef ~ featureDef ~ repsep(scenario, eol) ^^ {case (tags ~ featureName ~ scenarios) => Feature(featureName, tags, scenarios)}

  def tagDef: Parser[List[String]] = "Tags:" ~> rep(tags) <~ opt(whiteSpace) <~ eol

  def tags: Parser[String] = opt(whiteSpace) ~> tag

  def tag: Parser[String]   = """[1-9A-Za-z-_~]+""".r

  def featureDef: Parser[String] = "Feature:" ~> opt(whiteSpace) ~> """(.)+""".r <~ eol

  def scenario: Parser[Scenario] = basicScenario // | scenarioOutline

  def basicScenario: Parser[Scenario] = opt(tagDef) ~ scenarioDef ~ repsep(substep, eol) ^^ {

    case (Some(tags) ~ scenarioName ~ substeps) => Scenario(scenarioName, tags, substeps)
    case (None ~ scenarioName ~ substeps) => Scenario(scenarioName, Nil, substeps)
  }


  def scenarioDef: Parser[String] = "Scenario:" ~> opt(whiteSpace) ~> """.+""".r <~ eol

}