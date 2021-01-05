import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { DataView } from "primereact/dataview";
import "primeicons/primeicons.css";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.css";
import "primeflex/primeflex.css";
import "../css/wallOfFame.css";
import axios from 'axios';
import Auth from './Auth';
import {CreatePremiado} from './CreatePremiado';
import {EditPremiado} from './EditPremiado';
import { Dialog } from 'primereact/dialog';



export class WallOfFameStudents extends Component{

    constructor(props){
        super(props)
        this.state={
            fecha:"",
            premiados:null,
            premiado: null,
            comprobation: false,
            formularioCrear: null,
            formularioEditar: null,
            displayConfirmation: false,
            renderFooter: null
        }
        this.mostrarWallSeleccionado=this.mostrarWallSeleccionado.bind(this);
        this.premiados = new AlumnoComponent();
        this.itemTemplate = this.itemTemplate.bind(this);
        this.obtenerUltimoWall = this.obtenerUltimoWall.bind(this);
        this.BotonAñadirPremiado= this.BotonAñadirPremiado.bind(this);
        this.SeleccionarFechaWall= this.SeleccionarFechaWall.bind(this);
        this.MostrarWall= this.MostrarWall.bind(this);
        this.BotonEditaroEliminarPremiado= this.BotonEditaroEliminarPremiado.bind(this);
        this.handleDelete= this.handleDelete.bind(this);  
        this.FormCreatePremiado = this.FormCreatePremiado.bind(this);
        this.FormEditPremiado = this.FormEditPremiado.bind(this);
        this.fecha = this.fecha.bind(this);
    }

    componentDidMount(){
        axios.get(this.props.urlBase + "/auth", {withCredentials: true}).then(res => {
        if(res.data==="profesor" || res.data==="alumno"){
            this.setState({comprobation: true})
        }
        })

        this.obtenerUltimoWall();
    }

    fecha(event){
        this.setState({fecha:event.target.value})      
    }

    async obtenerUltimoWall(){
        await this.premiados.getTheLastWeek(this.props.urlBase).then(data => this.setState({ fecha: data }))
        this.mostrarWallSeleccionado()
    }

    
    MostrarWall(){
        if(!this.state.addForm && !this.state.editForm){
            return(
                <div className="dataview-demo">
                    <DataView
                        value={this.state.premiados}
                        itemTemplate={this.itemTemplate}
                    />
                </div>
            );
        }
    }

    itemTemplate(premiados) {
        if (!premiados) {
          return;
        }
        return this.renderGridItem(premiados);
    }

    renderGridItem(data) {
        return (
        <React.Fragment>
            <div className="cuadro p-col-12 p-md-7">
                <div className="premiado-grid-item card">  
                <div className="premiado-grid-item-content">
                    {this.BotonEditaroEliminarPremiado(data)}
                    
                    <img 
                        src={"/photosWall/"+data.foto}
                        onError={(e) =>
                        (e.target.src =
                            "https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png")
                        }
                        alt={data.name}/>
                    
                    <div className="nombreCompleto">{data.alumnos.nombreCompletoUsuario}</div>
                    <div className="descripcion">{data.descripcion}</div>
                    </div>
                </div>
            </div>
        </React.Fragment>
        );    
    }

    
    SeleccionarFechaWall(){
        if(!this.state.addForm){
            return(
                <div className="mb-4 p-inputgroup">
                    <InputText className="mr-2" placeholder="Choose a date" name="fechaWall" type="week" value={this.state.fecha} onChange={this.fecha} />
                    <Button className="p-button-secondary" label="Search in this date" icon="pi pi-search" onClick={()=>this.mostrarWallSeleccionado()}/>
                </div>
            );
        }
    }

    mostrarWallSeleccionado(){
        this.premiados.getWallOfFameForStudents(this.props.urlBase, this.state.fecha).then(data => this.setState({ premiados: data }))
    }
    
    BotonAñadirPremiado(){
        if(this.props.userType==="profesor" && !this.state.addForm && !this.state.editForm){
            return(
                <div className="mt-3 mb-3">  
                    <Button className="p-button-secondary" label="Add a new awarded student" icon="pi pi-plus" onClick={()=>this.FormCreatePremiado()}/>
                </div>
            );
        } 
    }

    FormCreatePremiado(){
        this.setState({
            formularioCrear: 
            <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({formularioCrear: null})}>
                <CreatePremiado urlBase={this.props.urlBase} fecha={this.state.fecha}></CreatePremiado>
            </Dialog>
        })
    }

    BotonEditaroEliminarPremiado(data){
        if(this.props.userType==="profesor" && !this.state.addForm && !this.state.editForm){
            return(
                <div>
                    <Button className="p-button-secondary" icon="pi pi-fw pi-pencil" onClick={() => this.setState({premiado: data}, () => this.FormEditPremiado())} ></Button>  
                    <Button className="ml-2 p-button-secondary" icon="pi pi-fw pi-trash" onClick={() => this.setState({premiado: data}, () => this.setState({displayConfirmation: true}))}></Button> 
                </div>
            );
        }
    }

    FormEditPremiado(){
        this.setState({
            formularioEditar: 
            <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({formularioEditar: null})}>
                <EditPremiado urlBase={this.props.urlBase} premiado={this.state.premiado}></EditPremiado>
            </Dialog>
        })
    }

    async handleDelete(){
        await this.premiados.deletePremiado(this.props.urlBase, this.state.premiado.id)
        this.setState({displayConfirmation: false})
        this.obtenerUltimoWall();
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
        if (!this.state.comprobation) {
            return <Auth authority="teacher neither as student"></Auth>
        } else {
            return(
            <React.Fragment>
                {this.SeleccionarFechaWall()}                
                {this.BotonAñadirPremiado()} 
                {this.MostrarWall()}
                {this.state.formularioCrear}   
                {this.state.formularioEditar}  

                <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                <div className="confirmation-content">
                    <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                    <span>Are you sure you want to delete the awarded student?</span>
                </div>
                </Dialog>       

            </React.Fragment>
        );
        }
    }
}