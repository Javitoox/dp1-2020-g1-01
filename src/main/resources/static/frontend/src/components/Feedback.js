import React, { Component } from 'react'
import MaterialComponent from './MaterialComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { ToggleButton } from 'primereact/togglebutton';
import { Rating } from 'primereact/rating';
import Auth from './Auth';

export class Feedback extends Component{

    constructor(props){
        super(props);
        this.state = {
            feedback: null,
            comprobation: true
        }
        this.materiales = new MaterialComponent();
        this.obtenerFeedback= this.obtenerFeedback.bind(this);
        this.botonDone= this.botonDone.bind(this);
        this.cambiarEstadoDeActividad= this.cambiarEstadoDeActividad.bind(this);
        this.valoracion = this.valoracion.bind(this);
    }

    componentDidMount(){
        this.obtenerFeedback();
    }

    async obtenerFeedback(){
        await this.materiales.obtenerFeedbackMaterial(this.props.urlBase, this.props.id).then(res => this.setState({feedback: res.data})).catch(error => this.setState({comprobation: false}));
    }

    botonDone(rowData){
        return(
            <div>
                <div className="card">
                    <ToggleButton checked={rowData.completado} onChange={()=> this.cambiarEstadoDeActividad(rowData)} onIcon="pi pi-check" offIcon="pi pi-times" />     
                </div>
            </div>
        );
    }

    async cambiarEstadoDeActividad(rowData) {
        await this.materiales.modificarDone(this.props.urlBase, rowData.id);
        this.obtenerFeedback();
    }

    valoracion(rowData) {
        return(
            <div>
                <Rating value={rowData.valoracion} readOnly stars={5} cancel={false} />
            </div>

        );
    }

    render(){
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        } else {
            return(
                <React.Fragment>
                    <DataTable value={this.state.feedback}>
                        <Column header="Student" field="alumnos.nombreCompletoUsuario"></Column>
                        <Column header="Comment" field="comentario"></Column>
                        <Column header="Rate (1-5)" body={this.valoracion}></Column>
                        <Column header="Done" body={this.botonDone}></Column>
                        <Column header="Date of delivery" field="diaEntrega"></Column>
                    </DataTable>
                </React.Fragment>
            );
        }
    }
}