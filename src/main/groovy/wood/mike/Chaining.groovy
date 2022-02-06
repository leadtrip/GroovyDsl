package wood.mike

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

