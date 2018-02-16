package net.janvsmachine.quotebot

import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.Tweet

import scala.concurrent.Future

trait TweetOps {

  def mostRecent(): Future[Seq[Tweet]]

  def tweet(text: String): Future[Tweet]

}

class TwitterOpsClient(username: String) extends TweetOps {

  import scala.concurrent.ExecutionContext.Implicits.global

  val client = TwitterRestClient()

  override def mostRecent(): Future[Seq[Tweet]] =
    client.userTimelineForUser(username, count = 10).map(_.data)

  override def tweet(text: String): Future[Tweet] =
    client.createTweet(text)

}
