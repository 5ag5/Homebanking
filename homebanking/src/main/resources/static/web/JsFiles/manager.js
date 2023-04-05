const { createApp } = Vue

const app = createApp( {
    data(){
        return {
            clients:[ ],
            firstNam:'',
            lastNam: '',
            emailInp:''
        }
    },
    created(){
            this.getData()
    },
    methods: {
        async getData(){
            try{
            axios.get('http://localhost:8080/rest/clients')
            .then(elemento => {
                this.clients=elemento.data._embedded.clients    
                console.log(elemento.data._embedded.clients)
                console.log("este funciona")
            })
            }catch{
                console.log(err)
            }
        },

        async addClient(){
            let variableNombre = this.firstNam
            let variableApellido = this.lastNam
            let variableCorreo = this.emailInp

            console.log(variableNombre)
            console.log(variableApellido)
            console.log(variableCorreo)

            if((variableNombre.length && variableApellido.length && variableCorreo.length) >1){
                this.postClient(variableNombre, variableApellido, variableCorreo)
            } else{
                console.log("No se ejecuta proceso");
            }
        },
            postClient(firstName, lastName, email){
            axios({
                method:'post',
                url:'http://localhost:8080/rest/clients',
                data:{
                    firstName: firstName,
                    lastName: lastName,
                    email: email
                }
            });
        }

    },

    computed: {

    }

})

app.mount('#app')