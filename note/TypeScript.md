- [JavaScript bugs](#javascript-bugs)
- [TypeScript](#typescript)
  - [Compile](#compile)
  - [Usage](#usage)
    - [New types](#new-types)
    - [`symbol` and `unique symbol`](#symbol-and-unique-symbol)
    - [custom type](#custom-type)
      - [string and String](#string-and-string)
      - [`object` and `Object`](#object-and-object)
    - [Declare](#declare)
    - [Class](#class)
    - [Abstract](#abstract)
    - [Interface](#interface)
    - [Genericity](#genericity)
    - [type declare for js](#type-declare-for-js)
    - [Decorator](#decorator)


# JavaScript bugs
```javascript
let a = "123"
a() // error
```
```javascript
const a = new Date().getTime() % 2
if (a != 1){
    console.log(0)
} else if (a == 0){
    console.log(0) // never access
}
```
```javascript
let a = {age:0}
console.log(a.name) // undefined
a.func() // no such function
```
**but TypeScript will treat these as error in compile period**

# TypeScript
## Compile
- method 1
  - `tsc index.ts` -> compile to `index.js`
- method 2
  - `tsc --init` to form `tsconfig.json` at project root
  - `tsc --watch` to compile all ts file
- method 3
  - `webpack` or `vite` will automaticly compile `.ts` file

## Usage

### New types
1. `any`
   1. `let a:any` or just `let a` will get a `any` variable
   2. tsc will not check type of a `any` variable
   3. an `any` type variable **could** assignment to other every type variable
2. `unknown`
   1. a `any` like type with safe type
   2. a `unknown` type variable **could not** assignment to other every type variable
   3. use `if(typeof unk === 'string'){ str = unk }` or `str = unk as string` or `str = <string> unk` to assignment `unknown` type variable `unk` to `string` type variable `str` , or to access members in `unk`
3. `never`
   1. a function return `never` type must not to be finish or must throw error
4. `void`
   1. a `void` function means could not use return value
   2. a `void` function could return `undefined` or other type accepted by `void`
5. `tuple`
   1. `tuple` is not key word of typescript, it is a special Array allowed specific different types
   2. declare like this: `let tup:[string, number?, ...string[]] = ["123"]`, `tup` is a `tuple` 
6. `enum`
   - if declare Season as `const` like `const enum Season`, `Season` and all it followed operations would be handled in ts compile and replace with `number` or `string`
```typescript
enum Season {
    haru, natsu, aki, fuyu
}
console.log(Season) // {0: 'haru', 1: 'natsu', 2: 'aki', 3: 'fuyu', haru: 0, natsu: 1, aki: 2, fuyu: 3}
console.log(Season[0]) // haru
console.log(Season["haru"]) // 0
console.log(Season.aki) // 2

let season1:Season = 0
let season2:Season = Season.aki
const num2str = (s:Season):string=>{
    return Season[s]
}
console.log(season1) // 0
console.log(season2) // 2
console.log(num2str(Season.aki)) // aki
console.log(num2str(Season["aki"])) // aki
console.log(num2str(0)) // haru

enum NumSeason {
    haru = 0,
    natsu = 1,
    aki = 2,
    fuyu = 3
}
console.log(NumSeason) // {0: 'haru', 1: 'natsu', 2: 'aki', 3: 'fuyu', haru: 0, natsu: 1, aki: 2, fuyu: 3}

enum StrSeason {
    haru = "haru",
    natsu = "natsu",
    aki = "aki",
    fuyu = "fuyu"
}
console.log(StrSeason) // {haru: 'haru', natsu: 'natsu', aki: 'aki', fuyu: 'fuyu'}
```
7. `bigint`
   1. not compatiable with number
   2. target >= es2020
### `symbol` and `unique symbol`
- each value of `symbol` is not the same as other `symbol`, but type is the same
- each type of `unique symbol` is not same with other `unique symbol`
- target >= es2015
- `unique symbol` must be `const`
- as class member, `unique symbol` must be `readonly static`
```typescript
// create a symbol variable
const s3 = Symbol("liao");
const s4 = Symbol("liao");
console.log(s3 === s4); // error
// create a unique symbol variable
const s1:unique symbol = Symbol()
const s2:typeof s1 = s1
console.log(s1 === s2) // true

// symbol type as object member name
let sname = Symbol();
let sobj = {
    [sname]: "a"
};
console.log(sobj); // { Symbol(): 'a' }

// Symbol inner function
const s3 = Symbol.for("a")
console.log(Symbol.keyFor(s3)) // a
```
### custom type
- `type`
   - in following "special situation", `dst.push(a)` returns new lenght is a number, however `src.forEach` must be given into a void type function, so, **if assign void `type` first and then declare this `type` to a function, this function could return any type, not just undefine**, **however**, this return could not to used for any other operations, for it is from a `void` type return function
```typescript
type StatusCode = number | string
const scode = (code: StatusCode): void => {
    console.log(code);
}

type Area = {area:number}
type Address = {addr:string}
type House = Address & Area
let house:House = {
    area: 10,
    addr: "1ban"
}

// special situation
type voidFunc = ()=>void

const printTime:voidFunc = ()=>{
    return 10 // special situation
}

const src  = [1,2,3]
const dst = []
src.forEach(
    (a) => dst.push(a)
)
```

#### string and String
```typescript
let s1:string = "123"
s1 = "321" // ok
s1 = new String("123") // error

let s2:String = "123"
s2 = "321" // ok
s2 = new string("123") // ok
```

#### `object` and `Object`
- `object` could storage any type except origin types
- `Object` could storage types which could call Object method (`null`, `undefined` could not)

### Declare
```typescript
// declare a variable
let a: string
a = "hello"

// declare a object
let aPerson: { name: string, age?: number, [_: string]: any } = {
    name: "li",
    sudo: true
}

// declare a function
const sum = (a: number, b: number): number => {
    return a + b
}

// declare an array
let arr1: string[] = []
let arr2: Array<string> = []
```

### Class
- `private` type could be extended, console.log could print out after extending, but could not be visited
- `readonly` could only be modified on self constructor
```typescript
// class test
class Person {
    readonly name: string
    age: number
    private info: string = ""
    constructor(n: string, a: number, i?: string) {
        this.name = n
        this.age = a
        if (i)
            this.info = i
    }
    toString(): string {
        return `[name]: ${this.name}, age: ${this.age}, info: ${this.info}`
    }
}

class Student extends Person {
    score: number|undefined

    constructor(n: string, a: number, i?: string, s?: number) {
        super(n, a, i)
        this.score = s
    }
    override toString(): string {
        return super.toString() + `, score: ${this.score}`
    }
}

console.log(new Student("li", 0, "123321", 99)) // Student {info: '123321', name: 'li', age: 0, score: 99}
console.log(new Student("li", 0, "123321", 99).toString()) // [name]: li, age: 0, info: 123321, score: 99
```

### Abstract
- define and share general function
- ensure key function
```typescript
// abstract test
abstract class AbHouse {
    constructor(public price: number, public area: number) { }
    abstract getPrice(): number
}

class MyHouse extends AbHouse {
    name:string
    constructor(price: number, area: number, name:string) { 
        super(price, area)
        this.name = name
    }
    getPrice(): number{
        return this.area * this.price
    }
}
console.log(new MyHouse(0,0,"li").getPrice())
```

### Interface
- `interface` could conbine and extends while `type` could use `&` and `|`
```typescript
// interface test
interface Animal{
    age:number
}
// interface extends
interface IPet extends Animal {
    name: string
}
// interface auto conbine
interface IPet extends Animal {
    play(): void
}

//class interface
class mydog implements IPet {
    constructor(public name: string, public age: number) { }
    play = () => {
        console.log(`play with ${this.name}`);
    }
}
new mydog("mydog", 0).play()

// object interface
let mycat: IPet = {
    name: "mycat",
    age: 0,
    play: () => {
        console.log(`play with ${mycat.name}`);
    }
}
mycat.play()

// function interface
interface FuncInterface {
    (a: number, b: number): number
}
const iSum: FuncInterface = (a, b) => a + b
```

### Genericity
```typescript
// genericity test
function asArray<T>(...arr: T[]): T[] {
    return [...arr]
}
console.log(asArray<string>("1", "2", "3"))
console.log(asArray<number>(1, 2, 3))

// multiple generic type
function genFunc<T, U, V>(t:T, u:U, v:V):void {
    console.log(t, u, v)
}
genFunc<number, string, number>(1, "1", 1)
```

### type declare for js
- `.ts` file could not import from `.js` file directly, it need declare type of function in `.js` in an additional `.d.ts` file
```typescript
// index.d.ts
declare function add(a:number, b:number):number;
declare function sum(a:number, b:number):number;

export {add, sum}
```
### Decorator
