- [Basic Data Operation](#basic-data-operation)
- ["==" and "==="](#-and-)
- [for each](#for-each)
- [function state](#function-state)
- [JS object](#js-object)
- [JSON in JS](#json-in-js)


# Basic Data Operation
```javascript
10/2 -> 5
10/4 -> 2.5
10/0 -> Infinity
10%0 -> NaN
```
# "==" and "==="
- "==": if data type is not the same, transform both into number
- "===": if data type is not the same then return false
```javascript
1 == 1      -> true
1 == '1'    -> true
1 == true   -> true

1 === 1      -> true
1 === '1'    -> false
1 === true   -> false
```

# for each
- for each in javascript will get a increasing index of iterable object
```javascript
var arr = ["java", "python", "c++"]
for (var index in arr){
    console.log(arr[index])
}
```

# function state
- use **keyword** `arguments` to get all arguments of current function in array type
```javascript
// method 1:(traditional)
function sum(a,b){
    return a+b;
}
// method 2:
var sum = function(a,b){
    return a+b
}
// method 3: (es)
var sum = (a,b)=>{
    return a+b
}

// function as argument:
var invoke = (func, ...args)=>{
    return func(...args)
}
console.log(invoke(sum, 1, 2))
```

# JS object
```javascript
// method 1
// create object
var myObject = new Object()
// object attribute
myObject.name = "li"
myObject.age = 0
console.log(myObject.age)
// object method
myObject.myfunc = ()=>{
    console.log("myobject function")
}
myObject.myfunc()

// method 2
var myObject = {
    name: "li",
    age: 0,
    myfunc: ()=>{
        console.log("myobject function")
    }
}
```

# JSON in JS
- `JSON` is an inner object of JavaScript
- `JSON object` dose not exist, it is a common object in js, operation is the same as common js object
- use `JSON.parse(string)` to transform json string into object
- use `JSON.stringify(object)` to transform object into json object
```javascript
j1 = JSON.parse('{"name":"li", "age": 0}')
console.log(j1)
console.log(JSON.stringify(j1))
```