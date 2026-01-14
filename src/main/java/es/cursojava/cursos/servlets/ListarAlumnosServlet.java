package es.cursojava.cursos.servlets;

import es.cursojava.cursos.entity.Alumno;
import es.cursojava.cursos.service.AlumnoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que muestra la lista de alumnos guardados en la BD.
 */
@WebServlet("/listarAlumnos")
public class ListarAlumnosServlet extends HttpServlet {

    // Servicio que encapsula la lógica de acceso a BD
    private final AlumnoService alumnoService = new AlumnoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Pedimos al servicio todos los alumnos
        List<Alumno> alumnos = alumnoService.listarAlumnos();

        // Metemos la lista en el request para que la JSP pueda usarla
        request.setAttribute("alumnos", alumnos);

        // Hacemos forward a la página JSP que mostrará la tabla
        request.getRequestDispatcher("/listaAlumnos.jsp")
               .forward(request, response);
    }
}
