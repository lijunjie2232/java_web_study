<template>
    <div>
        <input type="text" ref="p1" />
        <button @click="() => console.log(p1.value)">
            output
        </button>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
const p1 = ref()
</script>
<style scoped></style>