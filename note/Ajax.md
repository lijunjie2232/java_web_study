# AJAX (Asynchronous JavaScript and XML)

- [AJAX (Asynchronous JavaScript and XML)](#ajax-asynchronous-javascript-and-xml)
  - [JavaScript AJAX](#javascript-ajax)
    - [GET via XHR](#get-via-xhr)
    - [POST via XHR](#post-via-xhr)


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
