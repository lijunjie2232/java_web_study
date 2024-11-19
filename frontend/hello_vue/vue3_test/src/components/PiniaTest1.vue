<template>
    <div>
        <input type="number" v-model="num" />
        <p>{{ mulStore.mul }}</p>
        <p>{{ mul }}</p>
    </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { useMulStore } from "../store/piniatest1.ts"
import { storeToRefs } from 'pinia';
const num = ref(0)
const mulStore = useMulStore()
const { mul } = storeToRefs(mulStore)
// watchEffect(() => { mulStore.mul = num.value ** 2 })
watchEffect(() => { mulStore.multiple(num.value) })

</script>
<style scoped></style>