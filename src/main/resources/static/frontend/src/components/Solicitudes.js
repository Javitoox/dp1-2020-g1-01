import { Component } from 'react';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import FormTutor from './FormTutor';
import Inject from './Inject';
import {Password} from 'primereact/password';
import axios from 'axios';

export class Solicitudes extends Component {

    username = this.username.bind(this);
    password = this.password.bind(this);
    card = this.card.bind(this);
    name = this.name.bind(this);
    email = this.email.bind(this);
    telefono = this.telefono.bind(this);
    telefono2 = this.telefono2.bind(this);
    address = this.address.bind(this);
    birthdate = this.birthdate.bind(this);
    username1 = this.username1.bind(this);
    password1 = this.password1.bind(this);
    card1 = this.card1.bind(this);
    name1 = this.name1.bind(this);
    email1 = this.email1.bind(this);
    telefono1 = this.telefono1.bind(this);
    telefono21 = this.telefono21.bind(this);
    address1 = this.address1.bind(this);
    birthdate1 = this.birthdate1.bind(this);
    button = this.button.bind(this);

    state = {
        username: "",
        password: "",
        card: "",
        name: "",
        email: "",
        telefono: "",
        telefono2: "",
        address: "",
        birthdate: "",
        username1: "",
        password1: "",
        card1: "",
        name1: "",
        email1: "",
        telefono1: "",
        telefono21: "",
        address1: "",
        birthdate1:"",
        button:false
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

    birthdate(event) {
        this.setState({ birthdate: event.target.value });
    }

    username1(event) {
        this.setState({ username1: event.target.value });
    }

    password1(event) {
        this.setState({ password1: event.target.value });
    }

    card1(event) {
        this.setState({ card1: event.target.value });
    }

    name1(event) {
        this.setState({ name1: event.target.value });
    }

    email1(event) {
        this.setState({ email1: event.target.value });
    }

    telefono1(event) {
        this.setState({ telefono1: event.target.value });
    }

    telefono21(event) {
        this.setState({ telefono21: event.target.value });
    }

    address1(event) {
        this.setState({ address1: event.target.value });
    }

    birthdate1(event) {
        this.setState({ birthdate1: event.target.value });
    }

    button(event) {
        this.setState({ button: !this.state.button })
    }

    form() {
        return <FormTutor username={this.state.username1} password={this.state.password1} card={this.state.card1} name={this.state.name1} email={this.state.email1}
        telefono={this.state.telefono1} telefono2={this.state.telefono21} address={this.state.address1} birthdate={this.state.birthdate1} 
        onUsername={this.username1} onPassword={this.password1} onCard={this.card1} onName={this.name1} onEmail={this.email1} onTelefono={this.telefono1} onTelefono2={this.telefono21} 
        onAddress={this.address1} onBirthdate={this.birthdate1}></FormTutor>
    }

    otherNumber() {
        return <div className="i">
            <div className="p-inputgroup">
                <span className="p-inputgroup-addon">
                    <i className="pi pi-mobile"></i>
                </span>
                <InputText placeholder="Phone number" name="alumno.numTelefonoUsuario2" type="tel" value={this.state.telefono2} onChange={this.telefono2} />
            </div>
        </div>
    }

    handleSubmit = event => {
        event.preventDefault();

        const alumno = {
            nickUsuario: this.state.username,
            contraseya: this.state.password,
            dniUsuario: this.state.card,
            nombreCompletoUsuario: this.state.name,
            correoElectronicoUsuario: this.state.email,
            numTelefonoUsuario: this.state.telefono,
            numTelefonoUsuario2: this.state.telefono2,
            direccionUsuario: this.state.address,
            fechaNacimiento: this.state.birthdate
        }

        const tutor = {
            nickUsuario: this.state.username1,
            contraseya: this.state.password1,
            dniUsuario: this.state.card1,
            nombreCompletoUsuario: this.state.name1,
            correoElectronicoUsuario: this.state.email1,
            numTelefonoUsuario: this.state.telefono1,
            numTelefonoUsuario2: this.state.telefono21,
            direccionUsuario: this.state.address1,
            fechaNacimiento: this.state.birthdate1
        }

        if (this.state.button) {
            axios.post(this.props.urlBase + "/requests/sendingAll", {alumno, tutor}).then(res => {
                console.log(res);
                console.log(res.data);
            })
        } else {
            axios.post(this.props.urlBase + "/requests/sending", {alumno}).then(res => {
                console.log(res);
                console.log(res.data);
            })
        }
    }

    render() {
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.handleSubmit}>
                            <div className="t"><div><h5>Request</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="alumno.nickUsuario" type="text" value={this.state.username} onChange={this.username} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <Password mediumRegex="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,30}$" strongRegex="^^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{14,30}$$" 
                                    placeholder="Password" name="alumno.contraseya" value={this.state.password} onChange={this.password} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-id-card"></i>
                                    </span>
                                    <InputText placeholder="Identity card" name="alumno.dniUsuario" type="text" value={this.state.card} onChange={this.card} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user-plus"></i>
                                    </span>
                                    <InputText placeholder="Full Name" name="alumno.nombreCompletoUsuario" type="text" value={this.state.name} onChange={this.name} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-inbox"></i>
                                    </span>
                                    <InputText placeholder="Email" name="alumno.correoElectronicoUsuario" type="email" value={this.state.email} onChange={this.email} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-mobile"></i>
                                    </span>
                                    <InputText placeholder="Phone number" name="alumno.numTelefonoUsuario" type="tel" value={this.state.telefono} onChange={this.telefono} />
                                </div>
                            </div>
                            <Inject activated={false} content={this.otherNumber()} message="Add another phone number"></Inject>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-home"></i>
                                    </span>
                                    <InputText placeholder="Address" name="alumno.direccionUsuario" type="text" value={this.state.address} onChange={this.address} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-calendar"></i>
                                    </span>
                                    <InputText placeholder="Birthdate" name="alumno.fechaNacimiento" type="date" value={this.state.birthdate} onChange={this.birthdate} />
                                </div>
                            </div>
                            <Inject onActivate={this.button} activated={true} content={this.form()} message="Under-age"></Inject>
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