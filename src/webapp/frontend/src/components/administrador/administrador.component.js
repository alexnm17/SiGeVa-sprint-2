import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"

class Administrador extends Component {

    componentDidMount() {

    }

    render() {
        return (
            <div>
			<Breadcrumb style={{margin:30}}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                </Breadcrumb>
                <div>
                    <p>Selecciona la acción que quieres realizar: </p>
                    <a href="/administrador/GestionUsuarios">
                        <Button style={{ marginRight: 15 }}>Gestión de Usuarios</Button>
                    </a>
                    <a href="/administrador/GestionCentrosSalud">
                        <Button style={{ marginRight: 15 }}>Gestión de Centros de Salud</Button>
                    </a>
                    <a href="/administrador/GestionDefinicionDeCitas">
                        <Button style={{ marginRight: 15 }}>Gestión de Definición de Citas</Button>
                    </a>
                </div>
            </div>
        );
    }
}

export default Administrador