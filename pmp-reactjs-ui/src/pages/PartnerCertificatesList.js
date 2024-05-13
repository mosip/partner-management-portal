import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import UploadCertificate from "./UploadCertificate";
import HttpService from "../services/HttpService";
import {formatDate} from "../utils/AppUtils"

function PartnerCertificatesList() {

    const [activeBtn, setActiveBtn] = useState(false);


    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);
    const [selectedPartnerData, setSelectedPartnerData] = useState(null);
    const [certificatesData, setcertificatesData] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(false);

    const clickOnUpload = (partner) => {
        setShowPopup(!showPopup);
        setSelectedPartnerData(partner);
    };

    const closePopup = (reload) => {
        setShowPopup(false);
        if (reload) {
            window.location.reload();
        }
    };

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const getOriginalCertificate = () => {
        setErrorMsg("The process of downloading the original certificate is in progress.")
    }

    const getMosipSignedCertificate = async (partner) => {
        try {
            const response = await HttpService.get('/api/partners/' + partner.partnerId + '/certificate');
            if (response != null) {
                const responseData = response.data;
                if (responseData.errors && responseData.errors.length > 0) {
                    const errorMessage = responseData.errors[0].message;
                    setErrorMsg(errorMessage);
                    console.error('Error:', errorMessage);
                } else {
                    const resData = responseData.response;
                    console.log('Response data:', resData);
                    const blob = new Blob([resData.certificateData], { type: 'application/x-x509-ca-cert' });
                    const url = window.URL.createObjectURL(blob);
                    const link = document.createElement('a');
                    link.href = url;
                    link.download = 'mosip_signed_certificate.cer';

                    document.body.appendChild(link);
                    link.click();

                    // Cleanup
                    window.URL.revokeObjectURL(url);
                    document.body.removeChild(link);
                }

            } else {
                setErrorMsg("There is some error in fetching the mosip signed certificate. Try again later!");
            }

        } catch (err) {
            console.error('Error fetching certificate:', err);
            setErrorMsg(err);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await HttpService.get('/api/partners/getAllCertificateDetails');
                if (response != null) {
                    const responseData = response.data;
                    if (responseData.errors && responseData.errors.length > 0) {
                        const errorMessage = responseData.errors[0].message;
                        setErrorMsg(errorMessage);
                        console.error('Error:', errorMessage);
                    } else {
                        const resData = responseData.response;
                        setcertificatesData(resData);
                        console.log('Response data:', resData);
                    }
                } else {
                    setErrorMsg("There is some error in fetching the certificates. Try again later!");
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, []);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    return (
        <div className=" flex-col w-full p-5 bg-anti-flash-white h-full font-inter">
            {!dataLoaded && (
                <div className="flex items-center justify-center h-4/5">

                    <div role="status" className="flex items-center">
                        <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                        </svg>
                        <p className="ml-3">Loading</p>
                    </div>

                </div>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className="flex justify-end max-w-7xl">
                            <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 mr-10">
                                <div>
                                    <p className="text-xs font-semibold text-white break-words">
                                        {errorMsg}
                                    </p>
                                </div>
                                <div className="mr-3 ml-5">
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="16.828"
                                        height="16.828"
                                        viewBox="0 0 16.828 16.828"
                                        onClick={cancelErrorMsg}
                                    >
                                        <path
                                            id="close_FILL0_wght400_GRAD0_opsz48"
                                            d="M 23.27308082580566 25.05710983276367 L 22.91953086853027 24.70355033874512 L 17.35000038146973 19.13401985168457 L 11.7804708480835 24.70355033874512 L 11.42691993713379 25.05710983276367 L 11.0733699798584 24.70355033874512 L 9.996450424194336 23.62663078308105 L 9.642889976501465 23.27308082580566 L 9.996450424194336 22.91953086853027 L 15.56597995758057 17.35000038146973 L 9.996450424194336 11.7804708480835 L 9.642889976501465 11.42691993713379 L 9.996450424194336 11.07336044311523 L 11.07338047027588 9.996439933776855 L 11.42693042755127 9.642889976501465 L 11.78048038482666 9.996450424194336 L 17.35000038146973 15.5659704208374 L 22.91953086853027 9.996450424194336 L 23.27308082580566 9.642889976501465 L 23.62663078308105 9.996450424194336 L 24.70355033874512 11.0733699798584 L 25.05710983276367 11.42691993713379 L 24.70355033874512 11.7804708480835 L 19.13401985168457 17.35000038146973 L 24.70355033874512 22.91953086853027 L 25.05710983276367 23.27308082580566 L 24.70355033874512 23.62663078308105 L 23.62663078308105 24.70355033874512 L 23.27308082580566 25.05710983276367 Z"
                                            transform="translate(-8.936 -8.936)"
                                            fill="#fff"
                                        />
                                    </svg>
                                </div>
                            </div>
                        </div>
                    )}
                    <div className="flex-col ml-1">
                        <div className="flex space-x-4">
                            <svg onClick={() => moveToHome()} className="mt-5 cursor-pointer"
                                xmlns="http://www.w3.org/2000/svg"
                                width="22.765" height="14.416" viewBox="0 0 22.765 17.416">
                                <path
                                    id="keyboard_backspace_FILL0_wght200_GRAD0_opsz24"
                                    d="M168-676.306l-8-8,8-8,1.067,1.067-6.18,6.18h18.671v1.507H162.887l6.18,6.18Z"
                                    transform="translate(-159.293 693.015)" stroke="#000" strokeWidth="1.5" />
                            </svg>

                            <div className="flex-col mt-4">
                                <h1 className="font-bold text-md text-blue-900">Partner Certificate</h1>
                                <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                                    Home</p>
                            </div>
                        </div>
                        <ul className="min-w-3.5 bg-white mt-3 rounded-lg shadow-md p-5 mr-8 pb-20">
                            {certificatesData.length === 0 ?
                                <div className="p-14 flex flex-col justify-center items-center w-full">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="273" height="132" viewBox="0 0 273 132">
                                        <g id="Group_58184" data-name="Group 58184" transform="translate(-633 -323)">
                                            <g id="Group_58183" data-name="Group 58183" transform="translate(0 5.431)">
                                                <g id="page_under_construction" transform="translate(595.167 316.495)">
                                                    <path id="Path_155" data-name="Path 155" d="M348.036,207.189H159.343c-1.754,0-3.18-1.805-3.18-4.025s1.427-4.025,3.18-4.025H348.036c1.754,0,3.18,1.805,3.18,4.025S349.79,207.189,348.036,207.189Z" transform="translate(-105.33 -114.842)" fill="#e6e6e6" />
                                                    <path id="Path_175" data-name="Path 175" d="M360.375,176.189H121.586a4.025,4.025,0,1,1,0-8.049H360.375a4.025,4.025,0,0,1,0,8.049Z" transform="translate(-65.868 -104.635)" fill="#e6e6e6" />
                                                    <path id="Path_155384" data-name="Path 155384" d="M119.084,176.189h90.358c.84,0,1.523-1.805,1.523-4.025s-.683-4.025-1.523-4.025H119.084c-.84,0-1.523,1.805-1.523,4.025S118.245,176.189,119.084,176.189Z" transform="translate(86.705 -63.735)" fill="#e6e6e6" />
                                                    <path id="Path_179" data-name="Path 179" d="M333.4,238.189H200.586a4.025,4.025,0,0,1,0-8.049H333.4a4.025,4.025,0,0,1,0,8.049Z" transform="translate(-145.728 -125.048)" fill="#e6e6e6" />
                                                    <path id="Path_155385" data-name="Path 155385" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(148.079 -83.682)" fill="#e6e6e6" />
                                                    <path id="Path_155386" data-name="Path 155386" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(146.535 -143.682)" fill="#d0d0d0" />
                                                    <path id="Path_155387" data-name="Path 155387" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(104.535 -143.682)" fill="#d0d0d0" />
                                                    <path id="Path_155388" data-name="Path 155388" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(62.535 -143.682)" fill="#d0d0d0" />
                                                    <path id="Path_155389" data-name="Path 155389" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(20.535 -143.682)" fill="#d0d0d0" />
                                                    <path id="Path_155390" data-name="Path 155390" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(-21.465 -143.682)" fill="#d0d0d0" />
                                                    <path id="Path_155391" data-name="Path 155391" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(-63.465 -143.682)" fill="#d0d0d0" />
                                                </g>
                                            </g>
                                            <g id="Rectangle_7160" data-name="Rectangle 7160" transform="translate(633 323)" fill="none" stroke="#d0d0d0" strokeWidth="1">
                                                <rect width="273" height="132" rx="20" stroke="none" />
                                                <rect x="0.5" y="0.5" width="272" height="131" rx="19.5" fill="none" />
                                            </g>
                                        </g>
                                    </svg>
                                    <p className="mt-5 ml-4 font-inter text-xs font-normal tracking-tight text-[#666666]"> No partner types are mapped to your user id</p>
                                </div> :
                                certificatesData.map((partner, index) => {
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
                                                            {partner.isCertificateAvailable ? partner.certificateName : "Please upload partner certificate here"}
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
                                                                        <a onClick={() => getOriginalCertificate()} className="block px-4 py-2 text-xs font-semibold text-gray-900">Original Certificate</a>
                                                                        <svg
                                                                            xmlns="http://www.w3.org/2000/svg"
                                                                            width="12.266" height="12.266" viewBox="0 0 15.266 15.266">
                                                                            <path id="download_FILL0_wght300_GRAD0_opsz24"
                                                                                d="M187.634-768.511l-4.345-4.345,1.073-1.1,2.509,2.509V-780H188.4v8.549l2.509-2.509,1.073,1.1Zm-5.793,3.777a1.776,1.776,0,0,1-1.305-.534,1.776,1.776,0,0,1-.534-1.305v-2.76h1.527v2.76a.3.3,0,0,0,.1.215.3.3,0,0,0,.215.1h11.586a.3.3,0,0,0,.215-.1.3.3,0,0,0,.1-.215v-2.76h1.527v2.76a1.776,1.776,0,0,1-.534,1.305,1.776,1.776,0,0,1-1.305.534Z"
                                                                                transform="translate(-180.001 779.999)" fill="#1447b2" />
                                                                        </svg>

                                                                    </div>
                                                                    <div className="flex items-center cursor-pointer">
                                                                        <a onClick={() => getMosipSignedCertificate(partner)} className="block px-4 py-2 text-xs font-semibold text-gray-900">MOSIP Signed Certificate</a>
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
                                                        <button onClick={() => clickOnUpload(partner)} className="text-xs p-3 py-2 text-blue-700 border border-blue-700 font-semibold rounded-md text-center">
                                                            Re-Upload
                                                        </button>
                                                    </div>
                                                    : <button onClick={() => clickOnUpload(partner)} className="bg-tory-blue h-9 w-28 text-white text-sm font-medium rounded-md">
                                                        Upload
                                                    </button>}
                                                {showPopup && (
                                                    <UploadCertificate closePopup={closePopup} partnerData={selectedPartnerData} />
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
                                                    <p className="font-semibold text-sm text-red-950">{formatDate(partner.certificateExpiryDate, 'date')}</p>
                                                </div>
                                                <div className="flex-col ml-36">
                                                    <p className="font-medium text-xs text-gray-400">Time of Upload</p>
                                                    <p className="font-semibold text-sm text-red-950">{formatDate(partner.certificateUploadDate, 'dateTime')}</p>
                                                </div>
                                            </div>
                                        </li>
                                    )
                                }
                                )
                            }
                        </ul>
                    </div>
                </>
            )}
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