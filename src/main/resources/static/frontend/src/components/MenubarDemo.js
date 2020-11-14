import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import '../index.css';
import { Component } from 'react';
import { TabMenu } from 'primereact/tabmenu';

export class MenubarDemo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            items1: [
                {label: 'Inicio', icon: 'pi pi-fw pi-home'},
                {label: 'Solicitudes de matrícula', icon: 'pi pi-fw pi-file'},
                {label: 'Sobre notrosos', icon: 'pi pi-fw pi-question'}
            ],
            items2: [
                {label: 'Alumnos', icon: 'pi pi-fw pi-users'},
                {label: 'Pagos', icon: 'pi pi-fw pi-dollar'},
                {label: 'Material', icon: 'pi pi-fw pi-pencil'},
                {label: 'Wall of Fame', icon: 'pi pi-fw pi-star'},
                {label: 'Calendario escolar', icon: 'pi pi-fw pi-calendar'}
            ]
        };
    }

    render() {
        return (
                <TabMenu model={false ? this.state.items1:this.state.items1.concat(this.state.items2)} activeItem={this.state.activeItem} onTabChange={(e) => this.setState({activeItem: e.value})}/>
        );
    }
}