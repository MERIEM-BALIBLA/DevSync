<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

</head>
<body>
<%@ include file="components/header.jsp" %>
<h1>Welcome to the Home Page!</h1>
<a href="vue/InsertForm.jsp"><button class='relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
    <span class='relative py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Liste des utilisateurs </span>
</button></a>
<a href="vue/InsertForm.jsp"><button class='relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
    <span class='relative py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Ajouter un utilisateur </span>
</button></a>
<a href="vue/ListUsers.jsp"><button class='relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
    <span class='relative py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Liste des utilisateurs </span>
</button></a>

</body>
</html>
