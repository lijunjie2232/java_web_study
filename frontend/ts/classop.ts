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
    score: number | undefined

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


// abstract test
abstract class AbHouse {
    constructor(public price: number, public area: number) { }
    abstract getPrice(): number
}

class MyHouse extends AbHouse {
    name: string
    constructor(price: number, area: number, name: string) {
        super(price, area)
        this.name = name
    }
    getPrice(): number {
        return this.area * this.price
    }
}
console.log(new MyHouse(0, 0, "li").getPrice())

// interface test
interface Animal{
    age:number
}
// interface extends
interface IPet extends Animal {
    name: string
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