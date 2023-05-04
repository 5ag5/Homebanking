const {createApp} = Vue

const app = createApp({
    data(){
        return {
            client: undefined,
            accounts: [],
            destinationType: undefined,
            accountNumberOrigin:undefined,
            description: "description",
            accountNumberDestination: undefined,
            amountTransfer: undefined
        }
    },

    created(){
        this.getData()
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/clients/current/').then(element =>{
                this.client = element
                this.accounts = element.data.accounts
                console.log(this.accounts)
                console.log(this.amountTransfer)
            })
        },
        
        transferTransactions(){

            axios.post('/api/transactions',`amount=${this.amountTransfer}&description=${this.description}&numberOrigin=${this.accountNumberOrigin}&numberDestination=${this.accountNumberDestination}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}}).then(element =>{ 
                Swal.fire(
                    'Transaction Succesful!',
                    'Transaction made to destination account',
                    'success'
                )
                console.log("operacion correcta")

                document.querySelector('.swal2-confirm').addEventListener('click',() =>{location.reload(true)})
            }).catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Issue transfering account',
                    text: err.response.data,
                })
                console.log(err.response.data)
            })
            console.log(this.destinationType)
            console.log(this.accountNumberOrigin)
            console.log(this.accountNumberDestination)
            console.log(this.amountTransfer)
        },

        logOut(){
            axios.post('/api/logout').then(element =>{
                console.log("LogOut Correct")
                window.location.href='/web/index.html'
            })
        }

    },
})

app.mount("#app")