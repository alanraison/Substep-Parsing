package com.technophobia.substeps.nodes

import org.mockito.runners.MockitoJUnitRunner
import org.junit.runner.RunWith
import org.junit.{Assert, Test}
import org.mockito.{Mockito, Mock}
import com.technophobia.substeps.RunResult.{Failed, Passed}

@RunWith(classOf[MockitoJUnitRunner])
class ScenarioOutlineTest {

  @Mock
  var basicScenarioOne: BasicScenario = _

  @Mock
  var basicScenarioTwo: BasicScenario = _

  val tags = List("a", "b")

  @Test
  def testRun() {

    Mockito.when(basicScenarioOne.run(tags)).thenReturn(Passed)
    Mockito.when(basicScenarioTwo.run(tags)).thenReturn(Passed)

    val scenarioOutline = ScenarioOutline("A scenario outline", List(), List(basicScenarioOne, basicScenarioTwo))

    val passed = scenarioOutline.run(tags)

    Assert.assertEquals(Passed, passed)

    Mockito.verify(basicScenarioOne).run(tags)
    Mockito.verify(basicScenarioTwo).run(tags)
  }

  @Test
  def testWhenOneFailsTwoIsStillRun() {

    val failure = Failed("failure")

    Mockito.when(basicScenarioOne.run(tags)).thenReturn(failure)
    Mockito.when(basicScenarioTwo.run(tags)).thenReturn(Passed)

    val scenarioOutline = ScenarioOutline("A scenario outline", List(), List(basicScenarioOne, basicScenarioTwo))
    val passed = scenarioOutline.run(tags)

    Assert.assertEquals(failure, passed)
    Mockito.verify(basicScenarioOne).run(tags)
    Mockito.verify(basicScenarioTwo).run(tags)
  }

  @Test
  def testWhenTwoFailsOneIsStillRun() {

    val failure = Failed("failure")

    Mockito.when(basicScenarioOne.run(tags)).thenReturn(Passed)
    Mockito.when(basicScenarioTwo.run(tags)).thenReturn(failure)

    val scenarioOutline = ScenarioOutline("A scenario outline", List(), List(basicScenarioOne, basicScenarioTwo))
    val passed = scenarioOutline.run(tags)

    Assert.assertEquals(failure, passed)
    Mockito.verify(basicScenarioOne).run(tags)
    Mockito.verify(basicScenarioTwo).run(tags)
  }
  
  
}
