import React, { Component } from 'react';
import './App.css';
import {AlumnoComponent} from './components/AlumnoComponent';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {MenubarDemo} from './components/MenubarDemo';

class App extends Component {
	constructor(){
		super();
		this.state = {}
		this.alumnoComponent = new AlumnoComponent();
	}
	componentDidMount(){
		this.alumnoComponent.getAllAlumnos().then(data => this.setState({alumnos:data}));
	}
	render(){
		return(
			<React.Fragment>
				<MenubarDemo></MenubarDemo>
				<h2>LISTA DE ALUMNOS</h2>
				<DataTable value={this.state.alumnos}> 
		            <Column field="id" header="ID"></Column>
		            <Column field="name" header="name"></Column>
		            <Column field="dni" header="dni"></Column>
		            <Column field="correo" header="correo"></Column>
		            <Column field="telefono" header="telefono"></Column>
		            <Column field="telefono2" header="telefono2"></Column>
		            <Column field="direccion" header="direccion"></Column>
		            <Column field="fechanacimiento" header="fechanacimiento"></Column>
		        </DataTable>
			</React.Fragment>
		)
	}
}

export default App;
