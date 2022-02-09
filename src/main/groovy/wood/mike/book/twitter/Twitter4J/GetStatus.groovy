package wood.mike.book.twitter.Twitter4J

import twitter4j.*

// Get a twitter connection
def twitter = TwitterFactory.singleton

// Update twitter status
twitter.updateStatus("Updating my status via the Twitter4J APIS")

println twitter.showUser('fdearle').status.text
