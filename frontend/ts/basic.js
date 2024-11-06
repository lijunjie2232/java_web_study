"use strict";
const person = {
    name: "li"
};
console.log(person.name);
let a;
a = "hello";
const sum = (a, b) => {
    return a + b;
};
console.log(sum(1, 2));
// symbol test
const s1 = Symbol();
const s2 = s1;
console.log(s1 === s2);
const s3 = Symbol.for("a");
console.log(Symbol.keyFor(s3));
// const s4 = Symbol.for("a")
// console.log(s3 === s4)
let sname = Symbol();
let sobj = {
    [sname]: "a"
};
console.log(sobj); // { Symbol(): 'a' }
// declare test
let aPerson = {
    name: "li",
    sudo: true
};
console.log(aPerson);
const newSum = (a, b) => {
    return a + b;
};
console.log(newSum(1, 2));
let arr1 = [];
let arr2 = [];
arr1.push("123");
console.log(arr1);
// tuple test
let tup = ["123"];
tup.push(123);
tup.push("123");
tup.push("123");
tup.push("123");
console.log(tup);
// enum test
var Season;
(function (Season) {
    Season[Season["haru"] = 0] = "haru";
    Season[Season["natsu"] = 1] = "natsu";
    Season[Season["aki"] = 2] = "aki";
    Season[Season["fuyu"] = 3] = "fuyu";
})(Season || (Season = {}));
console.log(Season); // {0: 'haru', 1: 'natsu', 2: 'aki', 3: 'fuyu', haru: 0, natsu: 1, aki: 2, fuyu: 3}
console.log(Season[0]); // haru
console.log(Season["haru"]); // 0
console.log(Season.aki); // 2
let season1 = 0;
let season2 = Season.aki;
const num2str = (s) => {
    return Season[s];
};
console.log(season1); // 0
console.log(season2); // 2
console.log(num2str(Season.aki)); // aki
console.log(num2str(Season["aki"])); // aki
console.log(num2str(0)); // haru
var NumSeason;
(function (NumSeason) {
    NumSeason[NumSeason["haru"] = 0] = "haru";
    NumSeason[NumSeason["natsu"] = 1] = "natsu";
    NumSeason[NumSeason["aki"] = 2] = "aki";
    NumSeason[NumSeason["fuyu"] = 3] = "fuyu";
})(NumSeason || (NumSeason = {}));
console.log(NumSeason); // {0: 'haru', 1: 'natsu', 2: 'aki', 3: 'fuyu', haru: 0, natsu: 1, aki: 2, fuyu: 3}
var StrSeason;
(function (StrSeason) {
    StrSeason["haru"] = "haru";
    StrSeason["natsu"] = "natsu";
    StrSeason["aki"] = "aki";
    StrSeason["fuyu"] = "fuyu";
})(StrSeason || (StrSeason = {}));
console.log(StrSeason); // {haru: 'haru', natsu: 'natsu', aki: 'aki', fuyu: 'fuyu'}
