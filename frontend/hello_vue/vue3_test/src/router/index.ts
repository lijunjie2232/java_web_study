import { createRouter, createWebHashHistory } from "vue-router"
import Route1 from "../components/Route1.vue"
import Route2 from "../components/Route2.vue"
import Route3 from "../components/Route3.vue"

export default createRouter(
    {
        history: createWebHashHistory(),
        routes: [
            {
                path: '/r1',
                component: Route1,
            },
            {
                path: '/r2',
                component: Route2,
            },
            {
                path: '/r3',
                component: Route3,
            }
        ],
    }
)