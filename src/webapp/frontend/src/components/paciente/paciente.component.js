import React, { Component } from "react";
import { Breadcrumb } from "react-bootstrap"
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from "reactstrap"
import axios from "axios"
import 'bootstrap/dist/css/bootstrap.min.css'

class Paciente extends Component {
    state = {
        citaUsuario: [],
        cuposList: [],
        email: "",
        modalSolicitar: false,
        modalModificar: false,
        modalConfirmarAnulacion: false,
        citaSeleccionada: "",
        fechaModificar: "",
        idCitaModalModificar: ""
    }

    componentDidMount() {
        this.getCitaPaciente()
    }

    getCitaPaciente() {
        axios.get('https://sigeva-grupo6.herokuapp.com/getCitaByEmail', { params: { email: localStorage.getItem("emailUsuario") } })
            .then(res => {
                this.setState({ citaUsuario: res.data })
            })
    }

    SolicitarClickHandler = () => {
        this.ocultarModalSolicitar()
        axios.post("https://sigeva-grupo6.herokuapp.com/solicitarCita", { email: localStorage.getItem("emailUsuario") })
            .then(res => {
                window.location.reload(true);
            }).catch(error => {
                this.ocultarModalSolicitar()
                if (error.response.status === 403) {
                    alert("Ya tienes tus citas asignada. Si hay algún error, por favor contacta con el administrador.");
                } else if (error.response.status === 404) {
                    alert("Usuario no encontrado. Si hay algún error, por favor contacta con el administrador.");
                } else if (error.response.status === 409) {
                    alert("Usuario ya vacunado, no puedes pedir cita si ya tienes las dos dosis puestas. Si hay algún error, por favor contacta con el administrador.");
                } else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
            })
    }

    anularHandler = e => {
        e.preventDefault()
        axios.delete('https://sigeva-grupo6.herokuapp.com/anularCita', { data: { idCita: this.state.idCitaModalModificar } })
            .then(res => {
                window.location.reload(true);
            }).catch(error => {
                this.ocultarModalSolicitar()
                if (error.response.status === 409) {
                    alert("La cita que intenta anular ya ha sido utilizada.");
                } else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
            })
    }

    getCuposLibres(fecha) {
        axios.get('https://sigeva-grupo6.herokuapp.com/getAllCuposConHuecoPorFecha', { params: { email: localStorage.getItem("emailUsuario"), fecha: fecha } })
            .then(res => {
                this.setState({ cuposList: res.data })
            })
    }

    seleccionarHandler = e => {
        e.preventDefault()
        this.ocultarModalModificar()
        axios.post('https://sigeva-grupo6.herokuapp.com/modificarCita', { emailUsuario: localStorage.getItem("emailUsuario"), idCupo: e.target.id, idCita: this.state.citaSeleccionada })
            .then(res => {
                window.location.reload(true);
            }).catch(error => {
                if (error.response.status === 403) {
                    alert("No hay hueco para cita en ese momento");
                } else if (error.response.status === 404) {
                    alert("No se puede modificar su cita puesto que ya está vacunado");
                } else if (error.response.status === 451){
                    alert("No se puede modificar la cita. La fecha no cumple con la norma de 21 entre citas entre citas o porque estas intentando poner la primera despues de la segunda dosis.")
                }
                else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
            })
    }

    ModificarClickHandler = e => {
        e.preventDefault()
    }

    mostrarModalSolicitar = () => {
        this.setState({ modalSolicitar: true })
    }

    ocultarModalSolicitar = () => {
        this.setState({ modalSolicitar: false })
    }

    mostrarModalModificar = e => {
        e.preventDefault()
        this.setState({ modalModificar: true })
        this.setState({ citaSeleccionada: e.target.id })
    }

    ocultarModalModificar = () => {
        this.setState({ modalModificar: false })
    }

    mostrarModalConfirmarAnulacion = e => {
        e.preventDefault()
        this.setState({ modalConfirmarAnulacion: true })
        this.setState({ idCitaModalModificar: e.target.id })
    }

