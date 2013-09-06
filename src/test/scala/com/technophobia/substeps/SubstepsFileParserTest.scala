package com.technophobia.substeps

import org.junit.{Before, Assert, Test}
import com.technophobia.subteps.nodes.{SubstepFileDefinition, Substep}
import com.technophobia.subteps.SubstepsFileParser
import com.technophobia.subteps.node.factory.SubstepNodeFactory

class SubstepsFileParserTest extends SubstepsFileParser with ParsingTestHelpers[List[Substep]]{

  private val SUBSTEPS_FILE = "simple.substeps"

  @Before
  def removeAllSubstepsFromCache() {

    SubstepNodeFactory.clear()
  }

  @Test
  def testSubstepFile() {

    val substeps = getSuccessfulParse(SUBSTEPS_FILE)

    Assert.assertEquals(8, substeps.size)

    val firstSubstep = substeps.head

    Assert.assertEquals("Given series of substeps is executed", firstSubstep.name)

    def assertSubstepFileDefinition(substepDefinition: SubstepFileDefinition) {

      Assert.assertEquals("When an event occurs", substepDefinition.substepUsages(0).usageString)
      Assert.assertEquals("Then bad things happen", substepDefinition.substepUsages(1).usageString)
      Assert.assertEquals("And people get upset", substepDefinition.substepUsages(2).usageString)
    }

    firstSubstep.definition match {

      case Some(definition: SubstepFileDefinition) => assertSubstepFileDefinition(definition)
      case x => throw new AssertionError(x.toString)

    }

  }

}
