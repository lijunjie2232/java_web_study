# AJAX (Asynchronous JavaScript and XML)

- [AJAX (Asynchronous JavaScript and XML)](#ajax-asynchronous-javascript-and-xml)
  - [JavaScript AJAX](#javascript-ajax)
    - [GET via XHR](#get-via-xhr)
    - [POST via XHR](#post-via-xhr)
    - [timeout](#timeout)
    - [catch error](#catch-error)
    - [cancel request](#cancel-request)
  - [jQuery AJAX](#jquery-ajax)
    - [$.get](#get)
    - [$.post](#post)
    - [$.ajax](#ajax)
      - [x-www-form-urlencoded data in body](#x-www-form-urlencoded-data-in-body)
      - [json raw data in body](#json-raw-data-in-body)
      - [form-data data in body (including file sending)](#form-data-data-in-body-including-file-sending)
  - [Axios](#axios)
    - [get](#get-1)
    - [post](#post-1)
    - [request](#request)
      - [get](#get-2)
      - [x-www-form-urlencoded data in body](#x-www-form-urlencoded-data-in-body-1)
      - [json raw data in body](#json-raw-data-in-body-1)
      - [form-data data in body (including file sending)](#form-data-data-in-body-including-file-sending-1)
  - [Fetch](#fetch)
    - [get](#get-3)
    - [post](#post-2)
      - [x-www-form-urlencoded data in body](#x-www-form-urlencoded-data-in-body-2)
      - [json raw data in body](#json-raw-data-in-body-2)
      - [form-data data in body (including file sending)](#form-data-data-in-body-including-file-sending-2)
- [JSONP](#jsonp)
  - [JQuery jsonp](#jquery-jsonp)


## JavaScript AJAX
### GET via XHR
1. create xhr: `const xhr = new XMLHttpRequest();`
2. set recall function: 
```javascript
// method 1: on ready
xhr.onreadystatechange = () => {
if (xhr.readyState !== 4) return;
if (xhr.status >= 200 && xhr.status < 300 ){
    console.log(xhr.responseText);
    }
};
// method 2: add listener
xhr.addEventListener("readystatechange", () => {
    if (xhr.readyState === 4) {
        if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
            console.log(
                JSON.parse(xhr.response)
            )
        } else {
            console.log(xhr.response)
        }
    }
});
```
| Ajax状态码 | 状态                                                             |
| ---------- | ---------------------------------------------------------------- |
| 0          | （**未初始化**）未启动                                           |
| 1          | （**启动**）已经调用 open()，但尚未调用 send()                   |
| 2          | （**发送**）发送状态，已经调用 send()，但尚未接收到响应          |
| 3          | （**接收**）已经接收到部分响应数据                               |
| 4          | （**完成**）已经接收到全部响应数据，而且已经可以在浏览器中使用了 |
3. open url: `xhr.open('GET','/user/login', true);`, arguments:
   1. request method
   2. url
   3. is request async
4. other options: `xhr.setRequestHeader("User-Agent", "..")` or `xhr.withCredentials = true` and so on
5. send request: `xhr.send(null)`, argument:
   1. data to send in request body

- `xhr.setRequestHeader` must after `xhr.open`
```javascript
// WARNING: For GET requests, body is set to null by browsers.

var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.onreadystatechange = () => {
    if (xhr.readyState === 4) {
        if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
            resolve(
                JSON.parse(xhr.response)
            )
        } else {
            reject(new Error('Response error'))
        }
    }
}

xhr.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

xhr.open("GET", "https://blog.csdn.net//phoenix/web/v2/skill-tree-info?articleId=134156433");

xhr.send(); 
```

### POST via XHR
```javascript
const xhrPost = () => {
    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    xhr.onreadystatechange = () => {
        if (xhr.readyState === 4) {
            if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                console.log(
                    JSON.parse(xhr.response)
                )
            } else {
                console.log(xhr.response)
            }
        }
    }

    xhr.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

    // json raw
    var data = JSON.stringify({
        "k1": "v1"
    });
    xhr.setRequestHeader("Content-Type", "application/json");

    // form data
    var data = new FormData();
    data.append("k1", "v1");
    data.append("myfile", fileInput.files[0]);

    // x-www-form-urlencoded
    var data = "k1=v1&k2=v2";
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.open("POST", "/ajax/api", true);
    xhr.send(data);
}
```

### timeout
- `xhr.timeout = 3000`
- if timeout, request will be cancled in client

### catch error
```javascript
xhr.onerror = (err)=>{
    console.error(err)
}
```
### cancel request
`xhr.abort()`

## jQuery AJAX
### $.get
```javascript
function jpost() {
    $.get(
        'http://127.0.0.1:10037/api/test',
        {
            a: 123,
            b: "321"
        },// request body form urlencoded
        (resp) => {
            console.log(resp)
        },
        "json"//response body is json
    )
}
```
### $.post
```javascript
function jpost() {
    $.post(
        'http://127.0.0.1:10037/api/test',
        {
            a: 123,
            b: "321"
        },// request body form urlencoded
        (resp) => {
            console.log(resp)
        },
        "json"//response body is json
    )
}
```

### $.ajax
#### x-www-form-urlencoded data in body 
```javascript
$.ajax(
    {
        url: "http://127.0.0.1:10037/api/test",
        type: "POST",
        data: {
            a: 123,
            b: "321"
        },
        dataType: "json",
        success: (resp) => {
            console.log(resp)
        },
        error: (err) => {
            console.log(err)
        }
    }
)
```
#### json raw data in body
```javascript
$.ajax(
    {
        url: "http://127.0.0.1:10037/api/test",
        type: "POST",
        data: JSON.stringify({
            a: 123,
            b: "321"
        }),
        contentType: false, // disable for json sending
        crossDomain: true,
        dataType: "json",
        success: (resp) => {
            console.log(resp)
        },
        error: (err) => {
            console.log(err)
        }
    }
)
```
#### form-data data in body (including file sending)
```javascript
const formdata = new FormData()
formdata.append("a", "123")
formdata.append("b", "321")
// send file
// formdata.append("file", event.currentTarget.files[0])

$.ajax(
    {
        url: "http://127.0.0.1:10037/api/test",
        type: "POST",
        data: formdata,
        processData: false, // disable for formdata sending
        contentType: false, // disable for formdata sending
        crossDomain: true,
        dataType: "json",
        success: (resp) => {
            console.log(resp)
        },
        error: (err) => {
            console.log(err)
        }
    }
)
```

## Axios
### get
### post
### request
#### get
```javascript
let config = {
  method: 'get',
  maxBodyLength: Infinity,
  url: '127.0.0.1:10037/api/test?k=123321',
  headers: { }
}
axios.request(config)
.then((resp) => {
  console.log(resp);
})
.catch((err) => {
  console.log(err);
})
```
#### x-www-form-urlencoded data in body 
```javascript
let data = qs.stringify({
  'k': '123321' 
})

let config = {
  method: 'post',
  maxBodyLength: Infinity,
  url: '127.0.0.1:10037/api/test',
  headers: { 
    'Content-Type': 'application/x-www-form-urlencoded'
  },
  data : data
}
axios.request(config).then(...)
```
#### json raw data in body
```javascript
let data = JSON.stringify({
  "k": "133321"
})

let config = {
  method: 'post',
  maxBodyLength: Infinity,
  url: '127.0.0.1:10037/api/test',
  headers: { 
    'Content-Type': 'application/json'
  },
  data : data
}
axios.request(config).then(...)
```
#### form-data data in body (including file sending)
```javascript
let data = new FormData()
data.append('v', '123321')

let config = {
  method: 'post',
  maxBodyLength: Infinity,
  url: '127.0.0.1:10037/api/test',
  headers: { 
    ...data.getHeaders()
  },
  data : data
}
axios.request(config).then(...)
```

## Fetch
### get
```javascript
const requestOptions = {
  method: "GET",
  redirect: "follow"
};

fetch("127.0.0.1:10037/api/test?k=123321", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));
```

### post

#### x-www-form-urlencoded data in body 
```javascript
const myHeaders = new Headers();
myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

const urlencoded = new URLSearchParams();
urlencoded.append("k", "123321");

const requestOptions = {
  method: "POST",
  headers: myHeaders,
  body: urlencoded,
  redirect: "follow"
};

fetch("127.0.0.1:10037/api/test", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));
```
#### json raw data in body
```javascript
const myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");

const raw = JSON.stringify({
  "k": "133321"
});

const requestOptions = {
  method: "POST",
  headers: myHeaders,
  body: raw,
  redirect: "follow"
};

fetch("127.0.0.1:10037/api/test", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));
```
#### form-data data in body (including file sending)
```javascript
const formdata = new FormData();
formdata.append("v", "123321");

const requestOptions = {
  method: "POST",
  body: formdata,
  redirect: "follow"
};

fetch("127.0.0.1:10037/api/test", requestOptions)
  .then((response) => response.text())
  .then((result) => console.log(result))
  .catch((error) => console.error(error));
```

# JSONP
- jsonp could only work with get method
- backend return a javascript function code piece in string as response body, frontend request by `<script src="...">` and excute the code piece after received response

```javascript
// jsonp
const script = document.createElement('script')
script.src = "request url"
document.body.appendChild(script)
// handle function
const handle = (data)=>{...}
```
```javascript
// express backend
let data = ......

app.get(
    "/xxx/xx",
    (req, resp) => {
        resp.send(
            `handle(${JSON.stringify(data)})`
        )
    }
)
```
## JQuery jsonp
- add parameter: `callback=?` in url, `?` will automaticly replaced
- get `request.query.callback` and call it in response body
```javascript
// jsonp
const script = document.createElement('script')
script.src = "request url"
document.body.appendChild(script)
// handle function
const handle = (data)=>{...}
```
```javascript
// express backend
let data = ......

app.get(
    "/xxx/xx",
    (req, resp) => {
        let callback = req.query.callback
        resp.send(
            `${callback}(${JSON.stringify(data)})`
        )
    }
)
```