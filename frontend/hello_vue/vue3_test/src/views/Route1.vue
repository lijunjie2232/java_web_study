<template>
    <div>route1</div>
    <div id="router-link">
        <RouterLink :to="{ path: '/r1/r1-1', query: r1_data[0] }">r1-1-0</RouterLink>
        <RouterLink :to="{ path: '/r1/r1-1', query: r1_data[1] }">r1-1-1</RouterLink>
        <RouterLink :to="{ name: 'r1_2', params: r1_data[2] }">r1-2</RouterLink>
        <button @click="clickFunc('r1_2', r1_data[3])">r1-2-1</button>
    </div>
    <div id="content">
        <RouterView />
    </div>

</template>

<script setup lang="ts">
import { onMounted, onUnmounted, reactive } from 'vue';
import { RouterView, RouterLink, useRouter } from 'vue-router';
const router = useRouter()
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
        },
        {
            username: 'user',
            password: 'user'
        }
    ]
)
const clickFunc = (route: string, data: {}) => {
    router.push({ name: route, params: data })
}

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