package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Paciente;
import logica.Secretario;
import logica.TipoDocumento;
import logica.TipoSangre;
import logica.Usuario;


@WebServlet(name = "SvSecretario", urlPatterns = {"/SvSecretario"})
public class SvSecretario extends HttpServlet {

    ControladoraLogica controlLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*String accion = request.getParameter("accion");

        /*Con esto traigo la Lista de Pacientes*/
        /*if (accion == null || accion.equals("listar")) {
            listarSecretarios(request, response);
            
        /*Con esto BUSCO 1 Paciente para EDITARLO*/
        /*} else if ("buscar".equals(accion)) {
            
            buscarSecretarioPorId(request, response);
        
           /*Busco Ambas Listas, para luego enviarlas a nuevoPaciente.jsp,
            y poder crear al Nuevo Paciente con su correspondiente 
            TipoSangre y TipoDocumento*/
            /*UtilidadesServlet.listaTiposSangre(request, controlLogica);
            UtilidadesServlet.listaTiposDocumentos(request, controlLogica);
        
            //Redirijo a la pestaña editarOdonto.jsp
            request.getRequestDispatcher("editarSecretario.jsp").forward(request, response);
        
        } else if ("info".equals(accion)) {
            
            buscarSecretarioPorId(request, response);
            
            /*Redirecciono a la pestaña de Informacion extra del Paciente*/
            /*request.getRequestDispatcher("infoSecretario.jsp").forward(request, response);
            
        /*Con esto BUSCO la Lista de Tipos de Sangre y de Documentos, 
          los necesito para poder enviarlos al nuevoPaciente.jsp y asi Crear un
          Nuevo Paciente. Sin esto, al intentar crear un Paciente, no podre
          ponerle que TipoSangre y Documento posee, ya que no me mostrara la 
          Lista de estos.*/
        /*} else if ("traerAccesorios".equals(accion)) {
            
            /*Busco Ambas Listas, para luego enviarlas a nuevoPaciente.jsp,
              y poder crear al Nuevo Paciente con su correspondiente 
              TipoSangre, TipoDocumento y Usuarios*/
            /*UtilidadesServlet.listaTiposSangre(request, controlLogica);
            UtilidadesServlet.listaTiposDocumentos(request, controlLogica);
            UtilidadesServlet.listaUsuarios(request, controlLogica);
            
            /*Redirecciono a la pestaña de Crear un Nuevo Paciente*/
            /*request.getRequestDispatcher("nuevoEmpleado.jsp").forward(request, response);
        }*/
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String fechaNac = request.getParameter("fechaNacimiento");

        /*Me trae el ID seleccionado en el combo*/
        int idTipoSangre = Integer.parseInt(request.getParameter("tipo_Sangre"));
        /*El ID de TipoSangre que se selecciono lo busco en la BD*/
        TipoSangre tipoSangre = controlLogica.traerTipoSangre(idTipoSangre);
        
        
        /*Me trae el ID seleccionado en el combo*/
        int idTipoDoc = Integer.parseInt(request.getParameter("tipoDoc"));
        /*El ID de TipoDocumento que se selecciono lo busco en la BD*/
        TipoDocumento tipoDoc = controlLogica.traerTipoDocumento(idTipoDoc);
        
        String sector = request.getParameter("sector");
        
        /*Me trae el ID seleccionado en el combo*/
        int idUsuario = Integer.parseInt(request.getParameter("usuario"));
        /*El ID de Usuario que se selecciono lo busco en la BD*/
        Usuario usuario = controlLogica.traerUsuario(idUsuario);

        //Solo es una prueba para ver si llega bien el Secretario
        System.out.println("El Secretario es: " + nombre);

        controlLogica.crearSecretario(nombre, apellido, dni, telefono, direccion,
                fechaNac, tipoSangre, tipoDoc, sector, usuario);

