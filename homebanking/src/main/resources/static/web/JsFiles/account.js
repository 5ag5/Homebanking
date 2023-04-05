const {createApp} = Vue;

const app = createApp({
    data(){
        return {
            transactions: undefined,
            cuentas: undefined,
            numeroCuenta: undefined,
            params: undefined,
            id: undefined
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

                    console.log( this.cuenta)

                    this.getNumeroCuenta()
                } 
            )
        },

        getNumeroCuenta(){
            this.numeroCuenta = this.cuenta.number    
            console.log(this.numeroCuenta)
        }


    },

})

app.mount('#app')