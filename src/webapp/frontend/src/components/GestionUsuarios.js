import React from "react";
import { Breadcrumb, Button } from "react-bootstrap"

const GestionCentroSalud = () => {
    return (
        <div>
			<Breadcrumb style={{margin:30}}>
                <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                <Breadcrumb.Item href="/Administrador/GestionUsuarios">Gestion de Usuarios</Breadcrumb.Item>
            </Breadcrumb>
            <div style={{ marginBotton: 20 }}>
                <h5>Estas en la página de Gestion de Usuarios</h5>
            </div>
            <div>
                <p>Selecciona la acción que quieres realizar: </p>
                <a href="/administrador/GestionUsuarios/CrearUsuario">
                    <Button style={{ marginRight: 15 }}>Crear usuario</Button>
                </a>
                <Button variant="secondary" style={{ marginRight: 15 }}>Modificar usuario</Button>
                <Button variant="secondary" style={{ marginRight: 15 }}>Borrar usuario</Button>
            </div>
        </div>
    );
}

export default GestionCentroSalud