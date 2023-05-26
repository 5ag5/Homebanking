const { createApp } = Vue

const app = createApp({
    data(){
        return{
            accounts: [],
            totalBalance: 0,
            totalBalanceLoans: 0,
            clientes: [],
            cliente: undefined,
            accountChoise: undefined,
            countingAccounts: 0,
            clientLoans: [],
            identificador: 0
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
                    this.accounts = elemento.data.accounts
                    this.clientes = elemento.data;
                    this.clientLoans = elemento.data.clientLoans
                    this.identificador = elemento.data.id
                    console.log(this.clientes)
                    console.log(this.clientLoans)
                    console.log(this.identificador)


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
        },

        logOut(){            
            console.log("funciona")
            axios.post('/api/logout').then(response => {
                console.log('signed out!!!')
                window.location.href='/web/index.html'   
            })
            .catch(err => console.log(err))
        },

        eliminateAccount(number){
            console.log(number)
            let account = 0
            let valueBalance = 0

            this.accounts.forEach( element =>{
                if(element.number === number){
                    account = element
                }  
            })

            console.log(account)

            valueBalance = parseFloat(account.balance.slice(1,account.balance.length))
            console.log(valueBalance)

            if(valueBalance <=0){
                console.log('se borra')
            axios.post('/api/clients/current/accounts/delete',`account=${number}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
            swal.fire({
                title:'Message Confirmation',
                text: 'Your account has being Deleted',
                icon:'success',
                didOpen:() => {
                    document.querySelector('.swal2-confirm').addEventListener('click', () =>{location.reload(true)})
                },
            })

            }).catch(err => {
                console.log(err)
            })
            }else{
                console.log('No se borra')
                Swal.fire({
                    icon: 'error',
                    title: 'Error errasing account',
                    text: 'account still has a balance, it cannot be deleted'
                  })
            }

        },

        openAccountDetails(id){
            console.log(id)
            localStorage.setItem( 'accountID', JSON.stringify(id))
            window.location.href = '/web/account.html'
        },

        createAccount(){
            console.log("esto funciona")
            console.log(this.accountChoise)
            axios.post('/api/clients/current/accounts',`accountType=${this.accountChoise}`)
            .then( response =>{
                Swal.fire({
                    title:'Message Confirmation',
                    text: 'Your account has being created',
                    icon:'success',
                    didOpen:() => {
                        document.querySelector('.swal2-confirm').addEventListener('click', () =>{location.reload(true)})
                    },
                })
            }).catch(err =>{
                Swal.fire({
                    icon: 'error',
                    title: 'Error creating account',
                    text: err.response.data,
                  })
            })
        },

        pdftransactions(){
        }

    },

    computed: {

    },
})

app.mount('#app')