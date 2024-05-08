import { useState } from 'react';
import mosip_icon from '../../src/mosip_icon.svg';
import side_menu_title from '../../src/side_menu_title.svg';
import { MenuItemHome } from '../sideNaveOptions/MenuItemHome';
import { MenuItemPartnerCertificate } from '../sideNaveOptions/MenuItemPartnerCertificate';
import { MenuItemDeviceDetails } from '../sideNaveOptions/MenuItemDeviceDetails';
import { MenuItemSBIInformation } from '../sideNaveOptions/MenuItemSBIInformation';
import { MenuItemPartnerProfile } from '../sideNaveOptions/MenuItemPartnerProfile';

function SideNav() {
    const [open, setOpen] = useState(false);

    return (
        <div className="flex font-inter">
            <div className={`${open ? "w-72" : "w-20"} h-screen duration-500`}>
                <div className={`flex h-16 gap-x-4 items-center pl-8 ${open ? 'shadow-md' : 'shadow-sm'}`}>
                    <img src={mosip_icon}/>
                    <div className={`${!open && 'scale-0'} items-center duration-300`}>
                        <img src={side_menu_title}/>
                    </div>
                </div>
                <ul className="mt-5 items-center">
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <MenuItemHome />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <MenuItemPartnerCertificate />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <MenuItemDeviceDetails />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <MenuItemSBIInformation />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <MenuItemPartnerProfile />
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default SideNav;