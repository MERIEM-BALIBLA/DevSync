<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.User" %>
<%@ page import="org.example.model.enums.Role" %>

<%
    User user = (User) request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Mise à jour de l'utilisateur</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

</head>
<body class="bg-gray-100">
<%@ include file="../../../components/header.jsp" %>
<div class=" flex justify-center py-6 relative ">
    <a href="${pageContext.request.contextPath}/userList" class="absolute left-6 ">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
            <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M15.798 12H2.871m5.157-5.778l-4.717 4.717c-.293.293-.44.677-.44 1.061m5.157 5.778l-4.717-4.717A1.5 1.5 0 0 1 2.87 12m17.88-7.905v15.81"/>
        </svg>
    </a>
    <div class="bg-white p-8 rounded-lg shadow-md w-1/3">
        <h1 class="text-2xl font-bold mb-4">Edit users informations here</h1>

        <form action="${pageContext.request.contextPath}/userList" method="post">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="userId" value="<%= user.getId() %>">
            <div class="mb-4">
                <label for="username" class="block text-sm font-medium text-gray-700">New user name:</label>
                <input type="text" id="username" name="username" required value="<%= user.getUsername() %>"
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
            </div>
            <div class="mb-4">
                <label for="email" class="block text-sm font-medium text-gray-700">New Email:</label>
                <input type="email" id="email" name="email" required value="<%= user.getEmail() %>"
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
            </div>
            <div class="mb-4">
                <label for="role" class="block text-sm font-medium text-gray-700">Role:</label>
                <select id="role" name="role" required
                        class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
                    <option value="USER" <%= !user.isManager() ? "selected" : "" %>>User</option>
                    <option value="ADMIN" <%= user.isManager() ? "selected" : "" %>>Admin</option>
                </select>

            </div>
            <button type="submit"
                    class='relative w-full inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
                <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'>Save modification</span>
            </button>
        </form>
    </div>
</div>
</body>
<%--<body>


<form action="${pageContext.request.contextPath}/userList" method="post">
    <input type="hidden" name="action" value="edit">
    <input type="hidden" name="userId" value="<%= user.getId() %>">

    <div class="mb-4">
        <label for="username" class="block text-sm font-medium text-gray-700">Nom d'utilisateur:</label>
        <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required
               class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
    </div>

    <div class="mb-4">
        <label for="email" class="block text-sm font-medium text-gray-700">Email:</label>
        <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required
               class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
    </div>

    <button type="submit"
            class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
        Mettre à jour l'utilisateur
    </button>
</form>

</body>--%>
</html>
