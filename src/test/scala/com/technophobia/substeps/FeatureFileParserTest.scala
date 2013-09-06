package com.technophobia.subteps

import java.io.{InputStreamReader, FileReader}
import org.junit.{Test, Assert}
import com.technophobia.subteps.nodes.{ScenarioOutline, Feature}

class FeatureFileParserTest extends FeatureFileParser {

  private val SIMPLE_FEATURE_FILE = "/simple.feature"
  private val SCENARIO_OUTLINE_FEATURE_FILE = "/scenario-outline.feature"


  @Test
  def testSimpleFeature() {

    val feature = getSuccessfulParse(SIMPLE_FEATURE_FILE)

    Assert.assertEquals("A simple feature name", feature.name)
    Assert.assertEquals(List("featureTag-1", "featureTag-2"), feature.tags)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

    Assert.assertEquals("A simple scenario name", scenario.name)
    Assert.assertEquals(List("scenarioTag-1", "scenarioTag-2"), scenario.tags)
    Assert.assertEquals(2, scenario.steps.size)

    val substep1 = scenario.steps(0)
    val substep2 = scenario.steps(1)

    Assert.assertEquals("Given I think", substep1.definition)
    Assert.assertEquals("Then I am", substep2.definition)

  }

  @Test
  def testFeatureWithScenarioOutline() {

    val feature = getSuccessfulParse(SCENARIO_OUTLINE_FEATURE_FILE)

    Assert.assertEquals("A feature with a scenario outline", feature.name)
    Assert.assertTrue(feature.tags.isEmpty)
    Assert.assertEquals(1, feature.scenarios.size)

    val scenario = feature.scenarios.head

    def assertScenarioOutline(scenarioOutline: ScenarioOutline) {

      Assert.assertEquals("A scenario that's an outline", scenarioOutline.name)
      Assert.assertEquals(List("scenarioOutlineTag"), scenarioOutline.tags)

      Assert.assertEquals(2, scenarioOutline.steps.size)

      val substep1 = scenarioOutline.steps(0)
      val substep2 = scenarioOutline.steps(1)

      Assert.assertEquals("Given I think for <SECONDS>", substep1.definition)
      Assert.assertEquals("Then I am <TIREDNESS_LEVEL>", substep2.definition)

      Assert.assertEquals(2, scenarioOutline.examples.size)

      val example1 = scenarioOutline.examples(0)
      val example2 = scenarioOutline.examples(1)

      Assert.assertEquals(Map(("SECONDS" -> "5"), ("TIREDNESS_LEVEL" -> "OK")), example1)
      Assert.assertEquals(Map(("SECONDS" -> "120"), ("TIREDNESS_LEVEL" -> "EXHAUSTED")), example2)
    }

    scenario match {

      case scenarioOutline: ScenarioOutline => assertScenarioOutline(scenarioOutline)
      case x => throw new AssertionError("Scenario was of wrong type" + x.getClass.toString);

    }




  }

  private def getSuccessfulParse(fileName: String) : Feature = {

    parse(fileName) match {

      case Success(feature, _) => feature
      case x => throw new AssertionError(x.toString)

    }

  }

  private def parse(fileName: String) = {

    val reader = new InputStreamReader(classOf[FeatureFileParserTest].getResourceAsStream(fileName))

    try {

      new FeatureFileParser().parse(reader)
    }
    finally {

      reader.close()
    }

  }


}