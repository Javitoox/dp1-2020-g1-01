import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import Inject from './Inject';
import Auth from './Auth';
import axios from 'axios';
import UserData from './UserData'
export default class EditPersonalInfo extends Component {

    nickUsuario = this.nickUsuario.bind(this);
    contraseya = this.contraseya.bind(this);
    dniUsuario = this.dniUsuario.bind(this);
    nombreCompletoUsuario = this.nombreCompletoUsuario.bind(this);
    correoElectronicoUsuario = this.correoElectronicoUsuario.bind(this);
    numTelefonoUsuario = this.numTelefonoUsuario.bind(this);
    numTelefonoUsuario2 = this.numTelefonoUsuario2.bind(this);
    direccionUsuario = this.direccionUsuario.bind(this);
    fechaNacimiento = this.fechaNacimiento.bind(this);
    fechaMatriculacion = this.fechaMatriculacion.bind(this);
    buttonTel1 = this.buttonTel1.bind(this);
    buttonTel2 = this.buttonTel2.bind(this);
    constructor() {
        super()
        this.state = {
            button: false,
            buttonTel1: false,
            buttonTel2: false,
            usernameError: null,
            passwordError: null,
            cardError: null,
            nameError: null,
            emailError: null,
            telefonoError: null,
            telefono2Error: null,
            addressError: null,
            birthdateError: null,
            succes: null,
            comprobation: false,
        }
        this.userDataComponent = new UserData();
    }
  async componentDidMount() {
        console.log(this.props.nickUser);
        await this.userDataComponent.getAlumnoInfo(this.props.urlBase, this.props.nickUser).then(data => this.setState({
            nickUsuario: data.nickUsuario,
            contraseya: data.contraseya,
            dniUsuario: data.dniUsuario,
            nombreCompletoUsuario: data.nombreCompletoUsuario,
            correoElectronicoUsuario: data.correoElectronicoUsuario,
            numTelefonoUsuario: data.numTelefonoUsuario,
            numTelefonoUsuario2: data.numTelefonoUsuario2,
            direccionUsuario: data.direccionUsuario,
            fechaNacimiento: data.fechaNacimiento,
            fechaMatriculacion: data.fechaMatriculacion,
        }))
     
       await axios.get(this.props.urlBase + "/auth", {withCredentials: true}).then(res => {
            if(res.data==="alumno"){
                this.setState({comprobation: true})
            }
            })
    }
    nickUsuario(event) {
        this.setState({ nickUsuario: event.target.value });
    }

    contraseya(event) {
        this.setState({ contraseya: event.target.value });
    }

    dniUsuario(event) {
        this.setState({ dniUsuario: event.target.value });
    }

    nombreCompletoUsuario(event) {
        this.setState({ nombreCompletoUsuario: event.target.value });
    }

    correoElectronicoUsuario(event) {
        this.setState({ correoElectronicoUsuario: event.target.value });
    }

    numTelefonoUsuario(event) {
        this.setState({ numTelefonoUsuario: event.target.value });
    }

    numTelefonoUsuario2(event) {
        this.setState({ numTelefonoUsuario2: event.target.value });
    }

    direccionUsuario(event) {
        this.setState({ direccionUsuario: event.target.value });
    }

    fechaMatriculacion(event) {
        this.setState({ fechaMatriculacion: event.target.value });
    }

    fechaNacimiento(event) {
        this.setState({ fechaNacimiento: event.target.value });
    }

    button(event) {
        this.setState({ button: !this.state.button })
    }

    buttonTel1(event) {
        this.setState({ buttonTel1: !this.state.buttonTel1 })
    }

    buttonTel2(event) {
        this.setState({ buttonTel2: !this.state.buttonTel2 })
    }

    otherNumber() {
        return <div className="i">
            <div className="p-inputgroup">
                <span className="p-inputgroup-addon">
                    <i className="pi pi-mobile"></i>
                </span>
                <InputText placeholder="Phone number" name="alumno.numTelefonoUsuario2" type="tel" value={this.state.numTelefonoUsuario2 || ""} onChange={this.numTelefonoUsuario2} />
            </div>
        </div>
    }

