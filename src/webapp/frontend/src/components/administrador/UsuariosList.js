import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import { Table, Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'

class UsuariosList extends Component {
    state = {
        usuarios: [],
        form: {
            dni: "",
            nombre: "",
            apellido: "",
            centroVacunacion: "",
            rol: ""
        },
        modalModificar: false,
        butttonDni:""
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

    handleChange = e => {
        e.preventDefault()
        this.setState({
            form: {
                ...this.state.form,
                [e.target.name]: e.target.value,
            }
        });
    }
 
    mostrarModalModificar = (dni) => {
        console.log(dni)
        this.setState({butttonDni:dni})
        this.setState({ modalModificar: true })
    }

    ocultarModalModificar = e => {
        e.preventDefault()
        this.setState({ modalModificar: false })
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
        axios.delete('http://localhost:8080/eliminarUsuario', { data: e.target.id })
            .then(res => {
                this.getUsuarios()
            })
    }

    render() {
        const { usuarios } = this.state

        return (
            <div>
                <Table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
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
                                <Button color="primary" id={usuario.dni} onClick={()=>this.mostrarModalModificar(usuario.dni)} style={{ marginRight: 10 }}>Modificar usuario</Button>
                                <Button color="danger" id={usuario.dni} onClick={this.EliminarClickHandler}>Eliminar usuario</Button>
                            </td>
                        </tr>
                    )}
                </Table>
                <Modal isOpen={this.state.modalModificar}>
                    <ModalHeader>
                        <div><h3>Modificar usuario</h3></div>
                    </ModalHeader>

                    <ModalBody>
                        <FormGroup>
                            <label style={{marginRight: 15 }}>Dni: </label>
                            <label>{this.state.butttonDni}</label>
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
                            <label>Centro de Vacunacion</label>
                            <input className="form-control" type="text" name="centroVacunacion" onChange={this.handleChange}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Rol</label>
                            <input className="form-control" readOnly type="text"></input>
                        </FormGroup>
                    </ModalBody>

                    <ModalFooter>
                        <Button color="primary">Aceptar</Button>
                        <Button color="danger" onClick={this.ocultarModalModificar}>Cancelar</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default UsuariosList






