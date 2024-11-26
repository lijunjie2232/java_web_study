<template>
  <div>

    <button @click="getArticle">click me</button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const article = ref({
  body: null,
  id: null,
  title: null,
  userId: null
})

const getArticle = async () => {
}

</script>
<style scoped></style>