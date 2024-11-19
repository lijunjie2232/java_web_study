import {defineStore} from 'pinia'

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