import { useState } from "react";

export const SBIInformation = (props) => {

    return (
        <div className="flex gap-x-5 items-center mt-4 pl-1">
            <div className={`h-6 w-1 ${props.selectIcon ? 'bg-tory-blue' : null} rounded-e-md`}></div>
            <div className="h-10 p-3 rounded-md shadow-md">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="18.498" height="20.642" viewBox="0 0 18.498 20.642">
                    <g id="Group_58281"
                        data-name="Group 58281" transform="translate(-14.75 -13.378)">
                        <path
                            id="quick_reference_FILL0_wght200_GRAD0_opsz24"
                            d="M173.342-822.1h.861v-3.357h-.861Zm.43-4.282a.34.34,0,0,0,.306-.114.452.452,0,0,0,.125-.316.4.4,0,0,0-.136-.306.428.428,0,0,0-.295-.125.416.416,0,0,0-.306.125.416.416,0,0,0-.125.306.427.427,0,0,0,.125.295A.4.4,0,0,0,173.772-826.378Zm0-2.776a4.318,4.318,0,0,1,3.173,1.3,4.318,4.318,0,0,1,1.3,3.173,4.318,4.318,0,0,1-1.3,3.173,4.318,4.318,0,0,1-3.173,1.3,4.318,4.318,0,0,1-3.173-1.3,4.318,4.318,0,0,1-1.3-3.173,4.318,4.318,0,0,1,1.3-3.173A4.318,4.318,0,0,1,173.772-829.154Z"
                            transform="translate(-145 853.628)"
                            fill={props.selectIcon ? "#1447b2" : "#7a7e82"} />
                        <path
                            id="quick_reference_FILL0_wght200_GRAD0_opsz24-2"
                            data-name="quick_reference_FILL0_wght200_GRAD0_opsz24"
                            d="M161.119-838.881v0Zm2.8,10.631h3.66q.179-.3.38-.579t.425-.54h-4.465Zm0,4.476h2.752a3.791,3.791,0,0,1-.041-.549q0-.269.019-.57h-2.731Zm-2.109,3.917a1.751,1.751,0,0,1-1.29-.518,1.751,1.751,0,0,1-.518-1.29v-16.527a1.751,1.751,0,0,1,.518-1.29,1.751,1.751,0,0,1,1.29-.518h8.823l5.036,5.036v3.413q-.295-.082-.564-.133a4.872,4.872,0,0,0-.555-.073V-834.4h-4.476v-4.476h-8.263a.658.658,0,0,0-.473.215.658.658,0,0,0-.215.473v16.527a.658.658,0,0,0,.215.473.658.658,0,0,0,.473.215h5.855a6.164,6.164,0,0,0,.38.6,3.9,3.9,0,0,0,.447.519Z"
                            transform="translate(-145 853.628)"
                            fill={props.selectIcon ? "#1447b2" : "#7a7e82"} stroke={props.selectIcon ? "#1447b2" : "#7a7e82"} stroke-width="0.5" />
                    </g>
                </svg>

            </div>
            <p className={`duration-200 text-nowrap font-semibold text-xs`}>
                SBI Information
            </p>
        </div>
    )
}
