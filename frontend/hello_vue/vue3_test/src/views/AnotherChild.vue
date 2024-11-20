<template>
    <div>
        <p>me: {{ childData }}</p>
        <button @click="clickFunc">click me</button>
    </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import emitter from '../utils/emitter'
const childData = ref("anotherchildData...")

const clickFunc = () => {
    childData.value += '.'
}
watchEffect(() => {
    emitter.emit("ChildData", childData.value)
})
</script>

<style scoped></style>