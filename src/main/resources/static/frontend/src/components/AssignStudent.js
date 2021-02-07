import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { connect } from 'react-redux';
import AlumnoComponent from './AlumnoComponent';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';
import axios from 'axios';
import AuthenticationService from '../service/AuthenticationService';
import { Dialog } from 'primereact/dialog';

class AssignStudent extends Component  {
   
    constructor(props){
    super(props);
        this.state = {   
        nickUsuario:this.props.astudent.nickUsuario,
        contraseya: this.props.astudent.contraseya,
        dniUsuario: this.props.astudent.dniUsuario,
        nombreCompletoUsuario: this.props.astudent.nombreCompletoUsuario,
        correoElectronicoUsuario: this.props.astudent.correoElectronicoUsuario,
        numTelefonoUsuario: this.props.astudent.numTelefonoUsuario,
        numTelefonoUsuario2: this.props.astudent.numTelefonoUsuario2,
        direccionUsuario: this.props.astudent.direccionUsuario,
        fechaNacimiento: this.props.astudent.fechaNacimiento,
        numTareasEntregadas: this.props.astudent.numTareasEntregadas,
        fechaMatriculacion: this.props.astudent.fechaMatriculacion,
        fechaSolicitud: this.props.astudent.fechaSolicitud,
        fechaBaja: this.props.astudent.fechaBaja,
        grupos: {
            nombreGrupo: '',
            cursos: {
            cursoDeIngles: ""
        }
        },
        tutores:{
            nickUsuario:''
        },

        listaGrupos:{
           nombreGrupo: ""
        } ,
        lisT:{

        },
        listaSinGrupos:{
               
        },  
        gruposs: {
            nombreGrupo: '',
            cursos: {
            cursoDeIngles: ""
        }
        },            
        cursoS:"",
        succes:null,
        comprobation: true,
        displayConfirmation: false,
        A1:{
            nombreGrupo: "",
        },
        A2:{
            nombreGrupo: "",
        },
        B1:{
            nombreGrupo: "",
        },
        B2:{
            nombreGrupo: "",
        }, 
        C1:{
            nombreGrupo: "",
        },
        C2:{
            nombreGrupo: "",
        },
        APRENDIZAJELIBRE:{
            nombreGrupo: "",
        }
        
        
    }
    this.nickUsuario = this.nickUsuario.bind(this);
    this.alumnos = new AlumnoComponent();
    this.grupos = new GrupoComponent();
    this.handleNG = this.handleNG.bind(this); 

}
    nickUsuario(event) {
        this.setState({ nickUsuario: event.target.value });
    }

