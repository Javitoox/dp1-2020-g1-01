import React, { Component } from 'react'

import PagoComponent from './PagoComponent';
import NotificacionesStudents from './NotificacionesStudents';

export default class NotificationStudent extends Component {
  constructor() {
    super();
    this.state = {
      impagos:0,
      comprobation: false
    }
    this.pagos = new PagoComponent();
  }

   componentDidMount() {
    this.pagos.getNotPaidByStudent(this.props.nickUser).then(data => this.setState({impagos:data.length})); 
   

  }

  render() {   
    return (
      <div>
        <NotificacionesStudents payment={this.state.impagos}></NotificacionesStudents>
      </div>

    )
  
}
}