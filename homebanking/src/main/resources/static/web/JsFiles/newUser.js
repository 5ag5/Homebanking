const {createApp} = Vue

const app = createApp({
data(){
    return{
        firtName: undefined, 
        lastName: undefined,
        email: undefined,
        password: undefined
    }
},
created(){

},

methods:{
    registrarionNewUser(){
        axios.post('/api/clients',`firstName=${this.firtName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
        .then(response => {
            console.log('registered')
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response2 => {
                console.log("funciona2")
                window.location.href = '/web/accounts.html'
            }).catch(err => {
                Swal.fire({
                    icon: 'error',
                    title: 'Issue with Operation',
                    text: err.response.data,
                })            
            })
            }
        ).catch(err => {
            Swal.fire({
                icon: 'error',
                title: 'Issue with Operation',
                text: err.response.data,
            })
        })
    },
},
})

app.mount("#app")
