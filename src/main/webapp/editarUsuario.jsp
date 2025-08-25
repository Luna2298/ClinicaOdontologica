<%@page import="logica.Rol"%>
<%@page import="java.util.List"%>
<%@page import="logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Editar Usuario</h1>
</div>
<!-- Traigo el Usuario que le guarde como atributo a la session del Cliente -->
<% Usuario usu = (Usuario) request.getSession().getAttribute("usuEditar");%>

<form class="user" id="form-editar-usuario" >

    <!-- Este campo esta oculto, gracias al "hidden", 
    aqui estara el id del Usuario que se muestra para Editar.
    Si pongo: usu.getIdUsuario() sin usar el = y teniendo ; .Esto no dejara luego que el fetch
    pueda ver el Id del Usuario, y si no puede verlo, no puede acceder al Usuario
    en la BD para poder Editar. Provocando que siempre salte "Error al Actualizar".
    Por ende, la ediccion jamas ocurre.
    Ademas, el name de cada campo o input, debe ser igual al nombre de cada atributo
    de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
    al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
    <input type="hidden" id="idUsuario" name="idUsuario" value="<%=usu.getIdUsuario()%>">

    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Nombre Usuario:</label>
            <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
            de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
            al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
            <input type="text" class="form-control form-control-user" name="nombreUsuario"
                   id="nombreUsuario" placeholder="Nombre Usuario" 
                   value=
                   "<%= request.getParameter("nombreUsuario") != null
                           ? request.getParameter("nombreUsuario") : usu.getNombreUsuario()%>">
            <!--usu.getNombreUsuario()-->

            <!--Aca se muestra los mensaje de Error/Advertencia que llegan desde
            el Servlet en formato JSON y van al JS. 
            El JS se encarga de mostrar los mensajes al lado de cada input.-->
            <div class="error" id="error-nombreUsuario"></div>
        </div>
            
        <div class="col-sm-3 mb-3 col-sm-3">
            <% List<Rol> listaRoles
                        = (List<Rol>) request.getAttribute("listaRoles");

                /*Con esto al enviar al Servlet, este mira si se selecciono algo en el Combo.
                Si es DISTINTO de NULL, dara TRUE, osea que SI se SELECCIONO 
                algo en el Combo, entonces, el TipoDoc
                del Objeto sera request.getParameter("tipoDoc"), una opcion del combo.
                De lo contrario, si NO ES DISTINTO de NULL, dara FALSE, 
                o sea que NO se SELECCIONO nada del combo,
                entonces, el TipoDoc del Objeto sera 
                String.valueOf(persona.getTipoDocumento().getIdTipoDocumento()), es decir
                el tendra el TipoDoc que ya traia el Objeto desde antes de Editar.*/
                String rolSeleccionado = request.getParameter("rol") != null
                        ? request.getParameter("rol")
                        : String.valueOf(usu.getTipoRol().getIdRol());%>


            <label>Tipo de Rol:</label>
            <select id="rol" class="form-control" name="rol">

                <!--Es la primera opción del combo, la que no tiene valor (value="") 
                y está deshabilitada para que no se pueda elegir como válida.
                Si tipoDocSeleccionado es null, entonces esta opción se marca 
                como seleccionada por defecto.
                
                Si el usuario no seleccionó nada y a su vez la persona que 
                llega para ser Editada tampoco tiene 
                tipo de doc, entonces sí: se seleccionará la opción 
                "Seleccione una opción".
                Pero si la persona sí tiene tipo de doc (es decir, estás 
                editando alguien que ya tiene un tipo de doc cargado), 
                se mostrará seleccionada la opción correspondiente a ese 
                tipo de doc.-->
                <option value="" disabled <%= rolSeleccionado == null
                        || rolSeleccionado.isEmpty()
                        ? "selected" : ""%>>Seleccione una opción</option>


                <% for (Rol tipoRol : listaRoles) {%>

                <!--
                - Se generara una opción <option> por cada TipoDoc.
                - El value (value="tipo.getIdTipoDoc()") es el ID del tipo de doc.
                - Si el ID de este tipo coincide con el valor de 
                tipoDocSeleccionado, se marca como "selected" para que 
                aparezca como la opción elegida en el combo.
                - El texto visible es el nombre del tipo de doc.
                -->
                <option value="<%= tipoRol.getIdRol()%>"
                        <%= String.valueOf(tipoRol.getIdRol()).
                                equals(rolSeleccionado) ? "selected" : ""%>>
                    <%= tipoRol.getRol()%>
                </option>
                <% }%>
            </select>

            <!--Aca se muestra los mensaje de Error/Advertencia que llegan desde
            el Servlet en formato JSON y van al JS. 
            El JS se encarga de mostrar los mensajes al lado de cada input.-->
            <div class="error" id="error-rol"></div>
        </div>
    </div>


    <div class="form-group row"> 

        <div class="col-sm-12 mb-3">
            <!-- Botón que habilita/deshabilita los campos de contraseña -->
            <button type="button" class="btn btn-warning mb-3" id="btnCambiarContrasenia">
                Cambiar contraseña
            </button>
        </div>

        <!-- Input con el boton Ver y Ocultar Contraseña -->
        <div class="mb-3 col-sm-3">
            <label>Contraseña Actual:</label>
            <div class="input-group">
                <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
                de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
                al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
                <input type="password" class="form-control" id="contrasenia" 
                       name="contrasenia"
                       placeholder="Contraseña Actual"   
                       disabled>
                <div class="input-group-append">
                    <!--una clase común a los botones (ej: class="togglePassword") 
                    y en cada botón agregá un atributo que diga a qué input 
                    pertenece (ej: data-target="contrasenia").
                    Así cada botón controla SOLO su input.-->
                    <button class="btn btn-outline-secondary togglePassword" 
                            data-target="contrasenia" type="button">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>
            <!--Aca se muestra los mensaje de Error/Advertencia que llegan desde
                el Servlet en formato JSON y van al JS. 
                El JS se encarga de mostrar los mensajes al lado de cada input.-->
            <div class="error" id="error-contrasenia"></div>
        </div>


        <div class="mb-3 col-sm-3">
            <label>Contraseña Nueva:</label>
            <div class="input-group">
                <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
                de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
                al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
                <input type="password" class="form-control" id="contraseniaNueva" 
                       name="contraseniaNueva"
                       placeholder="Contraseña Nueva" disabled>
                <div class="input-group-append">
                    <!--una clase común a los botones (ej: class="togglePassword") 
                    y en cada botón agregá un atributo que diga a qué input 
                    pertenece (ej: data-target="contraseniaNueva").
                    Así cada botón controla SOLO su input.-->
                    <button class="btn btn-outline-secondary togglePassword" 
                            data-target="contraseniaNueva" type="button">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>
            <!--Aca se muestra los mensaje de Error/Advertencia que llegan desde
                el Servlet en formato JSON y van al JS. 
                El JS se encarga de mostrar los mensajes al lado de cada input.-->
            <div class="error" id="error-contraseniaNueva"></div>
        </div>


        <!-- Input con el boton Ver y Ocultar Contraseña -->
        <div class="mb-3 col-sm-3">
            <label>Confirmar Contraseña:</label>
            <div class="input-group">
                <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
                de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
                al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
                <input type="password" class="form-control" id="contraseniaConfirmar" 
                       name="contraseniaConfirmar"
                       placeholder="Confirmar Contraseña" disabled>
                <div class="input-group-append">
                    <!--una clase común a los botones (ej: class="togglePassword") 
                    y en cada botón agregá un atributo que diga a qué input 
                    pertenece (ej: data-target="contraseniaConfirmar").
                    Así cada botón controla SOLO su input.-->
                    <button class="btn btn-outline-secondary togglePassword" 
                            data-target="contraseniaConfirmar" type="button">
                        <i class="fas fa-eye"></i>
                    </button>
                </div>
            </div>
            <!--Aca se muestra los mensaje de Error/Advertencia que llegan desde
                el Servlet en formato JSON y van al JS. 
                El JS se encarga de mostrar los mensajes al lado de cada input.-->
            <div class="error" id="error-contraseniaConfirmar"></div>
        </div>

    </div>

    <div class="form-group row">

        <div class="col-sm-3">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Guardar Cambios</button>
        </div>

    </div>

    <hr>

