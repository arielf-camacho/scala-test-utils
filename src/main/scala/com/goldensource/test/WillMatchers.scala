package com.goldensource.test

import org.scalatest.MustMatchers
import org.scalatest.exceptions.TestFailedException
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.words._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions

/**
  * Matchers that will provide some improvement to the ScalaTest DSL specifically when matching assertions involving
  * [[Future]] and their "eventual" results.
  */
trait WillMatchers {

  implicit def convertToWillWord[T](f: Future[T])(implicit timeout: Duration): WillWord[T] = {
    WillWord[T](Await.result(f, timeout))
  }

  case class WillWord[T](left: T, mustBeTrue: Boolean = true) {
    def will(matcher: Matcher[T]): MatchResult = {
      val result = matcher.apply(left)

      try {
        if (!result.matches)
          throw new TestFailedException(Some(result.failureMessage), None, 1)

        result
      }
      catch {
        case e: Throwable => throw new TestFailedException(Some(result.failureMessage), Some(e), 1)
      }
    }

    def will(notWord: NotWord): ResultOfNotWordForAny[T] = {
      new ResultOfNotWordForAny[T](left, false)
    }

    def will(beWord: BeWord): MustMatchers.ResultOfBeWordForAny[T] = {
      new MustMatchers.ResultOfBeWordForAny[T](left, mustBeTrue)
    }

    def will(haveWord: HaveWord): MustMatchers.ResultOfHaveWordForExtent[T] = {
      new MustMatchers.ResultOfHaveWordForExtent[T](left, mustBeTrue)
    }

    def will(containWord: ContainWord): ResultOfContainWord[T] = {
      new ResultOfContainWord[T](left, mustBeTrue)
    }

    def futureValue: T = left
  }

}
