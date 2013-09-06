package com.technophobia.subteps.node.factory

import com.technophobia.subteps.nodes.{SubstepImplementationDefinition, SubstepUsage, SubstepFileDefinition, Substep}
import scala.collection.mutable.Map
import java.lang.reflect.Method

object SubstepNodeFactory {

  private val substepCache : Map[String, Substep] = Map()

  def defineFromFile(name: String, substeps: List[Substep]) = {

    val substep = getUndefinedSubstep(name)

    substep.definition = Some(new SubstepFileDefinition(substeps))

    substepCache += (name -> substep)

    substep
  }


  def defineFromSource(name: String, execution : ((Any*) => Any)) = {

    val substep = getUndefinedSubstep(name)

    substep.definition = Some(new SubstepImplementationDefinition(execution))

    substep
  }

  def getUndefinedSubstep(name: String): Substep = {

    val substep = find(name)
    if (substep.definition.isDefined) throw new RuntimeException(s"Attempt to define $name but this substep was already defined")
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
