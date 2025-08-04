<%@page import="logica.TipoDocumento"%>
<%@page import="logica.TipoSangre"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="logica.Paciente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>
<!-- <h1>Nuevo Odontologo</h1> -->
<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Editar Paciente</h1>
</div>

<!-- Traigo al Paciente que le guarde como atributo a la session del Cliente -->
<% Paciente pa = (Paciente) request.getAttribute("paciente");%>

<form class="user" id="form-editar-paciente">

    <!-- Este campo esta oculto, gracias al "hidden", 
    aqui estara el id del Usuario que se muestra para Editar.
    Si pongo: usu.getIdUsuario() sin usar el = y teniendo ; .Esto no dejara luego que el fetch
    pueda ver el Id del Usuario, y si no puede verlo, no puede acceder al Usuario
    en la BD para poder Editar. Provocando que siempre salte "Error al Actualizar".
    Por ende, la ediccion jamas ocurre.
    Ademas, el name de cada campo o input, debe ser igual al nombre de cada atributo
    de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
    al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
    <input type="hidden" name="id" value="<%=pa.getId()%>">

    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Nombre:</label>
            <input type="text" class="form-control form-control-user" id="nombre"
                   name="nombre" placeholder="Nombre" value="<%=pa.getNombre()%>">
        </div>
        <div class="col-sm-3">
            <label>Apellido:</label>
            <input type="text" class="form-control form-control-user" id="apellido"
                   name="apellido" placeholder="Apellido" value="<%=pa.getApellido()%>">
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
                        boolean seleccionado = tipoDoc.getIdTipoDocumento() == pa.getTipoDocumento().getIdTipoDocumento();%>
                <option value="<%= tipoDoc.getIdTipoDocumento()%>" <%= seleccionado ? "selected" : ""%>>
                    <%= tipoDoc.getTipoDoc()%>
                </option>
                <% }%>
            </select>
        </div>
        <div class="col-sm-3">
            <label>DNI:</label>
            <input type="text" class="form-control form-control-user" id="dni"
                   name="dni" placeholder="DNI" value="<%=pa.getDni()%>">
        </div>
    </div>
    <br>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <label>Teléfono:</label>
            <input type="text" class="form-control form-control-user" id="telefono"
                   name="telefono" placeholder="Teléfono" value="<%=pa.getTelefono()%>">
        </div>
        <div class="col-sm-3">
            <label>Dirección:</label>
            <input type="text" class="form-control form-control-user" id="direccion"
                   name="direccion" placeholder="Dirección" value="<%=pa.getDireccion()%>">
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
                String fechaFormateada = sdf.format(pa.getFecha_nac());%>
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
                        boolean seleccionado = tipo.getIdTipoSangre() == pa.getTipoSangre().getIdTipoSangre();%>
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
            <div>
                <label>Posee Obra Social:</label>
                <select id="tieneObraSocial" class="form-control" name="tieneObraSocial">
                    <!--
                    Si pa.getTieneObraSocial() es TRUE, en el Ternario:
                    
                       (true)
                    pa.getTieneObraSocial() ? "selected" : ""
                    
                    se cargara con "selected" la primera opcion,
                    o sea la option seleccionada es Sí tiene Obra Social.
                    Pero si es FALSE, el Ternario ACA dara null, o sea "".
                    Pasa a la siguiente opcion a ver si coincide.
                    -----------------------------------------------------
                    Si pa.getTieneObraSocial() es FALSE, 
                    en el Ternario:
                    
                    (false, contrario '!' de TRUE)
                    !pa.getTieneObraSocial() ? "selected" : ""
                    
                    este se cargara con "selected", o sea la option seleccionada 
                    sera No tiene Obra Social.
                    -->
                            <option value="true" <%= pa.getTieneObraSocial()
                                    ? "selected" : ""%>>Sí tiene Obra Social</option>
                            <option value="false" <%= !pa.getTieneObraSocial()
                                    ? "selected" : ""%>>No tiene Obra Social</option>
                </select>
            </div>
            <!--<input type="text" id="campoExtra" class="form-control mt-2" 
                   name="obraSocial" placeholder="Obra Social" disabled>-->
        </div>
        <div class="col-sm-3">
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
    document.getElementById("form-editar-paciente").addEventListener("submit", function (event) {

        event.preventDefault(); // Evita que el form se envíe normalmente
        //Evito que el navegador envíe la info como POST clásico.

        const form = event.target;

        // Armamos el objeto USUARIO con los datos del formulario
        // JavaScript toma los datos del formulario y los convierte a JSON
        const paciente = {
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
            tieneObraSocial: form.elements["tieneObraSocial"].value,
            tipo_Sangre: form.elements["tipo_Sangre"].value,
            tipoDoc: form.elements["tipoDoc"].value
        };

        // Mostrar en consola el JSON que vas a enviar PRUEBA
        console.log(JSON.stringify(paciente)); // ⬅️ Acá lo ves


        //Una vez convertidos los datos en JSON, los envía mediante fetch() al Servlet SvUsuarios
        fetch("SvPaciente", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(paciente)
        })
                .then(res => {
                    if (res.ok) {
                        Swal.fire({
                            //Aca es donde arma y muestra el mensaje
                            icon: "success",
                            title: "Éxito",
                            text: "Paciente actualizado correctamente",
                            confirmButtonColor: "#3085d6",
                            confirmButtonText: "Aceptar"
                        }).then(() => {
                            //Luego de mostrar el Mensaje
                            //Redireccionamos al Servlet, por default ejecuta el .doGet(), 
                            //haciendo que este actualice la Lista de Usuario, 
                            //y termina redireccionando a mostrar la Lista.
                            window.location.href = "SvPaciente";
                        });
                    } else {
                        Swal.fire({
                            icon: "error",
                            title: "Error",
                            text: "No se pudo actualizar el paciente",
                            confirmButtonColor: "#d33",
                            confirmButtonText: "Cerrar"
                        });
                    }
                });
    });
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
