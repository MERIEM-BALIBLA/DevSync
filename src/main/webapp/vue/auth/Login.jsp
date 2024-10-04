<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 04/10/2024
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ include file="../../components/head.jsp" %>

<%@ include file="../../components/header.jsp" %>


<body>
<!-- component -->
<div class="py-16 flex items-center justify-center w-full dark:bg-gray-950">
    <div class="bg-white dark:bg-gray-900 shadow-md rounded-lg px-8 py-6 max-w-md">
        <h1 class="text-2xl font-bold text-center mb-4 dark:text-gray-200">Welcome Back!</h1>

        <form action="${pageContext.request.contextPath}/auth" method="post">
            <input type="hidden" name="action" value="login"> <!-- Action ajoutée ici -->

            <div class="mb-4">
                <label for="email" class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Email
                    Address</label>
                <input type="email" id="email" name="email"
                       class="shadow-sm rounded-md w-full px-3 py-2 border border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                       placeholder="your@email.com" required>
            </div>

            <div class="mb-4">
                <label for="password"
                       class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Password</label>
                <input type="password" id="password" name="password"
                       class="shadow-sm rounded-md w-full px-3 py-2 border border-gray-300 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                       placeholder="Enter your password" required>
                <a href="#"
                   class="text-xs text-gray-600 hover:text-indigo-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">Forgot
                    Password?</a>
            </div>

            <button type="submit" onclick="alert(" hello
            ")" type="submit" class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm
            text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2
            focus:ring-offset-2 focus:ring-indigo-500">Login</button>
        </form>
    </div>
</div>
</body>
</html>
