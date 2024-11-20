<template>
    <div style="text-align: center;">
        <div style="border:1px solid; border-color: black; border-radius:5px">Route Test</div>
        <div id="router-link">
            <RouterLink active-class="router-active" to="/r1">r1</RouterLink>
            <RouterLink active-class="router-active" to="/r2">r2</RouterLink>
            <RouterLink active-class="router-active" to="/cmp">cmp</RouterLink>
            <RouterLink active-class="router-active" to="/r3">r3</RouterLink>
        </div>
        <div id="content">
            <RouterView></RouterView>
        </div>
    </div>
</template>

<script setup lang="ts">
import { RouterView, RouterLink } from 'vue-router'
</script>
<style scoped>
#router-link {
    width: 200px;
    margin: 0 auto;
    text-align: center;
    display: flex;
    justify-content: space-around;
}

#content {
    margin-left: 1%;
    width: 98%;
    min-height: 500px;
    border: 1px solid;
    border-radius: 10px;
    margin: 0 auto;
}

#router-link a {
    width: 100px;
    height: 20px;
    border: 2px solid;
    border-radius: 5px;
    margin: 5px;
}

.router-active {
    color: #fd79a8;
}
</style>