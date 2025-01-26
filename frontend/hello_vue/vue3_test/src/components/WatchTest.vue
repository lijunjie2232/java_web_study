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
import { ref, watch, reactive, watchEffect } from 'vue'

let person = ref({
    name: "li",
    age: 0,
    langs: {
        k1: "v1",
        k2: "v2"
    }
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
// person.value.name is basic type, so a ()=>{} is needed
const personNameWatch = watch(() => person.value.name, (nv, ov) => {
    console.log("personNameWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// person.value.langs is an object, could directly pass into watch
// could not called back after whole langs change to a new object, but called back if attibutes in langs changed
const personLangsWatch1 = watch(person.value.langs, (nv, ov) => {
    console.log("personLangsWatch1: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// could only called back after whole langs change to a new object, not called back if attibutes in langs changed
const personLangsWatch2 = watch(() => person.value.langs, (nv, ov) => {
    console.log("personLangsWatch2: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// could called back on both whole langs change to a new object or attibutes in langs changed
const personLangsWatch3 = watch(() => person.value.langs, (nv, ov) => {
    console.log("personLangsWatch3: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true })

// multiple var watcher
const mulWatch = watch([
    () => person.value.name,
    () => person.value.langs,
], (nv, ov) => {
    console.log("mulWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
// watchEffect
const a = ref(0)
const b = ref(0)
watchEffect(
    () => {
        if (a.value > 10 || b.value > 0)
            console.log("triggered")
    }
)
</script>
<style scoped></style>