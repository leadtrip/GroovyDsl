package wood.mike.binding

// adding nested closures to the binding

binding.block = { closure ->
    def cloned = closure.clone()    // clone the passed in closure in case original is used externally
    cloned.delegate = delegate
    this.enclosing = "block"        // add property so we can check in nestedBlock
    
    println "block encountered"
    cloned()
}

binding.nestedBlock = { closure ->
    assert closure.delegate.enclosing == "block"
    def cloned = closure.clone()
    cloned.delegate = delegate
    this.enclosing = "nestedBlock"
    
    println "nested block encountered"
    cloned()
}

block {
  nestedBlock { 
  }
}
block {
  nestedBlock { 
  }
}

