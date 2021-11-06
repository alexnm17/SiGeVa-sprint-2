import React, { Component } from "react";
import { Breadcrumb, } from "react-bootstrap"


class Sanitario extends Component {

    state = {
        fecha: "",
        listaVacunacion: []
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
        //Aquí luego se mete el axios.get("getListaVacunacion/{fecha}")
    }

    render() {
        const {fecha, listaVacunacion} = this.state
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Sanitario">Sanitario</Breadcrumb.Item>
                </Breadcrumb>
                <div>
                    <h6>Seleccinoa una fecha para ver la lista de vacunacion</h6>
                    <h4>Lista de vacunacion para el día </h4>
                    <input type="date" onChange={this.changeHandler} name="fecha" value={fecha}></input>
                </div>
            </div>
        );
    }
}

export default Sanitario