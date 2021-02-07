import React, { Component } from 'react'
import PagoComponent from './PagoComponent';
import AlumnoComponent from './AlumnoComponent';
import Auth from './Auth';
import { InputText } from 'primereact/inputtext';
import axios from 'axios';
import moment from 'moment';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';


export class RealizarPago extends Component {
    constructor(props) {
        super(props);
        this.state = {
            nickUsuario: "",
            fecha: moment().format('YYYY-MM-DD'),
            concepto: "",
            type: "",
            listaConcepto: {
                concepto: ""
            },
            listaNombres: {
                nickUsuario: ""
            },
            listaNombres2: {
                nickUsuario: ""
            },
            comprobation: true,
            opcion: "",
            usernameError: null,
            tipoError: null,
            conceptoError: null,
            succes: null,
            exist: null

        }
        this.pagos = new PagoComponent();
        this.alumnos = new AlumnoComponent();
        this.save = this.save.bind(this);
        this.handleP = this.handleP.bind(this);
        this.handlePI = this.handlePI.bind(this);
        this.handlePU = this.handlePU.bind(this);
        this.handleNU = this.handleNU.bind(this);
        this.handleF = this.handleF.bind(this);
        this.allConceptNames = this.allConceptNames.bind(this);
        this.form = this.form.bind(this);
    }
    save = event => {
        event.preventDefault();
        this.setState({
            usernameError: null,
            tipoError: null,
            conceptoError: null,
        });

        const pago = {
            alumnos: {
                nickUsuario: this.state.nickUsuario
            },
            fecha: moment().format('YYYY-MM-DD'),
            concepto: this.state.concepto,
            tipo: {
                tipo: this.state.type
            }
        }

        axios.post("http://localhost:8081/pagos/new/", pago).then(res => {
            this.respuesta(res.status, res.data);
        })

    }
    handleP(event) {
        this.setState({
            concepto: event.target.value
        });
    }

    handlePU(event) {
        this.setState({
            nickUsuario: event.target.value
        });
        this.pagos.getNotPaidByStudent(event.target.value).then(data => this.setState({ listaConcepto: data }));

    }
    handlePI(event) {
        this.setState({
            type: event.target.value
        });
    }
    assignOption(data) {
        this.setState({
            opcion: data

        });
    }

    form(option) {
        const tipoPagoSelectItems = [
            { label: 'Bizum', value: 'Bizum' },
            { label: 'Transfer', value: 'Transfer' },
            { label: 'Cash', value: 'Cash' }
        ];
        if (option === "") {
            return <div>
                <div className="t"><div><h5>Register Payment</h5></div></div>
                <div className="t6">
                    <div className="t8">
                        <Button className="p-button-secondary" label="Create Payment" onClick={() => this.assignOption("op1")} />
                    </div>
                    <div className="t7"><Button className="p-button-secondary" label="Create new concept" onClick={() => this.assignOption("op2")} />
                    </div>
                </div>
            </div>

        } else if (option === "op1") {
            if (this.state.nickUsuario === "") {
                return <div>
                    <div className="t"><div><h5>Register Payment</h5></div></div>

                    <div className="i">
                        <div className="p-inputgroup">
                            <Dropdown name="pago.nickUsuario" value={this.state.nickUsuario} placeholder="Select a student" options={this.allStudentsNames()} onChange={this.handlePU} />

                        </div>
                    </div>
                </div>

            } else {


                return <div>
                    <div className="t"><div><h5>Register Payment</h5></div></div>

                    <div className="i">
                        {this.state.usernameError}
                        <div className="p-inputgroup">
                            <Dropdown field="pago.nickUsuario" name="pago.nickUsuario" value={this.state.nickUsuario} placeholder="Select a student" options={this.allStudentsNames()} onChange={this.handlePU} />

                        </div>
                    </div>

                    <div className="i">
                        {this.state.conceptoError}
                        <div className="p-inputgroup">
                            <Dropdown field="concepto" name="concepto" value={this.state.concepto} placeholder="Select a payment" options={this.allConceptNames()} onChange={this.handleP} />

                        </div>
                    </div>

                    <div className="i">
                        {this.state.tipoError}
                        <div className="p-inputgroup">
                            <Dropdown field="tipo.tipo" name="tipo.tipo" value={this.state.type} placeholder="Select a payment type" options={tipoPagoSelectItems} onChange={this.handlePI} />

                        </div>
                    </div>

                    <div className="b">
                        <div className="i">
                            <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" />

                        </div>
                    </div>
                </div>

            }

        } else if (option === "op2") {
            return <div>
                <div className="t"><div><h5>Register Payment</h5></div></div>

                <div className="i">
                    {this.state.usernameError}
                    <div className="p-inputgroup">
                        <InputText field="pago.nickUsuario" name="pago.nickUsuario" value={this.state.nickUsuario} placeholder="Enter a student's username" onChange={this.handlePU} />

                    </div>
                </div>

                <div className="i">
                    {this.state.conceptoError}
                    <div className="p-inputgroup">
                        <InputText placeholder="New Concept" field="concepto" value={this.state.concepto} name="concepto" onChange={this.handleP} />

                    </div>
                </div>

                <div className="i">
                    {this.state.tipoError}
                    <div className="p-inputgroup">
                        <Dropdown field="tipo" name="tipo" value={this.state.type} placeholder="Select a payment type" options={tipoPagoSelectItems} onChange={this.handlePI} />

                    </div>
                </div>

                <div className="b">
                    <div className="i">
                        <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" />

                    </div>
                </div>
            </div>
        }
    }

