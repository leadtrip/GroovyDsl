package wood.mike.book.twitter.Twitter4J

import twitter4j.*

def twitter = TwitterFactory.singleton

// Get a list of my followers
def friends = twitter.getFriendsList('groovydsl',-1)
friends.each { friend ->
	// Print each screen name
    println friend.screenName
}

// "Follow" the Twitter user GroovyDSL
twitter.createFriendship("graemerocher")
