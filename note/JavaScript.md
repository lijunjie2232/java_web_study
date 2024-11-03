- [Basic Data Operation](#basic-data-operation)
- ["==" and "==="](#-and-)
- [for each](#for-each)
- [function state](#function-state)
- [JS object](#js-object)
- [JSON in JS](#json-in-js)
- [JS Array](#js-array)
  - [api of Array](#api-of-array)


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
    return a+b
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

# JS Array
- create: `var arr = new Array("java", "python", "c++")`
- add：
  - `var newLength = arr.push("c")` = `arr[arr.length] = "java"`
  - `var newLength = arr.unshift("java")`
- modify: `arr[0] = "java"`
- delete: `var last = arr.pop()`
## api of Array
- includes: `arr.includes("c++")`
- indexOf & lastIndexOf: get the first & last index of an item
- isArray: `Array.isArray(arr)`
- join: `arr.join(',')`, both `"` and `'` are ok
- keys: return an Iterator
- map: `arr.map((value, index, array) => {})`
- reverse: `arr_r = arr.reverse()`, **must assign to a variable**, `arr` and others are ok
- reduce & reduceRight: `console.log(arr.reduce((previousItem, currentItem, currentIndex, array) => {return previousItem + "[:reduce:]" + currentItem}))`, (from left to right & from right to left) to calc item, **must return value after calculation**
- shift & pop: remove and return first & last item
- unshift & push: add item to first & last and return new length of array
- of: `var arr = Array.of(1,2,3,4,5)` -> [1,2,3,4,5]
- slice: `var subArr = arr.slice(1,2)`, return sub array
- splice: `arr.splice(startIndex, deleteCount, addItem1, addItem2, ...)`, it changes origin variable directly
  1. deletes deleteCount items starts at startIndex
  2. returns these items in a new Array
  3. insert any addItem? at startIndex if addItem? exists in arguments
  4. number of addItem? has no relationship with deleteCount, when deleteCount=0, only insert new items at startIndex
  5. `startIndex < 0` is ok, for example, `arr = [ 1, 2, 3, 4 ];arr.splice(-1, 0, 7);`, the arr will be `[ 1, 2, 3, 7, 4 ]`
- some: `arr.some((value, index, array)=>{(return boolean)}`, **must retuen boolean**
- filter: `var newArr = arr.filter((item) => {return item !== "c++"}`, not change origin variable arr
```javascript
var arr:Array = new Array()
```