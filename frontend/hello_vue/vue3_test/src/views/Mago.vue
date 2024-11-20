<template>
    <div>
        <p>magoData: {{ magoData }}</p>
        <p>a: {{ a }}</p>
        <p>b: {{ b }}</p>
        <button @click="aadd">aadd</button>
        <button @click="fFunc($parent)">fFunc</button>
        <button @click="console.log($parent)">$parent</button>
        <button @click="console.log($parent.childData)">$parent.childData</button>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const magoData = ref("magoData...")
defineProps(["a", "b", "aadd"])
const fFunc = (p) => {
    console.log(p)
    console.log(p.childData)

}
</script>

<style scoped></style>