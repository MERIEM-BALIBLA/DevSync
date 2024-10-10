<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Task" %>
<html>
<head>
    <title>Liste des Tâches</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">

<%
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
%>


<%@ include file="../../components/header.jsp" %>
<%@ include file="../../components/sideBar.jsp" %>

<div class="text-gray-900 bg-gray-200">
    <div class="p-4 flex">
        <h1 class="text-3xl">
            Users
        </h1>
    </div>
    <a href="${pageContext.request.contextPath}/tasks?action=insertTask" class="mr-3">
        <button class="bg-pink-500 text-white py-1 px-3 rounded">Add a new user</button>
    </a>

    <div class="px-3 py-4 flex justify-center">
        <table class="w-full text-md bg-white shadow-md rounded mb-4">
            <tbody>
            <tr class="border-b">
                <th class="text-left p-3 px-5">Title</th>
                <th class="text-left p-3 px-5">Description</th>
                <th class="text-left p-3 px-5">Start date</th>
                <th class="text-left p-3 px-5">End date</th>
                <th class="text-left p-3 px-5">Assigned to</th>
                <th class="text-left p-3 px-5">Status</th>
                <th class="text-left p-3 px-5">Still confirmed</th>
                <th></th>
            </tr>


            <%
                if (tasks != null && !tasks.isEmpty()) {
                    for (Task task : tasks) {
            %>
            <tr class="border-b hover:bg-orange-100 bg-gray-100">
                <td class="p-3 px-5"><%= task.getTitle() %>
                </td>
                <td class="p-3 px-5"><%= task.getDescription() %>
                </td>
                <td class="p-3 px-5"><%= task.getCreatedAt() %>
                </td>
                <td class="p-3 px-5"><%= task.getEndDate() %>
                </td>
                <td class="p-3 px-5"><%= task.getAssignedUser().getUsername() %>
                </td>
                <td class="p-3 px-5"><%= task.isCompleted() ? "completed" : "not completed" %>
                </td>
                <td class="p-3 px-5"><%= task.isConfirmed() ? "working on it" : "refused" %>
                </td>

                <td class="p-3 px-5 flex justify-end">

                    <% if (task != null) { %>
                    <a href="${pageContext.request.contextPath}/tasks?action=editTask&id=<%= task.getId() %>">
                        <button class="mr-3 text-sm bg-blue-500 hover:bg-blue-700 text-white py-1 px-2 rounded">
                            Edit
                        </button>
                    </a>

                    <% } else { %>
                    <span>Task not available</span>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/tasks" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="taskId" value="<%= task.getId() %>">
                        <button type="submit"

                                onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette tâche ?');"
                                class="text-sm bg-red-500 hover:bg-red-700 text-white py-1 px-2 rounded focus:outline-none focus:shadow-outline">
                            Delete
                        </button>
                    </form>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="7" class="text-center p-3">Aucune tâche à afficher.</td>
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