    handleSubmit = event => {
        event.preventDefault();
        console.log("entra en el submit");
        this.setState({
            usernameError: null,
            passwordError: null,
            cardError: null,
            nameError: null,
            emailError: null,
            telefonoError: null,
            telefono2Error: null,
            addressError: null,
            birthdateError: null,
            succes: null
        })

        const alumno = {
            nickUsuario: this.state.nickUsuario,
            contraseya: this.state.contraseya,
            dniUsuario: this.state.dniUsuario,
            nombreCompletoUsuario: this.state.nombreCompletoUsuario,
            correoElectronicoUsuario: this.state.correoElectronicoUsuario,
            numTelefonoUsuario: this.state.numTelefonoUsuario,
            numTelefonoUsuario2: this.state.numTelefonoUsuario2,
            direccionUsuario: this.state.direccionUsuario,
            fechaNacimiento: this.state.fechaNacimiento,
            fechaMatriculacion: this.state.fechaMatriculacion
        }
        if (!this.state.buttonTel1) {
            alumno.numTelefonoUsuario2 = null
        }
        axios.put(this.props.urlBase + "/alumnos/editStudent", alumno, { withCredentials: true }).then(res => {
            this.respuesta(res.status, res.data)
        })

    }

    respuesta(status, data) {
        console.log(status);
        if (status === 203) {
            data.forEach(e => this.error(e.field, e.defaultMessage))
        } else {
            this.setState({
                nickUsuario: this.state.nickUsuario,
                contraseya: this.state.contraseya,
                dniUsuario: this.state.dniUsuario,
                nombreCompletoUsuario: this.state.nombreCompletoUsuario,
                correoElectronicoUsuario: this.state.correoElectronicoUsuario,
                numTelefonoUsuario: this.state.numTelefonoUsuario,
                numTelefonoUsuario2: this.state.numTelefonoUsuario2,
                direccionUsuario: this.state.direccionUsuario,
                fechaNacimiento: this.state.fechaNacimiento,
                succes: <div className="alert alert-success" role="alert">Successful shipment</div>
            })

        }
    }

    error(campo, mensaje) {
        if (campo === "nickUsuario") {
            this.setState({ usernameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "contraseya") {
            this.setState({ passwordError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "dniUsuario") {
            this.setState({ cardError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "nombreCompletoUsuario") {
            this.setState({ nameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "correoElectronicoUsuario") {
            this.setState({ emailError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "numTelefonoUsuario") {
            this.setState({ telefonoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "numTelefonoUsuario2") {
            this.setState({ telefono2Error: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "direccionUsuario") {
            this.setState({ addressError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "fechaNacimiento") {
            this.setState({ birthdateError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="alumno"></Auth>
        } else {
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.handleSubmit}>
                            {this.state.succes}
                            <div className="t"><div><h5>Modify</h5></div></div>
                            <div className="i">
                                {this.state.usernameError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="nickUsuario" type="text" value={this.state.nickUsuario} readOnly />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.passwordError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <Password mediumRegex="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,30}$" strongRegex="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{14,30}$"
                                        placeholder="Password" name="contraseya" value={this.state.contraseya} readOnly />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.cardError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-id-card"></i>
                                    </span>
                                    <InputText placeholder="Identity card" name="dniUsuario" type="text" value={this.state.dniUsuario} readOnly />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.nameError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user-plus"></i>
                                    </span>
                                    <InputText placeholder="Full Name" name="nombreCompletoUsuario" type="text" value={this.state.nombreCompletoUsuario} readOnly />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.emailError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-inbox"></i>
                                    </span>
                                    <InputText placeholder="Email" name="correoElectronicoUsuario" type="email" value={this.state.correoElectronicoUsuario} onChange={this.correoElectronicoUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.telefonoError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-mobile"></i>
                                    </span>
                                    <InputText placeholder="Phone number" name="numTelefonoUsuario" type="tel" value={this.state.numTelefonoUsuario} onChange={this.numTelefonoUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.telefono2Error}
                                <Inject onActivate={this.buttonTel1} activated={true} content={this.otherNumber()} message="Add or modify a second phone number"></Inject>
                            </div>
                            <div className="i">
                                {this.state.addressError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-home"></i>
                                    </span>
                                    <InputText placeholder="Address" name="direccionUsuario" type="text" value={this.state.direccionUsuario} readOnly />
                                </div>
                            </div>
                            <div className="i">
                                {this.state.birthdateError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-calendar"></i>
                                    </span>
                                    <InputText placeholder="Birthdate" name="fechaNacimiento" type="date" value={this.state.fechaNacimiento} readOnly />
                                </div>
                            </div>
                            <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}
}