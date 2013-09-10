package com.technophobia.substeps.model.parameter

import java.lang.Boolean

object BooleanConverter extends Converter[Boolean] {

  def converts = classOf[Boolean]
  def convert(value: String): Boolean = Boolean.valueOf(value)
}
