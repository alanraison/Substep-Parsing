package com.technophobia.substeps.lexer

import org.junit.{Assert, Test}
import scala.io.Source

class FeatureFileLexerTest {

  private val SIMPLE_FEATURE_FILE = "/simple.feature"

  @Test
  def testSimpleFeature() {

    val lexer = new FeatureFileLexer()
    var scanner = lexer.createScanner(Source.fromInputStream(this.getClass.getResourceAsStream(SIMPLE_FEATURE_FILE)).getLines().mkString("\n"), 0)

    val firstToken = scanner.first

    Assert.assertEquals(classOf[lexer.Keyword], firstToken.getClass)
    Assert.assertEquals(5, scanner.rest.offset);
    Assert.assertEquals(0, scanner.offset)

    scanner = scanner.drop(1).asInstanceOf[lexer.Scanner]

    val secondToken = scanner.first

    Assert.assertEquals(classOf[lexer.WhiteSpace], secondToken.getClass)
    Assert.assertEquals(6, scanner.rest.offset);
    Assert.assertEquals(5, scanner.offset)


  }

}
