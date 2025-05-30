import { useState, useEffect } from 'react';
import { HttpService } from "../../services/HttpService.js";
import {
    getPartnerTypeDescription, createRequest,
    getPolicyGroupList, getPartnerManagerUrl, handleServiceErrors, logout
} from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService.js';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';
import DropdownWithSearchComponent from '../common/fields/DropdownWithSearchComponent.js';
import FocusTrap from 'focus-trap-react';

function SelectPolicyPopup() {
    const [selectedPolicyGroup, setSelectedPolicyGroup] = useState("");
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();
    const userprofile = getUserProfile();
    const [isExpanded, setIsExpanded] = useState(false);

    const descriptionText = t('selectPolicyPopup.description');
    const maxWords = 20;
    const displayText = isExpanded ? descriptionText : `${descriptionText.split(' ').slice(0, maxWords).join(' ')}...`;

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    const expandDescription = () => {
        setIsExpanded(!isExpanded);
    };

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            await getPolicyGroupList(HttpService, setPolicyGroupList, setErrorCode, setErrorMsg, t);
            setDataLoaded(true);
        };
        fetchData();
    }, []);

    const changePolicyGroupSelection = (fieldName, policyGrpId) => {
        setSelectedPolicyGroup(policyGrpId);
    };
    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const clickOnSubmit = async () => {
        setDataLoaded(false);
        const userProfile = getUserProfile();
        const registerUserRequest = createRequest({
            partnerId: userProfile.userName,
            organizationName: userProfile.orgName,
            address: userProfile.address,
            contactNumber: userProfile.phoneNumber,
            emailId: userProfile.email,
            partnerType: userProfile.partnerType,
            policyGroup: selectedPolicyGroup,
            langCode: userProfile.langCode,
        });
        const registerUserResponse = await HttpService.post(getPartnerManagerUrl('/partners', process.env.NODE_ENV), registerUserRequest);
        const registerUserResponseData = registerUserResponse.data;
        if (registerUserResponseData && registerUserResponseData.response) {
            setDataLoaded(true);
            window.location.reload();
        } else {
            setDataLoaded(true);
            handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
        }
    }

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !my-2 mb-0",
        dropdownButton: "!w-full min-w-fit !h-10 !rounded-md !text-sm !text-dark-blue",
        selectionBox: "",
        loadingDiv: "!py-[50%]"
    }

    const customStyle = {
        outerDiv: "!flex !justify-end !relative !items-center !w-full",
        innerDiv: "!flex !justify-between !items-center !w-full !min-h-14 !px-3 !py-2"
    }

    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter -mt-[2rem]">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white w-1/3 h-fit rounded-xl shadow-lg`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles} />
                    )}
                    {dataLoaded && (
                        <>
                            <div className="px-4 py-2">
                                <h3 className="text-base font-bold text-[#333333]">{t('selectPolicyPopup.title')}</h3>
                            </div>
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            <div className="py-3 px-4 text-sm text-[#414141]">
                                <p>
                                    {displayText}
                                </p>
                                {descriptionText.split(' ').length > maxWords && (
                                    <button id="select_policy_group_view_text" className="text-tory-blue text-sm font-semibold" onClick={expandDescription}>
                                        {isExpanded ? t('selectPolicyPopup.viewLess') : t('selectPolicyPopup.viewMore')}
                                    </button>
                                )}
                                <form>
                                    <div className="pt-2 w-full mb-1 flex flex-col">
                                        <div className="flex flex-col">
                                            <label className="block text-dark-blue text-sm font-semibold mb-2">
                                                {t('selectPolicyPopup.partnerTypeLabel')}<span className="text-red-500 pl-1">*</span>
                                            </label>
                                            <button id='select_policyPopup_partner_type' disabled className="flex items-center justify-between w-full h-10 px-2 py-2 border border-gray-300 rounded-md text-sm text-dark-blue bg-gray-200 leading-tight focus:outline-none focus:shadow-outline" type="button">
                                                <span>{getPartnerTypeDescription(userprofile.partnerType, t)}</span>
                                            </button>
                                        </div>
                                        <div className="flex flex-col">
                                            <DropdownWithSearchComponent
                                                fieldName='policyGroup'
                                                dropdownDataList={policyGroupList}
                                                onDropDownChangeEvent={changePolicyGroupSelection}
                                                fieldNameKey='selectPolicyPopup.policyGroup*'
                                                placeHolderKey='selectPolicyPopup.title'
                                                searchKey='commons.search'
                                                selectPolicyPopup
                                                styleSet={styles}
                                                id="select_policy_group_dropdown">
                                            </DropdownWithSearchComponent>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border-[#E5EBFA] border-t mx-2"></div>
                            <div className="p-3 flex justify-between relative">
                                <p className="text-[#333333] text-sm font-semibold ml-2">{t('selectPolicyPopup.logoutMsg')}
                                    <button id="select_policy_group_logout" className="text-tory-blue font-semibold cursor-pointer" onClick={logout}>
                                        <span> {t('commons.logout')}</span>
                                    </button>
                                </p>
                                <button
                                    className={`w-40 h-10 m-1 border-[#1447B2] border rounded-lg text-white text-sm font-semibold relative z-60 
                                ${selectedPolicyGroup ? 'bg-tory-blue cursor-pointer' : 'bg-gray-400 cursor-not-allowed opacity-55'}`}
                                    onClick={clickOnSubmit}
                                    disabled={!selectedPolicyGroup}
                                    id="select_policy_group_submit"
                                >
                                    {t('selectPolicyPopup.submit')}
                                </button>
                            </div>
                        </>
                    )}
                </div>
            </FocusTrap>
        </div>

    );
}

export default SelectPolicyPopup;