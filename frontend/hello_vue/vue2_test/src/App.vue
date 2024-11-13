<template>
    <button v-on:click="clicked">count = {{ count }}</button>
    <div>
        <test1></test1>
    </div>
</template>

<script lang="ts">
import { ref } from 'vue'
import test1 from "./components/test1.vue"
export default {
    name: 'App',
    components: { test1 },
    data() {
        return {
            count: ref(0)
        }
    },
    methods: {
        clicked() { this.count++ }
    }
}
</script>

<style></style>