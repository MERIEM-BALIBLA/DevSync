<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Task" %>
<%@ page import="java.util.Collections" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Utilisateurs et Tâches</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/alpinejs@2.8.2/dist/alpine.min.js" defer></script>
</head>
<body class="bg-gray-100">
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>

<%@ include file="../../../components/header.jsp" %>
<%@ include file="../../../components/sideBar.jsp" %>


<div class="bg-gray-100 font-sans">
<%--    <span class="text-black">Bienvenue, <%= sessionUser.getUsername() %>!</span>--%>
<%--    <div x-data="{ openTab: 1 }" class="p-8">--%>
        <div class="">
<%--            <div class="mb-4 flex space-x-4 p-2 bg-white rounded-lg shadow-md">--%>
<%--                <button x-on:click="openTab = 1" :class="{ 'bg-pink-600 text-white': openTab === 1 }" class="flex-1 py-2 px-4 rounded-md focus:outline-none focus:shadow-outline-blue transition-all duration-300">Users list</button>--%>
<%--                <button x-on:click="openTab = 2" :class="{ 'bg-pink-600 text-white': openTab === 2 }" class="flex-1 py-2 px-4 rounded-md focus:outline-none focus:shadow-outline-blue transition-all duration-300">Tasks list</button>--%>
<%--            </div>--%>

            <!-- Liste des Utilisateurs -->
            <div class="transition-all duration-300 p-4">
                <div class="flex justify-end mb-4">
                    <a href="userList?action=add" class="mr-3">
                        <button class="bg-pink-500 text-white py-1 px-3 rounded">Add a new user</button>
                    </a>
                </div>
                <table class="w-full text-md bg-white shadow-md rounded mb-4">
                    <thead>
                    <tr class="border-b">
                        <th class="text-left p-3 px-5">ID</th>
                        <th class="text-left p-3 px-5">User name</th>
                        <th class="text-left p-3 px-5">Email</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if (users != null && !users.isEmpty()) {
                            for (User user : users) {
                    %>
                    <tr class="border-b hover:bg-orange-100 bg-gray-100">
                        <td class="p-3 px-5"><%= user.getId() %></td>
                        <td class="p-3 px-5"><%= user.getUsername() %></td>
                        <td class="p-3 px-5"><%= user.getEmail() %></td>
                        <td class="p-3 px-5 flex justify-end">
                            <a href="${pageContext.request.contextPath}/userList?action=edit&userId=<%= user.getId() %>" class="mr-3 text-sm bg-blue-500 hover:bg-blue-700 text-white py-1 px-2 rounded">Edit</a>
                            <form action="${pageContext.request.contextPath}/userList" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="userId" value="<%= user.getId() %>">
                                <button type="submit" onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');" class="text-sm bg-red-500 hover:bg-red-700 text-white py-1 px-2 rounded">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">Aucun utilisateur trouvé.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
<%--    </div>--%>
</div>

</body>
</html>
