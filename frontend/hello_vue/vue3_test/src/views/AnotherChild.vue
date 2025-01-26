<template>
    <div>
        <p>me: {{ childData }}</p>
        <!-- <p>fatherData: {{ $parent }}</p> -->
        <button @click="clickFunc">click me</button><br />
        <!-- <input type="text" :value="modelValue" @input="inputUpFunc((<HTMLInputElement>$event.target).value)" /> -->
        <input type="text" :value="intext" @input="inputUpFunc((<HTMLInputElement>$event.target).value)" />
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

// defineProps(["modelValue"])
// const emit = defineEmits(["update:modelValue"])
// const inputUpFunc = (value: string) => {
//     emit("update:modelValue", value)
// }
defineProps(["intext"])
const emit = defineEmits(["update:intext"])
const inputUpFunc = (value: string) => {
    emit("update:intext", value)
}
</script>

<style scoped></style>