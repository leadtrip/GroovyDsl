package wood.mike

import org.codehaus.groovy.runtime.DefaultGroovyMethods

// examples from the Closure class definition

// Resolution strategies
/**
 * OWNER_FIRST (default)
 * Will succeed, because the x and y fields declared in the Test class shadow the variables in the delegate.
   Note that local variables are always looked up first, independently of the resolution strategy
 */
class TestOwnerFirst {
    def x = 30
    def y = 40

    def run() {
        def data = [ x: 301, y: 3832 ]
        def cl = { y = x + y }
        cl.delegate = data
        cl()
        assert x == 30
        assert y == 70
        assert data == [x:301, y:3832]
    }
}

new TestOwnerFirst().run()

// --------------------------------------------------------------------
/**
 * DELEGATE_FIRST
 * This will succeed, because the x and y variables declared in the delegate shadow the fields in the owner class.
 */
class TestDelegateFirst {
    def x = 30
    def y = 40

    def run() {
        def data = [ x: 74, y: 826 ]
        def cl = { y = x + y }
        cl.delegate = data
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
        assert x == 30
        assert y == 40
        assert data == [x:74, y:900]
    }
}

new TestDelegateFirst().run()

// --------------------------------------------------------------------

/**
 * OWNER_ONLY
 * will throw "No such property: z" error because even if the z variable is declared in the delegate, no lookup is made.
 */
class TestOwnerOnly {
    def x = 30
    def y = 40

    def run() {
        def data = [ x: 10, y: 20, z: 30 ]
        def cl = { y = x + y + z }
        cl.delegate = data
        cl.resolveStrategy = Closure.OWNER_ONLY
        cl()
        println x
        println y
        println data
    }
}

//new TestOwnerOnly().run()

// ------------------------------------------------------------------

/**
 * DELEGATE_ONLY
 * will throw an error because even if the owner declares a "z" field, the resolution strategy will bypass lookup in the owner
 */
class TestDelegateOnly {
    def x = 30
    def y = 40
    def z = 50

    def run() {
        def data = [ x: 10, y: 20 ]
        def cl = { y = x + y + z }
        cl.delegate = data
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl()
        println x
        println y
        println data
    }
}

// new TestDelegateOnly().run()

// ------------------------------------------------------------------

// closures are of abstract type Closure, there are many concrete implementations
def printerClosure = {println it}
assert printerClosure instanceof Closure

// various ways to use closure on list
def strList = ['red', 'yellow', 'brown']
DefaultGroovyMethods.each( strList, {println it} )  // dunno why you'd do this but you can
strList.each ({println it})                         // {} aren't required but can be specified
strList.each(){ println it.capitalize() }           // parenthesis for each aren't required by can be specified
strList.each {println it.size() }                   // standard
strList.each printerClosure                                  // no additional {}, 'it' is implicitly the list item
strList.each {printerClosure 'always cheese' } // not making use of it i.e. the list item & passing 'always cheese' to closure
strList.each {printerClosure it.reverse()  }         // single arg to closure requires no parenthesis
strList.each {thing -> printerClosure.call( thing.toUpperCase() ) } // explicit call to call

def zeroArgsClosure = { -> println 'I accepts no args'}
zeroArgsClosure()
// zeroArgsClosure(1) can't do this
// ------------------------------------------------------------------

// closure is only argument
static def myClosureMethod(Closure c ) {
    c()
}

// closure is final argument
static def myClosureMethodTwoArgs(Integer i, Closure c ) {
    c(i)
}

myClosureMethod{ println ('Hello') }        // no need for parenthesis with closure as only method argument

myClosureMethodTwoArgs(6) {println it*2}    // closure as final argument to method can go outside of parenthesis

def myClosureWithDefault = { arg1, arg2 = 'bob' -> println "$arg1 $arg2" }

myClosureWithDefault 'Hello'

// ------------------------------------------------------------------

class Customer{
    String name
}

// looking up something then applying closure on retrieved thing
static def withCustomer(id, Closure c) {
    def cust = getCustomerRecord(id)
    c.call(cust)
}

static def getCustomerRecord(id) {
    new Customer(name: 'Bob')
}

withCustomer(1232) {
    customer -> println "Found customer ${customer.name}"
}

// ------------------------------------------------------------------

def locked (Closure c ) {
    println "Transaction lock"
    c.call()
    println "Transaction released"
}

def update(customer, Closure c) {
    println "Customer name was ${customer.name}"
    c.call(customer)
    println "Customer name is now ${customer.name}"
}

def customer = new Customer(name: 'Fred')

locked {
    update(customer) {
        cust -> cust.name = 'Barney'
    }
}

// ------------------------------------------------------------------
// doCall()
// when we create a groovy closure, groovy creates a Closure class with implementations of doCall based on how we've
// defined the closure parameters
def defaultParams = {println it}


// testing our own Java closures

def stringParams = new StringClosure(this)
stringParams 'Java string'

try{
    stringParams 1
}catch(MissingMethodException mme) {        // no doCall implemented for Integers
    println mme.message
}

def multiParams = new MultiClosure(this)
multiParams 'a string is fine'
multiParams 9210 // so is Integer as we've implemented doCall for Integer
// multiParams 9210L  but not for Long so this will fail

// ------------------------------------------------------------------
// currying
def indian = {style, meat, rice -> return "$meat $style with $rice rice"}   // indian closure accepts 3 parameters
def vindaloo = indian.curry 'Vindaloo'      // currying effectively copies the original closure & prepacks arguments, in this case the first argument only
def korma = indian.curry 'Korma', 'Lamb' // and now with 2 aruguments

def vindalooMeal = vindaloo "Chicken", "fried"
assert vindalooMeal == "Chicken Vindaloo with fried rice"

def kormaMeal = korma "boiled"
assert kormaMeal == "Lamb Korma with boiled rice"

def randomMeal = korma.curry 'brown'    // currying an curried closure
assert randomMeal() == 'Lamb Korma with brown rice'

println indian('bacon', 'my', 'egg')
println vindaloo( 'hog', 'tim' )
