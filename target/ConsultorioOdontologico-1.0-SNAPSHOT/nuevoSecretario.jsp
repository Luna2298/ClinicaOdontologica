<%@page import="logica.Usuario"%>
<%@page import="logica.TipoSangre"%>
<%@page import="logica.TipoDocumento"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>


<!-- <h1>Nuevo Odontologo</h1> -->
<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Nuevo Secretario</h1>
</div>

<form class="user" action="SvSecretario" method="POST">
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Nombre:</label>
            <input type="text" class="form-control form-control-user" id="nombre"
                   name="nombre" placeholder="Nombre">
        </div>
        <div class="col-sm-3">
            <label>Apellido:</label>
            <input type="text" class="form-control form-control-user" id="apellido"
                   name="apellido" placeholder="Apellido">
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <% List<TipoDocumento> listaTiposDocumentos
                        = (List<TipoDocumento>) request.getAttribute("listaTiposDocumentos"); %>
            <label>Tipo de Doc. de Identidad:</label>
            <select id="tipoDoc" class="form-control" name="tipoDoc">
                <% for (TipoDocumento tipoDoc : listaTiposDocumentos) {%>
                <option value="<%= tipoDoc.getIdTipoDocumento()%>">
                    <%= tipoDoc.getTipoDoc()%>
                </option>
                <% } %>
            </select>
        </div>
        <div class="col-sm-3">
            <label>Nro. de DNI:</label>
            <input type="text" class="form-control form-control-user" id="dni"
                   name="dni" placeholder="00.000.000">
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Teléfono:</label>
            <input type="text" class="form-control form-control-user" id="telefono"
                   name="telefono" placeholder="Teléfono">
        </div>
        <div class="col-sm-3">
            <label>Dirección:</label>
            <input type="text" class="form-control form-control-user" id="direccion"
                   name="direccion" placeholder="Dirección">
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Fecha de Nacimiento:</label>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                   class="form-control form-control-user" 
                   value="2000-01-01">
        </div>
        <div class="col-sm-3">
            <% List<TipoSangre> listaTiposSangre
                        = (List<TipoSangre>) request.getAttribute("listaTiposSangre"); %>
            <label>Tipo de Sangre:</label>
            <select id="tipoSangre" class="form-control" name="tipo_Sangre">
                <% for (TipoSangre tipo : listaTiposSangre) {%>
                <option value="<%= tipo.getIdTipoSangre()%>">
                    <%= tipo.getTipo_Sangre()%>
                </option>
                <% }%>
            </select>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Sector en la Clinica:</label>
            <input type="text" class="form-control form-control-user" id="sector"
                   name="sector" placeholder="Sector">
        </div>
        <div class="col-sm-3">
            <% List<Usuario> listaUsuarios
                        = (List<Usuario>) request.getAttribute("listaUsuarios"); %>
            <label>Usuario:</label>
            <select id="usuario" class="form-control" name="usuario">
                <% for (Usuario usu : listaUsuarios) {%>
                <option value="<%= usu.getIdUsuario()%>">
                    <%= usu.getNombreUsuario() %>, <%=usu.getRol() %>
                </option>
                <% }%>
            </select>
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <br>
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Crear Secretario</button>
        </div>
    </div>

    <hr>

</form>


<script>
    /*Para controlar que no se ingrese una Fecha Mayor a la Actual 
     en Fecha Nacimiento Odontologo*/

    // Obtener la fecha actual en formato YYYY-MM-DD
    const hoy = new Date().toISOString().split("T")[0];

    // Asignar la fecha actual como límite máximo al input
    document.getElementById("fechaNacimiento").max = hoy;
</script>




<!--<script>
    /*
    Con esto, dependiendo de si el Paciente TIENE o NO obra social, entonces:
    De ser TRUE, el input siguiente se activara permitiendo poder escribir en el. 
    De lo contrario, FALSE, el input no se activara y no se podra escribir en el.*/
    document.getElementById("opcion").addEventListener("change", function () {
        const campo = document.getElementById("campoExtra");
        if (this.value === "si") {
            campo.disabled = false;
        } else {
            campo.disabled = true;
            campo.value = ""; // opcional: limpiar el campo si se desactiva
        }
    });
</script>-->




<!--
Otra opcion para activar o no un campo, en este caso ObraSocial del Paciente.
Usando: radioButtons para SI y NO. Y un input donde escribir el tipo de ObraSocial.
Y el codigo JavaScript correspondiente para activar o no el input, 
dependiendo la opcion elegida.

HTML:
<label><input type="radio" name="activar" value="si"> Sí</label>
<label><input type="radio" name="activar" value="no" checked> No</label>
<input type="text" id="campoExtra" class="form-control mt-2" placeholder="Campo adicional" disabled>

JAVASCRIPT:
<script>
    document.querySelectorAll('input[name="activar"]').forEach(function (radio) {
        radio.addEventListener("change", function () {
            const campo = document.getElementById("campoExtra");
            if (this.value === "si") {
                campo.disabled = false;
            } else {
                campo.disabled = true;
                campo.value = ""; // opcional
            }
        });
    });
</script>-->

<%@include file="components/bodyfinal.jsp"%>

