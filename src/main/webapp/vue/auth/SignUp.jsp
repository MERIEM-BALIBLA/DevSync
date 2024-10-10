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

<%@ include file="../../components/homeHeader.jsp" %>

<%--<head>
    <title>Title</title>

</head>--%>
<body>
<!-- component -->
<div class="py-8 px-24">
    <div class="bg-white shadow-md rounded-lg px-8 py-6">
        <h1 class="text-2xl font-bold text-center mb-4 dark:text-gray-200">Create a new account!</h1>

        <form action="${pageContext.request.contextPath}/auth" method="post">
            <input type="hidden" name="action" value="signUp"> <!-- Action ajoutée ici -->
            <div class="mb-4">
                <label for="username" class="block text-sm font-medium text-gray-700">User name:</label>
                <input type="text" id="username" name="username" required
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200"
                       placeholder="your account name">
            </div>

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

            <div class="mb-2">
                <label for="email" class="block text-sm font-medium text-gray-700">Confirm password:</label>
                <input name="confirm-password" required type="password"
                       class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring focus:ring-blue-200">
            </div>

            <div>
                <p class="text-sm mb-2">You already have account ? <a href="auth?action=login">Log in</a></p>
            </div>
            <div class="flex justify-center">
                <button type="submit"
                        onclick="alert('Hello')"
                        class="w-1/3 flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm
                    text-sm font-medium text-white bg-pink-600 hover:bg-pink-700 focus:outline-none focus:ring-2
                    focus:ring-offset-2 focus:ring-indigo-500">
                    Sign up
                </button>
            </div>

        </form>
    </div>
</div>
</body>
</html>
