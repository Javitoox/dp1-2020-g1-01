import React, { Component } from 'react';
import { MenubarResponsive } from './components/MenubarResponsive';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Solicitudes } from './components/Solicitudes';
import './index.css';
import { Login } from './components/Login';

class App extends Component {

	render() {
		return (
			<React.Fragment>
				<MenubarResponsive tipoDeUsuario="usuario"></MenubarResponsive>
				<Router>
					<Route path="/requests" render={()=> 
					<Solicitudes tipoDeUsuario="usuario"></Solicitudes>
					} />
					<Route path="/login" render={()=> 
					<Login></Login>
					} />
				</Router>
			</React.Fragment>
		)
	}
}

export default App;