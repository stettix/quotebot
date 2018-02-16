package net.janvsmachine.quotebot

import java.time.LocalDate

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FlatSpec


class QuotesSpec extends FlatSpec with TypeCheckedTripleEquals {

  val lines = Seq(
    "Always rise_to an early meal,_but eat your fill before a feast.",
    "Go you must",
    "Be your friend's__true friend"
  )

  val quotes = Seq(
    Quote("Always rise\nto an early meal,\nbut eat your fill before a feast."),
    Quote("Go you must"),
    Quote("Be your friend's\n\ntrue friend")
  )

  "Reading a list of quotes" should "reformat line breaks" in {
    val parsed = Quotes.parse(lines)
    assert(parsed == quotes)
  }

  val startDate = LocalDate.now()

  "Getting the quote for a date" should "get the first quote if the local date is the same as the start date" in {
    assert(Quotes.forDate(quotes)(startDate = startDate, today = startDate) == quotes.head)
  }

  it should "get the last quote if the local date is N days after the start date for a list of N quotes" in {
    assert(Quotes.forDate(quotes)(startDate = startDate, today = startDate.plusDays(quotes.length - 1)) == quotes.last)
  }

  it should "wrap around quotes after the end of the list" in {
    val quotesForDate = Quotes.forDate(quotes) _
    assert(quotesForDate(startDate, startDate.plusDays(quotes.length)) == quotes.head)
    assert(quotesForDate(startDate, startDate.plusDays(quotes.length * 2)) == quotes.head)
    assert(quotesForDate(startDate, startDate.plusDays(quotes.length * 3 - 1)) == quotes.last)
    assert(quotesForDate(startDate, startDate.plusDays(quotes.length * 42)) == quotes.head)
  }

}
