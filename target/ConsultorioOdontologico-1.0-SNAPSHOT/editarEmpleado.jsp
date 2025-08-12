<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Persona"%>
<%@page import="logica.Secretario"%>
<%@page import="java.util.Map"%>
<%@page import="logica.TipoDocumento"%>
<%@page import="logica.TipoSangre"%>
<%@page import="logica.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="logica.Odontologo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<!-- Traigo el Tipo de Empleado que le guarde como atributo a la request del Cliente -->

<%    /*Si el atributo 'tipoEmpleado' existe ya que previamente fue creado, gracias a 
request.setAttribute("tipoEmpleado", "odontologo"); Como existe, entonces en
el request.getAttribute() retornara un valor de tipo Object, que es el tipo de dato
mas generico. Pero si se ha almacenado un valor de un tipo más específico 
(por ejemplo, un String o un Integer) en el atributo, es necesario realizar 
un CASTEO para poder utilizarlo como tal.
De NO ESTAR CREADO PREVIAMENTE la variable 'tipoEmpleado', 
como el atributo solicitado no existe, request.getAttribute() devuelve NULL. */

 /*Con esto, mostrare el Tipo de Empleado a Editar en el Titulo del JSP*/
    String tipoEmpleado = (String) request.getAttribute("tipoEmpleado");

    /*Tomo al 'empleado' (Que puede ser Odontologo o Secretario) y lo paso al 
    TIPO Persona. Ya que Persona es el Generico de Odontologo y Secretario, 
    podre asi en cada input trabajar con el Objeto sin
    importar el TIPO de EMPLEADO que sea*/
    Persona persona = (Persona) request.getAttribute("empleado");

    // Variables específicas según el tipo
    Odontologo odonto = null;
    Secretario secre = null;

    if ("Odontologo".equals(tipoEmpleado)) {

        odonto = (Odontologo) request.getAttribute("empleado");

    } else if ("Secretario".equals(tipoEmpleado)) {

        secre = (Secretario) request.getAttribute("empleado");
    }

%>


<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Editar <%=tipoEmpleado%></h1>
</div>

