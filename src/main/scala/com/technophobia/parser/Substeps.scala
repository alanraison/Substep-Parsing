package com.technophobia.parser

import scala.util.parsing.combinator._

class Substeps extends JavaTokenParsers {
  
  override val whiteSpace                    = """[ \t]+""".r
  def substepsFile: Parser[List[ParentStep]] = rep(substepDefinition)
  def substepDefinition: Parser[ParentStep]  = definitionHeader~rep(step) ^^
                                                   {case defName~steps => ParentStep(defName, steps)}
  def definitionHeader: Parser[List[Any]]    = "Define"~>":"~>definitionName<~eol
  def definitionName: Parser[List[Any]]      = rep(quotedParameter|word)
  def step: Parser[Step]                     = rep(quotedParameter|word)<~eol ^^ ((x) => Step(x))
  def quotedParameter : Parser[Parameter]    = "\""~>parameter<~"\"" | "'"~>parameter<~"'" | parameter
  def parameter: Parser[Parameter]           = "<"~>rep(word)<~">" ^^ ((param:List[String]) => Parameter(param.mkString(" ")))
  def word: Parser[String]                   = """[1-9A-Za-z]+""".r
  def eol: Parser[Any]                       = """(\r?\n)+""".r
  
}

import java.io.FileReader

object ParseSubsteps extends Substeps {

  def main(args: Array[String]) {

    val reader = new FileReader(args(0))
    val result = parseAll(substepsFile, reader)  match {

      case Success(parentSteps, in) => parentSteps.mkString("\n")
      case x => x.toString 

    }

    println(result)
  }
}

case class ParentStep(name: String, paramNames: Seq[String], steps: Seq[Step])
case class Parameter(name: String)
case class Step(callLine: String, paramValues: Seq[String])

trait CallLineGenerator {

  def generate(namesAndParameters: List[Any]) = {

    val lineAsList = for(nameOrParameter <- namesAndParameters) yield {
      nameOrParameter match {
        case name:String         => name
        case parameter:Parameter => "<param>"
      }
    }

    lineAsList.mkString(" ")

  }
}

object ParentStep extends CallLineGenerator {

  def apply(namesAndParameters: List[Any], steps: Seq[Step]) : ParentStep = {

    val params: List[String] = for(nameOrParam <- namesAndParameters; if nameOrParam.isInstanceOf[Parameter]) yield nameOrParam.toString
      //namesAndParameters.filter(_.isInstanceOf[Parameter]).map(_.toString)

    ParentStep(generate(namesAndParameters), params, steps)
  }
}

object Step extends CallLineGenerator {

  def apply(namesAndParameters : List[Any]): Step =
    Step(generate(namesAndParameters), namesAndParameters.filter(_.isInstanceOf[Parameter]).map(_.toString))

}
