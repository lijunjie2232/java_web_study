<template>
    <div>route1</div>
    <div id="router-link">
        <RouterLink :to="{ path: '/r1/r1-1', query: r1_data[0] }">r1-1-0</RouterLink>
        <RouterLink :to="{ path: '/r1/r1-1', query: r1_data[1] }">r1-1-1</RouterLink>
        <RouterLink :to="{ name: 'r1_2', params: r1_data[2] }">r1-2</RouterLink>
    </div>
    <div id="content">
        <RouterView />
    </div>

</template>

<script setup lang="ts">
import { onMounted, onUnmounted, reactive } from 'vue';
import { RouterView, RouterLink } from 'vue-router';
onMounted(() => {
    console.log("route1 mounted");
})
onUnmounted(() => {
    console.log("route1 unmounted");
})
const r1_data = reactive(
    [
        {
            username: 'username',
            password: 'password'
        },
        {
            username: 'admin',
            password: 'admin'
        },
        {
            username: 'root',
            password: 'root'
        }
    ]
)


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
    margin: 5px;
}

.router-active {
    color: #fd79a8;
}
</style>