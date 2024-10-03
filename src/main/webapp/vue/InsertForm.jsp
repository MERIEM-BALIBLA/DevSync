<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 03/10/2024
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire d'Insertion d'Utilisateur</title>
    <!-- Lien vers le CDN Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 flex items-center justify-center h-screen">
<div class="bg-white p-8 rounded-lg shadow-md w-1/3">
    <h1 class="text-2xl font-bold mb-4">Ajouter un Utilisateur</h1>
    <form action="addUser" method="post">
        <div class="mb-4">
            <label for="username" class="block text-sm font-medium text-gray-700">Nom d'utilisateur:</label>
            <input type="text" id="username" name="username" required
                   class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
        </div>
        <div class="mb-4">
            <label for="email" class="block text-sm font-medium text-gray-700">Email:</label>
            <input type="email" id="email" name="email" required
                   class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
        </div>
        <button type="submit"
                class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
            Ajouter Utilisateur
        </button>
    </form>
</div>
<%--<div>--%>
<%--    <a href="/home.jsp" class="text-blue-600 hover:underline">Retour à l'accueil</a>--%>
<%--</div>--%>
</body>
</html>
