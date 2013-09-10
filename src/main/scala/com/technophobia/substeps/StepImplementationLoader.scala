package com.technophobia.substeps

import com.technophobia.substeps.model.SubSteps.{StepImplementations, Step}
import org.reflections.{ReflectionUtils, Reflections}
import com.technophobia.substeps.node.factory.SubstepNodeFactory
import scala.collection.JavaConversions._

object StepImplementationLoader {

  def loadStepImplementations(basePackages : List[String]) = {

    for(basePackage <- basePackages; reflections = new Reflections(basePackage);
        stepImplClass <- reflections.getTypesAnnotatedWith(classOf[StepImplementations]);
        clazz = StepImplementationClassCache.define(stepImplClass);
        method <- ReflectionUtils.getAllMethods(stepImplClass, ReflectionUtils.withAnnotation(classOf[Step]));
        stepAnnotation = method.getAnnotation(classOf[Step]))
            yield SubstepNodeFactory.defineFromSource(stepAnnotation.value(), stepImplClass, method)
  }
}
