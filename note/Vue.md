# Vue3

- [Vue3](#vue3)
  - [Vite](#vite)
  - [Vue2 to Vue3](#vue2-to-vue3)


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

