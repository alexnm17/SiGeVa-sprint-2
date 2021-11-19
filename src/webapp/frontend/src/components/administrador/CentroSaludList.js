import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'


class centroSaludList extends Component {
    state = {
        centroSalud: [],
        modalModificar: false,
        form: {
            idCentroVacunacion:"",
            nombre: "",
            municipio: "",
            dosis: ""
        }
    }

    componentDidMount() {
        this.getCentrosSalud()
    }

    getCentrosSalud() {
        axios.get('http://localhost:8080/getAllCentros')
            .then(res => {
                this.setState({ centroSalud: res.data })
            })
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
        /*axios.get('http://localhost:8080/',e.target.id)
            .then(res => {
                this.setState({ usuarios: res.data })
            })*/
        this.getCentrosSalud()
    }

    mostrarModalModificar = (centro) => {
        this.setState({ modalModificar: true })
        this.setState({ form: centro })
    }

    ocultarModalModificar = () => {
        this.setState({ modalModificar: false })
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

    changeOnlyNumberHandler = e => {
        if(e.target.value.match("^[0-9]*$") != null){
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }   
    }

    changeOnlyNumberAndStringHandler = e => {
        if(e.target.value.match("^[A-Za-z0-9 ]*$") != null){
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }   
    }

    changeOnlyStringHandler = e => {
        if(e.target.value.match("^[A-Za-z ]*$") != null){
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }   
    }

    ModificarHandler = e => {
        e.preventDefault()
        console.log(this.state.form);
        axios.post('http://localhost:8080/modificarCentro', this.state.form)
            .then(res => {
                this.getCentrosSalud()
                this.setState({ modalModificar: false })
            })
    }

    render() {
        return (
            <div>
                <table className="table" style={{ marginTop: 15, marginLeft: 15 }}>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Municipio</th>
                            <th>Dosis</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.centroSalud.map(centroSalud =>
                            <tr key={centroSalud.nombre}>
                                <td>{centroSalud.nombre}</td>
                                <td>{centroSalud.municipio}</td>
                                <td>{centroSalud.dosis}</td>
                                <td>
                                    <button className="btn btn-primary" id={centroSalud.idCentroVacunacion} style={{ marginRight: 10 }} onClick={() => this.mostrarModalModificar(centroSalud)}>Modificar centro</button>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>

                <Modal isOpen={this.state.modalModificar}>
                    <ModalHeader>
                        <div><h3>Modificar centro de salud</h3></div>
                    </ModalHeader>

                    <ModalBody>
                        <FormGroup>
                            <label>Nombre:</label>
                            <input className="form-control" type="text" name="nombre" onChange={this.changeOnlyNumberAndStringHandler} value={this.state.form.nombre}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Municipio:</label>
                            <input className="form-control" type="text" name="municipio" onChange={this.changeOnlyStringHandler} value={this.state.form.municipio}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Dosis</label>
                            <input className="form-control" type="text"  name="dosis" onChange={this.changeOnlyNumberHandler} value={this.state.form.dosis}></input>
                        </FormGroup>
                    </ModalBody>

                    <ModalFooter>
                        <Button color="primary" onClick={this.ModificarHandler}>Aceptar</Button>
                        <Button color="danger" onClick={() => this.ocultarModalModificar()}>Cancelar</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default centroSaludList