    ocultarModalConfirmarAnulacion = () => {
        this.setState({ modalConfirmarAnulacion: false })
        this.setState({ idCitaModalModificar: "" })
    }

    changeDateHandler = e => {
        this.setState({ fechaModificar: e.target.value })
        this.getCuposLibres(e.target.value)
    }

    render() {
        if (localStorage.getItem('rolUsuario') === "Paciente") {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                    </Breadcrumb>
                    <div>
                        <p>Selecciona la acción que quieres realizar: </p>
                        <Button color="primary" onClick={this.mostrarModalSolicitar} style={{ marginRight: 15, marginBottom: 20 }}>Solicitar cita</Button>
                    </div>
                    <div>
                        <h5>Tus citas</h5>
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>Día</th>
                                    <th>Hora</th>
                                    <th>Centro Vacunacion</th>
                                    <th>Municipio</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.citaUsuario.map(cita =>
                                    <tr key={cita.idCita} style={{ marginBottom: 15 }}>
                                        <td>{cita.fecha}</td>
                                        <td>{cita.hora}</td>
                                        <td>{cita.centroVacunacion.nombre}</td>
                                        <td>{cita.centroVacunacion.municipio} </td>
                                        <td>
                                            <Button color="primary" id={cita.idCita} style={{ marginRight: 15 }} onClick={this.mostrarModalModificar}>Modificar</Button>
                                            <Button color="danger" id={cita.idCita} onClick={this.mostrarModalConfirmarAnulacion}>Anular</Button>
                                        </td>
                                    </tr>
                                )
                                }
                            </tbody>
                        </table>
                    </div>

                    {/* Modal para confirmar solicitud de cita */}
                    <Modal isOpen={this.state.modalSolicitar}>
                        <ModalHeader>
                            <div><h3>Confirmación de Solicitud</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label style={{ marginRight: 15 }}>Para solicitar sus citas de vacunación pulse "Solicitar"</label>
                            </FormGroup>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="success" onClick={this.SolicitarClickHandler}>Solicitar</Button>
                            <Button color="danger" onClick={this.ocultarModalSolicitar}>Salir</Button>
                        </ModalFooter>
                    </Modal>

                    {/* Modal para modificar citas */}
                    <Modal isOpen={this.state.modalModificar}>
                        <ModalHeader>
                            <div><h3>Modificar cita</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label style={{ marginRight: 15 }}>Selecciona la fecha a la que quieres cambiar la cita</label>
                                <input className="form-control" type="date" name="fechaModificar" onChange={this.changeDateHandler} value={this.state.fechaModificar}></input>
                                <table className="table" style={{ marginTop: 15, marginLeft: 15 }}>
                                    <thead>
                                        <tr>
                                            <th>Fecha</th>
                                            <th>Hora</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.cuposList.map(cupo =>
                                            <tr key={cupo.idCupo}>
                                                <td>{cupo.fecha}</td>
                                                <td>{cupo.hora}</td>
                                                <td>
                                                    <button className="btn btn-primary" id={cupo.idCupo} onClick={this.seleccionarHandler} style={{ marginRight: 10 }}>Seleccionar</button>
                                                </td>
                                            </tr>
                                        )}
                                    </tbody>
                                </table>
                            </FormGroup>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="danger" onClick={this.ocultarModalModificar}>Salir</Button>
                        </ModalFooter>
                    </Modal>

                    {/* Modal para confirmar la anulación de una cita */}
                    <Modal isOpen={this.state.modalConfirmarAnulacion}>
                        <ModalHeader>
                            <div><h3>Confirmar anulación</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label style={{ marginRight: 15 }}>¿Seguro que quieres anular la cita?</label>
                            </FormGroup>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="danger" onClick={this.anularHandler}>Sí, anular</Button>
                            <Button color="primary" onClick={this.ocultarModalConfirmarAnulacion}>No, no anular</Button>
                        </ModalFooter>
                    </Modal>

                </div>
            );
        } else {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                    </Breadcrumb>
                    <p>A esta sección solo pueden acceder los Pacientes.</p>
                    <p>Inicie sesión como paciente o contacte con un administrador.</p>
                </div>
            );
        }
    }
}

export default Paciente