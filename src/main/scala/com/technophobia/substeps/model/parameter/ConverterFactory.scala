package com.technophobia.substeps.model.parameter

object ConverterFactory {

  private val CORE_CONVERTERS = Set(BooleanConverter, DoubleConverter, IntegerConverter, LongConverter, StringConverter)
  private val CONVERTERS_BY_TYPE: Map[Class[_], Converter[_]] = CORE_CONVERTERS.map(t => (t.converts, t)).toMap

  def getConverter(desiredType: Class[_]) = {

    CONVERTERS_BY_TYPE.get(desiredType) //TODO This wont work for primitive types
  }
}
