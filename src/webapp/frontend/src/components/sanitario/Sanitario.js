import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import { Table, Col, Row } from 'reactstrap'
import axios from "axios"
import 'bootstrap/dist/css/bootstrap.min.css'

class Sanitario extends Component {
    constructor() {
        super()

        var today = new Date()
        var diaFormateado = ''
        if (today.getDate() < 10)
            diaFormateado = '-0' + today.getDate()
        else
            diaFormateado = '-' + today.getDate.value

        var date = today.getFullYear() + '-' + (today.getMonth() + 1) + diaFormateado;

        this.state = {
            fechaHoy: date,
            fechaSeleccionada: "",
            listaVacunacion: [],
            isOpenListaHoy: "hidden"
        }
    }


    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
        this.mostrarTablaVacunacion(this.state.fechaSeleccionada)
    }

    mostrarTablaVacunacion(fechaSeleccionada) {
        axios.post('http://localhost:8080/getCitasPorDia', { dni: localStorage.getItem("dniUsuario"), fecha: fechaSeleccionada })
            .then(res => {
                console.log(res.data)
                this.setState({ listaVacunacion: res.data })
            })
    }


    render() {
        const { fechaSeleccionada, listaVacunacion } = this.state
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Sanitario">Sanitario</Breadcrumb.Item>
                </Breadcrumb>
                <div>
                    <h6>Selecciona una fecha para ver la lista de vacunacion</h6>
                    {/* <input type="date" onChange={this.changeHandler} name="fechaSeleccionada" value={fechaSeleccionada}></input> */}
                    <Button>Ver lista de otro día</Button>
                </div>
                <Table style={{ marginTop: 15 }}>
                    <Row>
                        <Col></Col>
                        <Col><h6>DNI</h6></Col>
                        <Col><h6>Hora Vacunacion</h6></Col>
                        <Col><h6>Acciones</h6></Col>
                        <Col></Col>
                    </Row>
                    {this.state.listaVacunacion.map(usuario =>
                        <Row style={{ marginBottom: 15 }}>
                            <Col></Col>
                            <Col>{this.listaVacunacion.dni}</Col>
                            <Col>{this.listaVacunacion.hora}</Col>
                            <Col><Button>Vacunar</Button></Col>
                            <Col></Col>
                        </Row>
                    )
                    }
                </Table>
            </div>
        );
    }
}

export default Sanitario