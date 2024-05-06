import { useState } from "react";

export const PartnerCertificate = (props) => {

    return (
        <div className="flex gap-x-5 items-center mt-4">
            <div className={`h-5 w-1 ${props.selectIcon ? 'bg-tory-blue' : null} rounded-e-xl`}></div>
            <div className="h-10 p-3 rounded-md shadow-md">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="15.587" height="19.897" viewBox="0 0 15.587 19.897">
                    <path id="upload_file_FILL0_wght200_GRAD0_opsz24"
                        d="M207-824.084h1.078v-5.059l2.263,2.263.763-.769-3.564-3.564-3.564,3.564.769.763L207-829.143Zm-5.264,3.482a1.686,1.686,0,0,1-1.242-.5,1.686,1.686,0,0,1-.5-1.242v-15.916a1.686,1.686,0,0,1,.5-1.242,1.686,1.686,0,0,1,1.242-.5h8.5l4.849,4.849v12.807a1.686,1.686,0,0,1-.5,1.242,1.686,1.686,0,0,1-1.242.5Zm7.958-14.009v-4.31h-7.958a.634.634,0,0,0-.456.207.634.634,0,0,0-.207.456v15.916a.634.634,0,0,0,.207.456.634.634,0,0,0,.456.207h11.605a.634.634,0,0,0,.456-.207.634.634,0,0,0,.207-.456v-12.268Zm-8.621-4.31v0Z"
                        transform="translate(-199.75 840.25)"
                        fill={props.selectIcon ? "#1447b2" : "#7a7e82"} stroke={props.selectIcon ? "#1447b2" : "#7a7e82"} stroke-width="0.5" />
                </svg>
            </div>
            <p className={`duration-200 text-nowrap font-semibold text-xs`}>
                Partner Certificate
            </p>
        </div>
    )
}
