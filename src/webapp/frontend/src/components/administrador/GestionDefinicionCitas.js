import React, { Component } from 'react';
import { Breadcrumb, Button } from "react-bootstrap"
import axios from 'axios';
import { Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'

class GestionDefinicionDeCitas extends Component {
    state = {
        formatoVacunacion: [],
        buttonDisabled: false,
        modalDefinir: false,
        horaInicio: '',
        horaFin: '',
        duracionFranja: '',
        personasAVacunar: ''
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

    definirHandler = e => {
        e.preventDefault()
        this.ocultarModalDefinir()
        alert("Ya puedes navegar por la aplicación, si hay algun problema en la creación se te notificará.")
        axios.post("http://localhost:8080/definirFormatoVacunacion", this.state)
            .then(res => {
                window.location.reload(true);
                this.crearPlantillaCitas()
            }).catch(error => {
                alert("No se pudo definir el formato de vacunacion, ¿Seguro que no está definido ya?")
            })
    }

    crearPlantillaCitas() {
        axios.post("http://localhost:8080/crearPlantillasCitaVacunacion")
            .then(res => {
                alert("Plantilla de citas generada totalmente con éxito")
            }).catch(error => {
                alert("No se pudo generar la plantilla de citas")
            })
    }

    mostrarModalDefinir = () => {
        this.setState({ modalDefinir: true })
    }

    ocultarModalDefinir = () => {
        this.setState({ modalDefinir: false })
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
    }

    changeHoraHandler = e => {
        if (e.target.value.match("^[0-5]{0,1}[0-9]{0,1}[:]{0,1}[0-5]{0,1}[0-9]{0,1}$") != null) {
            this.setState({[e.target.name]: e.target.value});
        }
    }
    
    changeOnlyNumberHandler = e => {
        if (e.target.value.match("^[0-9]*$") != null) {
            this.setState({[e.target.name]: e.target.value});
        }
    }

    changeOnlyMinutesHandler = e => {
        if (e.target.value.match("^[0-5]{0,1}[0-9]{0,1}$") != null) {
            this.setState({[e.target.name]: e.target.value});
        }
    }

    render() {
        const { formatoVacunacion } = this.state
        if (localStorage.getItem('rolUsuario') === "Administrador") {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador/GestionDefinicionDeCitas">Gestión de Definicón de Citas</Breadcrumb.Item>
                    </Breadcrumb>
                    <div>
                        <p>Selecciona la acción que quieres realizar: </p>
                        <Button style={{ marginRight: 15 }} onClick={() => this.mostrarModalDefinir()}>Definir formato de vacunacion</Button>
                        <table className="table" style={{ marginTop: 15, marginLeft: 15 }}>
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
                    <Modal isOpen={this.state.modalDefinir}>
                        <ModalHeader>
                            <div><h3>Definir formato de vacunación</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label style={{ marginRight: 15 }}>Hora Inicio de Vacunación: </label>
                                <input className="form-control" placeholder="08:00" type="text" name="horaInicio" onChange={this.changeHoraHandler} value={this.state.horaInicio}></input>
                            </FormGroup>
                            <FormGroup>
                                <label style={{ marginRight: 15 }}>Hora Fin de Vacunación: </label>
                                <input className="form-control" placeholder="09:00" type="text" name="horaFin" onChange={this.changeHoraHandler} value={this.state.horaFin}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Duración de cada franja:</label>
                                <input className="form-control" placeholder="30" type="text" name="duracionFranja" onChange={this.changeOnlyMinutesHandler} value={this.state.duracionFranja}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Número de personas por franja:</label>
                                <input className="form-control" placeholder="5" type="text" name="personasAVacunar" onChange={this.changeOnlyNumberHandler} value={this.state.personasAVacunar}></input>
                            </FormGroup>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="primary" onClick={this.definirHandler}>Aceptar y crear los cupos de citas</Button>
                            <Button color="danger" onClick={() => this.ocultarModalDefinir()}>Cancelar</Button>
                        </ModalFooter>
                    </Modal>
                </div>
            );
        } else {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
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

export default GestionDefinicionDeCitas