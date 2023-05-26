const {createApp} = Vue

const app = createApp({
    data(){
        return {
            client: undefined,
            accounts: [],
            destinationType: undefined,
            accountNumberOrigin:0,
            balanceAccount:0,
            filterAccounts: [],
            description: undefined,
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
                this.getBalanceAccount()
            })
        },

        filterAccountsMethod(){
            this.filterAccounts = this.accounts.filter(element => element.number !== this.accountNumberOrigin)
            console.log(this.filterAccounts)
        },
        
        transferTransactions(){
            if(this.amountTransfer <=0){
                Swal.fire({
                    icon: 'error',
                    title: 'Something went wrong',
                    text: 'Negative numbers or 0 value are not allowed',
                })
            }else{
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
            }   
        },

        getBalanceAccount(){
            this.accounts.forEach(elements =>{

                if(elements.number === this.accountNumberOrigin){
                    return this.balanceAccount =  Math.round(elements.balance*100)/100
                }
            })
        },

        logOut(){
            axios.post('/api/logout').then(element =>{
                console.log("LogOut Correct")
                window.location.href='/web/index.html'
            }).catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Issue solciting card',
                    text: err.response.data,
                })
            })
        }

    },

    computed:{

    },
})

app.mount("#app")