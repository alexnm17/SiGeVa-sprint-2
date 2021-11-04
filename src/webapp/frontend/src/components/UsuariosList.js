import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import ModificarUsuario from "./ModificarUsuario"


class UsuariosList extends Component {
    state = {
        usuarios: []
    }

    componentDidMount() {
        this.getUsuarios()
    }

    getUsuarios() {
        axios.get('http://localhost:8080/getUsuarios')
            .then(res => {
                this.setState({ usuarios: res.data })
            })
    }

    ModificarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
        /*axios.get('http://localhost:8080/',e.target.id)
            .then(res => {
                this.setState({ usuarios: res.data })
            })*/
        this.getUsuarios()
    }


    render() {
        return (
            <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                <tr>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Dni</th>
                    <th>Centro de Vacunacion</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
                {this.state.usuarios.map(usuario =>
                    <tr key={usuario.dni}>
                        <td>{usuario.nombre}</td>
                        <td>{usuario.apellido}</td>
                        <td>{usuario.dni}</td>
                        <td>{usuario.centroSalud}</td>
                        <td>{usuario.rol}</td>
                        <td>
                            <button class="btn btn-primary" id={usuario.dni} onClick={this.ModificarClickHandler} style={{ marginRight: 10 }}>Modificar usuario</button>
                            <button class="btn btn-danger" id={usuario.dni} onClick={this.EliminarClickHandler}>Eliminar usuario</button>
                        </td>
                    </tr>
                )}

            </table>
        );
    }
}

export default UsuariosList






