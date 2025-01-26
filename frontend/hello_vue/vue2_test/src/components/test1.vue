<template>
    <div class="test1">
        <h2>name: {{ name }}</h2>
        <h2>age: {{ age }}</h2>
    </div>
</template>

<script lang="ts">
// import { ref, reactive, toRefs, onMounted } from 'vue'
export default {
    name: 'test1',
    data() {
        return {
            name: "li",
            age: 0
        }
    }
}
</script>
<style scoped lang="less"></style>