    handleF(event) {
        this.setState({
            fecha: event.target.value
        });
    }
    handleNU(event) {
        this.setState({
            nickUsuario: event.target.value
        });
    }

    respuesta(status, data) {
        if (status === 203) {
            data.forEach(e => this.error(e.field, e.defaultMessage))
        } else if (status === 201) {
            this.setState({

                grupoS: {
                    nombreGrupo: "",
                    cursos: {
                        cursoDeIngles: ""
                    }
                },
                succes: <div className="alert alert-success" role="alert">Successful payment</div>
            })

        } else if (status === 204) {
            this.setState({ exist: <div className="alert alert-danger" role="alert">The username doesn't exists</div> })
        } else {
            this.setState({ exist: <div className="alert alert-danger" role="alert">{data}</div> })
        }
    }

    error(campo, mensaje) {
        if (campo === "nickUsuario") {
            this.setState({ usernameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "concepto") {
            this.setState({ conceptoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "tipo") {
            this.setState({ tipoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }
    componentDidMount() {
        this.pagos.getNotPaidByStudent(this.props.nickUser).then(data => this.setState({ listaConcepto: data })).catch(error => this.setState({ comprobation: false }));;
        this.pagos.getNameStudentByNoPago().then(data => this.setState({ listaNombres: data }))
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ listaNombres2: data }))


    }

    allConceptNames() {
        var t = this.state.listaConcepto
        var i = 0
        var conceptoSelectItems = [];
        while (i < t.length) {
            conceptoSelectItems.push(
                { label: String(t[i]), value: String(t[i]) })
            i += 1
        }
        return conceptoSelectItems
    }
    allStudentsNames() {

        var t = this.state.listaNombres
        var i = 0
        var nombresSelectItems = [];
        while (i < t.length) {
            nombresSelectItems.push(
                { label: String(t[i]), value: String(t[i]) })
            i += 1
        }
        return nombresSelectItems
    }

    allStudentsNames2() {

        var t = this.state.listaNombres2[0]
        var i = 0
        var nombresSelectItems = [];
        while (i < t.length) {
            nombresSelectItems.push(
                { label: String(t[i]), value: String(t[i]) })
            i += 1
        }
        return nombresSelectItems
    }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        } else {

            if (this.state.opcion === "") {

                return (
                    <React.Fragment>

                        <div>
                            <div className="c">
                                <div className="login request">
                                    <form onSubmit={this.save}>

                                        {this.state.succes}
                                        {this.state.exist}
                                        {this.form("")}


                                    </form>
                                </div>
                            </div>
                        </div>
                    </React.Fragment>

                );

            } else if (this.state.opcion === "op1") {

                return (
                    <React.Fragment>
                        <div>
                            <div className="c">
                                <div className="login request">
                                    <form onSubmit={this.save}>

                                        {this.state.succes}
                                        {this.state.exist}
                                        {this.form("op1")}

                                    </form>
                                </div>
                            </div>
                        </div>
                    </React.Fragment>
                );

            } else if (this.state.opcion === "op2") {

                return (
                    <React.Fragment>

                        <div>
                            <div className="c">
                                <div className="login request">
                                    <form onSubmit={this.save}>

                                        {this.state.succes}
                                        {this.state.exist}
                                        {this.form("op2")}

                                    </form>
                                </div>
                            </div>
                        </div>
                    </React.Fragment>
                );
            }
        }
    }
}
