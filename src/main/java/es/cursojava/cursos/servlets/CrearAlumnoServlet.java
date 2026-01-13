package es.cursojava.cursos.servlets;

import es.cursojava.cursos.dto.AlumnoDTO;
import es.cursojava.cursos.dto.ResultadoAltaAlumno;
import es.cursojava.cursos.entity.Alumno;
import es.cursojava.cursos.service.AlumnoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Servlet que recibe el POST del formulario, crea el DTO,
 * llama al service y muestra:
 *  - mensaje de éxito + datos, o
 *  - lista de errores por campo.
 */
@WebServlet(name = "CrearAlumnoServlet", urlPatterns = {"/crearAlumno"})
public class CrearAlumnoServlet extends HttpServlet {

    private final AlumnoService alumnoService = new AlumnoService();
    
    // GET: mostrar formulario vacío
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/nuevoAlumno.jsp")
               .forward(request, response);
    }

    // POST: procesar formulario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        AlumnoDTO dto = new AlumnoDTO();
        dto.setNombre(request.getParameter("nombre"));
        dto.setEmail(request.getParameter("email"));
        dto.setEdad(request.getParameter("edad"));
        dto.setCodigoCurso(request.getParameter("codigoCurso"));

        ResultadoAltaAlumno resultado = alumnoService.crearAlumno(dto);

        if (resultado.isValido()) {
            // éxito: mostramos página de confirmación
            request.setAttribute("alumno", resultado.getAlumno());
            request.getRequestDispatcher("/resultadoAlumno.jsp")
                   .forward(request, response);

        } else {
            // error: volvemos al formulario con los mensajes y los valores
        	// Guardamos en el request el DTO con los datos que ha enviado el usuario,
        	// para poder rellenar de nuevo el formulario con esos mismos valores.
        	request.setAttribute("dto", dto);

        	// Guardamos en el request el mapa de errores de validación,
        	// para que la JSP pueda mostrar los mensajes debajo de cada campo.
        	request.setAttribute("errores", resultado.getErrores());

        	// En lugar de redirigir, hacemos un forward interno al JSP del formulario.
        	// Se vuelve a mostrar nuevoAlumno.jsp en la misma petición, usando "dto" y "errores"
        	// que acabamos de poner en el request.
        	request.getRequestDispatcher("/nuevoAlumno.jsp")
        	       .forward(request, response);
        }
    }
}
