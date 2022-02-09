package wood.mike.book.twitter.Twitter4J

import twitter4j.*

def twitter = TwitterFactory.singleton

// Send a direct messsage to twitter user GroovyDSL
twitter.sendDirectMessage(
	"GroovyDSL",
    "Hi Fergal read Groovy for DSL and loved it")

// Retrieve our latest direct messages 
// same as visiting http://twitter.com/#inbox
messages = twitter.directMessages

messages.each { message ->
    println "Message from : $message.senderScreenName"
    println "     ${message.text}"
}


