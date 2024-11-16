- [Basic Data Operation](#basic-data-operation)
- ["==" and "==="](#-and-)
- [for each](#for-each)
- [function state](#function-state)
- [JS object](#js-object)
  - [JSON in JS](#json-in-js)
  - [JS Array](#js-array)
    - [api of Array](#api-of-array)
  - [JS Boolean](#js-boolean)
  - [JS Date](#js-date)
  - [JS Math](#js-math)
    - [Math 对象属性](#math-对象属性)
    - [Math 对象方法](#math-对象方法)
  - [JS Number](#js-number)
    - [Number 对象属性](#number-对象属性)
    - [Number 对象方法](#number-对象方法)
  - [JS String](#js-string)
    - [JS String Template](#js-string-template)
  - [JS RegExp](#js-regexp)
- [JS Event](#js-event)
  - [mouse event](#mouse-event)
  - [keyboard event](#keyboard-event)
  - [键盘事件](#键盘事件)
  - [Full events api](#full-events-api)
- [splash information](#splash-information)
- [BOM](#bom)
  - [window](#window)
  - [window API](#window-api)
    - [history](#history)
    - [location](#location)
    - [sessionStorage \&\& localStorage](#sessionstorage--localstorage)
    - [more attribute of window](#more-attribute-of-window)
- [DOM](#dom)
  - [document API](#document-api)


# Basic Data Operation
```javascript
10/2 -> 5
10/4 -> 2.5
10/0 -> Infinity
10%0 -> NaN
```
# "==" and "==="
- "==": if data type is not the same, transform both into number
- "===": if data type is not the same then return false
```javascript
1 == 1      -> true
1 == '1'    -> true
1 == true   -> true

1 === 1      -> true
1 === '1'    -> false
1 === true   -> false
```

# for each
- for each in javascript will get a increasing index of iterable object
```javascript
var arr = ["java", "python", "c++"]
for (var index in arr){
    console.log(arr[index])
}
```

# function state
- use **keyword** `arguments` to get all arguments of current function in array type
```javascript
// method 1:(traditional)
function sum(a,b){
    return a+b
}
// method 2:
var sum = function(a,b){
    return a+b
}
// method 3: (es)
var sum = (a,b)=>{
    return a+b
}

// function as argument:
var invoke = (func, ...args)=>{
    return func(...args)
}
console.log(invoke(sum, 1, 2))
```

# JS object
```javascript
// method 1
// create object
var myObject = new Object()
// object attribute
myObject.name = "li"
myObject.age = 0
console.log(myObject.age)
// object method
myObject.myfunc = ()=>{
    console.log("myobject function")
}
myObject.myfunc()

// method 2
var myObject = {
    name: "li",
    age: 0,
    myfunc: ()=>{
        console.log("myobject function")
    }
}
```

## JSON in JS
- `JSON` is an inner object of JavaScript
- `JSON object` dose not exist, it is a common object in js, operation is the same as common js object
- use `JSON.parse(string)` to transform json string into object
- use `JSON.stringify(object)` to transform object into json object
```javascript
j1 = JSON.parse('{"name":"li", "age": 0}')
console.log(j1)
console.log(JSON.stringify(j1))
```

## JS Array
- create: `var arr = new Array("java", "python", "c++")`
- add：
  - `var newLength = arr.push("c")` = `arr[arr.length] = "java"`
  - `var newLength = arr.unshift("java")`
- modify: `arr[0] = "java"`
- delete: `var last = arr.pop()`
- !!! `reverse` and `sort` could change original array, so do like this:`return [...myArray].reverse()`
### api of Array
- includes: `arr.includes("c++")`
- indexOf & lastIndexOf: get the first & last index of an item
- isArray: `Array.isArray(arr)`
- join: `arr.join(',')`, both `"` and `'` are ok
- keys: return an Iterator
- map: `arr.map((value, index, array) => {})`
- reverse: `arr_r = arr.reverse()`, **must assign to a variable**, `arr` and others are ok
- reduce & reduceRight: `console.log(arr.reduce((previousItem, currentItem, currentIndex, array) => {return previousItem + "[:reduce:]" + currentItem}))`, (from left to right & from right to left) to calc item, **must return value after calculation**
- shift & pop: remove and return first & last item
- unshift & push: add item to first & last and return new length of array
- of: `var arr = Array.of(1,2,3,4,5)` -> [1,2,3,4,5]
- slice: `var subArr = arr.slice(1,2)`, return sub array
- splice: `arr.splice(startIndex, deleteCount, addItem1, addItem2, ...)`, it changes origin variable directly
  1. deletes deleteCount items starts at startIndex
  2. returns these items in a new Array
  3. insert any addItem? at startIndex if addItem? exists in arguments
  4. number of addItem? has no relationship with deleteCount, when deleteCount=0, only insert new items at startIndex
  5. `startIndex < 0` is ok, for example, `arr = [ 1, 2, 3, 4 ];arr.splice(-1, 0, 7);`, the arr will be `[ 1, 2, 3, 7, 4 ]`
- some: `arr.some((value, index, array)=>{(return boolean)}`, **must retuen boolean**
- filter: `var newArr = arr.filter((item) => {return item !== "c++"}`, not change origin variable arr
```javascript
var arr = new Array()
console.log(arr.length)
arr[arr.length] = "java"
arr[arr.length] = "python"
arr[arr.length] = "c++"
console.log(arr)
console.log("arr.length: ", arr.length)
arr.length = 5
console.log("arr.length = 5: ", arr.length)
console.log(arr)
for (var i in arr) {
    console.log(i, ": ", arr[i])
}
var arr = new Array("java", "python", "c++")
console.log(arr.length)
console.log(arr)
console.log("includes c++:", arr.includes("c++"))
console.log("index of c++:", arr.indexOf("c++"))
console.log("index of c:", arr.indexOf("c"))

console.log(arr.join(','))

console.log(Array.isArray(arr))

console.log(arr.keys())

arr.map((value, index, array) => {
    console.log("value: ", value, ", index: ", index, ", array:", array)
})

console.log(arr.reverse())
arr_r = arr.reverse()
console.log(arr_r);
console.log(arr)

console.log(arr.push("c"))
console.log(arr)
console.log(arr.pop())
console.log(arr)

console.log(arr.shift())
console.log(arr)
console.log(arr.unshift("java"))
console.log(arr)

console.log(arr.slice(1, 2))

console.log(arr.some((value, index, array) => {
    console.log("value: ", value, ", index: ", index, ", array:", array)
    return false
}))


console.log(arr.reduce((previousItem, currentItem, currentIndex, array) => {
    console.log("currentIndex: ", currentIndex)
    console.log("previousItem:", previousItem)
    return previousItem + "[:reduce:]" + currentItem
}))

console.log(arr.reduce((previousItem, currentItem, currentIndex, array) => { return previousItem + "[:reduce:]" + currentItem }))

console.log(arr.filter((item) => {return item !== "c++"}))

console.log(arr)
console.log(arr.splice(1, 1, "py", "c"))
console.log(arr)
console.log(arr.splice(1, 0, "py", "c"))
console.log(arr)
console.log(arr.splice(-1, 0, "typescript"))
console.log(arr)
```

## JS Boolean
```javascript
var bool = new Boolean(0)
var boolValue = bool.valueOf()
```

## JS Date

| 方法                                                                               | 描述                                                                                                       |
| :--------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------- |
| [getDate()](https://www.runoob.com/jsref/jsref-getdate.html)                       | 从 Date 对象返回一个月中的某一天 (1 ~ 31)。                                                                |
| [getDay()](https://www.runoob.com/jsref/jsref-getday.html)                         | 从 Date 对象返回一周中的某一天 (0 ~ 6)。                                                                   |
| [getFullYear()](https://www.runoob.com/jsref/jsref-getfullyear.html)               | 从 Date 对象以四位数字返回年份。                                                                           |
| [getHours()](https://www.runoob.com/jsref/jsref-gethours.html)                     | 返回 Date 对象的小时 (0 ~ 23)。                                                                            |
| [getMilliseconds()](https://www.runoob.com/jsref/jsref-getmilliseconds.html)       | 返回 Date 对象的毫秒(0 ~ 999)。                                                                            |
| [getMinutes()](https://www.runoob.com/jsref/jsref-getminutes.html)                 | 返回 Date 对象的分钟 (0 ~ 59)。                                                                            |
| [getMonth()](https://www.runoob.com/jsref/jsref-getmonth.html)                     | 从 Date 对象返回月份 (0 ~ 11)。                                                                            |
| [getSeconds()](https://www.runoob.com/jsref/jsref-getseconds.html)                 | 返回 Date 对象的秒数 (0 ~ 59)。                                                                            |
| [getTime()](https://www.runoob.com/jsref/jsref-gettime.html)                       | 返回 1970 年 1 月 1 日至今的毫秒数。                                                                       |
| [getTimezoneOffset()](https://www.runoob.com/jsref/jsref-gettimezoneoffset.html)   | 返回本地时间与格林威治标准时间 (GMT) 的分钟差。                                                            |
| [getUTCDate()](https://www.runoob.com/jsref/jsref-getutcdate.html)                 | 根据世界时从 Date 对象返回月中的一天 (1 ~ 31)。                                                            |
| [getUTCDay()](https://www.runoob.com/jsref/jsref-getutcday.html)                   | 根据世界时从 Date 对象返回周中的一天 (0 ~ 6)。                                                             |
| [getUTCFullYear()](https://www.runoob.com/jsref/jsref-getutcfullyear.html)         | 根据世界时从 Date 对象返回四位数的年份。                                                                   |
| [getUTCHours()](https://www.runoob.com/jsref/jsref-getutchours.html)               | 根据世界时返回 Date 对象的小时 (0 ~ 23)。                                                                  |
| [getUTCMilliseconds()](https://www.runoob.com/jsref/jsref-getutcmilliseconds.html) | 根据世界时返回 Date 对象的毫秒(0 ~ 999)。                                                                  |
| [getUTCMinutes()](https://www.runoob.com/jsref/jsref-getutcminutes.html)           | 根据世界时返回 Date 对象的分钟 (0 ~ 59)。                                                                  |
| [getUTCMonth()](https://www.runoob.com/jsref/jsref-getutcmonth.html)               | 根据世界时从 Date 对象返回月份 (0 ~ 11)。                                                                  |
| [getUTCSeconds()](https://www.runoob.com/jsref/jsref-getutcseconds.html)           | 根据世界时返回 Date 对象的秒钟 (0 ~ 59)。                                                                  |
| getYear()                                                                          | 已废弃。 请使用 getFullYear() 方法代替。                                                                   |
| [parse()](https://www.runoob.com/jsref/jsref-parse.html)                           | 返回1970年1月1日午夜到指定日期（字符串）的毫秒数。                                                         |
| [setDate()](https://www.runoob.com/jsref/jsref-setdate.html)                       | 设置 Date 对象中月的某一天 (1 ~ 31)。                                                                      |
| [setFullYear()](https://www.runoob.com/jsref/jsref-setfullyear.html)               | 设置 Date 对象中的年份（四位数字）。                                                                       |
| [setHours()](https://www.runoob.com/jsref/jsref-sethours.html)                     | 设置 Date 对象中的小时 (0 ~ 23)。                                                                          |
| [setMilliseconds()](https://www.runoob.com/jsref/jsref-setmilliseconds.html)       | 设置 Date 对象中的毫秒 (0 ~ 999)。                                                                         |
| [setMinutes()](https://www.runoob.com/jsref/jsref-setminutes.html)                 | 设置 Date 对象中的分钟 (0 ~ 59)。                                                                          |
| [setMonth()](https://www.runoob.com/jsref/jsref-setmonth.html)                     | 设置 Date 对象中月份 (0 ~ 11)。                                                                            |
| [setSeconds()](https://www.runoob.com/jsref/jsref-setseconds.html)                 | 设置 Date 对象中的秒钟 (0 ~ 59)。                                                                          |
| [setTime()](https://www.runoob.com/jsref/jsref-settime.html)                       | setTime() 方法以毫秒设置 Date 对象。                                                                       |
| [setUTCDate()](https://www.runoob.com/jsref/jsref-setutcdate.html)                 | 根据世界时设置 Date 对象中月份的一天 (1 ~ 31)。                                                            |
| [setUTCFullYear()](https://www.runoob.com/jsref/jsref-setutcfullyear.html)         | 根据世界时设置 Date 对象中的年份（四位数字）。                                                             |
| [setUTCHours()](https://www.runoob.com/jsref/jsref-setutchours.html)               | 根据世界时设置 Date 对象中的小时 (0 ~ 23)。                                                                |
| [setUTCMilliseconds()](https://www.runoob.com/jsref/jsref-setutcmilliseconds.html) | 根据世界时设置 Date 对象中的毫秒 (0 ~ 999)。                                                               |
| [setUTCMinutes()](https://www.runoob.com/jsref/jsref-setutcminutes.html)           | 根据世界时设置 Date 对象中的分钟 (0 ~ 59)。                                                                |
| [setUTCMonth()](https://www.runoob.com/jsref/jsref-setutcmonth.html)               | 根据世界时设置 Date 对象中的月份 (0 ~ 11)。                                                                |
| [setUTCSeconds()](https://www.runoob.com/jsref/jsref-setutcseconds.html)           | setUTCSeconds() 方法用于根据世界时 (UTC) 设置指定时间的秒字段。                                            |
| setYear()                                                                          | 已废弃。请使用 setFullYear() 方法代替。                                                                    |
| [toDateString()](https://www.runoob.com/jsref/jsref-todatestring.html)             | 把 Date 对象的日期部分转换为字符串。                                                                       |
| toGMTString()                                                                      | 已废弃。请使用 toUTCString() 方法代替。                                                                    |
| [toISOString()](https://www.runoob.com/jsref/jsref-toisostring.html)               | 使用 ISO 标准返回字符串的日期格式。                                                                        |
| [toJSON()](https://www.runoob.com/jsref/jsref-tojson.html)                         | 以 JSON 数据格式返回日期字符串。                                                                           |
| [toLocaleDateString()](https://www.runoob.com/jsref/jsref-tolocaledatestring.html) | 根据本地时间格式，把 Date 对象的日期部分转换为字符串。                                                     |
| [toLocaleTimeString()](https://www.runoob.com/jsref/jsref-tolocaletimestring.html) | 根据本地时间格式，把 Date 对象的时间部分转换为字符串。                                                     |
| [toLocaleString()](https://www.runoob.com/jsref/jsref-tolocalestring.html)         | 根据本地时间格式，把 Date 对象转换为字符串。                                                               |
| [toString()](https://www.runoob.com/jsref/jsref-tostring-date.html)                | 把 Date 对象转换为字符串。                                                                                 |
| [toTimeString()](https://www.runoob.com/jsref/jsref-totimestring.html)             | 把 Date 对象的时间部分转换为字符串。                                                                       |
| [toUTCString()](https://www.runoob.com/jsref/jsref-toutcstring.html)               | 根据世界时，把 Date 对象转换为字符串。实例：`var today = new Date(); var UTCstring = today.toUTCString();` |
| [UTC()](https://www.runoob.com/jsref/jsref-utc.html)                               | 根据世界时返回 1970 年 1 月 1 日 到指定日期的毫秒数。                                                      |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-date.html)                  | 返回 Date 对象的原始值。                                                                                   |

- Date string format:
  1. month/day/year: `11/04/2024`
  2. year/month/day: `2024/11/04`
  3. year-month-day: `2024-11-04`
  4. day month year: `04 Nov 2024`
- Default(UTC) standard Date string: `Mon Nov 04 2024 14:06:27 GMT+0900`

## JS Math
- Most of functions in JavaScript Math object are the same as Java
### Math 对象属性

| 属性                                                       | 描述                                                    |
| :--------------------------------------------------------- | :------------------------------------------------------ |
| [E](https://www.runoob.com/jsref/jsref-e.html)             | 返回算术常量 e，即自然对数的底数（约等于2.718）。       |
| [LN2](https://www.runoob.com/jsref/jsref-ln2.html)         | 返回 2 的自然对数（约等于0.693）。                      |
| [LN10](https://www.runoob.com/jsref/jsref-ln10.html)       | 返回 10 的自然对数（约等于2.302）。                     |
| [LOG2E](https://www.runoob.com/jsref/jsref-log2e.html)     | 返回以 2 为底的 e 的对数（约等于 1.4426950408889634）。 |
| [LOG10E](https://www.runoob.com/jsref/jsref-log10e.html)   | 返回以 10 为底的 e 的对数（约等于0.434）。              |
| [PI](https://www.runoob.com/jsref/jsref-pi.html)           | 返回圆周率（约等于3.14159）。                           |
| [SQRT1_2](https://www.runoob.com/jsref/jsref-sqrt1-2.html) | 返回 2 的平方根的倒数（约等于 0.707）。                 |
| [SQRT2](https://www.runoob.com/jsref/jsref-sqrt2.html)     | 返回 2 的平方根（约等于 1.414）。                       |

### Math 对象方法

| 方法                                                            | 描述                                                          |
| :-------------------------------------------------------------- | :------------------------------------------------------------ |
| [abs(x)](https://www.runoob.com/jsref/jsref-abs.html)           | 返回 x 的绝对值。                                             |
| [acos(x)](https://www.runoob.com/jsref/jsref-acos.html)         | 返回 x 的反余弦值。                                           |
| [asin(x)](https://www.runoob.com/jsref/jsref-asin.html)         | 返回 x 的反正弦值。                                           |
| [atan(x)](https://www.runoob.com/jsref/jsref-atan.html)         | 以介于 -PI/2 与 PI/2 弧度之间的数值来返回 x 的反正切值。      |
| [atan2(y,x)](https://www.runoob.com/jsref/jsref-atan2.html)     | 返回从 x 轴到点 (x,y) 的角度（介于 -PI/2 与 PI/2 弧度之间）。 |
| [ceil(x)](https://www.runoob.com/jsref/jsref-ceil.html)         | 对数进行上舍入。                                              |
| [cos(x)](https://www.runoob.com/jsref/jsref-cos.html)           | 返回数的余弦。                                                |
| [exp(x)](https://www.runoob.com/jsref/jsref-exp.html)           | 返回 Ex 的指数。                                              |
| [floor(x)](https://www.runoob.com/jsref/jsref-floor.html)       | 对 x 进行下舍入。                                             |
| [log(x)](https://www.runoob.com/jsref/jsref-log.html)           | 返回数的自然对数（底为e）。                                   |
| [max(x,y,z,...,n)](https://www.runoob.com/jsref/jsref-max.html) | 返回 x,y,z,...,n 中的最高值。                                 |
| [min(x,y,z,...,n)](https://www.runoob.com/jsref/jsref-min.html) | 返回 x,y,z,...,n中的最低值。                                  |
| [pow(x,y)](https://www.runoob.com/jsref/jsref-pow.html)         | 返回 x 的 y 次幂。                                            |
| [random()](https://www.runoob.com/jsref/jsref-random.html)      | 返回 0 ~ 1 之间的随机数。                                     |
| [round(x)](https://www.runoob.com/jsref/jsref-round.html)       | 四舍五入。                                                    |
| [sin(x)](https://www.runoob.com/jsref/jsref-sin.html)           | 返回数的正弦。                                                |
| [sqrt(x)](https://www.runoob.com/jsref/jsref-sqrt.html)         | 返回数的平方根。                                              |
| [tan(x)](https://www.runoob.com/jsref/jsref-tan.html)           | 返回角的正切。                                                |
| [tanh(x)](https://www.runoob.com/jsref/jsref-tanh.html)         | 返回一个数的双曲正切函数值。                                  |
| [trunc(x)](https://www.runoob.com/jsref/jsref-trunc.html)       | 将数字的小数部分去掉，只保留整数部分。                        |

## JS Number

### Number 对象属性

| 属性                                                                           | 描述                                   |
| :----------------------------------------------------------------------------- | :------------------------------------- |
| [constructor](https://www.runoob.com/jsref/jsref-constructor-number.html)      | 返回对创建此对象的 Number 函数的引用。 |
| [MAX_VALUE](https://www.runoob.com/jsref/jsref-max-value.html)                 | 可表示的最大的数。                     |
| [MIN_VALUE](https://www.runoob.com/jsref/jsref-min-value.html)                 | 可表示的最小的数。                     |
| [NEGATIVE_INFINITY](https://www.runoob.com/jsref/jsref-negative-infinity.html) | 负无穷大，溢出时返回该值。             |
| [NaN](https://www.runoob.com/jsref/jsref-number-nan.html)                      | 非数字值。                             |
| [POSITIVE_INFINITY](https://www.runoob.com/jsref/jsref-positive-infinity.html) | 正无穷大，溢出时返回该值。             |
| [prototype](https://www.runoob.com/jsref/jsref-prototype-num.html)             | 允许您可以向对象添加属性和方法。       |

### Number 对象方法

| 方法                                                                                              | 描述                                                 |
| :------------------------------------------------------------------------------------------------ | :--------------------------------------------------- |
| [isFinite](https://www.runoob.com/jsref/jsref-isfinite-number.html)                               | 检测指定参数是否为无穷大。                           |
| [isInteger](https://www.runoob.com/jsref/jsref-isinteger-number.html)                             | 检测指定参数是否为整数。                             |
| [isNaN](https://www.runoob.com/jsref/jsref-isnan-number.html)                                     | 检测指定参数是否为 NaN。                             |
| [isSafeInteger](https://www.runoob.com/jsref/jsref-issafeInteger-number.html)                     | 检测指定参数是否为安全整数。                         |
| [toExponential(x)](https://www.runoob.com/jsref/jsref-toexponential.html)                         | 把对象的值转换为指数计数法。                         |
| [toFixed(x)](https://www.runoob.com/jsref/jsref-tofixed.html)                                     | 把数字转换为字符串，结果的小数点后有指定位数的数字。 |
| [toLocaleString(locales, options)](https://www.runoob.com/jsref/jsref-tolocalestring-number.html) | 返回数字在特定语言环境下的表示字符串。               |
| [toPrecision(x)](https://www.runoob.com/jsref/jsref-toprecision.html)                             | 把数字格式化为指定的长度。                           |
| [toString()](https://www.runoob.com/jsref/jsref-tostring-number.html)                             | 把数字转换为字符串，使用指定的基数。                 |
| [valueOf()](https://www.runoob.com/jsref/jsref-valueof-number.html)                               | 返回一个 Number 对象的基本数字值。                   |
| parseInt                                                                                          | 将字符串转换为Int                                    |
| parseFloat                                                                                        | 将字符串转换为Float                                  |

## JS String
- Most of functions in JavaScript String object are the same as Java

### JS String Template
- template str must states after all used variable has been stated
```javascript
const hello = ()=>{
    return "str temp"
}
var name = "li"
var age = 0

let str =`
Name:${name}
age:${age}
function:${hello()}
sanmu: ${age > 30 ? 'over 30': 'under 30'}
`
console.log(str)
```
## JS RegExp
- create: `var pattern = new RegExp("[0-9]+", "g")` or `var pattern = /[0-9]+/g`
- test: `pattern.test("123321")`=>`true`, return boolean
- match: `"123abc321".match(pattern)`=>`["123", "321"]`, return result Array
- replace: `"abc123abc".replace(pattern, "321")`=>`"abc321abc"`

# JS Event
- event of mouse / keyboard / form
## mouse event
| 属性                                                                   | 描述                                   | DOM  |
| :--------------------------------------------------------------------- | :------------------------------------- | :--- |
| [onclick](https://www.runoob.com/jsref/event-onclick.html)             | 当用户点击某个对象时调用的事件句柄。   | 2    |
| [oncontextmenu](https://www.runoob.com/jsref/event-oncontextmenu.html) | 在用户点击鼠标右键打开上下文菜单时触发 |      |
| [ondblclick](https://www.runoob.com/jsref/event-ondblclick.html)       | 当用户双击某个对象时调用的事件句柄。   | 2    |
| [onmousedown](https://www.runoob.com/jsref/event-onmousedown.html)     | 鼠标按钮被按下。                       | 2    |
| [onmouseenter](https://www.runoob.com/jsref/event-onmouseenter.html)   | 当鼠标指针移动到元素上时触发。         | 2    |
| [onmouseleave](https://www.runoob.com/jsref/event-onmouseleave.html)   | 当鼠标指针移出元素时触发               | 2    |
| [onmousemove](https://www.runoob.com/jsref/event-onmousemove.html)     | 鼠标被移动。                           | 2    |
| [onmouseover](https://www.runoob.com/jsref/event-onmouseover.html)     | 鼠标移到某元素之上。                   | 2    |
| [onmouseout](https://www.runoob.com/jsref/event-onmouseout.html)       | 鼠标从某元素移开。                     | 2    |
| [onmouseup](https://www.runoob.com/jsref/event-onmouseup.html)         | 鼠标按键被松开。                       | 2    |

## keyboard event

## 键盘事件

| 属性                                                             | 描述                       | DOM  |
| :--------------------------------------------------------------- | :------------------------- | :--- |
| [onkeydown](https://www.runoob.com/jsref/event-onkeydown.html)   | 某个键盘按键被按下。       | 2    |
| [onkeypress](https://www.runoob.com/jsref/event-onkeypress.html) | 某个键盘按键被按下并松开。 | 2    |
| [onkeyup](https://www.runoob.com/jsref/event-onkeyup.html)       | 某个键盘按键被松开。       | 2    |

```javascript
function keydown(event){
//表示键盘监听所触发的事件，同时传递传递参数event
    document.write(event.keyCode);//keyCode表示键盘编码
}
//键盘监听，注意：在非ie浏览器和非ie内核的浏览器
//参数1：表示事件，keydown:键盘向下按；参数2：表示要触发的事件
document.addEventListener("keydown",keydown);
```

## [Full events api](https://www.runoob.com/jsref/dom-obj-event.html)

# splash information
1. `alert()` no return
2. `prompt()` input text return, cancle -> return `null`
3. `confirm()` boolean return

# BOM
## window
- history
- location
- codument
- console
- screen
- navigator
- sessionStorage
- localStorage

## window API
| 方法                                                                           | 描述                                                                                                    |
| :----------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------ |
| [alert()](https://www.runoob.com/jsref/met-win-alert.html)                     | 显示带有一段消息和一个确认按钮的警告框。                                                                |
| [atob()](https://www.runoob.com/jsref/met-win-atob.html)                       | 解码一个 base-64 编码的字符串。                                                                         |
| [btoa()](https://www.runoob.com/jsref/met-win-btoa.html)                       | 创建一个 base-64 编码的字符串。                                                                         |
| [blur()](https://www.runoob.com/jsref/met-win-blur.html)                       | 把键盘焦点从顶层窗口移开。                                                                              |
| [clearInterval()](https://www.runoob.com/jsref/met-win-clearinterval.html)     | 取消由 setInterval() 设置的 timeout。                                                                   |
| [clearTimeout()](https://www.runoob.com/jsref/met-win-cleartimeout.html)       | 取消由 setTimeout() 方法设置的 timeout。                                                                |
| [close()](https://www.runoob.com/jsref/met-win-close.html)                     | 关闭浏览器窗口。                                                                                        |
| [confirm()](https://www.runoob.com/jsref/met-win-confirm.html)                 | 显示带有一段消息以及确认按钮和取消按钮的对话框。                                                        |
| [createPopup()](https://www.runoob.com/jsref/met-win-createpopup.html)         | 创建一个 pop-up 窗口。                                                                                  |
| [focus()](https://www.runoob.com/jsref/met-win-focus.html)                     | 把键盘焦点给予一个窗口。                                                                                |
| [getSelection](https://www.runoob.com/jsref/met-win-getselection.html)()       | 返回一个 Selection 对象，表示用户选择的文本范围或光标的当前位置。                                       |
| [getComputedStyle()](https://www.runoob.com/jsref/jsref-getcomputedstyle.html) | 获取指定元素的 CSS 样式。                                                                               |
| [matchMedia()](https://www.runoob.com/jsref/met-win-matchmedia.html)           | 该方法用来检查 media query 语句，它返回一个 MediaQueryList对象。                                        |
| [moveBy()](https://www.runoob.com/jsref/met-win-moveby.html)                   | 可相对窗口的当前坐标把它移动指定的像素。                                                                |
| [moveTo()](https://www.runoob.com/jsref/met-win-moveto.html)                   | 把窗口的左上角移动到一个指定的坐标。                                                                    |
| [open()](https://www.runoob.com/jsref/met-win-open.html)                       | 打开一个新的浏览器窗口或查找一个已命名的窗口。                                                          |
| [print()](https://www.runoob.com/jsref/met-win-print.html)                     | 打印当前窗口的内容。                                                                                    |
| [prompt()](https://www.runoob.com/jsref/met-win-prompt.html)                   | 显示可提示用户输入的对话框。                                                                            |
| [resizeBy()](https://www.runoob.com/jsref/met-win-resizeby.html)               | 按照指定的像素调整窗口的大小。                                                                          |
| [resizeTo()](https://www.runoob.com/jsref/met-win-resizeto.html)               | 把窗口的大小调整到指定的宽度和高度。                                                                    |
| scroll()                                                                       | 已废弃。 该方法已经使用了 [scrollTo()](https://www.runoob.com/jsref/met-win-scrollto.html) 方法来替代。 |
| [scrollBy()](https://www.runoob.com/jsref/met-win-scrollby.html)               | 按照指定的像素值来滚动内容。                                                                            |
| [scrollTo()](https://www.runoob.com/jsref/met-win-scrollto.html)               | 把内容滚动到指定的坐标。                                                                                |
| [setInterval()](https://www.runoob.com/jsref/met-win-setinterval.html)         | 按照指定的周期（以毫秒计）来调用函数或计算表达式。                                                      |
| [setTimeout()](https://www.runoob.com/jsref/met-win-settimeout.html)           | 在指定的毫秒数后调用函数或计算表达式。                                                                  |
| [stop()](https://www.runoob.com/jsref/met-win-stop.html)                       | 停止页面载入。                                                                                          |
| [postMessage()](https://www.runoob.com/jsref/met-win-postmessage.html)         | 安全地实现跨源通信。                                                                                    |

### history
- `back()`
- `forward()`
- `go(n)`: forward for n page, `n < 0` is ok

### location
- `location.href="www.baidu.com"`

### sessionStorage && localStorage
sessionStorage will cleared after browser closing
- `setItem("SESSION", "123321")`
- `getItem("SESSION")`
- `removeItem("SESSION")`

### more attribute of window

| 属性                                                                        | 描述                                                                                                     |
| :-------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------- |
| [closed](https://www.runoob.com/jsref/prop-win-closed.html)                 | 返回窗口是否已被关闭。                                                                                   |
| [defaultStatus](https://www.runoob.com/jsref/prop-win-defaultstatus.html)   | 设置或返回窗口状态栏中的默认文本。                                                                       |
| [document](https://www.runoob.com/jsref/dom-obj-document.html)              | 对 Document 对象的只读引用。(请参阅[对象](https://www.runoob.com/jsref/dom-obj-document.html))           |
| [frames](https://www.runoob.com/jsref/prop-win-frames.html)                 | 返回窗口中所有命名的框架。该集合是 Window 对象的数组，每个 Window 对象在窗口中含有一个框架。             |
| [history](https://www.runoob.com/jsref/obj-history.html)                    | 对 History 对象的只读引用。请参数 [History 对象](https://www.runoob.com/jsref/obj-history.html)。        |
| [innerHeight](https://www.runoob.com/jsref/prop-win-innerheight.html)       | 返回窗口的文档显示区的高度。                                                                             |
| [innerWidth](https://www.runoob.com/jsref/prop-win-innerheight.html)        | 返回窗口的文档显示区的宽度。                                                                             |
| [localStorage](https://www.runoob.com/jsref/prop-win-localstorage.html)     | 在浏览器中存储 key/value 对。没有过期时间。                                                              |
| [length](https://www.runoob.com/jsref/prop-win-length.html)                 | 设置或返回窗口中的框架数量。                                                                             |
| [location](https://www.runoob.com/jsref/obj-location.html)                  | 用于窗口或框架的 Location 对象。请参阅 [Location 对象](https://www.runoob.com/jsref/obj-location.html)。 |
| [name](https://www.runoob.com/jsref/prop-win-name.html)                     | 设置或返回窗口的名称。                                                                                   |
| [navigator](https://www.runoob.com/jsref/obj-navigator.html)                | 对 Navigator 对象的只读引用。请参数 [Navigator 对象](https://www.runoob.com/jsref/obj-navigator.html)。  |
| [opener](https://www.runoob.com/jsref/prop-win-opener.html)                 | 返回对创建此窗口的窗口的引用。                                                                           |
| [outerHeight](https://www.runoob.com/jsref/prop-win-outerheight.html)       | 返回窗口的外部高度，包含工具条与滚动条。                                                                 |
| [outerWidth](https://www.runoob.com/jsref/prop-win-outerheight.html)        | 返回窗口的外部宽度，包含工具条与滚动条。                                                                 |
| [pageXOffset](https://www.runoob.com/jsref/prop-win-pagexoffset.html)       | 设置或返回当前页面相对于窗口显示区左上角的 X 位置。                                                      |
| [pageYOffset](https://www.runoob.com/jsref/prop-win-pagexoffset.html)       | 设置或返回当前页面相对于窗口显示区左上角的 Y 位置。                                                      |
| [parent](https://www.runoob.com/jsref/prop-win-parent.html)                 | 返回父窗口。                                                                                             |
| [screen](https://www.runoob.com/jsref/obj-screen.html)                      | 对 Screen 对象的只读引用。请参数 [Screen 对象](https://www.runoob.com/jsref/obj-screen.html)。           |
| [screenLeft](https://www.runoob.com/jsref/prop-win-screenleft.html)         | 返回相对于屏幕窗口的x坐标                                                                                |
| [screenTop](https://www.runoob.com/jsref/prop-win-screenleft.html)          | 返回相对于屏幕窗口的y坐标                                                                                |
| [screenX](https://www.runoob.com/jsref/prop-win-screenx.html)               | 返回相对于屏幕窗口的x坐标                                                                                |
| [sessionStorage](https://www.runoob.com/jsref/prop-win-sessionstorage.html) | 在浏览器中存储 key/value 对。 在关闭窗口或标签页之后将会删除这些数据。                                   |
| [screenY](https://www.runoob.com/jsref/prop-win-screenx.html)               | 返回相对于屏幕窗口的y坐标                                                                                |
| [self](https://www.runoob.com/jsref/prop-win-self.html)                     | 返回对当前窗口的引用。等价于 Window 属性。                                                               |
| [status](https://www.runoob.com/jsref/prop-win-status.html)                 | 设置窗口状态栏的文本。                                                                                   |
| [top](https://www.runoob.com/jsref/prop-win-top.html)                       | 返回最顶层的父窗口。                                                                                     |

# DOM
## document API
- get element directly
  - `getElementById("username")`
  - `getElementByName("username")`
  - `getElementByTag("body")`
  - `getElementByClass("btn btn-primary")`
- get element indirectly
  - `div.children`: get child nodes
  - `div.firstElementChild / div.lastElementChild`
  - `div.parentElement`
  - `div.previousElementSibling / div.nextElementSibling`
- modify node
  - `document.createTextNode("text")`
  - `document.createElement("input")`
  - `div.appendChild(input1)`
  - `div.insertBefore(input2, div.children[0])`
  - `div.remove()`: delete div

| 属性 / 方法                                                                                                | 描述                                                                                            |
| :--------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------- |
| [document.activeElement](https://www.runoob.com/jsref/prop-document-activeelement.html)                    | 返回当前获取焦点元素                                                                            |
| [document.addEventListener()](https://www.runoob.com/jsref/met-document-addeventlistener.html)             | 向文档添加句柄                                                                                  |
| [document.adoptNode(node)](https://www.runoob.com/jsref/met-document-adoptnode.html)                       | 从另外一个文档返回 adapded 节点到当前文档。                                                     |
| [document.anchors](https://www.runoob.com/jsref/coll-doc-anchors.html)                                     | 返回对文档中所有 Anchor 对象的引用。                                                            |
| document.applets                                                                                           | 返回对文档中所有 Applet 对象的引用。**注意:** HTML5 已不支持 <applet> 元素。                    |
| [document.baseURI](https://www.runoob.com/jsref/prop-doc-baseuri.html)                                     | 返回文档的绝对基础 URI                                                                          |
| [document.body](https://www.runoob.com/jsref/prop-doc-body.html)                                           | 返回文档的body元素                                                                              |
| [document.close()](https://www.runoob.com/jsref/met-doc-close.html)                                        | 关闭用 document.open() 方法打开的输出流，并显示选定的数据。                                     |
| [document.cookie](https://www.runoob.com/jsref/prop-doc-cookie.html)                                       | 设置或返回与当前文档有关的所有 cookie。                                                         |
| [document.createAttribute()](https://www.runoob.com/jsref/met-document-createattribute.html)               | 创建一个属性节点                                                                                |
| [document.createComment()](https://www.runoob.com/jsref/met-document-createcomment.html)                   | createComment() 方法可创建注释节点。                                                            |
| [document.createDocumentFragment()](https://www.runoob.com/jsref/met-document-createdocumentfragment.html) | 创建空的 DocumentFragment 对象，并返回此对象。                                                  |
| [document.createElement()](https://www.runoob.com/jsref/met-document-createelement.html)                   | 创建元素节点。                                                                                  |
| [document.createTextNode()](https://www.runoob.com/jsref/met-document-createtextnode.html)                 | 创建文本节点。                                                                                  |
| [document.doctype](https://www.runoob.com/jsref/prop-document-doctype.html)                                | 返回与文档相关的文档类型声明 (DTD)。                                                            |
| [document.documentElement](https://www.runoob.com/jsref/prop-document-documentelement.html)                | 返回文档的根节点                                                                                |
| [document.documentMode](https://www.runoob.com/jsref/prop-doc-documentmode.html)                           | 返回用于通过浏览器渲染文档的模式                                                                |
| [document.documentURI](https://www.runoob.com/jsref/prop-document-documenturi.html)                        | 设置或返回文档的位置                                                                            |
| [document.domain](https://www.runoob.com/jsref/prop-doc-domain.html)                                       | 返回当前文档的域名。                                                                            |
| document.domConfig                                                                                         | **已废弃**。返回 normalizeDocument() 被调用时所使用的配置。                                     |
| [document.embeds](https://www.runoob.com/jsref/coll-doc-embeds.html)                                       | 返回文档中所有嵌入的内容（embed）集合                                                           |
| [document.forms](https://www.runoob.com/jsref/coll-doc-forms.html)                                         | 返回对文档中所有 Form 对象引用。                                                                |
| [document.getElementsByClassName()](https://www.runoob.com/jsref/met-document-getelementsbyclassname.html) | 返回文档中所有指定类名的元素集合，作为 NodeList 对象。                                          |
| [document.getElementById()](https://www.runoob.com/jsref/met-document-getelementbyid.html)                 | 返回对拥有指定 id 的第一个对象的引用。                                                          |
| [document.getElementsByName()](https://www.runoob.com/jsref/met-doc-getelementsbyname.html)                | 返回带有指定名称的对象集合。                                                                    |
| [document.getElementsByTagName()](https://www.runoob.com/jsref/met-document-getelementsbytagname.html)     | 返回带有指定标签名的对象集合。                                                                  |
| [document.images](https://www.runoob.com/jsref/coll-doc-images.html)                                       | 返回对文档中所有 Image 对象引用。                                                               |
| [document.implementation](https://www.runoob.com/jsref/prop-document-implementation.html)                  | 返回处理该文档的 DOMImplementation 对象。                                                       |
| [document.importNode()](https://www.runoob.com/jsref/met-document-importnode.html)                         | 把一个节点从另一个文档复制到该文档以便应用。                                                    |
| [document.inputEncoding](https://www.runoob.com/jsref/prop-document-inputencoding.html)                    | 返回用于文档的编码方式（在解析时）。                                                            |
| [document.lastModified](https://www.runoob.com/jsref/prop-doc-lastmodified.html)                           | 返回文档被最后修改的日期和时间。                                                                |
| [document.links](https://www.runoob.com/jsref/coll-doc-links.html)                                         | 返回对文档中所有 Area 和 Link 对象引用。                                                        |
| [document.normalize()](https://www.runoob.com/jsref/met-document-normalize.html)                           | 删除空文本节点，并连接相邻节点                                                                  |
| [document.normalizeDocument()](https://www.runoob.com/jsref/met-document-normalizedocument.html)           | 删除空文本节点，并连接相邻节点的                                                                |
| [document.open()](https://www.runoob.com/jsref/met-doc-open.html)                                          | 打开一个流，以收集来自任何 document.write() 或 document.writeln() 方法的输出。                  |
| [document.querySelector()](https://www.runoob.com/jsref/met-document-queryselector.html)                   | 返回文档中匹配指定的CSS选择器的第一元素                                                         |
| [document.querySelectorAll()](https://www.runoob.com/jsref/met-document-queryselectorall.html)             | document.querySelectorAll() 是 HTML5中引入的新方法，返回文档中匹配的CSS选择器的所有元素节点列表 |
| [document.readyState](https://www.runoob.com/jsref/prop-doc-readystate.html)                               | 返回文档状态 (载入中……)                                                                         |
| [document.referrer](https://www.runoob.com/jsref/prop-doc-referrer.html)                                   | 返回载入当前文档的文档的 URL。                                                                  |
| [document.removeEventListener()](https://www.runoob.com/jsref/met-document-removeeventlistener.html)       | 移除文档中的事件句柄(由 addEventListener() 方法添加)                                            |
| [document.renameNode()](https://www.runoob.com/jsref/met-document-renamenode.html)                         | 重命名元素或者属性节点。                                                                        |
| [document.scripts](https://www.runoob.com/jsref/coll-doc-scripts.html)                                     | 返回页面中所有脚本的集合。                                                                      |
| [document.strictErrorChecking](https://www.runoob.com/jsref/prop-document-stricterrorchecking.html)        | 设置或返回是否强制进行错误检查。                                                                |
| [document.title](https://www.runoob.com/jsref/prop-doc-title.html)                                         | 返回当前文档的标题。                                                                            |
| [document.URL](https://www.runoob.com/jsref/prop-doc-url.html)                                             | 返回文档完整的URL                                                                               |
| [document.write()](https://www.runoob.com/jsref/met-doc-write.html)                                        | 向文档写 HTML 表达式 或 JavaScript 代码。                                                       |
| [document.writeln()](https://www.runoob.com/jsref/met-doc-writeln.html)                                    | 等同于 write() 方法，不同的是在每个表达式之后写一个换行符。                                     |

