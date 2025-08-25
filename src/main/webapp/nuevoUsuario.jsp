<%@page import="java.util.Map"%>
<%@page import="logica.Rol"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Nuevo Usuario</h1>
</div>

<form onsubmit="return validarFormularioUsuario()" class="user" action="SvUsuarios" method="POST">

    <!--Con esto mostrare cada MENSAJE DE ERROR/ADVERTENCIA que viene desde el SERVLET,
    el cual se mostrara al lado de cada input o combo-->
    <% Map<String, String> erroresUsuario = (Map<String, String>) request.getAttribute("erroresUsuario");%>

    <div class="form-group row">
        <div class="col-sm-4">
            <label>Nombre de Usuario:</label>
            <input type="text" class="form-control form-control-user" id="nombreUsuario" name="nombreUsuario"
                   placeholder="Nombre Usuario">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-nombreUsuario"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (erroresUsuario != null && erroresUsuario.containsKey("nombreUsuario")) {%>
            <div class="text-danger"><%= erroresUsuario.get("nombreUsuario")%></div>
            <% }%>

        </div>

        <div class="col-sm-4">
            <label>Contraseña:</label>
            <div class="input-group">
                <input type="password" class="form-control form-control-user" 
                       id="contra" name="contrasenia" placeholder="Contraseña">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-contrasenia"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (erroresUsuario != null && erroresUsuario.containsKey("contrasenia")) {%>
            <div class="text-danger"><%= erroresUsuario.get("contrasenia")%></div>
            <% }%>

        </div>
    </div>

    <div class="form-group row mt-2">
        <div class="col-sm-4">
            <% List<Rol> listaRoles
                        = (List<Rol>) request.getAttribute("listaRoles"); %>
            <label>Tipo de Rol:</label>
            <select id="rol" class="form-control" name="rol" >
                <option value="" disabled selected>Seleccione una opcion</option>
                <% for (Rol tipo : listaRoles) {%>
                <option value="<%= tipo.getIdRol()%>"
                        <%= String.valueOf(tipo.getIdRol()).equals(request.getParameter("rol")) ? "selected" : ""%>>
                    <%= tipo.getRol()%>
                </option>
                <% }%>
            </select>
            
            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-rol"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (erroresUsuario != null && erroresUsuario.containsKey("rol")) {%>
            <div class="text-danger"><%= erroresUsuario.get("rol")%></div>
            <% }%>
            
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
        const passwordInput = document.getElementById("contra");
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



<!--Validación extra con JavaScript (opcional pero más visual)
Si querés mostrar advertencias antes de enviar el formulario, podés hacer algo así:-->
<script>
    function validarFormularioUsuario() {

        // Borrar errores previos
        const errores = document.querySelectorAll(".text-danger");

        errores.forEach(e => e.innerText = "");
        let valido = true;

        const nombreUsuario = document.getElementById("nombreUsuario").value.trim();
        const contrasenia = document.getElementById("contrasenia").value.trim();
        const rol = document.getElementById("rol").value;


        if (!/^[a-zA-Z0-9.,]{8,}$/.test(nombreUsuario) || nombreUsuario === "") {
            document.getElementById("error-nombreUsuario").
            innerText = "Ingrese un usuario.\nMínino 8 caracteres.\nSin espacios";
            valido = false;
        }

        if (!/^(?=.*[A-Za-z])(?=.*\\d).{8,}$/.test(contrasenia) || contrasenia === "") {
            document.getElementById("error-contrasenia").
            innerText = "Ingrese una contraseña.\nMínino 8 caracteres.\nSin espacios";
            valido = false;
        }

        if (rol === "") {
            document.getElementById("error-rol").innerText = "Seleccione un rol";
            valido = false;
        }

        return valido;
    }
</script>


<%@include file="components/bodyfinal.jsp"%>
