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
import { inject, ref } from 'vue'
const magoData = ref("magoData...")
defineProps(["a", "b", "aadd"])
const fFunc = (p) => {
    console.log(p)
    console.log(p.childData)

}
console.log("inject fatherData in Child.vue: ", inject('fatherData', ref("defaultValue")).value)
</script>

<style scoped>
div {
    width: 98%;
    margin: 1%;
    padding: 2px;
    border: 1px solid;
    border-radius: 5px;
}
</style>