import { getAppConfig } from '../services/ConfigService.js';

function HeaderNav() {
   
    const logout = async () => {
        // const cachedAppConfig = await getAppConfig();
        // if (cachedAppConfig)
        // console.log(cachedAppConfig['sbiPorts']);
        localStorage.clear();
        window.location.href = `/api/logout/user?redirecturi=` + btoa(window.location.href);
    }
    return (
        <nav class="flex justify-between bg-gray-300 text-gray-700 w-screen">
            <div class="px-5 xl:px-12 py-3">
                <a class="text-3xl font-bold font-heading" href="#">
                    Logo Here
                </a>
            </div>
            <div class="px-5 xl:px-12 py-3">
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