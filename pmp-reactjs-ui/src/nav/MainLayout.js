import HeaderNav from './HeaderNav.js';
import SideNav from './SideNav.js';
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
        <div className="flex">
            <SideNav open={open}></SideNav>
            <div className='w-full lg:w-full'>
                <HeaderNav open={open} setOpen={setOpen}></HeaderNav>
                {children}
            </div>
        </div>
    );
}

export default MainLayout;