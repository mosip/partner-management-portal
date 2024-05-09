import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UploadCertificate from "./UploadCertificate";

function PartnerCertificatesList() {

    const [activeBtn, setActiveBtn] = useState(false);

    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);

    var dateAndMonth = new Date().getDate() + '-' + new Date().getMonth();
    var expiryYear = parseInt(new Date().getFullYear()) + 1;

    const expiryDate = [dateAndMonth, expiryYear].join('-');
    const uploadDate = [expiryDate + ' ' + new Date().toLocaleTimeString('en-GB')];

    const certificatesData = [
        {
            partnerId: 0,
            partnerType: "Authentication Partner",
            isCertificateAvailable: true,
            uploadDt: uploadDate,
            expiryDt: expiryDate
        },
        {
            partnerId: 1,
            partnerType: "FTM Chip Provider",
            isCertificateAvailable: false,
            uploadDt: "-",
            expiryDt: "-"
        }
    ];

    const uploadCertificate = () => {
        setShowPopup(!showPopup);
    };

    const closePopup = () => {
        console.log("Popup closed");
        setShowPopup(false);
        window.location.reload();
    };

    const moveToHome = () => {
        navigate('/partnermanagement')
    };


    return (
        <div className=" flex-col w-full p-5 bg-anti-flash-white h-full font-inter">
            <div className="flex-col ml-1">
                <div className="flex space-x-4">
                    <svg onClick={() => moveToHome()} className="mt-5 cursor-pointer"
                        xmlns="http://www.w3.org/2000/svg"
                        width="22.765" height="14.416" viewBox="0 0 22.765 17.416">
                        <path
                            id="keyboard_backspace_FILL0_wght200_GRAD0_opsz24"
                            d="M168-676.306l-8-8,8-8,1.067,1.067-6.18,6.18h18.671v1.507H162.887l6.18,6.18Z"
                            transform="translate(-159.293 693.015)" stroke="#000" stroke-width="1.5" />
                    </svg>

                    <div className="flex-col mt-4">
                        <h1 className="font-bold text-md text-blue-900">Partner Certificate</h1>
                        <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                            Home</p>
                    </div>
                </div>

                <ul className="min-w-3.5 bg-white mt-3 rounded-lg shadow-md p-5 mr-8 pb-20">
                    {certificatesData.map((partner, index) => {
                        return (
                            <li key={index} className="rounded-lg shadow-lg border mb-6">
                                <div className={`flex p-5 items-center ${partner.isCertificateAvailable ? "bg-green-50" : "bg-slate-100"} justify-between`}>
                                    <div className="flex items-center">
                                        {partner.isCertificateAvailable
                                            ? <svg className="h-11"
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="36.754" height="47.255" viewBox="0 0 36.754 47.255">
                                                <path id="description_FILL0_wght200_GRAD0_opsz24"
                                                    d="M209.188-801.934h18.377v-2.625H209.188Zm0-10.5h18.377v-2.625H209.188Zm-4.948,19.69a4.108,4.108,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                                    transform="translate(-200 840)" fill="#1d9027" />
                                            </svg>
                                            : <svg className="h-11"
                                                xmlns="http://www.w3.org/2000/svg"
                                                width="36.754" height="47.255" viewBox="0 0 36.754 47.255">
                                                <path
                                                    id="upload_file_FILL0_wght200_GRAD0_opsz24"
                                                    d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                                                    transform="translate(-200 840)" fill="#1347b2"
                                                />
                                            </svg>
                                        }

                                        <div className="flex-col p-3 items-center">
                                            <h6 className={`text-sm text-gray-600 ${partner.isCertificateAvailable ? 'font-bold' : 'font-medium'}`}>
                                                {partner.isCertificateAvailable ? "Certificate Name Goes Here.CER" : "Please upload partner certificate here"}
                                            </h6>
                                            <p className="font-medium text-xs text-gray-400">{partner.isCertificateAvailable ? null : "Only .cer or .pem certificate formats are allowed for upload"}</p>
                                        </div>
                                    </div>
                                    {partner.isCertificateAvailable
                                        ? <div className=" flex space-x-4">
                                            <div className="flex-col">
                                                <button onClick={() => setActiveBtn(!activeBtn)} className={`flex items-center ${activeBtn ? 'bg-blue-800 text-white' : 'text-blue-700'} text-xs px-2 py-2 mr-1 text-blue-700 border border-blue-700 font-semibold rounded-lg text-center`}>
                                                    Download
                                                    <svg
                                                        xmlns="http://www.w3.org/2000/svg" className={`${activeBtn ? 'rotate-180 duration-700 text-white' : null} ml-2`}
                                                        width="10" height="8" viewBox="0 0 10 8">
                                                        <path id="Polygon_8"
                                                            data-name="Polygon 8"
                                                            d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                                                            transform="translate(10 8) rotate(180)" fill={`${activeBtn ? '#ffff' : '#1447b2'}`} />
                                                    </svg>

                                                </button>

                                                {activeBtn && (
                                                    <div className="absolute py-2 px-1 mr-2 right-48 origin-bottom-left rounded-md bg-white shadow-lg ring-gray-50 border duration-700">
                                                        <div className="flex items-center border-b-2 justify-between cursor-pointer">
                                                            <a href="#" className="block px-4 py-2 text-xs font-semibold text-gray-900">Original Certificate</a>
                                                            <svg
                                                                xmlns="http://www.w3.org/2000/svg"
                                                                width="12.266" height="12.266" viewBox="0 0 15.266 15.266">
                                                                <path id="download_FILL0_wght300_GRAD0_opsz24"
                                                                    d="M187.634-768.511l-4.345-4.345,1.073-1.1,2.509,2.509V-780H188.4v8.549l2.509-2.509,1.073,1.1Zm-5.793,3.777a1.776,1.776,0,0,1-1.305-.534,1.776,1.776,0,0,1-.534-1.305v-2.76h1.527v2.76a.3.3,0,0,0,.1.215.3.3,0,0,0,.215.1h11.586a.3.3,0,0,0,.215-.1.3.3,0,0,0,.1-.215v-2.76h1.527v2.76a1.776,1.776,0,0,1-.534,1.305,1.776,1.776,0,0,1-1.305.534Z"
                                                                    transform="translate(-180.001 779.999)" fill="#1447b2" />
                                                            </svg>

                                                        </div>
                                                        <div className="flex items-center cursor-pointer">
                                                            <a href="#" className="block px-4 py-2 text-xs font-semibold text-gray-900">MOSIP Signed Certificate</a>
                                                            <svg
                                                                xmlns="http://www.w3.org/2000/svg"
                                                                width="12.266" height="12.266" viewBox="0 0 15.266 15.266">
                                                                <path id="download_FILL0_wght300_GRAD0_opsz24"
                                                                    d="M187.634-768.511l-4.345-4.345,1.073-1.1,2.509,2.509V-780H188.4v8.549l2.509-2.509,1.073,1.1Zm-5.793,3.777a1.776,1.776,0,0,1-1.305-.534,1.776,1.776,0,0,1-.534-1.305v-2.76h1.527v2.76a.3.3,0,0,0,.1.215.3.3,0,0,0,.215.1h11.586a.3.3,0,0,0,.215-.1.3.3,0,0,0,.1-.215v-2.76h1.527v2.76a1.776,1.776,0,0,1-.534,1.305,1.776,1.776,0,0,1-1.305.534Z"
                                                                    transform="translate(-180.001 779.999)" fill="#1447b2" />
                                                            </svg>

                                                        </div>
                                                    </div>)}
                                            </div>
                                            <button onClick={uploadCertificate} className="text-xs p-3 py-2 text-blue-700 border border-blue-700 font-semibold rounded-md text-center">
                                                Re-Upload
                                            </button>
                                        </div>
                                        : <button onClick={uploadCertificate} className="bg-tory-blue h-9 w-28 text-white text-sm font-medium rounded-md">
                                            Upload
                                        </button>}
                                    {showPopup && (
                                        <UploadCertificate closePopup={closePopup} />
                                    )}
                                </div>
                                <hr className="border" />
                                <div className="flex items-center p-5 bg-white">
                                    <div className="flex-col">
                                        <p className="font-medium text-xs text-gray-400">Partner Type</p>
                                        <p className="font-semibold text-sm text-red-950">{partner.partnerType}</p>
                                    </div>
                                    <div className="flex-col ml-12">
                                        <p className="font-medium text-xs text-gray-400">Expiry Date</p>
                                        <p className="font-semibold text-sm text-red-950">{partner.expiryDt}</p>
                                    </div>
                                    <div className="flex-col ml-36">
                                        <p className="font-medium text-xs text-gray-400">Time of Upload</p>
                                        <p className="font-semibold text-sm text-red-950">{partner.uploadDt}</p>
                                    </div>
                                </div>
                            </li>
                        )
                    }
                    )}
                </ul>
            </div>
            <hr className="h-px ml-7 mt-9 bg-gray-200 border-0 " />
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

export default PartnerCertificatesList;