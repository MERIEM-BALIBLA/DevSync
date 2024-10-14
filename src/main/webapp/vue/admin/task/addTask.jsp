<%@ page import="org.example.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../../../components/head.jsp" %>
<body>
<%@ include file="../../../components/header.jsp" %>
<div class=" flex justify-center py-6 relative py-8 px-24">
    <a href="${pageContext.request.contextPath}/tasks" class="absolute left-6 ">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
            <path fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
                  d="M15.798 12H2.871m5.157-5.778l-4.717 4.717c-.293.293-.44.677-.44 1.061m5.157 5.778l-4.717-4.717A1.5 1.5 0 0 1 2.87 12m17.88-7.905v15.81"/>
        </svg>
    </a>
    <div class="bg-white p-8 rounded-lg shadow-md w-full">
        <div class="flex justify-center">
            <h1 class="text-2xl font-bold mb-4">Add a new Task</h1>
        </div>

        <div>
            <form action="${pageContext.request.contextPath}/tasks" method="post">
                <input type="hidden" name="action" value="insertTask"
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">

                <label for="title" class="block text-sm font-medium text-gray-700">Titre:</label>
                <input type="text" id="title" name="title" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">

                <label for="description" class="block text-sm font-medium text-gray-700">Description:</label>
                <input type="text" id="description" name="title" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">


                <%--  <label for="description" class="block text-sm font-medium text-gray-700">Description:</label>
                  <textarea id="description" name="description" required
                            class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
                  </textarea>--%>

                <label for="startDate" class="block text-sm font-medium text-gray-700">Start date:</label>
                <input type="date" id="startDate" name="startDate" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">

                <label for="endDate" class="block text-sm font-medium text-gray-700">End Date:</label>
                <input type="date" id="endDate" name="endDate" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">

                <label for="assignedUserId" class="block text-sm font-medium text-gray-700 mt-4">Utilisateur
                    Assigné:</label>
                <select
                        class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200"
                        id="assignedUserId" class="block text-sm font-medium text-gray-700" name="assignedUserId"
                        required>
                    <%
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null && !users.isEmpty()) {
                            for (User user : users) {
                    %>
                    <option value="<%= user.getId() %>"><%= user.getUsername() %>
                    </option>
                    <%
                        }
                    } else {
                    %>
                    <option value="">Aucun utilisateur disponible</option>
                    <%
                        }
                    %>
                </select>
                <div>
                    <%--                    <label for="newTags" class="block text-sm font-medium text-gray-700">Nommer des tags (séparés par--%>
                    <%--                        des virgules):</label>--%>
                    <%--                    <textarea id="newTags" name="tag" rows="3"--%>
                    <%--                              class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200"></textarea>--%>
                    <label for="tag" class="block text-sm font-medium text-gray-700">newTags:</label>
                    <input type="text" id="tag" name="tag" required
                           class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">

                </div>

                <div class="flex justify-center mt-4">
                    <button type="submit"
                            class='relative w-1/3 inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-white font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400'>
                        <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-pink-500 rounded-lg group-hover:bg-opacity-0'>Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
</html>
