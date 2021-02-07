import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Redirect } from 'react-router-dom';
import { ListBox } from 'primereact/listbox';
import GrupoComponent from './GrupoComponent';
import { selectStudent } from '../actions/index';
import { selectAssignedStudent } from '../actions/index';
import {selectCreatedGroup} from '../actions/index'
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { Dialog } from 'primereact/dialog';
import Auth from './Auth';
import CreateGroup from './CreateGroup';
import DeleteGroup from './DeleteGroup'
import AssignStudent from './AssignStudent';
import axios from 'axios';



class Alumnos extends Component {

    constructor(props) {
        super(props);
        this.state = {
            curso: "allCourses",
            grupo: "allGroups",
            redirect: false,
            nickUsuario: "",
            contraseya: "",
            dniUsuario: "",
            nombreCompletoUsuario: "",
            correoElectronicoUsuario: "",
            numTelefonoUsuario: "",
            direccionUsuario: "",
            fechaNacimiento: "",
            numTareasEntregadas: "",
            fechaMatriculacion: "",
            groupSelectItems: "",
            rowDataInfo: null,
            comprobation: true,
            formularioCrearGrupo: null,
            formularioDeleteGrupo: null,
            formularioAssginStudent: null,
            displayConfirmation: false,
            listaGrupos: {
                nombreGrupo: "",
            },
            lista: {
                nombreGrupo: "",
            },
            listaEliminables: {

            },
            listaSinGrupos: {

            },
            listaSinTutor: {

            },
            listaAllGrupos: {

            },
            listaGruposE:{
                nombreGrupo: ""
            },
            reducerC: this.props.cgselected,
            cursoS:"",
            grupoSS:"",
            grupoS:{
                nombreGrupo: "",
                cursos:{
                    cursoDeIngles:""
                }
            },
            listaGruposs:{
                nombreGrupo: ""
            },
            nombreGrupoError:null,
            cursoError:null,
            succes:null,
            exist:null,
            succes2:null,
            exist2:null,
            opcion:""

        }
        this.alumnos = new AlumnoComponent();
        this.boton = this.boton.bind(this);
        this.grupos = new GrupoComponent();
        this.botonAssign = this.botonAssign.bind(this);
        this.formCreateGrupo = this.formCreateGrupo.bind(this);
        this.formDeleteGrupo = this.formDeleteGrupo.bind(this);
        this.botonGrupos = this.botonGrupos.bind(this);
        this.allGroupNames = this.allGroupNames.bind(this);
        this.botonDelete = this.botonDelete.bind(this);
        this.botonInfo = this.botonInfo.bind(this);
        this.mostrarInfoStudent = this.mostrarInfoStudent.bind(this);
        this.mostrarInfo = this.mostrarInfo.bind(this);
        this.mostrarDatosTutor = this.mostrarDatosTutor.bind(this);
        this.mostrarTabla = this.mostrarTabla.bind(this);
        this.botonDelete = this.botonDelete.bind(this);
        this.formAssignStudent = this.formAssignStudent.bind(this);
        this.handleCI = this.handleCI.bind(this);
        this.handleNG = this.handleNG.bind(this);
        this.assignOption = this.assignOption.bind(this);
        this.form=this.form.bind(this);
        this.handleNG2=this.handleNG2.bind(this);
        this.assignGroup=this.assignGroup.bind(this);
    }

