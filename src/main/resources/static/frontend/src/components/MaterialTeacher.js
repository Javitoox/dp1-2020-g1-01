import React, { Component } from 'react'
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import Pdf from './all-pages';
import { Dialog } from 'primereact/dialog';
import {UploadMaterial} from './UploadMaterial'
import MaterialComponent from './MaterialComponent';
import {Feedback} from './Feedback';

 export  class MaterialTeacher extends Component{

    constructor(props){
        super(props);
        this.state={
            nickUsuario: this.props.nickUser,
            urlBase: this.props.urlBase, 
            materiales: null,
            visualizarPDF: null,
            formularioUpload: null,
            displayConfirmation: false,
            visualizarFeedback: null,
        }

        this.mostrarMaterial= this.mostrarMaterial.bind(this);
        this.obtenerMaterial= this.obtenerMaterial.bind(this);
        this.botonVerMaterial = this.botonVerMaterial.bind(this);
        this.botonDescargarMaterial = this.botonDescargarMaterial.bind(this);
        this.botonEliminarMaterial = this.botonEliminarMaterial.bind(this);
        this.mostrarBotonUpload = this.mostrarBotonUpload.bind(this);
        this.mostrarFormUpload = this.mostrarFormUpload.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.renderFooter = this.renderFooter.bind(this);
        this.materiales= new MaterialComponent();
        this.botonFeedback=  this.botonFeedback.bind(this);
        this.onHidenFormUpload = this.onHidenFormUpload.bind(this);
    }

    componentDidMount() {
        this.obtenerMaterial();
    }

    async obtenerMaterial() {
        await this.materiales.obtenerMaterialTeacher(this.state.urlBase,this.state.nickUsuario).then(res => this.setState({materiales: res.data}))
    }

    mostrarBotonUpload(){
        return(
        <div className="mt-3 mb-3">  
            <Button className="p-button-secondary" label="Upload new content" icon="pi pi-plus" onClick={()=>this.mostrarFormUpload()}/>
        </div>
        );
    }

    mostrarFormUpload(){
        this.setState({
            formularioUpload: 
                <Dialog visible={true} style={{ width: '50vw' }} onHide={() => this.onHidenFormUpload()}>
                    <UploadMaterial urlBase={this.state.urlBase} nickUsuario={this.state.nickUsuario}></UploadMaterial>
                </Dialog>
        })
    }

    onHidenFormUpload(){
        this.setState({formularioUpload: null})
        this.obtenerMaterial()
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
                <Column header="Type of Material" field="tipoMaterial.tipo"></Column>
                <Column header="Upload date" field="fechaSubida"></Column>
                <Column header="Preview" body={this.botonVerMaterial}></Column>
                <Column header="Download" body={this.botonDescargarMaterial}></Column>
                <Column header="Delete" body={this.botonEliminarMaterial}></Column>
                <Column header="Feedback" body={this.botonFeedback}></Column>

            </DataTable>
        );

    }

    botonFeedback(rowData){
        return(
            <React.Fragment>
                <Button icon="pi pi-star-o" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => 
                this.setState({visualizarFeedback:
                    <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({visualizarFeedback: null})}> 
                            <center><Feedback urlBase={this.state.urlBase} id = {rowData.id}/></center>
                    </Dialog>
                })}/>      
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
        );
    }

    botonEliminarMaterial(data){
        return(
            <div>
                <Button className="ml-2 p-button-secondary" icon="pi pi-fw pi-trash" onClick={() => this.setState({material: data}, () => this.setState({displayConfirmation: true}))}></Button> 
            </div>    
        );
    }

    async handleDelete(){
        await this.materiales.deleteMaterial(this.state.urlBase,this.state.material.id);
        this.setState({displayConfirmation: false})
        this.obtenerMaterial();
    }

    renderFooter(){
        return (
            <div>
                <Button label="No" icon="pi pi-times" onClick={() => this.setState({displayConfirmation: false})} className="p-button-text" />
                <Button label="Yes" icon="pi pi-check" onClick={() => this.handleDelete()} autoFocus />
            </div>
        );
    }

    render(){

        return (
            <React.Fragment>
                {this.mostrarBotonUpload()}
                {this.mostrarMaterial()}
                {this.state.visualizarPDF}
                {this.state.formularioUpload}
                {this.state.visualizarFeedback}

                <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                    <span>Are you sure you want to delete the selected material?</span>
                </div>
                </Dialog> 

            </React.Fragment>
        );
    }
}