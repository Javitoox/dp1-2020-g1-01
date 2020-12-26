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

export class WallOfFameStudents extends Component{
    fecha = this.fecha.bind(this);
    nickusuario= this.nickusuario.bind(this);
    description= this.description.bind(this);
    photo= this.photo.bind(this);

    constructor(props){
        super(props)
        this.state={
            fecha:"",
            premiados:null,
            nickusuario : "",
            description: "",
            photo: null,
            addForm:false,
            editForm: false,
            premiado: null,
        }
        this.mostrarWallSeleccionado=this.mostrarWallSeleccionado.bind(this);
        this.premiados = new AlumnoComponent();
        this.itemTemplate = this.itemTemplate.bind(this);
        this.obtenerUltimoWall = this.obtenerUltimoWall.bind(this);
        this.BotonAñadirPremiado= this.BotonAñadirPremiado.bind(this);
        this.SeleccionarFechaWall= this.SeleccionarFechaWall.bind(this);
        this.MostrarWall= this.MostrarWall.bind(this);
        this.BotonEditarPremiado= this.BotonEditarPremiado.bind(this);
        this.mostrarFormularioEdit= this.mostrarFormularioEdit.bind(this);
        this.handleSubmitEdit= this.handleSubmitEdit.bind(this);    
    }

    componentDidMount(){
        this.obtenerUltimoWall();
    }
  
    async obtenerUltimoWall(){
        await this.premiados.getTheLastWeek(this.props.urlBase).then(data => this.setState({ fecha: data }))
        console.log("fecha:" + this.state.fecha)
        this.mostrarWallSeleccionado()
    }

    
    renderGridItem(data) {
        return (
        <React.Fragment>
            <div className="cuadro p-col-12 p-md-7">
                <div className="premiado-grid-item card">  
                <div className="premiado-grid-item-content">
                    {this.BotonEditarPremiado(data)}
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

    itemTemplate(premiados) {
        if (!premiados) {
          return;
        }
        return this.renderGridItem(premiados);
    }


    mostrarWallSeleccionado(){
        console.log(this.state.fecha)
        this.premiados.getWallOfFameForStudents(this.props.urlBase, this.state.fecha).then(data => this.setState({ premiados: data }))
        //console.log(this.state.premiados);
    }

    fecha(event){
        console.log("Hola soy fecha(event) :)")
        this.setState({fecha:event.target.value})      
    }

    nickusuario(event){
        this.setState({nickusuario : event.target.value})
    }

    description(event){
        this.setState({description : event.target.value})
    }

    photo(event){
        this.setState({photo : event.target.files[0]})
    }


    async handleSubmit(){
        const formData = new FormData();
        formData.append('photo', this.state.photo) ;
        formData.append('nickUsuario', this.state.nickusuario) ;
        formData.append('description', this.state.description) ;
        await this.premiados.postNewPremiado(this.props.urlBase, this.state.fecha, formData).then(() => this.setState({ addForm: false }));
        console.log("Añadiendo en la fecha... "+this.state.fecha)
        this.mostrarWallSeleccionado();
    }
    
    SeleccionarFechaWall(){
        if(!this.state.addForm){
            return(
                <div className="p-inputgroup">
                    <InputText className="mr-2" placeholder="Choose a date" name="fechaWall" type="week" value={this.state.fecha} onChange={this.fecha} />
                    <Button className="p-button-secondary" label="Search in this date" icon="pi pi-search" onClick={()=>this.mostrarWallSeleccionado()}/>
                </div>
            );
        }
    }
    
    BotonAñadirPremiado(){
        if(this.props.userType==="profesor" && !this.state.addForm && !this.state.editForm){
            return(
                <div className="mt-3 mb-3">  
                    <Button className="p-button-secondary" label="Add a new awarded student" icon="pi pi-plus" onClick={()=>this.setState({addForm: true})}/>
                </div>
            );
        }

    }

    BotonEditarPremiado(data){
        if(this.props.userType==="profesor" && !this.state.addForm && !this.state.editForm){
            return(
                <div>
                    <Button className="p-button-secondary" icon="pi pi-fw pi-pencil" onClick={() => this.setState({premiado: data}, () => this.setState({editForm:true}))} ></Button>  
                    <Button className="ml-2 p-button-secondary" icon="pi pi-fw pi-trash"></Button> 
                </div>
            );
        }
    }

    mostrarFormularioEdit(){
        if(this.state.editForm){
            return(
                <div className="c">
                    <div className="login request">
                        <div className="t">
                            <div><h5>You're editing: {this.state.premiado.alumnos.nickUsuario} </h5></div>
                        </div>

                        <div className="i">
                            <div className="p-inputgroup">
                            <span className="p-inputgroup-addon">
                                <i className="pi pi-align-center"></i>  
                            </span>
                                <InputText type= "textarea" placeholder={this.state.premiado.descripcion} name="description" onChange={this.description} />
                            </div>
                        </div>

                        <div className="i">
                            <div className="p-inputgroup">
                                <InputText type= "file" name="photo" onChange={this.photo}/>
                            </div>
                        </div>

                        <div className="b">
                            <div className="i">
                                <Button className="p-button-secondary" label="Add the student" icon="pi pi-fw pi-upload" onClick={() => this.handleSubmitEdit()}/>
                            </div>
                        </div>
                        
                    </div>
                </div>


            );
        }
    }


    async handleSubmitEdit(){
        const formData = new FormData();
        formData.append('photo', this.state.photo) ;
        formData.append('description', this.state.description) ;
        formData.append('id', this.state.premiado.id) ;
        formData.append('nickUsuario', this.state.premiado.alumnos.nickUsuario) ;
        await this.premiados.editPremiado(this.props.urlBase, formData).then(() => this.setState({editForm: false}))
        this.mostrarWallSeleccionado();
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

    mostrarFormulario(){
        if(this.state.addForm){
            return( 
                <div className="c">
                    <div className="login request">
                       
                        <div className="t">
                            <div><h5>Add a new awarded student</h5></div>
                        </div>
                        <div className="i">
                            <div className="p-inputgroup">
                            <span className="p-inputgroup-addon">
                                <i className="pi pi-user"></i>
                            </span>
                                <InputText type= "text" placeholder="Username" name="nickusuario" onChange={this.nickusuario} />
                            </div>
                        </div>

                        <div className="i">
                            <div className="p-inputgroup">
                            <span className="p-inputgroup-addon">
                                <i className="pi pi-align-center"></i>  
                            </span>
                                <InputText type= "textarea" placeholder="description" name="description" onChange={this.description} />
                            </div>
                        </div>

                        <div className="i">
                            <div className="p-inputgroup">
                                <InputText type= "file" name="photo" onChange={this.photo}/>
                            </div>
                        </div>

                        <div className="b">
                            <div className="i">
                                <Button className="p-button-secondary" label="Add the student" icon="pi pi-fw pi-upload" onClick={() => this.handleSubmit()}/>
                            </div>
                        </div>
                        
                    </div>
                </div>
                );
            }
    }

    render(){
        return(
            <React.Fragment>
                {this.SeleccionarFechaWall()}                
                {this.BotonAñadirPremiado()} 
                {this.MostrarWall()}
                {this.mostrarFormulario()}     
                {this.mostrarFormularioEdit()}          
            </React.Fragment>
        );
    }
}