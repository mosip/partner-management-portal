import HeaderNav from './HeaderNav.js';
import SideNav from '../nav/SideNav.js';
import '../index.css';

function MainLayout({ children }) {
    return (
        <div className="flex w-full">
            <SideNav></SideNav>
            <div className='w-full'>
                < HeaderNav></HeaderNav>
                {children}
            </div>
        </div>
    );
}

export default MainLayout;