import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import '../menu.css';
import { Component } from 'react';
import { Menubar } from 'primereact/menubar';

export class MenubarResponsive extends Component {

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
        const start = <img alt="logo" src="https://static.wixstatic.com/media/8d86d1_1e825f1702a2414eba98748ce4fc56b3~mv2.png/v1/fill/w_121,h_106,al_c,q_85,usm_0.66_1.00_0.01/8d86d1_1e825f1702a2414eba98748ce4fc56b3~mv2.webp" height="40" className="p-mr-2"></img>;

        return (
            <div>
                <div className="card">
                    <Menubar model={false ? this.state.items1:this.state.items1.concat(this.state.items2)} start={start}/>
                </div>
            </div>
        );
    }
}