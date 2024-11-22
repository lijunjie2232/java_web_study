<template>
    <button @click="sum++">click: {{ sum }}</button>
    <!-- <table>
        <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>email</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in data" v-bind:key="data.id">
                <td>{{ item.id }}</td>
                <td>{{ item.name }}</td>
                <td>{{ item.email }}</td>
            </tr>
        </tbody>
    </table> -->
    <input v-testcmd="text"/>
</template>

<script setup lang="ts">
import { ref } from 'vue'
// import axios from 'axios'
const sum = ref(0)
const text = ref("123321")
// let {data} = await axios.get("https://jsonplaceholder.typicode.com/users")
// console.log(data)

</script>
<style scoped>
.splash-bg {
    position: fixed;
    left: 0%;
    top: 0%;
    width: 100%;
    height: 100%;
    background-color: #636e727F;
}

.splash {
    width: 300px;
    height: 200px;
    border: 2px #636e72 solid;
    box-shadow: 10px 5px 5px #636e72;
    border-radius: 10px;
    text-align: center;
    position: fixed;
    left: 50%;
    margin-left: -150px;
    top: 50%;
    margin-top: -100px;
    background-color: #FFFFFFFF;
}
table{
    border: 1px solid;
}
</style>