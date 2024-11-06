# ES6 (ECMAScript6)
- [ES6 (ECMAScript6)](#es6-ecmascript6)
  - [var / let / const](#var--let--const)
  - [解构表达式](#解构表达式)
  - [function](#function)
    - [`this`](#this)


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

