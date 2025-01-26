<template>
    <div>selected: {{ selected }}</div>
    <div v-for="lang in langList" v-key="lang.id">
        <li @click="sendSelect(lang.id)">{{ lang.name }}</li>
    </div>
</template>

<script setup lang="ts">
// import { defineProps, defineEmits } from 'vue'
const {langList, selected} = defineProps(["langList", "selected"])
const emitSelect = defineEmits(["sendSelect"])

const sendSelect = (id: number) => {
    console.log("clicked: ", id)
    emitSelect("sendSelect", id)
}
</script>
<style scoped></style>