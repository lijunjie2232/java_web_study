<template>
    <div>
        <p>name: {{ person.name }}</p>
        <p>age: {{ person.age }}</p>
        <p>r name: {{ rperson.name }}</p>
        <p>r age: {{ rperson.age }}</p>
        <button v-on:click="changeName">change name</button>
        <button v-on:click="changeAge">change age</button>
        <button v-on:click="changePerson">change person</button>
        <button v-on:click="changeRName">change reactive name</button>
        <button v-on:click="changeRAge">change reactive age</button>
        <button v-on:click="changeRPerson">change reactive person</button>
    </div>
</template>

<script setup lang="ts">
import { ref, watch, reactive } from 'vue'

let person = ref({
    name: "li",
    age: 0
})
let rperson = reactive(
    {
        name: "li",
        age: 0
    }
)
const changeName = () => {
    person.value.name += "~"
}
const changeAge = () => {
    person.value.age += 1
}
const changePerson = () => {
    person.value = {
        name: "li",
        age: 0
    }
}
const changeRName = () => {
    rperson.name += "~"
}
const changeRAge = () => {
    rperson.age += 1
}
const changeRPerson = () => {
    Object.assign(rperson,
        {
            name: "li++",
            age: 10
        }
    )
}
// ref watch
const personWatch = watch(person, (nv, ov) => {
    console.log("personWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
const personWatchDeep = watch(person, (nv, ov) => {
    console.log("personWatchDeep: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true })
const personWatchDeepIm = watch(person, (nv, ov) => {
    console.log("personWatchDeepIm: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true, immediate: true })

// reactive watch
const rpersonWatch = watch(rperson, (nv, ov) => {
    console.log("rpersonWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
const rpersonWatchDeep = watch(rperson, (nv, ov) => {
    console.log("rpersonWatchDeep: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true })
const rpersonWatchDeepIm = watch(rperson, (nv, ov) => {
    console.log("rpersonWatchDeepIm: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true, immediate: true })

// getter watch
const personNameWatch = watch(() => person.value.name, (nv, ov) => {
    console.log("personNameWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
</script>
<style scoped></style>