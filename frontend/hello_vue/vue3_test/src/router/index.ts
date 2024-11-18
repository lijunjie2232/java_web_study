import { createRouter, createWebHashHistory } from "vue-router"
import Route1 from "../views/Route1.vue"
import Route2 from "../views/Route2.vue"
import Route3 from "../views/Route3.vue"

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