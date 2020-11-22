import { Component } from 'react';

export default class ExtraccionMensajes extends Component {
    getParameterByName(name) {
        // eslint-disable-next-line
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(window.location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }
}
