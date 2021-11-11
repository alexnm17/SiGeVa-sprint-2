import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"

class Administrador extends Component {

    componentDidMount() {

    }

    render() {
        if(localStorage.getItem('rolUsuario')=="Administrador"){
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
        }else{
            return(
                <div>
                    <Breadcrumb style={{margin:30}}>
                            <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                            <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    </Breadcrumb>
                    <p>A esta sección solo pueden acceder los Administradores.</p>
                    <p>Inicie sesión como administrador para continuar.</p>
                </div>
            );
        }
    }
}

export default Administrador