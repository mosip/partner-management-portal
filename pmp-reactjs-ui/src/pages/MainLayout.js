import HeaderNav from './HeaderNav.js';
import SideNav from './SideNav.js';
import '../index.css';

function MainLayout({ children }) {
    return (
        <div className="flex">
            <SideNav></SideNav>
            <div>
                < HeaderNav></HeaderNav>
                {children}
            </div>
        </div>
    );
}

export default MainLayout;