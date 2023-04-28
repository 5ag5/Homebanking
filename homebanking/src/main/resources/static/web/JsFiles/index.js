const {createApp} = Vue 

const app = createApp({
    data(){
        return{
            data: undefined,
            usuario: '',
            contrasena: ''
        }
    },

    created(){
        console.log("HOLA")
    },

    methods:{
        getData(){
        },

        postUsuario(){
            console.log(this.usuario)
            console.log(this.contrasena)
            axios.post('/api/login',`email=${this.usuario}&password=${this.contrasena}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => {
                console.log('signed in!!!')
                window.location.href = '/web/accounts.html'
                })
                .catch(err => {
                    console.log(err)
                    Swal.fire({
                        icon: "error",
                        title: "Login Error",
                        text: "Incorrect informacion Added or User doesn't excist"
                    })
                });
        },

        loginManager(){
            axios.post('api/login',`email=${this.usuario}&password=${this.contrasena}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
                // window.location.href = '/web/manager.html'
                console.log("this is correct")
            })
        }

    },  

})

app.mount("#app")