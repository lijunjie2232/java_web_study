<template>
    <div>
        <p>childData: {{ childData }}</p>
        <p>fatherData: {{ fatherData }}</p>
        <button @click="emit('customEvent', childData)">click me</button>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
let childData = ref("childData...")
const props = defineProps(["fatherData", "sendData"])
props.sendData(childData.value)

const emit = defineEmits(["customEvent"])

</script>

<style scoped></style>