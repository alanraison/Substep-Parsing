package com.technophobia.substeps

import java.io.FileReader

object FeatureFileRunner extends FeatureFileParser {

  def main(args: Array[String]) {

    val reader = new FileReader(args(0))

    val result = parse(reader)  match {

      case Success(feature, in) => feature
      case x => x.toString

    }

    println(result)
  }
}