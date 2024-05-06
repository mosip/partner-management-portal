function HeaderNav() {

    const logout = async () => {
        // const cachedAppConfig = await getAppConfig();
        // if (cachedAppConfig)
        // console.log(cachedAppConfig['sbiPorts']);
        localStorage.clear();
        window.location.href = `/api/logout/user?redirecturi=` + btoa(window.location.href);
    }
    return (
        <nav className="flex justify-between w-screen h-16">
            <div className="px-5 xl:px-12">
                <div className=" flex-1 justify-evenly mt-6 cursor-pointer">
                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="14" viewBox="0 0 22 14">
                        <path id="menu_FILL0_wght300_GRAD0_opsz24" d="M140-691.384v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Zm0-6.068v-1.863h22v1.863Z" transform="translate(-140.001 705.384)" fill="#071121" />
                    </svg>
                </div>
            </div>
            <div className="px-5 xl:px-12 py-3">
                <a href="#" class="space-x-12 flex items-center hover:text-gray-200" onClick={logout}>
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-9 w-9 hover:text-gray-200" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                </a>
            </div>
        </nav>
    );
}

export default HeaderNav;