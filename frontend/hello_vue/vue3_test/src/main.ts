import App1 from './App.vue'
import { createApp } from 'vue'
import router from './router'

const app = createApp(App1)
app.use(router)
app.mount("#app")