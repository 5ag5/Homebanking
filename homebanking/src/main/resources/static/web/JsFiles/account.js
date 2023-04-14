const {createApp} = Vue;

const app = createApp({
    data(){
        return {
            transactions: undefined,
            sumTransactions: 0,
            cuentas: undefined,
            numeroCuenta: undefined,
            params: undefined,
            id: undefined,
        }
    },

    created(){
        this.params = new URLSearchParams(location.search)
        this.id = this.params.get("id")
        this.getData();
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/accounts/' + this.id)
            .then(elemento =>{
                    this.cuentas = elemento.data
                    this.transactions = this.cuentas.transactions
                    this.transactions = this.transactions.sort((x,y) => y.id - x.id)

                    console.log(this.id)
                    console.log(this.cuentas)   

                    this.callSumTransactions()
                    this.getNumeroCuenta()
                    this.convertirADolares()
                } 
            )
        },

        getNumeroCuenta(){
            this.numeroCuenta = this.cuentas.number    
            console.log(this.numeroCuenta)
        },

        convertirADolares(){
            let indiceT =0
            this.transactions.forEach(element => {
                element.amount = element.amount.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

                indiceT = element.date.indexOf("T")
                element.date = element.date.slice(0,indiceT) + " " +  element.date.slice(indiceT+1,element.date.length)
            })

            this.sumTransactions = this.sumTransactions.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
        },

        callSumTransactions(){
            for(let elemento of this.transactions){
                if(elemento.type === "CREDITO"){
                    this.sumTransactions = this.sumTransactions + elemento.amount
                }else{
                    this.sumTransactions = this.sumTransactions - elemento.amount
                }
            }

            this.sumTransactions = this.sumTransactions.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

        }


    },

})

app.mount('#app')