import React from "react";
import Title from "../../common/Title";
import { useTranslation } from "react-i18next";
import { isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";

function BiometricProviderConfigurationList() {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  return (
    <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-1" : "ml-20 mr-1"} overflow-x-scroll`}>
      <div className="flex justify-between mb-5">
        <Title
          title="dashboard.biometricProviderConfiguration"
          subTitle="dashboard.biometricProviderConfigurationDesc"
          backLink="/partnermanagement"
        />
      </div>
    </div>
  );
}

export default BiometricProviderConfigurationList;
