import axios from 'axios';
import { Component } from 'react';

export default class AssignmentComponent extends Component {


  getListOfAssignment(baseUrl, user) {
    return axios.get(baseUrl + "/asignaciones/get/" + user).then(res => res.data);
  }

  deleteAsignacion(baseUrl, nickUser, nombreGrupo) {
    return axios.delete(baseUrl + "/asignaciones/delete/" + nickUser + "/" + nombreGrupo).then(res => res.data);
  }

  getTeacherByGroup(baseUrl, user) {
    return axios.get(baseUrl + "/asignaciones/" + user, {
      headers: {
        authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"),
          sessionStorage.getItem("password"))
      }
    }).then(res => res.data);
  }

}
