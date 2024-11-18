import { createRouter, createWebHistory } from "vue-router"
import Route1 from "../views/Route1.vue"
import Route2 from "../views/Route2.vue"
import Route3 from "../views/Route3.vue"
import Route1_1 from "../views/Route1-1.vue"
import Route1_2 from "../views/Route1-2.vue"

export default createRouter(
    {
        history: createWebHistory(),
        routes: [
            {
                name: "route1",
                path: '/r1',
                component: Route1,
                children:[
                    {
                        path:"r1-1",
                        component: Route1_1
                    },
                    {
                        name: "r1_2",
                        path:"r1-2/:username?/:password?",
                        component: Route1_2,
                        
                    }
                ]
            },
            {
                name: "route2",
                path: '/r2',
                component: Route2,
            },
            {
                name: "route3",
                path: '/r3',
                component: Route3,
            }
        ],
    }
)