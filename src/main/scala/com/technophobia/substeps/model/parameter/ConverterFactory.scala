package com.technophobia.substeps.model.parameter

object ConverterFactory {

  private val CORE_CONVERTERS = Set(BooleanConverter, DoubleConverter, IntegerConverter, LongConverter, StringConverter)
  private val CONVERTERS_BY_TYPE: Map[Class[_], Converter[_]] = CORE_CONVERTERS.map(t => (t.converts, t)).toMap

  private val PRIMITIVE_CLASS_TO_WRAPPER_CLASS : Map[Class[_], Class[_]] = Map(
    (java.lang.Boolean.TYPE -> classOf[Boolean]),
    (java.lang.Character.TYPE -> classOf[Character]),
    (java.lang.Integer.TYPE -> classOf[Integer]),
    (java.lang.Double.TYPE -> classOf[Double]),
    (java.lang.Long.TYPE -> classOf[Long]),
    (java.lang.Float.TYPE -> classOf[Float]),
    (java.lang.Byte.TYPE -> classOf[Byte]))

  def getConverter(desiredType: Class[_]) = {

    CONVERTERS_BY_TYPE.get(PRIMITIVE_CLASS_TO_WRAPPER_CLASS.getOrElse(desiredType, desiredType)) //TODO This wont work for primitive types
  }
}
