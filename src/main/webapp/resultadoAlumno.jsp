<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.cursojava.cursos.entity.Alumno" %>
<%@ page import="es.cursojava.cursos.entity.Curso" %>

<%
    Alumno alumno = (Alumno) request.getAttribute("alumno");
    Curso curso   = alumno != null ? alumno.getCurso() : null;
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Alumno creado</title>
</head>
<body>
<h1>Alumno creado correctamente</h1>

<% if (alumno != null) { %>
    <p>Nombre: <%= alumno.getNombre() %></p>
    <p>Email: <%= alumno.getEmail() %></p>
    <p>Edad: <%= alumno.getEdad() %></p>

    <% if (curso != null) { %>
        <p>Curso: <%= curso.getCodigo() %> - <%= curso.getNombre() %></p>
    <% } %>
<% } else { %>
    <p>No se han recibido datos de alumno.</p>
<% } %>

</body>
</html>
