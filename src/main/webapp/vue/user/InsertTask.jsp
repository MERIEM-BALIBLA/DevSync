<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.service.UserService" %>
<%@ page import="org.example.model.Tag" %>

<!DOCTYPE html>
<html lang="fr">
<%@ include file="../../components/head.jsp" %>
<body>
<%@ include file="../../components/header.jsp" %>
<h1>Ajouter une nouvelle tâche</h1>

<% if (request.getAttribute("errorMessage") != null) { %>
<div style="color: red;">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>
<div class=" flex justify-center py-6 relative py-8 px-24">

    <div class="bg-white p-8 rounded-lg shadow-md w-full">


        <%
            // Retrieve the current user from the session
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/auth?action=login");
                return;
            }
        %>

        <form action="${pageContext.request.contextPath}/dashboard" method="post">
            <input type="hidden" name="action" value="InsertTask">
            <div>
                <label for="title">Titre :</label>
                <input type="text" id="title" name="title" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
            </div>
            <div>
                <label for="description">Description :</label>
                <textarea id="description" name="description" required
                          class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">

        </textarea>
            </div>
            <div>
                <label for="endDate">Date de fin :</label>
                <input type="date" id="endDate" name="endDate" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
            </div>

            <label for="existingTags">Tags existants :</label>
            <select id="existingTags" name="existingTags[]" multiple
                    class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
                <%
                    List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                    if (tags == null || tags.isEmpty()) {
                %>
                <option>Pas de tags</option>
                <%
                } else {
                    for (Tag tag : tags) {
                %>
                <option value="<%= tag.getId() %>"><%= tag.getTitle() %>
                </option>
                <%
                        }
                    }
                %>
            </select>

            <div>
                <label for="newTag">Nouveau tag :</label>
                <input type="text" id="newTag" name="newTag"
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
            </div>

            <!-- Hidden field for the assigned user ID -->
            <input type="hidden" name="assignedUserId" value="<%= currentUser.getId() %>">

            <div class="flex justify-center">
                <button type="submit"
                        class='relative w-1/3 inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
                    <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'>Save</span>
                </button>
            </div>
        </form>

        <a href="<%= request.getContextPath() %>/dashboard">Retourner au tableau de bord</a>
    </div>
</div>

</body>
</html>
