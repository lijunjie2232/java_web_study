import App1 from './App.vue'
import { createApp } from 'vue'
import router from './router'
import { createPinia } from 'pinia'
import emitter from 'utils/emitter.ts'

const app = createApp(App1)
app.use(router)
const pinia = createPinia()
app.use(pinia)
app.mount("#app")