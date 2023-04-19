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
            axios.get('http://localhost:8080/api/clients/' + this.id)
            .then(elemento => {
                this.datos = elemento
                this.nombre = elemento.data.firstName + " " + elemento.data.lastName
                this.tarjetas = elemento.data.cards
                
                console.log(this.datos)
                console.log(this.nombre)
                console.log(this.tarjetas)

                this.datosTarjeta()
                console.log(this.tarjetaDebito)
                console.log(this.tarjetaCredito)

                if(this.tarjetaCredito === undefined){
                    console.log(true)
                }
                this.splitDebitCardNumber()
                console.log(this.numeroTarjetaDebito)

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

        splitDebitCardNumber(){
            this.numeroTarjetaDebito = this.tarjetaDebito.number.split("-")
            this.numeroTarjetaCredito = this.tarjetaCredito.number.split("-")
        },
    },
})

app.mount("#app")