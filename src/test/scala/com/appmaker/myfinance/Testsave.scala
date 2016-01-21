package com.appmaker.myfinance

import org.scalatest.FlatSpec

/**
  * Created by choudhurynirjhar on 1/20/2016.
  */
class Testsave extends FlatSpec{

  "this method" should "return 1" in   {
    val myQuote = new Quote()
    assert(myQuote.getQuote() == 1)
  }
}
