const {createApp} = Vue;

const app = createApp({
    data(){
        return {
            idAccount: undefined,
            transactions: undefined,
            sumTransactions: 0,
            cuentas: undefined,
            tempAccounts: [],
            numeroCuenta: undefined,
            params: undefined,
            id: undefined,
            pdfYearFrom: [],
            pdfYearTo: [],
            pdfMonth: [],
            pdfDay: [],
            pdfYearFromVal: undefined,
            pdfMonthFromVal: undefined,
            pdfDayFromVal: undefined,
            pdfYearToVal: undefined,
            pdfMontToVal: undefined,
            pdfDayToVal: undefined,
        }
    },

    created(){
        this.params = new URLSearchParams(location.search)
        this.id = this.params.get("id")
        this.getData();
    },

    methods:{
        async getData(){
            //axios.get('http://localhost:8080/api/accounts/current/'+this.id)
            //axios.get('http://localhost:8080/api/accounts/current')
            axios.get('http://localhost:8080/api/clients/current/')
            .then(elemento =>{
                    this.idAccount = JSON.parse(localStorage.getItem('accountID') || 0)
                    console.log(this.idAccount)  
                    this.tempAccounts = elemento.data.accounts

                    this.findAccount()

                    this.transactions = this.cuentas.transactions
                    this.transactions = this.transactions.sort((x,y) => y.id - x.id)

                    console.log(this.cuentas)  
                    console.log(this.transactions)

                    let date1 = new Date()

                    console.log(date1)

                    this.filteAvailableDates()
                    this.callSumTransactions()
                    this.getNumeroCuenta()
                    // this.updateAmountType()
                    this.convertirADolares()

                    console.log(this.transactions)
                } 
            )
        },

        findAccount(){
            for(let i =0; i < this.tempAccounts.length; i++){
                if(this.tempAccounts[i].id === this.idAccount){
                    this.cuentas = this.tempAccounts[i]
                }
            }
            this.tempAccounts = null
            localStorage.clear()
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

                element.currentBalance = element.currentBalance.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
            })

            this.sumTransactions = this.sumTransactions.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
        },

        PDFTransactions(){
            console.log(this.pdfYearFromVal)
            console.log(this.pdfMonthFromVal)
            console.log(this.pdfDayFromVal)
            console.log(this.pdfYearToVal)
            console.log(this.pdfMontToVal)
            console.log(this.pdfDayToVal)

            let dateFrom = this.pdfYearFromVal + "-" + this.pdfMonthFromVal + "-" + this.pdfDayFromVal
            let dateTo = this.pdfYearToVal + "-" + this.pdfMontToVal + "-" + this.pdfDayToVal
            console.log(dateFrom)
            console.log(dateTo)

            if(this.pdfYearFromVal > this.pdfYearToVal){
                Swal.fire({
                    icon: 'error',
                    title: 'Error creating PDF',
                    text: 'Year From cannot be higher than Year To',
                })
            }else if(this.pdfYearFromVal === undefined || this.pdfMonthFromVal === undefined || this.pdfDayFromVal === undefined
                || this.pdfYearToVal === undefined || this.pdfMontToVal === undefined || this.pdfDayToVal === undefined){
                    Swal.fire({
                        icon: 'error',
                        title: 'Error creating PDF',
                        text: 'All values need to be filled in',
                    })
            }else{
             axios.post('/api/transactions/transactionslist',`accountNumber=${this.numeroCuenta}&startDate=${dateFrom}&endDate=${dateTo}`,{responseType:'blob'}).then((response) =>{
                console.log(response.data)
                //`AccountNumber=VIN001`
                const url = URL.createObjectURL(response.data)
                const a = document.createElement('a')
                a.href = url
                a.download = 'transactions_.pdf'
                a.style.display = 'none'
                document.body.appendChild(a)
                a.click()
                a.remove()
                URL.revokeObjectURL(url)
            }).catch(err =>{
                console.log(err)
            })
            }
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

        },

        // updateAmountType(){
        //     this.transactions.forEach( element =>{
        //     //element.amount = parseFloat(element.amount.substring(1,element.amount.length))
        //     console.log(element.amount)
        //     })
        // },

        filteAvailableDates(){
            let tempYear = []
            let tempMonth = []
            let tempDay = []

            this.transactions.forEach(element =>{
                tempYear.push(element.date.substring(0,4))
                tempMonth.push(element.date.substring(5,7))
                tempDay.push(element.date.substring(8,10))
            })

            this.pdfYearFrom = tempYear.filter((item, index) =>
                tempYear.indexOf(item) === index
            )
            this.pdfMonth = tempMonth.filter((item, index) =>
                tempMonth.indexOf(item) === index
            ) 
            this.pdfDay = tempDay.filter((item, index) =>
                tempDay.indexOf(item) === index
            ) 

            console.log(this.pdfYearFrom)
            console.log(this.pdfMonth)
            console.log(this.pdfDay)

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

    computed:{

    }

})

app.mount('#app')