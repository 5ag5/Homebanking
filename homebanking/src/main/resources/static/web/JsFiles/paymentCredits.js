const { createApp } = Vue

const app = createApp({
    data(){
        return{
            outstandingLoans: [],
            clientAccounts: [],
            selectedLoan: undefined,
            accountSelected: undefined,
            balanceRemaining: undefined,
            paymentsChosen: undefined,
            paymentsRemaining: undefined
        }
    },
    created(){
        this.getData()
    },

    methods: {
        async getData(){
            try{
                axios.get('http://localhost:8080/api/clients/current')
                .then(elemento =>{
                    console.log(elemento.data.accounts)
                    this.outstandingLoans = elemento.data.clientLoans
                    this.clientAccounts = elemento.data.accounts
                    console.log(this.outstandingLoans)

            })
            }catch{
                console.log(err)
            }
        },

        getBalanceLoans(){
            console.log(this.accountSelected)
            this.outstandingLoans.forEach(element => {
                if(element.serialNumber === this.selectedLoan){
                this.balanceRemaining = element.amount
                this.paymentsRemaining = element.payments
                }
            });

        },

        payLoan(){
            // console.log(this.selectedLoan)
            // console.log(this.paymentsChosen)
            axios.post('/api/clients/current/loans/payment',`serialNumber=${this.selectedLoan}&numberOfPayments=${this.paymentsChosen}&accountNumber=${this.accountSelected}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response =>{
                console.log("Funciono!!!")
                Swal.fire(
                    'Payment Processed',
                    'Your Payment has being processed correctly into your loan',
                    'success'
                )
            }).catch(err =>{
                console.log(err)
                console.log(err.response.data)
                Swal.fire({
                    icon: 'error',
                    title: 'Error processing Payment',
                    text: err.response.data,
                })
            })
        },

        logOut(){
            console.log("funciona")
            axios.post('/api/logout').then(response => {
                console.log('signed out!!!')
                window.location.href='/web/index.html'   
            })
            .catch(err => console.log(err))
        },

    },

    computed: {

    },
})

app.mount('#app')