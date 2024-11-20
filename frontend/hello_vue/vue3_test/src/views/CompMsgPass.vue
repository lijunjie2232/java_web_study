<template>
    <div>components message pass</div>
    <div id="nav">
        <RouterLink :to="{ path: '/cmp/fc' }">fc</RouterLink>
    </div>
    <div id="content">
        <RouterView />
    </div>
</template>

<script setup lang="ts">

</script>
<style scoped>
#nav {
    width: 22%;
    min-height: 350px;
    margin-left: 10px;
    float: left;
    border: 1px solid;
    border-radius: 10px;
}

#content {
    width: 75%;
    min-height: 350px;
    margin-right: 10px;
    float: right;
    border: 1px solid;
    border-radius: 10px;
}
</style>