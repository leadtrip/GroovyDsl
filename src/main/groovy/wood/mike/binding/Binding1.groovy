package wood.mike.binding

/**
 * Every groovy script has an associated binding (groovy.lang.Binding) where instances of variables referenced in
 * the script are stored.
 * Groovy adds previously undeclared variables to the binding whereas defined variables i.e. prefixed with def or
 * statically typed are not added to the binding.
 * This allows us to pre-load a script with variables and closures for use as a DSL
 */

// undeclared variable will be added to the binding
count = 1

assert count == 1
assert binding.getVariable("count") == 1
binding.setVariable("count" , 2)
assert count == 2
assert binding.getVariable("count") == 2 

// declared variable will not be added to the binding
def local = count 

assert local == 2
try {
    binding.getVariable("local")
    assert false
} catch (e) {
    assert e in MissingPropertyException
}

println "Success"