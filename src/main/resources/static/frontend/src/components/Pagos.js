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
           
            lista:{

            },
            listaConcepto:{
                concepto:""

            },
            opcion:"",
            textBuscar:"",
            textBuscar2:"",
            comprobation: false,

        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
        this.filter = this.filter.bind(this);
        this.filterDNI = this.filterDNI.bind(this);
        this.notificar = this.notificar.bind(this);
        this.botonPagos = this.botonPagos.bind(this);

    }

    componentDidMount(){

        axios.get("http://localhost:8081/auth", {withCredentials: true}).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
                }
            })
        
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ lista: data }));
        this.pagos.getAllPayments().then(data => this.setState({listaConcepto:data}))
    }

    // fix Warning: Can't perform a React state update on an unmounted component
   componentWillUnmount() {
    this.setState = (state,callback)=>{
        return;
        };
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
        console.log("f"+this.state.alumnosNP)
    }

     filterDNI(event){
        var text = event.target.value

        const data = this.state.listaTemporal2
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
        const data2 = this.state.listaTemporal
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

    allConceptsNames(){

        var t=this.state.listaConcepto
        var i=0
        var conceptSelectItems = [
        ];
        while(i<t.length){        
            conceptSelectItems.push(         
                { label: String(t[i]) , value: String(t[i]) })        
            i+=1
            }
        return conceptSelectItems
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
                />}
            }

            if(this.state.pagoS===""){

                return (

                    <React.Fragment>
                    
                        <div className="datatable-templating-demo">
                        <div className="t2"><div className="t3"><h5>{` `}Concepts</h5></div></div>

                            <div>
                            <ListBox options={this.allConceptsNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                            </div>

                            {` `}
                            <div>&nbsp;</div>
        
                        
                        </div>
                        <Button icon="pi pi-fw pi-users" label="Create payment" className="p-button-secondary" onClick={this.botonPagos} />

                    </React.Fragment>
                
                )

            }else{

                return (
                
                    <React.Fragment>
                        
                        <div className="datatable-templating-demo">
                            <div className="t2"><div className="t3"><h5>{` `}Concepts</h5></div></div>
                            
                                <div>
                                <ListBox options={this.allConceptsNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                                </div>
                                <Button icon="pi pi-fw pi-users" label="Create payment" className="p-button-secondary" onClick={this.botonPagos} />
    
                                <div>&nbsp;</div>
                                <div className="t6">
                                <InputText className="form-control" placeholder="Search by name" value={this.state.text} onChange={this.filter} />
                                {` `}

                                <InputText className="form-control" placeholder="Search by DNI/NIF" value={this.state.text2} onChange={this.filterDNI} />
                                </div>
                                {` `}
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
}

function  matchDispatchToProps(dispatch) {
    return bindActionCreators({selectStudent : selectStudent,
        selectAssignedStudent: selectAssignedStudent}, dispatch) //se mapea el action llamado selectStudent y se transforma en funcion con este metodo, sirve para pasarle la info que queramos al action, este se la pasa al reducer y de alli al store 
}
export default connect(null , matchDispatchToProps)(Pagos) //importante poner primero el null si no hay mapStateToProps en el componente chicxs