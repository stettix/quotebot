# Quotebot

A simple Twitter bot that posts a daily quote.

Uses [twitter4s](https://github.com/DanielaSfregola/twitter4s) to access
the Twitter API.

Disclaimer: This is a quick little thing that hasn't been very well tested.
Use at your own peril!

## Usage

Set your consumer and access token as environment variables:

```bash
export TWITTER_CONSUMER_TOKEN_KEY='my-consumer-key'
export TWITTER_CONSUMER_TOKEN_SECRET='my-consumer-secret'
export TWITTER_ACCESS_TOKEN_KEY='my-access-key'
export TWITTER_ACCESS_TOKEN_SECRET='my-access-secret'
```

Then run the bot using the command line (probably by a cron job or similar):

```bash
QuoteBot <Twitter user ID> <quotes file URL> <start date (YYYY-MM-DD)>
```

This reads the list of quotes and will pick a quote for the day using the current date's offset from
the start date. It will wrap around the list to repeat quotes as needed.

Then the picked quote is tweeted. The action is idempotent so it won't post the same quote twice if run
multiple times on the same day.

## Runnng with AWS Lambda

To run this from an AWS Lambda, there's a Lambda event handler class included, `QuoteBotLambda`, that
implements the required interface. This expects the event input as a JSON array that contains the arguments
you'd use when running it from the command line.
