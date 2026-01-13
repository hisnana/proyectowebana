<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    es.cursojava.cursos.dto.AlumnoDTO dto =
        (es.cursojava.cursos.dto.AlumnoDTO) request.getAttribute("dto");
    java.util.Map<String,String> errores =
        (java.util.Map<String,String>) request.getAttribute("errores");

    if (dto == null) {
        dto = new es.cursojava.cursos.dto.AlumnoDTO();
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo alumno</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 30px auto; }
        h1 { text-align: center; }
        form { border: 1px solid #ccc; padding: 20px; border-radius: 6px; }
        .campo { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 4px; }
        input { width: 100%; padding: 6px 8px; box-sizing: border-box; }
        .error { color: red; font-size: 0.9em; }
    </style>
</head>
<body>

<h1>Alta de alumno</h1>

<form action="crearAlumno" method="post">

    <div class="campo">
        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre"
               value="<%= dto.getNombre() != null ? dto.getNombre() : "" %>">
        <% if (errores != null && errores.get("nombre") != null) { %>
            <div class="error"><%= errores.get("nombre") %></div>
        <% } %>
    </div>

    <div class="campo">
        <label for="email">Email</label>
        <input type="email" id="email" name="email"
               value="<%= dto.getEmail() != null ? dto.getEmail() : "" %>">
        <% if (errores != null && errores.get("email") != null) { %>
            <div class="error"><%= errores.get("email") %></div>
        <% } %>
    </div>

    <div class="campo">
        <label for="edad">Edad</label>
        <input type="number" id="edad" name="edad"
               value="<%= dto.getEdad() != null ? dto.getEdad() : "" %>">
        <% if (errores != null && errores.get("edad") != null) { %>
            <div class="error"><%= errores.get("edad") %></div>
        <% } %>
    </div>

    <div class="campo">
        <label for="codigoCurso">Código de curso</label>
        <input type="text" id="codigoCurso" name="codigoCurso"
               value="<%= dto.getCodigoCurso() != null ? dto.getCodigoCurso() : "" %>">
        <% if (errores != null && errores.get("codigoCurso") != null) { %>
            <div class="error"><%= errores.get("codigoCurso") %></div>
        <% } %>
    </div>

    <% if (errores != null && errores.get("general") != null) { %>
        <div class="error"><%= errores.get("general") %></div>
    <% } %>

    <button type="submit">Crear alumno</button>
</form>

</body>
</html>
