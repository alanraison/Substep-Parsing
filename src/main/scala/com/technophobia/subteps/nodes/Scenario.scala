package com.technophobia.subteps.nodes

sealed case class Scenario(title: String, tags: List[String], steps: List[Substep])

class ScenarioOutline(title: String, tags: List[String], steps: List[Substep], examples: List[Map[String, String]]) extends Scenario(title, tags, steps)
