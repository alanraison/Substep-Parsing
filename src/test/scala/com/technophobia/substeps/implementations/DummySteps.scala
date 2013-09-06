package com.technophobia.substeps.implementations

import com.technophobia.substeps.model.SubSteps.Step

class DummySteps {

  @Step("Assert I am")
  def assertIAm() {

    print("I am")
    //NOP
  }

  @Step("Assert I'm not")
  def assertImNot() {

    print("No I'm not")
    throw new AssertionError("I'm not")
  }

}
