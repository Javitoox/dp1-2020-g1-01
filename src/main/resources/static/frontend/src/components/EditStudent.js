import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
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
   
//importante sacar la informacion de la variable student en el estado, para poder editarla y procesarla despues en la tabla
      state = {
            change: false, 
            nickUsuario: this.props.student.nickUsuario,
            contraseya: this.props.student.contraseya,
            dniUsuario: this.props.student.dniUsuario,
            nombreCompletoUsuario: this.props.student.nombreCompletoUsuario,
            correoElectronicoUsuario: this.props.student.correoElectronicoUsuario,
            numTelefonoUsuario: this.props.student.numTelefonoUsuario,
            direccionUsuario: this.props.student.direccionUsuario,
            fechaNacimiento: this.props.student.fechaNacimiento,
            numTareasEntregadas: this.props.student.numTareasEntregadas,
            fechaMatriculacion: this.props.student.fechaMatriculacion
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
    render() {
        console.log("Se ha redireccionado")
        //<InputText type="hidden" value={this.state.change} onChange={this.changing}></InputText>
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form method="GET" action={this.urlBase} >
                            <div className="t"><div><h5>Edit Student</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="nickUsuario" type="text" value={this.state.nickUsuario} onChange={this.nickUsuario} />
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

function mapStateToProps(state){ //metodo para poder pillar datos del store
    return{
        student : state.student //le pasamos a nuestra variable student la informacion del estudiante almacenada en el store
    }
}

export default connect(mapStateToProps)(EditStudent); 
