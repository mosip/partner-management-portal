import React from "react";
import { useState} from "react";
import { useTranslation } from "react-i18next";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import { createRequest, getPartnerManagerUrl, handleServiceErrors, isLangRTL } from "../../utils/AppUtils";
import { HttpService } from "../../services/HttpService.js";
import { getUserProfile } from "../../services/UserProfileService.js";

function DeactivateOidcClient({ closePopUp, clientData }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const clickOnConfirm = async () => {
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        const request = createRequest({
            logoUri: clientData.logoUri,
            redirectUris: clientData.redirectUris,
            status: "INACTIVE",
            grantTypes: clientData.grantTypes,
            clientName: clientData.oidcClientName,
            clientAuthMethods: clientData.clientAuthMethods,
            clientNameLangMap: {
                "eng" : clientData.oidcClientName
            }
        });
        try {
            const response = await HttpService.put(getPartnerManagerUrl(`/oauth/client/${clientData.oidcClientId}`, process.env.NODE_ENV), request, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            const responseData = response.data;
            if (responseData && responseData.response) {
                setDataLoaded(true);
                window.location.reload();
            } else {
                setDataLoaded(true);
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } catch (err) {
            setDataLoaded(true);
            setErrorMsg(err);
        }
    }

    const styles = {
        loadingDiv: "!py-[35%]"
    }

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <div className={`bg-white md:w-[390px] w-[55%] mx-auto rounded-lg shadow-lg md:h-[240px] h-[260px]`}>
                {!dataLoaded && (
                    <LoadingIcon styleSet={styles}></LoadingIcon>
                )}
                {dataLoaded && (
                    <>
                        {errorMsg && (
                            <div className="flex justify-end absolute w-1/3">
                                <div className="flex justify-between items-center min-h-14 bg-[#C61818] rounded-xl p-3">
                                    <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                                </div>
                            </div>
                        )}
                        <div className={`p-[10%] flex-col text-center justify-center items-center`}>
                            <p className="text-[17px] font-semibold text-black break-words px-[6%]">
                                {t('deactivateOidcClient.oidcClientName')} - '{clientData.oidcClientName}'?
                            </p>
                            <p className="text-sm text-[#666666] break-words py-[6%]">
                                {t('deactivateOidcClient.description')}
                            </p>
                            <div className="flex flex-row items-center justify-center space-x-3 pt-[4%]">
                                <button onClick={() => closePopUp(false)} type="button" className="w-40 h-12 border-[#1447B2] border rounded-md text-tory-blue text-sm font-semibold">{t('requestPolicy.cancel')}</button>
                                <button onClick={() => clickOnConfirm()}type="button" className={`w-40 h-12 border-[#1447B2] border rounded-md bg-tory-blue text-white text-sm font-semibold ${isLoginLanguageRTL && '!mr-3'}`}>{t('deactivateOidcClient.confirm')}</button>
                            </div>
                        </div>
                    </>
                )}
            </div>
        </div>
    )
}

export default DeactivateOidcClient;