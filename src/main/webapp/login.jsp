<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Login Clinica Odontólogica</title>

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body class="bg-gradient-primary">

        <div class="container">

            <!-- Outer Row -->
            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <!-- Nested Row within Card Body -->
                            <div class="row">
                                <div class="col-lg-6 d-none d-lg-block bg-login-image" style="background-repeat:no-repeat">
                                    <!--<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQqzLT8-pUDAWpAz8OLKtda0lYc_1ItPLrhUEnY_S3undx4MpwICV9AKxHEmxaaTGacjQA&usqp=CAU" alt="alt"/>-->
                                    <img src="https://www.clinicadentalarco.com/wp-content/uploads/2020/08/dientes-de-leche-odontopediatria-centro-vitoria.jpg" alt="alt"/>
                                </div>
                                <div class="col-lg-6">
                                    <div class="p-5">
                                        <div class="text-center">
                                            <h1 class="h4 text-gray-900 mb-4">Login!</h1>
                                        </div>
                                        <form onsubmit="return validarFormulario()" class="user" action="SvLogin" method="POST" >
                                            <% Map<String, String> errorUsuario = (Map<String, String>) request.getAttribute("errorUsuario"); %>
                                            <div class="form-group">
                                                <input class="form-control form-control-user"
                                                       id="usuario" name="usuario" 
                                                       placeholder="Usuario">

                                                <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
                                                <div class="text-danger" id="error-usuario"></div>

                                                <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
                                                en caso de que el script del JS falle o pase por alto algo-->
                                                <% if (errorUsuario != null && errorUsuario.containsKey("usuario")) {%>
                                                <div class="text-danger"><%= errorUsuario.get("usuario")%></div>
                                                <% }%>
                                            </div>
                                            <div class="form-group">
                                                <input type="password" class="form-control form-control-user"
                                                       id="contrasenia" name="contrasenia" placeholder="Contraseña">
                                                <!--Para mostrar la ADVETENCIA/ERROR que viene del script del JS-->
                                                <div class="text-danger" id="error-contrasenia"></div>
                                                
                                                <!--Para mostrar la ADVETENCIA/ERROR que viene desde el SERVLET, 
                                                en caso de que el script del JS falle o pase por alto algo-->
                                                <% if (errorUsuario != null && errorUsuario.containsKey("contrasenia")) {%>
                                                <div class="text-danger"><%= errorUsuario.get("contrasenia")%></div>
                                                <% }%>

                                            </div>

                                            <button class="btn btn-primary btn-user btn-block" type="submit">
                                                Login</button>

                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <script>

            function validarFormulario() {

                // Borrar errores previos
                /*
                 Con document.querySelectorAll(".text-danger") toma cada elemento
                 que su clase se llame text-danger*/
                const errores = document.querySelectorAll(".text-danger");

                errores.forEach(e => e.innerText = "");
                let valido = true;

                const usuario = document.getElementById("usuario").value.trim();
                const contrasenia = document.getElementById("contrasenia").value.trim();

                if (usuario === "") {
                    document.getElementById("error-usuario").innerText = "Ingrese un usuario";
                    valido = false;
                }

                if (contrasenia === "") {
                    document.getElementById("error-contrasenia").innerText = "Ingrese la contraseña";
                    valido = false;
                }

                return valido;
            }
        </script>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin-2.min.js"></script>



    </body>

</html>

