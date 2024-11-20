<template>
    <div>
        <div>
            <p>fatherData: {{ fatherData }}</p>
            <p>childData: {{ childData }}</p>
        </div>
        <Child :father-data="fatherData" :send-data="getData" @custom-event="custFunc"></Child>
    </div>
</template>

<script setup lang="ts">
import Child from './Child.vue';
import { ref } from 'vue'
let fatherData = ref("fatherData...")
let childData = ref("")
const getData = (data: string) => {
    childData.value = data
}
const custFunc = (s:string)=>{
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