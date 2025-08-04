package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Odontologo;
import logica.TipoSangre;


@WebServlet(name = "SvTipoSangre", urlPatterns = {"/SvTipoSangre"})
public class SvTipoSangre extends HttpServlet {

    ControladoraLogica controlLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {
            listarTiposSangre(request, response);
            
        } else if ("buscar".equals(accion)) {
            //buscarTipoSangrePorId(request, response);
        } 
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

    private void listarTiposSangre(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        /*Busco la Listas de TipoSangre, para luego enviarlas a donde yo desee*/
        UtilidadesServlet.listaTiposSangre(request, controlLogica);
        
    }

}
