import { useState } from 'react';

function UploadCertificate({closePopup}) {
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [selectedDomainType, setSelectedDomainType] = useState("");

    const openDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };
    const clickOnCancel = () => {
        console.log("Cancel button clicked");
        closePopup();
    };
    const selectDomainType = (option) => {
        setSelectedDomainType(option);
        openDropdown();
    };
    const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
            const fileName = file.name;
            const fileExtension = fileName.split('.').pop().toLowerCase();
            if (fileExtension === 'cer' || fileExtension === 'pem') {
                console.log(`Selected file: ${fileName}`);
            } else {
                alert('Please select a file with .cer or .pem extension.');
            }
        }
    };
    return(
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white md:w-[400px] w-[60%] mx-auto h-[500px] mt-5 rounded-lg shadow-lg">
                <div className="px-4 py-3">
                    <h3 className="text-md font-bold text-gray-900">Upload Partner Certificate</h3>
                    <p className="text-sm text-gray-600">Please select the fields and upload certificate.</p>
                </div>
                <div className="border-gray-200 border-opacity-75 border-t"></div>
                <div className="p-4">
                    <form>
                        <div className="mb-4">
                            <label className="block text-indigo-950 text-md font-semibold mb-2">Partner Type</label>
                            <input type="text" className="w-full h-15 px-3 py-2 border border-gray-300 rounded-md text-md text-gray-800 bg-gray-200 leading-tight focus:outline-none focus:shadow-outline" 
                                value={"Device Provider"} disabled />
                        </div>
                        <div className="mb-4">
                            <label className="block text-indigo-950 text-md font-semibold mb-2">Partner Domain Type<span className="text-red-500">*</span></label>
                            <div className="relative">
                                <button onClick={openDropdown} class="flex items-center justify-between w-full h-10 px-2 py-2 border border-gray-400 rounded-md text-md text-start text-gray-800 leading-tight focus:outline-none focus:shadow-none" type="button">
                                    <span>{selectedDomainType || "Enter Device Type"}</span>
                                    <svg class="w-3 h-2 ml-3 transform rotate-0 text-gray-500 text-sm" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4"/>
                                    </svg>
                                </button>
                                {isDropdownOpen && (
                                    <div className="absolute z-50 top-10 left-0 w-full">
                                        <div class="z-10 border border-gray-400 bg-white rounded-lg shadow-lg w-full dark:bg-gray-700 cursor-pointer">
                                            <a className="block px-4 py-2 text-base text-blue-950" onClick={() => selectDomainType("Domain Type 1")}>Domain Type 1</a>
                                            <div className="border-gray-100 border-t mx-2"></div>
                                            <a className="block px-4 py-2 text-base text-blue-950" onClick={() => selectDomainType("Domain Type 2")}>Domain Type 2</a>
                                            <div className="border-t border-gray-100 mx-2"></div>
                                            <a className="block px-4 py-2 text-base text-blue-950" onClick={() => selectDomainType("Domain Type 3")}>Domain Type 3</a>
                                            <div className="border-t border-gray-100 mx-2"></div>
                                            <a className="block px-4 py-2 text-base text-blue-950" onClick={() => selectDomainType("Domain Type 4")}>Domain Type 4</a>
                                        </div>
                                    </div>
                                )}
                            </div>
                        </div>
                    </form>
                    <div className="flex items-center justify-center w-full h-36 p-4 border-2 border-blue-300 rounded-lg bg-blue-50 bg-opacity-25 text-center cursor-pointer relative">
                        <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                            <div className="flex flex-col items-center justify-center mb-2 cursor-pointer">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="28.75"
                                    height="39.26"
                                    viewBox="0 0 36.754 47.255"
                                    >
                                    <path
                                        id="upload_file_FILL0_wght200_GRAD0_opsz24"
                                        d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                        transform="translate(-200 840)"
                                        fill="#1347b2"
                                    />
                                </svg>
                                <h5 className="text-gray-700 text-sm">
                                Please tap to select the certificate
                                </h5>
                                <p className="text-sm text-gray-400">
                                Only .cer or .pem certificate formats are allowed for upload
                                </p>
                            </div>
                        </label>
                        <input id="fileInput" type="file" className="hidden" accept=".cer,.pem" onChange={handleFileChange} />
                    </div>
                </div>
                <div className="border-gray-200 border-opacity-50 border-t"></div>
                <div className="p-4 flex justify-end">
                    <button className="mr-2 w-36 h-10 border-blue-700 border rounded-md text-blue-700 text-base font-semibold" onClick={clickOnCancel}>Cancel</button>
                    <button disabled className="w-36 h-10 border-zinc-400 border bg-zinc-400 rounded-md text-white text-base font-semibold">Upload</button>
                </div>
            </div>
        </div>
        
    );
}

export default UploadCertificate;