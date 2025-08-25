package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.ControladoraLogica;
import logica.Odontologo;
import logica.Rol;
import logica.Secretario;
import logica.Usuario;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "SvUsuarios", urlPatterns = {"/SvUsuarios"})
public class SvUsuarios extends HttpServlet {

    ControladoraLogica controlLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    //Aca en base a que accion quiera realizar, Buscar para Editar o Ver la Lista de Usuarios
    //Se ejecutara uno u otro metodo.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {
            listarUsuarios(request, response);

        } else if ("buscar".equals(accion)) {
            buscarUsuarioPorId(request, response);

            /*Redirecciono a la pestaña de Crear un Nuevo Paciente*/
            request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);

        } else if ("traerAccesorios".equals(accion)) {

            UtilidadesServlet.listaRoles(request, controlLogica);

            /*Redirecciono a la pestaña de Crear un Nuevo Paciente*/
            request.getRequestDispatcher("nuevoUsuario.jsp").forward(request, response);
        }

        //Lo hizo la Profe:
        /*HttpSession mysession = request.getSession();
        mysession.setAttribute("listUsers", listUsers);
        System.out.println("Usuario: " + listUsers.get(0).getNombreUsuario());
        response.sendRedirect("listaUsuarios.jsp");*/
    }

    //Aca Creo un Nuevo Usuario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //========================== Codigo Normal del doPost(): =============
        /*Creo este map para asi guardar cada error o advertencia que se detecte 
        en el Servlet, y luego lo mando por request al nuevoEmpleado.jsp, 
        para asi mostrar cada advertencia al lado de cada input o combo*/
        Map<String, String> erroresUsuario = new HashMap<>();

        //============== Valido que se haya ingresado/elejido correctamente todo: ===========
        //Veo que tipo de Empleado se va a crear, Odontologo o Secretario:
        //Campos comunes que comparten Odontologo y Secretario:
        if (request.getParameter("nombreUsuario") == null
                || !request.getParameter("nombreUsuario").matches("^[a-zA-Z0-9.,]{8,}$")) {
            /*Con .matches("^[a-zA-Z0-9.,]+$") Permito el nombreUsuario pueda contener:
              Letras, Numeros, . y , 
              Con {8,} le digo que el mínimo son 8 caracteres.*/

            erroresUsuario.put("nombreUsuario", "Campo Requerido.\nMínino 8 caracteres.\nSin espacios.");

        }
        if (request.getParameter("contrasenia") == null
                || !request.getParameter("contrasenia").matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
            /*Con "^(?=.*[A-Za-z])(?=.*\\d).{8,}$" le permito ingresar todo tipo de
            caracteres. Y como mínimo 8.*/

            erroresUsuario.put("contrasenia", "Campo Requerido.\nMínino 8 caracteres.\nSin espacios.");
        }

        if (request.getParameter("rol") == null
                || request.getParameter("rol").isEmpty()) {

            erroresUsuario.put("rol", "Campo Requerido.\nSeleccione una opción");
        }

        //Si el mapa de errores es DISTINTO de Vacio
        if (!erroresUsuario.isEmpty()) {

            //Le asigno como Atributo a la request TODOS los Errores
            request.setAttribute("erroresUsuario", erroresUsuario);

            /*Traigo la lista de Accesorios, y los asigno a la request. De lo contrario,
              el nuevoEmpleado.jsp no se mostrara, ya que le falta contenido, o sea
              le falta la info a los combos*/
            UtilidadesServlet.listaRoles(request, controlLogica);

            //Redirecciono a nuevoEmpleado.jsp
            request.getRequestDispatcher("nuevoUsuario.jsp").forward(request, response);
            return;
        }

        String nombreUsuario = request.getParameter("nombreUsuario");
        String contra = request.getParameter("contrasenia");

        /*Generar hash/Encriptacion para la Contraseña.
         Con esto en la BD no se vera tal cual la contraseña. Sino que una Encriptacion,
        para asi proteger mejor los datos de cada registro en la BD, por mas que esta
        sea saqueada. Ademas, cualquiera que quiera maneje la BD tampoco sabra las
        Contraseñas exactas de cada Usuario.
        Ejemplo:
        Usuario: Juan
        Contraseña: 123
        En la BD NO se guarda Juan, 123. 
        Se guardara Juan, $2a$10$8kHzmJp7Ddn0NUN4Xn4xpepxlE.tnZjUgQXdxaoDtiBCV3WQ0j3QK*/
        String hashContrasenia = BCrypt.hashpw(contra, BCrypt.gensalt());
        // Ahora guardás "hashContrasenia" en la base de datos en lugar de "contra"

        int idRol = Integer.parseInt(request.getParameter("rol"));
        Rol rol = controlLogica.traerRol(idRol);

        try {

            // Ahora guardás "hashContrasenia" en la base de datos en lugar de "contra"
            controlLogica.crearUsuario(nombreUsuario, hashContrasenia, rol);

        } catch (Exception e) {

            e.printStackTrace();
            request.setAttribute("error", "Error al crear el usuario: " + e.getMessage());
            UtilidadesServlet.listaRoles(request, controlLogica);
            request.getRequestDispatcher("nuevoUsuario.jsp").forward(request, response);
        }

        response.sendRedirect("SvUsuarios?accion=traerAccesorios");
    }

    //Aca Edito un Usuario
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Codigo Actual:
        // 2. Leer JSON del body
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);

        //========== ↓↓↓ VALIDACIONES ↓↓↓ =======================================
        Map<String, String> erroresEditarUsuario = validarDatos(jsonObj);

        //Si el mapa de errores es DISTINTO de Vacio
        if (!erroresEditarUsuario.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonErrores = new Gson().toJson(erroresEditarUsuario);
            System.out.println("JSON de errores enviado al frontend: " + jsonErrores); // <-- debug

            response.getWriter().write(new Gson().toJson(erroresEditarUsuario));
            return;
        }

        //========== ↑↑↑ VALIDACIONES ↑↑↑ =======================================

        /*Si uso 'request.getParameter("tipoEmpleado")'.
            Ya entiendo por qué te da 404 Odontologo no encontrado.
            Está intentando leer tipoEmpleado desde la URL, pero en tu prueba 
            de Postman vos lo pusiste en el JSON del body, no en la query string.
            Por eso request.getParameter("tipoEmpleado") devuelve null y ni 
            siquiera entra a buscar el odontólogo.
            
            Con tu URL actual:
            http://localhost:8080/ConsultorioOdontologico/SvEmpleado?id=17
            el servlet nunca recibe tipoEmpleado como parámetro de la URL.
            
            
            Para que el doPut() pueda tomar tipoEmpleado tanto desde la URL 
            como desde el JSON:
         */
        // 1. Obtener id desde la URL:
        /*El ID lo Obtengo de la URL del fetch:
        "SvUsuarios?id=" + usuario.idUsuario,  de la parte ' ?id= ' 
        NO del 'usuario.idUsuario'
        Ya que sin importar de donde se le pase el parametro, o sea usuario.idUsuario,
        el dato siempre se identificara como id, gracias a --> ?id= 
        Es por ello, que llamo al parametro "id":
        ' request.getParameter("id") '*/
        String idStr = request.getParameter("id");

        //Prueba para ver si llega el ID desde la URL al Servlet: 
        System.out.println("idUsuario que recibe el doPut() es desde la URL: " + idStr);

        /*Verifico si llego el ID*/
        if (idStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Falta el parámetro id\"}");
            return;
        }

        /*Intento convertir de String a Int el ID de la URL:*/
        int id = Integer.parseInt(idStr);

        /*Pero si no obtiene el ID mediante la URL, ya que ID le da null,
        pero aun asi en el Objeto JSON exite una constante llamada "idUsuario",
        Gracias a:
        const usuario = { idUsuario: ...,  }
        Entonces...*/
        if (jsonObj.has("idUsuario")) {

            /*Ahora, intenta obtener el ID mediante el JSON,
                debido a 'jsonObj.get("id").getAsInt()':*/
            id = jsonObj.get("idUsuario").getAsInt();
            //Prueba para ver si llega el ID desde la URL al Servlet: 
            System.out.println("idUsuario que recibe el doPut() es desde el JSON: " + id);
        }

        /*Aca verifico haber obtenido el ID, por alguna de las
        2 opciones, o sea URL o JSON. De no obtenerlo, que muestre un 
        mensaje de error.*/
        if (id == 0) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Falta el parámetro ID\"}");
            return;
        }

        try {

            // 3. Buscar Usuario
            Usuario usu = controlLogica.traerUsuario(id);

            if (usu == null) {

                /*Muestra un mensaje de error*/
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Usuario no encontrado\"}");
                return;

            }

            editarUsuario(usu, jsonObj);

            Map<String, String> mensaje = new HashMap<>();
            mensaje.put("status", "success");
            //mensaje.put("tipoEmpleado", "Odontologo");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(mensaje));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");

            System.out.println("Falta el parámetro idUsuario");

        }

        //Codigo anterior
        /*BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new Gson();

        //Traigo los datos nuevos del Usuario Editado en el JSP
        Usuario usuarioJspRecibido = gson.fromJson(sb.toString(), Usuario.class);

        //Busco el usuario ORIGINAL en la BD, por su ID
        Usuario usuarioOriginal = controlLogica.traerUsuario(usuarioJspRecibido.getIdUsuario());

        JsonObject jsonObjRol = JsonParser.parseString(sb.toString()).getAsJsonObject();
        int idRol = jsonObjRol.get("rol").getAsInt();
        Rol rolSeleccionado = controlLogica.traerRol(idRol);

        if (usuarioOriginal != null) {
            // Paso 3: Actualizar campos
            usuarioOriginal.setNombreUsuario(usuarioJspRecibido.getNombreUsuario());
            usuarioOriginal.setContrasenia(usuarioJspRecibido.getContrasenia());
            usuarioOriginal.setTipoRol(rolSeleccionado);

            try {
                // Paso 4: Guardar cambios
                controlLogica.editarUsuario(usuarioOriginal);

                //Envia un Ok al editarUsuario.jsp
                response.setStatus(HttpServletResponse.SC_OK);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error al editar usuario" + e.getMessage());
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
        }*/
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            Usuario usuario = controlLogica.traerUsuario(idUsuario);

            if (usuario != null) {

                controlLogica.borrarUsuario(idUsuario);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {

                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al eliminar el usuario");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    //Aca Traigo la Lista de Usuarios Actualizada
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Usuario> listUsers = new ArrayList<Usuario>();

        listUsers = controlLogica.getListUsers();

        /*ChatGPT me lo dijo, Ya que Quiero hacer el proyecto con menos Servlets*/
        request.setAttribute("listUsers", listUsers);
        request.getRequestDispatcher("listaUsuarios.jsp").forward(request, response);
    }

    //Aca traigo 1 Usuario, muestro los datos del Usuario que Quiero EDITAR
    private void buscarUsuarioPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Traigo al Servlet, el id del Usuario Elejido en listaUsuarios.jsp
        int id = Integer.parseInt(request.getParameter("id"));

        //Busco ese Usuario en la BD
        Usuario usu = controlLogica.traerUsuario(id);

        //Traigo la session del Cliente
        HttpSession misession = request.getSession();
        //Le paso como atributo al Cliente, el Usuario que Quiere Editar
        misession.setAttribute("usuEditar", usu);

        //Solo es una prueba para ver si llega bien el Usuario
        System.out.println("El usuario es: " + usu.getNombreUsuario());

        UtilidadesServlet.listaRoles(request, controlLogica);

        //Redirijo a la pestaña editarUsuario.jsp
        //response.sendRedirect("editarUsuario.jsp");
    }

    private Map<String, String> validarDatos(JsonObject jsonObj) {

        Map<String, String> errores = new HashMap<>();

        /*Ahora, intenta obtener el ID mediante el JSON,
                debido a 'jsonObj.get("id").getAsInt()':*/
        int id = jsonObj.get("idUsuario").getAsInt();
        Usuario usu = controlLogica.traerUsuario(id);

        /*Ya obtuve el Usuario, ahora busco que contraseña Actual tiene*/
        String hashGuardado = usu.getContrasenia();

        /*Valido cada Input y Combo*/
        String nombre = jsonObj.get("nombreUsuario").getAsString().trim();
        if (nombre.isEmpty() || nombre.isBlank() || !nombre.matches("^[a-zA-Z0-9.,]{8,}$")) {
            errores.put("nombreUsuario", "Campo Requerido.\nMínino 8 caracteres.\nSin espacios.");
        }

        int idRol = jsonObj.get("rol").getAsInt();
        if (idRol <= 0) {
            errores.put("rol", "Seleccione una opción");
        }

        //====================================================================
        /*Pregunto si el Objeto JSON tiene dentro algun atributo llamado
        'contrasenia'*/
        String contraActual = "";
        if (jsonObj.has("contrasenia")) {

            /*Ahora, intenta obtener el ID mediante el JSON,
                debido a 'jsonObj.get("id").getAsInt()':*/
            contraActual = jsonObj.get("contrasenia").getAsString();

            //Prueba para ver si llega el ID desde la URL al Servlet: 
            System.out.println("contraseniaActual que recibe el validarDatos() del JSON es: " + contraActual);
        }

        /*Valido lo que se ingreso en Contraseña*/
        if (contraActual.isEmpty() || contraActual.isBlank() || !contraActual.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {
            errores.put("contrasenia", "Campo Requerido.\nMínino 8 caracteres.\nSin espacios.");

            /*Si lo ingresado cumple las primeras normas. Ahora evaluo que la 
          contraseña exista en la BD*/
        } else {

            // Validar contraseña actual
            if (!BCrypt.checkpw(contraActual, hashGuardado)) {

                errores.put("contrasenia", "Contraseña Incorrecta.");
            }
        }
        
        String contraNueva = "";
        /*Con esto 'jsonObj.has("contraseniaNueva")' pregunto si el JSON tiene un
        atributo llamado "contraseniaNueva"*/
        if (jsonObj.has("contraseniaNueva")) {

            contraNueva = jsonObj.get("contraseniaNueva").getAsString().trim();

            //Prueba para ver si llega el ID desde la URL al Servlet: 
            System.out.println("contraNueva que recibe el validarDatos() del JSON: " + contraNueva);
        }
        
        
 /*Valido la Contraseña Nueva*/
        if (contraNueva.isEmpty() || contraNueva.isBlank() || !contraNueva.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$")) {

            errores.put("contraseniaNueva", "Campo Requerido.\nMínino 8 caracteres.\nSin espacios.");
        }
        
        
        String contraConfirmar = "";
        if (jsonObj.has("contraseniaConfirmar")) {

            contraConfirmar = jsonObj.get("contraseniaConfirmar").getAsString().trim();

            //Prueba para ver si llega el ID desde la URL al Servlet: 
            System.out.println("contraConfirmar que recibe el validarDatos() del JSON: " + contraConfirmar);
        }

        // Validar la confirmacion de la contraseña
        if (contraConfirmar.isEmpty() || contraConfirmar.isBlank() || !contraNueva.equals(contraConfirmar)) {

            errores.put("contraseniaConfirmar", "La nueva contraseña no coincide con la confirmación.");
        }

        return errores;
    }

    private void editarUsuario(Usuario usu, JsonObject jsonObj) {

        //==========  Traigo los datos de cada input y combo:  ================
        //Campos comunes entre Odontologo y Secretario:
        String nombreUsuario = jsonObj.get("nombreUsuario").getAsString();

        int idRol = jsonObj.get("rol").getAsInt();
        Rol rol = controlLogica.traerRol(idRol);

        /*Pregunto si el Objeto JSON tiene dentro algun atributo llamado
        'contrasenia'*/
        if (jsonObj.has("contraseniaNueva")) {

            String contraNueva = jsonObj.get("contraseniaNueva").getAsString().trim();

            //Hashear/Encripto la Nueva Contraseña y la mando a guardar en la BD
            String nuevoHash = BCrypt.hashpw(contraNueva, BCrypt.gensalt());

            // 5. Guardar cambios. Edito el usuario y TAMBIEN LA CONTRASEÑA
            controlLogica.editarUsuario(usu, nombreUsuario, rol, nuevoHash);

            //Prueba para ver si llega el ID desde la URL al Servlet: 
            System.out.println("contrasenia Nueva que recibe el editarUsuario() del JSON es: " + contraNueva);
        }

        // 5. Guardar cambios. Edito el usuario PERO NO la CONTRASEÑA
        controlLogica.editarUsuario(usu, nombreUsuario, rol);

    }

}
