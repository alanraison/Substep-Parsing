package com.technophobia.subteps.nodes

case class SubstepUsage(usageString: String) {

  var substep: Option[Substep] = None

  def applyParameters(parameters: Map[String, String]) = {

    var usageStringWithParamsApplied = usageString

    for((key, value) <- parameters) {

      usageStringWithParamsApplied = usageStringWithParamsApplied.replaceAll("<" + key + ">", value)

    }

    SubstepUsage(usageStringWithParamsApplied)
  }
}