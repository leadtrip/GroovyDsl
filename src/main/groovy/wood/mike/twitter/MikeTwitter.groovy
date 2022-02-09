package wood.mike.twitter

import twitter4j.Query
import twitter4j.Status

import static twitter4j.TwitterFactory.getSingleton

class MikeTwitter {
    static sendMessage(user, message) {
        // Send a direct messsage to twitter user GroovyDSL
        singleton.sendDirectMessage(user, message)
    }
    // Method to apply a closure to each friend
    static eachMessage(Closure c) {
        singleton.getDirectMessages(4).each {
            c.call(it.senderId,it.text)
        }
    }

    // Method to apply a closure to each friend
    static void eachFriend(Closure c) {
        singleton.getFriendsList('fdearle',-1).each {
            c.call(it.screenName)
        }
    }

    static void display(user) {
        println user
    }

    // Method to apply a closure to each follower
    static void eachFollower(Closure c) {
        singleton.getFollowersList('fdearle',-1).each {
            c.call(it.screenName)
        }
    }

    // Method to follow another twitter user
    static void follow(user) {
        singleton.createFriendship(user)
    }

    static search(terms) {
        def query = new Query(terms)
        def tweets = singleton.search(query).tweets
        def result = []
        tweets.each {
            println it.text
            result << [from:it.user.name, tweet: it.text]
        }
        result
    }

    static void search(terms, Closure c) {
        def query = new Query(terms)
        singleton.search(query).tweets.each {
            c.call(it.user.screenName,it.text)
        }
    }

    static void search(Map args, String terms = "") {
        def queryString = ""
        args.each { arg ->
            switch (arg.key.toString().toLowerCase()) {
                case 'from':
                    queryString += "from:${arg.value} "
                    break
                case 'to':
                    queryString += "to:${arg.value} "
                    break
                case 'hashtag':
                    queryString += "#${arg.value} "
                    break
                case 'username':
                    queryString += "@${arg.value} "
                    break
            }
        }
        queryString += terms
        def query = new Query(queryString)
        singleton.search(query).tweets.each {
            println it.user.screenName + ' - ' + it.text
        }
    }
}
