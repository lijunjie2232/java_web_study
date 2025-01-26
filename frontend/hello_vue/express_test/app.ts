import express from 'express'

const app = express()

app.post(
    "/api/test",
    (req, resp) => {
        console.log(req)
        console.log(resp)
        resp.setHeader("Content-Type", "application/json")
        resp.setHeader("Access-Control-Allow-Origin", "*")
        resp.send(
            JSON.stringify({ msg: "ok" })
        )
    }
)

app.get(
    "/api/random",
    (req, resp) => {
        console.log(req)
        console.log(resp)
        resp.setHeader("Content-Type", "application/json")
        resp.setHeader("Access-Control-Allow-Origin", "*")
        resp.send(
            JSON.stringify({ num: `${Math.random()}` })
        )
    }
)

app.listen(
    10037, () => {
        console.log("express start")

    }
)