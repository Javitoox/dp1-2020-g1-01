import React, { Component } from 'react';
import { MenubarResponsive } from './components/MenubarResponsive';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Solicitudes } from './components/Solicitudes';
import './index.css';
import { Login } from './components/Login';
import ExtraccionMensajes from './components/ExtraccionMensajes';
import { EditAlumno } from './components/EditAlumno';
import { SolicitudesProfesor } from './components/SolicitudesProfesor';
import { AlumnosPorTutor } from './components/AlumnosPorTutor';


class App extends Component {

	extraccion = new ExtraccionMensajes();
	constructor(){
		super()
		this.state = {
			urlBase: "http://localhost:8081",
			tipoDeUsuario:"usuario",
			nickUsuario: this.extraccion.getParameterByName("nickUsuario")
		}
	}
	
	componentDidMount(){
		var m = this.extraccion.getParameterByName("message");
		if(m!==""){
			this.setState({tipoDeUsuario:m});
		}
	}

	render() {
		
		console.log( this.state.nickUsuario)
		return (
			<React.Fragment>
				<MenubarResponsive tipoDeUsuario={this.state.tipoDeUsuario}></MenubarResponsive>
				<Router>
					<Route path="/requests" render={() =>
						<Solicitudes tipoDeUsuario={this.state.tipoDeUsuario} urlBase={this.state.urlBase}></Solicitudes>
					} />
					<Route path="/login" render={() =>
						<Login urlBase={this.state.urlBase}></Login>
					} />
					<Route path="/solicitudesProfesor" render= {() =>
						<SolicitudesProfesor></SolicitudesProfesor>
					}/>
					<Route path="/myStudents" render={()=>
						<AlumnosPorTutor nickUsuario={this.state.nickUsuario}></AlumnosPorTutor>
					}>
					</Route>
				</Router>
			</React.Fragment>
		)
	}
}

export default App;