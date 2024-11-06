# ES6 (ECMAScript6)
- [ES6 (ECMAScript6)](#es6-ecmascript6)
  - [var / let / const](#var--let--const)
  - [解构表达式](#解构表达式)
  - [function](#function)
    - [`this`](#this)
  - [class / extends / constructor](#class--extends--constructor)
  - [copy](#copy)
  - [module](#module)
    - [three export / import methods](#three-export--import-methods)


## var / let / const
- use let instead of var
```javascript
console.log(a)// undefined
var a = 0

console.log(b)// error
let b = 0
```

```javascript
var a = 1
var a = 0// ok

let b = 1
let b = 0// error
```

```javascript
var a = 1
console.log(window.a)// 1

let b = 1
console.log(window.b)// undefined
```

```javascript
{
    var a = 1
    let b = 1
}
console.log(window.a)// 1
console.log(window.b)// undefined
```

```javascript
var a;
let b;
const c;// error
```

```javascript
// c is an object, attibutes in object could be modified
const c = ref(1)
c.value = 0// ok
```

## 解构表达式
```javascript
let arr = [1, 2, 3, 4]
let [a, b, c, d = 10, e = 20] = arr
console.log(a, b, c, d, e)// 1 2 3 4 20

const arrFunc = ([a,b,c=100])=>{
    console.log(a,b,c)
}
arrFunc(arr)// 1 2 3

let obj = {
    name: "li",
    age: 0
}
let {name, age} = obj
console.log(name, age)// li 0

let newArr = [...arr1, ...arr2]
```

## function

`let myfun = x => x+1` == `let myfun = (x)=>{return x+1}`

### `this`
this in `const myfunc = ()=>{}` is the same level as `myfunc`
```javascript
var myname = "LI"
obj = {
    myname: "li",
    getName: function () {
        console.log(myname)
    },
    getname: function () {
        console.log(this.myname)
    },
    showName: () => {
        console.log(myname)
    },
    showname: () => {
        console.log(this.myname)
    }
}
obj.getName()// LI
obj.getname()// li
obj.showName()// LI
obj.showname()// LI this->window; this.name->window.myname
```

## class / extends / constructor
```javascript
class Person {
    #_name// private start with '#'
    constructor(name) {
        this.#_name = name
    }
    set name(name) {
        console.log("set name:", name)
        this.#_name = name
    }
    get name() {
        console.log("get name:", this.#_name)
        return this.#_name
    }
    static sfun() {
        console.log("sfun")
    }
}
console.log(new Person())

let p = new Person("")
console.log(p)
p.name = "li"
console.log(p)
console.log(p.name)
console.log(p._name)
Person.sfun()

class Student extends Person {
    score
    constructor(name, score) {
        super(name)
        this.score = score
    }
    test() {
        console.log("score: ", this.score)
    }
}

let s = new Student("li", 99)
console.log(s)
```

## copy
- shallow copy
```javascript
let a2 = a
console.log(a)// 1
console.log(a2)// 1
a = 123
console.log(a)// 123
console.log(a2)// 1

let arr2 = arr
console.log(arr)// [ 1, 2, 3, 4 ]
console.log(arr2)// [ 1, 2, 3, 4 ]
arr[0] = 123
console.log(arr)// [ 123, 2, 3, 4 ]
console.log(arr2)// [ 123, 2, 3, 4 ]

let p1 = p
console.log(p1.name)// li
p.name = "LI"
console.log(p1.name)// LI
```
- deep copy
```javascript
// deep copy for Array
arr2 = [...arr]
console.log(arr)// [ 123, 2, 3, 4 ]
console.log(arr2)// [ 123, 2, 3, 4 ]
arr[0] = 1
console.log(arr)// [ 1, 2, 3, 4 ]
console.log(arr2)// [ 123, 2, 3, 4 ]

// deep copy for object
// method 1
p1 = { ...p }
console.log(p1.name)// LI
p.name = "li"
console.log(p1.name)// LI
// method 2
p1 = JSON.parse(JSON.stringify(p))
console.log(p1.name)// li
p.name = "LI"
console.log(p1.name)// li
```

## module
### three export / import methods
- **three methods could use in one js file at the same time**
1. each members decorated with `export` could be imported by other modules
```javascript
// module.js
export MAX = 1
export const func = ()=>{}

// app.js
import * as module from './module.js'
console.log(module.MAX)
module.func()
```
2. use `export {...}` to export some members of module
```javascript
// module.js
MAX = 1
const func = ()=>{}
export {
    MAX,
    func
}

// app.js
// method 1
import * as module from './module.js'
console.log(module.MAX)
module.func()
// method 2
import {MAX as maximize, func} from './module.js'
console.log(maximize)
func()
```
3. use `export default xxx`, default export could only export 1 member, but other member could use `method 1` and `method 2` to export
```javascript
// module.js
MAX = 1
const func = ()=>this.MAX
export default func
// app.js
// method 1
import {default as maximize} from './module.js'
console.log(maximize)
// method 2
import maximize from './module.js'
console.log(maximize)
```

