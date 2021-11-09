import React, { Component } from "react";
import { Button, Breadcrumb, Table, Row, Col } from "react-bootstrap"
import axios from "axios"

class Paciente extends Component {
    state = {
        citaUsuario: []
    }

    componentDidMount() {
        //this.setState({dni:localStorage.getItem("dniUsuario")}
        this.getCitaPaciente(localStorage.getItem("dniUsuario"))
    }

    getCitaPaciente(dniUsuario) {
        axios.get('http://localhost:8080/getCitaByDni', dniUsuario)
            .then(res => {
                console.log(res.data)
                this.setState({ citaUsuario: res.data })
            })
    }

    render() {
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                </div>
                <div>
                    <p>Selecciona la acción que quieres realizar: </p>
                    <a href="/paciente/SolicitarCita">
                        <Button style={{ marginRight: 15 }}>Solicitar cita</Button>
                    </a>
                    <Button variant="secondary" style={{ marginRight: 15 }}>Consultar cita</Button>
                </div>
                <Table>
                    <Row>
                        <Col></Col>
                        <Col><h6>Fecha:</h6></Col>
                        <Col></Col>
                    </Row>
                    <Row>
                        <Col></Col>
                        <Col><h6>Hora:</h6></Col>
                        <Col></Col>
                    </Row>
                    <Row>
                        <Col></Col>
                        <Col><h6>Centro:</h6></Col>
                        <Col></Col>
                    </Row>
                </Table>
            </div>
        );
    }
}

export default Paciente