<!--
El 'onsubmit="return validarFormulario()" ' capta el formulario antes de enviarlo Servlet para asi, 
mediante el JS controlar/validar que todo lo que se ingreso a los inputs y combos
este correcto. Manda cada dato ingresado en los inputs y combos al metodo validarFormulario()
del JS, donde ahi se haran los controles.
En caso de que por alguna razon la validacion del JS falle, aun asi el Servlet,
tendra su propia validacion. Y en caso de encontrar error, se mostrara la advertencia
en este mismo .jsp, al lado del input o combo correspondiente.
-->
<form id="formEditarEmpleado" class="user"> <!--onsubmit="return validarFormulario()"-->

    <!--Con esto mostrare cada MENSAJE DE ERROR/ADVERTENCIA que viene desde el SERVLET,
    el cual se mostrara al lado de cada input o combo. Solo si NO USO JSON.
    Pero en este caso, USARE JSON, el cual le llagara al JS, y este se encarga de 
    mostrar cada error al lado de cada input-->
    <!--Map<String, String> erroresAlEditar = (Map<String, String>) request.getAttribute("erroresAlEditar");-->


    <!-- Este campo esta oculto, gracias al "hidden", 
    aqui estara el id del Usuario que se muestra para Editar.
    Si pongo: usu.getIdUsuario() sin usar el = y teniendo ; .Esto no dejara luego que el fetch
    pueda ver el Id del Usuario, y si no puede verlo, no puede acceder al Usuario
    en la BD para poder Editar. Provocando que siempre salte "Error al Actualizar".
    Por ende, la ediccion jamas ocurre.
    Ademas, el name de cada campo o input, debe ser igual al nombre de cada atributo
    de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
    al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
    <input type="hidden" id="idPersona" name="idPersona" value="<%=persona.getId()%>">
    <input type="hidden" name="tipoEmpleado" id="tipoEmpleado" 
           value="<%=tipoEmpleado%>"> 

    <!-- Campos comunes entre Secretario y Odontologo -->
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Nombre:</label>
            <input type="text" class="form-control form-control-user" id="nombre"
                   name="nombre" placeholder="Nombre"

                   value="<%= request.getParameter("nombre") != null
                           ? request.getParameter("nombre") : persona.getNombre()%> ">


            <!--<span class="error" id="error-nombre"></span>-->
            <div class="error" id="error-nombre"></div>


        </div>
        <div class="col-sm-3">
            <label>Apellido:</label>
            <input type="text" class="form-control form-control-user" id="apellido"
                   name="apellido" placeholder="Apellido"

                   value="<%= request.getParameter("apellido") != null
                           ? request.getParameter("apellido") : persona.getApellido()%>">


            <!--<span class="error" id="error-apellido"></span>-->
            <div class="error" id="error-apellido"></div>


        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <% List<TipoDocumento> listaTiposDocumentos
                        = (List<TipoDocumento>) request.getAttribute("listaTiposDocumentos");

                /*Con esto al enviar al Servlet, este mira si se selecciono algo en el Combo.
                Si es DISTINTO de NULL, dara TRUE, osea que SI se SELECCIONO 
                algo en el Combo, entonces, el TipoDoc
                del Objeto sera request.getParameter("tipoDoc"), una opcion del combo.
                De lo contrario, si NO ES DISTINTO de NULL, dara FALSE, 
                o sea que NO se SELECCIONO nada del combo,
                entonces, el TipoDoc del Objeto sera 
                String.valueOf(persona.getTipoDocumento().getIdTipoDocumento()), es decir
                el tendra el TipoDoc que ya traia el Objeto desde antes de Editar.*/
                String tipoDocSeleccionado = request.getParameter("tipoDoc") != null
                        ? request.getParameter("tipoDoc")
                        : String.valueOf(persona.getTipoDocumento().getIdTipoDocumento());%>


            <label>Tipo de Doc. de Identidad:</label>
            <select id="tipoDoc" class="form-control" name="tipoDoc">


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
                <option value="" disabled <%= tipoDocSeleccionado == null
                        || tipoDocSeleccionado.isEmpty()
                        ? "selected" : ""%>>Seleccione una opción</option>


                <% for (TipoDocumento tipoDoc : listaTiposDocumentos) {%>

                <!--
                - Se generara una opción <option> por cada TipoDoc.
                - El value (value="tipo.getIdTipoDoc()") es el ID del tipo de doc.
                - Si el ID de este tipo coincide con el valor de 
                tipoDocSeleccionado, se marca como "selected" para que 
                aparezca como la opción elegida en el combo.
                - El texto visible es el nombre del tipo de doc.
                -->
                <option value="<%= tipoDoc.getIdTipoDocumento()%>"
                        <%= String.valueOf(tipoDoc.getIdTipoDocumento()).
                                equals(tipoDocSeleccionado) ? "selected" : ""%>>
                    <%= tipoDoc.getTipoDoc()%>
                </option>
                <% }%>
            </select>


            <!--<span class="error" id="error-tipoDoc"></span>-->
            <div class="error" id="error-tipoDoc"></div>


        </div>
        <div class="col-sm-3">
            <label>Nro. de DNI:</label>
            <input type="text" class="form-control form-control-user" id="dni"
                   name="dni" placeholder="00.000.000"

                   value="<%= request.getParameter("dni") != null
                           ? request.getParameter("dni") : persona.getDni()%>">


            <!--<span class="error" id="error-dni"></span>-->
            <div class="error" id="error-dni"></div>


        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Teléfono:</label>
            <input type="text" class="form-control form-control-user" id="telefono"
                   name="telefono" placeholder="Teléfono"

                   value="<%= request.getParameter("telefono") != null
                           ? request.getParameter("telefono") : persona.getTelefono()%>">

            <!--<span class="error" id="error-telefono"></span>-->
            <div class="error" id="error-telefono"></div>


        </div>
        <div class="col-sm-3">
            <label>Dirección:</label>
            <input type="text" class="form-control form-control-user" id="direccion"
                   name="direccion" placeholder="Dirección"

                   value="<%= request.getParameter("direccion") != null
                           ? request.getParameter("direccion") : persona.getDireccion()%>">


        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Fecha de Nacimiento:</label>
            <!-- Con esto logro convertir de Date a Date SIMPLE
            y ultimo a String, la Fecha que llega desde la BD.
            Como llega y se muestra la fecha: Wed Feb 12 00:00:00 ART 1986,
            Pero con SimpleDateFormat, le doy un formato mas SIMPLE
            a la Fecha, y la paso de Date a String, 
            entonces me queda: 12/02/1986-->
            <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = sdf.format(persona.getFecha_nac());%>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                   class="form-control form-control-user"
                   value="<%=fechaFormateada%>">

            <!--<span class="error" id="error-fechaNacimiento"></span>-->
            <div class="error" id="error-fechaNacimiento"></div>


        </div>
        <div class="col-sm-3">
            <%
                /*Traigo al Lista de TipoSangre que guarde como atributo en la request*/
                List<TipoSangre> listaTiposSangre
                        = (List<TipoSangre>) request.getAttribute("listaTiposSangre");

                /*Con esto al enviar al Servlet, este mira si se selecciono algo en el Combo.
                Si es DISTINTO de NULL, dara TRUE, osea que SI se SELECCIONO 
                algo en el Combo, entonces, el TipoSangre
                del Objeto sera request.getParameter("tipo_Sangre"), una opcion del combo.
                De lo contrario, si NO ES DISTINTO de NULL, dara FALSE, 
                o sea que NO se SELECCIONO nada del combo,
                entonces, el TipoSangre del Objeto sera 
                String.valueOf(persona.getTipoSangre().getIdTipoSangre()), es decir
                el tendra el TipoSangre que ya traia el Objeto desde antes de Editar.*/
                String tipoSangreSeleccionado = request.getParameter("tipo_Sangre") != null
                        ? request.getParameter("tipo_Sangre")
                        : String.valueOf(persona.getTipoSangre().getIdTipoSangre());%>

            <label>Tipo de Sangre:</label>
            <select id="tipo_Sangre" class="form-control" name="tipo_Sangre" >

                <!--Es la primera opción del combo, la que no tiene valor (value="") 
                y está deshabilitada para que no se pueda elegir como válida.
                Si tipoSangreSeleccionado es null, entonces esta opción se marca 
                como seleccionada por defecto.
                
                Si el usuario no seleccionó nada y a su vez la persona que 
                llega para ser Editada tampoco tiene 
                tipo de sangre, entonces sí: se seleccionará la opción 
                "Seleccione una opción".
                Pero si la persona sí tiene tipo de sangre (es decir, estás 
                editando alguien que ya tiene un tipo de sangre cargado), 
                se mostrará seleccionada la opción correspondiente a ese 
                tipo de sangre.-->
                <option value="" disabled <%= tipoSangreSeleccionado == null
                        || tipoSangreSeleccionado.isEmpty()
                        ? "selected" : ""%>>Seleccione una opción</option>

                <% for (TipoSangre tipo : listaTiposSangre) {%>
                <!--
                - Se generara una opción <option> por cada TipoSangre.
                - El value (value="tipo.getIdTipoSangre()") es el ID del tipo de sangre.
                - Si el ID de este tipo coincide con el valor de 
                tipoSangreSeleccionado, se marca como "selected" para que 
                aparezca como la opción elegida en el combo.
                - El texto visible es el nombre del tipo de sangre.
                -->
                <option value="<%= tipo.getIdTipoSangre()%>"
                        <%= String.valueOf(tipo.getIdTipoSangre()).
                                equals(tipoSangreSeleccionado) ? "selected" : ""%>>
                    <%= tipo.getTipo_Sangre()%>
                </option>
                <% }%>
            </select>

            <!--<span class="error" id="error-tipo_Sangre"></span>-->
            <div class="error" id="error-tipo_Sangre"></div>

        </div>
    </div>
    <br>
    <!-- Usuario y Campo específico (Especialidad o Sector) -->
    <div class="form-group row">
        <div class="col-sm-3">
            <% List<Usuario> listaUsuarios
                        = (List<Usuario>) request.getAttribute("listaUsuarios");

                /*Con esto al enviar al Servlet, este mira si se selecciono algo en el Combo.
                Si es DISTINTO de NULL, dara TRUE, osea que SI se SELECCIONO 
                algo en el Combo, entonces, el Usuario
                del Objeto sera request.getParameter("usuario"), una opcion del combo.
                De lo contrario, si NO ES DISTINTO de NULL, dara FALSE, 
                o sea que NO se SELECCIONO nada del combo,
                entonces, el Usuario del Objeto sera 
                String.valueOf(odonto.getUsuario().getIdUsuario()); solo si lo 
                que llego es un Objeto Odontologo, es decir el combo seleccionara
                el Usuario que ya traia el Objeto desde antes de Editar.
                Y lo mismo ocurrira, si lo que llega es un Secretario.
                 */
                String usuarioSeleccionado = "";
                if (odonto != null && secre == null) {

                    usuarioSeleccionado = request.getParameter("usuario") != null
                            ? request.getParameter("usuario")
                            : String.valueOf(odonto.getUsuario().getIdUsuario());

                } else if (secre != null && odonto == null) {

                    usuarioSeleccionado = request.getParameter("usuario") != null
                            ? request.getParameter("usuario")
                            : String.valueOf(secre.getUsuario().getIdUsuario());
                }
            %>
            <label>Usuario:</label>
            <select class="form-control" name="usuario" id="usuario" > 
                <!--required:
                Habia puesto esto en cada COMBO, ya que queria controlar que se 
                elija alguna opcion del combo si o si.
                Ya que en su momento sin tener la validacion en el Servlet de que
                si o si se deba elejir algo del combo. Al enviar al Servlet saltaba error.
                Por eso use el required. Para obligar a escoger una opcion.
                
                Una vez puse validacion en el Servlet para controlar el Error,
                y saque el required.
                Ya que me pasaba que si no se elejia una opcion del combo
                el form al enviar al Servlet, me saltaba el mensaje de error 
                "Campo Requerido. Elija una opcion"
                ===========================================================-->

                <!--Es la primera opción del combo, la que no tiene valor, 
                o sea (value=""), y está deshabilitada para que no se pueda elegir 
                como válida.
                Si tipoUsuarioSeleccionado es null, entonces esta opción 
                se mostrara por defecto en el combo, de lo contrario, se 
                mostrara el usuario con el que ya viene el Objeto.
                
                Si el cliente no seleccionó nada y a su vez la persona que 
                llega para ser Editada tampoco tiene cargado
                usuario, entonces sí: se mostrara la opción 
                "Seleccione una opción".
                Pero si la persona sí tiene usuario (es decir, estás 
                editando alguien que ya tiene un usuario cargado), 
                se mostrará seleccionada la NUEVA opción usuario que 
                elejio el cliente.-->
                <option value="" disabled <%=usuarioSeleccionado == null
                        || usuarioSeleccionado.isEmpty()
                        ? "selected" : ""%>>Seleccione una opción</option>

                <% for (Usuario usu : listaUsuarios) {%>
                <!--
                - Se generara una opción <option> por cada Usuario.
                - El value (value="tipo.getIdUsuario()") es el ID del Usuario.
                - Si el ID usuario que viene de la lista coincide con el valor de 
                usuarioSeleccionado, se marca como "selected" para que 
                aparezca como la opción elegida en el combo.
                - El texto visible es el nombre del usuario, o sea 'usu.getNombreUsuario()'.
                -->
                <option value="<%= usu.getIdUsuario()%>"
                        <%= String.valueOf(usu.getIdUsuario()).
                                equals(usuarioSeleccionado) ? "selected" : ""%>>
                    <%= usu.getNombreUsuario()%>
                </option>


                <% }%>
            </select>

            <!--<span class="error" id="error-usuario"></span>-->
            <div class="error" id="error-usuario"></div>

        </div>

        <div class="col-sm-3" id="campo-especifico">
            <!-- Dependiendo del TipoEmpleado (Odontologo o Secretario) se mostrara
            el input Especialidad o Sector. -->
            <script>
                const contenedor = document.getElementById("campo-especifico");
                <% if (odonto != null) {%>

                contenedor.innerHTML = `
                <label>Especialidad:</label>
                <input type="text" class="form-control" name="especialidad" 
                   id="especialidad" placeholder="Especialidad"
                   value="<%=odonto != null ? odonto.getEspecialidad() : ""%>">`;
                <% } else if (secre != null) {%>

                contenedor.innerHTML = `
                <label>Sector en la Clínica:</label>
                <input type="text" class="form-control" name="sector" 
                   id="sector" placeholder="Sector"
                   value="<%=secre != null ? secre.getSector() : ""%>">`;
                <% }%>
            </script>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <br>
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Guardar Cambios</button>
        </div>
    </div>

    <hr>

