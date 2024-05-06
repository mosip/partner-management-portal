import { useNavigate } from "react-router-dom";

function NewPage() {
    const navigate = useNavigate();

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    return (
        <div className=" flex-col w-screen p-5 bg-anti-flash-white h-full font-inter">
            <div className="flex-col ml-4">
                <h1 className="font-bold text-md text-blue-900">Partner Certificate</h1>
                <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">Home</p>
                <ul className="min-w-3.5 bg-white mt-3 rounded-lg shadow-md p-5 mr-8 pb-20">
                    <li className="rounded-lg shadow-lg border">
                        <div className="flex p-5 items-center bg-slate-100 justify-between">
                            <div className="flex">
                                <svg className="h-11"
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="36.754" height="47.255" viewBox="0 0 36.754 47.255">
                                    <path
                                        id="upload_file_FILL0_wght200_GRAD0_opsz24"
                                        d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                        transform="translate(-200 840)" fill="#1347b2"
                                    />
                                </svg>
                                <div className="flex-col p-3 items-center">
                                    <h6 className="text-sm text-gray-600 font-medium">Please upload partner certificate here</h6>
                                    <p className="font-medium text-xs text-gray-400">Only .cer or .pem certificate formats are allowed for upload</p>
                                </div>
                            </div>
                            <button className="bg-tory-blue h-9 w-28 text-white text-sm font-medium rounded-md">Upload</button>
                        </div>
                        <div className="flex items-center p-5 bg-white">
                            <div className="flex-col">
                                <p className="font-medium text-xs text-gray-400">Partner Type</p>
                                <p className="font-semibold text-sm text-red-950">Authentication Partner</p>
                            </div>
                            <div className="flex-col ml-12">
                                <p className="font-medium text-xs text-gray-400">Expiry Date</p>
                                <p className="font-bold text-sm text-red-950">-</p>
                            </div>
                            <div className="flex-col ml-36">
                                <p className="font-medium text-xs text-gray-400">Time of Upload</p>
                                <p className="font-bold text-sm text-red-950">-</p>
                            </div>
                        </div>
                    </li>

                    <li className="rounded-lg shadow-lg border mt-6">
                        <div className="flex p-5 items-center bg-slate-100 justify-between">
                            <div className="flex">
                                <svg className="h-11"
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="36.754" height="47.255" viewBox="0 0 36.754 47.255">
                                    <path
                                        id="upload_file_FILL0_wght200_GRAD0_opsz24"
                                        d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                        transform="translate(-200 840)" fill="#1347b2"
                                    />
                                </svg>
                                <div className="flex-col p-3 items-center">
                                    <h6 className="text-sm text-gray-600 font-medium">Please upload partner certificate here</h6>
                                    <p className="font-medium text-xs text-gray-400">Only .cer or .pem certificate formats are allowed for upload</p>
                                </div>
                            </div>
                            <button className="bg-tory-blue h-9 w-28 text-white text-sm font-medium rounded-md">Upload</button>
                        </div>
                        <div className="flex items-center p-5 bg-white">
                            <div className="flex-col">
                                <p className="font-medium text-xs text-gray-400">Partner Type</p>
                                <p className="font-semibold text-sm text-red-950">FTM Chip Provider</p>
                            </div>
                            <div className="flex-col ml-12">
                                <p className="font-medium text-xs text-gray-400">Expiry Date</p>
                                <p className="font-bold text-sm text-red-950">-</p>
                            </div>
                            <div className="flex-col ml-36">
                                <p className="font-medium text-xs text-gray-400">Time of Upload</p>
                                <p className="font-bold text-sm text-red-950">-</p>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <hr className="w-[81.5rem] h-px ml-7 mt-9 bg-gray-200 border-0 "/>
            <div className="flex mt-7 ml-7 justify-between text-sm text-gray-400">
                <div>
                    <p>2024 © MOSIP - All rights reserved.</p>
                </div>
                <div className="flex justify-between">
                    <p className="mr-7">Documentation</p>
                    <p className="mr-7">MOSIP Community</p>
                    <p className="mr-7">Contact Us</p>
                </div>
            </div>
        </div>
    );
}

export default NewPage;