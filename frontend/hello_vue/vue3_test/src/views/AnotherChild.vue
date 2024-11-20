<template>
    <div>
        <p>me: {{ childData }}</p>
        <button @click="clickFunc">click me</button><br />
        <input type="text" :value="modelValue" @input="inputUpFunc((<HTMLInputElement>$event.target).value)" />
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
defineProps(["modelValue"])
const emit = defineEmits(["update:modelValue"])
const inputUpFunc = (value: string) => {
    console.log(value);
    emit("update:modelValue", value)
}
</script>

<style scoped></style>