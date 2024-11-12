"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
const myToString = (target) => {
    target.prototype.toString = () => {
        return JSON.stringify(target);
    };
    Object.seal(target.prototype);
};
let dClass = class dClass {
    constructor(key, value) {
        this.key = key;
        this.value = value;
    }
};
dClass = __decorate([
    myToString
], dClass);
let dc = new dClass("key", "value");
console.log(dc);
const instTimer = (target) => {
    return class extends target {
        constructor(...args) {
            super(...args);
            this.getCreatedTime = () => this.createdTime;
            this.createdTime = new Date();
        }
    };
};
let d1Class = class d1Class {
    constructor(key, value) {
        this.key = key;
        this.value = value;
    }
};
d1Class = __decorate([
    myToString,
    instTimer
], d1Class);
console.log(new d1Class("key", "value")); // d1Class {key: 'key', value: 'value', getCreatedTime: ƒ, createdTime: Tue Nov 12 2024 15:06:53 GMT+0800 (中国標準時)}
console.log(new d1Class("key", "value").getCreatedTime()); // Tue Nov 12 2024 15:06:53 GMT+0800
console.log(new d1Class("key", "value").getCreatedTime().getTime()); // 1731395213964
console.log(new d1Class("key", "value").getCreatedTime().getTime()); // 1731395213965
// decorator factory
const introWraper = (arg) => {
    return (target) => {
        target.prototype.toString = function () {
            return `[key]: ${this.key}, [value]: ${this.value}, [warper arg]: ${arg}`;
        };
    };
};
let dfClass = class dfClass {
    constructor(key, value) {
        this.key = key;
        this.value = value;
    }
};
dfClass = __decorate([
    introWraper(1)
], dfClass);
console.log(new dfClass("value", "key").toString()); // [key]: value, [value]: key, [warper arg]: 1
// attibute decorator
const aWarpper = (target, propertyKey) => {
    console.log(`[aWarpper] [target]: ${target}, [propertyKey]: ${propertyKey.toString()}`);
};
class aClass {
    constructor(key) {
        this.key = key;
        // this.value = value
    }
}
aClass.value = "aClass value";
__decorate([
    aWarpper // [aWarpper] [target]: [object Object], [propertyKey]: key
], aClass.prototype, "key", void 0);
__decorate([
    aWarpper /** static attibute will pass class as target
    [aWarpper] [target]: class aClass {
        constructor(key) {
            this.key = key
            // this.value = value
        }
    }, [propertyKey]: value
     */
], aClass, "value", void 0);
// refresh decorator
const state = (target, propertyKey) => {
    let key = `__${propertyKey.toString()}`;
    Object.defineProperty(target, propertyKey, {
        get() {
            console.log("get");
            return this[key];
        },
        set(newValue) {
            console.log(`${propertyKey.toString()} set a new value: ${newValue}`);
            this[key] = newValue;
        },
        enumerable: true,
        configurable: true
    });
};
class sClass {
    constructor(key, value) {
        this.value = "aClass value";
        this.key = key;
        this.value = value;
    }
}
__decorate([
    state
], sClass.prototype, "key", void 0);
let sc = new sClass("v", "k"); // key set a new value: v
console.log(sc.key); // get\nv
sc.key = "value"; // key set a new value: value
// method decorator
const Timer = (target, propertyKey, descriptor) => {
    const func = descriptor.value;
    descriptor.value = function (...args) {
        let start = Date.now();
        let result = func.call(this, ...args);
        let end = Date.now();
        console.log(`use time: ${end - start}ms`);
        return result;
    };
};
class AC {
    static call(start, end, step) {
        if (step == null)
            step = 1;
        let acc = 1;
        for (let i = start; i < end; i++) {
            acc += i ** 2;
        }
        return acc;
    }
}
__decorate([
    Timer
], AC, "call", null);
AC.call(1, 100000); // use time: 2ms
// setter / getter decorator
const numRange = (min, max) => {
    return (target, propertyKey, descriptor) => {
        const setter = descriptor.set;
        descriptor.set = function (v) {
            if (min != null && v < min)
                throw new Error(`${v} must >= ${min}`);
            if (max != null && v > max)
                throw new Error(`${v} must <= ${max}`);
            if (setter)
                setter.call(this, v);
        };
    };
};
class sgClass {
    constructor(a) { this._age = a; }
    set age(v) {
        this._age = v;
    }
    get age() { return this._age; }
}
__decorate([
    numRange(0, 120)
], sgClass.prototype, "age", null);
let sg = new sgClass(10);
new sgClass(100);
new sgClass(1000);
sg.age = 100;
// sg.age = 1000 // Uncaught Error Error: 1000 must <= 120
// parameter decorator
require("reflect-metadata");
const requiredMetadataKey = Symbol("required");
// pnpm i reflect-metadata --save
function required(target, propertyKey, parameterIndex) {
    let existingRequiredParameters = Reflect.getOwnMetadata(requiredMetadataKey, target, propertyKey) || [];
    existingRequiredParameters.push(parameterIndex);
    Reflect.defineMetadata(requiredMetadataKey, existingRequiredParameters, target, propertyKey);
}
function validate(target, propertyName, descriptor) {
    let method = descriptor.value;
    descriptor.value = function () {
        let requiredParameters = Reflect.getOwnMetadata(requiredMetadataKey, target, propertyName);
        if (requiredParameters) {
            for (let parameterIndex of requiredParameters) {
                if (parameterIndex >= arguments.length || arguments[parameterIndex] === undefined) {
                    throw new Error("Missing required argument.");
                }
            }
        }
        return method.apply(this, arguments);
    };
}
class Greeter {
    constructor(message) {
        this.greeting = message;
    }
    greet(name) {
        return "Hello " + name + ", " + this.greeting;
    }
}
__decorate([
    validate,
    __param(0, required)
], Greeter.prototype, "greet", null);
let g = new Greeter("msg");
console.log(g.greet(""));
