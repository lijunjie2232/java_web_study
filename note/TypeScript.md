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
      - [Interface of different decorator](#interface-of-different-decorator)
      - [Class decorator](#class-decorator)
      - [attibute decorator](#attibute-decorator)
      - [function decorator](#function-decorator)
    - [Parameter decorator](#parameter-decorator)


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
- config `"experimentalDecorators": true` to enable decorator
- multiple warppers will get the lowest warps first
#### Interface of different decorator
```typescript
declare type ClassDecorator = <TFunction extends Function>(target: TFunction) => TFunction | void;
declare type PropertyDecorator = (target: Object, propertyKey: string | symbol) => void;
declare type MethodDecorator = <T>(target: Object, propertyKey: string | symbol, descriptor: TypedPropertyDescriptor<T>) => TypedPropertyDescriptor<T> | void;
declare type ParameterDecorator = (target: Object, propertyKey: string | symbol | undefined, parameterIndex: number) => void;
```
#### Class decorator
- args
  - `target:Function`
- use `type` to restrict on the type being decorated
```typescript
// decorate class
const myToString = (target:Function)=>{
    target.prototype.toString = ()=>{
        return JSON.stringify(target)
    }
    Object.seal(target.prototype)
}

@myToString
class dClass{
    constructor(public key:string, public value:string){}
}

var dc = new dClass("key", "value")
console.log(dc) // dClass {key: 'key', value: 'value'}
```
- if decoretor return a class, it will replace the decorated class
```typescript
// replace class
type TimeWrapper = new (...args: any[]) => {}
const instTimer = <T extends TimeWrapper>(target: T) => {
    return class extends target {
        createdTime: Date;
        constructor(...args: any[]) {
            super(...args)
            this.createdTime = new Date()
        }
        getCreatedTime = () => this.createdTime
    }
}

@myToString
@instTimer
class d1Class {
    constructor(public key: string, public value: string) { }
}
// auto combine to class d1Class
interface d1Class {
    getCreatedTime: () => Date;
}
console.log(new d1Class("key", "value")) // d1Class {key: 'key', value: 'value', getCreatedTime: ƒ, createdTime: Tue Nov 12 2024 15:06:53 GMT+0800 (中国標準時)}
console.log(new d1Class("key", "value").getCreatedTime()) // Tue Nov 12 2024 15:06:53 GMT+0800
console.log(new d1Class("key", "value").getCreatedTime().getTime()) // 1731395213964
console.log(new d1Class("key", "value").getCreatedTime().getTime()) // 1731395213965
```

- decorator factory
```typescript
// decorator factory
const introWraper = (arg: number) => {
    return (target: Function) => {
        target.prototype.toString = function () {
            return `[key]: ${this.key}, [value]: ${this.value}, [warper arg]: ${arg}`
        }
    }
}
@introWraper(1)
class dfClass {
    constructor(
        public key: string,
        public value: string
    ) { }
}

console.log(new dfClass("value", "key").toString()) // [key]: value, [value]: key, [warper arg]: 1
```
#### attibute decorator
- args:
  - `target:object`
  - `propertyKey:string`
```typescript
// attibute decorator
const aWarpper = (target: object, propertyKey: string) => {
    console.log(`[aWarpper] [target]: ${target}, [propertyKey]: ${propertyKey}`)
}

class aClass {
    @aWarpper // [aWarpper] [target]: [object Object], [propertyKey]: key
    public key: string
    @aWarpper/** static attibute will pass class as target
    [aWarpper] [target]: class aClass {
        constructor(key) {
            this.key = key;
            // this.value = value
        }
    }, [propertyKey]: value
     */
    public static value: string = "aClass value"
    constructor(
        key: string,
        // value: string
    ) {
        this.key = key
        // this.value = value
    }
}

// example
const state = (target: object, propertyKey: string) => {
    Object.defineProperty(
        target,
        propertyKey,
        {
            get() {
                console.log("get")
                return this[`__${propertyKey}`]
            },
            set(newValue) {
                console.log(`${propertyKey} set a new value: ${newValue}`)
                this[`__${propertyKey}`] = newValue
            },
            enumerable: true,
            configurable: true
        }
    )
}
class sClass {
    @state public key: string
    public value: string = "aClass value"
    constructor(
        key: string,
        value: string
    ) {
        this.key = key
        this.value = value
    }
}

let sc = new sClass("v", "k") // key set a new value: v
console.log(sc.key) // get\nv
sc.key = "value" // key set a new value: value
```

#### function decorator
- args:
  - `target:object`
  - `propertyKey:string`
  - `descriptor:PropertyDescriptor`
```typescript
// method decorator
const Timer: MethodDecorator = (target: object, propertyKey: string | symbol, descriptor: PropertyDescriptor) => {
    const func = descriptor.value
    descriptor.value = function (...args: any[]) {
        let start = Date.now()
        let result = func.call(this, ...args)
        let end = Date.now()
        console.log(`use time: ${end - start}ms`)
        return result
    }
}

class AC {
    @Timer static call(start: number, end: number, step?: number) {
        if (step == null) step = 1
        let acc = 1
        for (let i: number = start; i < end; i++) {
            acc += i**2
        }
        return acc
    }
}

AC.call(1, 100000) // use time: 2ms
```
```typescript
// setter / getter decorator
const numRange = (min?: number, max?: number) => {
    return (target: Object, propertyKey: string, descriptor: PropertyDescriptor) => {
        const setter = descriptor.set
        descriptor.set = function (v: number) {
            if (min != null && v < min) throw new Error(`${v} must >= ${min}`)
            if (max != null && v > max) throw new Error(`${v} must <= ${max}`)
            if (setter)
                setter.call(this, v)

        }
    }
}

class sgClass {
    private _age: number;
    constructor(a: number) { this._age = a }
    @numRange(0, 120)
    set age(v: number) {
        this._age = v
    }
    get age() { return this._age }
}

let sg = new sgClass(10)
new sgClass(100)
new sgClass(1000)

sg.age = 100
sg.age = 1000 // Uncaught Error Error: 1000 must <= 120
```

### Parameter decorator

```typescript
// parameter decorator
import "reflect-metadata";

const requiredMetadataKey = Symbol("required");

// pnpm i reflect-metadata --save
function required(target: Object, propertyKey: string | symbol, parameterIndex: number) {
    let existingRequiredParameters: number[] = Reflect.getOwnMetadata(requiredMetadataKey, target, propertyKey) || [];
    existingRequiredParameters.push(parameterIndex);
    Reflect.defineMetadata(requiredMetadataKey, existingRequiredParameters, target, propertyKey);
}
function validate(target: any, propertyName: string, descriptor: PropertyDescriptor) {
    let method = descriptor.value;
    descriptor.value = function () {
        let requiredParameters: number[] = Reflect.getOwnMetadata(requiredMetadataKey, target, propertyName);
        if (requiredParameters) {
            for (let parameterIndex of requiredParameters) {
                if (parameterIndex >= arguments.length || arguments[parameterIndex] === undefined) {
                    throw new Error("Missing required argument.");
                }
            }
        }

        return method.apply(this, arguments);
    }
}
class Greeter {
    greeting: string;

    constructor(message: string) {
        this.greeting = message;
    }

    @validate
    greet(@required name: string) {
        return "Hello " + name + ", " + this.greeting;
    }
}

let g = new Greeter("msg")
console.log(g.greet(""));
```