package com.technophobia.subteps.nodes

import java.lang.reflect.Method

case class Substep(name: String) {

  var definition: Option[SubstepDefinition] = None;
}

abstract class SubstepDefinition

case class SubstepFileDefinition(substeps: List[Substep]) extends SubstepDefinition

case class SubstepImplementationDefinition(execution: ((Any*) => Any)) extends SubstepDefinition