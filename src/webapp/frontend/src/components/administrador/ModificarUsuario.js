import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'


class UsuariosList extends Component {

    render() {
        return (
            <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                    <tr>
                        <input placeholder=""></input>
                        <input placeholder=""></input>
                        <input placeholder=""></input>
                        <input placeholder=""></input>
                        <input placeholder=""></input>
                        <td>
                            <button class="btn btn-success" style={{ marginRight: 10 }}>Aceptar</button>
                        </td>
                    </tr>
            </table>
        );
    }
}

export default UsuariosList






