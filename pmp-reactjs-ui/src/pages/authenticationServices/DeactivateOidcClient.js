import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL } from "../../utils/AppUtils";
import backArrow from "../../svg/back_arrow.svg";
import { formatDate, moveToOidcClientsList } from "../../utils/AppUtils";

function DeactivateOidcClient() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const navigate = useNavigate();
    const [oidcClientDetails, setOidcClientDetails] = useState([]);

    useEffect(() => {
        const clientData = localStorage.getItem('selectedClientData');
        if (clientData) {
            try {
                const selectedClient = JSON.parse(clientData);
                setOidcClientDetails(selectedClient);
            } catch (error) {
                navigate('/partnermanagement/authenticationServices/oidcClientsList');
                console.error('Error in viewOidcClientDetails page :', error);
            }
        } else {
            navigate('/partnermanagement/authenticationServices/oidcClientsList');
        }
    }, [navigate]);

    const moveToHome = () => {
        navigate("/partnermanagement");
    };

    return (
        <>
            <div className={`flex-col w-full p-5 bg-anti-flash-white h-full font-inter mb-[2%] ${isLoginLanguageRTL ? "mr-[8%]" : "ml-[8%]"} overflow-x-scroll`}>
                <div className="flex justify-between mb-5">
                    <div className="flex items-center gap-x-2">
                        <img
                            src={backArrow}
                            alt=""
                            onClick={() => moveToOidcClientsList(navigate)}
                            className={`cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`}
                        />
                        <div className="flex-col">
                            <h1 className="font-bold text-lg text-md text-dark-blue">
                                {t("deactivateOidcClient.deactivateOidcClient")}
                            </h1>
                            <div className="flex space-x-1">
                                <p
                                    onClick={() => moveToHome()}
                                    className="font-semibold text-tory-blue text-xs cursor-pointer"
                                >
                                    {t("commons.home")} /
                                </p>
                                <p onClick={() => moveToOidcClientsList(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                                    {t("deactivateOidcClient.authenticationServiceSection")}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default DeactivateOidcClient;