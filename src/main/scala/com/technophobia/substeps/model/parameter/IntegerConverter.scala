package com.technophobia.substeps.model.parameter

import java.lang.Integer

object IntegerConverter extends Converter[Integer] {

  val converts = classOf[Integer]
  def convert(value: String) = Integer.valueOf(value)
}
