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