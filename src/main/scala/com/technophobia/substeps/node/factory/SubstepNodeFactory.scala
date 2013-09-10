package com.technophobia.substeps.node.factory

import com.technophobia.substeps.nodes.{ImplementationSubstep, FileSubstep, SubstepUsage, Substep}
import scala.collection.mutable.Map
import scala.util.matching.Regex
import java.util.regex.MatchResult
import java.lang.reflect.Method

object SubstepNodeFactory {

  private val nameToSubstep : Map[String, Substep] = Map()

  private val regexToSubstep: Map[Regex, Substep] = Map()

  def defineFromFile(name: String, substeps: List[SubstepUsage]) = {

    val substep = new FileSubstep(name, substeps)

    addSubstep(name, substep)

    substep
  }

  def defineFromSource(name: String, stepImplClass: AnyRef, stepImplMethod: Method) = {

    val substep = new ImplementationSubstep(name, stepImplClass, stepImplMethod)

    addSubstep(name, substep)

    substep
  }

  def clear() {
    nameToSubstep.clear()
    regexToSubstep.clear()
  }

  def find(usageString: String): List[(Substep, MatchResult)] = {

    (for(regex <- regexToSubstep.keys; matcher = regex.pattern.matcher(usageString); if matcher.matches()) yield (regexToSubstep(regex), matcher.toMatchResult)).toList
  }

  def addSubstep(name: String, substep: Substep) {

    if (nameToSubstep.isDefinedAt(name)) throw new RuntimeException(s"Attempt to define $name but this substep was already defined")
    nameToSubstep += (name -> substep)
    regexToSubstep += (substep.regex -> substep)
  }

}
