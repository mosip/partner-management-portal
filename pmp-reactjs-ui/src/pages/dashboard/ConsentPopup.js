import { useState } from 'react';
import { HttpService } from "../../services/HttpService.js";
import { logout } from '../../utils/AppUtils.js';
import { useTranslation } from 'react-i18next';
import ErrorMessage from "../common/ErrorMessage.js";
import LoadingIcon from '../common/LoadingIcon.js';
import { getPartnerManagerUrl, handleServiceErrors } from '../../utils/AppUtils.js';

function ConsentPopup() {
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();
    const [isChecked, setIsChecked] = useState(false);

    const consentText = t('consentPopup.description');

    const styles = {
        loadingDiv: "!py-[50%]"
    }
    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const handleCheckboxChange = () => {
        setIsChecked(!isChecked);
    };

    const saveUserConsent = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        document.body.style.overflow="auto";
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/partners/saveUserConsentGiven`, process.env.NODE_ENV));
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
            setErrorMsg(err);
            console.log("Error: ", err);
        }
        setDataLoaded(true);
    }

    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-50 z-50 font-inter">
            <div className={`bg-white w-3/5 mx-auto rounded-xl shadow-lg -mt-3`}>
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles}></LoadingIcon>
                )}
                {dataLoaded && (
                    <>
                        {errorMsg && (
                            <div className="flex justify-end absolute w-3/5 mt-2 px-2">
                                <div className="flex justify-between items-center min-h-13 bg-[#C61818] rounded-xl p-3 w-6/12">
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                                </div>
                            </div>
                        )}
                        <div className="p-4">
                            <h3 className="text-base font-bold text-[#333333]">{t('consentPopup.title')}</h3>
                        </div>
                        <div className="border-gray-200 border-opacity-75 border-t"></div>
                        <div className="text-sm text-[#414141] w-full">
                            <div className="p-4"> <p>{consentText}</p></div>
                            <div className="flex items-center mt-3 bg-alice-green w-full px-4 py-3">
                                <input id="default-checkbox" checked={isChecked} onChange={handleCheckboxChange} type="checkbox" value="" className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded " />
                                <label className="ms-2 text-sm font-semibold">{t('consentPopup.checkBoxDesc')}</label>
                            </div>
                        </div>
                        <div className="border-[#E5EBFA] border-t mx-2"></div>
                        <div className="p-4 flex justify-between relative items-center">
                            <p className="text-[#333333] text-sm font-semibold">{t('consentPopup.logoutMsg')}
                                <span className="text-tory-blue font-semibold cursor-pointer underline underline-offset-2 px-[0.1rem] px" onClick={logout}> {t('commons.logout')}</span>
                            </p>
                            <button
                                className={`w-40 h-10 mx-1 border-[#1447B2] border rounded-lg text-white text-sm font-semibold relative z-60 
                                ${isChecked ? 'bg-tory-blue cursor-pointer' : 'bg-gray-400 cursor-not-allowed opacity-55'}`}
                                disabled={!isChecked}
                                onClick={saveUserConsent}
                            >
                                {t('consentPopup.proceed')}
                            </button>
                        </div>
                    </>
                )}
            </div>
        </div>

    );
}

export default ConsentPopup;