package com.technophobia.substeps.nodes

import org.junit.{Assert, Test}
import org.mockito.{Matchers, Mock, Mockito}
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import com.technophobia.substeps.RunResult.{Failed, Passed}


@RunWith(classOf[MockitoJUnitRunner])
class FeatureTest {

  @Mock
  var scenarioOne : Scenario = _

  @Mock
  var scenarioTwo : Scenario = _
  
  val tags = List("a", "b")

  @Test
  def testRun() {

    Mockito.when(scenarioOne.run(tags)).thenReturn(Passed)
    Mockito.when(scenarioTwo.run(tags)).thenReturn(Passed)

    val feature = Feature("A feature", tags, List(scenarioOne, scenarioTwo))
    val passed = feature.run(tags)

    Assert.assertEquals(Passed, passed)
    Mockito.verify(scenarioOne).run(tags)
    Mockito.verify(scenarioTwo).run(tags)
  }

  @Test
  def testWhenOneFailsTwoIsStillRun() {

    val failure = Failed("failure")

    Mockito.when(scenarioOne.run(tags)).thenReturn(failure)
    Mockito.when(scenarioTwo.run(tags)).thenReturn(Passed)

    val feature = Feature("A feature", tags, List(scenarioOne, scenarioTwo))
    val passed = feature.run(tags)

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run(tags)
    Mockito.verify(scenarioTwo).run(tags)
  }

  @Test
  def testWhenTwoFailsOneIsStillRun() {

    val failure = Failed("failure")

    Mockito.when(scenarioOne.run(tags)).thenReturn(Passed)
    Mockito.when(scenarioTwo.run(tags)).thenReturn(failure)

    val feature = Feature("A feature", tags, List(scenarioOne, scenarioTwo))
    val passed = feature.run(tags)

    Assert.assertEquals(failure, passed)
    Mockito.verify(scenarioOne).run(tags)
    Mockito.verify(scenarioTwo).run(tags)
  }


}