    componentDidMount() {
        this.mostrarTabla();
        this.alumnos.getAlumnosEliminiables(this.props.urlBase).then(data => this.setState({ listaEliminables: data }));
        this.alumnos.getAlumnosSinGrupo(this.props.urlBase).then(data => this.props.selectCreatedGroup(data));
        this.grupos.getEmptyGroupNames().then(data => this.setState({ listaGruposE: data }));

    }
    handleCI(event) {
        this.setState({
            grupoS:{
                nombreGrupo:this.state.grupoS.nombreGrupo,
                cursos:{
                    cursoDeIngles:event.target.value
                } 
            }
        });
    }
    handleNG(event) {
        this.setState({grupoS:{
            
           nombreGrupo:event.target.value,
           cursos:{
            cursoDeIngles:this.state.grupoS.cursos.cursoDeIngles
        }
        }});
    }
    handleNG2(event) {
        this.setState({grupoS:{            
           nombreGrupo:event.target.value
        }});
    }
    save = event => {
        event.preventDefault();
        this.setState({
            nombreGrupoError:null,
            cursoError:null
        });
        const grupo = {
            nombreGrupo:this.state.grupoS.nombreGrupo,
            cursos:{
                cursoDeIngles:this.state.grupoS.cursos.cursoDeIngles
            } 
        }
        if(this.state.grupoS.cursos.cursoDeIngles===""){
            this.setState({displayConfirmation: true})    
        }else{
            axios.post("http://localhost:8081/grupos/new", grupo).then(res => {
                this.respuesta2(res.status, res.data, grupo);
            })
           

        }
    }

    delete = event => {
        event.preventDefault();
            if(this.state.grupoS.nombreGrupo===""){
                this.setState({displayConfirmation: true})    

            }else{
                axios.delete("http://localhost:8081/grupos/delete/"+this.state.grupoS.nombreGrupo).then(res => {
                    this.respuesta3(res.status, res.data, this.state.grupoS.nombreGrupo);        })
    

            }       
    }

    allGroupNamesE(){
        var t=this.state.listaGruposE
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return groupSelectItems
    }

    allGroupNames(){
        var t=this.state.listaGrupos
        var ii=0
        var groupSelectItems = [
            { label: 'All groups' , value: 'allGroups' },    
        ];
        while(ii<t.length){  
            groupSelectItems.push(         
                    { label: String(t[ii]) , value: String(t[ii]) })        
                ii+=1
                }
            return groupSelectItems   
    }

    allGroupNames2(){
        var t=this.state.listaGrupos2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }

    cambio(c){
        this.setState({opcion:c})
    }

    form(){
               
        this.grupos.getEmptyGroupNames().then(data => this.setState({ listaGruposE: data }));

        var l = this.state.listaGruposE
        if(Object.keys(l).length===0){
            return <div className="t"><div><h5>There are no groups to delete</h5></div></div>
        }else{
            return <div>
                                <div className="t"><div><h5>Delete Group</h5></div></div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <InputText value={this.state.grupoS.nombreGrupo} placeholder="Group's name" name="nombreGrupo" onChange={this.handleNG} hidden={true}/>
                                <Dropdown name="nombreGrupo" placeholder="Select group" value={this.state.grupoS.nombreGrupo} options={this.allGroupNamesE()} onChange={this.handleNG2} />

                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                <Button className="p-button-secondary" label="Delete" icon="pi pi-fw pi-check"/>

                                </div>
                                </div>
                                
                    </div>


        }
    }

