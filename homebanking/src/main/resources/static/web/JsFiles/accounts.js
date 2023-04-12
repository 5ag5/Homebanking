const { createApp } = Vue

const app = createApp({
    data(){
        return{
            accounts: [],
            totalBalance: 0,
            clientes: [],
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
                    this.accounts = elemento.data.accounts
                    this.clientes = elemento.data;
                    console.log( this.accounts)

                    this.valoreCards()
                    
            })
            }catch{
                console.log(err)
            }
        },

        valoreCards(){
            let totalBalanceTemp =0

            for(let elemento of this.accounts){
                totalBalanceTemp = totalBalanceTemp + elemento.balance;
            }

            this.totalBalance = totalBalanceTemp.toLocaleString('en-US', { style: 'currency', currency: 'USD' })

            this.cliente = this.clientes.firstName + " " + this.clientes.lastName
            console.log(this.cliente)   

            this.conteoCuentas= this.accounts.length;

            let indiceT = 0;
            this.accounts.forEach(element => {
                element.balance = element.balance.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
                indiceT = element.creationDate.indexOf("T")

                element.creationDate = element.creationDate.slice(0,indiceT) + " " + element.creationDate.slice(indiceT+1,element.creationDate.length)
            });

            console.log(indiceT)


        },

    },

    computed: {

    },
})

app.mount('#app')