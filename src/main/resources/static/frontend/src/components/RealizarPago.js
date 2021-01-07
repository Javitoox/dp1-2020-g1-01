import React, { Component } from 'react'

import GrupoComponent from './GrupoComponent';
import PagoComponent from './PagoComponent';

import axios from 'axios';
import { connect } from 'react-redux';
import moment from 'moment';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';





export class RealizarPago extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            nickUsuario:this.props.nickUser,
            fecha:moment().format('YYYY-MM-DD'),
            concepto:"",
            type:"",
            listaConcepto:{
                concepto:""
            },
            succes:null,
            exist:null,

        }
        this.grupos = new GrupoComponent();
        this.pagos = new PagoComponent();
        this.save = this.save.bind(this);
        this.handleP = this.handleP.bind(this);
        this.handlePI = this.handlePI.bind(this);
        this.handleNU = this.handleNU.bind(this);
        this.handleF = this.handleF.bind(this);
        this.allConceptNames= this.allConceptNames.bind(this);
        this.form=this.form.bind(this);
    }
    save = event => {
        event.preventDefault();

        const grupo = {
            alumnos:{
                nickUsuario:this.props.nickUser
            },            
            fecha:moment().format('YYYY-MM-DD'),
            concepto:this.state.concepto,
            tipo:this.state.type,
           
        }

        axios.post("http://localhost:8081/pagos/new", grupo, {withCredentials: true}).then(res => {
            this.respuesta(res.status, res.data);
        })
        
    }
    handleP(event) {
        this.setState({
           
            concepto:event.target.value
            
            
        });
    }
    handlePI(event) {
        this.setState({
            type:event.target.value
        });
    }
    
    form(){
        var l = this.state.listaConcepto
        if(l===""){

            return <div className="t"><div><h5>There is no payment to make</h5></div></div>


        }else{
            const tipoPagoSelectItems = [
                { label: 'BIZUM', value: 'BIZUM' },
                { label: 'Tranferencia bancaria', value: 'TRANSFERENCIA BANCARIA' },
                { label: 'Efectivo', value: 'EFECTIVO' }
            ];

            return <div>
                                <div className="t"><div><h5>Register Payment</h5></div></div>


                                <div className="i">
                                <div className="p-inputgroup">
                                <Dropdown name="concepto" value={this.state.concepto} placeholder="Select a payment" options={this.allConceptNames()} onChange={this.handleP} />

                                </div>
                                </div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <Dropdown name="tipo" value={this.state.type} placeholder="Select a payment type" options={tipoPagoSelectItems} onChange={this.handlePI} />

                                </div>
                                </div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <InputText value={this.props.nickUser} hidden={true}  name="pago.nickUsuario" />

                                </div>
                                </div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <InputText value={this.state.fecha}  name="fecha"  hidden={true}/>

                                </div>
                                </div>


                                <div className="b">
                                <div className="i">
                                <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check"/>

                                </div>
                            </div>
                            </div>


        }
    }
    
    
    handleF(event) {
        this.setState({

            fecha:event.target.value

        });
    }
    handleNU(event) {

        this.setState({

           nickUsuario:event.target.value

           
        });
    }

    respuesta(status, data){
        console.log(status);
        if(status===203){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status===201){
            this.setState({               

                grupoS:{
                    nombreGrupo:"",
                    cursos:{
                        cursoDeIngles:""
                    }
                },
                succes: <div className="alert alert-success" role="alert">Successful payment</div>
            })
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }
    componentDidMount() {
       this.pagos.getNotPaidByStudent(this.props.nickUser).then(data => this.setState({ listaConcepto: data }));
    }
    allConceptNames(){

        var t=this.state.listaConcepto
        var i=0
        var conceptoSelectItems = [];
        while(i<t.length){        
            conceptoSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return conceptoSelectItems
    }

    render() {
        console.log(this.state.listaConcepto);
        console.log(this.props.nickUser)
        
        

        

        return (

            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.save}>

                        {this.state.succes}
                        {this.state.exist}
                        {this.form()}

                            
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}
function mapStateToProps(state) { //metodo para poder pillar datos del store
    return {
        uselected: state.uselected //le pasamos a nuestra variable student la informacion del estudiante almacenada en el store
    }
}

export default connect(mapStateToProps)(RealizarPago)