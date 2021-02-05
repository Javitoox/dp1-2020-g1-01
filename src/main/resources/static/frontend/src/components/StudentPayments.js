import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import PagoComponent from './PagoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';


import { ListBox } from 'primereact/listbox';
import {selectStudent} from '../actions/index';
import {selectAssignedStudent}  from '../actions/index';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
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


            //nodes: null,
            //selectedKey: null,
        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
        //this.edicion = this.edicion.bind(this);
        //this.assignGroup = this.assignGroup.bind(this)      
        //this.botonAssign = this.botonAssign.bind(this);
        //this.cursos = new CursoComponent();
        //this.onNodeSelect = this.onNodeSelect.bind(this);
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
            return <div className="t"><div><h5>There are no payments to make</h5></div></div>
        }

    }

    componentDidMount() {
        //this.pagos.getAllStudentsPaid().then(data => this.setState({ alumnos: data }));
        this.pagos.getNotPaidByStudent(this.props.nickUser).then(data => this.setState({ listaGrupos: data }));
        this.pagos.getPaymentsByStudent(this.props.nickUser).then(data => this.setState({ paid: data }));
        //this.grupos.getAllGroups().then(data => this.setState({ groupSelectItems: data }));
        //this.cursos.getCourses().then(data => this.setState({ nodes: data }));
    }


    render() {        
        console.log(this.state);
        console.log(this.props.nickUser);
        return (
            <React.Fragment>
                <div className="datatable-templating-demo">                 
                    <DataTable header="Payments made:" value={this.state.paid}>
                        <Column field="concepto" header="Concept"></Column>
                        <Column field="tipo" header="Type"></Column>
                        <Column field="fecha" header="Date of payment"></Column>
                    </DataTable>
                    <div>&nbsp;</div>
                    {this.form()}

                </div>
            </React.Fragment>
        )
    }
}

function  matchDispatchToProps(dispatch) {
    return bindActionCreators({selectStudent : selectStudent,
        selectAssignedStudent: selectAssignedStudent}, dispatch) //se mapea el action llamado selectStudent y se transforma en funcion con este metodo, sirve para pasarle la info que queramos al action, este se la pasa al reducer y de alli al store 
}