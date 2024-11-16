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
  - [Components](#components)


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

## Components
- use `defineProps(['varName'])` to pass info from importing vue to imported vue
```vue
<script setup>
defineProps(['title'])
</script>
<template>
  <h4>{{ title }}</h4>
</template>
```
- use `defineExpose({a:a.value,b:b.value})` or shorten to `defineExpose({a,b})` to pass variable in imported vue to importing vue

