import React, { Component } from 'react';
import { Breadcrumb, Button } from "react-bootstrap"
import axios from 'axios';



class GestionCentroSalud extends Component {
    state = {
        formatoVacunacion: [],
        buttonDisabled: false
    }

    componentDidMount() {
        this.getFormatoVacunacion()
    }

    getFormatoVacunacion() {
        axios.get('http://localhost:8080/getFormatoVacunacion')
            .then(res => {
                this.setState({ formatoVacunacion: res.data })
            })
    }

    render() {
        const { formatoVacunacion } = this.state
        if(localStorage.getItem('rolUsuario')=="Administrador"){
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador/GestionDefinicionDeCitas">Gestión de Definición De Citas</Breadcrumb.Item>
                    </Breadcrumb>
                    <div style={{ marginBotton: 20 }}>
                        <h5>Estas en la página de Gestión de la Definición de Citas</h5>
                    </div>
                    <div>
                        <p>Selecciona la acción que quieres realizar: </p>
                        <a href="/Administrador/GestionDefinicionDeCitas/DefinirFormatoVacunacion">
                            <Button style={{ marginRight: 15 }}>Definir formato de vacunacion</Button>
                        </a>
                        <a href="/Administrador/GestionDefinicionDeCitas/CrearPlantillaCitas">
                            <Button style={{ marginRight: 15 }}>Crear plantilla de citas</Button>
                        </a>
                        <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Hora Inicio</th>
                                    <th>Hora Fin</th>
                                    <th>Duracion de la franja</th>
                                    <th>Personas por franja</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr key={formatoVacunacion.horaInicioVacunacion}>
                                    <td></td>
                                    <td>{formatoVacunacion.horaInicioVacunacion}</td>
                                    <td>{formatoVacunacion.horaFinVacunacion}</td>
                                    <td>{formatoVacunacion.duracionFranjaVacunacion}</td>
                                    <td>{formatoVacunacion.personasPorFranja}</td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            );
        }else{
            return(
                <div>
                    <Breadcrumb style={{margin:30}}>
                            <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                            <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    </Breadcrumb>
                    <p>A esta sección solo pueden acceder los Administradores.</p>
                    <p>Inicie sesión como administrador para continuar.</p>
                </div>
            );
        }
    }
}

export default GestionCentroSalud