</form>


<!--Aqui con ayuda del boton 'btnCambiarContrasenia' Habilito y Deshabilito los
Inputs de Contraseña-->
<script>
    document.getElementById("btnCambiarContrasenia").addEventListener("click", function () {
        const campos = [
            document.getElementById("contrasenia"),
            document.getElementById("contraseniaNueva"),
            document.getElementById("contraseniaConfirmar")
        ];

        const deshabilitado = campos[0].disabled;

        // Alternar entre habilitado/deshabilitado
        campos.forEach(input => input.disabled = !deshabilitado);

        // Cambiar texto del botón según estado
        this.textContent = deshabilitado ? "Cancelar" : "Cambiar contraseña";

        // Si cancela
        if (deshabilitado === false) {

            /*
             Limpiara los campos, o sea lo ingresado en cada input Contraseña 
             se borrara*/
            campos.forEach(input => input.value = "");

            /*
            Y tambien borrara mensajes de error/advertencia en cada 
            input Contraseña.
            Se agregó un array errores con los div de error.
            Recorro ese array y le asigno "" al texto → 
            los mensajes  de error/advertencia desaparecen.*/
            const errores = [
                document.getElementById("error-contrasenia"),
                document.getElementById("error-contraseniaNueva"),
                document.getElementById("error-contraseniaConfirmar")
            ];
            errores.forEach(err => err.textContent = "");
        }
    });
