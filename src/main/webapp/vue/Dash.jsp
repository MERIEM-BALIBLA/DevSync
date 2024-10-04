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
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
<%@ include file="../components/header.jsp" %>
<div class="flex items-center justify-center py-6 border">
    <h1 class="text-4xl font-bold text-center text-zinc-800 ">
        Tasks list </h1>
</div>
<a href="${pageContext.request.contextPath}/auth?action=logout">Déconnexion</a>


<div class="text-gray-900">
    <div class="px-16 py-2 flex justify-center flex flex-col">
        <div class="flex ">
            <a href="${pageContext.request.contextPath}/home">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                    <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                          stroke-width="1.5"
                          d="M15.798 12H2.871m5.157-5.778l-4.717 4.717c-.293.293-.44.677-.44 1.061m5.157 5.778l-4.717-4.717A1.5 1.5 0 0 1 2.87 12m17.88-7.905v15.81"></path>
                </svg>
            </a>

        </div>
        <table class="w-full text-md bg-white shadow-md rounded mb-4">
            <thead>
            <tr class="border-b">
                <%--                <th class="text-left p-3 px-5">ID</th>--%>
                <th class="text-left p-3 px-5">Task title</th>
                <th class="text-left p-3 px-5">Description</th>
                <th class="text-left p-3 px-5">Date</th>
                <th class="text-left p-3 px-5">Status</th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            <tr class="border-b hover:bg-orange-100 bg-gray-100">
                <%--                <td class="p-3 px-5">--%>
                <%--                </td>--%>
                <td class="p-3 px-5">
                </td>
                <td class="p-3 px-5">
                </td>
                <td class="p-3 px-5">
                </td>
                <td class="p-3 px-5">
                </td>
                <td class="p-3 px-5">
                </td>
            </tr>
            <%--            <tr>--%>
            <%--                <td colspan="3">Aucun utilisateur trouvé.</td>--%>
            <%--            </tr>--%>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
