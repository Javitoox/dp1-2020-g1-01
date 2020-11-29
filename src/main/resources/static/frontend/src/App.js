import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Solicitudes } from './components/Solicitudes';
import './index.css';
import Login from './components/Login';
import { EditStudent } from './components/EditStudent';
import { SolicitudesProfesor } from './components/SolicitudesProfesor';
import { AlumnosPorTutor } from './components/AlumnosPorTutor';
import { Alumnos } from './components/Alumnos';
import {Home} from './components/Home';
import { MenubarResponsive } from './components/MenubarResponsive';


class App extends Component {

	home = new Home();

	state = {
		urlBase: "http://localhost:8081",
		//tipoDeUsuario: "integrante"
		tipoDeUsuario: "this.home.state.type"
	}

	componentDidMount(){
		console.log(this.home.state.type);
	}

	render() {
		return (
			<div>
			<MenubarResponsive tipoDeUsuario={this.home.state.type}></MenubarResponsive>
				<Router>
					<Route path="/home" render={() =>
						<Home></Home>
					} />
					<Route path="/requests" render={() =>
						<Solicitudes tipoDeUsuario={this.state.tipoDeUsuario} urlBase={this.state.urlBase}></Solicitudes>
					} />
					<Route path="/login" render={() =>
						<Login urlBase={this.state.urlBase}></Login>
					} />
					<Route path="/pendingRequests" render={() =>
						<SolicitudesProfesor urlBase={this.state.urlBase}></SolicitudesProfesor>
					} />
					<Route path="/myStudents" render={() =>
						<AlumnosPorTutor urlBase={this.state.urlBase}></AlumnosPorTutor>
					} />
					<Route path="/allStudents" render={() =>
						<Alumnos urlBase={this.state.urlBase}></Alumnos>
					} />
					<Route path="/editStudent" render={() =>
						<EditStudent></EditStudent>
					} />
				</Router>
			</div>
		)
	}
}

export default App;