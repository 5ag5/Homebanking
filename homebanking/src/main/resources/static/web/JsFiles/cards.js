const {createApp} = Vue 

const app = createApp({
    data(){
        return {
            datos:undefined
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/creditCards').then(elemento => {
                this.datos = elemento
                console.log(this.datos)
            })
        },
    },
})

app.mount("#app")