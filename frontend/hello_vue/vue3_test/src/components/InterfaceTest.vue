<template>
    <div>
        <p>massage</p>
        <P>code: {{ massage.code }}</P>
        <P>msg: {{ massage.msg }}</P>
        <p>-----------------</p>
        <table>
            <thead>
                <tr>
                    <th>index</th>
                    <th>code</th>
                    <th>msg</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(msg, idx) in messages">
                    <td>
                        {{ idx }}
                    </td>
                    <td>
                        {{ msg.code }}
                    </td>
                    <td>
                        {{ msg.msg }}
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup lang="ts">
import { type IMessage } from '../types';
import { reactive } from 'vue';
let massage: IMessage = reactive({
    code: 200,
    msg: "ok"
})
let messages: IMessage[] = reactive(
    [
        {
            code: 200,
            msg: "ok"
        },
        {
            code: 200,
            msg: "ok"
        },
        {
            code: 200,
            msg: "ok"
        },
        {
            code: 200,
            msg: "ok"
        },
        {
            code: 200,
            msg: "ok"
        },
    ]
)
</script>
<style scoped></style>