<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Paciente"%>
<%@page import="logica.TipoDocumento"%>
<%@page import="logica.ControladoraLogica"%>
<%@page import="java.util.List"%>
<%@page import="logica.TipoSangre"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>


<!-- <h1>Nuevo Odontologo</h1> 
<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Informacion extra del Paciente</h1>
</div>-->

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Informacion extra del Paciente</h1>
    <div class="card shadow mb-4">
        <div class="card-body">
            <% Paciente pa = (Paciente) request.getAttribute("paciente");%>

            <div><label>Nro. de Registro: <%=pa.getId()%></label> </div>
            <div><label>Apellido y Nombre: <%=pa.getApellido()%>, <%=pa.getNombre()%></label> </div>
            <div><label>Tipo de Doc.: <%=pa.getTipoDocumento().getTipoDoc()%></label> </div>
            <div><label>Nro. de Doc.: <%=pa.getDni()%></label> </div>
            <div><label>Tipo de Sangre: <%=pa.getTipoSangre().getTipo_Sangre()%></label> </div>
            <div><label>Obra Social: <%=pa.getTieneObraSocial()%></label> </div>

            <!-- Con esto logro convertir de Date a Date SIMPLE
            y ultimo a String, la Fecha que llega desde la BD.
            Como llega y se muestra la fecha: Wed Feb 12 00:00:00 ART 1986,
            Pero con SimpleDateFormat, le doy un formato mas SIMPLE
            a la Fecha, y la paso de Date a String, 
            entonces me queda: 12/02/1986-->
            <% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");%>
            <div><label>Fecha Nac.: <%=sdf.format(pa.getFecha_nac())%> </label> </div>
            <div><label>Celular: <%=pa.getTelefono()%></label></div>
            <div><label>Dirección: <%=pa.getDireccion()%></label> </div>


            <!--<a href="listaPacientes.jsp" class="btn btn-primary btn-user btn-block">
            Volver</a>-->
            <form action="SvPaciente" method="GET">
                <div class="form-group row">
                    <div class="col-sm-3">
                        <button class="btn btn-primary btn-user btn-block" type="submit"
                                name="accion" value="listar">
                            Volver</button>
                    </div>     
                </div>
            </form>
        </div>
    </div>
</div>

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

