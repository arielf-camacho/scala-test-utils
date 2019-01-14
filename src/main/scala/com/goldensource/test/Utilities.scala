package com.goldensource.test

import scala.util.Random

/**
  * Utilities to fasten the testing process by quickly creating random values or lists of them.
  */
trait Utilities {
  private val random = new Random


  /**
    * Gets a randomly selected item from the given list.
    *
    * @param items : The [[List]] to select the item from.
    * @tparam T : The type of the items in the list.
    * @return A certain randomly selected item from the list.
    */
  def getOneOf[T](items: List[T]): T = {
    assert(items != null)
    assert(items.nonEmpty)

    val index = makeRandomInt(items.length)
    items(index)
  }

  /**
    * Creates a sized list of elements of type [[T]].
    *
    * @param factory : A function that creates an instance of [[T]].
    * @tparam T : The type of the elements to create.
    * @return A List with a specified size containing all the build elements using the provided factory function.
    */
  def makeListOf[T](count: Int, factory: () => T): List[T] = {
    val range = 1 to count
    val createListOfElement: (List[T], Int) => List[T] = (list, _) => factory() :: list
    val seed = List[T]()

    range.foldLeft(seed)(createListOfElement)
  }

  /**
    * Gets a random Boolean value.
    *
    * @return A random [[Boolean]] value.
    */
  def makeRandomBoolean: Boolean = makeRandomInt(1000) % 2 == 0

  /**
    * Gives a pseudo-random, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive).
    *
    * @param maximum : The number that be always be greater that any returned number.
    */
  def makeRandomInt(maximum: Int = 10): Int = random.nextInt(maximum)

  /**
    * Gets a random [[Long]] numeric value.
    *
    * @return A random [[Long]].
    */
  def makeRandomLong(): Long = makeRandomInt(1, 1000).toLong

  /**
    * Gives a pseudo-random, uniformly distributed int value between a minimum (inclusive) and the specified maximum
    * value (exclusive).
    *
    * @param minimum : The number that the generated random one will always be equal or greater.
    * @param maximum : The number that be always be greater that any returned number.
    */
  def makeRandomInt(minimum: Int, maximum: Int): Int = minimum + random.nextInt(maximum - minimum)

  /**
    * Creates a random sized list of elements of type [[T]].
    *
    * @param factory : A function that creates an instance of [[T]].
    * @tparam T : The type of the elements to create.
    * @return A List with random size containing all the build elements using the provided factory function.
    */
  def makeRandomListOf[T](factory: () => T): List[T] = makeListOf(1 + makeRandomInt(), factory)

  /**
    * Gets a randomly formed string of the defined characters.
    *
    * @param count : Length of the random String (10 by default).
    * @return A [[String]] randomly formed of "count" characters.
    */
  def makeRandomString(count: Int = 10): String = (1 to count).foldLeft("")((string, index) => {
    string + makeRandomInt(65, 90).asInstanceOf[Char]
  })
}