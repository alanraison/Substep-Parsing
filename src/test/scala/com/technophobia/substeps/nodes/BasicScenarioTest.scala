package com.technophobia.substeps.nodes

import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.{Mockito, Mock}
import org.junit.{Assert, Test}
import com.technophobia.substeps.RunResult.Passed

@RunWith(classOf[MockitoJUnitRunner])
class BasicScenarioTest {

  @Mock
  var usageOne: SubstepUsage = _

  @Mock
  var usageTwo: SubstepUsage = _

  val tags = List("a", "b")

  @Test
  def testRun() {

    Mockito.when(usageOne.run()).thenReturn(Passed)
    Mockito.when(usageTwo.run()).thenReturn(Passed)

    val basicScenario = BasicScenario("Scenario name", tags, List(usageOne, usageTwo))
    val passed = basicScenario.run(tags)

    Assert.assertEquals(Passed, passed)

    Mockito.verify(usageOne).run()
    Mockito.verify(usageTwo).run()
  }

}
