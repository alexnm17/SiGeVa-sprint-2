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
        modalEliminar: false,
        butttonDni: "",
        usuarioAEliminar: ""
    }

    componentDidMount() {
        this.getUsuarios()
        this.getCentrosSalud()
    }

    getUsuarios() {
        axios.get('https://sigeva-grupo6.herokuapp.com/getUsuarios')
            .then(res => {
                this.setState({ usuarios: res.data })
            }).catch(error => {
                alert("Error desconocido, por favor contacta con el administrador.")
            })
    }

    getCentrosSalud() {
        axios.get('https://sigeva-grupo6.herokuapp.com/getAllCentros')
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

    mostrarModalEliminar = e => {
        e.preventDefault()
        this.setState({ usuarioAEliminar: e.target.id })
        this.setState({ modalEliminar: true })
    }

    ocultarModalEliminar = e => {
        e.preventDefault()
        this.setState({ modalEliminar: false })
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        axios.delete('https://sigeva-grupo6.herokuapp.com/eliminarUsuario', { data: { email: this.state.usuarioAEliminar } })
            .then(res => {
                this.getUsuarios()
                this.setState({ modalEliminar: false })
            }).catch(error => {
                if (error.response.status === 403) {
                    alert("No puede eliminar a otro administrador del sistema");
                } else if (error.response.status === 423) {
                    alert("Alguno de los campos introducidos es erroneo. Compruebalos y prueba otra vez");
                } else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
                this.setState({ modalEliminar: false })
            })
    }

    ModificarHandler = e => {
        e.preventDefault()
        axios.post('https://sigeva-grupo6.herokuapp.com/modificarUsuario', this.state.form)
            .then(res => {
                this.getUsuarios()
                this.setState({ modalModificar: false })
            }).catch(error => {
                if (error.response.status === 403) {
                    alert("No puede modificar a otro administrador del sistema");
                } else if (error.response.status === 404) {
                    alert("No existe un usuario con este identificador");
                }else if (error.response.status === 304) {
                    alert("No se puede completar este proceso ya que el usuario ya esta vacunado");
                }else if (error.response.status === 409) {
                    alert("Algún campo es erroneo. Compruebelos y pruebe otra vez.");
                }else if (error.response.status === 501) {
                    alert("Algún campo está vacío. Compruebelos y pruebe otra vez.");
                } else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
            })
    }

    changeOnlyStringHandler = e => {
        if (e.target.value.match("^[a-zA-Z ]*$") != null) {
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
    }

    changeDNIHandler = e => {
        if (e.target.value.match("^[0-9]{0,8}[A-Za-z]{0,1}$") != null) {
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
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
                                    <Button color="danger" id={usuario.email} onClick={this.mostrarModalEliminar}>Eliminar usuario</Button>
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
                            <input className="form-control" placeholder="Si cambias la contraseña se cambiará. Si no, se mantendrá." type="password" name="password" onChange={this.changeHandler} value={this.state.form.password}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>DNI:</label>
                            <input className="form-control" type="text" name="dni" onChange={this.changeDNIHandler} value={this.state.form.dni}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Nombre:</label>
                            <input className="form-control" type="text" name="nombre" onChange={this.changeOnlyStringHandler} value={this.state.form.nombre}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Apellidos</label>
                            <input className="form-control" type="text" name="apellido" onChange={this.changeOnlyStringHandler} value={this.state.form.apellido} />
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
                <Modal isOpen={this.state.modalEliminar}>
                    <ModalHeader>
                        <div><h3>Confirmar eliminación</h3></div>
                    </ModalHeader>
                    <ModalBody>
                        <FormGroup>
                            <label style={{ marginRight: 15 }}>¿Seguro que quieres eliminar este usuario?</label>
                        </FormGroup>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="danger" onClick={this.EliminarClickHandler}>Sí, eliminar</Button>
                        <Button color="primary" onClick={this.ocultarModalEliminar}>No, no eliminar</Button>
                    </ModalFooter>
                </Modal>

            </div>
        );
    }
}

export default UsuariosList






