package net.janvsmachine.quotebot

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object Quotes {

  def forDate(quotes: Seq[Quote])(startDate: LocalDate, today: LocalDate): Quote = {
    require(quotes.nonEmpty)

    val days = ChronoUnit.DAYS.between(startDate, today)
    require(days >= 0)

    val offset = days.toInt % quotes.length
    quotes(offset)
  }


  def parse(lines: Seq[String]): Seq[Quote] =
    lines.map(_.replaceAll("_", "\n")).map(Quote.apply)

}
