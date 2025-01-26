<template>
    <div>
        <button @click="countAdd">count: {{ count }}</button>
    </div>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, onMounted, onBeforeUpdate, onUpdated } from 'vue'
const count = ref(0)
const countAdd = () => {
    count.value++
}

onBeforeMount(() => {
    console.log("onBeforeMount");
})
onMounted(() => {
    console.log("onMounted");
})
onBeforeUpdate(() => {
    console.log("onBeforeUpdate");
})
onUpdated(() => {
    console.log("onUpdated");
})

</script>
<style scoped></style>