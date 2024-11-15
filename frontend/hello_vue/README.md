# Vue3

- [Vue3](#vue3)
  - [Vite](#vite)
  - [Vue2 to Vue3](#vue2-to-vue3)
  - [Tips](#tips)
  - [Reactivity Fundamentals](#reactivity-fundamentals)
    - [ref](#ref)
    - [reactive](#reactive)
    - [toRefs](#torefs)
    - [computed](#computed)
    - [watch](#watch)


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
- 

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
const personNameWatch = watch(()=>person.value.name, (nv, ov) => {
    console.log("personNameWatch: ")
    console.log("old: ", ov);
    console.log("new: ", nv);
})
```