        /*Se actualiza la misma pestaña, dejando limpio cada input*/
        response.sendRedirect("SvEmpleado?accion=traerAccesorios");
    }
    
    
    //Aca Edito un Secretario
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Gson gson = new Gson();

        //Traigo los datos nuevos del Secretario Editado en el JSP
        Secretario secretarioJspRecibido = gson.fromJson(sb.toString(), Secretario.class);

        //Busco el Secretario ORIGINAL en la BD, por su ID
        Secretario secretarioOriginal = controlLogica.traerSecretario(secretarioJspRecibido.getId());
        System.out.println("telefono secretario: " + secretarioOriginal.getTelefono());

        // ← Esto no sirve si usás JSON
        //int idTipoSangre = Integer.parseInt(request.getParameter("tipo_Sangre"));
        // Mejor: tomalo desde el JSON manualmente
        JsonObject jsonObjTipoSan = JsonParser.parseString(sb.toString()).getAsJsonObject();
        int idTipoSangre = jsonObjTipoSan.get("tipo_Sangre").getAsInt();

        // Traés el objeto desde la BD
        TipoSangre tipoSeleccionado = controlLogica.traerTipoSangre(idTipoSangre);
        
        
        // ← Esto no sirve si usás JSON
        //int idTipoDocumento = Integer.parseInt(request.getParameter("tipoDoc"));
        // Mejor: tomalo desde el JSON manualmente
        JsonObject jsonObjTipoDoc = JsonParser.parseString(sb.toString()).getAsJsonObject();
        int idTipoDoc = jsonObjTipoDoc.get("tipoDoc").getAsInt();

        // Traés el objeto desde la BD
        TipoDocumento tipoDocSeleccionado = controlLogica.traerTipoDocumento(idTipoDoc);
        
        
        
        //Obtengo el Usuario que posee el Secretario
        int idUsu = secretarioJspRecibido.getUsuario().getIdUsuario();

        //Traigo el objeto Usuario desde la BD
        Usuario usuarioSeleccionado = controlLogica.traerUsuario(idUsu);

        if (secretarioOriginal != null) {
            // Paso 3: Actualizar campos
            secretarioOriginal.setNombre(secretarioJspRecibido.getNombre());
            secretarioOriginal.setApellido(secretarioJspRecibido.getApellido());
            secretarioOriginal.setDni(secretarioJspRecibido.getDni());
            secretarioOriginal.setTelefono(secretarioJspRecibido.getTelefono());
            secretarioOriginal.setDireccion(secretarioJspRecibido.getDireccion());
            secretarioOriginal.setFecha_nac(secretarioJspRecibido.getFecha_nac());
            secretarioOriginal.setTipoSangre(tipoSeleccionado);
            secretarioOriginal.setTipoDocumento(tipoDocSeleccionado);
            secretarioOriginal.setSector(secretarioJspRecibido.getSector());
            secretarioOriginal.setUsuario(usuarioSeleccionado);
            
            //Esto va a dar null
            //pacienteOriginal.setTipoSangre(pacienteJspRecibido.getTipoSangre()); 
            //pacienteOriginal.setTipoDocumento(pacienteJspRecibido.getTipoDocumento()); 
            
            try {
                // Paso 4: Guardar cambios
                //controlLogica.editarSecretario(secretarioOriginal);

                //Envia un Ok al editarUsuario.jsp
                response.setStatus(HttpServletResponse.SC_OK);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error al editar secretario" + e.getMessage());
            }

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Secretario no encontrado");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            /*
            El parametro 'idSecretario' llega del fetch("SvSecretario?idSecretario=" + idSe, */
            int idSecretario = Integer.parseInt(request.getParameter("idSecretario"));

            Secretario se = controlLogica.traerSecretario(idSecretario);

            if (se != null) {

                controlLogica.borrarSecretario(idSecretario);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {

                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Secretario no encontrado");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al eliminar el secretario");
        }
    }
    

    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /*private void listarSecretarios(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        List<Secretario> listaSecretarios = controlLogica.traerListaSecretarios();
        
        request.setAttribute("listaSecretarios", listaSecretarios);
        request.getRequestDispatcher("listaSecretarios.jsp").forward(request, response);
    }

    private void buscarSecretarioPorId(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        int idSecretario = Integer.parseInt(request.getParameter("id"));
        
        Secretario se = controlLogica.traerSecretario(idSecretario);
        
        //Solo es una prueba para ver si llega bien el Usuario
        System.out.println("El secretario es: " + se.getApellido());
        
        request.setAttribute("secretario", se);
    }*/

}
