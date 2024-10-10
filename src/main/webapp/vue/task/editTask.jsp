<%@ page import="org.example.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier une Tâche</title>
</head>
<body>
<%
    Task task = (Task) request.getAttribute("task");
%>
<h2>Modifier une Tâche</h2>

<form action="${pageContext.request.contextPath}/tasks" method="post">

    <input type="hidden" name="action" value="editTask">
    <input type="hidden" name="id" value="<%= task.getId() %>">

    <label for="title">Titre:</label>
    <input type="text" id="title" name="title" required value="<%= task.getTitle() %>">

    <label for="description">Description:</label>
    <textarea id="description" name="description" required><%= task.getDescription() %></textarea><br><br>

    <label for="endDate">Date d'échéance:</label>
    <input type="date" id="endDate" name="endDate" required
           value="<%= task.getEndDate() != null ? task.getEndDate().toString() : "" %>"><br><br>

    <label for="assignedUserId">Utilisateur Assigné:</label>
    <select id="assignedUserId" name="assignedUserId" required>
        <option value="">Sélectionnez un utilisateur</option>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
        %>
        <option value="<%= user.getId() %>" <%= (user.getId() == task.getAssignedUser().getId() ? "selected" : "") %>><%= user.getUsername() %>
        </option>
        <%
            }
        } else {
        %>
        <option value="">Aucun utilisateur disponible</option>
        <%
            }
        %>
    </select>

    <input type="submit" value="Modifier la Tâche">
</form>

</body>
</html>
