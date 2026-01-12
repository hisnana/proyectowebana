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
@WebServlet("/crearAlumno")
public class CrearAlumnoServlet extends HttpServlet {

    private final AlumnoService alumnoService = new AlumnoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1) Construir DTO a partir de los parámetros del formulario
        AlumnoDTO dto = new AlumnoDTO();
        dto.setNombre(request.getParameter("nombre"));
        dto.setEmail(request.getParameter("email"));
        dto.setEdad(request.getParameter("edad"));
        dto.setCodigoCurso(request.getParameter("codigoCurso"));

        // 2) Llamar al service
        ResultadoAltaAlumno resultado = alumnoService.crearAlumno(dto);

        // 3) Generar respuesta HTML sencilla
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head><meta charset='UTF-8'><title>Resultado alta alumno</title></head>");
            out.println("<body>");

            if (resultado.isValido()) {
                Alumno alumno = resultado.getAlumno();

                out.println("<h1>Alumno creado correctamente</h1>");
                out.println("<p>Nombre: " + alumno.getNombre() + "</p>");
                out.println("<p>Email: " + alumno.getEmail() + "</p>");
                out.println("<p>Edad: " + alumno.getEdad() + "</p>");
                out.println("<p>Curso: " + alumno.getCurso().getCodigo() +
                        " - " + alumno.getCurso().getNombre() + "</p>");

            } else {
                out.println("<h1>Errores en el formulario</h1>");
                out.println("<ul>");
                for (Map.Entry<String, String> entry : resultado.getErrores().entrySet()) {
                    String campo = entry.getKey();
                    String mensaje = entry.getValue();
                    out.println("<li><strong>" + campo + ":</strong> " + mensaje + "</li>");
                }
                out.println("</ul>");
                out.println("<p><a href='nuevoAlumno.html'>Volver al formulario</a></p>");
            }

            out.println("</body></html>");
        }
    }
}
