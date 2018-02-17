package net.janvsmachine.quotebot

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.util.{List => JList}

import scala.collection.JavaConverters._

class QuoteBotLambda extends RequestHandler[JList[String], Unit] {

  override def handleRequest(input: JList[String], context: Context): Unit = {

    QuoteBot.main(input.asScala.toArray)

  }

}
