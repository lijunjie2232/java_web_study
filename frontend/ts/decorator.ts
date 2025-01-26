const myToString: ClassDecorator = (target: Function) => {
    target.prototype.toString = () => {
        return JSON.stringify(target)
    }
    Object.seal(target.prototype)
}

@myToString
class dClass {
    constructor(public key: string, public value: string) { }
}

let dc = new dClass("key", "value")
console.log(dc)

// replace class
type TimeWrapper = new (...args: any[]) => {}
const instTimer = <T extends TimeWrapper>(target: T) => {
    return class extends target {
        createdTime: Date
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
    getCreatedTime: () => Date
}
console.log(new d1Class("key", "value")) // d1Class {key: 'key', value: 'value', getCreatedTime: ƒ, createdTime: Tue Nov 12 2024 15:06:53 GMT+0800 (中国標準時)}
console.log(new d1Class("key", "value").getCreatedTime()) // Tue Nov 12 2024 15:06:53 GMT+0800
console.log(new d1Class("key", "value").getCreatedTime().getTime()) // 1731395213964
console.log(new d1Class("key", "value").getCreatedTime().getTime()) // 1731395213965

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

// attibute decorator
const aWarpper: PropertyDecorator = (target: object, propertyKey) => {
    console.log(`[aWarpper] [target]: ${target}, [propertyKey]: ${propertyKey.toString()}`)
}

class aClass {
    @aWarpper // [aWarpper] [target]: [object Object], [propertyKey]: key
    public key: string
    @aWarpper/** static attibute will pass class as target
    [aWarpper] [target]: class aClass {
        constructor(key) {
            this.key = key
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

// refresh decorator
const state: PropertyDecorator = (target: object, propertyKey) => {
    let key = `__${propertyKey.toString()}`
    Object.defineProperty(
        target,
        propertyKey,
        {
            get() {
                console.log("get")
                return this[key]
            },
            set(newValue) {
                console.log(`${propertyKey.toString()} set a new value: ${newValue}`)
                this[key] = newValue
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
            acc += i ** 2
        }
        return acc
    }
}

AC.call(1, 100000) // use time: 2ms


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
// sg.age = 1000 // Uncaught Error Error: 1000 must <= 120

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

