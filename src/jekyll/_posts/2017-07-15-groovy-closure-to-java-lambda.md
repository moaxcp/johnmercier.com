Groovy provides standard high-level functions for dealing with java collections: each, find, findAll, collect, inject. It may be possible to map these methods to java stream equivelents.

# find

"Finds the first value matching the closure condition." --groovydoc

`find` runs a closure on each element in a collection returning the first element where the closure returns true.

```groovy
def names = ['John', 'Joe', 'Josh']
assert 'John' == names.find { it.length() == 4 }
```
