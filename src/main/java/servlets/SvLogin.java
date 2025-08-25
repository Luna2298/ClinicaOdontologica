package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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

        Map<String, String> errorUsuario = validarUsuario(usuario, contra);

        //Si el mapa de errores es DISTINTO de Vacio
        if (!errorUsuario.isEmpty()) {

            //Le asigno como Atributo a la request TODOS los Errores
            request.setAttribute("errorUsuario", errorUsuario);

            //Redirecciono a nuevoEmpleado.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;

        }

        System.out.println("Prueba: ");
        HttpSession misession = request.getSession(true);
        misession.setAttribute("usuario", usuario);
        response.sendRedirect("index.jsp");

        /*boolean valido = false;
        valido = controlLogica.validarLogin(usuario, contra);

        if (valido) {

            HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);
            response.sendRedirect("index.jsp");

        } else {

            String mensajeError = controlLogica.enviarMensaje();
            System.out.println("mensajeError:" + mensajeError);
            request.setAttribute("mensajeError", mensajeError);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            //response.sendRedirect("loginError.jsp");
        }*/
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private Map<String, String> validarUsuario(String usuario, String contra) {

        Map<String, String> errores = new HashMap<>();
        boolean valido = false;
        String mensajeError = "";

        if (usuario.isBlank() || usuario.isEmpty()) {

            errores.put("usuario", "Campo Requerido.\nIngrese un usuario");

        }

        if (contra.isBlank() || contra.isEmpty()) {

            errores.put("contrasenia", "Campo Requerido.\nIngrese la contraseña");

        }

        if (usuario != null && contra != null) {

            valido = controlLogica.validarLogin(usuario, contra);
            System.out.println("valido: " + valido);
        }

        if (!valido) {

            /*HttpSession misession = request.getSession(true);
            misession.setAttribute("usuario", usuario);
            response.sendRedirect("index.jsp");*/
            mensajeError = controlLogica.enviarMensaje();

        }

        if (mensajeError != null) {

            if (mensajeError.toLowerCase().contains("contraseña")) {

                errores.put("contrasenia", mensajeError);
            } else {
                errores.put("usuario", mensajeError);
            }
        }
        return errores;
    }

}
