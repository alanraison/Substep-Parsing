package com.technophobia.substeps

object StepImplementationClassCache {


  def define(clazz: Class[_]) = {



    clazz.newInstance()
  }

}
