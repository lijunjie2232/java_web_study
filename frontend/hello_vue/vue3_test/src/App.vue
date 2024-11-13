<template>
    <div>
        <i v-on:click="clicked">clicked={{ count }}</i>
    </div>
</template>

<script setup lang="ts">
import { ref, Ref } from 'vue'
const count: Ref = ref(0)
const clicked = () => {
    count.value++
}
</script>
<style scoped></style>