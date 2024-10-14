<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Request" %>
<%@ page import="org.example.model.User" %>
<%@ page import="org.example.model.Task" %>
<%@ page import="org.example.model.enums.RequetStatus" %>

<html>
<%@ include file="../../../components/head.jsp" %>
<body>
<%@ include file="../../../components/header.jsp" %>
<%@ include file="../../../components/sideBar.jsp" %>

<div class="text-gray-900 bg-gray-200">
    <div class="p-4 flex">
        <h1 class="text-3xl">
            Request List
        </h1>
    </div>
    <div class="px-3 py-4 flex justify-center">
        <table class="w-full text-md bg-white shadow-md rounded mb-4">
            <tbody>
            <tr class="border-b">
                <th class="text-left p-3 px-5">Task</th>
                <th class="text-left p-3 px-5">Status</th>
                <th></th>
            </tr>

            <%
                List<Request> requests = (List<Request>) request.getAttribute("requests");
                List<User> userList = (List<User>) request.getAttribute("users");
                if (requests != null && !requests.isEmpty()) {
                    for (Request requestItem : requests) {
            %>
            <tr class="border-b hover:bg-orange-100 bg-gray-100">
                <td class="p-3 px-5"><%= requestItem.getTask().getTitle() %></td>
                <td class="p-3 px-5"><%= requestItem.getStatus() %></td>
                <td class="p-3 px-5">
                    <form action="${pageContext.request.contextPath}/request" method="POST">
                        <input type="hidden" name="requestId" value="<%= requestItem.getId() %>">

                        <!-- Assigned User Dropdown -->
                        <label>
                            <select name="assignedUserId" class="bg-white border border-gray-300 rounded py-1 px-2">
                                <%
                                    // Iterate over the list of users to display options
                                    for (User user : userList) {
                                        // Check if the current user is assigned to the request
                                        boolean isSelected = user.getId() == requestItem.getUser().getId();
                                %>
                                <option value="<%= user.getId() %>" <%= isSelected ? "selected" : "" %> >
                                    <%= user.getUsername() %>
                                </option>
                                <% } %>
                            </select>
                        </label>

                        <!-- Status Dropdown -->
                        <label for="status">Select Status:</label>
                        <select name="status" id="status" class="bg-white border border-gray-300 rounded py-1 px-2">
                            <!-- Display the current status in the select options -->
                            <option value="PENDING" <%= "PENDING".equals(requestItem.getStatus().name()) ? "selected" : "" %>>Pending</option>
                            <option value="APPROVED" <%= "APPROVED".equals(requestItem.getStatus().name()) ? "selected" : "" %>>Approved</option>
                            <option value="REJECTED" <%= "REJECTED".equals(requestItem.getStatus().name()) ? "selected" : "" %>>Rejected</option>
                        </select>

                        <button class="bg-blue-500 text-white py-1 px-3 rounded">Process</button>
                    </form>
                </td>

            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="3" class="text-center p-3">No requests available.</td>
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
