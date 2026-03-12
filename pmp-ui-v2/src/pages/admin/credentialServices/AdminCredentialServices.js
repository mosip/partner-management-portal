import React from "react";
import Title from "../../common/Title";
import { useTranslation } from "react-i18next";
import { isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";

function AdminCredentialServices() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    return (
        <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-1" : "ml-20 mr-1"} overflow-x-scroll`}>
            <div className="flex justify-between mb-5">
                <Title
                    title="credentialServices.adminTitle"
                    subTitle="credentialServices.adminBreadcrumb"
                    backLink="/partnermanagement/dashboard"
                />
            </div>
            <div className="bg-snow-white h-full mt-1 rounded-xl shadow-lg" />
        </div>
    );
}

export default AdminCredentialServices;
