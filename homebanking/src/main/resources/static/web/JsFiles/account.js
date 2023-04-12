const {createApp} = Vue;

const app = createApp({
    data(){
        return {
            transactions: undefined,
            cuentas: undefined,
            numeroCuenta: undefined,
            params: undefined,
            id: undefined,
        }
    },

    created(){
        this.getData();
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/accounts')
            .then(elemento =>{
                    this.cuentas = elemento.data
                    
                    this.params = new URLSearchParams(location.search)
                    this.id = this.params.get("id")

                    console.log(this.id)

                    this.cuenta = this.cuentas.find(cuenta => cuenta.id.toString()=== this.id)
                    this.transactions = this.cuenta.transactions
                    this.transactions = this.transactions.sort((x,y) => y.id - x.id)

                    console.log(this.cuenta)
                    console.log(this.transactions)

                    this.getNumeroCuenta()
                    this.convertirADolares()
                } 
            )
        },

        getNumeroCuenta(){
            this.numeroCuenta = this.cuenta.number    
            console.log(this.numeroCuenta)
        },

        convertirADolares(){
            let indiceT =0
            this.transactions.forEach(element => {
                element.amount = element.amount.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

                indiceT = element.date.indexOf("T")
                element.date = element.date.slice(0,indiceT) + " " +  element.date.slice(indiceT+1,element.date.length)
            })

        }


    },

})

app.mount('#app')