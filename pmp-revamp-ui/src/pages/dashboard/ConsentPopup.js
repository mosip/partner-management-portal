import { useState, useEffect } from 'react';
import { HttpService } from "../../services/HttpService.js";
import { logout, onPressEnterKey, getPartnerManagerUrl, handleServiceErrors } from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';
import FocusTrap from 'focus-trap-react';

function ConsentPopup() {
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();
    const [isChecked, setIsChecked] = useState(false);

    const consentText = t('consentPopup.description');

    const styles = {
        loadingDiv: "!py-[20%]"
    }
    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    const handleCheckboxChange = () => {
        setIsChecked(!isChecked);
    };

    const saveUserConsent = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/users/user-consent`, process.env.NODE_ENV));
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    window.location.reload();
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('consentPopup.consentSaveError'));
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error: ", err);
        }
        setDataLoaded(true);
    }

    const customStyle = {
        outerDiv: "!flex !justify-end !absolute !min-h-10 !w-3/5 !mt-1 !px-0.5",
        innerDiv: "!flex !justify-between !items-center !rounded-xl !min-h-12 !p-3 !w-6/12"
    }

    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white w-3/5 mx-auto rounded-xl shadow-lg -mt-3`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles}></LoadingIcon>
                    )}
                    {dataLoaded && (
                        <>
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle} />
                            )}
                            <div className="p-4">
                                <h3 className="text-base font-bold text-[#333333]">{t('consentPopup.title')}</h3>
                            </div>
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            <div className="text-sm text-[#414141] w-full">
                                <div className="p-4"> <p>{consentText}</p></div>
                                <div className="flex items-start mt-3 bg-alice-green w-full px-4 py-3">
                                    <input id="default-checkbox" checked={isChecked} onChange={handleCheckboxChange} type="checkbox" value="" className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded mt-[3px]" tabIndex="0"
                                        onKeyDown={(e) => onPressEnterKey(e, handleCheckboxChange)} />
                                    <label className="ms-2 text-sm font-semibold">{t('consentPopup.checkBoxDesc')}</label>
                                </div>
                            </div>
                            <div className="border-[#E5EBFA] border-t mx-2"></div>
                            <div className="p-4 flex justify-between relative items-center">
                                <p className="text-[#333333] text-sm font-semibold">{t('consentPopup.logoutMsg')}
                                    <span role='button' id='consent_logout_btn' className="text-tory-blue font-semibold cursor-pointer underline underline-offset-2 px-[0.1rem] px" onClick={logout} tabIndex="0"
                                        onKeyDown={(e) => onPressEnterKey(e, logout)}> {t('commons.logout')}</span>
                                </p>
                                <button
                                    className={`w-40 h-10 mx-1 border-[#1447B2] border rounded-lg text-white text-sm font-semibold relative z-60 
                                ${isChecked ? 'bg-tory-blue cursor-pointer' : 'bg-gray-400 cursor-not-allowed opacity-55'}`}
                                    disabled={!isChecked}
                                    onClick={saveUserConsent}
                                    id='consent_proceed_btn'
                                >
                                    {t('consentPopup.proceed')}
                                </button>
                            </div>
                        </>
                    )}
                </div>
            </FocusTrap>
        </div>

    );
}

export default ConsentPopup;