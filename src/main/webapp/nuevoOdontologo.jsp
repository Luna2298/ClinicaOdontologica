<%@page import="logica.TipoSangre"%>
<%@page import="logica.TipoSangre"%>
<%@page import="logica.TipoDocumento"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>
<!-- <h1>Nuevo Odontologo</h1> -->
<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Nuevo Odontólogo</h1>
</div>

<form class="user" action="SvOdonto" method="POST">
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
            <label>Especialidad:</label>
            <input type="text" class="form-control form-control-user" id="especialidad"
                   name="especialidad" placeholder="Especialidad">
        </div>
        <div class="col-sm-3">
            <br>
            <button class="btn btn-primary btn-user btn-block" type="submit">
            Crear Odontólogo</button>
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


<%@include file="components/bodyfinal.jsp"%>