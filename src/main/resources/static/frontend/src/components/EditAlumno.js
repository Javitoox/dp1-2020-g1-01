import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';




class EditAlumno extends Component {
    username = this.username.bind(this);
    password = this.password.bind(this);
    card = this.card.bind(this);
    fechaMatriculacion = this.fechaMatriculacion.bind(this);
    dniUsuario = this.dniUsuario.bind(this);
    correoElectronicoUsuario = this.correoElectronicoUsuario.bind(this);
    numTelefonoUsuario= this.numTelefonoUsuario.bind(this);
    direccionUsuario = this.direccionUsuario.bind(this);
    fechaNacimiento= this.fechaNacimiento.bind(this);
    changeButton = this.changeButton.bind(this);
    nombreCompletoUsuario= this.nombreCompletoUsuario.bind(this);
    
state= {

    username: "Gonzalo",
    password: "Holaaa",
    card: "20502441B",
    fechaMatriculacion: "01/01/2018",
    nombreCompletoUsuario: "Gonzalo Alvarez Garcia",
    dniUsuario: "20502441B",
    correoElectronicoUsuario: "gonalvgar,alumno@gmail.com",
    numTelefonoUsuario:"622119555",
    direccionUsuario :"Yucatan nº 3",
    fechaNacimiento: "03/10/1998"

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

    fechaMatriculacion(event) {
        this.setState({ fechaMatriculacion: event.target.value });
    }

    nombreCompletoUsuario(event){
        this.setState({ nombreCompletoUsuario: event.target.value})
    }

    dniUsuario(event) {
        this.setState({ dniUsuario: event.target.value });
    }

    correoElectronicoUsuario(event) {
        this.setState({ correoElectronicoUsuario: event.target.value });
    }

    numTelefonoUsuario(event) {
        this.setState({ numTelefonoUsuario: event.target.value });
    }

    direccionUsuario(event) {
        this.setState({ direccionUsuario: event.target.value });
    }

    fechaNacimiento(event) {
        this.setState({ fechaNacimiento: event.target.value });
    }

    changeButton(event){
        this.setState({button: !this.state.button});
    }
    baseUrl = "http://localhost:8081/alumnos/{nick_usuario}/edit";
    
    render() {
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form method="POST"  action={this.props.urlBase} >
                        <div className="t"><div><h5>Request</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="nickUsuario" type="text" value={this.state.username} onChange={this.username}/>
                                </div>
                            </div>

                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <InputText placeholder="Password" name="contraseya" type="text" value={this.state.password} onChange={this.password}/>
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-id-card"></i>
                                    </span>
                                    <InputText placeholder="dniUsuario" name="dniUsuario" type="text" value={this.state.card} onChange={this.card}/>
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user-plus"></i>
                                    </span>
                                    <InputText placeholder="nombreCompletoUsuario" name="nombreCompletoUsuario" type="text" value={this.state.nombreCompletoUsuario} onChange={this.name}/>
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-inbox"></i>
                                    </span>
                                    <InputText placeholder="correoElectronicoUsuario" name="correoElectronicoUsuario" type="text" value={this.state.correoElectronicoUsuario} onChange={this.email}/>
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-mobile"></i>
                                    </span>
                                    <InputText placeholder="numTelefonoUsuario" name="numTelefonoUsuario" type="text" value={this.state.numTelefonoUsuario} onChange={this.telefono}/>
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-home"></i>
                                    </span>
                                    <InputText placeholder="direccionUsuario" name="direccionUsuario" type="text" value={this.state.direccionUsuario} onChange={this.address}/>
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-calendar"></i>
                                    </span>
                                    <InputText placeholder="fechaNacimiento" name="fechaNacimiento" type="text" value={this.state.fechaNacimiento} onChange={this.birthdate}/>
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
export {EditAlumno}; 