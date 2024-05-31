/* eslint-disable react-hooks/exhaustive-deps */
import React from 'react';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import backArrow from '../../svg/back_arrow.svg';
import { formatDate } from '../../utils/AppUtils';
import adminImage from '../../svg/admin.png';
import partnerImage from '../../svg/partner.png';

function ViewPolicyDetails() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const [policyDetails, setPolicyDetails] = useState([]);

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const moveToPolicies = () => {
        navigate('/partnermanagement/policies')
    };

    function bgOfStatus(status) {
        if (status === "Approved") {
            return ("bg-[#D1FADF] text-[#155E3E]")
        }
        else if (status === "Rejected") {
            return ("bg-[#FAD6D1] text-[#5E1515]")
        }
        else if (status === "Pending for Approval") {
            return ("bg-[#FEF1C6] text-[#6D1C00]")
        }
        else if (status === "Deactivated") {
            return ("bg-[#EAECF0] text-[#525252]")
        }
    }
    const policyDetail = {
        "partnerId": "P10001",
        "partnerIdName": "partnerIdName",
        "partnerType": "partnerType",
        "policyGroup": "Policy Group Name1",
        "policyName": "Full KYC",
        "policyGroupDescription": "lorem ipsum",
        "policyNameDescription": "lorem ipsum",
        "createDate": "2024-05-21T02:11:42.422+00:00",
        "adminComments": "comments",
        "partnerComments": "comments",
        "adminCommentsDate": "2024-05-21T02:11:42.422+00:00",
        "partnerCommentsDate": "2024-05-21T02:11:42.422+00:00",
        "status": "Approved"
    };

    useEffect(() => {
        setPolicyDetails(policyDetail);
    }, []);

    return (
        <>
            <div className="flex-col w-full p-5 bg-anti-flash-white h-full font-inter mb-[2%] ml-[8%]">
                <div className="flex justify-between mb-5">
                    <div className="flex space-x-4">
                        <img src={backArrow} alt="" onClick={() => moveToPolicies()} className="mt-1 cursor-pointer" />
                        <div className="flex-col mt-4">
                            <h1 className="font-bold text-lg text-md text-blue-900">{t('viewPolicyDetails.viewPolicyDetails')}</h1>
                            <div className="flex space-x-1">
                                <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                                    {t('commons.home')} /
                                </p>
                                <p onClick={() => moveToPolicies()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                                    {t('viewPolicyDetails.policySection')}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="bg-white h-fit mt-1 rounded-t-xl shadow-lg">
                    <div className="flex flex-col pl-8 pt-6 pb-5">
                        <p className="font-bold text-lg text-blue-900 mb-3">PolicyName</p>
                        <div className="flex items-center justify-start">
                            <div className={`${bgOfStatus(policyDetails.status)}flex w-fit py-1.5 px-2 text-xs rounded-md`}>
                                {policyDetails.status}
                            </div>
                            <div className="font-medium ml-3 text-sm text-blue-900">{'Created on ' + formatDate(policyDetails.createDate, 'dateTime')}</div>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className="pl-8 pt-6 mr-8 mb-4">
                        <div className="flex flex-wrap">
                            <div className="w-[50%]">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.partnerId')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.partnerId}</p>
                            </div>
                            <div className="w-[50%]">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.partnerIdName')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.partnerIdName}</p>
                            </div>
                            <div className="my-5">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.partnerType')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.partnerType}</p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="flex flex-wrap pt-6">
                            <div className="w-[50%]">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.policyGroup')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.policyGroup}</p>
                            </div>
                            <div className="w-[50%]">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.policyName')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.policyName}</p>
                            </div>
                            <div className="w-[50%] my-5">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.policyGroupDescription')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.policyGroupDescription}</p>
                            </div>
                            <div className="w-[50%]  my-5">
                                <p className="font-semibold text-suva-gray text-base" >{t('viewPolicyDetails.policyNameDescription')}</p>
                                <p className="font-semibold text-vulcan text-lg">{policyDetails.policyNameDescription}</p>
                            </div>
                        </div>
                        <hr className="h-px w-full bg-gray-200 border-0" />
                        <div className="mt-4">
                            <p className="font-medium text-vulcan text-lg mb-4">{t('viewPolicyDetails.comments')}</p>
                            <div>
                                <div class="flex font-medium w-full  ">
                                    <span class=" w-8 h-8 rounded-full flex justify-center items-center mr-3 text-sm text-white lg:w-10 lg:h-10 ">
                                        <img
                                            src={adminImage}
                                            alt="Example"
                                            className=""
                                        />
                                    </span>
                                    <div class="flex bg-[#FFF9F0] w-full flex-col p-4">
                                        <h4 class="text-lg  text-indigo-600">{t('viewPolicyDetails.adminComments')}</h4>
                                        <span class="text-sm mt-4">Create Pagedone Account ushhsddshusdh dsksddshhu dshusdhudshu dshudshudsu dsudsusgufgcgfg dsgug</span>
                                        <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                        <div className="flex items-center justify-start">
                                            <div className={`${bgOfStatus(policyDetails.status)}flex w-fit py-1.5 px-2 text-xs rounded-md`}>
                                                {policyDetails.status}
                                            </div>
                                            <div className="font-medium ml-3 text-sm text-blue-900">{'Created on ' + formatDate(policyDetails.createDate, 'dateTime')}</div>
                                        </div>
                                    </div>
                                </div>
                                <div className="mt-4">
                                    <div class="flex font-medium w-full  ">
                                        <span class="w-8 h-8 rounded-full flex justify-center items-center mr-3 text-sm lg:w-10 lg:h-10">
                                            <img
                                                src={partnerImage}
                                                alt="Example"
                                                className=""
                                            />
                                        </span>
                                        <div class="flex bg-[#F2F5FC] w-full flex-col p-4">
                                            <h4 class="text-lg  text-indigo-600">{t('viewPolicyDetails.partnerComments')}</h4>
                                            <span class="text-sm mt-3">Create Pagedone Account ushhsddshusdh dsksddshhu dshusdhudshu dshudshudsu dsudsusgufgcgfg dsgug</span>
                                            <hr className="h-px w-full bg-gray-200 border-0 my-4" />
                                            <div className="flex items-center justify-start">
                                                <div className="font-medium text-sm text-blue-900">{'Created on ' + formatDate(policyDetails.createDate, 'dateTime')}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr className="h-px w-full bg-gray-200 border-0" />
                    <div className="flex justify-end py-5 mr-8">
                        <button  onClick={() => moveToPolicies()}className="h-11 w-[120px] text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center">
                        {t('viewPolicyDetails.back')}
                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ViewPolicyDetails;