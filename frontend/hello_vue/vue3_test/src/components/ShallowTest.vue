<template>
    <div>{{ person }}</div>
    <div>{{ shPerson }}</div>
    <button @click="shPerson.age++; shPerson.name += '~'; person.age++; person.name += '~';">clickme</button>
    <button @click="clickFunc">clickme</button>
    <button @click="count++">count:{{ count }} count_ro:{{ count_ro }}</button>
    <h1>{{ msg }}</h1>
    <input type="text" v-model="msg" />
</template>

<script setup lang="ts">
import { readonly, ref, shallowRef } from 'vue'
import useDelayMsg from '../hooks/useDelayMsg.ts'

const person = ref({ name: "li", age: 0 })
const shPerson = shallowRef({ name: "li", age: 0 })
const clickFunc = () => {
    shPerson.value.age++;
    shPerson.value.name += "~";
    person.value.age++;
    person.value.name += "~";
}

let {msg} = useDelayMsg("", 3000)

const count = ref(0)
const count_ro = readonly(count)
</script>
<style scoped></style>