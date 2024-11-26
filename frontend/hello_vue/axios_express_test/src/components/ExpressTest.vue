<template>
    <div>
      <div v-show="article.userId">
        <p>userId: {{ article.userId }}</p>
        <p>id: {{ article.id }}</p>
        <p>title: {{ article.title }}</p>
        <p>body: {{ article.body }}</p>
      </div>
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