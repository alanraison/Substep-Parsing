package com.technophobia.substeps.model.parameter

import java.lang.Double

object DoubleConverter extends Converter[Double] {

  val converts = classOf[Double]
  def convert(value: String) = {

    if (value == null) {

      throw new NumberFormatException("null")
    }

    Double.valueOf(value)
  }
}
