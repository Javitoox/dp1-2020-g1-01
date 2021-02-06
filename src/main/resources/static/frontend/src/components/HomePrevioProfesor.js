import React, { Component } from 'react'
import Auth from './Auth';
import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import HomeReal from './HomeReal';
export default class HomePrevio extends Component {
  constructor() {
    super();
    this.state = {
      numeroSolicitudes: 0,
      comprobation: true
    }
    this.solicitudesComponent = new ExtraccionSolicitudes();
  }

   componentDidMount() {
    this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({numeroSolicitudes: data.length}))
    .catch(error => this.setState({comprobation: false}));
  }

  render() {  
    if (!this.state.comprobation) {
      return <Auth authority="profesor"></Auth>
  } else {
    return (
      <div>
        <HomeReal solicitudes={this.state.numeroSolicitudes}></HomeReal>
      </div>

    )
  }
  }
}