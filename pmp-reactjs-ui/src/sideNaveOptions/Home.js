import React, { useState } from 'react'

export const Home = (props) => {

    return (
        <div className="flex gap-x-5 items-center mt-4 font-inter">
            <div className={`h-5 w-1 ${props.selectIcon ? 'bg-tory-blue' : null} rounded-e-xl`}></div>
            <div className="h-10 p-3 rounded-md shadow-md">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="18" height="19.699"
                    viewBox="0 0 18 19.699">
                    <path id="home_FILL0_wght300_GRAD0_opsz24"
                        d="M181.8-797.818h4.015v-6.929h6.369v6.929H196.2v-10.494l-7.2-5.269-7.2,5.269Zm-1.8,1.749v-13.118l9-6.581,9,6.581v13.118h-7.615V-803h-2.769v6.929ZM189-805.7Z"
                        transform="translate(-180.001 815.767)"
                        fill={props.selectIcon ? "#1447b2" : "#7a7e82"}
                    />
                </svg>
            </div>
            <p className={`duration-200 text-nowrap font-semibold text-xs`}>
                Home
            </p>
        </div>
    )
}
