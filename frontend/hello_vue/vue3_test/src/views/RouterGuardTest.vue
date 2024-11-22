<template>
    <div>
        <button @click="login">login</button>
        <button @click="logout">logout</button>
        <div id="routerlogin"></div>
        <Teleport to="body">
            <div class="splash-bg" v-show="isSplash"></div>
            <div class="splash" v-show="isSplash">
                <h3>Login</h3>
                <button @click="login">login</button>
            </div>
        </Teleport>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router';

const isSplash = ref(false)
const router = useRouter()
const login = () => {
    window.sessionStorage.setItem("username", "admin")
    isSplash.value = false
    router.push('/r1')
}
const logout = () => {
    window.sessionStorage.removeItem("username")
    isSplash.value = true
}
</script>
<style scoped>
.splash-bg {
    position: fixed;
    left: 0%;
    top: 0%;
    width: 100%;
    height: 100%;
    background-color: #636e727F;
}

.splash {
    width: 300px;
    height: 200px;
    border: 2px #636e72 solid;
    box-shadow: 10px 5px 5px #636e72;
    border-radius: 10px;
    text-align: center;
    position: fixed;
    left: 50%;
    margin-left: -150px;
    top: 50%;
    margin-top: -100px;
    background-color: #FFFFFFFF;
}
</style>