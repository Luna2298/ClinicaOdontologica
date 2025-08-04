package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

            buscarSecretarioPorId(request, response);

            //Redirijo a la pestaña editarOdonto.jsp
            request.getRequestDispatcher("editarSecretario.jsp").forward(request, response);

            /*Con esto BUSCO 1 Odontologo para EDITARLO*/
        } else if ("buscarOdonto".equals(accion)) {

            buscarOdontoPorId(request, response);

            //Redirijo a la pestaña editarOdonto.jsp
            request.getRequestDispatcher("editarOdonto.jsp").forward(request, response);

        } else if ("infoSecre".equals(accion)) {

            buscarSecretarioPorId(request, response);

            /*Redirecciono a la pestaña de Informacion extra del Secretario*/
            request.getRequestDispatcher("infoSecretario.jsp").forward(request, response);

        } else if ("infoOdonto".equals(accion)) {

            buscarOdontoPorId(request, response);

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

    private void buscarOdontoPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Odontologo odo = controlLogica.traerOdonto(id);

        //Solo es una prueba para ver si llega bien el Odonto
        System.out.println("El Odontólogo es: " + odo.getApellido());

        traerAccesorios(request, response);

        /*Asigno el Odontólogo a la request, para que esta
        lo lleve al JSP y ahi lo pueda usar*/
        request.setAttribute("odo", odo);

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

    private void buscarSecretarioPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idSecretario = Integer.parseInt(request.getParameter("id"));

        Secretario se = controlLogica.traerSecretario(idSecretario);

        //Solo es una prueba para ver si llega bien el Usuario
        System.out.println("El secretario es: " + se.getApellido());

        traerAccesorios(request, response);

        request.setAttribute("secretario", se);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*Creo este map para asi guardar cada error o advertencia que se detecte 
        en el Servlet, y luego lo mando por request al nuevoEmpleado.jsp, 
        para asi mostrar cada advertencia al lado de cada input o combo*/
        Map<String, String> errores = new HashMap<>();

        //============== Valido que se haya ingresado/elejido correctamente todo: ===========
        //Veo que tipo de Empleado se va a crear, Odontologo o Secretario:
        if (request.getParameter("tipoEmpleado") == null
                || request.getParameter("tipoEmpleado").isEmpty()) {

            errores.put("tipoEmpleado", "Campo Requerido. Elija una opcion");
        }

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

            if (tipoEmpleado.equals("odontologo")) {

                //Campo especifico de Odontologo:
                String especialidad = request.getParameter("especialidad");

                //Solo es una prueba para ver si llega bien el Odontologo
                System.out.println("El Odontologo es: " + nombre);

                controlLogica.crearOdonto(nombre, apellido, dni, telefono, direccion,
                        fechaNac, especialidad, tipoSangre, tipoDoc, usuario);

            } else if (tipoEmpleado.equals("secretario")) {

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

}
