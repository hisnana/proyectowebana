package es.cursojava.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class calculadora
 */
@WebServlet("/calculadoraServlet")
public class CalculadoraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CalculadoraServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   response.setContentType("text/html; charset=UTF-8");

		    String op = request.getParameter("op");
		    String aStr = request.getParameter("num1");
		    String bStr = request.getParameter("num2");

		    int num1 = Integer.parseInt(aStr);
		    int num2= Integer.parseInt(bStr);

		    int resultado;

		    switch (op) {
		      case "suma":
		        resultado = num1 + num2;
		        break;
		      case "resta":
		        resultado = num1 - num2;
		        break;
		      case "multi":
		        resultado = num1 * num2;
		        break;
		      default:
		        response.getWriter().println("<h1>Operación no válida</h1>");
		        response.getWriter().println("<a href='calculadora.html'>Volver</a>");
		        return;
		    }

		    response.getWriter().println("<!doctype html>");
		    response.getWriter().println("<html lang='es'><head><meta charset='UTF-8'><title>Resultado</title></head><body>");
		    response.getWriter().println("<h1>Resultado</h1>");
		    response.getWriter().println("<p>Operación: <b>" + op + "</b></p>");
		    response.getWriter().println("<p>" + num1 + " y " + num2 + " => <b>" + resultado + "</b></p>");
		    response.getWriter().println("<p><a href='calculadora.html'>Volver a la calculadora</a></p>");
		    response.getWriter().println("</body></html>");
}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");

		    PrintWriter out = response.getWriter();

		    try {
		      double num1 = Double.parseDouble(request.getParameter("a"));
		      double num2 = Double.parseDouble(request.getParameter("b"));
		      String op = request.getParameter("op");

		      double resultado;

		      switch (op) {
		        case "suma":
		          resultado = num1 + num2;
		          break;
		        case "resta":
		          resultado = num1 - num2;
		          break;
		        case "multi":
		          resultado = num1 * num2;
		          break;

		        default:
		          throw new IllegalArgumentException("Operación no válida");
		      }

		      out.println("<!doctype html>");
		      out.println("<html lang='es'><head><meta charset='UTF-8'><title>Resultado</title></head><body>");
		      out.println("<h1>Resultado</h1>");
		      out.println("<p><b>A:</b> " + num1 + "</p>");
		      out.println("<p><b>B:</b> " + num2 + "</p>");
		      out.println("<p><b>Operación:</b> " + op + "</p>");
		      out.println("<p><b>Resultado:</b> " + resultado + "</p>");
		      out.println("<p><a href='calculadora_post.html'>Volver</a></p>");
		      out.println("</body></html>");

		    } catch (Exception e) {
		      out.println("<h1>Error</h1>");
		      out.println("<p>" + e.getMessage() + "</p>");
		      out.println("<p><a href='calculadora_post.html'>Volver</a></p>");
		    }
	}

}
