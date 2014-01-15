package com.technophobia.substeps.nodes

import com.technophobia.substeps.{RunResult, TagMatcher}
import com.technophobia.substeps.RunResult.NotRun


sealed abstract class Scenario {

  def name: String
  def run(runWith: List[String]): RunResult

}

case class BasicScenario(name: String, tags: List[String], steps: List[SubstepUsage]) extends Scenario {

  def run(runWith: List[String]) = {

    TagMatcher.matches(runWith, tags) match {

      case true => steps.foldLeft[RunResult](NotRun)((previous, usage) => previous.combine(usage.run()))
      case false => NotRun
    }
  }
}

case class ScenarioOutline(name: String, steps: List[SubstepUsage], scenarios: List[BasicScenario]) extends Scenario {

  def run(runWith: List[String]) = scenarios.foldLeft[RunResult](NotRun)((previous, scenario) =>  previous.combine(scenario.run(runWith)))
}

object ScenarioOutline {

  def apply(name: String, tags: List[String], steps: List[SubstepUsage], examples: List[Map[String, String]]) : ScenarioOutline = {

    val basicScenarios = for((example, idx) <- examples.zip(Stream.from(1)); stepsForExample = steps.map(_.applyParameters(example))) yield BasicScenario(s"$name-$idx", tags, stepsForExample)

    ScenarioOutline(name, steps, basicScenarios)
  }

}