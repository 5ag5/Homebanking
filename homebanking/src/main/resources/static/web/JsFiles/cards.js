const {createApp} = Vue 

const app = createApp({
    data(){
        return {
            datos:undefined,
            params: undefined,
            id: undefined,
            nombre: undefined,
            tarjetas: [],
            tarjetaDebito: [], 
            tarjetaCredito: [],
            numeroTarjetaDebito: [],
            numeroTarjetaCredito: []

        }
    },

    created(){
        this.params = new URLSearchParams(location.search);
        this.id = this.params.get("id");
        this.getData()
    },

    methods:{
        async getData(){
            axios.get('http://localhost:8080/api/clients/current')
            .then(elemento => {
                this.datos = elemento
                this.nombre = elemento.data.firstName + " " + elemento.data.lastName
                this.tarjetas = elemento.data.cards
                
                console.log(this.tarjetas)
                // console.log(this.numeroTarjetaCredito)
                // console.log(this.tarjetas)

                this.changeDatoFormat()
                this.datosTarjeta()
                this.splitCardNumber()
                // console.log(this.tarjetaDebito)
                // console.log(this.tarjetaCredito)

                // console.log(this.tarjetas)
            })
        },

        datosTarjeta(){
            for(elemento of this.tarjetas){
                if(elemento.type === "DEBIT"){
                    this.tarjetaDebito.push(elemento)
                }else{
                    this.tarjetaCredito.push(elemento)
                }
            }
        },

        splitCardNumber(){
            this.tarjetaDebito.forEach(element => {
                element.numero1 = element.number.substring(0,4);
                element.numero2 = element.number.substring(4,8);
                element.numero3 = element.number.substring(8,12);
                element.numero4 = element.number.substring(12,16);
            })

            this.tarjetaCredito.forEach(element => {
                element.numero1 = element.number.substring(0,4);
                element.numero2 = element.number.substring(4,8);
                element.numero3 = element.number.substring(8,12);
                element.numero4 = element.number.substring(12,16);
            })
        },

        changeDatoFormat(){
            this.tarjetas.forEach(element => {
                element.number = element.number.replaceAll("-","")
                element.thruDate = element.thruDate.substring(0,7)
                console.log(element.thruDate.substring(0,7))
            });
        },

        logOut(){
            console.log("funciona")
            axios.post('/api/logout').then(response => {
                console.log('signed out!!!')
                window.location.href='/web/index.html'   
            })
            .catch(err => console.log(err))
        },

    },
})

app.mount("#app")