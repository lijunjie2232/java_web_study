<template>
    <div>
        <a v-on:click="clicked" href="#">clicked={{ count }}</a><br />
        <a v-on:click="pclicked" v-on:dblclick="pdclicked" href="#">age={{ person.age }}</a>
    </div>
    <!-- v-for on Array -->
    <table>
        <thead>
            <tr>
                <th>id</th>
                <th>name</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(item, idx) in langs" v-bind:key="idx">
                <td>{{ item.id }}</td>
                <td>{{ item.name }}</td>
            </tr>
        </tbody>
    </table>
    <!-- v-for on Object -->
    <li v-for="(value, key) in person">
        {{ key }}: {{ value }}
    </li>

    <!-- v-for on range -->
    <!-- start with 1 -->
    <li v-for="n in 7">{{ n }}</li>
    <Multiple></Multiple>
    <WatchTest></WatchTest>
    <RefTest></RefTest>
    <InterfaceTest></InterfaceTest>
    <CssTest></CssTest>
</template>

<script setup lang="ts" name="App1">
import { ref, reactive } from 'vue'
import Multiple from './components/Multiple.vue'
import WatchTest from './components/WatchTest.vue'
import RefTest from './components/RefTest.vue'
import InterfaceTest from './components/InterfaceTest.vue'
import CssTest from './components/CssTest.vue'
const count = ref(0)
const person = reactive(
    {
        name: "li",
        age: 0
    }
)
const langs = reactive(
    [
        { id: 0, name: 'python' },
        { id: 1, name: 'java' },
        { id: 2, name: 'typescript' }
    ]
)
const clicked = () => {
    count.value++
}
const pclicked = () => {
    person.age++
}
const pdclicked = () => {
    person.age = 0
}

// v-for
const myLangs = ref(
    [
        "py",
        "java",
        "ts"
    ]
)
</script>
<style scoped></style>