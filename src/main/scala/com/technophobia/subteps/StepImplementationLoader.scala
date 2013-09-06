package com.technophobia.subteps

import com.technophobia.substeps.model.SubSteps.{StepImplementations, Step}
import org.reflections.{ReflectionUtils, Reflections}
import com.technophobia.subteps.node.factory.SubstepNodeFactory
import java.lang.reflect.Method

object StepImplementationLoader {

  def loadStepImplementations(basePackages : List[String]) {

    for(basePackage <- basePackages; reflections = new Reflections(basePackage); stepImplClass <- reflections.getMethodsAnnotatedWith(classOf[StepImplementations])) {

      StepImplementationClassCache.define(stepImplClass)

      for(method <- ReflectionUtils.getAllMethods(stepImplClass, ReflectionUtils.withAnnotation(classOf[Step]))) {

        SubstepNodeFactory.defineFromSource(method.invoke(stepImplClass, _))
      }

    }

  }
}
