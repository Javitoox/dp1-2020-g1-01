import React, { Component } from 'react';
import { MenubarResponsive } from './components/MenubarResponsive';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Solicitudes } from './components/Solicitudes';
import './index.css';
import { Login } from './components/Login';
import ExtraccionMensajes from './components/ExtraccionMensajes';
import { SolicitudesProfesor } from './components/SolicitudesProfesor';

class App extends Component {

	extraccion = new ExtraccionMensajes();

	state = {
		urlBase: "http://localhost:8081",
		tipoDeUsuario:"usuario"
	}

	componentDidMount(){
		var m = this.extraccion.getParameterByName("message");
		if(m!==""){
			this.setState({tipoDeUsuario:m});
		}
	}

	render() {
		return (
			<React.Fragment>
				<MenubarResponsive tipoDeUsuario={this.state.tipoDeUsuario}></MenubarResponsive>
				<Router>
					<Route path="/requests" render={() =>
						<Solicitudes tipoDeUsuario={this.state.tipoDeUsuario}></Solicitudes>
					} />
					<Route path="/login" render={() =>
						<Login urlBase={this.state.urlBase}></Login>
					} />
				</Router>
			</React.Fragment>
		)
	}
}

export default App;