    async respuesta2(status, data,grupo){
        console.log(status)
        this.setState({
            exist2: "",
            succes2: ""});
        if(status===203){
            this.error2(data.field, data.defaultMessage)
        }else if(status===201){
            this.setState({
                grupoS:{
                    nombreGrupo:"",
                    cursos:{
                        cursoDeIngles:""
                    }
                },
                succes2: <div className="alert alert-success" role="alert">Successful creation</div>
            })
            if(this.state.curso==='allCourses'){
            this.showSelectCourse('allCourses')
            await this.showSelectCourse(String(grupo.cursos.cursoDeIngles))
            await this.showSelectCourse('allCourses')
            this.cambio("")

            }else{
            this.showSelectCourse('allCourses')
            await this.showSelectCourse(String(grupo.cursos.cursoDeIngles))
            this.cambio("")

            }
            

        }else if(status===226){
            
            this.setState({
                exist2: <div className="alert alert-danger" role="alert">The group already exists</div>})
        }else if(status===204){
            this.setState({exist2: <div className="alert alert-danger" role="alert">You must choose a course</div>})
        }else{
            this.setState({exist2: <div className="alert alert-danger" role="alert">{data}</div>})
        }
       
    }
    error2(campo, mensaje){
        if(String(campo) === "nombreGrupo"){
            this.setState({ nombreGrupoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "grupo.cursoDeIngles"){
            this.setState({ cursoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }

    async respuesta3(status, data,grupo){
        this.setState({
            exist: "",
            succes: ""})
        if(status===400){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status===200){
            this.grupos.getEmptyGroupNames().then(data => this.setState({ listaGrupos: data }));
            this.setState({               
                succes: <div className="alert alert-success" role="alert">Successful delete</div>
            })
            if(this.state.curso==='allCourses'){
                this.showSelectCourse('allCourses')
                await this.showSelectCourse('A1')
                await this.showSelectCourse('allCourses')
                this.cambio("")
    
                }else{
                this.showSelectCourse('allCourses')
                await this.showSelectCourse('A1')
                await this.showSelectCourse('allCourses')
                this.cambio("")
    
                }


        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
        this.setState({
            grupoS:{
                nombreGrupo: "",
                cursos:{
                    cursoDeIngles:""
                }
            }})
    }

    boton(rowData) {

        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => this.edicion(rowData)} />
            </React.Fragment>
        );
    }

    async formCreateGrupo() {
        this.setState({
            formularioCrearGrupo:
                <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({ formularioCrearGrupo: null })}>
                    <CreateGroup urlBase={this.props.urlBase} grupo={this.state.listaGrupos.nombreGrupo}></CreateGroup>
                </Dialog>
        });
        this.allGroupNames();

    }

    async formDeleteGrupo() {
        this.setState({
            formularioDeleteGrupo:
                <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({ formularioDeleteGrupo: null })}>
                    <DeleteGroup urlBase={this.props.urlBase}></DeleteGroup>
                </Dialog>
        });
        this.mostrarTabla();
    }


    formAssignStudent(data) {
        this.props.selectAssignedStudent(data)
        this.props.selectCreatedGroup(this.state.listaSinGrupos)
        this.setState({
            formularioAssginStudent:
                <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({ formularioAssginStudent: null })}>
                    <AssignStudent urlBase={this.props.urlBase} list={this.state.listaSinGrupos} listT={this.state.listaSinTutor} listaAll={this.allGroupNames()} tut={''}></AssignStudent>
                </Dialog>
        });
        this.mostrarTabla();
    }

    botonGrupos() {
        this.setState({
            redirect: "/teacherGroups",

        });
    }

    edicion(data) {
        this.props.selectStudent(data) 

        this.setState({
            redirect: "/editStudent",

        });
    }

    botonAssign(rowData) {
        return (    
            <React.Fragment>
                <Button icon="pi pi-plus-circle" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => this.assignGroup(rowData)} />
            </React.Fragment>
        );
    }

    assignGroup(data) {
    
        this.props.selectAssignedStudent(data)
            this.setState({ 
                redirect: "/assignStudent",
                
        });
    }
    async showSelectCourse(course) {
        if (course !== null) {

            this.setState({ curso: course });
            if (course === "allCourses") {
                this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
                this.setState({ listaGrupos: "" });

            } else {
                this.setState({curso:"allCourses"})
                await this.setState({curso:course})
                this.alumnos.getStudentsByCourse(this.props.urlBase, course).then(data => this.setState({ alumnos: data }));
                await this.grupos.getGroupNamesByCourse(course).then(data => this.setState({ listaGrupos: data }));
            }
        }
    }
    async showSelectGroup(group) {
        if (group !== null) {
            this.setState({ grupo: group });
            if (group === "allGroups") {
                this.setState({ listaGrupos: "" });
                this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
            } else {
                this.setState({grupo:"allGroups"})
                await this.setState({grupo:group})
                this.alumnos.getStudentsByNameOfGroup(this.props.urlBase, group).then(data => this.setState({ alumnos: data }));
            }
        }
    }   

