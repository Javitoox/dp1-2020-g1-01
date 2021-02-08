import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import PagoComponent from './PagoComponent';
import Auth from './Auth';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import NotificacionesStudents from './NotificacionesStudents';
import { ListBox } from 'primereact/listbox';
import "../css/payment.css"

export class StudentPayments extends Component {

    constructor(props) {
        super(props);
        this.state = {

            pagoS: "",
            redirect: false,
            nickUsuario: this.props.nickUser,
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
                concepto: ""
            },
            impagos: 0,
            comprobation: true
        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
    }

    allPendingPayments() {
        var t = this.state.listaGrupos
        var i = 0
        var pagosPendientes = [];
        while (i < t.length) {
            pagosPendientes.push(
                { label: String(t[i]), value: String(t[i]) })
            i += 1
        }
        return pagosPendientes
    }
    form() {
        if (this.allPendingPayments().length > 0) {
            return <ListBox options={this.allPendingPayments()} />
        } else {
            return <div className="tt"><div><h5>There are no payments to make</h5></div></div>
        }
    }
    componentDidMount() {
        this.pagos.getNotPaidByStudent(this.props.nickUser).then(data => {
            this.setState({ listaGrupos: data, impagos: data.length })
        }).catch(error => this.setState({ comprobation: false }));
        this.pagos.getPaymentsByStudent(this.props.nickUser).then(data => this.setState({ paid: data }));
    }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="student"></Auth>
        } else {
            return (
                <React.Fragment>
                    <div>
                        <NotificacionesStudents payment={this.state.impagos}></NotificacionesStudents>
                    </div>
                    <div className="datatable-templating-demo">

                        <DataTable header="Payments made:" value={this.state.paid}>
                            <Column field="concepto" header="Concept"></Column>
                            <Column field="tipo.tipo" header="Type"></Column>
                            <Column field="fecha" header="Date of payment"></Column>
                        </DataTable>
                        <div>&nbsp;</div>
                        <DataTable header="Pending payments:"></DataTable>
                        {this.form()}
                    </div>
                </React.Fragment>
            )
        }
    }
}
