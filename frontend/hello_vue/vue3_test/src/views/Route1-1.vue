<template>
    <div>route1-1</div>
    <div>
        <p v-if="query.username">username: {{ query.username }}</p>
        <p v-if="query.password">password: {{ query.password }}</p>
    </div>
</template>

<script setup lang="ts" name="Route1_1">
import { toRefs } from 'vue'
import { useRoute } from 'vue-router'
let route = useRoute()
let {query} = toRefs(route)
console.log(route.query)
console.log(query)

</script>
<style scoped></style>