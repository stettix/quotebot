package net.janvsmachine.quotebot

import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.danielasfregola.twitter4s.entities.Tweet

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.Source

import scala.concurrent.ExecutionContext.Implicits.global

object QuoteBot {

  def main(args: Array[String]): Unit = {

    if (args.length < 3) {
      println(s"Usage: ${getClass.getSimpleName.takeWhile(_ != '$')} <Twitter user ID> <quotes file URL> <start date (YYYY-MM-DD)>")
      System.exit(1)
    }

    val username = args(0)
    val quotesFileUrl = new URI(args(1))
    val startDate = parseStartDate(args(2))
    val today = LocalDate.now()
    println(s"Reading quotes from $quotesFileUrl for date $today, start date is $startDate, posting as user $username")

    val lines = Source.fromURI(quotesFileUrl).getLines.toList
    val quotes = Quotes.parse(lines)
    val todaysQuote = Quotes.forDate(quotes)(startDate, today)

    val tweetOps = new TwitterOpsClient(username)

    val postedAlready: Future[Boolean] = tweetOps.mostRecent().map(_.exists(tweet => tweet.text == todaysQuote.text))

    val posted: Future[Option[Tweet]] = postedAlready.flatMap { posted =>
      if (posted) {
        println("Posted quote already today, not tweeting")
        Future.successful(None)
      }
      else {
        tweetOps.tweet(todaysQuote.text).map(Some(_))
      }
    }

    val res = Await.result(posted, 10.seconds)
    println(s"Completed: $res")
  }

  private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  private def parseStartDate(str: String): LocalDate = LocalDate.parse(str, format)

}
