import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import 'bootstrap/dist/css/bootstrap.min.css'
import ListUsuariosAVacunarHoy from "./listUsuariosAVacunarHoy";

class Sanitario extends Component {
    render() {
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
                <ListUsuariosAVacunarHoy />
            </div>
        );
    }
}

export default Sanitario