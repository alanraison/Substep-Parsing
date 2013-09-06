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

      Assert.assertEquals("When an event occurs", substepDefinition.substeps(0).name)
      Assert.assertEquals("Then bad things happen", substepDefinition.substeps(1).name)
      Assert.assertEquals("And people get upset", substepDefinition.substeps(2).name)
    }

    firstSubstep.definition match {

      case Some(definition: SubstepFileDefinition) => assertSubstepFileDefinition(definition)
      case x => throw new AssertionError(x.toString)

    }

  }

  @Test
  def testSubstepsReferToOneAnother() {

    val substeps = getSuccessfulParse(SUBSTEPS_FILE)

    val substepMap = substeps.map{ substep => (substep.name, substep)}.toMap

    val givenSomething = substepMap("Given something")

    def assertSubstepDefinitionIsGivenSomething(name: String) {

      val usage = substepMap(name).definition match {

        case Some(x:SubstepFileDefinition) => x.substeps.head
        case x => throw new AssertionError("Was expecting a substep file defintion but found " + x)

      }

      Assert.assertSame(givenSomething, usage)
    }

    List("Given a substep", "Given another substep", "Given yet another substep").foreach(assertSubstepDefinitionIsGivenSomething(_))

  }

}
