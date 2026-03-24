import React from "react";
import { useNavigate } from "react-router-dom";
import Title from "../../common/Title";
import { useTranslation } from "react-i18next";
import { isLangRTL, onPressEnterKey } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";

function BiometricProviderConfigurationList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  return (
    <div className={`w-full p-5 bg-anti-flash-white h-full font-inter break-words mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-1" : "ml-20 mr-1"} overflow-x-scroll`}>
      <div className="flex justify-between mb-5">
        <Title
          title="dashboard.biometricProviderConfiguration"
          subTitle="dashboard.biometricProviderConfigurationDesc"
          backLink="/partnermanagement"
        />
        <button
          id="bio_extractor_config_create_btn"
          type="button"
          onClick={() => navigate('/partnermanagement/admin/biometric-provider-configuration/create')}
          onKeyDown={(e) => onPressEnterKey(e, () => navigate('/partnermanagement/admin/biometric-provider-configuration/create'))}
          className="h-10 text-sm font-semibold px-4 py-2 bg-tory-blue text-white border border-[#1447B2] rounded-md"
        >
          {t('bioExtractorConfig.createBioExtractorConfig')}
        </button>
      </div>
    </div>
  );
}

export default BiometricProviderConfigurationList;
