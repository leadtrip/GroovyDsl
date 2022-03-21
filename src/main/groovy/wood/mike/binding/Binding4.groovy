package wood.mike.binding

Binding binding = new Binding()
binding.message = "Hello, World!"
binding.saySomething = { something -> println something }

shell = new GroovyShell(binding)

shell.evaluate("println message")
shell.evaluate( "saySomething( 'Bonjour' )" )
