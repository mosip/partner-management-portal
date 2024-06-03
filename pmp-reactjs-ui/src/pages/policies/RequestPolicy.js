import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getPartnerManagerUrl, getPolicyManagerUrl, handleServiceErrors, createDropdownDataList } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import backArrow from '../../svg/back_arrow.svg';
import DropdownComponent from "../common/fields/DropdownComponent";
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent";

function RequestPolicy() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerId, setPartnerId] = useState("");
    const [policyName, setPolicyName] = useState("");
    const [partnerType, setPartnerType] = useState("");
    const [policyGroupName, setPolicyGroupName] = useState("");
    const [comments, setComments] = useState("");
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [partnerData, setPartnerData] = useState([]);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const moveToHome = () => {
        navigate('/partnermanagement')
    };

    const moveToPolicies = () => {
        navigate('/partnermanagement/policies')
    };

    const moveToPolicyConfirmation = () => {
        navigate('/partnermanagement/statusOfRequestPolicy')
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApprovedPolicyGroups', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setPartnerData(resData);
                        setPartnerIdData(createDropdownDataList('partnerId', partnerData));
                        console.log('Response data:', partnerData.length);
                    } else {
                        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('policies.errorInResponse'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                setErrorMsg(err);
            }
        };
        fetchData();
    }, [partnerData.length, t]);

    const onChangePartnerId = (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        // Find the selected partner data
        const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
        if (selectedPartner) {
            setPartnerType(selectedPartner.partnerType);
            setPolicyGroupName(selectedPartner.policyGroupName);
            getListofPolicies(selectedPartner.policyGroupName);
        }
    };

    const onChangePolicyGroupName = (fieldName, selectedValue) => {
        setPolicyName(selectedValue);
    };

    const getListofPolicies = async (policyGroupName) => {
        try {
            const response = await HttpService({
                url: getPolicyManagerUrl(`/policies/active/group/${policyGroupName}`, process.env.NODE_ENV),
                method: 'get',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response;
                    setPolicyNameData(createDropdownDataList('name', resData));
                    console.log(`Response data: ${resData.length}`);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('requestPolicy.errorInFetchingPolicyNames'));
            }
        } catch (err) {
            console.error('Error fetching policies:', err);
            setErrorMsg(err.message);
        }
    };

    const clearForm = () => {
        setPartnerId("");
        setPartnerType("");
        setPolicyGroupName("");
        setPolicyName("");
        setComments("");
    };

    const isFormValid = () => {
        return partnerId && policyName && comments;
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-base !mb-1",
        dropdownButton: "!w-full !h-12 !rounded-md !text-lg !text-left !text-grayish-blue",
        selectionBox: "!top-12"
    }

    const styleForSearch = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-base !mb-1",
        dropdownButton: "!w-full !h-12 !rounded-md !text-lg !text-left !text-grayish-blue",
        selectionBox: "!top-12"
    }

    return (
        <div className="ml-32 mr-5 mt-5 w-[100%]">
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className="flex justify-end max-w-7xl">
                            <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 mr-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col">
                        <div className="flex items-start space-x-3">
                            <img src={backArrow} alt="" onClick={() => moveToHome()} className="mt-[1%] cursor-pointer" />
                            <div className="flex-col">
                                <h1 className="font-semibold text-xl text-dark-blue">{t('requestPolicy.requestPolicy')}</h1>
                                <div className="flex space-x-1">
                                    <p onClick={() => moveToHome()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('commons.home')} /
                                    </p>
                                    <p onClick={() => moveToPolicies()} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                        {t('requestPolicy.policies')}
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                            <div className="p-[2.5%]">
                                <p className="text-lg text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                <form>
                                    <div className="flex flex-col">
                                        <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                            <div className="flex flex-col w-[48%]">
                                                <DropdownComponent
                                                    fieldName='partnerId'
                                                    dropdownDataList={partnerIdData}
                                                    onDropDownChangeEvent={onChangePartnerId}
                                                    fieldNameKey='requestPolicy.partnerId*'
                                                    placeHolderKey='requestPolicy.selectPartnerId'
                                                    selectedDropdownValue={partnerId}
                                                    styleSet={styles}>
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.partnerType')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-12 px-2 py-2 border border-[#C1C1C1] rounded-md text-lg text-grayish-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline" type="button">
                                                    <span>{partnerType || "Partner Type"}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex flex-row justify-between space-x-4 my-[1%]">
                                            <div className="flex flex-col w-[48%]">
                                                <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.policyGroup')}<span className="text-crimson-red">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-12 px-2 py-2 border border-[#C1C1C1] rounded-md text-lg text-grayish-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline" type="button">
                                                    <span>{policyGroupName || "Policy Group"}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                            <div className="flex flex-col w-[48%]">
                                                <DropdownWithSearchComponent
                                                    fieldName='policyName'
                                                    dropdownDataList={policyNameData}
                                                    onDropDownChangeEvent={onChangePolicyGroupName}
                                                    fieldNameKey='requestPolicy.policyName*'
                                                    placeHolderKey='requestPolicy.selectPolicyName'
                                                    selectedDropdownValue={policyGroupName}
                                                    searchKey='commons.search'
                                                    styleSet={styleForSearch}>
                                                </DropdownWithSearchComponent>
                                            </div>
                                        </div>
                                        <div className="flex my-[1%]">
                                            <div className="flex flex-col w-full">
                                                <label className="block text-dark-blue text-base font-semibold mb-1">{t('requestPolicy.comments')}<span className="text-crimson-red">*</span></label>
                                                <textarea value={comments} onChange={(e) => setComments(e.target.value)} className="w-full h-12 px-2 py-2 border border-[#707070] rounded-md text-lg text-dark-blue dark:placeholder-gray-400 bg-white leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-nowrap no-scrollbar" placeholder={t('requestPolicy.commentBoxDesc')}>
                                                </textarea>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row px-[3%] py-[2%] justify-between">
                                <button onClick={() => clearForm()} className="mr-2 w-40 h-12 border-[#1447B2] border rounded-md bg-white text-tory-blue text-base font-semibold">{t('requestPolicy.clearForm')}</button>
                                <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                                    <button onClick={() => moveToPolicies()} className="mr-2 w-full md:w-40 h-12 border-[#1447B2] border rounded-md bg-white text-tory-blue text-base font-semibold">{t('requestPolicy.cancel')}</button>
                                    <button disabled={!isFormValid()} onClick={()=>moveToPolicyConfirmation()} className={`mr-2 w-full md:w-40 h-12 border-[#1447B2] border rounded-md text-base font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                                        {t('requestPolicy.submit')}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default RequestPolicy;