const { createApp } = Vue

const app = createApp({
    data(){
        return{
            accounts: [],
            totalBalance: 0,
            clientes: undefined,
            cliente: undefined,
            conteoCuentas: 0
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
                    console.log(elemento.data)
                    this.accounts = elemento.data.accounts
                    this.clientes = elemento.data;

                    this.valoreCards()
                    
            })
            }catch{
                console.log(err)
            }
        },

        valoreCards(){
            for(let elemento of this.accounts){
                this.totalBalance = this.totalBalance+ elemento.balance;
            }

            this.cliente = this.clientes.firstName + " " + this.clientes.lastName
            console.log(this.cliente)   

            this.conteoCuentas= this.accounts.length;
            
        },

    },

    computed: {

    },
})

app.mount('#app')