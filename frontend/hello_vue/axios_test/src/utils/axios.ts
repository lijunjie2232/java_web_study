import axios from "axios"

const instance = axios.create(
    {
        baseURL: "http://jsonplaceholder.typicode.com/",
        timeout: 200000
    }
)

// handle request
instance.interceptors.request.use(
    // before send request
    (config) => {
        console.log("filter: before request")

        console.log(config)

        config.headers.Accept = "application/json, text/plain, */*"
        return config
    },
    // if throw error
    (err) => {
        console.log("filter: error")
        console.log(err)

        return Promise.reject(err)
    },
)

// handle response
instance.interceptors.response.use(
    // before send response
    (resp) => {
        console.log("filter: before response")

        console.log(resp)

        return resp
    },
    // if throw error
    (err) => {
        console.log("filter: after response")
        console.log(err)

        return Promise.reject(err)
    },
)

export default instance

