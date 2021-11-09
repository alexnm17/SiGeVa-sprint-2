import React, { Component } from "react"
import { Breadcrumb } from "react-bootstrap"
import UsuariosList from "./UsuariosList"
import {Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import DropdownCentros from "./DropdownCentros"


class GestionCentroSalud extends Component {
    state = {
        form: {
            dni: "",
            nombre: "",
            apellido: "",
            centroVacunacion: "",
            rol: ""
        },
        modalCrear: false,
    }

    mostrarModalCrear = () => {
        this.setState({ modalCrear: true })
    }

    ocultarModalCrear = () => {
        this.setState({ modalCrear: false })
    }

    render() {
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionUsuarios">Gestion de Usuarios</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de Gestion de Usuarios</h5>
                </div>
                <div>
                    <p>Selecciona la acción que quieres realizar: </p>
                    {/* <a href="/administrador/GestionUsuarios/CrearUsuario"> */}
                        <Button color="success" onClick={() => this.mostrarModalCrear()}>Crear usuario</Button>
                    {/* </a> */}
                    <UsuariosList />
                </div>

                <Modal isOpen={this.state.modalCrear}>
                    <ModalHeader>
                        <div><h3>Crear usuario</h3></div>
                    </ModalHeader>

                    <ModalBody>
                        <FormGroup>
                            <label>Dni:</label>
                            <input className="form-control" type="text"></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Nombre:</label>
                            <input className="form-control" type="text" name="nombre" onChange={this.handleChange}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Apellidos</label>
                            <input className="form-control" type="text" name="apellidos" onChange={this.handleChange}></input>
                        </FormGroup>
                        <FormGroup>
                            <label  style={{ marginRight: 15}}>Centro de Vacunacion:</label>
                            <DropdownCentros />

                        </FormGroup>
                        <FormGroup>
                            <label style={{ marginRight: 15}}>Rol:</label>
                            <select>
                                <option>Paciente</option>
                                <option >Administrador</option>
                                <option >Sanitario</option>
                            </select>                        </FormGroup>
                    </ModalBody>

                    <ModalFooter>
                        <Button color="primary">Aceptar</Button>
                        <Button color="danger" onClick={() => this.ocultarModalCrear()}>Cancelar</Button>
                    </ModalFooter>
                </Modal>

            </div>
        );
    }

}

export default GestionCentroSalud