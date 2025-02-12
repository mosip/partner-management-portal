import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import ErrorMessage from '../../common/ErrorMessage.js';
import LoadingIcon from '../../common/LoadingIcon.js';
import FocusTrap from 'focus-trap-react';
import { HttpService } from '../../../services/HttpService.js';
import { getPolicyManagerUrl, handleServiceErrors } from '../../../utils/AppUtils.js';
import SuccessMessage from '../../common/SuccessMessage.js';

function PublishPolicyPopup ({policyDetails, closePopUp, onClickPublish}) {
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [publishPolicySuccess, setPublishPolicySccesss] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    const cancelPopUp = () => {
        closePopUp();
    };

    const clickOnClose = () => {
        onClickPublish();
    };

    const clickOnPublish = async () => {
        setErrorCode("");
        setErrorMsg("");
        setPublishPolicySccesss(false);
        setSuccessMsg("");
        setDataLoaded(false);
        try {
            const response = await HttpService({
                url: getPolicyManagerUrl(`/policies/${policyDetails.policyId}/group/${policyDetails.policyGroupId}/publish`, process.env.NODE_ENV),
                method: 'post',
                baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
            });
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    setPublishPolicySccesss(true);
                    setSuccessMsg(t('publishPolicyPopup.successMsg'));
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
                setDataLoaded(true);
            } else {
                setDataLoaded(true);
                setErrorMsg(t('publishPolicyPopup.errorInPublishPolicy'));
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setDataLoaded(true);
            console.log("Error fetching data: ", err);
        }
    };
    const styles = {
        loadingDiv: "!py-[30%]"
    }

    const customStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !w-full !min-h-12 !p-3 !-mb-2",
        cancelIcon: "!top-4 !mt-[3.25rem]"
    }
    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[25rem] w-[50%] h-fit rounded-xl shadow-sm`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles} />
                    )}
                    {dataLoaded && (
                        <div className="relative">
                            <div className="px-6 py-3">
                                <h3 className="text-lg font-bold text-[#333333]">{t('publishPolicyPopup.title')}</h3>
                            </div>
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle}/>
                            )}
                            {successMsg && (
                                <SuccessMessage successParam={policyDetails.policyName} successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={customStyle}/>
                            )}
                            <div className="py-4 px-6">
                            <p className="text-sm font-normal text-[#414141] break-words">{t('publishPolicyPopup.description1')} 
                                    <span className="font-bold"> {policyDetails.policyName}</span> {t('publishPolicyPopup.description2')}
                                </p>
                            </div>
                            <div className="border-[#E5EBFA] border-t mx-2"></div>
                            <div className="px-6 py-3 flex justify-between relative">
                                <button disabled={publishPolicySuccess} className={`w-36 h-10 m-1 ${publishPolicySuccess ? 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed' : 'border-[#1447B2] text-tory-blue bg-white'}  border rounded-lg  text-sm font-semibold relative z-60`}
                                    onClick={cancelPopUp}
                                    id="publish_policy_cancel">
                                    {t('commons.cancel')}
                                </button>
                                { !publishPolicySuccess ? 
                                    <button className={`w-36 h-10 m-1  border rounded-lg text-white text-sm font-semibold relative z-60 
                                        bg-tory-blue border-[#1447B2] cursor-pointer`}
                                        onClick={clickOnPublish}
                                        id="publish_policy_button">
                                        {t('publishPolicyPopup.publish')}
                                    </button> : 
                                    <button className={`w-36 h-10 m-1  border rounded-lg text-white text-sm font-semibold relative z-60 bg-tory-blue border-[#1447B2] cursor-pointer'`}
                                        onClick={clickOnClose}
                                        id="publish_policy_close_button">
                                        {t('commons.close')}
                                    </button>
                                }
                                
                            </div>
                        </div>
                    )}
                </div>
            </FocusTrap>
        </div>
    );
}

export default PublishPolicyPopup;