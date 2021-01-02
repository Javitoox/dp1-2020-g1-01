import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';
import axios from 'axios';



export class CreateGroup extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            grupoS:{
                nombreGrupo: "",
                cursos:{
                    cursoDeIngles:""
                }
            },
            listaGrupos:{
                nombreGrupo: ""
            },
            succes:null,
            exist:null

        }
        this.grupos = new GrupoComponent();
        this.save = this.save.bind(this);
        this.handleCI = this.handleCI.bind(this);
        this.handleNG = this.handleNG.bind(this);
    }
    save = event => {
        event.preventDefault();

        const grupo = {
            nombreGrupo:this.state.grupoS.nombreGrupo,
            cursos:{
                cursoDeIngles:this.state.grupoS.cursos.cursoDeIngles
            } 
        }

        axios.post("http://localhost:8081/grupos/new", grupo, {withCredentials: true}).then(res => {
            this.respuesta(res.status, res.data);
        })
        
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

    respuesta(status, data){
        console.log(status);
        if(status===203){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status===201){
            this.setState({               

                grupoS:{
                    nombreGrupo:"",
                    cursos:{
                        cursoDeIngles:""
                    }
                },
                succes: <div className="alert alert-success" role="alert">Successful creation</div>
            })
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }
    componentDidMount() {
        this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));
    }

    render() {

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

        console.log(this.state.grupoS.nombreGrupo)
        console.log(this.state.grupoS.cursos.cursoDeIngles)

        return (

            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.save}>
                        {this.state.succes}
                        {this.state.exist}
                            <div className="t"><div><h5>Create Group</h5></div></div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <Dropdown name="grupo.cursoDeIngles" value={this.state.grupoS.cursos.cursoDeIngles} placeholder="Select a course" options={courseSelectItems} onChange={this.handleCI} />

                                </div>
                                </div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <InputText placeholder="NG" value={this.state.grupoS.nombreGrupo} placeholder="Group's name" name="nombreGrupo" onChange={this.handleNG}/>

                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                <Button className="p-button-secondary" label="OK" label="Guardar" icon="pi pi-fw pi-check"/>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}