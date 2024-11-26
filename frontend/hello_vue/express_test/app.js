"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const app = (0, express_1.default)();
app.post("/api/test", (req, resp) => {
    console.log(req);
    console.log(resp);
    resp.setHeader("Content-Type", "application/json");
    resp.send(JSON.stringify({ msg: "ok" }));
});
app.get("/api/random", (req, resp) => {
    console.log(req);
    console.log(resp);
    resp.setHeader("Content-Type", "application/json");
    resp.send(JSON.stringify({ num: `${Math.random()}` }));
});
app.listen(8001, () => {
    console.log("express start");
});
