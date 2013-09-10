package com.technophobia.substeps.nodes

import com.technophobia.substeps.{RunResult, TagMatcher}
import com.technophobia.substeps.RunResult.NotRun

case class Feature(name: String, tags: List[String], scenarios: List[Scenario]) {


  def run(runWith: List[String]) : RunResult = {

    TagMatcher.matches(runWith, tags) match {

      case true  => scenarios.foldLeft[RunResult](NotRun)((previous, scenario) => previous.combine(scenario.run(tags)))
      case false => NotRun

    }
  }

}