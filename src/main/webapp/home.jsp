<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

</head>
<body class="bg-gray-100">
<%@ include file="components/header.jsp" %>
<div class="container px-40 py-24 mx-auto ">
    <div class="w-full flex flex-col">
        <div class="flex flex-col items-center justify-center mb-8">
            <h1 class="text-4xl font-bold text-center text-zinc-800 mb-4">
                Welcome to the Home Page!
            </h1>
            <p class="text-lg text-gray-600">
                Discover amazing content and enjoy your stay.
            </p>
        </div>

        <a href="userList">
            <button class='relative w-full inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
                <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Users list </span>
            </button>
        </a>
        <a href="userList?action=add">
            <button class='relative w-full inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-base text-pink-600 font-semibold rounded-lg group bg-gradient-to-br from-pink-500 to-pink-400 group-hover:from-pink-500 group-hover:to-pink-400 hover:text-white'>
                <span class='relative w-full py-2 px-5 transition-all ease-in duration-75 bg-white rounded-lg group-hover:bg-opacity-0'> Add a new User </span>
            </button>
        </a>

    </div>
</div>

</body>
</html>
