package wood.mike.twitter

import twitter4j.Query
import twitter4j.TwitterFactory

import static wood.mike.Props.getTWITTER_USER


def twitter = TwitterFactory.singleton

// twitter.updateStatus("Updating my status via the Twitter4J APIs")

//println()

println twitter.lookupUsers(TWITTER_USER)[0]

def query = new Query('groovy dsl').lang('en')
def res = twitter.search(query).tweets
println "Got ${res.size()} results about ${query.query}"
res.each { tweet->
    println "${tweet.user.screenName} : ${tweet.text}"
}

def grres = twitter.search(new Query('from:graemerocher')).tweets
println "Got ${grres.size()} results about graeme rocher"
grres.each {tweet->
    println "${tweet.text}"
}


def friends = twitter.getFriendsList(TWITTER_USER, -1)
friends.each {friend ->
    println friend.screenName
}

//twitter.createFriendship('GroovyDSL')

