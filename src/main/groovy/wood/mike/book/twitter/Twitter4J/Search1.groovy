package wood.mike.book.twitter.Twitter4J

import twitter4j.*

def twitter = TwitterFactory.singleton
// Create a query for tweets containing the terms "Groovy" and "DSL"
def query = new Query("Groovy DSL")
// Search and iterate the results
twitter.search(query).tweets.each { tweet ->
    println  "${tweet.user.screenName} : ${tweet.text}"
}
