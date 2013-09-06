package com.technophobia.subteps.nodes


sealed abstract class Scenario {

  def name: String

}

case class BasicScenario(name: String, tags: List[String], steps: List[SubstepUsage]) extends Scenario

case class ScenarioOutline(name: String, scenarios: List[BasicScenario]) extends Scenario

object ScenarioOutline {

  def apply(name: String, tags: List[String], steps: List[SubstepUsage], examples: List[Map[String, String]]) : ScenarioOutline = {

    val basicScenarios = for((example, idx) <- examples.zip(Stream.from(1)); stepsForExample = steps.map(_.applyParameters(example))) yield BasicScenario(s"$name-$idx", tags, stepsForExample)

    ScenarioOutline(name, basicScenarios)
  }

}