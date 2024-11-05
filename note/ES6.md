# ES6 (ECMAScript6)
- [ES6 (ECMAScript6)](#es6-ecmascript6)
  - [var / let / const](#var--let--const)


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
var a = 0 // ok

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
