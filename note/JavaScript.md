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
- [JS Event](#js-event)
  - [mouse event](#mouse-event)
  - [keyboard event](#keyboard-event)
  - [键盘事件](#键盘事件)
  - [Full event api](#full-event-api)


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
# JS Event
- event of mouse / keyboard / form
## mouse event
| 属性                                                         | 描述                                   | DOM  |
| :----------------------------------------------------------- | :------------------------------------- | :--- |
| [onclick](https://www.runoob.com/jsref/event-onclick.html)   | 当用户点击某个对象时调用的事件句柄。   | 2    |
| [oncontextmenu](https://www.runoob.com/jsref/event-oncontextmenu.html) | 在用户点击鼠标右键打开上下文菜单时触发 |      |
| [ondblclick](https://www.runoob.com/jsref/event-ondblclick.html) | 当用户双击某个对象时调用的事件句柄。   | 2    |
| [onmousedown](https://www.runoob.com/jsref/event-onmousedown.html) | 鼠标按钮被按下。                       | 2    |
| [onmouseenter](https://www.runoob.com/jsref/event-onmouseenter.html) | 当鼠标指针移动到元素上时触发。         | 2    |
| [onmouseleave](https://www.runoob.com/jsref/event-onmouseleave.html) | 当鼠标指针移出元素时触发               | 2    |
| [onmousemove](https://www.runoob.com/jsref/event-onmousemove.html) | 鼠标被移动。                           | 2    |
| [onmouseover](https://www.runoob.com/jsref/event-onmouseover.html) | 鼠标移到某元素之上。                   | 2    |
| [onmouseout](https://www.runoob.com/jsref/event-onmouseout.html) | 鼠标从某元素移开。                     | 2    |
| [onmouseup](https://www.runoob.com/jsref/event-onmouseup.html) | 鼠标按键被松开。                       | 2    |

## keyboard event

## 键盘事件

| 属性                                                         | 描述                       | DOM  |
| :----------------------------------------------------------- | :------------------------- | :--- |
| [onkeydown](https://www.runoob.com/jsref/event-onkeydown.html) | 某个键盘按键被按下。       | 2    |
| [onkeypress](https://www.runoob.com/jsref/event-onkeypress.html) | 某个键盘按键被按下并松开。 | 2    |
| [onkeyup](https://www.runoob.com/jsref/event-onkeyup.html)   | 某个键盘按键被松开。       | 2    |

```javascript
function keydown(event){
//表示键盘监听所触发的事件，同时传递传递参数event
    document.write(event.keyCode);//keyCode表示键盘编码
}
//键盘监听，注意：在非ie浏览器和非ie内核的浏览器
//参数1：表示事件，keydown:键盘向下按；参数2：表示要触发的事件
document.addEventListener("keydown",keydown);
```

## [Full event api](https://www.runoob.com/jsref/dom-obj-event.html)

