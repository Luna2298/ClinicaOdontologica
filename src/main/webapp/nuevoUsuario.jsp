<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Nuevo Usuario</h1>
</div>

<form class="user" action="SvUsuarios" method="POST">
    <div class="form-group row">
        <div class="col-sm-4">
            <label>Nombre de Usuario:</label>
            <input type="text" class="form-control form-control-user" id="usuario" name="nombreusuario"
                   placeholder="Nombre Usuario">
        </div>

        <div class="col-sm-4">
            <label>Contraseña:</label>
            <div class="input-group">
                <input type="password" class="form-control form-control-user" 
                       id="contrasenia" name="contra" placeholder="Contraseña">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group row mt-2">
        <div class="col-sm-4">
            <label>Rol:</label>
            <input type="text" class="form-control form-control-user" id="rol" name="rol"
                   placeholder="Rol">
        </div>

        <div class="col-sm-4 d-flex align-items-end">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Crear Usuario
            </button>
        </div>
    </div>

    <hr>
</form>

<script>
    /*Aqui esta el codigo JavaScript para poder Ver y Ocultar la Contraseña de cada Usuario,
     tanto en nuevoUsuario.jsp como en editarUsuario.jsp */
    document.getElementById("togglePassword").addEventListener("click", function () {
        const passwordInput = document.getElementById("contrasenia");
        const icon = this.querySelector("i");

        if (passwordInput.type === "password") {
            passwordInput.type = "text";
            icon.classList.remove("fa-eye");
            icon.classList.add("fa-eye-slash");
        } else {
            passwordInput.type = "password";
            icon.classList.remove("fa-eye-slash");
            icon.classList.add("fa-eye");
        }
    });
</script>  


<%@include file="components/bodyfinal.jsp"%>
