import HeaderNav from './HeaderNav.js';
import SideNav from './SideNav.js';
import Footer from './Footer.js';
import '../index.css';
import { getUserProfile } from '../services/UserProfileService.js';
import { useTranslation } from 'react-i18next';
import { useState, useEffect } from 'react';

function MainLayout({ children }) {
    const { i18n } = useTranslation();
    const [open, setOpen] = useState(false);

    useEffect(() => {
        const langCode = getUserProfile().langCode;
        if (langCode != null) {
            i18n.changeLanguage(langCode);
        }
    }, [i18n]);

    return (
        <div className="flex flex-col justify-evenly bg-anti-flash-white font-inter">
            <HeaderNav open={open} setOpen={setOpen}></HeaderNav>
            <div className='flex flex-row justify-stretch h-full'>
                <SideNav open={open}></SideNav>
                {children}
            </div>
            <Footer></Footer>
        </div>
    );
}

export default MainLayout;