package com.technophobia.substeps.nodes

import scala.util.matching.Regex
import java.lang.reflect.Method

abstract class Substep {

  def name: String

  def regex: Regex
}

case class FileSubstep(name: String, substepUsages: List[SubstepUsage]) extends Substep {

  val regex = name.replaceAll("<[^>]*>", "(.*)").r
  val properties: List[String] = "(?<=<)[^>]*(?=>)".r.findAllIn(name).toList
}

case class ImplementationSubstep(name: String, stepImplClass: AnyRef, stepImplMethod: Method) extends Substep {

  def invoke(args: AnyRef*) {

    stepImplMethod.invoke(stepImplClass, args:_*)
  }

  def regex = name.r
}