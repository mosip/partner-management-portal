import { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createRequest, getPartnerManagerUrl, isLangRTL } from "../../../utils/AppUtils";
import { HttpService } from "../../../services/HttpService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import DropdownComponent from "../../common/fields/DropdownComponent";
import { handleServiceErrors } from "../../../utils/AppUtils";
import BlockerPrompt from "../../common/BlockerPrompt";
import { useBlocker } from "react-router-dom";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";

const EMPTY_MAPPING_ROW = {
  biometricModality: "",
  biometricProviderConfiguration: ""
};

const biometricAttributeMap = {   
  FACE: "photo",
  IRIS: "iris",
  FINGER: "fingerprint"
};


function MapBiometricExtractorProvider() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { state } = useLocation();
  const hasRequiredState = Boolean(state?.partnerId && state?.mappingKey);
  const userProfile = getUserProfile();
  const isLoginLanguageRTL = isLangRTL(userProfile?.locale || "en");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [rows, setRows] = useState([{ ...EMPTY_MAPPING_ROW }]);
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [activeConfigs, setActiveConfigs] = useState({});
  const modalityDropdownData = useMemo(() => ([
    { fieldCode: t("bioExtractorConfig.face"), fieldValue: "FACE" },
    { fieldCode: t("bioExtractorConfig.iris"), fieldValue: "IRIS" },
    { fieldCode: t("bioExtractorConfig.finger"), fieldValue: "FINGER" }
  ]), [t]);

  const getAttributeName = (modality) => {
  return biometricAttributeMap[(modality || "").toUpperCase()] || "";
};

const getConfigDropdownByModality = (index, selectedModality) => {
  const configs = activeConfigs[index] || [];

  return configs
    .filter(cfg =>
      !selectedModality ||
      (cfg.biometricModality || "").toUpperCase() === (selectedModality || "").toUpperCase()
    )
    .map(cfg => ({
      fieldCode: cfg.configurationName,
      fieldValue: cfg.configurationName
    }));
};
const shouldBlockNavigation = () => {
  return rows.some(
    row => row.biometricModality || row.biometricProviderConfiguration
  );
};

const blocker = useBlocker(({ currentLocation, nextLocation }) => {
  if (isSubmitClicked) {
    return false; 
  }

  return (
    shouldBlockNavigation() &&
    currentLocation.pathname !== nextLocation.pathname
  );
});

  useEffect(() => {
    if (!hasRequiredState) {
      navigate("/partnermanagement/policies/request-policy");
      }
      }, [hasRequiredState, navigate]);
      const fetchBioExtractorConfigs = async (index, modality) => {
        try {
          setDataLoaded(false);

          const response = await HttpService.get(
            getPartnerManagerUrl(
              `/bio-extractor-configurations?bioModality=${modality.toLowerCase()}`,
              process.env.NODE_ENV
            )
          );

          console.log("API RESPONSE:", response?.data);

          if (response?.data?.response) {
            const configs = response?.data?.response?.data || [];

          const formattedConfigs = configs.map(item => ({
            configurationName: item.configName,
            bioextractorProviderName:
              item.bioextractorProviderName ||
              item.extractorProviderName ||
              item.provider ||
              "",
            bioextractorProviderVersion:
              item.bioextractorProviderVersion ||
              item.extractorProviderVersion ||
              item.version ||
              "",
            biometricModality: item.bioModality || ""
          }));

            setActiveConfigs(prev => ({
              ...prev,
              [index]: formattedConfigs
            }));
          } else {
            setActiveConfigs([]);
          }

        } catch (err) {
          console.error("API ERROR:", err);
          setActiveConfigs([]);
        } finally {
          setDataLoaded(true);
        }
      };
      if (!hasRequiredState) {
         return null;
         }

  const policyDetails = {
    partnerId: state?.partnerId || "",
    partnerType: state?.partnerType || "",
    policyGroupName: state?.policyGroupName || "",
    policyName: state?.policyName || "",
    policyId: state?.policyId || "",
    mappingKey: state?.mappingKey || "",
    requestPayload: state?.requestPayload || null,
    partnerComment: state?.partnerComment || ""
  };

  const getSelectedConfig = (index, configurationName) => {
  const configs = activeConfigs[index] || [];

  return configs.find(
    config => config.configurationName === configurationName
  );
};
 

