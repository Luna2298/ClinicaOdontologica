package servlets;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.ControladoraLogica;
import logica.Usuario;


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
        
        String nombreUsuario = request.getParameter("nombreusuario");
        String contra = request.getParameter("contra");
        String rol = request.getParameter("rol");
        
        controlLogica.crearUsuario(nombreUsuario, contra, rol);
        
        response.sendRedirect("SvUsuarios");
    }

    //Aca Edito un Usuario
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
        Usuario usuarioJspRecibido = gson.fromJson(sb.toString(), Usuario.class);
        
        //Busco el usuario ORIGINAL en la BD, por su ID
        Usuario usuarioOriginal = controlLogica.traerUsuario(usuarioJspRecibido.getIdUsuario());
        

        if (usuarioOriginal != null) {
            // Paso 3: Actualizar campos
            usuarioOriginal.setNombreUsuario(usuarioJspRecibido.getNombreUsuario());
            usuarioOriginal.setContrasenia(usuarioJspRecibido.getContrasenia());
            usuarioOriginal.setRol(usuarioJspRecibido.getRol());

            try{
                // Paso 4: Guardar cambios
                controlLogica.editarUsuario(usuarioOriginal);
                
                //Envia un Ok al editarUsuario.jsp
                response.setStatus(HttpServletResponse.SC_OK);
                
            } catch(Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                        "Error al editar usuario" + e.getMessage());
            }
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
        }
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
        
        //Redirijo a la pestaña editarUsuario.jsp
        response.sendRedirect("editarUsuario.jsp");
    }

}
