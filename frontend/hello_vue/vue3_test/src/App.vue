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
    <input @keyup.ctrl.enter="keyTest1" value="keyTest: ctrl+enter" />
    <input @click.ctrl="keyTest2" value="keyTest: click+ctrl" />
    <input @click.prevent.right="rightPrevent" value="keyTest: click.right(mouse)" />

    <!-- v-for on range -->
    <!-- start with 1 -->
    <li v-for="n in 7">{{ n }}</li>
    <Multiple></Multiple>
    <WatchTest></WatchTest>
    <RefTest></RefTest>
    <InterfaceTest></InterfaceTest>
    <CssTest></CssTest>
    <VModelTest></VModelTest>
    <LifecycleTest></LifecycleTest>
    <PropsEmitsTest @send-select="selectRec" :lang-list="langs" :selected="peselected"></PropsEmitsTest>
    <RouteTest></RouteTest>
    <PiniaTest1></PiniaTest1>
    <PiniaTest2></PiniaTest2>
</template>

<script setup lang="ts" name="App1">
import { ref, reactive } from 'vue'
import Multiple from './components/Multiple.vue'
import WatchTest from './components/WatchTest.vue'
import RefTest from './components/RefTest.vue'
import InterfaceTest from './components/InterfaceTest.vue'
import CssTest from './components/CssTest.vue'
import VModelTest from './components/VModelTest.vue'
import LifecycleTest from './components/LifecycleTest.vue'
import PropsEmitsTest from './components/PropsEmitsTest.vue'
import { type ILang } from './types'
import RouteTest from './components/RouteTest.vue'
import PiniaTest1 from './components/PiniaTest1.vue'
import PiniaTest2 from './components/PiniaTest2.vue'
const count = ref(0)
const person = reactive(
    {
        name: "li",
        age: 0
    }
)
const langs = reactive<ILang[]>(
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

const keyTest1 = () => {
    console.log('keyup.ctrl.enter')
}

const keyTest2 = () => {
    console.log('click.ctrl')
}
const rightPrevent = () => {
    alert("right prevent")
}

// props emits test
const peselected = ref()
const selectRec = (id: number) => {
    console.log("app received: ", id);
    peselected.value = id
}
</script>
<style scoped>
</style>