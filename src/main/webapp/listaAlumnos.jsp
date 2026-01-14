<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.cursojava.cursos.entity.Alumno" %>

<%
    // Recuperamos la lista de alumnos que ha puesto el servlet
    List<Alumno> alumnos = (List<Alumno>) request.getAttribute("alumnos");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de alumnos</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 800px; margin: 20px auto; }
        h1 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>

<h1>Alumnos registrados</h1>

<% if (alumnos == null || alumnos.isEmpty()) { %>
    <!-- Si no hay alumnos, mostramos un mensaje sencillo -->
    <p>No hay alumnos registrados en la base de datos.</p>
<% } else { %>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Edad</th>
            <th>Curso (código)</th>
        </tr>
        </thead>
        <tbody>
        <% for (Alumno a : alumnos) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getNombre() %></td>
                <td><%= a.getEmail() %></td>
                <td><%= a.getEdad() %></td>
                <td><%= (a.getCurso() != null) ? a.getCurso().getCodigo() : "" %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>

</body>
</html>
