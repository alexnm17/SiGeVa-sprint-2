import React from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import UsuariosList from "./UsuariosList";
import 'bootstrap/dist/css/bootstrap.min.css'

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
                    <button class="btn btn-success">Crear usuario</button>
                </a>
                <UsuariosList />
            </div>
        </div>
    );
}

export default GestionCentroSalud