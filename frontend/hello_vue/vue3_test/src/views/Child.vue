<template>
    <div>
        <p>childData: {{ childData }}</p>
        <p>anotherChildData: {{ ChildData1 }}</p>
        <p>fatherData: {{ fatherData }}</p>
        <button @click="emit('customEvent', childData)">click me</button>
        <Mago v-bind="$attrs"></Mago>
    </div>
</template>

<script setup lang="ts">
import { inject, onUnmounted, ref } from 'vue'
import emitter from '../utils/emitter'
import Mago from './mago.vue';
const childData = ref("childData...")
const props = defineProps(["fatherData", "sendData"])
props.sendData(childData.value)

const emit = defineEmits(["customEvent"])

const ChildData1 = ref("")
emitter.on("ChildData", (data) => {
    ChildData1.value = data as string
})
onUnmounted(() => {
    emitter.off("ChildData")
})

defineExpose({ childData })

console.log("inject fatherData in Child.vue: ", inject('fatherData', ref("defaultValue")).value)
</script>

<style scoped></style>