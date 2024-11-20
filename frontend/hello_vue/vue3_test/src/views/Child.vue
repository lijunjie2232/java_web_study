<template>
    <div>
        <p>childData: {{ childData }}</p>
        <p>anotherChildData: {{ ChildData1 }}</p>
        <p>fatherData: {{ fatherData }}</p>
        <button @click="emit('customEvent', childData)">click me</button>
    </div>
</template>

<script setup lang="ts">
import { onUnmounted, ref } from 'vue'
import emitter from '../utils/emitter'
const childData = ref("childData...")
const props = defineProps(["fatherData", "sendData"])
props.sendData(childData.value)

const emit = defineEmits(["customEvent"])

const ChildData1 = ref("")
emitter.on("ChildData", (data) => {
    ChildData1.value = data as string
})
onUnmounted(()=>{
    emitter.off("ChildData")
})
</script>

<style scoped></style>