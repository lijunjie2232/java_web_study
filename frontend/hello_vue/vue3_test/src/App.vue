<template>
    <div>
        <a v-on:click="clicked" href="#">clicked={{ count }}</a><br />
        <a v-on:click="pclicked" v-on:dblclick="pdclicked" href="#">age={{ person.age }}</a>
    </div>
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
        </tr>
        <tr v-for="item in langs" v-bind:key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.name }}</td>
        </tr>
    </table>
    <Multiple></Multiple>
    <WatchTest></WatchTest>
    <RefTest></RefTest>
    <InterfaceTest></InterfaceTest>
</template>

<script setup lang="ts" name="App1">
import { ref, reactive } from 'vue'
import Multiple from './components/Multiple.vue'
import WatchTest from './components/WatchTest.vue'
import RefTest from './components/RefTest.vue'
import InterfaceTest from './components/InterfaceTest.vue'
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

</script>
<style scoped></style>