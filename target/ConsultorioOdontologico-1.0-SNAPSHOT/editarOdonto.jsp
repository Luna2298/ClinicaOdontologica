<%@page import="logica.Usuario"%>
<%@page import="logica.TipoSangre"%>
<%@page import="logica.TipoDocumento"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Odontologo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>
<!-- <h1>Nuevo Odontologo</h1> -->
<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Editar Odontólogo</h1>
</div>
<!-- Traigo el Usuario que le guarde como atributo a la session del Cliente -->
<% Odontologo odo = (Odontologo) request.getAttribute("odo");%>

<form class="user" id="form-editar-odonto">

    <!-- Este campo esta oculto, gracias al "hidden", 
    aqui estara el id del Usuario que se muestra para Editar.
    Si pongo: usu.getIdUsuario() sin usar el = y teniendo ; .Esto no dejara luego que el fetch
    pueda ver el Id del Usuario, y si no puede verlo, no puede acceder al Usuario
    en la BD para poder Editar. Provocando que siempre salte "Error al Actualizar".
    Por ende, la ediccion jamas ocurre.
    Ademas, el name de cada campo o input, debe ser igual al nombre de cada atributo
    de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
    al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
    <input type="hidden" name="id" value="<%=odo.getId()%>">

    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Nombre:</label>
            <input type="text" class="form-control form-control-user" id="nombre"
                   name="nombre" placeholder="Nombre" value="<%=odo.getNombre()%>" >
        </div>
        <div class="col-sm-3">
            <label>Apellido:</label>
            <input type="text" class="form-control form-control-user" id="apellido"
                   name="apellido" placeholder="Apellido" value="<%=odo.getApellido()%>" >
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">

            <label>Tipo de Doc. de Identidad:</label>
            <% List<TipoDocumento> listaTiposDocumentos
                        = (List<TipoDocumento>) request.getAttribute("listaTiposDocumentos"); %>
            <select name="tipoDoc" class="form-control">
                <% for (TipoDocumento tipoDoc : listaTiposDocumentos) {
                        boolean seleccionado = tipoDoc.getIdTipoDocumento() == odo.getTipoDocumento().getIdTipoDocumento();%>
                <option value="<%= tipoDoc.getIdTipoDocumento()%>" <%= seleccionado ? "selected" : ""%>>
                    <%= tipoDoc.getTipoDoc()%>
                </option>
                <% }%>
            </select>
        </div>
        <div class="col-sm-3">
            <label>DNI:</label>
            <input type="text" class="form-control form-control-user" id="dni"
                   name="dni" placeholder="DNI" value="<%=odo.getDni()%>">
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Teléfono:</label>
            <input type="text" class="form-control form-control-user" id="telefono"
                   name="telefono" placeholder="Teléfono" value="<%=odo.getTelefono()%>">
        </div>
        <div class="col-sm-3">
            <label>Dirección:</label>
            <input type="text" class="form-control form-control-user" id="direccion"
                   name="direccion" placeholder="Dirección" value="<%=odo.getDireccion()%>">
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
                String fechaFormateada = sdf.format(odo.getFecha_nac());%>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" 
                   class="form-control form-control-user" 
                   value="<%=fechaFormateada%>">
        </div>
        <div class="col-sm-3">
            <label>Tipo de Sangre:</label>
            <% List<TipoSangre> listaTiposSangre
                        = (List<TipoSangre>) request.getAttribute("listaTiposSangre"); %>
            <select name="tipo_Sangre" class="form-control">
                <% for (TipoSangre tipo : listaTiposSangre) {
                        boolean seleccionado = tipo.getIdTipoSangre() == odo.getTipoSangre().getIdTipoSangre();%>
                <option value="<%= tipo.getIdTipoSangre()%>" <%= seleccionado ? "selected" : ""%>>
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
                   name="especialidad" placeholder="Especialidad" 
                   value="<%=odo.getEspecialidad()%>" >
        </div>
        <div class="col-sm-3">
            <% List<Usuario> listaUsuarios
                        = (List<Usuario>) request.getAttribute("listaUsuarios"); %>
            <label>Usuario:</label>
            <select class="form-control" name="usuario">
                <% for (Usuario usu : listaUsuarios) {
                        boolean seleccionado = usu.getIdUsuario() == odo.getUsuario().getIdUsuario();%>
                <option value="<%= usu.getIdUsuario()%>" <%= seleccionado ? "selected" : ""%>>
                    <%= usu.getNombreUsuario()%>
                </option>
                <% }%>
            </select>
        </div>
    </div>
    <div class="form-group row">       
        <div class="col-sm-3 mb-3 mb-sm-0">
            <br>
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Guardar Cambios</button>
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


<script>
    /*
     Aqui es donde paso cada dato del Usuario a un JSON y luego lo mando al Servlet.
     Tambien a su vez, muestra los mensajes */
    document.getElementById("form-editar-odonto").addEventListener("submit", function (event) {

        event.preventDefault(); // Evita que el form se envíe normalmente
        //Evito que el navegador envíe la info como POST clásico.

        const form = event.target;

        // Armamos el objeto USUARIO con los datos del formulario
        // JavaScript toma los datos del formulario y los convierte a JSON
        const odonto = {
            /*
             Como Odontologo HEREDA de Persona, los name="" de cada Input y propiedades
             del JS, del Objeto 'odonto' DEBEN llamarse igual a cada atributo de la
             clase PADRE del cual HEREDA.
             Ejemplo: 
             En vez de --> idOdonto: form.elements["idOdonto"].value,
             DEBE SER -->  id: form.elements["id"].value, --> 'id' es atributo de Persona
             
             De no respectar el nombre de los atributos HEREDADOS, el JSON no podra
             enviar la request al .doPut() del SvOdonto.
             Ademas, si Odontologo no tiene un atributo llamado 'id' o 'idOdonto', 
             el Gson no lo va a mapear bien → el ID puede quedar como 0 o null → 
             y por eso no encuentra el odontólogo original en la base de datos
             que debe editar, y falla silenciosamente.*/

            id: form.elements["id"].value,
            nombre: form.elements["nombre"].value,
            apellido: form.elements["apellido"].value,
            dni: form.elements["dni"].value,
            telefono: form.elements["telefono"].value,
            direccion: form.elements["direccion"].value,
            fecha_nac: form.elements["fechaNacimiento"].value,
            especialidad: form.elements["especialidad"].value,
            tipoDoc: form.elements["tipoDoc"].value,
            tipo_Sangre: form.elements["tipo_Sangre"].value,
            //usuario: form.elements["usuario"].value
            usuario: {
                idUsuario: form.elements["usuario"].value
            }
        };

        // Mostrar en consola el JSON que vas a enviar PRUEBA
        console.log(JSON.stringify(odonto)); // ⬅️ Acá lo ves


        //Una vez convertidos los datos en JSON, los envía mediante fetch() al Servlet SvOdonto
        fetch("SvOdonto", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(odonto)
        })
                .then(res => {
                    if (res.ok) {
                        Swal.fire({
                            //Aca es donde arma y muestra el mensaje
                            icon: "success",
                            title: "Éxito",
                            text: "Odontólogo actualizado correctamente",
                            confirmButtonColor: "#3085d6",
                            confirmButtonText: "Aceptar"
                        }).then(() => {
                            //Luego de mostrar el Mensaje
                            //Redireccionamos al Servlet, por default ejecuta el .doGet(), 
                            //haciendo que este actualice la Lista de Usuario, 
                            //y termina redireccionando a mostrar la Lista.
                            window.location.href = "SvEmpleado?accion=listarOdonto";
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error",
                            text: "No se pudo actualizar el odontólogo",
                            confirmButtonColor: "#d33",
                            confirmButtonText: "Cerrar"
                        });
                    }
                });
    });
</script>


<%@include file="components/bodyfinal.jsp"%>
