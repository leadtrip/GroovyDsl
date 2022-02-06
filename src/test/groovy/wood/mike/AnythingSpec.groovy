package wood.mike

import spock.lang.Specification

class AnythingSpec extends Specification{

    void setupSpec() {
        println 'setupSpec'
        // called once per specification run
    }

    void cleanupSpec() {
        println 'cleanupSpec'
        // called once per specification run
    }

    void setup() {
        println 'setup'
        // called before every test
    }

    void cleanup() {
        println 'cleanup'
        // called after every test
    }

    void "test anything"() {
        given:
            def t = true
        expect:
            t
    }

    void "test multiple"() {
        given:
            def a = 'a'
        and:
            def b = 'b'
        when:
            def ab = a+b
        then:
            'ab' == ab
        and:
            'ba' == ab.reverse()
    }
}
