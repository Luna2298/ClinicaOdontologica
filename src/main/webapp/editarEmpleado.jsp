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
<%String empleado = "";
%>
<% if ( "odo".equals(request.getAttribute("odo")) ) {
        
    Odontologo odonto = (Odontologo) request.getAttribute("odo");
    empleado = "Odontólogo";
    
    } else if ( "secretario".equals(request.getAttribute("secretario")) ) {
    
    Secretario secre = (Secretario) request.getAttribute("secretario");
    empleado = "Secretario";
    }
%>


<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Editar <%=empleado %></h1>
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
<form onsubmit="return validarFormulario()" action="SvEmpleado" method="POST">

    <!--Con esto mostrare cada MENSAJE DE ERROR/ADVERTENCIA que viene desde el SERVLET,
    el cual se mostrara al lado de cada input o combo-->
    <% Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");%>
    
    
    <!-- Este campo esta oculto, gracias al "hidden", 
    aqui estara el id del Usuario que se muestra para Editar.
    Si pongo: usu.getIdUsuario() sin usar el = y teniendo ; .Esto no dejara luego que el fetch
    pueda ver el Id del Usuario, y si no puede verlo, no puede acceder al Usuario
    en la BD para poder Editar. Provocando que siempre salte "Error al Actualizar".
    Por ende, la ediccion jamas ocurre.
    Ademas, el name de cada campo o input, debe ser igual al nombre de cada atributo
    de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
    al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
    <input type="hidden" name="id" value="<%=empleado.equals("Secretario") ? secre.getId() : odonto.getId()%>">
    

    <!-- Campos comunes entre Secretario y Odontologo -->
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Nombre:</label>
            <input type="text" class="form-control form-control-user" id="nombre"
                   name="nombre" placeholder="Nombre"

                   value="<%= request.getParameter("nombre") != null
                           ? request.getParameter("nombre") : ""%>">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-nombre"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("nombre")) {%>
            <div class="text-danger"><%= errores.get("nombre")%></div>
            <% }%>
        </div>
        <div class="col-sm-3">
            <label>Apellido:</label>
            <input type="text" class="form-control form-control-user" id="apellido"
                   name="apellido" placeholder="Apellido"

                   value="<%= request.getParameter("apellido") != null
                           ? request.getParameter("apellido") : ""%>">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-apellido"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("apellido")) {%>
            <div class="text-danger"><%= errores.get("apellido")%></div>
            <% } %>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <% List<TipoDocumento> listaTiposDocumentos
                        = (List<TipoDocumento>) request.getAttribute("listaTiposDocumentos"); %>
            <label>Tipo de Doc. de Identidad:</label>
            <select id="tipoDoc" class="form-control" name="tipoDoc">
                <option value="" disabled selected>Seleccione una opcion</option>
                <% for (TipoDocumento tipoDoc : listaTiposDocumentos) {%>
                <option value="<%= tipoDoc.getIdTipoDocumento()%>"
                        <%= String.valueOf(tipoDoc.getIdTipoDocumento()).equals(request.getParameter("tipoDoc")) ? "selected" : ""%>>
                    <%= tipoDoc.getTipoDoc()%>
                </option>
                <% }%>
            </select>

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-tipoDoc"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("tipoDoc")) {%>
            <div class="text-danger"><%= errores.get("tipoDoc")%></div>
            <% }%>
        </div>
        <div class="col-sm-3">
            <label>Nro. de DNI:</label>
            <input type="text" class="form-control form-control-user" id="dni"
                   name="dni" placeholder="00.000.000"

                   value="<%= request.getParameter("dni") != null
                           ? request.getParameter("dni") : ""%>">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-dni"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("dni")) {%>
            <div class="text-danger"><%= errores.get("dni")%></div>
            <% }%>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Teléfono:</label>
            <input type="text" class="form-control form-control-user" id="telefono"
                   name="telefono" placeholder="Teléfono"

                   value="<%= request.getParameter("telefono") != null
                           ? request.getParameter("telefono") : ""%>">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-telefono"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("telefono")) {%>
            <div class="text-danger"><%= errores.get("telefono")%></div>
            <% }%>

        </div>
        <div class="col-sm-3">
            <label>Dirección:</label>
            <input type="text" class="form-control form-control-user" id="direccion"
                   name="direccion" placeholder="Dirección"

                   value="<%= request.getParameter("direccion") != null
                           ? request.getParameter("direccion") : ""%>">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-direccion"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("direccion")) {%>
            <div class="text-danger"><%= errores.get("direccion")%></div>
            <% } %>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Fecha de Nacimiento:</label>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                   class="form-control form-control-user">

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-fechaNacimiento"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("fechaNacimiento")) {%>
            <div class="text-danger"><%= errores.get("fechaNacimiento")%></div>
            <% } %>
        </div>
        <div class="col-sm-3">
            <% List<TipoSangre> listaTiposSangre
                        = (List<TipoSangre>) request.getAttribute("listaTiposSangre"); %>
            <label>Tipo de Sangre:</label>
            <select id="tipo_Sangre" class="form-control" name="tipo_Sangre" >
                <option value="" disabled selected>Seleccione una opcion</option>
                <% for (TipoSangre tipo : listaTiposSangre) {%>
                <option value="<%= tipo.getIdTipoSangre()%>"
                        <%= String.valueOf(tipo.getIdTipoSangre()).equals(request.getParameter("tipo_Sangre")) ? "selected" : ""%>>
                    <%= tipo.getTipo_Sangre()%>
                </option>
                <% }%>
            </select>

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-tipo_Sangre"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("tipo_Sangre")) {%>
            <div class="text-danger"><%= errores.get("tipo_Sangre")%></div>
            <% }%>
        </div>
    </div>
    <br>
    <!-- Usuario y Campo específico (Especialidad o Sector) -->
    <div class="form-group row">
        <div class="col-sm-3">
            <% List<Usuario> listaUsuarios
                        = (List<Usuario>) request.getAttribute("listaUsuarios"); %>
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
                "Campo Requerido. Elija una opcion"-->
                <option value="" disabled selected>Seleccione una opcion</option>
                <% for (Usuario usu : listaUsuarios) {%>
                <option value="<%= usu.getIdUsuario()%>"
                        <%= String.valueOf(usu.getIdUsuario()).equals(request.getParameter("usuario")) ? "selected" : ""%>>
                    <%= usu.getNombreUsuario()%>, <%= usu.getRol()%>
                </option>
                <% }%>
            </select>

            <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
            <div class="text-danger" id="error-usuario"></div>

            <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
            en caso de que el script del JS falle o pase por alto algo-->
            <% if (errores != null && errores.containsKey("usuario")) {%>
            <div class="text-danger"><%= errores.get("usuario")%></div>
            <% }%>
        </div>

        <div class="col-sm-3" id="campo-especifico">
            <!-- Este espacio será reemplazado por JS -->
            <script>
                function mostrarCamposTipoEmpleado() {
                    const tipo = document.getElementById("tipoEmpleado").value;
                    const contenedor = document.getElementById("campo-especifico");
                    if (tipo === "odontologo") {
                        contenedor.innerHTML = `
                <label>Especialidad:</label>
                <input type="text" class="form-control" name="especialidad" id="especialidad" placeholder="Especialidad">
            `;
                    } else if (tipo === "secretario") {
                        contenedor.innerHTML = `
                <label>Sector en la Clínica:</label>
                <input type="text" class="form-control" name="sector" id="sector" placeholder="Sector">
            `;
                    }
                }
            </script>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <br>
            <button class="btn btn-primary btn-user btn-block" type="submit" id="botonEnviar">
                Guardar Cambios</button>
        </div>
    </div>

    <hr>

