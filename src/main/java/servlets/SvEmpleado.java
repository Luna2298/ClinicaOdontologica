package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Odontologo;
import logica.Persona;
import logica.Secretario;
import logica.TipoDocumento;
import logica.TipoSangre;
import logica.Usuario;

@WebServlet(name = "SvEmpleado", urlPatterns = {"/SvEmpleado"})
public class SvEmpleado extends HttpServlet {

    ControladoraLogica controlLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
            return;

            /*Con esto traigo la Lista de Secretarios*/
        } else if (accion.equals("listarSecre")) {
            listarSecretarios(request, response);

            /*Con esto traigo la Lista de Odontologos*/
        } else if (accion.equals("listarOdonto")) {
            listarOdontologos(request, response);

            /*Con esto BUSCO 1 Secretario para EDITARLO*/
        } else if ("buscarSecre".equals(accion)) {

            String tipoAccion = "editarSecre";
            buscarSecretarioPorId(request, response, tipoAccion);

            //Redirijo a la pestaña editarOdonto.jsp VIEJO
            //request.getRequestDispatcher("editarSecretario.jsp").forward(request, response);
            //Redirijo a la pestaña editarEmpleado.jsp
            request.getRequestDispatcher("editarEmpleado.jsp").forward(request, response);

            /*Con esto BUSCO 1 Odontologo para EDITARLO*/
        } else if ("buscarOdonto".equals(accion)) {

            String tipoAccion = "editarOdo";
            buscarOdontoPorId(request, response, tipoAccion);

            //Redirijo a la pestaña editarOdonto.jsp VIEJO
            //request.getRequestDispatcher("editarOdonto.jsp").forward(request, response);
            //Redirijo a la pestaña editarEmpleado.jsp
            request.getRequestDispatcher("editarEmpleado.jsp").forward(request, response);

        } else if ("infoSecre".equals(accion)) {

            String tipoAccion = "infoSecre";
            buscarSecretarioPorId(request, response, tipoAccion);

            /*Redirecciono a la pestaña de Informacion extra del Secretario*/
            request.getRequestDispatcher("infoSecretario.jsp").forward(request, response);

        } else if ("infoOdonto".equals(accion)) {

            String tipoAccion = "infoOdo";
            buscarOdontoPorId(request, response, tipoAccion);

            /*Redirecciono a la pestaña de Informacion extra del Odontologo*/
            request.getRequestDispatcher("infoOdonto.jsp").forward(request, response);

            /*Con esto BUSCO la Lista de Tipos de Sangre y de Documentos, 
          los necesito para poder enviarlos al nuevoPaciente.jsp y asi Crear un
          Nuevo Paciente. Sin esto, al intentar crear un Paciente, no podre
          ponerle que TipoSangre y Documento posee, ya que no me mostrara la 
          Lista de estos.*/
        } else if ("traerAccesorios".equals(accion)) {

            traerAccesorios(request, response);

            /*Redirecciono a la pestaña de Crear un Nuevo Paciente*/
            request.getRequestDispatcher("nuevoEmpleado.jsp").forward(request, response);
        }
    }

    private void buscarOdontoPorId(HttpServletRequest request, HttpServletResponse response, String tipoAccion)
            throws ServletException, IOException {

        request.removeAttribute("empleado");
        request.removeAttribute("Secretario");
        request.removeAttribute("tipoEmpleado");

        //Obtengo el ID de Odontologo seleccionado en la Lista de Odontologos
        int id = Integer.parseInt(request.getParameter("id"));

        //Busco el Odontologo en la BD
        Odontologo odo = controlLogica.traerOdonto(id);

        //Solo es una prueba para ver si llega bien el Odonto
        System.out.println("El Odontólogo es: " + odo.getApellido());

        String tipoEmpleado = "Odontologo";

        /*Si es 'infoOdo' o sea solo busco mostrar los datos del Odontologo
        en infoOdonto.jsp*/
        if (tipoAccion.equals("infoOdo")) {

            request.setAttribute("odo", odo);

            /*Si es DISTINTO de 'infoOdo' es porque busco al Odontologo solo 
        para EDITARLO en editarEmpleado.jsp*/
        } else {

            /*Traigo la Lista de TipoSangre, TipoDocumento y Usuarios, que necesito
        para poder Editar un Odontologo. De lo contrario, sin estos accesorios,
        al querer cargar el editarEmpleado.jsp no lo hara, ya que falta informacion.*/
            traerAccesorios(request, response);

            /*Asigno el Odontólogo a la request, para que esta
        lo lleve al editarEmpleado.jsp y ahi lo pueda usar*/
            request.setAttribute("empleado", odo);
            request.setAttribute("odontologo", odo);
            request.setAttribute("tipoEmpleado", tipoEmpleado);

            /*
        .setAttribute("tipoEmpleado", "odontologo")
               nombre de la variable,  valor que contiene la variable
             */
        }

    }

    private void listarSecretarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Secretario> listaSecretarios = controlLogica.traerListaSecretarios();

        request.setAttribute("listaSecretarios", listaSecretarios);
        request.getRequestDispatcher("listaSecretarios.jsp").forward(request, response);
    }

    private void listarOdontologos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Odontologo> listaOdonto = new ArrayList<Odontologo>();

        listaOdonto = controlLogica.traerListaOdontologos();

        /*ChatGPT me lo dijo, Ya que Quiero hacer el proyecto con menos Servlets*/
        request.setAttribute("listaOdonto", listaOdonto);
        request.getRequestDispatcher("listaOdontologos.jsp").forward(request, response);

    }

    private void buscarSecretarioPorId(HttpServletRequest request, HttpServletResponse response,
            String tipoAccion)
            throws ServletException, IOException {

        request.removeAttribute("empleado");
        request.removeAttribute("odontologo");
        request.removeAttribute("tipoEmpleado");

        //Obtengo el ID de Secretario seleccionado en la Lista de Secretarios
        int idSecretario = Integer.parseInt(request.getParameter("id"));

        //Busco el Secretario en la BD
        Secretario se = controlLogica.traerSecretario(idSecretario);

        //Solo es una prueba para ver si llega bien el Secretario
        System.out.println("El secretario es: " + se.getApellido());

        String tipoEmpleado = "Secretario";

        if (tipoAccion.equals("infoSecre")) {

            request.setAttribute("secretario", se);

        } else {
            /*Traigo la Lista de TipoSangre, TipoDocumento y Usuarios, que necesito
        para poder Editar un Secretario. De lo contrario, sin estos accesorios,
        al querer cargar el editarEmpleado.jsp no lo hara, ya que falta informacion.*/
            traerAccesorios(request, response);

            /*Asigno el Secretario a la request, para que esta
        lo lleve al editarEmpleado.jsp y ahi lo pueda usar*/
            request.setAttribute("empleado", se);
            request.setAttribute("Secretario", se);
            request.setAttribute("tipoEmpleado", tipoEmpleado);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //========================== Codigo Normal del doPost(): =============
        /*Creo este map para asi guardar cada error o advertencia que se detecte 
        en el Servlet, y luego lo mando por request al nuevoEmpleado.jsp, 
        para asi mostrar cada advertencia al lado de cada input o combo*/
        Map<String, String> errores = new HashMap<>();

        //============== Valido que se haya ingresado/elejido correctamente todo: ===========
        //Veo que tipo de Empleado se va a crear, Odontologo o Secretario:
        //Campos comunes que comparten Odontologo y Secretario:
        if (request.getParameter("nombre") == null
                || !request.getParameter("nombre").matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ\\s]+")) {

            errores.put("nombre", "Campo Requerido. Solo letras");

        }
        if (request.getParameter("apellido") == null
                || !request.getParameter("apellido").matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ\\s]+")) {

            errores.put("apellido", "Campo Requerido. Solo letras");
        }
        if (request.getParameter("dni") == null
                || !request.getParameter("dni").matches("\\d+")) {

            errores.put("dni", "Campo Requerido. Solo numeros");
        }

        if (request.getParameter("telefono") == null
                || !request.getParameter("telefono").matches("\\d+")) {

            errores.put("telefono", "Campo Requerido. Solo numeros");
        }

        //Controlo que fecha de nacimiento no sea null o vacio:
        if (request.getParameter("fechaNacimiento") == null
                || request.getParameter("fechaNacimiento").isEmpty()) {

            errores.put("fechaNacimiento", "Campo Requerido. Ingrese una Fecha");

        } else {

            try {

                //Convierto a LocalDate la fecha ingresada en nuevoEmpleado.jsp:
                LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fechaNacimiento"));

                //Obtengo la fecha actual:
                LocalDate hoy = LocalDate.now();

                /*Calculo el periodo entre la fecha de nacimiento ingresada y la actual,
                  con Period me calcula la edad directamente: */
                Period edad = Period.between(fechaNacimiento, hoy);

                //De edad, pido saber los años, y veo si los años son menor a 18
                if (edad.getYears() < 18) {

                    //Si es menor a 18, que lance la advertencia:
                    errores.put("fechaNacimiento", "El empleado debe ser mayor de 18 años");

                }

                /*El catch se activara en caso de que no se pueda convertir 
              'fechaNacimiento' en LocalDate:*/
            } catch (DateTimeParseException e) {

                //Y se mostrara la advertencia:
                errores.put("fechaNacimiento", "Formato de fecha inválido");
            }
        }

        if (request.getParameter("tipo_Sangre") == null
                || request.getParameter("tipo_Sangre").isEmpty()) {

            errores.put("tipo_Sangre", "Campo Requerido. Elija una opcion");
        }

        if (request.getParameter("tipoDoc") == null
                || request.getParameter("tipoDoc").isEmpty()) {

            errores.put("tipoDoc", "Campo Requerido. Elija una opcion");
        }

        if (request.getParameter("usuario") == null
                || request.getParameter("usuario").isEmpty()) {

            /*Si el usuario es null o vacio, entonces se crea un nuevo item
              en el map, donde se guardara el mensaje de error correspondiente
              a usuario. Y el cual se mostrara cuando haga falta 
              en su debido espacio*/
            errores.put("usuario", "Campo Requerido. Elija una opcion");
        }

        //Si el mapa de errores es DISTINTO de Vacio
        if (!errores.isEmpty()) {

            //Le asigno como Atributo a la request TODOS los Errores
            request.setAttribute("errores", errores);

            /*Traigo la lista de Accesorios, y los asigno a la request. De lo contrario,
              el nuevoEmpleado.jsp no se mostrara, ya que le falta contenido, o sea
              le falta la info a los combos*/
            traerAccesorios(request, response);

            //Redirecciono a nuevoEmpleado.jsp
            request.getRequestDispatcher("nuevoEmpleado.jsp").forward(request, response);
            return;
        }

        //Si todo esta BIEN ingresado.
        //==========  Traigo los datos de cada input y combo:  ================
        //Campos comunes entre Odontologo y Secretario:
        String tipoEmpleado = request.getParameter("tipoEmpleado");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String fechaNac = request.getParameter("fechaNacimiento");

        int idTipoSangre = Integer.parseInt(request.getParameter("tipo_Sangre"));
        TipoSangre tipoSangre = controlLogica.traerTipoSangre(idTipoSangre);

        int idTipoDoc = Integer.parseInt(request.getParameter("tipoDoc"));
        TipoDocumento tipoDoc = controlLogica.traerTipoDocumento(idTipoDoc);

        /*Me trae el ID seleccionado en el combo*/
        int idUsuario = Integer.parseInt(request.getParameter("usuario"));
        /*El ID de Usuario que se selecciono lo busco en la BD*/
        Usuario usuario = controlLogica.traerUsuario(idUsuario);

        try {

            if (tipoEmpleado.equals("Odontólogo")) {

                //Campo especifico de Odontologo:
                String especialidad = request.getParameter("especialidad");

                //Solo es una prueba para ver si llega bien el Odontologo
                System.out.println("El Odontologo es: " + nombre);

                controlLogica.crearOdonto(nombre, apellido, dni, telefono, direccion,
                        fechaNac, especialidad, tipoSangre, tipoDoc, usuario);

            } else if (tipoEmpleado.equals("Secretario")) {

                //Campo especifico de Secretario:
                String sector = request.getParameter("sector");

                //Solo es una prueba para ver si llega bien el Secretario
                System.out.println("El Secretario es: " + nombre);

                controlLogica.crearSecretario(nombre, apellido, dni, telefono, direccion,
                        fechaNac, tipoSangre, tipoDoc, sector, usuario);
            }

        } catch (Exception e) {

            e.printStackTrace();
            request.setAttribute("error", "Error al crear el empleado: " + e.getMessage());
            traerAccesorios(request, response);
            request.getRequestDispatcher("nuevoEmpleado.jsp").forward(request, response);
        }

        response.sendRedirect("SvEmpleado?accion=traerAccesorios");
    }

    //Aca Edito un Empleado
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 2. Leer JSON del body
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(reader, JsonObject.class);

        //========== ↓↓↓ VALIDACIONES ↓↓↓ =======================================
        Map<String, String> erroresAlEditar = validarDatos(jsonObj);

        //Si el mapa de errores es DISTINTO de Vacio
        if (!erroresAlEditar.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String jsonErrores = new Gson().toJson(erroresAlEditar);
            System.out.println("JSON de errores enviado al frontend: " + jsonErrores); // <-- debug

            response.getWriter().write(new Gson().toJson(erroresAlEditar));
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
        String idStr = request.getParameter("id");

        //Prueba para ver si llega el ID desde la URL al Servlet: 
        System.out.println("id que recibe el doPut() es desde la URL: " + idStr);

        /*Verifico si llego el ID*/
        if (idStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Falta el parámetro id\"}");
            return;
        }

        /*Intento convertir de String a Int el ID de la URL:*/
        int id = Integer.parseInt(idStr);

        /*Pero si no obtiene el ID mediante la URL, ya que ID le da null,
        pero aun asi en el Objeto JSON exite una constante llamada "id".
        Entonces...*/
        if (jsonObj.has("id")) {

            /*Ahora, intenta obtener el ID mediante el JSON,
                debido a 'jsonObj.get("id").getAsInt()':*/
            id = jsonObj.get("id").getAsInt();
            //Prueba para ver si llega el ID desde la URL al Servlet: 
            System.out.println("id que recibe el doPut() es desde el JSON: " + id);
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

            // 3. Obtener tipoEmpleado (prioriza URL, si no existe usa JSON)
            /*Intenta obtener el tipoEmpleado mediante la URL, 
            gracias a 'request.getParameter("tipoEmpleado")':*/
            String tipoEmpleado = request.getParameter("tipoEmpleado");

            /*Pero si no lo obtiene mediante la URL, ya que tipoEmpleado le da null,
            pero aun asi en el Objeto JSON exite una constante llamada "tipoEmpleado".
            Entonces...*/
            if (tipoEmpleado == null && jsonObj.has("tipoEmpleado")) {

                /*Ahora, intenta obtener el tipoEmpleado mediante el JSON,
                debido a 'jsonObj.get("tipoEmpleado").getAsString()':*/
                tipoEmpleado = jsonObj.get("tipoEmpleado").getAsString();
            }

            /*Aca verifico haber obtenido el tipoEmpleado, por alguna de las
            2 opciones, o sea URL o JSON. De no obtenerlo, que muestre un 
            mensaje de error.*/
            if (tipoEmpleado == null) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Falta el parámetro tipoEmpleado\"}");
                return;
            }

            /*Si obtuvo el tipoEmpleado, veo si es Odontologo.*/
            if (tipoEmpleado.equalsIgnoreCase("Odontologo")) {

                /*Como es Odontologo, entonces trato de buscar y modificar el Odontologo*/
                // 3. Buscar Odontologo
                Odontologo odo = controlLogica.traerOdonto(id);

                /*Si no lo encuentra en la BD*/
                if (odo == null) {

                    /*Muestra un mensaje de error*/
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Odontologo no encontrado\"}");
                    return;

                }

                editarEmpleado(odo, jsonObj);

                Map<String, String> mensaje = new HashMap<>();
                mensaje.put("status", "success");
                mensaje.put("tipoEmpleado", "Odontologo");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(mensaje));

            } else if (tipoEmpleado.equalsIgnoreCase("Secretario")) {

                /*Como es Secretario, entonces trato de buscar y modificar el Secretario*/
                // 3. Buscar Secretario
                Secretario se = controlLogica.traerSecretario(id);

                /*Si no lo encuentra en la BD*/
                if (se == null) {

                    /*Muestra un mensaje de error*/
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Secretario no encontrado\"}");
                    return;
                }

                editarEmpleado(se, jsonObj);

                Map<String, String> mensaje = new HashMap<>();
                mensaje.put("status", "success");
                mensaje.put("tipoEmpleado", "Secretario");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new Gson().toJson(mensaje));

            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");

            System.out.println("Falta el parámetro tipoEmpleado");

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void traerAccesorios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*Busco las 3 Listas, para luego enviarlas a editarOdonto.jsp,
            y poder editar al Odontologo con su correspondiente 
            TipoSangre, TipoDocumento y Usuario*/
        UtilidadesServlet.listaTiposSangre(request, controlLogica);
        UtilidadesServlet.listaTiposDocumentos(request, controlLogica);
        UtilidadesServlet.listaUsuarios(request, controlLogica);
    }

    private void editarEmpleado(Object obj, JsonObject jsonObj) {

        boolean esOdonto = obj instanceof Odontologo;
        boolean esSecre = obj instanceof Secretario;

        //==========  Traigo los datos de cada input y combo:  ================
        //Campos comunes entre Odontologo y Secretario:
        String nombre = jsonObj.get("nombre").getAsString();
        String apellido = jsonObj.get("apellido").getAsString();
        String dni = jsonObj.get("dni").getAsString();
        String telefono = jsonObj.get("telefono").getAsString();
        String direccion = jsonObj.get("direccion").getAsString();

        /*Devuelve la fecha en String*/
        String fechaStr = jsonObj.get("fechaNacimiento").getAsString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaFormateada = null;
        try {
            /*Convierto la FECHA de String a Date*/
            fechaFormateada = sdf.parse(fechaStr);
            System.out.println("fecha Nac: " + fechaFormateada);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        int idTipoDoc = jsonObj.get("tipoDoc").getAsInt();
        TipoDocumento tipoDoc = controlLogica.traerTipoDocumento(idTipoDoc);

        int idTipo_Sangre = jsonObj.get("tipo_Sangre").getAsInt();
        TipoSangre tipo_Sangre = controlLogica.traerTipoSangre(idTipo_Sangre);

        int idUsuario = jsonObj.get("usuario").getAsInt();
        Usuario usuario = controlLogica.traerUsuario(idUsuario);

        //=====================================================
        if (esOdonto) {

            String especialidad = jsonObj.get("especialidad").getAsString();

            /*Convierto de Object a Odontologo*/
            Odontologo odo = (Odontologo) obj;

            // 5. Guardar cambios
            controlLogica.editarOdonto(odo, nombre, apellido, dni, telefono,
                    direccion, fechaFormateada, tipoDoc, tipo_Sangre, usuario, especialidad);

        } else if (esSecre) {

            String sector = jsonObj.get("sector").getAsString();

            /*Convierto de Object a Odontologo*/
            Secretario se = (Secretario) obj;

            // 5. Guardar cambios
            controlLogica.editarSecretario(se, nombre, apellido, dni, telefono,
                    direccion, fechaFormateada, tipoDoc, tipo_Sangre, usuario, sector);

        }

    }

    private Map<String, String> validarDatos(JsonObject jsonObj) {

        Map<String, String> errores = new HashMap<>();

        /*Valido cada Input y Combo*/
        String nombre = jsonObj.get("nombre").getAsString().trim();
        if (nombre.isEmpty() || nombre.isBlank() || !nombre.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ\\s]+")) {
            errores.put("nombre", "Campo Requerido. Solo letras");
        }

        String apellido = jsonObj.get("apellido").getAsString();
        if (apellido.isEmpty() || apellido.isBlank() || !apellido.matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ\\s]+")) {
            errores.put("apellido", "Campo Requerido. Solo letras");
        }

        String dni = jsonObj.get("dni").getAsString().trim();
        if (dni.isEmpty() || dni.isBlank()) {
            errores.put("dni", "Campo Requerido. Solo numeros");

        } else if (!dni.matches("\\d+")) {
            errores.put("dni", "Campo Requerido. Solo numeros");

        }
        /*
        controlLogica.existeDNI(dni, id) es un método que deberías crear para 
        verificar si ya existe ese DNI en otro registro 
        (excluyendo el que se está editando).
        
        else if (controlLogica.existeDNI(dni, id)) {
            errores.put("dni","El DNI ya está registrado en otro empleado");
        }*/

        String telefono = jsonObj.get("telefono").getAsString().trim();
        if (telefono.isEmpty() || telefono.isBlank() || !telefono.matches("\\d+")) {
            errores.put("telefono", "Campo Requerido. Solo numeros");
        }

        String fechaNacimiento = jsonObj.get("fechaNacimiento").getAsString().trim();

        //Controlo que fecha de nacimiento no sea null o vacio:
        if (fechaNacimiento == null
                || fechaNacimiento.isEmpty()) {

            errores.put("fechaNacimiento", "Campo Requerido. Ingrese una Fecha");

        } else {

            try {

                //Convierto a LocalDate la fecha ingresada en nuevoEmpleado.jsp:
                LocalDate fechaNac = LocalDate.parse(fechaNacimiento);

                //Obtengo la fecha actual:
                LocalDate hoy = LocalDate.now();

                /*Calculo el periodo entre la fecha de nacimiento ingresada y la actual,
                  con Period me calcula la edad directamente: */
                Period edad = Period.between(fechaNac, hoy);

                //De edad, pido saber los años, y veo si los años son menor a 18
                if (edad.getYears() < 18) {

                    //Si es menor a 18, que lance la advertencia:
                    errores.put("fechaNacimiento", "El empleado debe ser mayor de 18 años");

                }

                /*El catch se activara en caso de que no se pueda convertir 
              'fechaNacimiento' en LocalDate:*/
            } catch (DateTimeParseException e) {

                //Y se mostrara la advertencia:
                errores.put("fechaNacimiento", "Formato de fecha inválido");
            }
        }

        //String fecha_nac = jsonObj.get("fecha_nac").getAsString().trim();
        int idTipoDoc = jsonObj.get("tipoDoc").getAsInt();
        if (idTipoDoc <= 0) {
            errores.put("tipoDoc", "Seleccione una opción");
        }

        int idTipoSangre = jsonObj.get("tipo_Sangre").getAsInt();
        if (idTipoSangre <= 0) {
            errores.put("tipo_Sangre", "Seleccione una opción");
        }

        int idUsuario = jsonObj.get("usuario").getAsInt();
        if (idUsuario <= 0) {
            errores.put("usuario", "Seleccione una opción");
        }

        return errores;
    }

}