const updateRow = (index, field, value) => {
  setRows(prev =>
    prev.map((row, rowIndex) => {
      if (rowIndex !== index) return row;

      const updatedRow = {
        ...row,
        [field]: value
      };

if (field === "biometricModality") {
  updatedRow.biometricProviderConfiguration = "";

  if (value) {
    fetchBioExtractorConfigs(index, value.toLowerCase());
  } else {
    setActiveConfigs([]);
  }
}

      if (field === "biometricProviderConfiguration") {
        const selectedConfig = getSelectedConfig(index, value);
        updatedRow.bioextractorProviderName =
          selectedConfig?.bioextractorProviderName || "";
        updatedRow.bioextractorProviderVersion =
          selectedConfig?.bioextractorProviderVersion || "";
      }

      return updatedRow;
    })
  );
};

  const clearForm = () => {
    setRows([{ ...EMPTY_MAPPING_ROW }]);
    setErrorCode("");
    setErrorMsg("");
  };



  const clickOnCancel = () => {
    navigate("/partnermanagement/policies/policies-list");
  };

  const isFormValid = () => {
    if (!policyDetails.partnerId || !policyDetails.mappingKey) return false;

    return rows.every((row, index) => {
      if (!row.biometricModality || !row.biometricProviderConfiguration) return false;

      const selected = getSelectedConfig(index, row.biometricProviderConfiguration) || {};

      return Boolean((selected.bioextractorProviderName || "").trim());
    });
  };

  const clickOnSaveAndProceed = async () => {
    setIsSubmitClicked(true); 

    if (!isFormValid()) {
      setErrorMsg(t("mapBiometricExtractorProvider.validationMsg"));
      setIsSubmitClicked(false); 
      return;
    }

    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);

    try {
      const request = createRequest({
       extractors: rows.map((row, index) => {
  const selectedConfig = getSelectedConfig(index, row.biometricProviderConfiguration) || {};
  return {
    biometric: (row.biometricModality || "").toLowerCase(),
    attributeName: getAttributeName(row.biometricModality),
    extractor: {
      provider: selectedConfig.bioextractorProviderName,
      version: selectedConfig.bioextractorProviderVersion
    }
  };
})
      });

      const response = await HttpService.post(
        getPartnerManagerUrl(`/partners/${policyDetails.partnerId}/bioextractors/${policyDetails.policyId}`, process.env.NODE_ENV),
        request
      );
      
      if (response?.data?.response) {
      const validRows = rows.filter(
        row => row.biometricModality && row.biometricProviderConfiguration
      );
      if (validRows.length === 0) {
        setErrorMsg(t("mapBiometricExtractorProvider.validationMsg"));
        setIsSubmitClicked(false);
        return;
      }

      navigate("/partnermanagement/policies/map-credential-type", {
        state: {
          ...policyDetails,
          biometricMappings: validRows.map(row => ({
            biometricModality: row.biometricModality,
            biometricProviderConfiguration: row.biometricProviderConfiguration
          }))
        }
      });
      }

     handleServiceErrors(response?.data, setErrorCode, setErrorMsg);
     setIsSubmitClicked(false);
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      } else if (err.response?.status !== 401) {
        setErrorMsg(t("mapBiometricExtractorProvider.saveError"));
      }
      setIsSubmitClicked(false);
    } finally {
      setDataLoaded(true);
    }}

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id='map_bio_extractor_provider_error_msg'
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={() => setErrorMsg("")}
            />
          )}
          <div className="flex-col mt-5">
          <Title
            title='mapBiometricExtractorProvider.title'
            subTitle='requestPolicy.requestPolicy'
            backLink="/partnermanagement/policies/policies-list"
          />
            <p id='map_bio_extractor_provider_mandatory_mapping_msg' className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]">
              {t("requestPolicy.mandatoryMappingBanner")}
            </p>
            <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
              <div className="p-7">
                <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerId')}<span className="text-crimson-red mx-1">*</span></label>
                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                      <span className="w-full break-words text-wrap text-start">{policyDetails.partnerId || t('commons.partnersHelpText')}</span>
                    </button>
                  </div>
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                      <span className="w-full break-words text-wrap text-start">{policyDetails.partnerType || t('commons.partnersHelpText')}</span>
                    </button>
                  </div>
                </div>
                <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyGroup')}<span className="text-crimson-red mx-1">*</span></label>
                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                      <span className="w-full break-words text-wrap text-start">{policyDetails.policyGroupName || t('commons.partnersHelpText')}</span>
                    </button>
                  </div>
                  <div className="flex flex-col w-full max-[450px]:w-full">
                    <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.policyName')}<span className="text-crimson-red mx-1">*</span></label>
                    <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                      <span className="w-full break-words text-wrap text-start">{policyDetails.policyName || t('commons.partnersHelpText')}</span>
                    </button>
                  </div>
                </div>
                <div className="mt-5 border border-[#D5D8E3] rounded-md p-4">
                  <p className="text-sm font-semibold text-dark-blue mb-3">{t('mapBiometricExtractorProvider.mappingSectionTitle')}</p>
                  {rows.map((row, index) => {
                    const selectedConfig = getSelectedConfig(index, row.biometricProviderConfiguration) || {};
                    const styles = {
                          outerDiv: '!ml-0 !mb-0',
                          dropdownLabel: '!text-sm !mb-1',
                          dropdownButton: '!w-full min-h-10 !rounded-md !text-base !text-start',
                          selectionBox: '!top-10'
                        };
                    return (
                      <div key={`bio-mapping-row-${index}`} className="border border-[#E7E7E7] rounded-md p-4 mb-4">                          <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                          <div className="flex flex-col w-full ">
                            <div className="w-full">
                            <DropdownComponent
                              fieldName='biometricModality'
                              dropdownDataList={modalityDropdownData}
                              onDropDownChangeEvent={(fieldName, selectedValue) => updateRow(index, fieldName, selectedValue)}
                              fieldNameKey='mapBiometricExtractorProvider.biometricModality*'
                              placeHolderKey='mapBiometricExtractorProvider.selectBiometricModality'
                              selectedDropdownValue={row.biometricModality}
                              styleSet={styles}
                              id={`map_bio_extractor_provider_modality_${index + 1}`}
                            />
                          </div>
                          </div>
                          <div className="flex flex-col w-full">
                            <div className="w-full">
                            <DropdownWithSearchComponent
                              fieldName='biometricProviderConfiguration'
                              dropdownDataList={
                                getConfigDropdownByModality(index, row.biometricModality)
                              }
                              onDropDownChangeEvent={(fieldName, selectedValue) =>
                                updateRow(index, fieldName, selectedValue)
                              }
                              fieldNameKey='mapBiometricExtractorProvider.biometricProviderConfiguration*'
                              placeHolderKey='mapBiometricExtractorProvider.selectBiometricProviderConfiguration'
                              selectedDropdownValue={row.biometricProviderConfiguration}
                              searchKey='commons.search'
                              styleSet={styles}
                              id={`map_bio_extractor_provider_config_${index + 1}`}
                            />
                          </div>
                          </div>
                        </div>
                        <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                          <div className="flex flex-col w-full max-[450px]:w-full">
                            <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('bioExtractorConfig.providerName')}<span className="text-crimson-red mx-1">*</span></label>
                            <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                              <span className="w-full break-words text-wrap text-start">{selectedConfig?.bioextractorProviderName || t('mapBiometricExtractorProvider.autoPopulatedValue')}</span>
                            </button>
                          </div>
                          <div className="flex flex-col w-full max-[450px]:w-full">
                            <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('bioExtractorConfig.providerVersion')}<span className="text-crimson-red mx-1">*</span></label>
                            <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                              <span className="w-full break-words text-wrap text-start">{selectedConfig?.bioextractorProviderVersion || t('mapBiometricExtractorProvider.autoPopulatedValue')}</span>
                            </button>
                          </div>
                        </div>
                        <div className="flex w-full items-center mt-4">
                  {/* LEFT */}
                  <button
                    id={`map_bio_extractor_provider_add_more_btn_${index}`}
                    type='button'
                    className={`text-tory-blue text-sm font-semibold ${
                      rows.length >= 3 ? "opacity-50 cursor-not-allowed" : ""
                    }`}
                    disabled={rows.length >= 3}
                    onClick={() => {
                      if (rows.length < 3) {
                        setRows(prev => [...prev, { ...EMPTY_MAPPING_ROW }]);
                      }
                    }}
                  >
                    + {t('mapBiometricExtractorProvider.addMore')}
                  </button>                 
                
                        {rows.length > 1 && (
                          <div className="ml-auto">
                            <button
                              type="button"
                              className="flex items-center gap-1 text-[#1447B2] text-sm font-medium"
                              onClick={() => {
                                setRows(prev => prev.filter((_, i) => i !== index));

                                setActiveConfigs(prev => {
                                  const updated = {};

                                  Object.keys(prev).forEach(key => {
                                    const numKey = Number(key);

                                    if (numKey < index) {
                                      updated[numKey] = prev[numKey];
                                    } else if (numKey > index) {
                                      updated[numKey - 1] = prev[numKey]; 
                                    }
                                  });

                                  return updated;
                                });
                              }}
                            >
                              <svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth={2}>
                                <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M6 7h12M9 7V4h6v3M5 7h14l-1 14H6L5 7z" />
                                <path strokeLinecap="round" strokeLinejoin="round"
                                  d="M10 11l4 4M14 11l-4 4" />
                              </svg>
                              {t('mapBiometricExtractorProvider.delete')}
                            </button>
                          </div>                          
                        )}
                        </div>
                      </div>                      
                    ); 
                                       
                  })}
                                    
                </div>
              </div>
              <div className="border bg-medium-gray" />
              <div className="flex flex-row px-[3%] py-5 justify-between">
                <button id="map_bio_extractor_provider_clear_btn" onClick={clearForm} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                <div className="flex flex-row space-x-3 w-full md:w-auto justify-end">
                  <button id="map_bio_extractor_provider_cancel_btn" onClick={clickOnCancel} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                  <button id="map_bio_extractor_provider_submit_btn" disabled={!isFormValid()} onClick={clickOnSaveAndProceed} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border rounded-md text-sm font-semibold ${isFormValid() ? 'border-[#1447B2] bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.saveAndProceed')}</button>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
      <BlockerPrompt 
          blocker={blocker} 
          message={t("unsavedChangesPopup.message")} 
      />
    </div>
  );
}

export default MapBiometricExtractorProvider;