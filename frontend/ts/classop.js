"use strict";
// class test
class Person {
    constructor(n, a, i) {
        this.info = "";
        this.name = n;
        this.age = a;
        if (i)
            this.info = i;
    }
    toString() {
        return `[name]: ${this.name}, age: ${this.age}, info: ${this.info}`;
    }
}
class Student extends Person {
    constructor(n, a, i, s) {
        super(n, a, i);
        this.score = s;
    }
    toString() {
        return super.toString() + `, score: ${this.score}`;
    }
}
console.log(new Student("li", 0, "123321", 99)); // Student {info: '123321', name: 'li', age: 0, score: 99}
console.log(new Student("li", 0, "123321", 99).toString()); // [name]: li, age: 0, info: 123321, score: 99
// abstract test
class AbHouse {
    constructor(price, area) {
        this.price = price;
        this.area = area;
    }
}
class MyHouse extends AbHouse {
    constructor(price, area, name) {
        super(price, area);
        this.name = name;
    }
    getPrice() {
        return this.area * this.price;
    }
}
console.log(new MyHouse(0, 0, "li").getPrice());
//class interface
class mydog {
    constructor(name, age) {
        this.name = name;
        this.age = age;
        this.play = () => {
            console.log(`play with ${this.name}`);
        };
    }
}
new mydog("mydog", 0).play();
// object interface
let mycat = {
    name: "mycat",
    age: 0,
    play: () => {
        console.log(`play with ${mycat.name}`);
    }
};
mycat.play();
const iSum = (a, b) => a + b;
