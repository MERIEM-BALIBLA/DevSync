<div x-data="setup()">
    <button @click="isSidebarOpen = true" class="fixed p-2 text-white bg-black rounded-lg bottom-5 left-5">
        <svg
                class="w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
        >
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path>
        </svg>
        <span class="sr-only">Open menu</span>
    </button>

    <!-- Sidebar -->
    <div
            x-transition:enter="transform transition-transform duration-300"
            x-transition:enter-start="-translate-x-full"
            x-transition:enter-end="translate-x-0"
            x-transition:leave="transform transition-transform duration-300"
            x-transition:leave-start="translate-x-0"
            x-transition:leave-end="-translate-x-full"
            x-show="isSidebarOpen"
            class="fixed inset-y-0 z-10 flex w-80"
    >
        <!-- Curvy shape -->
        <svg
                class="absolute inset-0 w-full h-full text-white"
                style="filter: drop-shadow(10px 0 10px #00000030)"
                preserveAspectRatio="none"
                viewBox="0 0 309 800"
                fill="currentColor"
                xmlns="http://www.w3.org/2000/svg"
        >
            <path
                    d="M268.487 0H0V800H247.32C207.957 725 207.975 492.294 268.487 367.647C329 243 314.906 53.4314 268.487 0Z"></path>
        </svg>
        <!-- Sidebar content -->
        <div class="z-10 flex flex-col flex-1">
            <div class="flex items-center justify-between flex-shrink-0 w-64 p-4">
                <!-- Logo -->
                <a href="#">
                    <svg
                            aria-hidden="true"
                            class="w-16 h-auto text-blue-600"
                            viewBox="0 0 96 53"
                            fill="currentColor"
                            xmlns="http://www.w3.org/2000/svg"
                    >
                        <path
                                fill-rule="evenodd"
                                clip-rule="evenodd"
                                d="M7.69141 34.7031L13.9492 28.1992L32.0898 52H40.1758L18.4492 23.418L38.5938 0.8125H30.4375L7.69141 26.125V0.8125H0.941406V52H7.69141V34.7031ZM35.3008 26.9102H52.457V21.6016H35.3008V26.9102ZM89.1914 13V35.5117C89.1914 39.2148 88.1719 42.0859 86.1328 44.125C84.1172 46.1641 81.1992 47.1836 77.3789 47.1836C73.6055 47.1836 70.6992 46.1641 68.6602 44.125C66.6211 42.0625 65.6016 39.1797 65.6016 35.4766V0.8125H58.9219V35.6875C58.9688 40.9844 60.6562 45.1445 63.9844 48.168C67.3125 51.1914 71.7773 52.7031 77.3789 52.7031L79.1719 52.6328C84.3281 52.2578 88.4062 50.5352 91.4062 47.4648C94.4297 44.3945 95.9531 40.4453 95.9766 35.6172V13H89.1914ZM89 8H96V1H89V8Z"></path>
                    </svg>
                </a>
                <!-- Close btn -->
                <button @click="isSidebarOpen = false" class="p-1 rounded-lg focus:outline-none focus:ring">
                    <svg
                            class="w-6 h-6"
                            aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                    >
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M6 18L18 6M6 6l12 12"></path>
                    </svg>
                    <span class="sr-only">Close sidebar</span>
                </button>
            </div>
            <nav class="flex flex-col flex-1 w-64 p-4 mt-4">
                <a href="userList">Users list</a>
                <a href="${pageContext.request.contextPath}/tasks">Task List</a>
            </nav>
            <div class="flex-shrink-0 p-4">
                <a href="${pageContext.request.contextPath}/auth?action=logout">
                    <button class="flex items-center space-x-2">
                        <svg
                                aria-hidden="true"
                                class="w-6 h-6"
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                        >
                            <path
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="2"
                                    d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
                        </svg>
                        <span>Logout</span>
                    </button>
                </a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/alpinejs/2.8.1/alpine.js"></script>
<script>
    const setup = () => {
        return {
            isSidebarOpen: false,
        }
    }
</script>
