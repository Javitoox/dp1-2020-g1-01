import React, { Component } from 'react'
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import Pdf from './all-pages';
import { Dialog } from 'primereact/dialog';
import MaterialComponent from './MaterialComponent';
import {FeedbackAlumno} from './FeedbackAlumno';

 export  class MaterialStudent extends Component{

    constructor(props){
        super(props);
        this.state={
            nickUsuario: this.props.nickUser,
            urlBase: this.props.urlBase, 
            materiales: null,
            visualizarPDF: null,
            mostrarFormFeedback: null
        }
        this.mostrarMaterial= this.mostrarMaterial.bind(this);
        this.obtenerMaterial= this.obtenerMaterial.bind(this);
        this.botonVerMaterial = this.botonVerMaterial.bind(this);
        this.botonDescargarMaterial = this.botonDescargarMaterial.bind(this);
        this.materiales= new MaterialComponent();
        this.botonMostrarFeedback= this.botonMostrarFeedback.bind(this);

    }

    componentDidMount() {
        this.obtenerMaterial();
    }

    async obtenerMaterial() {
        await this.materiales.obtenerMaterialStudent(this.state.urlBase,this.state.nickUsuario).then(res => this.setState({materiales: res.data}))
        console.log(this.state.materiales);
    }

    mostrarMaterial(){
        const paginatorLeft = <Button type="button" icon="pi pi-refresh" className="p-button-text" />;
        const paginatorRight = <Button type="button" icon="pi pi-cloud" className="p-button-text" />;

        return(
            <DataTable value={this.state.materiales} paginator
            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
            paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>

                <Column header="Material name" field="nombreMaterial"></Column>
                <Column header="Upload date" field="fechaSubida"></Column>
                <Column header="Preview" body={this.botonVerMaterial}></Column>
                <Column header="Download" body={this.botonDescargarMaterial}></Column>
                <Column header="Feedback" body={this.botonMostrarFeedback}></Column>
            </DataTable>
        );

    }

    botonMostrarFeedback(rowData){
        return(
        <React.Fragment>
                <Button icon="pi pi-star-o" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => 
                    this.setState({mostrarFormFeedback:
                        <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({mostrarFormFeedback: null})}> 
                            <center><FeedbackAlumno urlBase={this.state.urlBase} nickUsuario={this.state.nickUsuario} id = {rowData.id}/></center>
                        </Dialog>
                    })
                }
                />      
        </React.Fragment>
        );
    }

    botonVerMaterial(rowData){
        return(
            <React.Fragment>
                <Button icon="pi pi-eye" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => 
                    this.setState({visualizarPDF:
                        <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({visualizarPDF: null})}> 
                            <center><Pdf id = {rowData.id}/></center>
                        </Dialog>
                    })
                }
                />      
            </React.Fragment>
        );
    }

    botonDescargarMaterial(rowData){
        return(
            <a href={"/material/"+rowData.id+".pdf"} download={rowData.nombreMaterial}><i className="pi pi-download"></i></a>
        )
    }

    render(){
        return (
            <React.Fragment>
                {this.mostrarMaterial()}
                {this.state.visualizarPDF}
                {this.state.mostrarFormFeedback}
            </React.Fragment>
        );
    }
}