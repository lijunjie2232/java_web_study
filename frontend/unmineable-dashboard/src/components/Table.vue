<template>
  <div id="pbar">
    <v-progress-linear color="#fd79a8" height="2" v-show="refresh" indeterminate></v-progress-linear>
  </div>
  <div id="bar">
    <div id="select-algo">
      <v-select label="Select algo" :items="algoWorkers" item-title="info" item-value="label" variant="outlined"
        v-model="algoSelect" item-key="uuid" single-line></v-select>
    </div>

    <v-btn id="refresh-btn" density="compact" icon="mdi-refresh" @click="refresshWorker"></v-btn>
  </div>
  <div>
    <v-data-table :items="workerData"></v-data-table>
  </div>
</template>

<script setup lang="ts">
import { getWorkers } from '@/utils/getWorker'
import { ref, computed, type Ref } from 'vue'

const algoSelect = ref("")
const refresh = ref(false)
let workers: any = ref({})
const algoList: Ref<string[]> = ref([])
const algoWorkers: Ref<object[]> = ref([])
const workerData = computed(
  () => {
    if (workers.value.hasOwnProperty(algoSelect.value)) {
      let data = []
      let worker: any
      for (let idx in workers.value[algoSelect.value]["workers"]) {
        worker = workers.value[algoSelect.value]["workers"][idx]
        worker.last = `${(Math.ceil(Date.now() - worker.last) / 1000)}s`
        worker.online = worker.online ? "⭕" : "❌"
        data.push(worker)
      }
      return data
    }
    else
      return []
  }
)


const getDefaultAlgo = (): string => {
  return import.meta.env.VITE_ALGO
}


const refresshWorker = async () => {
  refresh.value = true
  getWorkers().then(
    (resp) => {
      workers.value = resp.data.data
      algoList.value = []
      algoWorkers.value = []
      let wnum = 0
      let alpower = 0
      for (let key in workers.value) {
        algoList.value.push(key)
        wnum = workers.value[key]["workers"].length
        alpower = 0
        if (wnum > 0)
          alpower = workers.value[key]["workers"].reduce((a: any, b: any) => {
            return a + parseInt(b.rhr);
          }, 0)

        algoWorkers.value.push({ label: key, info: `${key} (workers: ${wnum} | calc power: ${alpower})` })
      }
      if (!algoList.value.includes(algoSelect.value)) {
        algoSelect.value = getDefaultAlgo()
      }
      refresh.value = false
    }
  )
}
refresshWorker()
// const 

</script>
<style scoped>
#pbar {
  width: 100%;
  height: 10px;
}

#bar {
  display: flex;
  margin: 5px;
  line-height: 80px;
  height: 80px;
  padding: 5px;
  text-align: center;
}

#select-algo {
  display: flex;
  width: 90%;
  height: 70px;
  margin: 1px;
  min-width: 100px;
  float: left;
}

#refresh-btn {
  /* width: 10%; */
  min-width: 56px;
  min-height: 56px;
  margin: 1px;
  margin-left: 10px;
  line-height: 70px;
  float: left;
}
</style>