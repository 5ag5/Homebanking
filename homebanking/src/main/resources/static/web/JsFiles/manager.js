const { createApp } = Vue

const app = createApp( {
    data(){
        return {
            clients:[],
            firstNam:'',
            lastNam: '',
            emailInp:'',
            loansAvailable: [],
            nameloan: undefined,
            maximumAmount: undefined, 
            numberOfPayments: undefined,
            interestLoan: undefined
        }
    },
    created(){
            this.getData()
            this.getLoanData()
    },
    methods: {
        async getData(){
            try{
            axios.get('http://localhost:8080/rest/clients')
            .then(elemento => {
                this.clients=elemento.data._embedded.clients    
                console.log(elemento.data._embedded.clients)
                console.log("este funciona")
                console.log(this.loansAvailable)
            })
            }catch{
                console.log(err)
            }
        },

        async getLoanData(){
            axios.get('/api/loans').then(expected =>{
                this.loansAvailable = expected
            })
        },

        async addClient(){
            let variableNombre = this.firstNam
            let variableApellido = this.lastNam
            let variableCorreo = this.emailInp

            console.log(variableNombre)
            console.log(variableApellido)
            console.log(variableCorreo)

            if((variableNombre.length && variableApellido.length && variableCorreo.length) >1){
                this.postClient(variableNombre, variableApellido, variableCorreo)
            } else{
                console.log("No se ejecuta proceso");
            }
        },
            postClient(firstName, lastName, email){
            axios({
                method:'post',
                url:'http://localhost:8080/rest/clients',
                data:{
                    firstName: firstName,
                    lastName: lastName,
                    email: email
                }
            });
        },

        createLoan(){
            console.log(this.nameloan)
            console.log(this.maximumAmount)
            console.log(this.numberOfPayments)
            console.log(this.interestLoan)

            let arrayPayments = []
            arrayPayments = this.numberOfPayments.split(",")
            console.log(arrayPayments)

            axios.post('/api/loans/newLoan',
            {name:this.nameloan,
            MaxAmount:this.maximumAmount,
            payments: arrayPayments,
            interest: this.interestLoan
            }).then(response =>{
                Swal.fire(
                    'Transaction Succesful!',
                    'Loan created succesfully',
                    'success'
                )
            }).catch(err =>{
                console.log(err)
                Swal.fire({
                    icon: "error",
                    title: "Submit Error",
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
        }

    },

    computed: {

    }

})

app.mount('#app')


