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
<body class="bg-gray-100">
<%@ include file="../components/header.jsp" %>
<div class="flex items-center justify-center py-6 border">
    <h1 class="text-4xl font-bold text-center text-zinc-800 ">
        Users list </h1>
</div>


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
            <a href="userList?action=add" class="ml-auto">
                <button class='relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
                    <span class='relative py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Add a new user </span>
                </button>
            </a>
        </div>
        <table class="w-full text-md bg-white shadow-md rounded mb-4">
            <thead>
            <tr class="border-b">
                <th class="text-left p-3 px-5">ID</th>
                <th class="text-left p-3 px-5">User name</th>
                <th class="text-left p-3 px-5">Email</th>
                <th class="text-left p-3 px-5">Role</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                if (users != null && !users.isEmpty()) {
                    for (User user : users) {
            %>
            <tr class="border-b hover:bg-orange-100 bg-gray-100">
                <td class="p-3 px-5"><%= user.getId() %>
                </td class="p-3 px-5">
                <td class="p-3 px-5"><%= user.getUsername() %>
                </td>
                <td class="p-3 px-5"><%= user.getEmail() %>
                </td>
                <td class="p-3 px-5"><%= user.getRole() %>
                </td>
                <td class="p-3 px-5 flex justify-end">

                    <a href="${pageContext.request.contextPath}/userList?action=edit&userId=<%= user.getId() %>"
                       class="mr-3 text-sm bg-blue-500 hover:bg-blue-700 text-white py-1 px-2 rounded focus:outline-none focus:shadow-outline">
                        Update
                    </a>

                    <form action="${pageContext.request.contextPath}/userList" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="userId" value="<%= user.getId() %>">
                        <button type="submit"
                                onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');"
                                class="text-sm bg-red-500 hover:bg-red-700 text-white py-1 px-2 rounded focus:outline-none focus:shadow-outline">
                            Delete
                        </button>
                    </form>
                </td>

                <%--   <td class="p-3 px-5 flex justify-end">
                        <button type="button"
                                class="mr-3 text-sm bg-blue-500 hover:bg-blue-700 text-white py-1 px-2 rounded focus:outline-none focus:shadow-outline">
                            Save
                        </button>
                        <form action="${pageContext.request.contextPath}/userList" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="userId" value="<%= user.getId() %>">
                            <button type="submit"
                                    class="text-sm bg-red-500 hover:bg-red-700 text-white py-1 px-2 rounded focus:outline-none focus:shadow-outline">
                                Delete
                            </button>
                        </form>
                    </td>--%>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="3">Aucun utilisateur trouvé.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
