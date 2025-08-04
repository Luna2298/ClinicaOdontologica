<%@page import="logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="components/header.jsp"%>
<%@include file="components/bodyprimeraparte.jsp"%>

<div class="text-left">
    <h1 class="h3 text-gray-900 mb-3">Editar Usuario</h1>
</div>
<!-- Traigo el Usuario que le guarde como atributo a la session del Cliente -->
<% Usuario usu = (Usuario)request.getSession().getAttribute("usuEditar"); %>

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
    <input type="hidden" name="idUsuario" value="<%=usu.getIdUsuario() %>">
    
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
            de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
            al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
            <input type="text" class="form-control form-control-user" name="nombreUsuario"
                   placeholder="Nombre Usuario" value="<%=usu.getNombreUsuario() %>">
        </div>
        <!-- Input con el boton Ver y Ocultar Contraseña -->
        <div class="input-group mb-3 col-sm-3">
            <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
            de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
            al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
            <input type="password" class="form-control" id="contrasenia" name="contrasenia"
                   placeholder="Contraseña" value="<%= usu.getContrasenia() %>">
            <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" id="togglePassword">
                   <i class="fas fa-eye"></i>
                </button>
            </div>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-3 mb-3 mb-sm-0">
            <!-- El name de cada campo o input, debe ser igual al nombre de cada atributo
            de la clase Usuario que fue mapeada en la BD. Porque de lo contrario, dara error
            al intentar Editar, ya que no podra encontrar bien los atributos a editar. -->
            <input type="text" class="form-control form-control-user" id="rol" name="rol"
                   placeholder="Rol" value="<%=usu.getRol() %>">
        </div>
        <div class="col-sm-3">
            <button class="btn btn-primary btn-user btn-block" type="submit">
            Guardar Cambios</button>
        </div>
        
    </div>
    
    <hr>
    
</form>

        
<script>
/*
Aqui es donde paso cada dato del Usuario a un JSON y luego lo mando al Servlet.
Tambien a su vez, muestra los mensajes */
document.getElementById("form-editar-usuario").addEventListener("submit", function(event) {
    
    event.preventDefault(); // Evita que el form se envíe normalmente
    //Evito que el navegador envíe la info como POST clásico.

    const form = event.target;

    // Armamos el objeto USUARIO con los datos del formulario
    // JavaScript toma los datos del formulario y los convierte a JSON
    const usuario = {
        idUsuario:form.elements["idUsuario"].value,
        nombreUsuario: form.elements["nombreUsuario"].value,
        contrasenia: form.elements["contrasenia"].value,
        rol: form.elements["rol"].value
    };
    
    // Mostrar en consola el JSON que vas a enviar PRUEBA
    console.log(JSON.stringify(usuario)); // ⬅️ Acá lo ves


    //Una vez convertidos los datos en JSON, los envía mediante fetch() al Servlet SvUsuarios
    fetch("SvUsuarios", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(usuario)
    })
    .then(res => {
    if (res.ok) {
        Swal.fire({
            //Aca es donde arma y muestra el mensaje
            icon: "success",
            title: "Éxito",
            text: "Usuario actualizado correctamente",
            confirmButtonColor: "#3085d6",
            confirmButtonText: "Aceptar"
        }).then(() => {
            //Luego de mostrar el Mensaje
            //Redireccionamos al Servlet, por default ejecuta el .doGet(), 
            //haciendo que este actualice la Lista de Usuario, 
            //y termina redireccionando a mostrar la Lista.
            window.location.href = "SvUsuarios"; 
        });
    }else {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "No se pudo actualizar el usuario",
            confirmButtonColor: "#d33",
            confirmButtonText: "Cerrar"
        });
    }
    });
});
</script>



<script>
  /*Aqui esta el codigo JavaScript para poder Ver y Ocultar la Contraseña de cada Usuario,
   tanto en nuevoUsuario.jsp como en editarUsuario.jsp */
  document.getElementById("togglePassword").addEventListener("click", function () {
    const passwordInput = document.getElementById("contrasenia");
    const icon = this.querySelector("i");

    if (passwordInput.type === "password") {
      passwordInput.type = "text";
      icon.classList.remove("fa-eye");
      icon.classList.add("fa-eye-slash");
    } else {
      passwordInput.type = "password";
      icon.classList.remove("fa-eye-slash");
      icon.classList.add("fa-eye");
    }
  });
</script>  


<%@include file="components/bodyfinal.jsp"%>

