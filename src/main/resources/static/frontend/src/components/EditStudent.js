import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import eventBus from "./EventBus";
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';






  class EditStudent extends Component {
    nickUsuario = this.nickUsuario.bind(this);
    contraseya = this.contraseya.bind(this);
    dniUsuario = this.dniUsuario.bind(this);
    nombreCompletoUsuario = this.nombreCompletoUsuario.bind(this);
    correoElectronicoUsuario = this.correoElectronicoUsuario.bind(this);
    numTelefonoUsuario = this.numTelefonoUsuario.bind(this);
    direccionUsuario = this.direccionUsuario.bind(this);
    fechaNacimiento = this.fechaNacimiento.bind(this);
    numTareasEntregadas = this.numTareasEntregadas.bind(this);
    changeButton = this.changeButton.bind(this);
    fechaMatriculacion = this.fechaMatriculacion.bind(this);
    changing = this.changing.bind(this);
    constructor() {
        super();
        this.state = {
            change: false,
            nickUsuario: "",
            contraseya: "",
            dniUsuario: "",
            nombreCompletoUsuario: "",
            correoElectronicoUsuario: "",
            numTelefonoUsuario: "",
            direccionUsuario: "",
            fechaNacimiento: "",
            numTareasEntregadas: "",
            fechaMatriculacion: ""
        }
    }
    me() {
        eventBus.on("guardandoEstudiante", (data) => this.estadoActualizado(data));
        this.setState({ change: true });
    }
    changing(event) {
        if(this.state.change)
        this.setState({ change: false });
    }
     estadoActualizado(data) {
         console.log("hola");
        this.setState({
            nickUsuario: data.nickUsuario,
            contraseya: data.contraseya,
            dniUsuario: data.dniUsuario,
            nombreCompletoUsuario: data.nombreCompletoUsuario,
            correoElectronicoUsuario: data.correoElectronicoUsuario,
            numTelefonoUsuario: data.numTelefonoUsuario,
            direccionUsuario: data.direccionUsuario,
            fechaNacimiento: data.fechaNacimiento,
            numTareasEntregadas: data.numTareasEntregadas,
            fechaMatriculacion: data.fechaMatriculacion
        }
        )
    }

    componentWillUnmount() {
        eventBus.remove("guardandoEstudiante");
    }
    nickUsuario(event) {
        this.setState({ nickUsuario: event.target.value });
    }

    contraseya(event) {
        this.setState({ contraseya: event.target.value });
    }

    fechaMatriculacion(event) {
        this.setState({ fechaMatriculacion: event.target.value });
    }

    nombreCompletoUsuario(event) {
        this.setState({ nombreCompletoUsuario: event.target.value })
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
    numTareasEntregadas(event) {
        this.setState({ numTareasEntregadas: event.target.value });
    }

    direccionUsuario(event) {
        this.setState({ direccionUsuario: event.target.value });
    }

    fechaNacimiento(event) {
        this.setState({ fechaNacimiento: event.target.value });
    }

    changeButton(event) {
        this.setState({ button: !this.state.button });
    }
    urlBase = "http://localhost:8081/alumnos/editStudent"
    listNombre(){
        return this.props.students.map((user) => {
            return user.nickUsuario
        })
    }
    render() {
        console.log("Se ha redireccionado")
        console.log(this.listNombre())
        //<InputText type="hidden" value={this.state.change} onChange={this.changing}></InputText>
        return (
            <div>
                xddd
                <div className="c">
                    <div className="login request">
                        <form method="GET" action={this.urlBase} >
                            <div className="t"><div><h5>Edit Student</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="nickUsuario" type="text" value={this.props.students.map((user) => {
            return user.nickUsuario
        })} onChange={this.nickUsuario} />
                                </div>
                            </div>

                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <InputText placeholder="Password" name="contraseya" type="text" value={this.state.contraseya} onChange={this.contraseya} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-id-card"></i>
                                    </span>
                                    <InputText placeholder="dniUsuario" name="dniUsuario" type="text" value={this.state.dniUsuario} onChange={this.dniUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user-plus"></i>
                                    </span>
                                    <InputText placeholder="nombreCompletoUsuario" name="nombreCompletoUsuario" type="text" value={this.state.nombreCompletoUsuario} onChange={this.nombreCompletoUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-inbox"></i>
                                    </span>
                                    <InputText placeholder="correoElectronicoUsuario" name="correoElectronicoUsuario" type="text" value={this.state.correoElectronicoUsuario} onChange={this.correoElectronicoUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-mobile"></i>
                                    </span>
                                    <InputText placeholder="numTelefonoUsuario" name="numTelefonoUsuario" type="text" value={this.state.numTelefonoUsuario} onChange={this.numTelefonoUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-home"></i>
                                    </span>
                                    <InputText placeholder="direccionUsuario" name="direccionUsuario" type="text" value={this.state.direccionUsuario} onChange={this.direccionUsuario} />
                                </div>
                            </div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-calendar"></i>
                                    </span>
                                    <InputText placeholder="fechaNacimiento" name="fechaNacimiento" type="text" value={this.state.fechaNacimiento} onChange={this.fechaNacimiento} />
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

function mapStateToProps(state){
    return{
        students : state.students
    }
}

export default connect(mapStateToProps)(EditStudent);
