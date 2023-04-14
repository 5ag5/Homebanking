const { createApp } = Vue

const app = createApp({
    data(){
        return{
            accounts: [],
            totalBalance: 0,
            totalBalanceLoans: 0,
            clientes: [],
            cliente: undefined,
            countingAccounts: 0,
            clientLoans: []
        }
    },
    created(){

        this.getData()
    },

    methods: {
        async getData(){
            try{
                axios.get('http://localhost:8080/api/clients/1')
                .then(elemento =>{
                    this.accounts = elemento.data.accounts
                    this.clientes = elemento.data;
                    this.clientLoans = elemento.data.clientLoans
                    console.log( this.clientes)
                    console.log( this.clientLoans)

                    this.valueCards()
                    this.clientName()
                    
            })
            }catch{
                console.log(err)
            }
        },

        valueCards(){
            let totalBalanceTemp =0
            let totalBalanceTempLoans =0

            for(let elemento of this.accounts){
                totalBalanceTemp = totalBalanceTemp + elemento.balance;
            }

            for(let element of this.clientLoans){
                totalBalanceTempLoans  = totalBalanceTempLoans + element.amount
            }

            this.totalBalanceLoans  = totalBalanceTempLoans.toLocaleString('en-US', { style: 'currency', currency: 'USD' });

            this.totalBalance = totalBalanceTemp.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

            console.log(this.totalBalanceLoans)   

            this.countingAccounts= this.accounts.length;

            let indiceT = 0;
            this.accounts.forEach(element => {
                element.balance = element.balance.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
                indiceT = element.creationDate.indexOf("T")

                element.creationDate = element.creationDate.slice(0,indiceT) + " " + element.creationDate.slice(indiceT+1,element.creationDate.length)
            });

            this.clientLoans.forEach(loan =>{
                loan.amount = loan.amount.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
            })


        },

        clientName(){
            this.cliente = this.clientes.firstName + " " + this.clientes.lastName
        }

    },

    computed: {

    },
})

app.mount('#app')