package wood.mike

import groovy.transform.Canonical

import static wood.mike.EventPlanner.*
import static wood.mike.StreamServiceEnum.*

say( 'Hello').to( 'Fred' )
say "Hello" to "Dave"           // no need for parenthesis or dots

class Message {
    String message

    def to(String person) {
        println "$message $person"
    }
}

def say(String message) {
    new Message(message: message)
}

// -------------------------------------------------------------------

// call drive method then access the map entry with key 'to' which accepts
drive 'bus' to 'moon'
drive 'car' to new StringBuffer('shop')

// method returns a map with single entry with key named 'to' and value of closure
def drive(String vehicle) {
    [
        to: { place ->
            println "Driving $vehicle to the $place"
        }
    ]
}

remember to: 'wake up', then: 'eat breakfast', and: 'brush teeth'

static def remember(Map map) {
    print "Remember "
    map.each {
        print it.key + ' ' + it.value + ' '
    }
}

static def schedule(String movie) {
    [
        on: { StreamServiceEnum ss ->
            [at: { time ->
                [with: { guest ->
                    new EventPlanner().movieNight( movie, time, guest, ss )
                }]
            }
            ]
        }
    ]
}

println()

schedule 'Gladiator' on NETFLIX at '22:00' with 'bob'

play 'jesus of suburbia' by 'green day' on SPOTIFY

@Canonical
class EventPlanner {

    static def play(String song) {
        [
            by: { artist->
                [on: { StreamServiceEnum ss ->
                    playSong(song, artist, ss)
                }
                ]
            }
        ]
    }

    def playSong( song, artist, ss ) {
        service( ss ).playSong( song, artist )
    }

    def service ( StreamServiceEnum ss ) {
        switch (ss) {
            case YOUTUBE: return new YouTube()
            case NETFLIX: return new Netflix()
            case SPOTIFY: return new Spotify()
        }
    }

    def movieNight( movie, time, guest, ss ) {
        notify( movie, guest, time )
        service( ss ).streamMovie( movie, time )
    }

    def notify( movie, guest, time ) {
        println "Notifying $guest"
    }
}

trait StreamingService{
    void streamMovie( movie, time ) {
        throw new UnsupportedOperationException( "Sorry can't stream movies on this service" )
    }

    void playSong( song, artist ) {
        throw new UnsupportedOperationException( "Sorry can't play songs on this service" )
    }
}

class YouTube implements StreamingService {
    void streamMovie(Object movie, Object time) {
        println "YouTube will be streaming $movie at $time"
    }
}
class Netflix implements StreamingService {
    void streamMovie(Object movie, Object time) {
        println "Netflix will be streaming $movie at $time"
    }
}

class Spotify implements StreamingService {
    void playSong( song, artist ) {
        println "Spotify is playing $song by $artist"
    }
}

enum StreamServiceEnum {
    YOUTUBE, NETFLIX, SPOTIFY
}