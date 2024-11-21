<template>
    <div>
        <slot name="xxx" :data="scopedSlotData"></slot>
    </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
const scopedSlotData = ref(
    [
        { id: 0, name: 'java' },
        { id: 1, name: 'py' }
    ]
)
</script>
<style scoped>
div {
    text-align: center;
    display: flex;
}
</style>