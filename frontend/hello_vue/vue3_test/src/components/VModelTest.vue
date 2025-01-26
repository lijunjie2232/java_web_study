<template>
    <p>*************</p>
    <div>Picked: {{ picked }}</div>

    <input type="radio" id="one" value="One1" v-model="picked" />
    <label for="one">One</label>

    <input type="radio" id="two" value="Two1" v-model="picked" />
    <label for="two">Two</label>

    <p>*************</p>
    <span> Selected: {{ selected }}</span>

    <select v-model="selected">
        <option disabled value="">Please select one</option>
        <option value="a">A</option>
        <option value="b">B</option>
        <option value="c">C</option>
    </select>

    <p>*************</p>
    <div>Checked names: {{ checkedNames }}</div>

    <input type="checkbox" id="jack" value="Jack1" v-model="checkedNames" />
    <label for="jack">Jack</label>

    <input type="checkbox" id="john" value="John1" v-model="checkedNames" />
    <label for="john">John</label>

    <input type="checkbox" id="mike" value="Mike1" v-model="checkedNames" />
    <label for="mike">Mike</label>

    <p>*************</p>
    <div>Selected: {{ mulSelected }}</div>

    <select v-model="mulSelected" multiple>
        <option>A</option>
        <option>B</option>
        <option>C</option>
    </select>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const picked = ref('')
const selected = ref('')
const checkedNames = ref([])
const mulSelected = ref([])
</script>
