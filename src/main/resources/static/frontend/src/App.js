import React, { Component } from 'react'
import { BrowserRouter as Router, Redirect, Route } from 'react-router-dom'
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
import { WallOfFameStudents } from './components/WallOfFameStudents'
import EditStudent from './components/EditStudent'
import AssignStudent from './components/AssignStudent'
import AssignTeacher from './components/AssginTeacher'
import EditPersonalInfo from './components/EditPersonalInfo'
import {CalendarioProfesor} from './components/CalendarioProfesor'
import Pagos from './components/Pagos'
import { RealizarPago } from './components/RealizarPago'
import TeacherGroups from './components/TeacherGroups'
import { StudentPayments } from './components/StudentPayments'
import { CalendarioAlumno } from './components/CalendarioAlumno'
import {MaterialTeacher} from './components/MaterialTeacher'
import {MaterialStudent} from './components/MaterialStudent'
import NotificationStudent from './components/NotificationStudent'
import { AlumnosStudent } from './components/AlumnosStudent'
import HomePrevioProfesor from './components/HomePrevioProfesor'

class App extends Component {

	changeType = this.changeType.bind(this)

	state = {
		urlBase: "http://localhost:8081",
		username: "",
		userType: "",
		requests: 0,
		eventos: 0,
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
				<Route path="/notificationsStudent" render={() =>
					<NotificationStudent urlBase={this.state.urlBase} nickUser={this.calculateUserName()} ></NotificationStudent>
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
				<Route path="/students" render={() =>
					<AlumnosStudent urlBase={this.state.urlBase} nickUser={this.calculateUserName()}></AlumnosStudent>
				} />
				<Route path="/assignStudent" render={() =>
					<AssignStudent urlBase={this.state.urlBase} />
				} /> 
				<Route path="/assignTeacher" render={() =>
					<AssignTeacher urlBase={this.state.urlBase} nickUser={this.calculateUserName()} />
				} />
				<Route path="/teacherGroups" render={() =>
					<TeacherGroups urlBase={this.state.urlBase} nickUser={this.calculateUserName()} />
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
				<Route path="/calendarStudent" render={() =>
					<CalendarioAlumno urlBase={this.state.urlBase} nickUser={this.calculateUserName()} ></CalendarioAlumno>
                } />
				<Route path="/payments" render={() =>
					<Pagos urlBase={this.state.urlBase}> </Pagos>
                } />
				<Route path="/createPayment" render={() =>
					<RealizarPago nickUser={this.calculateUserName()}/>
				} />
				<Route path="/studentPayments" render={() =>
					<StudentPayments nickUser={this.calculateUserName()}/>
				} />
				<Route path="/materialsTeacher" render={() =>
					<MaterialTeacher urlBase={this.state.urlBase} nickUser={this.calculateUserName()}/>
				} />
				<Route path="/materialsStudent" render={() =>
					<MaterialStudent urlBase={this.state.urlBase} nickUser={this.calculateUserName()}/>
				} />
				<Route path="/" exact render={() =>
					<HomePrevioProfesor urlBase={this.state.urlBase} nickUser={this.calculateUserName()}/>
				} />
			</Router>
			
		)
	}
}

export default App

