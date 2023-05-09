const {createApp} = Vue

const app = createApp({
    data(){
        return{
            cardType:undefined,
            data: undefined,
            cardOptionColor:undefined 
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/clients/current').then( element =>{
            this.data = element    
            console.log("hola bienvenido")  
            })
        },

        createCardMethod(){
            console.log(this.cardType)
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.cardOptionColor}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{  
                console.log("funciona")
                Swal.fire(
                    'Card Added!',  
                    'Your new card has being added to account, and is being shipped to your address!',
                    'success'
                )

                document.querySelector('.swal2-confirm').addEventListener('click',() =>{window.location.href='/web/cards.html'})
            }).catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Issue solciting card',
                    text: err.response.data,
                })
            })
        },

        logOut(){
            axios.post('/api/logout').then(response =>{
                window.location.href='/web/index.html'
            }).catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Issue Loging out',
                    text: err.response.data,
                })
            })
        }

    }
})

app.mount("#app")