import { Component } from 'react';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import axios from 'axios';
import { Dropdown } from 'primereact/dropdown';

export class FormEvent extends Component {

    title = this.title.bind(this);
    start = this.start.bind(this);
    end = this.end.bind(this);
    description = this.description.bind(this);
    type = this.type.bind(this);

    state = {
        title: "",
        start: "",
        end: "",
        description: "",
        type: "",
        titleError: null,
        startError: null,
        endError: null,
        descriptionError: null,
        typeError: null,
        succes: null,
        exist:null
    }

    title(event) {
        this.setState({ title: event.target.value });
    }

    start(event) {
        this.setState({ start: event.target.value });
    }

    end(event) {
        this.setState({ end: event.target.value });
    }

    description(event) {
        this.setState({ description: event.target.value });
    }

    type(event) {
        this.setState({ type: event.target.value });
    }

    handleSubmit = event => {
        event.preventDefault();

        this.setState({
            titleError: null,
            startError: null,
            endError: null,
            descriptionError: null,
            typeError: null,
            succes: null,
            exist:null
        })

        const evento = {
            title: this.state.title,
            start: this.state.start,
            end: this.state.end,
            descripcion: this.state.description,
            tipo : {
                tipo: this.state.type
            }
        }

        axios.post(this.props.urlBase + "/events/create", evento, { withCredentials: true }).then(res => {
            this.respuesta(res.status, res.data)
        })

    }

    respuesta(status, data) {
        if (status === 203) {
            data.forEach(e => this.error(e.field, e.defaultMessage))
        } else if (status === 201) {
            this.setState({
                title: "",
                start: "",
                end: "",
                description: "",
                type: "",
                succes: <div className="alert alert-success" role="alert">Successful creation</div>
            })
            this.props.act("Event create")
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }

    error(campo, mensaje) {
        if (campo === "title") {
            this.setState({ titleError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "start") {
            this.setState({ startError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "end") {
            this.setState({ endError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "descripcion") {
            this.setState({ descriptionError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "tipo.tipo") {
            this.setState({ typeError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }

    render() {
        return (
            <div>
                <div className="c">
                    <form onSubmit={this.handleSubmit}>
                        {this.state.succes}
                        {this.state.exist}
                        <div className="i">
                            {this.state.titleError}
                            <div className="p-inputgroup">
                                <span className="p-inputgroup-addon">
                                    <i className="pi pi-info"></i>
                                </span>
                                <InputText placeholder="Title" name="title" type="text" value={this.state.title} onChange={this.title} />
                            </div>
                        </div>
                        <div className="i">
                            {this.state.startError}
                            <div className="p-inputgroup">
                                <span className="p-inputgroup-addon">
                                    <i className="pi pi-calendar"></i>
                                    Start date
                                </span>
                                <InputText placeholder="Start" name="start" type="date" value={this.state.start} onChange={this.start} />
                            </div>
                        </div>
                        <div className="i">
                            {this.state.endError}
                            <div className="p-inputgroup">
                                <span className="p-inputgroup-addon">
                                    <i className="pi pi-calendar"></i>
                                    End date
                                </span>
                                <InputText placeholder="End" name="end" type="date" value={this.state.end} onChange={this.end} />
                            </div>
                        </div>
                        <div className="i">
                            {this.state.descriptionError}
                            <div className="p-inputgroup">
                                <span className="p-inputgroup-addon">
                                    <i className="pi pi-align-center"></i>
                                </span>
                                <InputText placeholder="Description" name="descripcion" type="text" value={this.state.description} onChange={this.description} />
                            </div>
                        </div>
                        <div className="i">
                            {this.state.typeError}
                            <div className="p-inputgroup">
                                <span className="p-inputgroup-addon">
                                    <i className="pi pi-sort-down"></i>
                                </span>
                                <Dropdown value={this.state.type} options={["internal", "external"]} 
                                onChange={this.type} placeholder="Type" name="tipo.tipo"/>
                            </div>
                        </div>
                        <div className="b">
                            <div className="i">
                                <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        );
    }

}