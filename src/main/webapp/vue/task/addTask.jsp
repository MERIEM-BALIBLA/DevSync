<%@ page import="org.example.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter une Tâche</title>
</head>
<body>
<h2>Ajouter une Tâche</h2>
<form action="${pageContext.request.contextPath}/tasks" method="post">
    <input type="hidden" name="action" value="insertTask"> <!-- Action ajoutée ici -->

    <label for="title">Titre:</label>
    <input type="text" id="title" name="title" required><br><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description" required></textarea><br><br>

    <label for="endDate">Date d'échéance:</label>
    <input type="date" id="endDate" name="endDate" required><br><br>

    <label for="assignedUserId">Utilisateur Assigné:</label>
    <select id="assignedUserId" name="assignedUserId" required>
        <option value="">Sélectionnez un utilisateur</option>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
        %>
        <option value="<%= user.getId() %>"><%= user.getUsername() %></option>
        <%
            }
        } else {
        %>
        <option value="">Aucun utilisateur disponible</option>
        <%
            }
        %>
    </select>
<%--    <input type="number" id="assignedUserId" name="assignedUserId" required><br><br>--%>

    <input type="submit" value="Ajouter la Tâche">
</form>

</body>
</html>
