<template>
    <div>
        <div>
            <p>fatherData: {{ fatherData }}</p>
            <p>childData: {{ childData }}</p>
            <p>cinput: {{ cinput }}</p>
        </div>
        <Child :father-data="fatherData" :send-data="getData" @custom-event="custFunc"></Child>
        <AnotherChild v-model="cinput"></AnotherChild>
    </div>
</template>

<script setup lang="ts">
import AnotherChild from './AnotherChild.vue';
import Child from './Child.vue';
import { ref } from 'vue'
const fatherData = ref("fatherData...")
const childData = ref("")
const cinput = ref()
const getData = (data: string) => {
    childData.value = data
}
const custFunc = (s: string) => {
    console.log(s);
}
</script>
<style scoped>
div {
    width: 98%;
    margin: 1%;
    border: 1px solid;
    border-radius: 5px;
}
</style>