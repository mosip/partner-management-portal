import { useState, useEffect, useCallback } from 'react';
import { HttpService } from "../../services/HttpService.js";
import {
    getPartnerTypeDescription, createRequest,
    getPartnerManagerUrl, handleServiceErrors, logout, isLangRTL,
} from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService.js';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';
import SuccessMessage from '../common/SuccessMessage.js';
import PolicyGroupSelector from '../common/PolicyGroupSelector.js';
import TextInputComponent from '../common/fields/TextInputComponent.js';
import FocusTrap from 'focus-trap-react';
import PropTypes from 'prop-types';

function SelectPolicyPopup({ 
    onClose, 
    onSubmit, 
    isLinkPolicyGroupPopup = false, 
    partnerId = null,
    partnerType = null
}) {
    const [selectedPolicyGroup, setSelectedPolicyGroup] = useState(null);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [linkPolicyGroupResponse] = useState(null);
    const { t } = useTranslation();
    const userprofile = getUserProfile();
    const [isExpanded, setIsExpanded] = useState(false);

    const descriptionText = t('selectPolicyPopup.description');
    const maxWords = 20;
    const displayText = isExpanded ? descriptionText : `${descriptionText.split(' ').slice(0, maxWords).join(' ')}...`;
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    const handleClose = () => {
        if (onClose) {
            onClose();
        }
    };

    const expandDescription = () => {
        setIsExpanded(!isExpanded);
    };

    // Remove the useEffect for fetching policy group data as it's now handled by PolicyGroupDropdown

    const handlePolicyGroupSelect = useCallback((policyGroup) => {
        setSelectedPolicyGroup(policyGroup);
    }, []);

    const cancelErrorMsg = useCallback(() => {
        setErrorMsg("");
    }, []);

    const cancelSuccessMsg = useCallback(() => {
        setSuccessMsg("");
    }, []);

    // Handle linking policy group to existing user
    const handleLinkPolicyGroup = async () => {
        const policyGroupId = selectedPolicyGroup ? selectedPolicyGroup.id : null;
        
        const linkPolicyGroupRequest = createRequest({
            policyGroupId: policyGroupId
        }, "mosip.pms.link.policy.group.post", true);
        
        const linkPolicyGroupResponse = await HttpService.post(
            getPartnerManagerUrl(`/${partnerId}/policy-group`, process.env.NODE_ENV), 
            linkPolicyGroupRequest
        );
        
        const linkPolicyGroupResponseData = linkPolicyGroupResponse.data;
        
        if (linkPolicyGroupResponseData && linkPolicyGroupResponseData.response) {
            setDataLoaded(true);
            // Directly close popup and call onSubmit when linking is successful
            if (onSubmit) {
                onSubmit(linkPolicyGroupResponseData);
            }
            if (onClose) {
                onClose();
            }
        } else {
            setDataLoaded(true);
            handleServiceErrors(linkPolicyGroupResponseData, setErrorCode, setErrorMsg);
        }
    };

    // Handle new user registration
    const handleUserRegistration = async () => {
        const userProfile = getUserProfile();
        const registerUserRequest = createRequest({
            partnerId: userProfile.userName,
            organizationName: userProfile.orgName,
            address: userProfile.address,
            contactNumber: userProfile.phoneNumber,
            emailId: userProfile.email,
            partnerType: userProfile.partnerType,
            policyGroup: selectedPolicyGroup ? selectedPolicyGroup.name : "",
            langCode: userProfile.langCode,
        }, "mosip.pms.create.partner.post", true);

        const registerUserResponse = await HttpService.post(
            getPartnerManagerUrl('/partners/v3', process.env.NODE_ENV),
            registerUserRequest
        );
        
        const registerUserResponseData = registerUserResponse.data;
        
        if (registerUserResponseData && registerUserResponseData.response) {
            setDataLoaded(true);
            if (onSubmit) {
                onSubmit(registerUserResponseData);
            }
            window.location.reload();
        } else {
            setDataLoaded(true);
            handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
        }
    };

    const clickOnSubmit = async () => {
        setSuccessMsg("");
        setErrorCode("");
        setErrorMsg("");
        
        if (successMsg && !isLinkPolicyGroupPopup) {
            // Close popup when success message is shown and close button is clicked (only for user registration)
            if (onSubmit && linkPolicyGroupResponse) {
                onSubmit(linkPolicyGroupResponse);
            }
            if (onClose) {
                onClose();
            }
        } else {
            setDataLoaded(false);
            
            try {
                if (isLinkPolicyGroupPopup) {
                    await handleLinkPolicyGroup();
                } else {
                    await handleUserRegistration();
                }
            } catch (error) {
                setDataLoaded(true);
                console.error('Error in clickOnSubmit:', error);
                if (error.response?.data) {
                    handleServiceErrors(error.response.data, setErrorCode, setErrorMsg);
                } else {
                    setErrorMsg(t('commons.errorOccurred'));
                }
            }
        }
    }

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !my-2 mb-0",
        dropdownButton: "!w-full min-w-fit !h-10 !rounded-md !text-sm !text-dark-blue",
        selectionBox: "",
        loadingDiv: "!py-[30%]"
    }

    const policyGroupSelectorStyles = {
        listboxheight: "max-h-36",
    }

    const customStyle = {
        outerDiv: "!flex !justify-end !relative !items-center !w-full",
        innerDiv: "!flex !justify-between !items-center !w-full !min-h-14 !px-3 !py-2"
    }

    const successcustomStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !rounded-none !md:w-[25rem] !w-full !min-h-[3.2rem] !h-fit !px-4 !py-[10px]",
        cancelIcon: "!mt-3 !top-1"
    }

    return (
        <div className="fixed inset-0 w-full overflow-y-auto flex items-center justify-center bg-black bg-opacity-40 z-50 font-inter pt-1 pb-1">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white w-full max-w-2xl h-fit rounded-xl shadow-lg mt-6 mb-6`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles} />
                    )}
                    {dataLoaded && (
                        <>
                            <div className="px-4 py-2">
                                <h3 id='select_policy_group_popup_title' className="text-base font-bold text-[#333333]">
                                    {t('selectPolicyPopup.title')}
                                </h3>
                                {isLinkPolicyGroupPopup && (
                                  <p id='select_policy_group_popup_subtitle' className={`text-xs font-bold text-[#717171] ${isLoginLanguageRTL ? "text-right" : "text-left"}`}># {partnerId}</p>
                                )}
                            </div>
                            {errorMsg && (
                                <ErrorMessage id='select_policy_popup_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            {successMsg && !isLinkPolicyGroupPopup && (
                                <SuccessMessage id='select_policy_popup_success_msg' successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={successcustomStyle} />
                            )}
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            <div className="py-3 px-4 text-sm text-[#414141]">
                                <p id='select_policy_group_popup_description' className={isLoginLanguageRTL ? "text-right" : "text-left"}>
                                    {displayText}
                                </p>
                                {descriptionText.split(' ').length > maxWords && (
                                    <button id="select_policy_group_view_text" className="text-tory-blue text-sm font-semibold" onClick={expandDescription}>
                                        {isExpanded ? t('selectPolicyPopup.viewLess') : t('selectPolicyPopup.viewMore')}
                                    </button>
                                )}
                                <form>
                                    <div className="pt-2 w-full mb-1 flex flex-col">
                                        <div className="flex flex-col mb-3">
                                            <TextInputComponent
                                                fieldName="partnerType"
                                                fieldNameKey="selectPolicyPopup.partnerTypeLabel*"
                                                placeHolderKey="selectPolicyPopup.partnerTypeLabel"
                                                textBoxValue={getPartnerTypeDescription(isLinkPolicyGroupPopup ? partnerType : userprofile.partnerType, t)}
                                                onTextChange={() => {}}
                                                styleSet={{ outerDiv: "mb-0" }}
                                                id="select_policyPopup_partner_type"
                                                disableField={true}
                                            />
                                        </div>
                                        <div className="flex flex-col">
                                            <PolicyGroupSelector
                                                id="select_policy_group_list"
                                                onPolicyGroupSelect={handlePolicyGroupSelect}
                                                selectedPolicyGroup={selectedPolicyGroup}
                                                style={policyGroupSelectorStyles}
                                                containsAsterisk={true}
                                            />
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border-[#E5EBFA] border-t mx-2"></div>
                            <div className={`p-3 flex relative ${isLinkPolicyGroupPopup ? 'justify-end' : 'justify-between'}`}>
                                {!isLinkPolicyGroupPopup && (
                                    <p className="text-[#333333] text-sm font-semibold ml-2">{t('selectPolicyPopup.logoutMsg')}
                                        <button id="select_policy_group_logout" className="text-tory-blue font-semibold cursor-pointer" onClick={logout}>
                                            <span> {t('commons.logout')}</span>
                                        </button>
                                    </p>
                                )}
                                {isLinkPolicyGroupPopup && (
                                    <button 
                                        id="select_policy_group_cancel" 
                                        className={`${isLoginLanguageRTL ? "ml-5" : "mr-5"} border-[#1447B2] bg-white text-tory-blue border rounded-md w-36 h-10 text-sm font-semibold`}
                                        onClick={handleClose}
                                    >
                                        {t('commons.cancel')}
                                    </button>
                                )}
                                <button
                                    className={`w-36 h-10 border-[#1447B2] border bg-tory-blue rounded-md text-white text-sm font-semibold relative ${
                                        !selectedPolicyGroup ? 'opacity-50 cursor-not-allowed' : ''
                                    }`}
                                    onClick={clickOnSubmit}
                                    disabled={!selectedPolicyGroup}
                                    id="select_policy_group_submit_btn"
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

SelectPolicyPopup.propTypes = {
    onClose: PropTypes.func,
    onSubmit: PropTypes.func,
    isLinkPolicyGroupPopup: PropTypes.bool,
    partnerId: PropTypes.string,
    partnerType: PropTypes.string
};

export default SelectPolicyPopup;