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
import Axios from 'axios'
import request from './utils/axios'

const article = ref({
  body: null,
  id: null,
  title: null,
  userId: null
})

const getArticle = async () => {
  // Axios({
  //   method: "get",
  //   url: `http://jsonplaceholder.typicode.com/posts/${Math.round(Math.random() * 10)}`,
  // }).then(
  //   ({ data }) => {
  //     // console.log(data)
  //     // article.value = data
  //     Object.assign(article.value, data)
  //   }
  // )
  // Axios.get(
  //   `http://jsonplaceholder.typicode.com/posts/${Math.round(Math.random() * 10)}`,
  //   {
  //     headers: {},
  //     params: {},
  //     data: {}
  //   }
  // ).then(
  //   ({ data }) => {
  //     console.log(data)
  //     article.value = data
  //   }
  // )
  request.get(
    `/posts/${Math.round(Math.random() * 10)}`,
    {
      headers: {},
      params: {},
      data: {}
    }
  ).then(
    ({ data }) => {
      console.log(data)
      article.value = data
    }
  )
}

</script>
<style scoped></style>