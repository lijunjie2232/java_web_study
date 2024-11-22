import App1 from './App.vue'
import { createApp } from 'vue'
import router from './router'
import { createPinia } from 'pinia'
import VueApiTest from './components/VueApiTest.vue'
import axios from 'axios'

axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*'
axios.defaults.headers.common['Access-Control-Allow-Headers'] = '*'

const app = createApp(App1)
app.use(router)
const pinia = createPinia()
app.use(pinia)

app.component('VueApiTest', VueApiTest)
app.config.globalProperties.appVersion = "0.0.1"
app.directive('testcmd', (elem, { value }) => {
    elem.style.color = 'blue'
    elem.value = value
})

app.mount("#app")