<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

</head>
<body class="bg-gray-100">
<%@ include file="components/header.jsp" %>
<section class="bg-gray-50">
    <div class="mx-auto px-4 py-32 lg:flex lg:items-center">
        <div class="mx-auto max-w-xl text-center">
            <h1 class="text-3xl font-extrabold sm:text-5xl">
                Welcome to the Home Page.
                <strong class="font-extrabold text-pink-700 sm:block"> Increase Conversion. </strong>
            </h1>

            <p class="mt-4 sm:text-xl/relaxed">
                Discover amazing content and enjoy your stay.
            </p>

            <div class="mt-8 flex flex-wrap justify-center gap-4">
                <a href="auth?action=signUp"
                   class="block w-full rounded bg-pink-600 px-12 py-3 text-sm font-medium text-white shadow hover:bg-pink-700 focus:outline-none focus:ring active:bg-pink-500 sm:w-auto"
                >
                    Get Started
                </a>

                <a href="auth?action=login"
                   class="block w-full rounded px-12 py-3 text-sm font-medium text-red-600 shadow hover:text-sm text-pink-600 focus:outline-none focus:ring active:text-red-500 sm:w-auto"
                >
                    Log in
                </a>
            </div>
        </div>
    </div>
</section>
<%--<div class="container px-40 py-24 mx-auto ">--%>
<%--    <div class="w-full flex flex-col">--%>
<%--        <div class="flex flex-col items-center justify-center mb-8">--%>
<%--            <h1 class="text-4xl font-bold text-center text-zinc-800 mb-4">--%>
<%--                Welcome to the Home Page!--%>
<%--            </h1>--%>
<%--            <p class="text-lg text-gray-600">--%>
<%--                Discover amazing content and enjoy your stay.--%>
<%--            </p>--%>
<%--        </div>--%>

<%--        <a href="userList">--%>
<%--            <button class='relative w-full inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>--%>
<%--                <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Users list </span>--%>
<%--            </button>--%>
<%--        </a>--%>
<%--        <a href="userList?action=add">--%>
<%--            <button class='relative w-full inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>--%>
<%--                <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Add a new User </span>--%>
<%--            </button>--%>
<%--        </a>--%>

<%--    </div>--%>
<%--</div>--%>


</body>
</html>
