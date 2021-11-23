import React, { Component } from 'react'
import { Row, Table, Col, Button } from 'reactstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from "axios"

class Login extends Component {
    state = {
        form: {
            "email": '',
            "password": ''
        },
        error: false,
        errorMsg: ''
    }

    componentDidMount() {
        localStorage.setItem('emailUsuario', "");
        localStorage.setItem('rolUsuario', "");
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
                const rol = res.data;
                localStorage.setItem('emailUsuario', this.state.form.email);
                localStorage.setItem('rolUsuario', rol);
                if (rol === 'Paciente') {
                this.props.history.push("/paciente");
                } else if (rol === 'Administrador') {
                    this.props.history.push("/administrador");
                } else if (rol === 'Sanitario') {
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
                <h6>Introduce tus credenciales para entrar en el sistema</h6>
                <Table>
                    <Row>
                        <Col></Col>
                        <Col>
                            <input type="text" onChange={this.onChangeHandler} class="form-control" name="email" placeholder="Correo Electrónico" />
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