    handleNG(event) {
        this.grupos.getCourseNamesByGroup(event.target.value).then(data => this.setState({ cursoS: data }));
        this.setState({ grupos: {
            nombreGrupo: event.target.value,
            
        }});
    }  
    
     
    componentDidMount() {
        this.alumnos.getAlumnosSinGrupo(this.props.urlBase).then(data =>  this.setState({ listaSinGrupos: data }) ).catch(error => this.setState({ comprobation: false }));
        this.grupos.getAllGroups().then(data =>  this.setState({ gruposs: data }) )
        if(!this.props.cgselected.includes(this.props.astudent.nickUsuario)){
            this.grupos.getAssignmentGroupsByStudent(this.state.nickUsuario).then(data => this.setState({ listaGrupos: data })); 
        }else{
            this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));   
        
        }
        this.grupos.getGroupNamesByCourse('A1').then(data =>this.setState({ A1: data }));
        this.grupos.getGroupNamesByCourse('A2').then(data =>this.setState({ A2: data }));
        this.grupos.getGroupNamesByCourse('B1').then(data =>this.setState({ B1: data }));
        this.grupos.getGroupNamesByCourse('B2').then(data =>this.setState({ B2: data }));
        this.grupos.getGroupNamesByCourse('C1').then(data =>this.setState({ C1: data }));
        this.grupos.getGroupNamesByCourse('C2').then(data =>this.setState({ C2: data }));
        this.grupos.getGroupNamesByCourse('APRENDIZAJELIBRE').then(data =>this.setState({ APRENDIZAJELIBRE: data }));


        
    }
        


    allGroupNames(){
        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [];
        while(i<t.length){  
            if(this.allGroupNamesA1().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(A1)")  

            }   else if(this.allGroupNamesA2().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(A2)")  

            }   else if(this.allGroupNamesB1().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(B1)")  

            }   else if(this.allGroupNamesB2().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(B2)")  

            }   else if(this.allGroupNamesC1().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(C1)")  

            }   else if(this.allGroupNamesC2().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(C2)")  

            }else{
                groupSelectItems.push(         
                    String(t[i]) +"(APRENDIZAJE LIBRE)")  

            }
              
        i+=1
        }
        return groupSelectItems
    }
    allGroupNames2(){
        this.alumnos.getAlumnosSinGrupo(this.props.urlBase).then(data =>  this.setState({ listaSinGrupos: data }) );
        var t=this.state.listaSinGrupos
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNames3(){
        var t=this.state.lisT
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesA1(){
        var t=this.state.A1
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesA2(){
        var t=this.state.A2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesB1(){
        var t=this.state.B1
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesB2(){
        var t=this.state.B2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesC1(){
        var t=this.state.C1
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesC2(){
        var t=this.state.C2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesF(){
        var t=this.state.APRENDIZAJELIBRE
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }

    assign  =  event => {
        event.preventDefault();
        var a = String(this.state.grupos.nombreGrupo)
        var aa=a.split("(")
        var t=aa[0]
        if(!this.allGroupNames3().includes(this.props.astudent.nickUsuario)){
            const alumno ={
                nickUsuario: this.state.nickUsuario,
                contraseya: this.state.contraseya,
                dniUsuario: this.state.dniUsuario,
                nombreCompletoUsuario: this.state.nombreCompletoUsuario,
                correoElectronicoUsuario: this.state.correoElectronicoUsuario,
                numTelefonoUsuario: this.state.numTelefonoUsuario,
                numTelefonoUsuario2: this.state.numTelefonoUsuario2,
                direccionUsuario: this.state.direccionUsuario,
                fechaNacimiento: this.state.fechaNacimiento,
                numTareasEntregadas:this.state.numTareasEntregadas,
                fechaMatriculacion: this.state.fechaMatriculacion,
                fechaSolicitud: this.props.astudent.fechaSolicitud,
                fechaBaja: this.props.astudent.fechaBaja,
                tutores:{
                    nickUsuario:this.props.astudent.tutores.nickUsuario
                },
                grupos: {
                    nombreGrupo: t,
                    cursos: {
                        cursoDeIngles: this.state.cursoS[0]
                }
                }
                
            }
            if(this.state.grupos.nombreGrupo===""){
                this.setState({displayConfirmation: true})    
            }else{
             axios.put(this.props.urlBase + "/alumnos/assignStudent", alumno , { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
             sessionStorage.getItem("password")) } }).then(res => {
                this.respuesta(res.status, res.data)
                })
            }
            
           
        }else if(this.allGroupNames3().includes(this.props.astudent.nickUsuario)){

            const alumno ={
                nickUsuario: this.state.nickUsuario,
                contraseya: this.state.contraseya,
                dniUsuario: this.state.dniUsuario,
                nombreCompletoUsuario: this.state.nombreCompletoUsuario,
                correoElectronicoUsuario: this.state.correoElectronicoUsuario,
                numTelefonoUsuario: this.state.numTelefonoUsuario,
                numTelefonoUsuario2: this.state.numTelefonoUsuario2,
                direccionUsuario: this.state.direccionUsuario,
                fechaNacimiento: this.state.fechaNacimiento,
                numTareasEntregadas:this.state.numTareasEntregadas,
                fechaMatriculacion: this.state.fechaMatriculacion,
                grupos: {
                    nombreGrupo: t,
                    cursos: {
                        cursoDeIngles: this.state.cursoS[0]
                }
                }
                
            }

            if(this.state.grupos.nombreGrupo===""){
                window.alert("You must select a group")
    
            }else{
             axios.put(this.props.urlBase + "/alumnos/assignStudent", alumno , { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
             sessionStorage.getItem("password")) } }).then(res => {
                this.respuesta(res.status, res.data)
                })
            }            
        }       
        
      }

    async respuesta(status, data){
        if(status===203 ){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status === 208){
            this.setState({
                succes: <div className="alert alert-danger" role="alert">There are already 12 alumns in this group</div>
            })
        }else{
            this.setState({
                username: this.state.username,
                password: this.state.password,
                card: this.state.card,
                name: this.state.name,
                email: this.state.email,
                telefono: this.state.telefono,
                telefono2: this.state.telefono2,
                address: this.state.address,
                birthdate: this.state.birthdate,
                succes: <div className="alert alert-success" role="alert">Modified Succesfully</div>
            })
        }
    } 
    renderFooter(){
        return (
            <div>
            </div>
        );
    }

    
   
    
    render() {
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.assign}  >
                        {this.state.succes}
                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You must select a group</span>
                         </div>
                         </Dialog>
                            <div className="t"><div><h5>Assign Student</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <InputText placeholder="Username" readOnly={true} name="alumno.nickUsuario" type="text" value={this.props.astudent.nickUsuario} onChange={this.nickUsuario}  />
                                 </div>
                                 </div>

                                 <div className="i">
                                <div className="p-inputgroup">
                                    <Dropdown placeholder="Select a group" name="grupo.nombreGrupo" value={this.state.grupos.nombreGrupo} options={this.allGroupNames()} onChange={this.handleNG} />
                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="Guardar" icon="pi pi-fw pi-check"/>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) { //metodo para poder pillar datos del store
    return {
        astudent: state.astudent,
        cgselected: state.cgselected //le pasamos a nuestra variable student la informacion del estudiante almacenada en el store
    }
}

export default connect(mapStateToProps)(AssignStudent); 