import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import PagoComponent from './PagoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import Auth from './Auth';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import { ListBox } from 'primereact/listbox';
import GrupoComponent from './GrupoComponent';
import {selectStudent} from '../actions/index';
import {selectAssignedStudent}  from '../actions/index';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import "../css/payment.css"

class Pagos extends Component {

    constructor(props) {
        super(props);
        this.state = {
            
            pagoS:"",
            redirect: false,
            nickUsuario: "",
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
                nombreGrupo: ""
            },
            listaTemporal:{

            },
            lista:{

            },
            textBuscar:"",
            textBuscar2:"",
            comprobation: false,
            redirect:false


            //nodes: null,
            //selectedKey: null,
        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
        this.filter = this.filter.bind(this);
        this.filterDNI = this.filterDNI.bind(this);
        this.notificar = this.notificar.bind(this);
        this.botonPagos = this.botonPagos.bind(this);

        //this.edicion = this.edicion.bind(this);
        //this.assignGroup = this.assignGroup.bind(this)      
        //this.botonAssign = this.botonAssign.bind(this);
        //this.cursos = new CursoComponent();
        //this.onNodeSelect = this.onNodeSelect.bind(this);
    }

    componentDidMount() {
        axios.get("http://localhost:8081/auth", {withCredentials: true}).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
                }
            })
        
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ lista: data }));
    }

   
    
    showSelectGroup(pago) {
        console.log(pago);
        if (pago !== null) {
            this.setState({ pagoS: pago });
            if (pago === "") {
                this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
            } else {
                this.pagos.getAllStudentsPaid(pago).then(data => this.setState({ alumnos: data }));
                this.pagos.getAllStudentsNotPaid(pago).then(data => this.setState({ alumnosNP: data }));
                this.pagos.getAllStudentsPaid(pago).then(data => this.setState({ listaTemporal: data }));
                this.pagos.getAllStudentsNotPaid(pago).then(data => this.setState({ listaTemporal2: data }));
               // this.pagos.getAllStudentsPaid(pago).then(data => console.log(data));
            }
        }
    }
    notificar(rowData) {
        return (    
            <React.Fragment>
                <Button icon="pi pi-bell" className="p-button-rounded p-button-success p-mr-2"/>
            </React.Fragment>
        );
    }

    filter(event){
        var text = event.target.value
        const data = this.state.listaTemporal2
        const newData = data.filter(function(item){
            console.log(item);
            const itemData = item.nombreCompletoUsuario.toUpperCase()
            console.log(itemData);
            const textData = text.toUpperCase()
            console.log(textData);
            var r= itemData.indexOf(textData) > -1
            console.log(r)
            return  r
        })
        this.setState({
            alumnosNP: newData,
            text: text
        })
        const data2 = this.state.listaTemporal
        const newData2 = data2.filter(function(item){
            console.log(item);
            const itemData = item.nombreCompletoUsuario.toUpperCase()
            console.log(itemData);
            const textData = text.toUpperCase()
            console.log(textData);
            var r= itemData.indexOf(textData) > -1
            console.log(r)
            return  r
        })
        this.setState({
            alumnos: newData2,
            text: text
        })
    }

     filterDNI(event){
        var text = event.target.value
        const data = this.state.alumnosNP
        const newData = data.filter(function(item){
            console.log(item);
            const itemData = item.dniUsuario.toUpperCase()
            console.log(itemData);
            const textData = text.toUpperCase()
            console.log(textData);
            var r= itemData.indexOf(textData) > -1
            console.log(r)
            return  r
        })
        this.setState({
            alumnosNP: newData,
            text2: text
        })
        const data2 = this.state.alumnos
        const newData2 = data2.filter(function(item){
            console.log(item);
            const itemData = item.dniUsuario.toUpperCase()
            console.log(itemData);
            const textData = text.toUpperCase()
            console.log(textData);
            var r= itemData.indexOf(textData) > -1
            console.log(r)
            return  r
        })
        this.setState({
            alumnos: newData2,
            text2: text
        })
    }

    botonPagos() {
        this.setState({ 
            redirect: "/createPayment",
        
    });
    }


    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        }else{
                if (this.state.redirect) {
                    if(this.state.redirect==="/createPayment"){
                        return <Redirect
                        to={{
                        pathname: "/createPayment"
                        }}
                    />}}
                
        
        const pagoSelectItems = [
            { label: 'CONCEPTS:', value: '' },
            { label: 'First Mat', value: 'Pago matricula' },
            { label: 'First Pay', value: 'Primer plazo' },
            { label: 'Second Pay', value: 'Segundo plazo' }
           
        ];
        console.log(this.state)
        

        return (
            <React.Fragment>
                <div className="datatable-templating-demo">
                    <div>
                    <ListBox options={pagoSelectItems} onChange={(e) => this.showSelectGroup(e.value)} />
                    </div>

                    <div>&nbsp;</div>
                    <InputText class="form-control" placeholder="Search by name" value={this.state.text} onChange={this.filter} />
                    {` `}
                    <InputText class="form-control" placeholder="Search by DNI/NIF" value={this.state.text2} onChange={this.filterDNI} />
                    {` `}
                    <Button icon="pi pi-fw pi-users" label="Create payment" className="p-button-secondary" onClick={this.botonPagos} />


                    <div>&nbsp;</div>
                    
                    <DataTable header="Students who have paid:" value={this.state.alumnos}>
                        <Column field="nombreCompletoUsuario" header="Full name"></Column>
                        <Column field="dniUsuario" header="DNI"></Column>
                        <Column field="correoElectronicoUsuario" header="Email"></Column>
                    </DataTable>
                    <div>&nbsp;</div>
                    <DataTable header="Students who have not paid:" value={this.state.alumnosNP}>
                        <Column field="nombreCompletoUsuario" header="Full name"></Column>
                        <Column field="dniUsuario" header="DNI"></Column>
                        <Column field="correoElectronicoUsuario" header="Email"></Column>
                        <Column header="Notify payment's lack" body={this.notificar}></Column>
                    </DataTable>
                </div>
            </React.Fragment>
        
        )
    }
}
}

function  matchDispatchToProps(dispatch) {
    return bindActionCreators({selectStudent : selectStudent,
        selectAssignedStudent: selectAssignedStudent}, dispatch) //se mapea el action llamado selectStudent y se transforma en funcion con este metodo, sirve para pasarle la info que queramos al action, este se la pasa al reducer y de alli al store 
}
export default connect(null , matchDispatchToProps)(Pagos) //importante poner primero el null si no hay mapStateToProps en el componente chicxs