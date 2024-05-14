import HeaderNav from './HeaderNav.js';
import SideNav from '../nav/SideNav.js';
import '../index.css';
import { useState } from 'react';

function MainLayout({ children }) {
    
    const [open, setOpen] = useState(false);

    return (
        <div className="flex w-full">
            <SideNav open={open}></SideNav>
            <div className='w-full'>
                <HeaderNav open={open} setOpen={setOpen}></HeaderNav>
                {children}
            </div>
        </div>
    );
}

export default MainLayout;