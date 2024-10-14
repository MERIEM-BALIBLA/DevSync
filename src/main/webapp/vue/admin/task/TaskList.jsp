<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Task" %>
<%@ page import="org.example.model.Tag" %>
<%@ page import="java.util.stream.Collectors" %>
<html>
<%@ include file="../../../components/head.jsp" %>

<body class="bg-gray-100">

<%
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
%>

<%@ include file="../../../components/header.jsp" %>
<%@ include file="../../../components/sideBar.jsp" %>

<div class="text-gray-900">
    <div class="p-4 flex">
        <h1 class="text-3xl ">
            TASKS LIST
        </h1>
    </div>
    <div class="flex justify-end">
        <a href="${pageContext.request.contextPath}/tasks?action=insertTask" class="mr-3">
            <button class="bg-pink-500 text-white py-1 px-3 rounded">Add a new Task</button>
        </a>
    </div>
    <div class="px-3 py-4 flex justify-center">
        <table class="w-full text-md bg-white shadow-md rounded mb-4 text-sm font-medium">
            <tbody>
            <tr class="border-b">
                <th class="text-left p-3 px-5">Title</th>
                <th class="text-left p-3 px-5">Description</th>
                <th class="text-left p-3 px-5">Start date</th>
                <th class="text-left p-3 px-5">End date</th>
                <th class="text-left p-3 px-5">Assigned to</th>
                <th class="text-left p-3 px-5">Status</th>
                <th class="text-left p-3 px-5">tags</th>
                <th></th>
            </tr>
            <%
                if (tasks != null && !tasks.isEmpty()) {
                    for (Task task : tasks) {
            %>
            <tr class="border-b hover:bg-orange-100 bg-gray-100">
                <td class="p-3 px-5  text-sm"><%= task.getTitle() %>
                </td>
                <td class="p-3 px-5 tx-sm"><%= task.getDescription() %>
                </td>
                <td class="p-3 px-5 tx-sm"><%= task.getCreatedAt() %>
                </td>
                <td class="p-3 px-5"><%= task.getEndDate() %>
                </td>
                <td class="p-3 px-5"><%= task.getAssignedUser().getUsername() %>
                </td>
                <%--                <td class="p-3 px-5 font-medium" style="color: <%= task.isCompleted() ? "green bg-green-600" : "red" %>;">--%>
                <%--                    <%= task.isCompleted() ? "completed" : "not completed" %>--%>
                <%--                </td>--%>
                <td class="p-3 px-5 font-medium">
                    <div class=" p-1 rounded-md flex justify-center <%= task.isCompleted() ? "text-green-600 bg-green-100" : "text-red-600 bg-red-100" %>">
                        <%= task.isCompleted() ? "completed" : "not completed" %>
                    </div>
                </td>
                <td>
                    <%
                        String tagTitles = task.getTags().stream()
                                .map(Tag::getTitle)
                                .collect(Collectors.joining(", "));
                    %>
                    <%= tagTitles %>
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
