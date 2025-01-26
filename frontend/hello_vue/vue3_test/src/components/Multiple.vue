<template>
    <div>
        <p><input type="number" v-model="a">*<input type="number" v-model="b">=<span>{{ result }}</span></p>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
const a = ref(0)
const b = ref(0)
// const result = computed(() => {
//     return a.value * b.value
// })
const result = computed(
    {
        get() {
            return a.value * b.value
        },
        set(val) {
            result.value = val
        }
    }
)
const _ = watch(result, (newValue, oldValue) => {
    console.log("old: ", oldValue);
    console.log("new: ", newValue);

})
</script>
<style scoped></style>