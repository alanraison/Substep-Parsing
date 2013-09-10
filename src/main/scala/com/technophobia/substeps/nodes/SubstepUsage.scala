package com.technophobia.substeps.nodes

import com.technophobia.substeps.RunResult.{Failed, NotRun}
import com.technophobia.substeps.RunResult
import com.technophobia.substeps.node.factory.SubstepNodeFactory
import java.util.regex.MatchResult

trait SubstepUsage {

  def usageString: String

  def run() : RunResult

  def applyParameters(parameters: Map[String, String]) = {

    var usageStringWithParamsApplied = usageString

    for((key, value) <- parameters) {

      usageStringWithParamsApplied = usageStringWithParamsApplied.replaceAll("<" + key + ">", value)
    }

    UnresolvedSubstepUsage(usageStringWithParamsApplied)
  }

  def resolve(): SubstepUsage = this
}

case class UnresolvedSubstepUsage(usageString: String) extends SubstepUsage {

  def run() = Failed(s"Unresolved SubStep: ${usageString}")

  override def resolve(): SubstepUsage = {

    val resolved = SubstepNodeFactory.find(usageString) match {

      case List((substep:FileSubstep, matchResult))           => FileSubstepUsage.createFromMatchingSubstep(usageString, substep, matchResult)
      case List((substep:ImplementationSubstep, matchResult)) => ImplementationSubstepUsage.createFromMatchingSubstep(usageString, substep, matchResult)
      case _ => this

    }

    val withItsUsagesResolved = resolved match {

      case x:FileSubstepUsage => FileSubstepUsage(x.usageString, x.substepUsages.map(_.resolve()))
      case _ => resolved
    }

    withItsUsagesResolved
  }

}

case class FileSubstepUsage(usageString: String, substepUsages: List[SubstepUsage]) extends SubstepUsage {

  def run() = Failed("TODO")
}
object FileSubstepUsage {

  def createFromMatchingSubstep(usageString: String, substep: FileSubstep, matchResult: MatchResult): SubstepUsage = {


    val matchedValues = for(groupIdx <- 1 to matchResult.groupCount(); matchedValue = matchResult.group(groupIdx)) yield matchedValue

    val keysToValues = Map(substep.properties.zip(matchedValues):_*)

    FileSubstepUsage(usageString, substep.substepUsages.map(_.applyParameters(keysToValues)))
  }
}

case class ImplementationSubstepUsage(usageString: String, invocation: (() => Unit)) extends SubstepUsage {

  def run() = Failed("TODO")
}
object ImplementationSubstepUsage {


  def createFromMatchingSubstep(usageString: String, substep: ImplementationSubstep, matchResult: MatchResult): SubstepUsage = {

    val matchedValues = for(groupIdx <- 1 to matchResult.groupCount(); matchedValue = matchResult.group(groupIdx)) yield matchedValue

    ImplementationSubstepUsage(usageString, (() => substep.invoke(matchedValues:_*))) //TODO conversion
  }

}