import { Component } from 'react'
import axios from 'axios';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import {studentInfo} from '../actions/index';
class MetodosAlumno extends Component {

    getUserInfo(){
        return axios.get(this.propsurlBase+"/editStudentInfo", {withCredentials: true}, this.props.nickUser).then(res=>{
            this.props.studentInfo(res.data)
            });
    }
}
function  matchDispatchToProps(dispatch) {
    return bindActionCreators({
        studentInfo: studentInfo}, dispatch) //se mapea el action llamado selectStudent y se transforma en funcion con este metodo, sirve para pasarle la info que queramos al action, este se la pasa al reducer y de alli al store 
}
export default connect(null , matchDispatchToProps)(MetodosAlumno) //importante poner primero el null si no hay mapStateToProps en el componente chicxs