<template>
    <button @click="isSplash = !isSplash">{{ isSplash ? "hide" : "splash" }}</button>
    <Teleport to="#testdiv">
        <div class="splash-bg" v-show="isSplash"></div>
        <div class="splash" v-show="isSplash">
            <h3>Splash Window</h3>
            <button @click="isSplash = !isSplash">close</button>
        </div>
    </Teleport>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const isSplash = ref(false)
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