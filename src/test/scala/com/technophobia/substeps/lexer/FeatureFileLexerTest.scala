package com.technophobia.substeps.lexer

import org.junit.{Assert, Test}
import scala.io.Source

class FeatureFileLexerTest {

  private val SIMPLE_FEATURE_FILE = "/simple.feature"
  private val LEXER_EXAMPLE_FILE = "/lexerExample.file"

  val lexer = new FeatureFileLexer()

  var scanner : lexer.Scanner = _

  def createScanner(fileName: String) {

    scanner = lexer.createScanner(Source.fromInputStream(this.getClass.getResourceAsStream(fileName)).getLines().mkString("\n"), 0)
  }


  def advance() {
    scanner = scanner.drop(1).asInstanceOf[lexer.Scanner]
  }

  @Test
  def testSimpleFeature() {

    createScanner(SIMPLE_FEATURE_FILE)
    val firstToken = scanner.first

    Assert.assertEquals(classOf[lexer.Keyword], firstToken.getClass)
    Assert.assertEquals(5, scanner.rest.offset);
    Assert.assertEquals(0, scanner.offset)

    advance()

    val secondToken = scanner.first

    Assert.assertEquals(classOf[lexer.WhiteSpace], secondToken.getClass)
    Assert.assertEquals(6, scanner.rest.offset);
    Assert.assertEquals(5, scanner.offset)

    advance()

    val thirdToken = scanner.first

    Assert.assertEquals(classOf[lexer.Text], thirdToken.getClass)
    Assert.assertEquals(18, scanner.rest.offset)
    Assert.assertEquals(6, scanner.offset)

  }

  @Test
  def testParameter() {

    createScanner(LEXER_EXAMPLE_FILE)
    val thirdToken = scanner.rest.rest.rest.rest.first

    Assert.assertEquals(classOf[lexer.Parameter], thirdToken.getClass)
  }

}
