import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

// export const useMulStore = defineStore("mulstore", {
//     actions: {
//         multiple(num: number) {
//             this.mul = num ** 2
//         }
//     },
//     state() {
//         return {
//             mul: JSON.parse(localStorage.getItem("mul") as string) || 0
//         }
//     },
//     getters: {
//         log2(): number {
//             return Math.log2(this.mul)
//         }
//     }
// })

export const useMulStore = defineStore("mulstore", () => {
    const mul = ref(JSON.parse(localStorage.getItem("mul") as string) || 0)
    const log2 = computed(() => Math.log2(mul.value))
    const multiple = (num: number) => { mul.value = num ** 2 }
    return { mul, log2, multiple }
})