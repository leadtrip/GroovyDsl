package wood.mike

import spock.lang.Specification

class GroovyBasicsSpec extends Specification{

    enum Status{
        ACTIVE,
        INACTIVE,
        DECEASED
        def asBoolean() {
            this == ACTIVE
        }
    }

    def "A class or enum can implement asBoolean for Groovy Truth"() {
        expect: "Only Status.ACTIVE will return true from asBoolean"
            Status.ACTIVE
            !Status.INACTIVE
            !Status.DECEASED
    }

    def "Groovy has a ternary operator (a ? b : c)" () {
        given:
            def b = 'value1'
            def c = 'value2'
        and: "a ternary expression"
            def result1 = (a ? b : c)
        and: "the logical equivalent using if and condition"
            def result2
            if (a) {
                result2 = b
            } else {
                result2 = c
            }
        expect: "these expressions are equivalent for various values of a"
            result1 == result2
        where:
            a << [1,0,2,true,false]
    }

    def "Groovy also has the Elvis operator (a ?: b)" () {
        given:
            def b = 'value1'
        and: "a ternary expression"
            def result1 = (a ?: b)
        and: "the logical equivalent using if and condition"
            def result2
            if (a) {
                result2 = a
            } else {
                result2 = b
            }
        expect: "these expressions are equivalent for various values of a"
            result1 == result2
        where:
            a << [1,0,2,true,false]
    }

    def "Groovy spaceship works like Java's compareTo method"() {
        expect:
            a == (b <=> c)
        and:
            (b <=> c) == b.compareTo(c)
        where:
            a   |   b   |   c
            -1  |   1   |   2
            0   |   1   |   1
            1   |   2   |   1
    }

    def "range object have to and from properites"() {
        given: "some ranges"
            def numbers = 1..100
            def letters = 'a'..'z'
        expect: "range has to and from properties"
            numbers.from == 1
            numbers.to == 100
            letters.from == 'a'
            letters.to == 'z'
        and: "range is a java.util.List so we can use contains"
            numbers.contains 2
            numbers.contains 5
        and: "we can use the in keyword with ranges"
            5 in numbers
    }

    def "Ranges are just lists with values in sequence"() {
        given: "a range and the list equivalent"
            def numberList = [1,2,3,4,5,6,7,8,9,10]
            def numberRange = 1..10
        expect: "they are equal"
            numberList == numberRange
    }

    def "We can declare lists within lists and contents can be heterogeneous"(){
        given: "a list within a list"
            def multidimensional = [1,3,5,["apple","orange","pear"]]
        expect: "we can add to lists together using the plus operator"
            [1,3,5] + [["apple","orange","pear"]] == multidimensional
        and:
            multidimensional[3] instanceof List
        and: "also with the left shift operator"
            [1,3,5] << ["apple","orange","pear"] == multidimensional
        and: "we can Subtract elements with the minus operator"
            multidimensional - [["apple","orange","pear"]] == [1,3,5]
        and: "we can flatten that multi dimensional list"
            multidimensional.flatten() == [1,3,5,"apple","orange","pear"]
    }

    def "Groovy Lists have many useful helper methods"(){
        given: "some Lists"
            def odds = [1,3,5]
            def evens = [2,4,6]
            def animals = ["cat", "dog", "fox", "cow"]
        expect: "we can reverse the order of a list"
            odds.reverse() == [5,3,1]
        and: "can apply a closure to a list to transform it using collect"
            odds.collect { it + 1 } == evens
        and: "we can find in the list using regex"
            animals.grep( ~/.o./ ) == ["dog", "fox", "cow"]
        and: "we can sort a list"
            [5,1,3].sort() == odds
        and: "we can find elements matching an expression"
            animals.find { it == "dog" } == "dog"
    }

    def "We can iterate a list in both directions"() {
        given:
            def list = [1,3,5]
            def number = ''
        when: "we iterate Iterate forwards"
            list.each { number += it }
        then: "the numbers are added to the string in order"
            number == '135'
        when: "we iterate backwards"
            number = ''
            list.reverseEach { number += it }
        then: "the numbers are added in reverse order"
            number == '531'
    }

    def "we can use any and every methods on a List to test a condition across it"() {
        given:
            def list = [1,2,3,5,7,9]
        expect: "any member is even because 2 is even"
            list.any { it % 2 == 0 }
        and: "every member is not even"
            ! list.every { it % 2 == 0 }
    }

