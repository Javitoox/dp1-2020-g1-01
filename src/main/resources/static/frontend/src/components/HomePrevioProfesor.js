import React, { Component } from 'react'

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
    return (
      <div>
        <HomeReal solicitudes={this.state.numeroSolicitudes} comprobation= {this.state.comprobation}></HomeReal>
      </div>

    )
  }

}