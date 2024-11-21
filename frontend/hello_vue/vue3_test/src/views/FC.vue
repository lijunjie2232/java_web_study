<template>
    <div>
        <div>
            <p>fatherData: {{ fatherData }}</p>
            <p>childData: {{ childData }}</p>
            <p>cinput: {{ cinput }}</p>
            <p>a: {{ a }}</p>
            <p>b: {{ b }}</p>
            <p>c: {{ c }}</p>
            <p>d: {{ d }}</p>

        </div>
        <Child ref="c1" :father-data="fatherData" :send-data="getData" @custom-event="custFunc" :a="a" :b="b"
            :aadd="aadd">
        </Child>
        <!-- <AnotherChild v-model="cinput"></AnotherChild> -->
        <AnotherChild v-model:intext="cinput"></AnotherChild>
        <button @click="c1Func">c1Func</button>
        <button @click="console.log($refs.c1.childData)">$refs</button>
        <SlotTest>
            <template #test1>
                <table>
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="i in slotData" :key="i.id">
                            <td>{{ i.id }}</td>
                            <td>{{ i.name }}</td>
                        </tr>
                    </tbody>
                </table>
            </template>
        </SlotTest>
        <SSlotTest>
            <template v-slot="scopedSlot">
                <table>
                    <thead>
                        <tr>
                            <th>id</th>
                            <th>name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="i in scopedSlot.data" :key="i.id">
                            <td>{{ i.id }}</td>
                            <td>{{ i.name }}</td>
                        </tr>
                    </tbody>
                </table>
            </template>
        </SSlotTest>
    </div>
</template>

<script setup lang="ts">
import SlotTest from '../components/SlotTest.vue';
import SSlotTest from '../components/SSlotTest.vue';
import AnotherChild from './AnotherChild.vue';
import Child from './Child.vue';
import { provide, ref } from 'vue'
const fatherData = ref("fatherData...")
const childData = ref("")
const cinput = ref()
const getData = (data: string) => {
    childData.value = data
}
const custFunc = (s: string) => {
    console.log(s);
}
const a = ref(0)
const b = ref(0)
const c = ref(0)
const d = ref(0)

const aadd = () => {
    a.value++
}

const c1 = ref()
const c1Func = () => {
    console.log(c1.value)
    console.log(c1.value.childData)
}

provide('fatherData', fatherData)

const slotData = ref(
    [
        { id: 0, name: 'py' },
        { id: 1, name: 'ts' }
    ]
)
</script>
<style scoped>
div {
    width: 98%;
    margin: 1%;
    border: 1px solid;
    border-radius: 5px;
    text-align: center;
}
</style>