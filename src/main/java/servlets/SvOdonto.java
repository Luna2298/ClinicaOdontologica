package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.ControladoraLogica;
import logica.Odontologo;
import logica.TipoDocumento;
import logica.TipoSangre;
import logica.Usuario;


@WebServlet(name = "SvOdonto", urlPatterns = {"/SvOdonto"})
public class SvOdonto extends HttpServlet {

    ControladoraLogica controlLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //String accion = request.getParameter("accion");

        /*Con esto traigo la Lista de Odontologos*/
        /*if (accion.equals("listarOdonto")) {
            listarOdontologos(request, response);
            
        /*Con esto BUSCO 1 Odontologo para EDITARLO*/
        /*} else if ("buscar".equals(accion)) {
            
            buscarOdontoPorId(request, response);

            /*Busco Ambas Listas, para luego enviarlas a nuevoOdontologo.jsp,
            y poder crear al Nuevo Odontologo con su correspondiente 
            TipoSangre y TipoDocumento*/
            /*UtilidadesServlet.listaTiposSangre(request, controlLogica);
            UtilidadesServlet.listaTiposDocumentos(request, controlLogica);
        
            //Redirijo a la pestaña editarOdonto.jsp
            request.getRequestDispatcher("editarOdonto.jsp").forward(request, response);
            
            
        } else if ("info".equals(accion)) {
            
            buscarOdontoPorId(request, response);
            
            /*Redirecciono a la pestaña de Informacion extra del Odontologo*/
            /*request.getRequestDispatcher("infoOdonto.jsp").forward(request, response);
            
        /*Con esto BUSCO la Lista de Tipos de Sangre y de Documentos, 
          los necesito para poder enviarlos al nuevoPaciente.jsp y asi Crear un
          Nuevo Odontologo. Sin esto, al intentar crear un Odontologo, no podre
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

    
    //Creo el Odontologo
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String fechaNac = request.getParameter("fechaNacimiento");
        String especialidad = request.getParameter("especialidad");
        
        int idTipoSangre = Integer.parseInt(request.getParameter("tipo_Sangre"));
        TipoSangre tipoSangre = controlLogica.traerTipoSangre(idTipoSangre);
        
        int idTipoDoc = Integer.parseInt(request.getParameter("tipoDoc"));
        TipoDocumento tipoDoc = controlLogica.traerTipoDocumento(idTipoDoc);
        
        /*Me trae el ID seleccionado en el combo*/
        int idUsuario = Integer.parseInt(request.getParameter("usuario"));
        /*El ID de Usuario que se selecciono lo busco en la BD*/
        Usuario usuario = controlLogica.traerUsuario(idUsuario);
        
        
        //Solo es una prueba para ver si llega bien el Odontologo
        System.out.println("El Odontologo es: " + nombre);
        
        controlLogica.crearOdonto(nombre, apellido, dni, telefono, direccion, 
                fechaNac, especialidad, tipoSangre, tipoDoc, usuario);
        
        response.sendRedirect("SvEmpleado?accion=traerAccesorios");
    }
    
    
    //Aca Edito un Odontologo
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
        
        //Traigo los datos nuevos del Usuario Editado en el JSP
        Odontologo odontoJspRecibido = gson.fromJson(sb.toString(), Odontologo.class);
        
        //Busco el usuario ORIGINAL en la BD, por su ID
        Odontologo odontoOriginal = controlLogica.traerOdonto(odontoJspRecibido.getId());
        
        //Prueba
        System.out.println("telefono odonto: " + odontoOriginal.getTelefono());
        
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
        
        //Obtengo el Usuario que posee el Odontologo
        int idUsu = odontoJspRecibido.getUsuario().getIdUsuario();

        //Traigo el objeto Usuario desde la BD
        Usuario usuarioSeleccionado = controlLogica.traerUsuario(idUsu);

        if (odontoOriginal != null) {
            // Paso 3: Actualizar campos
            odontoOriginal.setNombre(odontoJspRecibido.getNombre());
            odontoOriginal.setApellido(odontoJspRecibido.getApellido());
            odontoOriginal.setDni(odontoJspRecibido.getDni());
            odontoOriginal.setTelefono(odontoJspRecibido.getTelefono());
            odontoOriginal.setDireccion(odontoJspRecibido.getDireccion());
            odontoOriginal.setFecha_nac(odontoJspRecibido.getFecha_nac());
            odontoOriginal.setEspecialidad(odontoJspRecibido.getEspecialidad());
            odontoOriginal.setTipoSangre(tipoSeleccionado);
            odontoOriginal.setTipoDocumento(tipoDocSeleccionado);
            odontoOriginal.setUsuario(usuarioSeleccionado);
            
            //odontoOriginal.setHorarioTrabajo(odontoJspRecibido.getHorarioTrabajo());
            //odontoOriginal.setUsuario(odontoJspRecibido.getUsuario());
            

            try{
                // Paso 4: Guardar cambios
                controlLogica.editarOdonto(odontoOriginal);
                
                //Envia un Ok al editarUsuario.jsp
                response.setStatus(HttpServletResponse.SC_OK);
                
            } catch(Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                        "Error al editar odontólogo" + e.getMessage());
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Odontólogo no encontrado");
        }
    }

    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        try {
            /*
            El parametro 'idOdonto' llega del fetch("SvOdonto?idOdonto=" + idOdo, */
            int idOdonto = Integer.parseInt(request.getParameter("idOdonto"));

            Odontologo odo = controlLogica.traerOdonto(idOdonto);

            if (odo != null) {
                
               controlLogica.borrarOdonto(idOdonto);
               response.setStatus(HttpServletResponse.SC_OK);
            } else {
                
               response.sendError(HttpServletResponse.SC_NOT_FOUND, "Odontólogo no encontrado");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Error al eliminar el odontólogo");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    /*private void listarOdontologos(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        List<Odontologo> listaOdonto = new ArrayList<Odontologo>();
        
        listaOdonto = controlLogica.traerListaOdontologos();
        
        /*ChatGPT me lo dijo, Ya que Quiero hacer el proyecto con menos Servlets*/
        /*request.setAttribute("listaOdonto", listaOdonto);
        request.getRequestDispatcher("listaOdontologos.jsp").forward(request, response);
        
    }

    private void buscarOdontoPorId(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        Odontologo odo = controlLogica.traerOdonto(id);
        
        //Solo es una prueba para ver si llega bien el Odonto
        System.out.println("El Odontólogo es: " + odo.getApellido());

        /*Asigno el Odontólogo a la request, para que esta
        lo lleve al JSP y ahi lo pueda usar*/
        /*request.setAttribute("odo", odo);
        
    }*/

}
