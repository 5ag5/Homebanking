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
            axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.cardOptionColor}`)
            .then(response =>{  
                console.log("funciona")
                Swal.fire(
                    'Card Added!',
                    'Your new card has being added to account, and is being shipped to your address!',
                    'success'
                )
            })
        },

        logOut(){
            axios.post('/api/logout').then(response =>{
                window.location.href='/web/index.html'
            })
        }

    }
})

app.mount("#app")