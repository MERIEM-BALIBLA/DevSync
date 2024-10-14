<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Task" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Utilisateurs et Tâches</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/alpinejs@2.8.2/dist/alpine.min.js" defer></script>
</head>
<body class="bg-gray-100">

<%@ include file="../../components/header.jsp" %>

<div class="bg-gray-100 font-sans">
    <div x-data="{ openTab: 1 }" class="p-8">
        <div class="">
            <div class="mb-4 flex space-x-4 p-2 bg-white rounded-lg shadow-md">
                <button x-on:click="openTab = 1" :class="{ 'bg-pink-600 text-white': openTab === 1 }"
                        class="flex-1 py-2 px-4 rounded-md focus:outline-none focus:shadow-outline-blue transition-all duration-300">
                    Tasks List
                </button>
                <button x-on:click="openTab = 2" :class="{ 'bg-pink-600 text-white': openTab === 2 }"
                        class="flex-1 py-2 px-4 rounded-md focus:outline-none focus:shadow-outline-blue transition-all duration-300">
                    Sub-Tasks list
                </button>
            </div>

            <div x-show="openTab === 1"
                 class="transition-all duration-300 p-4">
                <div><%= sessionUser.getToken().getDailyTokens()%></div>
                <div><%= sessionUser.getToken().getMonthlyTokens()%></div>
                <table class="w-full text-md bg-white shadow-md rounded mb-4">
                    <thead>
                    <tr class="border-b">
                        <th class="text-left p-3 px-5">Task title</th>
                        <th class="text-left p-3 px-5">Description</th>
                        <th class="text-left p-3 px-5">Start Date</th>
                        <th class="text-left p-3 px-5">End Date</th>
                        <th class="text-left p-3 px-5">Status</th>
                        <th class="text-left p-3 px-5"></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                    %>
                    <%
                        if (tasks != null && !tasks.isEmpty()) {
                            for (Task task : tasks) {
                    %>
                    <%
                        LocalDate currentDate = LocalDate.now();
                        LocalDate deadline = task.getEndDate();
                        boolean isPastDeadline = currentDate.isAfter(deadline);
                        boolean isCompleted = task.isCompleted();
                        boolean isNotCompletedAndPastDeadline = !isCompleted && isPastDeadline;

                    %>
                    <tr class="border-b hover:bg-orange-100 bg-gray-100">

                        <td class="p-3 px-5">
                            <%= task.getTitle() %>
                        </td>
                        <td class="p-3 px-5">
                            <%= task.getDescription() %>
                        </td>
                        <td class="p-3 px-5">
                            <%= task.getCreatedAt() %>
                        </td>
                        <td class="p-3 px-5">
                            <%= task.getEndDate() %>
                        </td>
                        <td class="p-3 px-5">
                            <%= task.isCompleted() ? "completed" : "not completed" %>
                        </td>
                        <td class="p-3 px-5 flex justify-end">
                            <form action="${pageContext.request.contextPath}/dashboard" method="POST">
                                <input type="hidden" name="action" value="updateStatus">
                                <input type="hidden" name="taskId" value="<%= task.getId() %>">

                                <%-- Bouton de soumission avec style conditionnel --%>
                                <input type="submit"
                                       value="<%= isCompleted ? "Completed" : (isNotCompletedAndPastDeadline ? "Not Completed (Past Deadline)" : "Not Completed") %>"
                                       class="mr-3 text-sm
                              <%= isNotCompletedAndPastDeadline ? "bg-red-500" : "bg-blue-500" %>
                              hover:bg-blue-700 text-white py-1 px-2 rounded focus:outline-none focus:shadow-outline">

                                <input type="hidden" name="status" value="<%= isCompleted ? "undo" : "completed" %>">
                            </form>
                            <form action="${pageContext.request.contextPath}/dashboard" method="POST"
                                  onsubmit="return confirm('Êtes-vous sûr de vouloir refuser cette tâche ?');">
                                <input type="hidden" name="action" value="refuseTask">
                                <input type="hidden" name="taskId" value="<%= task.getId() %>">

                                <button title="Refuse Task"
                                        class="cursor-pointer flex items-center fill-lime-400 bg-lime-950 hover:bg-lime-900 active:border active:border-lime-400 rounded-md duration-100 p-2"
                                        <%= sessionUser.getToken().getDailyTokens() <= 0 ? "disabled" : "" %>>
                                    <span class="text-sm text-lime-400 font-bold pr-1">Refuser la tâche</span>
                                </button>
                            </form>
                        </td>

                        <%
                                }
                            }
                        %>
                    </tr>
                    </tbody>
                </table>
            </div>

            <!-- Liste des sous taches-->
            <div x-show="openTab === 2" class="transition-all duration-300 p-4">
                <div class="flex justify-end mb-4">
                    <a href="${pageContext.request.contextPath}/dashboard?action=InsertTask" class="mr-3">
                        <button class="bg-pink-500 text-white py-1 px-3 rounded">Add a new task</button>
                    </a>
                </div>
                <table class="w-full text-md bg-white shadow-md rounded mb-4">
                    <thead>
                    <tr class="border-b">
                        <th class="text-left p-3 px-5">Task Title</th>
                        <th class="text-left p-3 px-5">Description</th>
                        <th class="text-left p-3 px-5">Start Date</th>
                        <th class="text-left p-3 px-5">End Date</th>
                        <th class="text-left p-3 px-5">Status</th>
                        <th class="text-left p-3 px-5"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Task> sousTasks = (List<Task>) request.getAttribute("sousTasks");
                    %>
                    <%
                        if (sousTasks != null && !sousTasks.isEmpty()) {
                            for (Task task : sousTasks) {
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
                        <td class="p-3 px-5"><%= task.isCompleted() ? "Completed" : "Not Completed" %>
                        </td>
                        <td class="p-3 px-5 flex justify-end">
                            <form action="${pageContext.request.contextPath}/dashboard" method="post"
                                  style="display:inline;">
                                <input type="hidden" name="action" value="Delete">
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
                        <td colspan="6" class="p-3 text-center">No tasks available.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>

</body>
</html>
