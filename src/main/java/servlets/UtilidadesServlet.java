package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.TipoDocumento;
import logica.TipoSangre;
import logica.Usuario;


@WebServlet(name = "UtilidadesServlet", urlPatterns = {"/UtilidadesServlet"})
public class UtilidadesServlet extends HttpServlet {

    ControladoraLogica controlLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

    
    public static void listaTiposSangre(HttpServletRequest request, ControladoraLogica controlLogica) 
        throws ServletException, IOException {
        
        List<TipoSangre> listaTiposSangre = controlLogica.traerListaTiposSangre();
        
        request.setAttribute("listaTiposSangre", listaTiposSangre);
    }
    
    
    public static void listaTiposDocumentos(HttpServletRequest request, ControladoraLogica controlLogica) 
        throws ServletException, IOException {
        
        List<TipoDocumento> listaTiposDocumentos = controlLogica.traerListaTipoDocumento();
        
        request.setAttribute("listaTiposDocumentos", listaTiposDocumentos);
    }
    
    public static void listaUsuarios(HttpServletRequest request, ControladoraLogica controlLogica) 
        throws ServletException, IOException {
        
        List<Usuario> listaUsuarios = controlLogica.getListUsers();
        
        request.setAttribute("listaUsuarios", listaUsuarios);
    }
}
