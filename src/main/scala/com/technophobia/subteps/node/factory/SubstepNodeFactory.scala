package com.technophobia.subteps.node.factory

import com.technophobia.subteps.nodes.{SubstepUsage, SubstepFileDefinition, Substep}
import scala.collection.mutable.Map

object SubstepNodeFactory {

  private val substepCache : Map[String, Substep] = Map()

  def defineFromFile(name: String, substepUsages: List[SubstepUsage]) = {

    val substep = find(name)

    if(substep.definition.isDefined) throw new RuntimeException(s"Attempt to define $name but this substep was already defined")

    substep.definition = Some(new SubstepFileDefinition(substepUsages))

    substepCache += (name -> substep)

    substep
  }

  def find(name: String) = {

    def addAndReturn = {

      val newStep = Substep(name)
      substepCache += (name -> newStep)
      newStep
    }

    substepCache.getOrElse(name, addAndReturn)
  }

  def clear() { substepCache.clear() }
}
