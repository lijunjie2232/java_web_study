<template>
    <div>
        <input type="text" ref="p1" />
        <button @click="() => console.log(p1.value)">
            output
        </button>
        <input ref="ref-temp-input" />
        <button @click="() => console.log(p2?.value)">
            output
        </button>
    </div>
</template>

<script setup lang="ts">
import { ref, useTemplateRef, onMounted } from 'vue'
const p1 = ref()
const p2 = useTemplateRef('ref-temp-input')

onMounted(() => {
    // p2.value.focus()
})
</script>
<style scoped></style>