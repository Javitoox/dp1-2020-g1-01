import axios from 'axios';
import { Component } from 'react';

export default class AssignmentComponent extends Component {


  getListOfAssignment(baseUrl, user) {
    return axios.get(baseUrl + "/asignaciones/get/" + user).then(res => res.data);
  }

  deleteAsignacion(baseUrl, nickUser, nombreGrupo) {
    return axios.delete(baseUrl + "/asignaciones/delete/" + nickUser + "/" + nombreGrupo).then(res => res.data);
  }

  getTeacherByGroup(baseUrl, grupo) {
    return axios.get(baseUrl + "/asignaciones/getNick/" + grupo).then(res => res.data);
  }

}
