package es.cursojava.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/formulariogetafe")
public class FormularioServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // Muestra el HTML del formulario
    request.getRequestDispatcher("/formulario.html").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String nombre = request.getParameter("nombre");
    String edad = request.getParameter("edad");
    String genero = request.getParameter("genero");

    response.setContentType("text/html; charset=UTF-8");

    response.getWriter().println("<!doctype html>");
    response.getWriter().println("<html lang='es'><head><meta charset='UTF-8'><title>Resultado</title></head><body>");
    response.getWriter().println("<h1>Datos recibidos</h1>");
    response.getWriter().println("<p><b>Nombre:</b> " + nombre + "</p>");
    response.getWriter().println("<p><b>Edad:</b> " + edad + "</p>");
    response.getWriter().println("<p><b>Género:</b> " + genero + "</p>");
    response.getWriter().println("<p><a href='formulariogetafe'>Volver al formulario</a></p>");
    response.getWriter().println("</body></html>");
  }
}
