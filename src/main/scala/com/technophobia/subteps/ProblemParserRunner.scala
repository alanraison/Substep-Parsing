package com.technophobia.subteps

import java.io.FileReader

object ProblemParserRunner extends ProblemParser {

  def main(args: Array[String]) {

    val reader = new FileReader(args(0))

    val result = parseAll(sections, reader)  match {

      case Success(feature, in) => feature
      case x => x.toString

    }

    println(result)
  }

}
