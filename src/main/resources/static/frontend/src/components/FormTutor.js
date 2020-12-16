import { Component } from 'react';
import { InputText } from 'primereact/inputtext';

export default class FormTutor extends Component {
    username = this.username.bind(this);
    password = this.password.bind(this);
    card = this.card.bind(this);
    name = this.name.bind(this);
    email = this.email.bind(this);
    telefono = this.telefono.bind(this);
    address = this.address.bind(this);
    birthdate = this.birthdate.bind(this);

    state = {
        username: "",
        password: "",
        card: "",
        name: "",
        email: "",
        telefono: "",
        address: "",
        birthdate: ""
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

    address(event) {
        this.setState({ address: event.target.value });
    }

    birthdate(event) {
        this.setState({ birthdate: event.target.value });
    }

    render() {
        return (
            <div>
                <div className="t"><div><h5>Tutor</h5></div></div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-user"></i>
                        </span>
                        <InputText placeholder="Username" name="nickUsuario" type="text" value={this.state.username} onChange={this.username} />
                    </div>
                </div>

                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-lock"></i>
                        </span>
                        <InputText placeholder="Password" name="contraseya" type="text" value={this.state.password} onChange={this.password} />
                    </div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-id-card"></i>
                        </span>
                        <InputText placeholder="Identity card" name="dniUsuario" type="text" value={this.state.card} onChange={this.card} />
                    </div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-user-plus"></i>
                        </span>
                        <InputText placeholder="Full Name" name="nombreCompletoUsuario" type="text" value={this.state.name} onChange={this.name} />
                    </div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-inbox"></i>
                        </span>
                        <InputText placeholder="Email" name="correoElectronicoUsuario" type="text" value={this.state.email} onChange={this.email} />
                    </div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-mobile"></i>
                        </span>
                        <InputText placeholder="Phone number" name="numTelefonoUsuario" type="text" value={this.state.telefono} onChange={this.telefono} />
                    </div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-home"></i>
                        </span>
                        <InputText placeholder="Address" name="direccionUsuario" type="text" value={this.state.address} onChange={this.address} />
                    </div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-calendar"></i>
                        </span>
                        <InputText placeholder="Birthdate" name="fechaNacimiento" type="date" value={this.state.birthdate} onChange={this.birthdate} />
                    </div>
                </div>
            </div>
        );
    }

}