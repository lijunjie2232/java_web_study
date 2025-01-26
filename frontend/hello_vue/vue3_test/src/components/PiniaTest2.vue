<template>
    <div>
        <p>{{ mul }}</p>
        <p>{{ log2 }}</p>
    </div>
</template>

<script setup lang="ts">
import { useMulStore } from "../store/piniatest1.ts"
import { storeToRefs } from 'pinia';
const mulStore = useMulStore()
const { mul, log2 } = storeToRefs(mulStore)
</script>
<style scoped></style>