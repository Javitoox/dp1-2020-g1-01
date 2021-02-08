import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import PagoComponent from './PagoComponent';
import Auth from './Auth';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Redirect } from 'react-router-dom';
import { ListBox } from 'primereact/listbox';
import { selectStudent } from '../actions/index';
import {selectAssignedStudent}  from '../actions/index';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import "../css/payment.css"

class Pagos extends Component {

    constructor(props) {
        super(props);
        this.state = {

            pagoS: "",
            redirect: false,
            nickUsuario: "",
            contraseya: "",
            dniUsuario: "",
            nombreCompletoUsuario: "",
            correoElectronicoUsuario: "",
            numTelefonoUsuario: "",
            direccionUsuario: "",
            fechaNacimiento: "",
            numTareasEntregadas: "",
            fechaMatriculacion: "",
            groupSelectItems: "",
            listaGrupos: {
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
            comprobation: true,

        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
        this.filter = this.filter.bind(this);
        this.filterDNI = this.filterDNI.bind(this);
        this.botonPagos = this.botonPagos.bind(this);
    }

    componentDidMount() {
        this.alumnos.getAllStudents(this.props.urlBase).then(data => {
            this.setState({ alumnos: data, lista: data})
        }).catch(error => this.setState({comprobation: false}));
        this.pagos.getAllPayments().then(data => this.setState({listaConcepto:data}))
    }
    
    
    showSelectGroup(pago) {
        if (pago !== null) {
            this.setState({ pagoS: pago });
            if (pago === "") {
                this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
            } else {                
                this.pagos.getAllStudentsPaid(pago).then(data => {
                    this.setState({ alumnos: data, listaTemporal: data });
                })
                this.pagos.getAllStudentsNotPaid(pago).then(data => {
                    this.setState({ alumnosNP: data, listaTemporal2: data})})
            }
        }
    }    
 
    filter(event) {
        var text = event.target.value
        const data = this.state.listaTemporal2
        const newData = data.filter(function (item) {
            const itemData = item.nombreCompletoUsuario.toUpperCase()
            const textData = text.toUpperCase()
            var r = itemData.indexOf(textData) > -1
            return r
        })
        this.setState({
            alumnosNP: newData,
            text: text
        })
        const data2 = this.state.listaTemporal
        const newData2 = data2.filter(function (item) {
            const itemData = item.nombreCompletoUsuario.toUpperCase()
            const textData = text.toUpperCase()
            var r = itemData.indexOf(textData) > -1
            return r
        })
        this.setState({
            alumnos: newData2,
            text: text
        })
    }

    filterDNI(event){
        var text = event.target.value
        const data = this.state.listaTemporal2
        const newData = data.filter(function(item){
            const itemData = item.dniUsuario.toUpperCase()
            const textData = text.toUpperCase()
            var r = itemData.indexOf(textData) > -1
            return r
        })
        this.setState({
            alumnosNP: newData,
            text2: text
        })
        const data2 = this.state.listaTemporal
        const newData2 = data2.filter(function(item){
            const itemData = item.dniUsuario.toUpperCase()
            const textData = text.toUpperCase()
            var r = itemData.indexOf(textData) > -1
            return r
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
                const paginatorLeft = <Button type="button" icon="pi pi-refresh" className="p-button-text" />;
                const paginatorRight = <Button type="button" icon="pi pi-cloud" className="p-button-text" />;
                return (
                    <React.Fragment>
                        <div className="datatable-templating-demo">
                            <div className="t2"><div className="t3"><h5>{` `}Concepts</h5></div></div>
                                <div>
                                <ListBox options={this.allConceptsNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                                </div>
                                <div>&nbsp;</div>
                                <Button icon="pi pi-fw pi-users" label="Create payment" className="p-button-secondary" onClick={this.botonPagos} />
                                <div>&nbsp;</div>
                                <InputText className="form-input" placeholder="Search by name" value={this.state.text} onChange={this.filter} />
                                {` `}
                                <InputText className="form-input" placeholder="Search by DNI/NIF" value={this.state.text2} onChange={this.filterDNI} />
                            <div>&nbsp;</div>
                            <DataTable header="Students who have paid:" value={this.state.alumnos} paginator
                                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                                currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                                paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>
                                    
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="dniUsuario" header="DNI"></Column>
                                <Column field="correoElectronicoUsuario" header="Email"></Column>
                            </DataTable>
                            <div>&nbsp;</div>
                            <DataTable header="Students who have not paid:" value={this.state.alumnosNP} paginator
                                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                                currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                                paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>

                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="dniUsuario" header="DNI"></Column>
                                <Column field="correoElectronicoUsuario" header="Email"></Column>
                            </DataTable>
                        </div>                        
                    </React.Fragment>
                    )
                }
        
            }
        }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        selectStudent: selectStudent,
        selectAssignedStudent: selectAssignedStudent
    }, dispatch) 
}
export default connect(null, matchDispatchToProps)(Pagos)