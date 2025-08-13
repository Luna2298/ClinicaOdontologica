<%@page import="logica.Secretario"%>
<%@page import="logica.Persona"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Odontologo"%>
<%@page import="logica.Odontologo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<div class="container-fluid">

    <%        String tipoEmpleado = (String) request.getAttribute("tipoEmpleado");

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

    <h1 class="h3 mb-2 text-gray-800">Información extra del Odontólogo</h1>
    <div class="card shadow mb-4">
        <div class="card-body">

            <div><label>Nro. de Registro: <%=persona.getId()%></label> </div>
            <div><label>Apellido y Nombre: <%=persona.getApellido()%>, <%=persona.getNombre()%></label> </div>
            <div><label>Tipo de Doc.: <%=persona.getTipoDocumento().getTipoDoc()%></label> </div>
            <div><label>Nro. de Doc.: <%=persona.getDni()%></label> </div>
            <div><label>Tipo de Sangre: <%=persona.getTipoSangre().getTipo_Sangre()%></label> </div>
            <hr>

            <!-- Con esto logro convertir de Date a Date SIMPLE
            y ultimo a String, la Fecha que llega desde la BD.
            Como llega y se muestra la fecha: Wed Feb 12 00:00:00 ART 1986,
            Pero con SimpleDateFormat, le doy un formato mas SIMPLE
            a la Fecha, y la paso de Date a String, 
            entonces me queda: 12/02/1986-->
            <% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");%>
            <div><label>Fecha Nac.: <%=sdf.format(persona.getFecha_nac())%> </label> </div>
            <div><label>Celular: <%=persona.getTelefono()%></label></div>
            <div><label>Dirección: <%=persona.getDireccion()%></label> </div>
            <hr>

            <% if (odonto != null) {%>

            <div><label>Especialidad: <%=odonto.getEspecialidad()%></label> </div>
            <div><label>Usuario: <%=odonto.getUsuario().getNombreUsuario()%></label> </div>
            <% } else {%>

            <div><label>Especialidad: <%=secre.getSector()%></label> </div>
            <div><label>Usuario: <%=secre.getUsuario().getNombreUsuario()%></label> </div>
            <% }%>



            <form action="SvEmpleado" method="GET">
                <div class="form-group row">
                    <div class="col-sm-3">
                        <button class="btn btn-primary btn-user btn-block" type="submit"
                   "             name="accion" value="<%=odonto != null ? "listarOdonto" : "listarSecre" %>">
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


<%@include file="components/bodyfinal.jsp"%>

