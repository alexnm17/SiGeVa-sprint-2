import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import { Table, Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'

class UsuariosList extends Component {
    state = {
        usuarios: [],
        centroSalud: [],
        form: {
            dni: "",
            nombre: "",
            apellido: "",
            email: "",
            centroVacunacion: "",
            password: "",
            rol: ""
        },
        modalModificar: false,
        butttonDni: ""
    }

    componentDidMount() {
        this.getUsuarios()
        this.getCentrosSalud()
    }

    getUsuarios() {
        axios.get('http://localhost:8080/getUsuarios')
            .then(res => {
                this.setState({ usuarios: res.data })
            })
    }

    getCentrosSalud() {
        axios.get('http://localhost:8080/getAllCentros')
            .then(res => {
                this.setState({ centroSalud: res.data })
            })
    }

    changeHandler = e => {
        e.preventDefault()
        this.setState({
            form: {
                ...this.state.form,
                [e.target.name]: e.target.value,
            }
        });
    }

    mostrarModalModificar = (usuario) => {
        this.setState({ modalModificar: true, form: usuario })
    }

    ocultarModalModificar = e => {
        e.preventDefault()
        this.setState({ modalModificar: false })
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        axios.delete('http://localhost:8080/eliminarUsuario', { data: { email: e.target.id } })
            .then(res => {
                this.getUsuarios()
            })
    }

    ModificarHandler = e => {
        e.preventDefault()
        axios.post('http://localhost:8080/modificarUsuario', this.state.form)
            .then(res => {
                this.getUsuarios()
                this.setState({ modalModificar: false })
            })
    }

    render() {
        const { centroVacunacion } = this.state.form
        return (
            <div>
                <Table className="table" style={{ marginTop: 15, marginLeft: 15 }}>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Dni</th>
                            <th>Email</th>
                            <th>Centro de Vacunacion</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr></thead>
                    <tbody>
                        {this.state.usuarios.map(usuario =>
                            <tr key={usuario.email}>
                                <td>{usuario.nombre}</td>
                                <td>{usuario.apellido}</td>
                                <td>{usuario.dni}</td>
                                <td>{usuario.email}</td>
                                <td>{usuario.centroVacunacion.nombre}</td>
                                <td>{usuario.rol}</td>
                                <td>
                                    <Button color="primary" onClick={() => this.mostrarModalModificar(usuario)} style={{ marginRight: 10 }}>Modificar usuario</Button>
                                    <Button color="danger" id={usuario.email} onClick={this.EliminarClickHandler}>Eliminar usuario</Button>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </Table>
                <Modal isOpen={this.state.modalModificar}>
                    <ModalHeader>
                        <div><h3>Modificar usuario</h3></div>
                    </ModalHeader>
                    <ModalBody>
                        <FormGroup>
                            <label style={{ marginRight: 15 }}>Email: </label>
                            <label>{this.state.form.email}</label>
                        </FormGroup>
                        <FormGroup>
                            <label style={{ marginRight: 15 }}>Contraseña: </label>
                            <input className="form-control" type="password" name="password" onChange={this.changeHandler} value={this.state.form.password}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>DNI:</label>
                            <input className="form-control" type="text" name="dni" onChange={this.changeHandler} value={this.state.form.dni}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Nombre:</label>
                            <input className="form-control" type="text" name="nombre" onChange={this.changeHandler} value={this.state.form.nombre}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Apellidos</label>
                            <input className="form-control" type="text" name="apellido" onChange={this.changeHandler} value={this.state.form.apellido} />
                        </FormGroup>
                        <FormGroup>
                            <label style={{ marginRight: 10 }}>Centro de Vacunacion: </label>
                            <select className="form-select" name="centroVacunacion" onChange={this.changeHandler} value={centroVacunacion}>
                                {this.state.centroSalud.map(centro =>
                                    <option key={centro.nombre}>{centro.nombre}</option>
                                )}
                            </select>
                        </FormGroup>
                        <FormGroup>
                            <label style={{ marginRight: 10 }}>Rol:</label>
                            <select name="rol" onChange={this.changeHandler} value={this.state.form.rol}>
                                <option defaultValue>Selecciona un rol...</option>
                                <option>Paciente</option>
                                <option >Administrador</option>
                                <option >Sanitario</option>
                            </select>
                        </FormGroup>
                    </ModalBody>

                    <ModalFooter>
                        <Button color="primary" onClick={this.ModificarHandler}>Aceptar</Button>
                        <Button color="danger" onClick={this.ocultarModalModificar}>Cancelar</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default UsuariosList






