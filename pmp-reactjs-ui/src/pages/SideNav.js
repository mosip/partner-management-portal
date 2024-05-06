import { useState } from 'react';
import mosip_icon from '../pages/mosip_icon.svg';
import { Home } from '../sideNaveOptions/Home';
import { ManagePartnerCertificate } from '../sideNaveOptions/ManagePartnerCertificate';
import { ManageDeviceDetails } from '../sideNaveOptions/ManageDeviceDetails';
import { ManageSBIInformation } from '../sideNaveOptions/ManageSBIInformation';
import { ManagePartnerProfile } from '../sideNaveOptions/ManagePartnerProfile';

function SideNav() {
    const [open, setOpen] = useState(true);
    const [activeIcon, setActiveIcon] = useState(false);

    const Options = [
        {
            id: 0,
            component: 'Home',
            status: true,
        },
        {
            id: 1,
            component: 'ManagePartnerCertificate',
            status: false,
        },
        {
            id: 2,
            component: 'ManageDeviceDetails',
            status: false,
        },
        {
            id: 3,
            component: 'ManageSBIInformation',
            status: false,
        },
        {
            id: 4,
            component: 'ManagePartnerProfile',
            status: false,
        }
    ]

    const activeTheIcon = (id) => {
        for (let i = 0; i > Options.length; i++) {
            if (i === id) {
                Options[id].status = true;
            }
            else {
                Options[id].status = false;
            }
        }
    };


    return (
        <div className="flex font-inter">
            <div className={`${open ? "w-72" : "w-20"} h-screen duration-500`}>
                <div className={`flex h-16 gap-x-4 items-center pl-8 ${open ? 'shadow-md' : 'shadow-sm'}`}>
                    <img src={mosip_icon}/>
                    <div className={`${!open && 'scale-0'} items-center duration-300`}>
                        <h1 className="text-black origin-left font-bold">MOSIP</h1>
                        <p className="text-black origin-left font-semibold text-xs text-nowrap">Partner Management Portal</p>
                    </div>
                </div>
                <ul className="mt-5 items-center">
                    <li onClick={() => setActiveIcon(!activeIcon)} className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <Home selectIcon={activeIcon} />
                    </li>
                    <li onClick={() => setActiveIcon(!activeIcon)} className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <ManagePartnerCertificate selectIcon={activeIcon} />
                    </li>
                    <li onClick={() => setActiveIcon(!activeIcon)} className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <ManageDeviceDetails selectIcon={activeIcon} />
                    </li>
                    <li onClick={() => setActiveIcon(!activeIcon)} className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <ManageSBIInformation selectIcon={activeIcon} />
                    </li>
                    <li onClick={() => setActiveIcon(!activeIcon)} className="flex items-center gap-x-4 duration-700 cursor-pointer">
                        <ManagePartnerProfile selectIcon={activeIcon} />
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default SideNav;