import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'


class UsuariosList extends Component {
    state = {
        usuarios: []
    }

    componentDidMount() {
        axios.get('http://localhost:8080/getUsuarios')
            .then(res => {
                this.setState({ usuarios: res.data })
            })
    }

    ModificarClickHandler = e => {
        e.preventDefault()
        console.log("Mod")
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        console.log("Del")
    }


    render() {
        return (
            <table class="table" style={{marginTop:15, marginLeft:15}}>
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
                                <button class="btn btn-primary" style={{marginRight:10}} onClick={this.ModificarClickHandler}>Modificar usuario</button>
                                <button class="btn btn-danger" onClick={this.EliminarClickHandler}>Eliminar usuario</button>
                            </td>
                        </tr>
                )}
            </table>
        );
    }
}

export default UsuariosList






