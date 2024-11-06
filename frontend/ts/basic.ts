const person = {
    name: "li"
}
console.log(person.name)

let a: string
a = "hello"

const sum = (a: number, b: number): number => {
    return a + b
}
console.log(sum(1, 2))

// symbol test
const s1: unique symbol = Symbol()
const s2: typeof s1 = s1
console.log(s1 === s2)

const s3 = Symbol.for("a")
console.log(Symbol.keyFor(s3))

// const s4 = Symbol.for("a")
// console.log(s3 === s4)

let sname = Symbol()
let sobj = {
    [sname]: "a"
}
console.log(sobj) // { Symbol(): 'a' }

// declare test
let aPerson: { name: string, age?: number, [_: string]: any } = {
    name: "li",
    sudo: true
}
console.log(aPerson)

const newSum = (a: number, b: number): number => {
    return a + b
}
console.log(newSum(1, 2));


let arr1: string[] = []
let arr2: Array<string> = []

arr1.push("123")

console.log(arr1);

// tuple test
let tup: [string, number?, ...string[]] = ["123"]
tup.push(123)
tup.push("123")
tup.push("123")
tup.push("123")
console.log(tup)

// enum test
enum Season {
    haru, natsu, aki, fuyu
}

console.log(Season) // {0: 'haru', 1: 'natsu', 2: 'aki', 3: 'fuyu', haru: 0, natsu: 1, aki: 2, fuyu: 3}
console.log(Season[0]) // haru
console.log(Season["haru"]) // 0
console.log(Season.aki) // 2

let season1: Season = 0
let season2: Season = Season.aki
const num2str = (s: Season): string => {
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