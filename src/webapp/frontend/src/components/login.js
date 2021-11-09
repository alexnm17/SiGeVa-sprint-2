import React, { Component } from 'react'
import { Row, Table, Col, Button } from 'reactstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from "axios"
import { Redirect } from 'react-router'

class Login extends Component {
    state = {
        form: {
            "dni": '',
            "password": '',
            "rol": ''
        },
        error: false,
        errorMsg: ''
    }

    onChangeHandler = async e => {
        await this.setState({
            form: {
                ...this.state.form,
                [e.target.name]: e.target.value
            }
        })
    }

    submitHandler = e => {
        e.preventDefault()
        axios.post("http://localhost:8080/login", this.state.form)
            .then(res => {
                localStorage.setItem('dniUsuario', this.state.form.dni);
                localStorage.setItem('rolUsuario', this.state.form.rol);
                if (this.state.form.rol === 'Paciente') {
                this.props.history.push("/paciente");
                } else if (this.state.form.rol === 'Administrador') {
                    this.props.history.push("/administrador");
                } else if (this.state.form.rol === 'Sanitario') {
                    this.props.history.push("/sanitario");
                } else {
                    alert('Hubo un problema durante la autenticación')
                }

            }).catch(error => {
                alert("No has podido acceder")
            })
    }

    render() {
        return (
            <div>
                <h6>Introduce tus credenciales y tu rol para entrar en el sistema</h6>
                <Table>
                    <Row>
                        <Col></Col>
                        <Col>
                            <input type="text" onChange={this.onChangeHandler} class="form-control" name="dni" placeholder="DNI" />
                        </Col>
                        <Col></Col>
                    </Row>
                    <Row>
                        <Col></Col>
                        <Col>
                            <input type="password" onChange={this.onChangeHandler} class="form-control" name="password" placeholder="Password" style={{ marginTop: 15 }} />
                        </Col>
                        <Col></Col>
                    </Row>
                    <Row>
                        <Col></Col>
                        <Col>
                            <select class="form-control" onChange={this.onChangeHandler} style={{ marginTop: 15 }} name="rol">
                                <option hidden selected>Selecciona un rol...</option>
                                <option>Paciente</option>
                                <option >Administrador</option>
                                <option >Sanitario</option>
                            </select></Col>
                        <Col></Col>
                    </Row>
                    <Row>
                        <Col></Col>
                        <Col>
                            <Button color="primary" onClick={this.submitHandler} style={{ marginTop: 15 }} >Log in</Button>
                        </Col>
                        <Col></Col>
                    </Row>
                </Table>
            </div>
        );
    }
}

export default Login;