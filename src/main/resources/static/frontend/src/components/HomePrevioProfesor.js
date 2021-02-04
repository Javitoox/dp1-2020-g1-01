import React, { Component } from 'react'

import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import axios from 'axios';
import HomeReal from './HomeReal';
export default class HomePrevio extends Component {
  constructor() {
    super();
    this.state = {
      numeroSolicitudes: 0,
      comprobation: false
    }
    this.solicitudesComponent = new ExtraccionSolicitudes();
  }

   componentDidMount() {
    this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({numeroSolicitudes: data.length}));
    axios.get(this.props.urlBase + "/auth", {withCredentials: true}).then(res => {
      if(res.data==="profesor"){
          this.setState({comprobation: true})
      }
      })
     

  }




  render() {  


    console.log("hay en total : " + this.state.numeroSolicitudes)
    return (
      <div>
        <HomeReal solicitudes={this.state.numeroSolicitudes} comprobation= {this.state.comprobation}></HomeReal>
      </div>

    )
  }

}