    def "we can use array and property style accessors for Maps"() {
        given: "we declare a simple map"
            def fruitPrices = ["apple":20,"orange":25,"pear":30]
        expect: "we can subscript a map with any key value"
            fruitPrices["apple"] == 20
        and: "use the key like it was a property"
            fruitPrices.apple == 20
    }

    def "we can access a Maps elements using get()"() {
        given: "we have keys of type String we can leave out the quotes"
            def fruitPrices = [apple:20,orange:25,pear:30]
        expect: "we can retrieve a value using the get method"
            fruitPrices.get("apple") == 20
        and: "we can supply a default value for items that are not found"
            fruitPrices.get("grape", 5) == 5
    }

    def "we can declare an empty Map"() {
        given: "we can declare a variable that is empty but is a map"
            def empty = [:]
        expect: "it is an empty map"
            empty instanceof Map
            empty.size() == 0
    }

    def "value assignment can be achieved with superscript or property syntax"() {
        given:
            def fruitPrices = [apple:20,orange:25,pear:30]
        when: "assigning a value, it can be done via superscript"
            fruitPrices['apple'] = 21
        then: "the expected value was set"
            fruitPrices['apple'] == 21
        when: "we try the same with a property accessor"
            fruitPrices.apple = 22
        then: "that also works"
            fruitPrices['apple'] == 22
        when: "assign a value to a key that does not exist"
            fruitPrices.grape = 6
        then: "a new item is added to the Map"
            fruitPrices == [apple:22,orange:25,pear:30, grape:6]
    }

    def "Maps support addition of elements via the plus operator"() {
        given:
            def fruit = [apple:20, orange:25 ]
            def veg = [pea:1, carrot:15]
        expect: "we can add these Maps using plus"
            fruit + veg == [apple:20, orange:25, pea:1, carrot:15]
        and: "map  equality is agnostic to order"
            fruit + veg == [ pea:1, carrot:15, apple:20, orange:25]
    }

    def "Map keys don't have to always be strings"() {
        given:
            def squares = [ 1:1, 2:4, 3.0:9]
        expect:
            squares[1] == 1
            squares[2] == 4
            squares[3.0] == 9
    }

    def "we can declare a key using a variable so long as we surround it with parens"() {
        given:
            def apple = 1
            def map = [ apple:"Red", (apple):"Green"]
        expect:
            map[1] == "Green"
            map["apple"] == "Red"
    }

    def "we can use the spread dot operator to access all keys/values of a Map"() {
        given: "a map and two arrays with the same keys and values"
            def map = [a:"apple", o:"orange", p:"pear"]
            def keys = ["a", "o", "p"]
            def values = ["apple", "orange", "pear"]
        expect: "we can use spread dot to access all keys/values"
            map*.key == keys
            map*.value == values
        and: "which is equivalent to using the collect method"
            map.collect { it.key } == keys
            map.collect { it.value } == values
    }

    class Name{
        String name
        String greet( String greeting ) {
            "$greeting, $name"
        }
    }

    def "we can use the spread dot operator to invoke the same method across an array"() {
        given: "An array of Name objects"
            def names = [ new Name(name:"Aaron"),
                                        new Name(name:"Bruce"),
                                        new Name(name:"Carol")]
        when: "we invoke a method via spread dot"
            names*.greet("Hello")
        then: "the method is called in sequence across all the members"
            1
    }

    def "the spread operator explodes collections into their constituent elements"() {
        given: "An array of Name objects"
            def names = [ new Name(name:"Aaron"),
                                        new Name(name:"Bruce"),
                                        new Name(name:"Carol")]
        and: "a closure that expects three parameters"
            def greetAll = { a, b, c ->
                println "Hello $a, $b and $c"
            }
        when: "we use spread against the names array"
            greetAll(*names.name)
        then: "It explodes the names array into three separate objects"
            "Hello Arron, Bruce and Carol"
    }

/*    def "we can use overloaded operators on some Groovy classes such as Date"() {
        given:
            def today = new Date()
            def tomorrow = today + 1
            def yesterday = today - 1
        expect:
            today.plus(1) == tomorrow
            tomorrow.minus(1) == today
            today.minus(1) == yesterday
    }*/

}
