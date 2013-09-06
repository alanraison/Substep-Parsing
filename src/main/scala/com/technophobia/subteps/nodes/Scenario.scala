package com.technophobia.subteps.nodes


sealed abstract class Scenario {

  def name: String
  def tags: List[String]
  def steps: List[Substep]

}

case class BasicScenario(name: String, tags: List[String], steps: List[Substep]) extends Scenario

case class ScenarioOutline(name: String, tags: List[String], steps: List[Substep], examples: List[Map[String, String]]) extends Scenario
