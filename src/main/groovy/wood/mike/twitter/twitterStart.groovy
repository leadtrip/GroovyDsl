package wood.mike.twitter


import twitter4j.Query
import twitter4j.TwitterFactory

import static wood.mike.Props.TWITTER_USER

followersList = { user ->
    TwitterFactory.singleton.getFollowersList( user, -1 )
}

friendsList = { user ->
    TwitterFactory.singleton.getFriendsList(user, -1)
}

cachedFriendsList = friendsList.memoize()

def eachFriend(Closure c) {
    def friends = friendsList( TWITTER_USER )
    friends.each{
        c.call(it.screenName)
    }
}

def eachFollower(Closure c) {
    def followers = followersList( TWITTER_USER )
    followers.each{ follower->
        c.call(follower.screenName)
    }
}

static void follow(user) {
    TwitterFactory.singleton.createFriendship(user)
}

void search(terms, Closure c) {
    def query = new Query(terms)
    TwitterFactory.singleton.search(query).tweets.each {
        c.call(it.user.screenName, it.text)
    }
}

println '-- Friends --'
eachFriend {
    println it
}

println()

println "-- Following me --"
eachFollower {
    println it
}

println()

search('bbc' ){ from, tweet ->
    println from + " : " + tweet
}