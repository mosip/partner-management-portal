import { useState } from 'react';
import mosip_icon from '../../src/mosip_icon.svg';
import side_menu_title from '../../src/side_menu_title.svg';
import { Home } from '../sideNaveOptions/Home';
import { PartnerCertificate } from '../sideNaveOptions/PartnerCertificate';
import { DeviceDetails } from '../sideNaveOptions/DeviceDetails';
import { SBIInformation } from '../sideNaveOptions/SBIInformation';
import { PartnerProfile } from '../sideNaveOptions/PartnerProfile';

function SideNav() {
    const [open, setOpen] = useState(false);
    const [activeIcon, setActiveIcon] = useState(false);


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
                        <Home />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <PartnerCertificate />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <DeviceDetails />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <SBIInformation />
                    </li>
                    <li className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <PartnerProfile />
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default SideNav;