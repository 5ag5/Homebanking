const {createApp } = Vue 

const app = createApp({
    data(){
        return {
            clientData:undefined,
            loansInfo: [],
            amountRequested: undefined,
            accounts: [],
            interestRateNewLoan: undefined,
            numberOfPayments: [],
            numberPaymentChosen: undefined,
            monthlyPayment: 0,
            maxAmountLoan: undefined,
            mapInterestPayments: [],
            typeLoan: [],
            typeLoanChosen: undefined,
            accountDestination:undefined
        }
    },

    created(){
        this.getData()
        this.getInfoLoans()
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/clients/current/').then(element =>{
                this.clientData = element;
                this.monthlyPayment =0;
                console.log(this.clientData)
                this.getAvailableAccounts()
            })
        },

        async getInfoLoans(){
            axios.get('/api/loans').then(element =>{
                this.loansInfo = element.data
                console.log(this.loansInfo)

            this.getTypeLoans()
            this.constructMap()
            })
        },

        constructMap(){
            let arrayTest = []
            const mapa = new Map();
            arrayTest = this.loansInfo[0].listInst

                for(let i=0; i <arrayTest.length;i++){
                        mapa.set(arrayTest[i][0],arrayTest[i][1])
                }
            
            this.mapInterestPayments = mapa;
            // console.log(this.mapInterestPayments)
            // console.log(this.mapInterestPayments.get(6))
            // console.log(this.mapInterestPayments.size)
        },

        getAvailableAccounts(){
            this.accounts = this.clientData.data.accounts
            console.log(this.accounts)
        },

        getTypeLoans(){
        this.loansInfo.forEach(element =>{
                this.typeLoan.push(element.name)
            })
        },

        getNumberOfPayments(){
            for(let i =0; i < this.loansInfo.length;i++){
                if(this.loansInfo[i].name === this.typeLoanChosen){
                    this.numberOfPayments = this.loansInfo[i].payments
                    this.maxAmountLoan = this.loansInfo[i].maxAmount
                } 
            }
        },
        
            submitLoanRequest(){
                if(this.amountRequested <= 0){
                    Swal.fire({
                        icon: 'error',
                        title: 'Something went wrong',
                        text: 'Negative numbers or 0 value are not allowed',
                    })
                }else{
                    console.log(this.amountRequested)
                    console.log(this.numberPaymentChosen)
                    console.log(this.typeLoanChosen)
                    console.log(this.interestRateNewLoan)
                    console.log(this.accountDestination)
                    
                    let amountToTransfer = this.amountRequested*(1 +(this.interestRateNewLoan/100))

                    axios.post('/api/loans',
                    {amount: amountToTransfer, 
                    payments: this.numberPaymentChosen,
                    tipoLoan: this.typeLoanChosen, 
                    interest: this.interestRateNewLoan, 
                    accountDestination: this.accountDestination
                    }).then( element =>{
                        Swal.fire(
                            'Transaction Succesful!',
                            'Loan Request made succesfully',
                            'success'
                        )
                    }).catch( err => {  
                        console.log(err)
                        Swal.fire({
                            icon: "error",
                            title: "Submit Error",
                            text: err.response.data,
                        })
                    })
                }
            },
    
            logOut(){
                // console.log("funciona")
                axios.post('/api/logout').then(response => {
                    // console.log('signed out!!!')
                    window.location.href='/web/index.html'   
                })
                .catch(err => console.log(err))
            },

            loanSimulation(){
                // console.log(this.typeLoanChosen)
                // console.log(this.interestRateNewLoan)
                // console.log(this.numberPaymentChosen)
                const interestPaymentChosen = this.mapInterestPayments.get(this.numberPaymentChosen)

                this.loansInfo.forEach(element => {
                    if(element.name === this.typeLoanChosen){
                        this.interestRateNewLoan = element.interest
                    }
                });
                
                if(this.interestRateNewLoan === undefined){
                    this.monthlyPayment = 0
                }else{
                    this.monthlyPayment = (this.amountRequested + (((this.interestRateNewLoan/100)*this.amountRequested)*(1+interestPaymentChosen)))/this.numberPaymentChosen 
                }
    
                this.monthlyPayment = Math.round(this.monthlyPayment*100/100)
    
                // console.log(this.interestRateNewLoan)
            },

    },


    computed:{

    },
})

app.mount("#app")