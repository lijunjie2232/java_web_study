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