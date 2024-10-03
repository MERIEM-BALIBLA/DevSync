<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Utilisateurs</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body>
<h1 class="text-2xl font-bold mb-4">Liste des Utilisateurs</h1>

<table class="min-w-full border-collapse border border-gray-300">
    <thead>
    <tr>
        <th class="border border-gray-300 px-4 py-2">ID</th>
        <th class="border border-gray-300 px-4 py-2">Nom d'utilisateur</th>
        <th class="border border-gray-300 px-4 py-2">Email</th>
    </tr>
    </thead>
    <tbody>
    <%
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null && !users.isEmpty()) {
            for (User utilisateur : users) {
    %>
    <tr>
        <td class="border border-gray-300 px-4 py-2"><%= utilisateur.getId() %></td>
        <td class="border border-gray-300 px-4 py-2"><%= utilisateur.getUsername() %></td>
        <td class="border border-gray-300 px-4 py-2"><%= utilisateur.getEmail() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3" class="border border-gray-300 px-4 py-2 text-center">Aucun utilisateur trouvé.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<a href="vue/InsertForm.jsp">
    <button class='relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
        <span class='relative py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Ajouter un utilisateur </span>
    </button>
</a>
</body>
</html>
