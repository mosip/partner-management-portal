import React, { useState } from 'react'

export const MenuItemDeviceDetails = (props) => {

  const [activeIcon, setActiveIcon] = useState(false);

  const DeviceDetails = () => {
    setActiveIcon(!activeIcon);
};
  
  return (
    <div className="flex gap-x-5 items-center mt-4 pl-1" onClick={DeviceDetails}>
      <div className={`h-6 w-1 ${activeIcon ? 'bg-tory-blue' : null} rounded-e-lg`}></div>
      <div className="h-10 p-3 rounded-md shadow-md">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="22.322"
          height="19.897"
          viewBox="0 0 22.322 19.897">
          <path id="dvr_FILL0_wght200_GRAD0_opsz24"
            d="M124.849-788.343a.718.718,0,0,0,.526-.22.718.718,0,0,0,.22-.526.718.718,0,0,0-.22-.526.718.718,0,0,0-.526-.22.718.718,0,0,0-.526.22.718.718,0,0,0-.22.526.718.718,0,0,0,.22.526A.718.718,0,0,0,124.849-788.343Zm0-4.849a.718.718,0,0,0,.526-.22.718.718,0,0,0,.22-.526.718.718,0,0,0-.22-.526.718.718,0,0,0-.526-.22.718.718,0,0,0-.526.22.718.718,0,0,0-.22.526.718.718,0,0,0,.22.526A.718.718,0,0,0,124.849-793.192Zm2.565,4.709h10.165V-789.7H127.414Zm0-4.849h10.165v-1.212H127.414Zm-.14,12.729v-2.425h-5.316a1.9,1.9,0,0,1-1.4-.561,1.9,1.9,0,0,1-.561-1.4v-13.056a1.9,1.9,0,0,1,.561-1.4,1.9,1.9,0,0,1,1.4-.561h17.905a1.9,1.9,0,0,1,1.4.561,1.9,1.9,0,0,1,.561,1.4v13.056a1.9,1.9,0,0,1-.561,1.4,1.9,1.9,0,0,1-1.4.561h-5.316v2.425Zm-5.316-3.637h17.905a.713.713,0,0,0,.513-.233.713.713,0,0,0,.233-.513v-13.056a.713.713,0,0,0-.233-.513.713.713,0,0,0-.513-.233H121.958a.713.713,0,0,0-.513.233.713.713,0,0,0-.233.513v13.056a.713.713,0,0,0,.233.513A.713.713,0,0,0,121.958-784.24Zm-.746,0v0Z"
            transform="translate(-119.75 800.25)"
            fill={activeIcon ? "#1447b2" : "#7a7e82"} stroke={activeIcon ? "#1447b2" : "#7a7e82"} strokeWidth="0.5" />
        </svg>

      </div>
      <p className={`duration-200 text-nowrap font-semibold text-xs`}>
        Device Details
      </p>
    </div>
  )
}
