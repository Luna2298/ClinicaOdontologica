package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logica.ControladoraLogica;


@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

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
        
        String usuario = request.getParameter("usuario");
        String contra = request.getParameter("contrasenia");
        
        boolean valido = false;
        valido = controlLogica.validarLogin(usuario, contra);
        
        if (valido) {
            
            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);
            response.sendRedirect("index.jsp");
            
        } else {
        
            response.sendRedirect("loginError.jsp");
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
