<template>
    <div>route1-2</div>
    <div>
        <p v-if="route.params.username">username: {{ params.username }}</p>
        <p v-if="route.params.password">password: {{ params.password }}</p>

        <p v-if="route.params.username">username: {{ username }}</p>
        <p v-if="route.params.password">password: {{ password }}</p>
    </div>
</template>

<script setup lang="ts" name="Route1_2">
import { toRefs } from 'vue'
import { useRoute } from 'vue-router'
let route = useRoute()
let { params } = toRefs(route)
console.log(route);
defineProps(["username", "password"])
</script>
<style scoped></style>