    mostrarTabla() {
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data })).catch(error => this.setState({ comprobation: false }));
    }

    botonDelete(rowData) {
        var s= this.state.listaEliminables
        var list=[];
        var i=0
        while(i<s.length){
            list.push(s[i]);
            i+=1
        }
        if(list.includes(String(rowData.nickUsuario))){            
            return (    
                <React.Fragment>
                                      
                    <Button icon="pi pi-trash" className="p-button-rounded p-button-secondary p-mr-2"  onClick={() => this.setState({seleccionado: rowData}, () => this.setState({displayConfirmation: true}))}/>
                </React.Fragment>
            );
        }
     }  

    async deleteAlumno(data){
        this.mostrarTabla()
        await this.alumnos.deleteAlumno(this.props.urlBase, data.nickUsuario);
        this.mostrarTabla()
        this.setState({displayConfirmation: false})
      
    }

    botonInfo(rowData) {
        return (
            <React.Fragment>
                <Button icon="pi pi-external-link" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => this.mostrarInfoStudent(rowData)} />
            </React.Fragment>
        );
    }

    mostrarInfoStudent(rowData) {
        this.setState({ rowDataInfo: rowData })
    }

    mostrarInfo() {
        if (this.state.rowDataInfo != null) {
            return (
                <Dialog header="Student' information" visible={true} style={{ width: '30vw' }} onHide={() => this.setState({ rowDataInfo: null })}>
                    <h4>Student data:</h4>
                    <p><b>Nick:</b> {this.state.rowDataInfo.nickUsuario}</p>
                    <p><b>DNI:</b> {this.state.rowDataInfo.dniUsuario}</p>
                    <p><b>Full name:</b> {this.state.rowDataInfo.nombreCompletoUsuario}</p>
                    <p><b>Email:</b> {this.state.rowDataInfo.correoElectronicoUsuario}</p>
                    <p><b>Birthdate:</b> {this.state.rowDataInfo.fechaNacimiento}</p>
                    <p><b>Address:</b> {this.state.rowDataInfo.direccionUsuario}</p>
                    <p><b>Phone number:</b> {this.state.rowDataInfo.numTelefonoUsuario}</p>

                    {this.mostrarDatosTutor(this.state.rowDataInfo)}
                </Dialog>

            );
        }
    }

    mostrarDatosTutor(rowData) {
        if (rowData.tutores != null) {
            return (
                <React.Fragment>
                    <h4>Tutor data:</h4>
                    <p><b>Nick:</b> {rowData.tutores.nickUsuario}</p>
                    <p><b>DNI:</b> {rowData.tutores.dniUsuario}</p>
                    <p><b>Full name:</b> {rowData.tutores.nombreCompletoUsuario}</p>
                    <p><b>Email:</b> {rowData.tutores.correoElectronicoUsuario}</p>
                    <p><b>Birthdate:</b> {rowData.tutores.fechaNacimiento}</p>
                    <p><b>Address:</b> {rowData.tutores.direccionUsuario}</p>
                    <p><b>Phone number:</b> {rowData.tutores.numTelefonoUsuario}</p>

                </React.Fragment>
            );
        }
    }

    renderFooter(){
        if(this.state.opcion===""){
            return (
                <div>
                    <Button label="No" icon="pi pi-times" onClick={() => this.setState({displayConfirmation: false})} className="p-button-text" />
                    <Button label="Yes" icon="pi pi-check" onClick={() => this.deleteAlumno(this.state.seleccionado)} autoFocus />
                </div>
            );

        }else if(this.state.opcion==="op1"){
            return (
                <div>
                    
                </div>
            );

        }else{
            return (
                <div>
                   
                </div>
            );

        }
       
    }

    assignOption(data) {
        this.setState({ 
            opcion: data,
            succes:"",
            succes2:"",
            exist:"",
            exist2:"",
            nombreGrupoError:"",
            cursoError:""
            
    });
    }



    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        } else {
            if (this.state.redirect) {

                if (this.state.redirect === "/editStudent") {
                    return <Redirect
                        to={{
                            pathname: "/editStudent"
                        }}
                    />

                } else if (this.state.redirect === "/teacherGroups") {
                    return <Redirect
                        to={{
                            pathname: "/teacherGroups"
                        }}
                        />
                    }else if(this.state.redirect==="/assignStudent"){
                      return <Redirect
                      to={{
                      pathname: "/assignStudent"
                  }}
                   />
                  }
                  
              }
              console.log(this.state)

            const courseSelectItems = [
                { label: 'All courses', value: 'allCourses' },
                { label: 'A1', value: 'A1' },
                { label: 'A2', value: 'A2' },
                { label: 'B1', value: 'B1' },
                { label: 'B2', value: 'B2' },
                { label: 'C1', value: 'C1' },
                { label: 'C2', value: 'C2' },
                { label: 'Free learning', value: 'APRENDIZAJELIBRE' }
            ];

            const paginatorLeft = <Button type="button" icon="pi pi-refresh" className="p-button-text" />;
            const paginatorRight = <Button type="button" icon="pi pi-cloud" className="p-button-text" />;

            if(this.state.opcion===""){
                return (
                    <React.Fragment>

                        <div className="datatable-templating-demo">
                        {this.state.formularioCrearGrupo} 
                        {this.state.formularioDeleteGrupo}  
                        {this.state.formularioAssginStudent}

                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>Are you sure you want to delete the student?</span>
                         </div>
                         </Dialog>
                            <div>
                            <ListBox value={this.state.curso} options={courseSelectItems} onChange={(e) => this.showSelectCourse(e.value)} />
                            <div>&nbsp;</div>
                        
                            <ListBox options={this.allGroupNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                            <div>&nbsp;</div>
                            <Button icon="pi pi-plus-circle" label="Create group" className="p-button-secondary" onClick={()=>this.assignOption("op1")} />
                            {` `}
                            <Button icon="pi pi-minus-circle" label="Delete group" className="p-button-secondary" onClick={()=>this.assignOption("op2")} />
                            {` `}
                            <Button icon="pi pi-fw pi-users" label="My groups" className="p-button-secondary" onClick={this.botonGrupos} />

                            </div>
                            <div>&nbsp;</div>
                            <DataTable value={this.state.alumnos} paginator
                            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                            currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                            paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>

                                <Column header="Info" body={this.botonInfo}></Column>
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="nickUsuario" header="Nickname"></Column>
                                <Column field="grupos.nombreGrupo" header="Group's name"></Column>
                                <Column field="numTareasEntregadas" header="Activities done"></Column>
                                <Column header="Assign" body={this.botonAssign}></Column>
                                <Column header="Edit" body={this.boton}></Column>
                                <Column header="Delete" body={this.botonDelete}></Column>
                            </DataTable>
                        </div>
                        {this.mostrarInfo()}
                    </React.Fragment>
                )
            }else if(this.state.opcion==="op1"){
                return(
                <React.Fragment>

                        <div className="datatable-templating-demo">
                        {this.state.formularioCrearGrupo} 
                        {this.state.formularioDeleteGrupo}  
                        {this.state.formularioAssginStudent}

                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You must select a course</span>
                         </div>
                         </Dialog>
                            <div>
                            <ListBox value={this.state.curso} options={courseSelectItems} onChange={(e) => this.showSelectCourse(e.value)} />
                            <div>&nbsp;</div>
                        
                            <ListBox options={this.allGroupNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                            <div>&nbsp;</div>
                            <Button icon="pi pi-plus-circle" label="Create group" className="p-button-secondary" onClick={()=>this.assignOption("op1")} />
                            {` `}
                            <Button icon="pi pi-minus-circle" label="Delete group" className="p-button-secondary" onClick={()=>this.formDeleteGrupo()} />
                            {` `}
                            <Button icon="pi pi-fw pi-users" label="My groups" className="p-button-secondary" onClick={this.botonGrupos} />

                            </div>
                            <Dialog visible={true} style={{ width: '40vw'}} onHide={() => this.setState({opcion: ""})}>

                            <div>
                    <div className="c">
                        <div className="login2 request2">
                            <form onSubmit={this.save}>
                            {this.state.succes2}
                            {this.state.exist2}                       

                                <div className="t"><div><h5>Create Group</h5></div></div>

                                    <div className="i">
                                    <div className="p-inputgroup">
                                    <Dropdown name="grupo.cursoDeIngles" value={this.state.grupoS.cursos.cursoDeIngles} placeholder="Select a course" options={["A1","A2","B1","B2","C1","C2","APRENDIZAJELIBRE"]} onChange={this.handleCI} />

                                    </div>
                                    </div>

                                    <div className="i">
                                    {this.state.nombreGrupoError}
                                    <div className="p-inputgroup">
                                    <InputText field="nombreGrupo" value={this.state.grupoS.nombreGrupo} placeholder="Group's name" name="nombreGrupo" onChange={this.handleNG}/>

                                    </div>
                                    </div>

                                    <div className="b">
                                    <div className="i">
                                    <Button className="p-button-secondary" label="Save" icon="pi pi-fw pi-check"/>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                </Dialog>
                            <div>&nbsp;</div>
                            <DataTable value={this.state.alumnos} paginator
                            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                            currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                            paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>

                                <Column header="Info" body={this.botonInfo}></Column>
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="nickUsuario" header="Nickname"></Column>
                                <Column field="grupos.nombreGrupo" header="Group's name"></Column>
                                <Column field="numTareasEntregadas" header="Activities done"></Column>
                                <Column header="Assign" body={this.botonAssign}></Column>
                                <Column header="Edit" body={this.boton}></Column>
                                <Column header="Delete" body={this.botonDelete}></Column>
                            </DataTable>
                        </div>
                        {this.mostrarInfo()}
                    </React.Fragment>
                );    
        }else{
            return(

                <React.Fragment>

                        <div className="datatable-templating-demo">
                        {this.state.formularioCrearGrupo} 
                        {this.state.formularioDeleteGrupo}  
                        {this.state.formularioAssginStudent}

                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You muest select a group</span>
                         </div>
                         </Dialog>
                            <div>
                            <ListBox value={this.state.curso} options={courseSelectItems} onChange={(e) => this.showSelectCourse(e.value)} />
                            <div>&nbsp;</div>
                        
                            <ListBox options={this.allGroupNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                            <div>&nbsp;</div>
                            <Button icon="pi pi-plus-circle" label="Create group" className="p-button-secondary" onClick={()=>this.assignOption("op1")} />
                            {` `}
                            <Button icon="pi pi-minus-circle" label="Delete group" className="p-button-secondary" onClick={()=>this.assignOption("op2")} />
                            {` `}
                            <Button icon="pi pi-fw pi-users" label="My groups" className="p-button-secondary" onClick={this.botonGrupos} />

                            </div>
                            <Dialog visible={true} style={{ width: '40vw'}} onHide={() => this.setState({opcion: ""})}>


                            <div>
                    <div className="c">
                        <div className="login2 request2">
                            <form onSubmit={this.delete}>
                            {this.state.succes}
                            {this.state.exist}
                            {this.form()}
                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You must select a group</span>
                        </div>
                        </Dialog>
                            </form>
                        </div>
                    </div>
                </div>


                            </Dialog>
                            <div>&nbsp;</div>
                            <DataTable value={this.state.alumnos} paginator
                            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                            currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                            paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>

                                <Column header="Info" body={this.botonInfo}></Column>
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="nickUsuario" header="Nickname"></Column>
                                <Column field="grupos.nombreGrupo" header="Group's name"></Column>
                                <Column field="numTareasEntregadas" header="Activities done"></Column>
                                <Column header="Assign" body={this.botonAssign}></Column>
                                <Column header="Edit" body={this.boton}></Column>
                                <Column header="Delete" body={this.botonDelete}></Column>
                            </DataTable>
                        </div>
                        {this.mostrarInfo()}
                    </React.Fragment>


            );
        }

            }
        }
    }
function matchDispatchToProps(dispatch) {

    return bindActionCreators({
        selectStudent: selectStudent,
        selectAssignedStudent: selectAssignedStudent,
        selectCreatedGroup: selectCreatedGroup,
    }, dispatch)
}


export default connect(null, matchDispatchToProps)(Alumnos) 