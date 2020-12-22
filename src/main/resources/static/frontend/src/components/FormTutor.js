import { Component } from 'react';
import { InputText } from 'primereact/inputtext';
import Inject from './Inject';
import {Password} from 'primereact/password';

export default class FormTutor extends Component {
    otherNumber() {
        return <div className="i">
            <div className="p-inputgroup">
                <span className="p-inputgroup-addon">
                    <i className="pi pi-mobile"></i>
                </span>
                <InputText placeholder="Phone number" name="tutor.numTelefonoUsuario2" type="tel" value={this.props.telefono2} onChange={this.props.onTelefono2} />
            </div>
        </div>
    }

    render() {
        return (
            <div>
                <div className="t"><div><h5>Tutor</h5></div></div>
                <div className="i">
                    {this.props.errorUsername}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-user"></i>
                        </span>
                        <InputText placeholder="Username" name="tutor.nickUsuario" type="text" value={this.props.username} onChange={this.props.onUsername} />
                    </div>
                </div>

                <div className="i">
                {this.props.errorPassword}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-lock"></i>
                        </span>
                        <Password mediumRegex="^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{8,30}$" strongRegex="^^(?=\w*\d)(?=\w*[A-Z])(?=\w*[a-z])\S{14,30}$$" 
                        placeholder="Password" name="tutor.contraseya" value={this.props.password} onChange={this.props.onPassword} />
                    </div>
                </div>
                <div className="i">
                {this.props.errorCard}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-id-card"></i>
                        </span>
                        <InputText placeholder="Identity card" name="tutor.dniUsuario" type="text" value={this.props.card} onChange={this.props.onCard} />
                    </div>
                </div>
                <div className="i">
                {this.props.errorName}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-user-plus"></i>
                        </span>
                        <InputText placeholder="Full Name" name="tutor.nombreCompletoUsuario" type="text" value={this.props.name} onChange={this.props.onName} />
                    </div>
                </div>
                <div className="i">
                {this.props.errorEmail}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-inbox"></i>
                        </span>
                        <InputText placeholder="Email" name="tutor.correoElectronicoUsuario" type="email" value={this.props.email} onChange={this.props.onEmail} />
                    </div>
                </div>
                <div className="i">
                {this.props.errorTelefono}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-mobile"></i>
                        </span>
                        <InputText placeholder="Phone number" name="tutor.numTelefonoUsuario" type="tel" value={this.props.telefono} onChange={this.props.onTelefono} />
                    </div>
                </div>
                <div className="i">
                {this.props.errorTelefono2}
                <Inject onActivate={this.props.onActiveButton} activated={true} content={this.otherNumber()} message="Add another phone number"></Inject>
                </div>
                <div className="i">
                {this.props.errorAddress}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-home"></i>
                        </span>
                        <InputText placeholder="Address" name="tutor.direccionUsuario" type="text" value={this.props.address} onChange={this.props.onAddress} />
                    </div>
                </div>
                <div className="i">
                {this.props.errorBirthdate}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-calendar"></i>
                        </span>
                        <InputText placeholder="Birthdate" name="tutor.fechaNacimiento" type="date" value={this.props.birthdate} onChange={this.props.onBirthdate} />
                    </div>
                </div>
            </div>
        );
    }
}