</script>



<script>
    /*
     El ".togglePassword" busca cada elemento que tenga esa clase, o sea cada
     button de cada input contraseña. */
    document.querySelectorAll(".togglePassword").forEach(button => {
        button.addEventListener("click", function () {
            const inputId = this.getAttribute("data-target");
            const input = document.getElementById(inputId);
            const icon = this.querySelector("i");

            if (input.type === "password") {
                input.type = "text";
                icon.classList.remove("fa-eye");
                icon.classList.add("fa-eye-slash");
            } else {
                input.type = "password";
                icon.classList.remove("fa-eye-slash");
                icon.classList.add("fa-eye");
            }
        });
    });
</script>  


<style>
    .error {
        color: red;
        font-size: 1em;
        margin-left: 1em;
        /*Espacio entre el lado izquierdo de la web y el div que muestra el error*/

        margin-top: 0.5em;
        /*Espacio superior, entre el input y el div que muestra el error*/
    }
</style>


<script>
    document.getElementById("form-editar-usuario").addEventListener("submit", function (e) {

        e.preventDefault();

        // Limpiar errores anteriores
        document.querySelectorAll(".error").forEach(span => span.textContent = "");

        // Referencias a los campos de contraseña
        const inputActual = document.getElementById("contrasenia");
        const inputNueva = document.getElementById("contraseniaNueva");
        const inputConfirmar = document.getElementById("contraseniaConfirmar");

        // Crear objeto con datos comunes
        const usuario = {
            idUsuario: document.getElementById("idUsuario").value,
            nombreUsuario: document.getElementById("nombreUsuario").value,
            rol: document.getElementById("rol").value
        };


        /*Solo Obtener y agregar al Objeto Usuario las contraseñas, 
         SOLO SI los inputs de contraseñas están habilitados*/
        if (!inputActual.disabled && !inputNueva.disabled && !inputConfirmar.disabled) {
            usuario.contrasenia = inputActual.value;
            usuario.contraseniaNueva = inputNueva.value;
            usuario.contraseniaConfirmar = inputConfirmar.value;
        }


        fetch("SvUsuarios?id=" + usuario.idUsuario, {
            method: "PUT",
            body: JSON.stringify(usuario),
            headers: {"Content-Type": "application/json"}
        })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => {
                            console.log("Errores recibidos del backend:", err); // <-- debug
                            mostrarErroresEnFormulario(err); // tu función que pone los errores en el DOM
                            throw new Error("Error en validación");
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    // Mensaje bonito con SweetAlert2
                    Swal.fire({
                        title: "¡Edición exitosa!",
                        text: `Usuario editado correctamente.`,
                        icon: "success",
                        confirmButtonText: "Aceptar",
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    }).then(() => {
                        //Luego de mostrar el Mensaje
                        //Redireccionamos al Servlet, por default ejecuta el .doGet(), 
                        //haciendo que este actualice la Lista de Usuario, 
                        //y termina redireccionando a mostrar la Lista.
                        window.location.href = "SvUsuarios?accion=listar";
                    });
                })
                .catch(error => {
                    console.error("Error en fetch:", error);
                    Swal.fire({
                        title: "Error",
                        text: "Hubo un problema al editar el usuario.",
                        icon: "error",
                        confirmButtonText: "Aceptar"
                    });
                });
    });
</script>

<script>
    function mostrarErroresEnFormulario(errores) {
        for (let campo in errores) {
            const span = document.getElementById("error-" + campo);
            if (span) {
                span.textContent = errores[campo];
            }
        }
    }
</script>


<%@include file="components/bodyfinal.jsp"%>

