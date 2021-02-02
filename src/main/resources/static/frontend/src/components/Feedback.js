import React, { Component } from 'react'
import MaterialComponent from './MaterialComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { ToggleButton } from 'primereact/togglebutton';

export class Feedback extends Component{

    constructor(props){
        super(props);
        this.state = {
            feedback: null,
        }
        this.materiales = new MaterialComponent();
        this.obtenerFeedback= this.obtenerFeedback.bind(this);
        this.botonDone= this.botonDone.bind(this);
        this.cambiarEstadoDeActividad= this.cambiarEstadoDeActividad.bind(this);
    }

    componentDidMount(){
        this.obtenerFeedback();
    }

    async obtenerFeedback(){
        await this.materiales.obtenerFeedbackMaterial(this.props.urlBase, this.props.id).then(res => this.setState({feedback: res.data}));
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

    render(){
        console.log(this.state.feedback);
        return(
            <React.Fragment>
                <DataTable value={this.state.feedback}>
                    <Column header="Student" field="alumnos.nombreCompletoUsuario"></Column>
                    <Column header="Comment" field="comentario"></Column>
                    <Column header="Rate (1-5)" field="valoracion"></Column>
                    <Column header="Done" body={this.botonDone}></Column>
                    <Column header="Date of delivery" field="diaEntrega"></Column>
                </DataTable>
            </React.Fragment>
        );
    }
}