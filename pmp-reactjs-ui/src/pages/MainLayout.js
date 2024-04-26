import HeaderNav from './HeaderNav.js';
import SideNav from './SideNav.js';

function MainLayout({ children }) {
    return (
        <div class="flex flex-wrap h-screen">
            <section class="relative mx-auto">
                <HeaderNav></HeaderNav>
            </section>
            <div>
                {children}
            </div>
        </div>
    );
}

export default MainLayout;