</form>


<!--Validación extra con JavaScript (opcional pero más visual)
Si querés mostrar advertencias antes de enviar el formulario, podés hacer algo así:-->
<script>
    function validarFormulario() {

        // Borrar errores previos
        const errores = document.querySelectorAll(".text-danger");

        errores.forEach(e => e.innerText = "");
        let valido = true;

        //const tipoEmpleado = document.getElementById("tipoEmpleado").value;
        const nombre = document.getElementById("nombre").value.trim();
        const apellido = document.getElementById("apellido").value.trim();
        const dni = document.getElementById("dni").value.trim();
        const telefono = document.getElementById("telefono").value.trim();
        const tipo_Sangre = document.getElementById("tipo_Sangre").value;
        const tipoDoc = document.getElementById("tipoDoc").value;
        const usuario = document.getElementById("usuario").value;

        //Obtengo la fecha ingresada por el usuario
        const fechaNacimiento = document.getElementById("fechaNacimiento").value;


        /*if (tipoEmpleado === "") {
            document.getElementById("error-tipoEmpleado").innerText = "Seleccione un Tipo de Empleado";
            valido = false;
        }*/

        if (!/^[a-zA-ZÁÉÍÓÚáéíóúÑñ\s]+$/.test(nombre) || nombre === "") {
            document.getElementById("error-nombre").innerText = "Solo letras. No vacio";
            valido = false;
        }

        if (!/^[a-zA-ZÁÉÍÓÚáéíóúÑñ\s]+$/.test(apellido) || apellido === "") {
            document.getElementById("error-apellido").innerText = "Solo letras. No vacio";
            valido = false;
        }

        if (!/^[0-9]+$/.test(dni) || dni === "") {
            document.getElementById("error-dni").innerText = "Solo números. No vacio";
            valido = false;
        }

        if (!/^[0-9]+$/.test(telefono) || telefono === "") {
            document.getElementById("error-telefono").innerText = "Solo números. No vacio";
            valido = false;
        }

        if (tipo_Sangre === "") {
            document.getElementById("error-tipo_Sangre").innerText = "Seleccione un Tipo de Sangre";
            valido = false;
        }

        if (tipoDoc === "") {
            document.getElementById("error-tipoDoc").innerText = "Seleccione un Tipo de Documento";
            valido = false;
        }

        if (usuario === "") {
            document.getElementById("error-usuario").innerText = "Seleccione un Usuario";
            valido = false;
        }


        //Controlar la fecha de nacimiento:
        //Si NO se ingreso una Fecha
        if (!fechaNacimiento) {

            //Se muestra el mensaje de error:
            document.getElementById("error-fechaNacimiento").innerText =
                    "Ingrese una Fecha de Nacimiento";
            valido = false;

        //Cuando SI se Ingreso una Fecha de Nacimiento:
        } else {
            
            // ===========  Calcular edad  ==================
            //Obtengo al fecha Actual:
            const hoy = new Date();

            //La fecha ingresada la paso a Date:
            const fechaNac = new Date(fechaNacimiento);

            //Calculo la diferencia de años entre la fecha actual y la ingresada:
            let edad = hoy.getFullYear() - fechaNac.getFullYear();

            //Calculo la diferencia de meses entre la fecha actual y la ingresada:
            const mes = hoy.getMonth() - fechaNac.getMonth();

            if (mes < 0 || (mes === 0 && hoy.getDate() < fechaNac.getDate())) {
                edad--;
            }

            //Si la fecha es menor a 18 años
            if (edad < 18) {

                document.getElementById("error-fechaNacimiento").innerText =
                        "El empleado debe ser mayor de 18 años";
                valido = false;

                //Cuando la fecha sea correcta
            } else {

                document.getElementById("error-fechaNacimiento").innerText = "";
            }
        }

        return valido;
    }
</script>

<!--
        El .getElementById("tipo_Sangre") espera resivir un id llamado "tipo_Sangre".
        Si el combo de TipoSangre su name es name="tipo_Sangre" no importa en este caso,
        lo que importa es su id, si el id es id="tipoSangre". 
        Como el .getElementById("tipo_Sangre") espera resivir un 'tipo_Sangre' obviamente
        no lo va a reconocer. 
        
        El no reconocerlo hara que el form se envie automaticamente
        al Servlet omitiendo la validacion del JS. Y por ende, las advertencias
        que mostrara son las del Servlet, o sea "Campo Requerido. Elija una opcion".
        En lugar de mostrar la advertencia de validacion del JS que es 
        "Debe seleccionar un tipo de sangre"
        
        Por ello, ver que cada .getElementById("") resiva el correspondiente id, 
        que coincida, ya que con que uno no coincida, 
        la validacion del JS se omitira, y el form se enviara automaticamente
        al Servlet, y saltara solo la advertencia del Servlet.
-->


<%@include file="components/bodyfinal.jsp"%>



