# Vue3

- [Vue3](#vue3)
  - [Vite](#vite)
  - [Vue2 to Vue3](#vue2-to-vue3)
  - [Tips](#tips)
  - [css style](#css-style)
  - [Vue directive](#vue-directive)
  - [Reactivity Fundamentals](#reactivity-fundamentals)
    - [ref](#ref)
    - [reactive](#reactive)
    - [toRefs](#torefs)
    - [computed](#computed)
    - [watch](#watch)
    - [watch effect](#watch-effect)
  - [Ref attribute of tag](#ref-attribute-of-tag)
  - [Lifecycle](#lifecycle)
    - [options lifecycle hooks api](#options-lifecycle-hooks-api)
    - [composition lifecycle hooks api](#composition-lifecycle-hooks-api)
  - [Components](#components)
    - [parameters pass](#parameters-pass)
    - [dynamic component](#dynamic-component)
  - [Router](#router)
    - [Router work mode](#router-work-mode)
    - [chill router and parameter pass](#chill-router-and-parameter-pass)
      - [configure child router:](#configure-child-router)
      - [query](#query)
      - [params](#params)
    - [route props](#route-props)
    - [route replace](#route-replace)
    - [programming navigator](#programming-navigator)
    - [redirect](#redirect)
  - [Pinia](#pinia)
    - [store data in pinia](#store-data-in-pinia)
    - [get data from pinia](#get-data-from-pinia)
    - [modify data in pinia](#modify-data-in-pinia)
    - [storeToRefs](#storetorefs)
    - [getters](#getters)
    - [subscript](#subscript)
    - [composed format of pinia](#composed-format-of-pinia)
  - [Components message pass](#components-message-pass)
    - [defineProps in Father-child structure](#defineprops-in-father-child-structure)
    - [custom event](#custom-event)
    - [mitt](#mitt)
    - [v-modle on components](#v-modle-on-components)
      - [bind variable to components by `v-model`:](#bind-variable-to-components-by-v-model)
      - [bind variable to components by custom name](#bind-variable-to-components-by-custom-name)
    - [$attrs](#attrs)
    - [$refs / $parent](#refs--parent)
    - [provide inject](#provide-inject)
    - [Pinia](#pinia-1)
    - [slot](#slot)
      - [default slot](#default-slot)
      - [named slot](#named-slot)
      - [scope slot](#scope-slot)
    - [sumary](#sumary)
  - [apis](#apis)
    - [shallowRef / shallowReactive](#shallowref--shallowreactive)
    - [readonly / shallowReadonly](#readonly--shallowreadonly)
    - [toRaw / markRaw](#toraw--markraw)
    - [customRef](#customref)
    - [Teleport](#teleport)


## Vite
1. `npm i -g vite pnpm`
2. `pnpm create vite` or `pnpm create vite project_name --template vue`
3. `pnpm i`
4. `pnpm dev`

## Vue2 to Vue3
- vue2 uses `options api`, while Vue3 uses `composition api`
- variable in `data(){return{}}` is reactive in vue2
- `options api` could coexists with `composition api`, but  `composition api` could not get things in `options api`, `export default` could not be defined in `<script setup>` tag of  `composition api` 

## Tips

- use vite extension `vite-plugin-vue-setup-extend` could enable define name of `.vue` file by `<script setup lang="ts" name="xxx">`
- use `import { type IMessage } from '../types';` to import ts interface in vue
- ref wraped in reactive should visited without `.value`

## css style
1. define in tag `<style scoped></style>` in `.vue` file
2. define in `.css` file and import in `.vue`:
   1. in `<script>` tag by: `import '../style/main.css'`
   2. in `<template>` tag by: `@import '../style/main.css'`
3. import as global style in `main.ts`

## Vue directive
1. text interpolation: `{{ msg }}`
2. **v-html**: `<i v-html="htmlStr"></i>`
3. **v-text**: `<p v-text="msg.msg"></p>`, `v-text` could not rander html even if `msg.msg` is a valid html string
4. **v-bind**: bind attributes of html tag to variable
   1. `<input v-bind:id="inputId"/>` or `<input :id="inputId"/>`
   2. `<input :id="id"/>`, `="id"` could be ignore to `<input :id/>`
   3. use options `const attrs = { id: "username", type: "text"}` and bind: `<input v-bind="attrs"/>`
5. other directive:
   - **v-if**:
     - `<p v-if="seen">Now you see me</p>`, the tag will hidden if `seen` not true
     - **v-else**: `v-else` tag after `v-if`
     - **v-else-if**:
       ```vue
        <div v-if="type === 'A'">A</div>
        <div v-else-if="type === 'B'">B</div>
        <div v-else-if="type === 'C'">C</div>
        <div v-else>Not A/B/C</div>
        ```
      - while `v-if` is `false`, nothing remains on DOM
   - **v-show**: set `display` attribute of tag, whole tag stay on DOM even the value is false
   - **v-for**:
      ```vue
      <!-- v-for on Array-->
      <table>
          <thead>
              <tr>
                  <th>id</th>
                  <th>name</th>
              </tr>
          </thead>
          <tbody>
              <tr v-for="(item, idx) in myArray" v-bind:key="idx">
                  <td>{{ idx }}</td>
                  <td>{{ item.name }}</td>
              </tr>
          </tbody>
      </table>

      <!-- v-for on Object -->
      <li v-for="(value, key) in myObject">
          {{ key }}: {{ value }}
      </li>

      <!-- v-for on range -->
      <!-- start with 1 -->
      <span v-for="n in 10">{{ n }}</span>
      ```
      - use `v-bind:key` to re-render single item if changed
      - v-for could monite following operation of Array:
        - push()
        - pop()
        - shift()
        - unshift()
        - splice()
        - sort()
        - reverse()
   - **v-on**:
     - `<button v-on:click="clickFunc"></button>`
     - shorten as: `<button @click="clickFunc"></button>`
     - modifier:
       ```vue
        <!-- 单击事件将停止传递 -->
        <a @click.stop="doThis"></a>

        <!-- 提交事件将不再重新加载页面 -->
        <form @submit.prevent="onSubmit"></form>

        <!-- 修饰语可以使用链式书写 -->
        <a @click.stop.prevent="doThat"></a>

        <!-- 也可以只有修饰符 -->
        <form @submit.prevent></form>

        <!-- 仅当 event.target 是元素本身时才会触发事件处理器 -->
        <!-- 例如：事件处理器不来自子元素 -->
        <div @click.self="doThat">...</div>
        <!-- 阻止元素及其子元素的所有点击事件的默认行为 -->
        <div @click.prevent.self="doThat">...</div>
        <!-- 只会阻止对元素本身的点击事件的默认行为 -->
        <div @click.self.prevent="doThat">...</div>
        
        <!-- 添加事件监听器时，使用 `capture` 捕获模式 -->
        <!-- 例如：指向内部元素的事件，在被内部元素处理前，先被外部处理 -->
        <div @click.capture="doThis">...</div>

        <!-- 点击事件最多被触发一次 -->
        <a @click.once="doThis"></a>

        <!-- 滚动事件的默认行为 (scrolling) 将立即发生而非等待 `onScroll` 完成 -->
        <!-- 以防其中包含 `event.preventDefault()` -->
        <div @scroll.passive="onScroll">...</div>
       ```
     - key event listener:
       - `<input @keyup.enter="submit" />`
       - `<div @click.ctrl="doSomething">Do something</div>`Ctrl + click
       - alias of keyboard keys:
         - .enter
         - .tab
         - .delete (`Delete` or `Backspace`)
         - .esc
         - .space
         - .up
         - .down
         - .left
         - .right
         - .ctrl
         - .alt
         - .shift
         - .meta
       - alias of mouse keys:
         - .left
         - .right
         - .middle
       - precisely control key event:
          ```vue
          <!-- 当按下 Ctrl 时，即使同时按下 Alt 或 Shift 也会触发 -->
          <button @click.ctrl="onClick">A</button>
          <!-- 仅当按下 Ctrl 且未按任何其他键时才会触发 -->
          <button @click.ctrl.exact="onCtrlClick">A</button>
          <!-- 仅当没有按下任何系统按键时触发 -->
          <button @click.exact="onClick">A</button>
          ```
   - **v-model**: bind variable with tag value
      ```vue
      <!-- checkedNames is an Array -->
      <!-- while selected, value of checkbox will add into checkedNames -->
      <!-- while dis-checked, value will be remove from checkedNames -->
      <div>Checked names: {{ checkedNames }}</div>

      <input type="checkbox" id="jack" value="Jack" v-model="checkedNames" />
      <label for="jack">Jack</label>

      <input type="checkbox" id="john" value="John" v-model="checkedNames" />
      <label for="john">John</label>

      <input type="checkbox" id="mike" value="Mike" v-model="checkedNames" />
      <label for="mike">Mike</label>
      ```
      ```vue

      ```
   - `v-model.lazy`: will update variable every change instead of every input
   - `v-model.number`: convert input into number
   - `v-model.trim`: strip blank
6. dynamic attribute: 
   1. `v-bind:[attrName]="attValue"` or `:[attrName]="attValue"`
   2. `v-on:[actionName]="actionValue"` or `@[actionName]="actionValue"`
   3. if `attValue` is `null`, the `attrName` will unset; if `actionValue` is `null`, the listener will unset
7. modifiers
   1. `<form @submit.prevent="onSubmit">...</form>`


## Reactivity Fundamentals

### ref

- declare: `const count:Ref = ref(0)`
- modify:
  - `count ++`
  - `count.value = 0`

- ref could wrap basic data type and object
- attibute `.value` should be call first while accessing value in ref

### reactive

- reactive could only wrap object
- while using `ref` wrap an object, the type of `ref.value` is `reactive`
- create:  `const obj = reactive({key:"value"})`
- modify: 
  - modify attibute of object: `obj.key = "value1"`
  - modify object: `Object.assign(obj, newobj)`(change all attributes in `obj` to attributes in `newobj`)


### toRefs

- wraps all attributes or specified attribute in an object to ref

```typescript
// specified attributes {msg, code} convert to ref
let {msg, code} = toRefs(
    {
        msg: "ok",
        code: 200
    }
)
// single attribute to ref
let name = toRefs(person, 'age')
```

### computed
- automaticly compute self value after used value has changed
- `computed` returns type `ComputedRef`, `ComputedRef` -> `BaseComputedRef` -> `Ref`
```typescript
<template>
    <div>
        <p><input type="number" v-model="a">*<input type="number" v-model="b">=<span>{{ result }}</span></p>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
const a = ref(0)
const b = ref(0)
// method 1
// const result = computed(() => {
//     return a.value * b.value
// })
//method 2
const result = computed(
    {
        get(){
            return a.value * b.value
        },
        set(val){
            result.value = val
        }
    }
)
</script>
```

### watch
1. watch `ref([basic type])`
```typescript
const input = ref("")
const _ = watch(input, (newValue, oldValue)=>{
    console.log("old: ", oldValue);
    console.log("new: ", newValue);
})
```
2. watch `ref([object])`
```typescript
let person = ref({
    name: "li",
    age: 0
})

// not called back after attribute of person change
const personWatch = watch(person, (nv, ov) => {
    console.log("personWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// called back after attribute of person change
const personWatchDeep = watch(person, (nv, ov) => {
    console.log("personWatchDeep: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true })

// called back a time after person declared
const personWatchDeepIm = watch(person, (nv, ov) => {
    console.log("personWatchDeepIm: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true, immediate: true })
```
3. watch `reactive`
```typescript
// { deep: true } is the defult option for reactive and could not change
const rpersonWatchDeep = watch(rperson, (nv, ov) => {
    console.log("rpersonWatchDeep: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
const rpersonWatchDeepIm = watch(rperson, (nv, ov) => {
    console.log("rpersonWatchDeepIm: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true, immediate: true })
```
4. watch any attibute in `ref` or `reactive` (watch getter())
```typescript
let person = ref({
    name: "li",
    langs : {
      k1: v1,
      k2: v2
    }
})
// person.value.name is basic type, so a ()=>{} is needed
const personNameWatch = watch(()=>person.value.name, (nv, ov) => {
    console.log("personNameWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// person.value.langs is an object, could directly pass into watch
// could not called back after whole langs change to a new object, but called back if attibutes in langs changed
const personLangsWatch1 = watch(person.value.langs, (nv, ov) => {
    console.log("personLangsWatch1: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// could only called back after whole langs change to a new object, not called back if attibutes in langs changed
const personLangsWatch2 = watch(()=>person.value.langs, (nv, ov) => {
    console.log("personLangsWatch2: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})

// could called back on both whole langs change to a new object or attibutes in langs changed
const personLangsWatch3 = watch(()=>person.value.langs, (nv, ov) => {
    console.log("personLangsWatch3: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
}, { deep: true })
```
5. watch multiple variable
- `nv` and `ov` includes all variable watched
```typescript
const mulWatch = watch([
    () => person.value.name,
    () => person.value.langs,
], (nv, ov) => {
    console.log("mulWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
```
### watch effect
- automatic analyze targets to be watched
```typescript
// watchEffect
const a = ref(0)
const b = ref(0)
watchEffect(
    () => {
        if (a.value > 10 || b.value > 0)
            console.log("triggered")
    }
)
```

## Ref attribute of tag
- method 1
  ```vue
  <template>
      <div>
          <input type="text" ref="p1" />
          <button @click="() => console.log(p1.value)">
              output
          </button>
      </div>
  </template>

  <script setup lang="ts">
  import { ref } from 'vue'
  const p1 = ref()
  </script>
  <style scoped></style>
  ```
- method 2: use `useTemplateRef` (vue3.5+)
```vue
<template>
    <div>
        <input ref="ref-temp-input" />
        <button @click="() => console.log(p2.value)">
            output
        </button>
    </div>
</template>

<script setup lang="ts">
import { useTemplateRef } from 'vue'
const p2 = useTemplateRef('ref-temp-input')
</script>
<style scoped></style>
```

## Lifecycle
![Lifecycle](https://vuejs.org/assets/lifecycle.MuZLBFAS.png)

### options lifecycle hooks api
1. beforeCreate
2. created
3. beforeMount
4. mounted
5. beforeUpdate
6. updated
7. beforeDestroy
8. destroyed

### composition lifecycle hooks api
1. setup
2. onBeforeMount
3. onMounted
4. onBeforeUpdate
5. onUpdated
6. onBeforeUnmount
7. onUnmounted

```vue
<template>
    <div>
        <button @click="countAdd">count: {{ count }}</button>
    </div>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, onMounted, onBeforeUpdate, onUpdated } from 'vue'
const count = ref(0)
const countAdd = () => {
    count.value++
}

onBeforeMount(() => {
    console.log("onBeforeMount");
})
onMounted(() => {
    console.log("onMounted");
})
onBeforeUpdate(() => {
    console.log("onBeforeUpdate");
})
onUpdated(() => {
    console.log("onUpdated");
})

</script>
<style scoped></style>
```

## Components
### parameters pass
- use `defineProps(["langList", "selected"])` at imported vue to pass variable to imported vue from importing vue
- use `const emitSelect = defineEmits(["sendSelect"])` to declare a method at imported vue returing data to importing vue
- in importing vue at the tag of imported vue, use `@sendSelect="selectRec"` to define a local funtion `selectRec` to receive data from imported vue, `sendSelect` is defined in imported vue by `defineEmits(["sendSelect"])`
- in importing vue at the tag of imported vue, use `:langList="langs"` to pass local variable `langs` to imported vue, `langList` is defined in imported vue by `defineProps(["langList", "selected"])`
```vue
<!-- Test.vue -->
<template>
    <div>selected: {{ selected }}</div>
    <div v-for="lang in langList" v-key="lang.id">
        <li @click="sendSelect(lang.id)">{{ lang.name }}</li>
    </div>
</template>
<script setup lang="ts">
// defineProps and defineEmits could ignore import
// import { defineProps, defineEmits } from 'vue'
// from App.vue
defineProps(["langList", "selected"])
// to App.vue
const emitSelect = defineEmits(["sendSelect"])
const sendSelect = (id: number) => {
    console.log("clicked: ", id)
    // emit info to App.vue
    emitSelect("sendSelect", id)
}
</script>
```
```vue
<!-- App.vue -->
<template>
    <Test @sendSelect="selectRec" :langList="langs" :selected="peselected"></Test>
</template>
<script setup lang="ts">
// props emits test
import { ref, reactive } from 'vue'
const langs = reactive(
    [
        { id: 0, name: 'python' },
        { id: 1, name: 'java' },
        { id: 2, name: 'typescript' }
    ]
)
const peselected = ref()
const selectRec = (id:number)=>{
    console.log("app received: ", id);
    peselected.value = id
}
</script>
```

### dynamic component
```vue
<!-- currentTab 改变时组件也改变 -->
<!--tabs[currentTab] could be an object of imported component or string name of imported component-->
<component :is="tabs[currentTab]"></component>
```

## Router
- `pnpm i vue-router` to install route module
- add `index.ts` into directory `router`:
  ```typescript
  // @/router/index.ts
  import { createRouter, createWebHistory } from "vue-router"
  import Route1 from "../views/Route1.vue"
  import Route2 from "../views/Route2.vue"
  import Route3 from "../views/Route3.vue"

  export default createRouter(
      {
          history: createWebHistory(),
          routes: [
              {
                  name: "route1",
                  path: '/r1',
                  component: Route1,
              },
              {
                  name: "route2",
                  path: '/r2',
                  component: Route2,
              },
              {
                  name: "route3",
                  path: '/r3',
                  component: Route3,
              }
          ],
      }
  )
  ```
- import router in `main.ts` and then sign by `app.use(router)`
- import `RouterView` to `.vue` file and insert `<RouterView/>` to html code where to display dynamic in page components;
- import `RouterLink` to `.vue` file and insert RouterLink like: `<RouterLink active-class="router-active" to="/r1">r1</RouterLink>` to html code where to choose displayed components, `to="r1"` is the path configure in `/router/index.ts`;
- it's recommended to put `.vue` of each component in router into `views` directory instead of `components` directory
- use `:to="{path: '/r1'}"` or `:to="{name: '/route1'}"` in `<RouterLink>` tag is recommended

```vue
<!-- RouteTest.vue -->
<template>
    <div style="text-align: center;">
        <div style="border:1px solid; border-color: black; border-radius:5px">Route Test</div>
        <div id="router-link">
            <RouterLink active-class="router-active" to="r1">r1</RouterLink>
            <RouterLink active-class="router-active" to="r2">r2</RouterLink>
            <RouterLink active-class="router-active" to="r3">r3</RouterLink>
        </div>
        <div id="contend">
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

#contend {
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
```
### Router work mode
1. history mode
   - configure as :`createRouter({history: createWebHistory(),...})`
   - without `#` in url
   - need nginx to handle url path
2. hash mode
   - configure as :`createRouter({history: createWebHashHistory(),...})`
   - good compatibility with url path handle
   - with `#` in url, bad `SEO` optimization

### chill router and parameter pass
#### configure child router:
```typescript
{
    name: "route1",
    path: '/r1',
    component: Route1,
    children:[
        {
            path:"r1-1",
            component: Route1_1
        },
        {
            name: "r1_2",
            // params placeholder, "?" means could be empty
            path:"r1-2/:username?/:password?",
            component: Route1_2,
            
        }
    ]
},
```
#### query
- use `<RouterLink :to="{ path: '/r1/r1-1', query: r1_data[1] }">r1-1-1</RouterLink>` to send query to child router
- at child vue, receive query as following:
  ```typescript
  import { toRefs } from 'vue'
  import { useRoute } from 'vue-router'
  let route = useRoute()
  let {query} = toRefs(route)
  ```
#### params
- use `<RouterLink :to="{ name: 'r1_2', params: r1_data[2] }">r1-2</RouterLink>`
- target route in `:to` should use name instead of path
- key in `r1_data[2]` should follow params placeholder in configured router
- at child vue, receive params as following:
  ```typescript
  import { toRefs } from 'vue'
  import { useRoute } from 'vue-router'
  let route = useRoute()
  let { params } = toRefs(route)
  ```

### route props
1. configuration (only params)
   - set `props:true` in router:
   - `defineProps(["username", "password"])` in vue, then derectly use `username` and `password` as variable
2. function
   - set `props(route){return route.query}`, return could be `route.query` or any else
   - `defineProps(["username", "password"])` in vue, then derectly use `username` and `password` as variable
    ```typescript
    ```
3. object
   - set `props:{}`, but value in `{}` could not changed

### route replace
- replace mode could not backward to last page
- add replace to `RouterLink` as `<RouterLink replace .../>`

### programming navigator
- `RouterLink` will be the `<a>` tag in HTML source code
- `useRouter().push("/xxx")` could forward to xxx
```vue
<template>
    <button @click="clickFunc('r1_2', r1_data)">r1-2-1</button>
    <RouterView />
</template>

<script setup lang="ts">
import { reactive } from 'vue';
import { RouterView, useRouter } from 'vue-router';
const router = useRouter()
const r1_data = reactive(
    {
        username: 'user',
        password: 'user'
    }
)
const clickFunc = (route: string, data: {}) => {
    router.push({ name: route, params: data })
}

</script>
<style scoped></style>
```

### redirect
- add new route to routes and redirect to exists route:
  ```typescript
  {
      path: '/',
      redirect: "/r3"
  }
  ```

## Pinia
1. `pnpm i pinia`
2. import and use pinia in main.ts:
    ```typescript
    import {createPinia} from 'pinia'
    // ......
    const pinia = createPinia()
    app.use(pinia)
    ```
### store data in pinia
```typescript
import {defineStore} from 'pinia'

export const useMulStore = defineStore("mul", {
    state(){
        return {
            mul: 0
        }
    }
})
```
### get data from pinia
```vue
<template>
    <div>
        <input type="number" v-model="num" />
        <p>{{ MulStore.mul }}</p>
    </div>
</template>

<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { useMulStore } from "../store/piniatest1.ts"
const num = ref(0)
const mulStore = useMulStore()
</script>
<style scoped></style>
```
### modify data in pinia
1. direct assignment
```typescript
mulStore.mul = num.value
```
2. batch assignment
```typescript
mulStore.$patch(
    {
        num: 10,
        mul: 100
    }
)
```
3. use actions:
```typescript
export const useMulStore = defineStore("mulstore", {
    actions:{
        multiple(num:number){
            this.mul = num ** 2
        }
    },
    state(){
        return {
            mul: 0
        }
    }
})
```
- then call the action like a method: `mulStore.multiple(num.value)`

### storeToRefs
compareing with `toRefs`, `storeToRefs` only wrap data in a pinia object
- import: `import { storeToRefs } from 'pinia';`
- usage: `const { mul } = storeToRefs(mulStore)`

### getters
```typescript
export const useMulStore = defineStore("mulstore", {
    actions:{
        multiple(num:number){
            this.mul = num ** 2
        }
    },
    state(){
        return {
            mul: 0
        }
    },
    getters:{
        log2():number{
            return Math.log2(this.mul)
        }
    }
})
```
- use as attribute in state: `const { mul, log2 } = storeToRefs(mulStore)`

### subscript
- call function if value in store changed
```typescript
// piniatest1.ts
mulStore.$subscribe((mutate, state) => {
    console.log("changed mulStore: ", mutate)
    localStorage.setItem("mul", JSON.stringify(state.mul))
})
```
```typescript
// xx.vue@<script>
const mul = ref(JSON.parse(localStorage.getItem("mul") as string) || 0)
```

### composed format of pinia
```typescript
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useMulStore = defineStore("mulstore", () => {
    const mul = ref(JSON.parse(localStorage.getItem("mul") as string) || 0)
    const log2 = computed(() => Math.log2(mul.value))
    const multiple = (num: number) => { mul.value = num ** 2 }
    return { mul, log2, multiple }
})
```

## Components message pass
### defineProps in Father-child structure
```vue
<!-- Father.vue -->
<template>
    <div>
        <div>
            <p>fatherData: {{ fatherData }}</p>
            <p>childData: {{ childData }}</p>
        </div>
        <Child :father-data="fatherData" :send-data="getData"></Child>
    </div>
</template>

<script setup lang="ts">
import Child from './Child.vue';
import { ref } from 'vue'
let fatherData = ref("fatherData...")
let childData = ref("")
const getData = (data: string) => {
    childData.value = data
}
</script>
<style scoped>
```

```vue
<!-- Child.vue -->
<template>
    <div>
        <p>childData: {{ childData }}</p>
        <p>fatherData: {{ fatherData }}</p>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
let childData = ref("childData...")
const { fatherData, sendData } = defineProps(["fatherData", "send-data"])
sendData(childData.value)
</script>
```

### custom event
```vue
<!-- Father.vue -->
<Child @custom-event="custFunc"></Child>
<script>
const custFunc = ()=>{
  ...
}
</script>

<!-- Chile.vue -->
<script>
const emit = defineEmits(["customEvent"])
emit("customEvent", args)
</script>
```

### mitt
- bind event in advance
- `pnpm i mitt` to install mitt

```typescript
// utils/emitter.ts
import mitt from 'mitt'

const emitter = mitt()

emitter.on('test1', (data) => {
    console.log("emitter test1");
    console.log(data);
}) // bind test1
emitter.emit("test1", "emiiting data") // emit data
emitter.off("test1") // unbind test1
emitter.all.clear() // unbind all

export default emitter
```
```typescript
// 1.vue@script
import emitter from '../utils/emitter'
const emit = defineEmits(["customEvent"])
const ChildData1 = ref("")
emitter.on("ChildData", (data) => {
    ChildData1.value = data as string
})
onUnmounted(()=>{
    emitter.off("ChildData")
})

// 2.vue@script
import emitter from '../utils/emitter'
watchEffect(() => {
    emitter.emit("ChildData", childData.value)
})
```

### v-modle on components
#### bind variable to components by `v-model`:
  - method1: `<MyCmp v-model="cinput"></MyCmp>`
  - mehtod2: `<MyCmp :modelValue="cinput" @update:modelValue="cinput=$event"></MyCmp>`
- components:
```vue
<!-- MyCmp.vue -->
<template>
    <div>
        <input type="text" :value="modelValue" @input="inputUpFunc((<HTMLInputElement>$event.target).value)" />
    </div>
</template>

<script setup lang="ts">
defineProps(["modelValue"])
const emit = defineEmits(["update:modelValue"])
const inputUpFunc = (value: string) => {
    emit("update:modelValue", value)
}
</script>

<style scoped></style>
```

#### bind variable to components by custom name
- custom name by "intext"
- change `v-model=` to `v-model:intext=` as : `<MyCmp v-model:intext="cinput"></MyCmp>`
- change props and emits name:
  - `:value="intext"`
  - `defineProps(["intext"])`
  - `defineEmits(["update:intext"])`
  - `emit("update:intext", value)`
```vue
<!-- MyCmp.vue -->
<template>
    <div>
        <input type="text" :value="intext" @input="inputUpFunc((<HTMLInputElement>$event.target).value)" />
    </div>
</template>

<script setup lang="ts">
defineProps(["intext"])
const emit = defineEmits(["update:intext"])
const inputUpFunc = (value: string) => {
    emit("update:intext", value)
}
</script>
```
### $attrs
- `v-bind="$attrs"` will pass attrs to child components

### $refs / $parent
- visit variables in child components:
  - `defineExpose({childData})` in child to expose variable to parent component
  - use ref to visit child node:
    ```vue
    <Child ref="c1" .../>
    ...
    const c1 = ref()
    const c1Func = () => {
        console.log(c1.value.childData)
    }
    ```
  - `$refs` is a `ref(object)` including all children components
  - `{{ $refs }}` -> `{ "c1": { "childData": "childData..." } }`
- visit variables in parent component:
  - `defineExpose({childData})` in `child.vue`
  - visit `childData` in `mago.vue` by `$parent.childData`

### provide inject
- in father component: `provide('key', value)`, value should pass ref or reactive
  ```typescript
  import { provide, ref } from 'vue'
  const fatherData = ref("fatherData...")
  provide('fatherData', fatherData)
  ```
- in child and grandchild and ... component: inject('key', 'default')
  ```typescript
  import { inject } from 'vue'
  console.log("inject fatherData in Child.vue: ", inject('fatherData', ref("defaultValue")).value)
  ```
### [Pinia](#pinia)

### slot
#### default slot
- add `<slot></slot>` to vue and add content between tag while using as components:`<SlotTest>content</SlotTest>`, content will display in slot

#### named slot
- `<slot name="xxx"></slot>`
- method 1 (not recommended): place content into xxx by `v-slot:xxx` or `#xxx` for example: `<SlotTest v-slot:xxx>content</SlotTest>`
- method 2: wrap content in `<template>` and set `v-slot:xxx` in template as :`<template v-slot:xxx>content</template>` or `<template #xxx>content</template>
```vue
<SlotTest>
    <template #test1>
        <table>
            <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="i in slotData" :key="i.id">
                    <td>{{ i.id }}</td>
                    <td>{{ i.name }}</td>
                </tr>
            </tbody>
        </table>
    </template>
</SlotTest>
```
```vue
<!-- SlotTest.vue -->
<template>
    <div>
        <slot name="test1"></slot>
    </div>
</template>
```

#### scope slot
```vue
<SSlotTest>
    <template v-slot:xxx="scopedSlot">
        <table>
            <thead>
                <tr>
                    <th>id</th>
                    <th>name</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="i in scopedSlot.data" :key="i.id">
                    <td>{{ i.id }}</td>
                    <td>{{ i.name }}</td>
                </tr>
            </tbody>
        </table>
    </template>
</SSlotTest>
```

```vue
<template>
    <div>
        <slot name="xxx" :data="scopedSlotData"></slot>
    </div>
</template>
<script setup lang="ts">
import {ref} from 'vue'
const scopedSlotData = ref(
    [
        { id: 0, name: 'java' },
        { id: 1, name: 'py' }
    ]
)
</script>
<style scoped>
```

### sumary
![sumary](https://raw.githubusercontent.com/lijunjie2232/java_web_study/refs/heads/master/note/components_message_pass.png)

## apis
### shallowRef / shallowReactive
  - shallow wrap and monitor reaction of variable
### readonly / shallowReadonly
  - create a readonly variable from existing variable(ref or reactive)
### toRaw / markRaw
  - toRaw unwrap ref or reactive to original variable, not recommonded unless pass to outer part of system
  - markRaw warp an object which could not be warpped into ref or reactive
### customRef
```typescript
// hooks/useDelayMsg.ts
import { customRef } from 'vue'

export default function (initValue?: string, latency?: number) {
    let msgValue = initValue === null ? "" : initValue
    if (latency === null) latency = 1000
    let timer: number
    const msg = customRef(
        (track, trigger) => {
            return {
                get() {
                    track()// vue will monite change of msg
                    return msgValue
                },
                set(value:string) {
                    msgValue = value
                    clearTimeout(timer)
                    timer = setTimeout(
                        () => {
                            trigger()// notice vue the change of msg
                        },
                        latency
                    )
                },
            }
        }
    )
    return { msg }
}
```

### Teleport
- put elements in `<Teleport>` into target tag, even if the target tag is in another component
```vue
<Teleport to="#testdiv"><!-- put into element which id is #testdiv -->
    <div class="splash-bg" v-show="isSplash"></div>
    <div class="splash" v-show="isSplash">
        <h3>Splash Window</h3>
        <button @click="isSplash = !isSplash">close</button>
    </div>
</Teleport>
```

