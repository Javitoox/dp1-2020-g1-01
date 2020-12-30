import React, { Component } from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import { Solicitudes } from './components/Solicitudes'
import './index.css'
import Login from './components/Login'
import { SolicitudesProfesor } from './components/SolicitudesProfesor'
import { AlumnosPorTutor } from './components/AlumnosPorTutor'
import  Alumnos  from './components/Alumnos'
import {Home} from './components/Home'
import MenubarResponsive from './components/MenubarResponsive'
import {getUserType} from './components/storage'
import {getUserName} from './components/storage'
//import {getAlumnoInfo} from './components/UserData'
import { WallOfFameStudents } from './components/WallOfFameStudents'
import EditStudent from './components/EditStudent'
import AssignStudent from './components/AssignStudent'
import {CreateGroup} from './components/CreateGroup'
import {DeleteGroup} from './components/DeleteGroup'
import EditPersonalInfo from './components/EditPersonalInfo'
import {CalendarioProfesor} from './components/CalendarioProfesor'


class App extends Component {

	changeType = this.changeType.bind(this)

	state = {
		urlBase: "http://localhost:8081",
		username: "",
		userType: ""
	}

	changeType(type){
		this.setState({userType: type})
	}

	componentDidMount(){
		this.setState({userType: getUserType()})
	}

	calculateUserName() {
		return getUserName()
	}
	// getUsernameInfo() {
	// 	return getAlumnoInfo(this.state.urlBase, getUserName())
	// }
	render() {
		return (
			<Router>
				<MenubarResponsive tipoDeUsuario={this.state.userType} onChange={this.changeType} urlBase={this.state.urlBase}></MenubarResponsive>
				<Route path="/home" render={() =>
					<Home></Home>
				} />
				<Route path="/requests" render={() =>
					<Solicitudes urlBase={this.state.urlBase}></Solicitudes>
				} />
				<Route path="/students/EditPersonalInfo" render={() =>
					<EditPersonalInfo urlBase={this.state.urlBase} nickUser={this.calculateUserName()} ></EditPersonalInfo>
				} />
				<Route path="/login" render={() =>
					<Login urlBase={this.state.urlBase} onChange={this.changeType}></Login>
				} />
				<Route path="/editStudent" render={() =>
					<EditStudent urlBase={this.state.urlBase}/>
				} />
				<Route path="/pendingRequests" render={() =>
					<SolicitudesProfesor urlBase={this.state.urlBase}></SolicitudesProfesor>
				} />
				<Route path="/allStudents" render={() =>
					<Alumnos urlBase={this.state.urlBase}></Alumnos>
				} />
				<Route path="/assignStudent" render={() =>
					<AssignStudent urlBase={this.state.urlBase} />
				} /> 
				<Route path="/createGroup" render={() =>
					<CreateGroup urlBase={this.state.urlBase}/>
				} />
				<Route path="/deleteGroup" render={() =>
					<DeleteGroup urlBase={this.state.urlBase}/>
				} /> 
				<Route path="/myStudents" render={() =>
					<AlumnosPorTutor urlBase={this.state.urlBase} nickUser={this.calculateUserName()}></AlumnosPorTutor>
				} />
				<Route path="/walloffame" render={() =>
					<WallOfFameStudents urlBase={this.state.urlBase} userType={this.state.userType}> </WallOfFameStudents>
                } /> 
				<Route path="/calendarTeacher" render={() =>
					<CalendarioProfesor urlBase={this.state.urlBase}></CalendarioProfesor>
                } />
			</Router>
		)
	}
}

export default App

