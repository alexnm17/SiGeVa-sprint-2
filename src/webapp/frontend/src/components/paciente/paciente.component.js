import React, { Component } from "react";
import {Breadcrumb} from "react-bootstrap"
import { Button, Table, Row, Col } from "reactstrap"
import axios from "axios"
import 'bootstrap/dist/css/bootstrap.min.css'

class Paciente extends Component {
    state = {
        citaUsuario: [],
        dni: ""
    }

    componentDidMount() {
       // this.setState({dni:localStorage.getItem("dniUsuario")})
        this.getCitaPaciente()
    }

    getCitaPaciente() {
        console.log(this.state.dni)
        axios.post('http://localhost:8080/getCitaByDni', { dni: localStorage.getItem("dniUsuario")})
            .then(res => {
                console.log(res.data)
                this.setState({ citaUsuario: res.data })
                console.log(this.citaUsuario)
            })
    }

    render() {
        if(localStorage.getItem('rolUsuario')=="Paciente"){
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
                    </div>
                    <h5>Tus citas</h5>
                    <Table class="table">
                        <Row>
                            <Col></Col>
                            <Col><h6>Día</h6></Col>
                            <Col><h6>Hora</h6></Col>
                            <Col><h6>Centro Vacunacion</h6></Col>
                            <Col><h6>Municipio</h6></Col>
                            <Col><h6>Acciones</h6></Col>
                            <Col></Col>
                        </Row>
                        {this.state.citaUsuario.map(cita =>
                            <Row style={{ marginBottom: 15 }}>
                                <Col></Col>
                                <Col>{cita.fecha}</Col>
                                <Col>{cita.hora}</Col>
                                <Col>{cita.centroVacunacion.nombre}</Col>
                                <Col>{cita.centroVacunacion.municipio} </Col>
                                <Col>
                                    <Button color="primary" style={{ marginRight: 15 }}>Modificar</Button>
                                    <Button color="danger">Anular</Button>
                                </Col>
                                <Col></Col>
                            </Row>
                        )
                        }
                    </Table>
                </div>
            );
        }else{
            return(
                <div>
                    <Breadcrumb style={{margin:30}}>
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