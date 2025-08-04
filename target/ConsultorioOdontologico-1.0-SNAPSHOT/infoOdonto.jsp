<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Odontologo"%>
<%@page import="logica.Odontologo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Información extra del Odontólogo</h1>
    <div class="card shadow mb-4">
        <div class="card-body">
            <% Odontologo odo = (Odontologo) request.getAttribute("odo");%>

            <div><label>Nro. de Registro: <%=odo.getId()%></label> </div>
            <div><label>Apellido y Nombre: <%=odo.getApellido()%>, <%=odo.getNombre()%></label> </div>
            <div><label>Tipo de Doc.: <%=odo.getTipoDocumento().getTipoDoc()%></label> </div>
            <div><label>Nro. de Doc.: <%=odo.getDni()%></label> </div>
            <div><label>Tipo de Sangre: <%=odo.getTipoSangre().getTipo_Sangre()%></label> </div>
            <hr>

            <!-- Con esto logro convertir de Date a Date SIMPLE
            y ultimo a String, la Fecha que llega desde la BD.
            Como llega y se muestra la fecha: Wed Feb 12 00:00:00 ART 1986,
            Pero con SimpleDateFormat, le doy un formato mas SIMPLE
            a la Fecha, y la paso de Date a String, 
            entonces me queda: 12/02/1986-->
            <% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");%>
            <div><label>Fecha Nac.: <%=sdf.format(odo.getFecha_nac())%> </label> </div>
            <div><label>Celular: <%=odo.getTelefono()%></label></div>
            <div><label>Dirección: <%=odo.getDireccion()%></label> </div>
            <hr>
            <div><label>Especialidad: <%=odo.getEspecialidad()%></label> </div>
            <div><label>Usuario: <%=odo.getUsuario().getNombreUsuario() %></label> </div>

            <form action="SvEmpleado" method="GET">
                <div class="form-group row">
                    <div class="col-sm-3">
                        <button class="btn btn-primary btn-user btn-block" type="submit"
                                name="accion" value="listarOdonto">
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
