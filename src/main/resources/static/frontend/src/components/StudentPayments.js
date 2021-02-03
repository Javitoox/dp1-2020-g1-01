import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import PagoComponent from './PagoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';


import { ListBox } from 'primereact/listbox';
import "../css/payment.css"

export class StudentPayments extends Component {

    constructor(props) {
        super(props);
        this.state = {
            
            pagoS:"",
            redirect: false,
            nickUsuario: this.props.nickUser,
            contraseya: "",
            dniUsuario: "",
            nombreCompletoUsuario: "",
            correoElectronicoUsuario: "",
            numTelefonoUsuario: "",
            direccionUsuario: "",
            fechaNacimiento:"",
            numTareasEntregadas :"",
            fechaMatriculacion: "",
            groupSelectItems: "",
            listaGrupos:{
                concepto: ""
            }

        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
    }

    allGroupNames(){

        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [
            {label:'PAYMENTS NOT MADE', value:'PAYMENT NOT MADE'}
        ];
        while(i<t.length){        
        groupSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return groupSelectItems
    }
    form(){
        if(this.allGroupNames().length>1){
           return <ListBox options={this.allGroupNames()} />


        }else{
            return <div className="tt"><div><h5>There are no payments to make</h5></div></div>
        }

    }

    componentDidMount() {
        this.pagos.getNotPaidByStudent(this.props.nickUser).then(data => this.setState({ listaGrupos: data }));
        this.pagos.getPaymentsByStudent(this.props.nickUser).then(data => this.setState({ paid: data }));
     }


    render() {        
        console.log(this.state);
        console.log(this.props.nickUser);
        return (
            <React.Fragment>
                <div className="datatable-templating-demo">                 
                    <DataTable header="Payments made:" value={this.state.paid}>
                        <Column field="concepto" header="Concept"></Column>
                        <Column field="tipo.tipo" header="Type"></Column>
                        <Column field="fecha" header="Date of payment"></Column>
                    </DataTable>
                    <div>&nbsp;</div>
                    {this.form()}

                </div>
            </React.Fragment>
        )
    }
}
