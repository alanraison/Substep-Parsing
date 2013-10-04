package com.technophobia.substeps.nodes

import scala.util.matching.Regex
import java.lang.reflect.Method
import com.technophobia.substeps.model.SubSteps.StepParameter
import com.technophobia.substeps.model.parameter.{Converter, ConverterFactory}

abstract class Substep {

  def name: String

  def regex: Regex
}

case class FileSubstep(name: String, substepUsages: List[SubstepUsage]) extends Substep {

  val regex = name.replaceAll("<[^>]*>", "(.*)").r
  val properties: List[String] = "(?<=<)[^>]*(?=>)".r.findAllIn(name).toList
}

case class ImplementationSubstep(name: String, stepImplClass: AnyRef, stepImplMethod: Method) extends Substep {

  val converters : List[Converter[_]] = {

    val paramTypes = stepImplMethod.getParameterTypes
    val paramAnnotations = stepImplMethod.getParameterAnnotations.map(_.find(_.isInstanceOf[StepParameter]).asInstanceOf[Option[StepParameter]])

    val found = for((paramType, paramAnnotation) <- paramTypes.zip(paramAnnotations)) yield {

      paramAnnotation match {

        case Some(annotation) => annotation.converter().newInstance().asInstanceOf[Converter[_]]
        case None => ConverterFactory.getConverter(paramType).getOrElse(throw new RuntimeException("Invalid converter"))
      }
    }

    found.toList
  }

  def invoke(args: AnyRef*) {

    stepImplMethod.invoke(stepImplClass, args:_*)
  }

  def regex = name.r
}