</form>

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
    document.getElementById("formEditarEmpleado").addEventListener("submit", function (e) {
        e.preventDefault();

        // Limpiar errores anteriores
        document.querySelectorAll(".error").forEach(span => span.textContent = "");

        const tipoEmpleado = document.getElementById("tipoEmpleado").value;

        // Crear objeto con datos comunes
        const datos = {
            id: document.getElementById("idPersona").value,
            tipoEmpleado: tipoEmpleado,
            nombre: document.getElementById("nombre").value,
            apellido: document.getElementById("apellido").value,
            dni: document.getElementById("dni").value,
            telefono: document.getElementById("telefono").value,
            direccion: document.getElementById("direccion").value,
            fechaNacimiento: document.getElementById("fechaNacimiento").value,
            tipoDoc: document.getElementById("tipoDoc").value,
            tipo_Sangre: document.getElementById("tipo_Sangre").value,
            usuario: document.getElementById("usuario").value
        };

        // Campos extra según tipo
        if (tipoEmpleado === "Odontologo") {
            datos.especialidad = document.getElementById("especialidad").value;
        } else if (tipoEmpleado === "Secretario") {
            datos.sector = document.getElementById("sector").value;
        }


        fetch("SvEmpleado?id=" + datos.id, {
            method: "PUT",
            body: JSON.stringify(datos),
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
                        text: `${tipoEmpleado} editado correctamente.`,
                        icon: "success",
                        confirmButtonText: "Aceptar",
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    }).then(() => {
                        // Redirigir después del mensaje
                        if (tipoEmpleado === "Odontologo") {
                            window.location.href = "SvEmpleado?accion=listarOdonto";
                        } else if (tipoEmpleado === "Secretario") {
                            window.location.href = "SvEmpleado?accion=listarSecre";
                        }
                    });
                })
                .catch(error => {
                    console.error("Error en fetch:", error);
                    Swal.fire({
                        title: "Error",
                        text: "Hubo un problema al editar el empleado.",
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

<!-- FUNCIONA PERO QUIERO QUE LOS MENSAJES SE MUESTREN MAS BONITOS. 
JavaScript para enviar con fetch y mostrar errores
El truco está en limpiar errores previos, luego mostrar los nuevos que 
vengan desde el Servlet.
<script>
    document.getElementById("formEditarEmpleado").addEventListener("submit", function (e) {

        e.preventDefault();

        // Limpiar errores anteriores
        document.querySelectorAll(".error").forEach(span => span.textContent = "");

        const tipoEmpleado = document.getElementById("tipoEmpleado").value;

        // Crear el Objeto con los DATOS COMUNES del formulario
        const datos = {
            id: document.getElementById("idPersona").value,
            tipoEmpleado: tipoEmpleado,
            nombre: document.getElementById("nombre").value,
            apellido: document.getElementById("apellido").value,
            dni: document.getElementById("dni").value,
            telefono: document.getElementById("telefono").value,
            direccion: document.getElementById("direccion").value,
            fechaNacimiento: document.getElementById("fechaNacimiento").value,
            tipoDoc: document.getElementById("tipoDoc").value,
            tipo_Sangre: document.getElementById("tipo_Sangre").value,
            usuario: document.getElementById("usuario").value

        };

        // Agrego al Objeto los campos SEGUN el TIPOEMPLEADO:
        if (tipoEmpleado === "Odontologo") {
            datos.especialidad = document.getElementById("especialidad").value;

        } else if (tipoEmpleado === "Secretario") {
            datos.sector = document.getElementById("sector").value;
        }


        fetch("SvEmpleado?id=" + datos.id, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(datos)
        })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(errores => {
                            throw errores;
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    alert(data.mensaje); // Ej: "Odontologo editado correctamente"
                })
                .catch(errores => {
                    // Mostrar cada error junto al campo correspondiente
                    for (const campo in errores) {
                        const spanError = document.getElementById(`error-campo}`);
                        if (spanError) {
                            spanError.textContent = errores[campo];
                        }
                    }
                });
    });

</script>-->


<!-- Funciona para mostrar el mensaje de Exito al editar un Odontologo.
Pero no muestra nunca los errores al lado de cada input en caso de haber.
Y solo lanza error 404 Bad Request.
<script>
    document.getElementById("formEditarEmpleado").addEventListener("submit", function (e) {
        
        e.preventDefault();

        // Limpiar errores anteriores
        document.querySelectorAll(".error").forEach(span => span.textContent = "");

        const tipoEmpleado = document.getElementById("tipoEmpleado").value;

        // Crear el Objeto con los DATOS COMUNES del formulario
        const datos = {
            id: document.getElementById("idPersona").value,
            tipoEmpleado: tipoEmpleado,
            nombre: document.getElementById("nombre").value,
            apellido: document.getElementById("apellido").value,
            dni: document.getElementById("dni").value,
            telefono: document.getElementById("telefono").value,
            direccion: document.getElementById("direccion").value,
            fechaNacimiento: document.getElementById("fechaNacimiento").value,
            tipoDoc: document.getElementById("tipoDoc").value,
            tipo_Sangre: document.getElementById("tipo_Sangre").value,
            usuario: document.getElementById("usuario").value

        };

        // Agrego al Objeto los campos SEGUN el TIPOEMPLEADO:
        if (tipoEmpleado === "Odontologo") {
            datos.especialidad = document.getElementById("especialidad").value;

        } else if (tipoEmpleado === "Secretario") {
            datos.sector = document.getElementById("sector").value;
        }

        fetch("SvEmpleado?id=" + datos.id, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(datos)
        })
                .then(async response => {
                    if (!response.ok) {
                        return response.json().then(errores => {
                            throw errores;
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.status === "success") {
                        Swal.fire({
                            icon: "success",
                            title: "Éxito",
                            text: "Empleado editado correctamente", // Texto fijo
                            confirmButtonColor: "#3085d6",
                            confirmButtonText: "Aceptar"
                        }).then(() => {
                            if (data.tipoEmpleado === "Odontologo") {
                                window.location.href = "SvEmpleado?accion=listarOdonto";
                            } else {
                                window.location.href = "SvEmpleado?accion=listarSecre";
                            }
                        });
                    }
                })
                .catch(errores => {
                    if (typeof errores === "object") {
                        for (const campo in errores) {
                            const spanError = document.getElementById(`error-campo}`);
                            if (spanError) {
                                spanError.textContent = errores[campo];
                            }
                        }
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error",
                            text: "No se pudo actualizar el empleado",
                            confirmButtonColor: "#d33",
                            confirmButtonText: "Cerrar"
                        });
                    }
                });
    });
</script>-->





<%@include file="components/bodyfinal.jsp"%>



