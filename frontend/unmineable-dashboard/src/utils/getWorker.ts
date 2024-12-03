import axios from "axios"
const WADDRESS = import.meta.env.VITE_WADDRESS
const BASE_URL = import.meta.env.VITE_BASE_URL
const USERAGENT = import.meta.env.VITE_USERAGENT

export const getWorkers = (waddress: string = WADDRESS) => {
    return axios({
        url: `${BASE_URL}/account/${waddress}/workers`,
        method: "get",
        headers: {
            'User-Agent': USERAGENT
        }
    })
}