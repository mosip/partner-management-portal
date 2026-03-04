import React from "react";
import Title from "../../common/Title";
import { useTranslation } from "react-i18next";
import { isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";

function CredentialServices() {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  return (
    <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-1" : "ml-20 mr-1"} overflow-x-scroll`}>
      <div className="flex justify-between mb-5">
        <Title
          title="credentialServices.title"
          subTitle="credentialServices.breadcrumb"
          backLink="/partnermanagement/dashboard"
        />
      </div>
      <div className="bg-snow-white h-full mt-1 rounded-xl shadow-lg flex items-center justify-center">
        <div className="max-w-xl text-center px-6 py-16">
          <h2 className="text-2xl font-semibold text-dark-blue mb-4">
            {t("credentialServices.comingSoonTitle")}
          </h2>
          <p className="text-sm text-gray-500 mb-6">
            {t("credentialServices.comingSoonDescription")}
          </p>
          <p className="text-xs text-gray-400">
            {t("credentialServices.comingSoonNote")}
          </p>
        </div>
      </div>
    </div>
  );
}

export default CredentialServices;

