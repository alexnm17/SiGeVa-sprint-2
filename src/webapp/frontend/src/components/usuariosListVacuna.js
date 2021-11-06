import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'


class UsuariosList extends Component {
    state = {
        usuarios: [],
        fecha:""
    }

    componentDidMount() {
        this.getUsuariosPorFechaYCentro()
    }

    getUsuariosPorFechaYCentro() {
        axios.get('http://localhost:8080/getUsuariosAVacunar')
            .then(res => {
                this.setState({ usuarios: res.data })
            })
    }

    VacunarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
    }

    render() {
        return (
            <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                <tr>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Dni</th>
                    <th>Centro de Vacunacion</th>
                    <th>Estado de Vacunacion</th>
                    <th>Vacunar</th>
                </tr>
                {this.state.usuarios.map(usuario =>
                    <tr key={usuario.dni}>
                        <td>{usuario.nombre}</td>
                        <td>{usuario.apellido}</td>
                        <td>{usuario.dni}</td>
                        <td>{usuario.centroSalud}</td>
                        <td>{usuario.estadoVacunacion}</td>
                        <td>
                            <button class="btn btn-primary" id={usuario.dni} onClick={this.VacunarClickHandler} style={{ marginRight: 10 }}>Modificar usuario</button>
                        </td>
                    </tr>
                )}

            </table>
        );
    }
}

export default UsuariosList
