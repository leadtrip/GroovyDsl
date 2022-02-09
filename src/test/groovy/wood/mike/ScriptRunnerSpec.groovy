package wood.mike

import spock.lang.Specification

import java.util.concurrent.TimeUnit

class ScriptRunnerSpec extends Specification{

    GroovyShell shell
    Binding binding
    PrintStream orig
    ByteArrayOutputStream out

    def setup() {
        orig = System.out
        out = new ByteArrayOutputStream()
        System.setOut( new PrintStream(out))
        binding = new Binding()
        shell = new GroovyShell(binding)
    }

    def cleanup() {
        System.setOut( orig )
    }

    String output() {
        out.toString().trim()
    }

    void "test running script"() {
        given: "get hello world script"
            def script = new File("./src/test/groovy/wood/mike/Hello.groovy")
        when: "the script is executed"
            shell.evaluate script
        then:
            "Hello, World!" == output()
    }

    def "HelloWorld says Hello World in Java and Groovy styles"() {
        given: "we have a Hello World script"
            def script = new File(fileName)

        when: "we run the script"
            shell.evaluate script

        then: "the script outputs the correct details"
            expectedOutput == output()

        where: "we have different versions of HelloWorld using more groovy features"
            fileName                                            | expectedOutput
            "./src/test/groovy/wood/mike/Hello.groovy"          | "Hello, World!"
            "./src/test/groovy/wood/mike/HelloWorld.groovy"     | "Hello, World!"
    }

    def "test gradle"() {
        given:
            def command = "cmd /c .\\gradlew -q -b .\\otherbuild.gradle hello"
        when:
            def proc = command.execute()
            proc.waitFor()
        then:
            proc.in.text.trim().endsWith( 'Hello everyone' )
    }
}
