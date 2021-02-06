import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { connect } from 'react-redux';
import axios from 'axios';
import {Password} from 'primereact/password';
import Inject from './Inject';
import Auth from './Auth';
import AuthenticationService from '../service/AuthenticationService';
class EditStudent extends Component {

    username = this.username.bind(this);
    password = this.password.bind(this);
    card = this.card.bind(this);
    name = this.name.bind(this);
    email = this.email.bind(this);
    telefono = this.telefono.bind(this);
    telefono2 = this.telefono2.bind(this);
    address = this.address.bind(this);
    birthdate = this.birthdate.bind(this);
    fechaMatriculacion = this.fechaMatriculacion.bind(this);
    buttonTel1 = this.buttonTel1.bind(this);
    buttonTel2 = this.buttonTel2.bind(this);
    
    state = {
        username: this.props.student.nickUsuario,
        password: this.props.student.contraseya,
        card: this.props.student.dniUsuario,
        name: this.props.student.nombreCompletoUsuario,
        email: this.props.student.correoElectronicoUsuario,
        telefono: this.props.student.numTelefonoUsuario,
        telefono2: this.props.student.numTelefonoUsuario2,
        address: this.props.student.direccionUsuario,
        birthdate: this.props.student.fechaNacimiento,
        fechaMatriculacion: this.props.student.fechaMatriculacion,
        numTareasEntregadas: this.props.student.numTareasEntregadas,
        fechaSolicitud: this.props.student.fechaSolicitud,
        button:false,
        buttonTel1:false,
        buttonTel2:false,
        usernameError:null,
        passwordError:null,
        cardError:null,
        nameError:null,
        emailError:null,
        telefonoError:null,
        telefono2Error:null,
        addressError:null,
        birthdateError:null,
        succes:null,
        comprobation: false,
    }
    componentDidMount(){
        axios.get("http://localhost:8081/basicauth", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
            }
            })
    }
    username(event) {
        this.setState({ username: event.target.value });
    }

    password(event) {
        this.setState({ password: event.target.value });
    }

    card(event) {
        this.setState({ card: event.target.value });
    }

    name(event) {
        this.setState({ name: event.target.value });
    }

    email(event) {
        this.setState({ email: event.target.value });
    }

    telefono(event) {
        this.setState({ telefono: event.target.value });
    }

    telefono2(event) {
        this.setState({ telefono2: event.target.value });
    }

    address(event) {
        this.setState({ address: event.target.value });
    }

    fechaMatriculacion(event) {
        this.setState({ fechaMatriculacion: event.target.value });
    }

    birthdate(event) {
        this.setState({ birthdate: event.target.value });
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
                <InputText placeholder="Phone number" name="numTelefonoUsuario2" type="tel" value={this.state.telefono2 || ""} onChange={this.telefono2} />
            </div>
        </div>
    }

    handleSubmit = event => {
        event.preventDefault();

        this.setState({
            usernameError:null,
            passwordError:null,
            cardError:null,
            nameError:null,
            emailError:null,
            telefonoError:null,
            telefono2Error:null,
            addressError:null,
            birthdateError:null,
            succes:null
        })

        const alumno = {
            nickUsuario: this.state.username,
            contraseya: this.state.password,
            dniUsuario: this.state.card,
            nombreCompletoUsuario: this.state.name,
            correoElectronicoUsuario: this.state.email,
            numTelefonoUsuario: this.state.telefono,
            numTelefonoUsuario2: this.state.telefono2,
            direccionUsuario: this.state.address,
            fechaNacimiento: this.state.birthdate,
            fechaMatriculacion: this.state.fechaMatriculacion,
            numTareasEntregadas: this.state.numTareasEntregadas,
            fechaSolicitud: this.state.fechaSolicitud
        }
        if(!this.state.buttonTel1){
            alumno.numTelefonoUsuario2 = null
        } 
            axios.put(this.props.urlBase + "/alumnos/editStudent", alumno, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
            sessionStorage.getItem("password")) } }).then(res => {
            this.respuesta(res.status, res.data)
            })
        
    }
    
    respuesta(status, data){
        if(status===203 ){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else{
            this.setState({
                username: this.state.username,
                password: this.state.password,
                card: this.state.card,
                name: this.state.name,
                email: this.state.email,
                telefono: this.state.telefono,
                telefono2: this.state.telefono2,
                address: this.state.address,
                birthdate: this.state.birthdate,
                succes: <div className="alert alert-success" role="alert">Modified Succesfully</div>
            })
        }
    }

    error(campo, mensaje){
        if(campo === "nickUsuario"){
            this.setState({ usernameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "contraseya"){
            this.setState({ passwordError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "dniUsuario"){
            this.setState({ cardError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "nombreCompletoUsuario"){
            this.setState({ nameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "correoElectronicoUsuario"){
            this.setState({ emailError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "numTelefonoUsuario"){
            this.setState({ telefonoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "numTelefonoUsuario2"){
            this.setState({ telefono2Error: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "direccionUsuario"){
            this.setState({ addressError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "fechaNacimiento"){
            this.setState({ birthdateError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }
       
    render() {
        if (!this.state.comprobation) {
            return <Auth authority="profesor"></Auth>
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
                                    <InputText placeholder="Username" name="nickUsuario" type="text" value={this.state.username} readOnly />
                                </div>
                            </div>
                            <div className="i">
                            {this.state.passwordError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <Password mediumRegex="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,30}$" strongRegex="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{14,30}$" 
                                    placeholder="Password" name="contraseya" value={this.state.password} onChange={this.password} />
                                </div>
                            </div>
                            <div className="i">
                            {this.state.cardError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-id-card"></i>
                                    </span>
                                    <InputText placeholder="Identity card" name="dniUsuario" type="text" value={this.state.card} onChange={this.card} />
                                </div>
                            </div>
                            <div className="i">
                            {this.state.nameError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user-plus"></i>
                                    </span>
                                    <InputText placeholder="Full Name" name="nombreCompletoUsuario" type="text" value={this.state.name} onChange={this.name} />
                                </div>
                            </div>
                            <div className="i">
                            {this.state.emailError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-inbox"></i>
                                    </span>
                                    <InputText placeholder="Email" name="correoElectronicoUsuario" type="email" value={this.state.email} onChange={this.email} />
                                </div>
                            </div>
                            <div className="i">
                            {this.state.telefonoError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-mobile"></i>
                                    </span>
                                    <InputText placeholder="Phone number" name="numTelefonoUsuario" type="tel" value={this.state.telefono} onChange={this.telefono} />
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
                                    <InputText placeholder="Address" name="direccionUsuario" type="text" value={this.state.address} onChange={this.address} />
                                </div>
                            </div>
                            <div className="i">
                            {this.state.birthdateError}
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-calendar"></i>
                                    </span>
                                    <InputText placeholder="Birthdate" name="fechaNacimiento" type="date" value={this.state.birthdate} onChange={this.birthdate} />
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
function mapStateToProps(state) { //metodo para poder pillar datos del store
    return {
        student: state.student //le pasamos a nuestra variable student la informacion del estudiante almacenada en el store
    }
}

export default connect(mapStateToProps)(EditStudent);