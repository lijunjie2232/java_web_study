<template>
    <div>
        <input type="number" v-model="num" />
        <p>{{ num }}^2={{ mulStore.mul }}</p>
        <p>{{ mul }}</p>
        <p>log2({{ num }})={{ log2 }}</p>
    </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { useMulStore } from "../store/piniatest1.ts"
import { storeToRefs } from 'pinia';
const num = ref(0)
const mulStore = useMulStore()
const { mul, log2 } = storeToRefs(mulStore)

// watchEffect(() => { mulStore.mul = num.value ** 2 })
watchEffect(() => { mulStore.multiple(num.value) })
mulStore.$subscribe((mutate, state) => {
    console.log("changed mulStore: ", mutate)
    localStorage.setItem("mul", JSON.stringify(state.mul))
})

</script>
<style scoped></style>