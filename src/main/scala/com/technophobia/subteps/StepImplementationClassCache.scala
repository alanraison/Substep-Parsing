package com.technophobia.subteps

object StepImplementationClassCache {


  def define(clazz: Class[_]) = {



    clazz.newInstance()
  }

}
