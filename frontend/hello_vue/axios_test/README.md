# Axios
- [Axios](#axios)
  - [Axios](#axios-1)
    - [a simple request](#a-simple-request)
    - [axios result](#axios-result)


## Axios

### a simple request
```typescript
const getArticle = async () => {
  Axios({
    method: "get",
    url: `http://jsonplaceholder.typicode.com/posts/${Math.round(Math.random() * 10)}`,
  }).then(
    ({ data }) => {
      article.value = data
    }
  )
}
```

### axios result
1. data: Object (json data form server)
2. config: Object
3. headers: Object
4. request: XMLHttpRequest
5. status: number (`200`)
6. statusTesxt: string (`"OK"`)