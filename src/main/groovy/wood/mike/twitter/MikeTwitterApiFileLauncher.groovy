package wood.mike.twitter

import org.codehaus.groovy.control.*
import org.codehaus.groovy.control.customizers.ImportCustomizer

/**
 * Invoke this script by passing in one of the *.mike suffixed files in same directory
 * We're both statically importing MikeTwitter and setting the base script class MikeTwitterScript so methods are
 * available from both these sources
 *
 * You'd have the @Grab the twitter4j dependency or bundle app as jar or whatever to actually use this standalone on the command line
 */
if(args) {
    def conf = new CompilerConfiguration()
    conf.setScriptBaseClass("MikeTwitterScript")
    def imports = new ImportCustomizer()
    imports.addStaticStars("MikeTwitter")
    conf.addCompilationCustomizers(imports)
    def shell = new GroovyShell(this.class.classLoader, new Binding(), conf)
    shell.evaluate (new File(args[0]))
}
else {
    println "Usage: MikeTwitterApiFileLauncher <script>"
    println "   e.g. MikeTwitterApiFileLauncher trends.mike"
    println "        MikeTwitterApiFileLauncher searchbbcbreaking.mike"
    println "        MikeTwitterApiFileLauncher multiple.mike"
}
