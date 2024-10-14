<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../../components/head.jsp" %>
<body>
<%@ include file="../../components/header.jsp" %>
<%@ include file="../../components/sideBar.jsp" %>
<div class="bg-white py-6 sm:py-8 lg:py-12">
    <div class="mx-auto max-w-screen-2xl px-4 md:px-8">

        <div class="grid gap-4 sm:grid-cols-2 md:gap-6 lg:grid-cols-3 xl:grid-cols-4">
            <!-- product - start -->
            <div>
                <a href="#" class="group relative flex items-end overflow-hidden rounded-lg bg-gray-100 p-4 shadow-lg">
                    <div class="relative flex w-full flex-col rounded-lg bg-white p-4 text-center">
                        <span class="text-gray-500">Tags</span>
                        <span class="text-lg font-bold text-gray-800 lg:text-xl"><%= request.getAttribute("conutTag") %></span>
                    </div>
                </a>
            </div>
            <!-- product - end -->

            <!-- product - start -->
            <div>
                <a href="#" class="group relative flex items-end overflow-hidden rounded-lg bg-gray-100 p-4 shadow-lg">
                    <div class="relative flex w-full flex-col rounded-lg bg-white p-4 text-center">
                        <span class="text-gray-500">Tasks</span>
                        <span class="text-lg font-bold text-gray-800 lg:text-xl"><%= request.getAttribute("counTask") %></span>
                    </div>
                </a>
            </div>
            <!-- product - end -->

            <!-- product - start -->
            <div>
                <a href="#" class="group relative flex items-end overflow-hidden rounded-lg bg-gray-100 p-4 shadow-lg">
                    <div class="relative flex w-full flex-col rounded-lg bg-white p-4 text-center">
                        <span class="text-gray-500">Request</span>
                        <span class="text-lg font-bold text-gray-800 lg:text-xl"><%= request.getAttribute("conutRequest") %></span>
                    </div>
                </a>
            </div>
            <!-- product - end -->

            <!-- product - start -->
            <div>
                <a href="#" class="group relative flex items-end overflow-hidden rounded-lg bg-gray-100 p-4 shadow-lg">
                    <div class="relative flex w-full flex-col rounded-lg bg-white p-4 text-center">
                        <span class="text-gray-500">User</span>
                        <span class="text-lg font-bold text-gray-800 lg:text-xl"><%= request.getAttribute("conutUser") %></span>
                    </div>
                </a>
            </div>
            <!-- product - end -->
        </div>
        <div class="mt-8">
            <table class="w-full text-md bg-white shadow-md rounded mb-4 text-sm font-medium">
                <thead>
                <tr class="border-b">
                    <th class="text-left p-3 px-5">ID</th>
                    <th class="text-left p-3 px-5">Task title</th>
                    <th class="text-left p-3 px-5">Description</th>
                    <th class="text-left p-3 px-5">Assigned user</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                    if (tasks != null && !tasks.isEmpty()) {
                        for (Task task : tasks) {
                %>
                <tr class="border-b hover:bg-orange-100 bg-gray-100">
                    <td class="p-3 px-5"><%= task.getId() %></td>
                    <td class="p-3 px-5"><%= task.getTitle() %></td>
                    <td class="p-3 px-5"><%= task.getDescription() %></td>
                    <td class="p-3 px-5"><%= task.getAssignedUser() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">There is no task.</td>
                </tr>
                <%
                    }
                %>

                </tbody>
            </table>

        </div>
    </div>

</div>

</body>
</html>
