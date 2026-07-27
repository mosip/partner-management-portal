import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { HttpService } from "../../../services/HttpService";
import {
  formatDate,
  getPartnerManagerUrl,
  isLangRTL,
} from "../../../utils/AppUtils";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import Title from "../../common/Title";
import somethingWentWrongIcon from "../../../svg/something_went_wrong_icon.svg";

const LIST_ROUTE = "/partnermanagement/admin/biometric-provider-configuration-list";

function ViewBioExtractorConfig() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
  const [unexpectedError, setUnexpectedError] = useState(false);
  const [configDetails, setConfigDetails] = useState({});
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);

  const moveToConfigList = () => {
    navigate(LIST_ROUTE);
  };

  const getModalityLabel = (modality) => {
    if (!modality) return "-";
    const upperModality = String(modality).toUpperCase();
    if (upperModality === "FACE") return t("bioExtractorConfig.face");
    if (upperModality === "IRIS") return t("bioExtractorConfig.iris");
    if (upperModality === "FINGER") return t("bioExtractorConfig.finger");
    return upperModality;
  };

  const normalizeConfigData = (item) => ({
    bioExtractorConfigurationId:
      item?.bioExtractorConfigurationId || item?.bio_extractor_configuration_id || item?.id || null,
    configName: item?.configName || "-",
    bioextractorProviderName: item?.bioextractorProviderName || "-",
    bioextractorProviderVersion: item?.bioextractorProviderVersion || "-",
    bioModality: item?.bioModality || "-",
    credentialDataFormat: item?.credentialDataFormat || item?.credential_data_format || "",
    attributeName: item?.attributeName || item?.attribute_name || "",
    createdDateTime: item?.createdDateTime || item?.createdDate || null,
  });

  const getCredentialDataFormatLabel = (value) => {
    if (!value) return "-";
    if (String(value).toLowerCase() === "rawdata") return t("bioExtractorConfig.rawData");
    if (String(value).toLowerCase() === "templatedata") return t("bioExtractorConfig.templateData");
    return value;
  };

  useEffect(() => {
    const selectedData = sessionStorage.getItem("selectedBioExtractorConfig");
    if (!selectedData) {
      setUnexpectedError(true);
      setDataLoaded(true);
      return;
    }

    let selectedConfig = null;
    try {
      selectedConfig = JSON.parse(selectedData);
      setConfigDetails(normalizeConfigData(selectedConfig));
    } catch (err) {
      setUnexpectedError(true);
      setDataLoaded(true);
      return;
    }

    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const configurationId = normalizeConfigData(selectedConfig).bioExtractorConfigurationId;

        if (!configurationId) {
          setDataLoaded(true);
          return;
        }

        const url = getPartnerManagerUrl(
          `/bio-extractor-configurations/${configurationId}`,
          process.env.NODE_ENV
        );
        const response = await HttpService.get(url);

        if (response?.data?.response) {
          setConfigDetails(normalizeConfigData(response.data.response));
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
        }
      } finally {
        setDataLoaded(true);
      }
    };

    fetchData();
  }, []);

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const styles = {
    loadingDiv: "!py-[20%]",
  };

  return (
    <div
      className={`w-full p-4 bg-anti-flash-white h-full font-inter break-words max-450:text-sm mb-[2%] ${
        isLoginLanguageRTL ? "mr-24 ml-1" : "ml-24 mr-1"
      } overflow-x-scroll`}
    >
      {!dataLoaded && <LoadingIcon styleSet={styles} />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id="view_bio_extractor_config_error_msg"
              errorCode=""
              errorMessage={errorMsg}
              clickOnCancel={cancelErrorMsg}
            />
          )}
          <div className="flex-col mt-5 bg-anti-flash-white h-full font-inter break-words max-450:text-sm mb-[2%]">
            <div className="flex justify-between mb-3">
              <Title
                title="bioExtractorConfig.viewBioExtractorProviderConfig"
                subTitle="dashboard.biometricProviderConfiguration"
                backLink={LIST_ROUTE}
              />
            </div>
          </div>

          {unexpectedError && (
            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
              <div className="flex items-center justify-center p-24">
                <div className="flex flex-col justify-center items-center">
                  <img className="max-w-60 min-w-52 my-2" src={somethingWentWrongIcon} alt="" />
                  <p
                    id="view_bio_extractor_config_unexpected_error"
                    className="text-base font-semibold text-[#6F6E6E] py-4"
                  >
                    {t("commons.unexpectedError")}
                  </p>
                  <button
                    onClick={moveToConfigList}
                    type="button"
                    id="view_bio_extractor_config_go_back_btn"
                    className="w-32 h-10 flex items-center justify-center font-semibold rounded-md text-sm mx-8 py-3 bg-tory-blue text-white"
                  >
                    {t("commons.goBack")}
                  </button>
                </div>
              </div>
            </div>
          )}

          {!unexpectedError && (
            <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg font-inter">
              <div className="flex flex-wrap justify-between px-7 pt-3 border-b max-450:flex-col">
                <div className="flex flex-col flex-wrap max-w-full">
                  <p
                    id="view_bio_extractor_config_name_sub_title"
                    className="text-lg text-dark-blue mb-2 break-words whitespace-normal max-w-full"
                  >
                    <span className="font-semibold">{configDetails.configName || "-"}</span>
                  </p>
                  <div className="flex flex-wrap items-center justify-start mb-2 max-[400px]:flex-col max-[400px]:items-start">
                    <div
                      id="view_bio_extractor_config_created_on"
                      className="font-semibold text-sm text-dark-blue"
                    >
                      {`${t("viewOidcClientDetails.createdOn")} ${formatDate(
                        configDetails.createdDateTime,
                        "date"
                      )}`}
                    </div>
                    <div className="mx-1 text-gray-300">|</div>
                    <div
                      id="view_bio_extractor_config_created_time"
                      className="font-semibold text-sm text-dark-blue"
                    >
                      {formatDate(configDetails.createdDateTime, "time")}
                    </div>
                  </div>
                </div>
              </div>

              <div className={`${isLoginLanguageRTL ? "pr-8 ml-8" : "pl-8 mr-8"} pt-5 mb-2`}>
                <div className="flex flex-wrap py-1 max-450:flex-col">
                  <div
                    className={`w-[49%] max-[600px]:w-[100%] mb-3 ${
                      isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"
                    }`}
                  >
                    <p className="font-[600] text-suva-gray text-sm">
                      {t("bioExtractorConfig.providerName")}
                    </p>
                    <p id="view_bio_extractor_provider_name" className="font-[600] text-vulcan text-base">
                      {configDetails.bioextractorProviderName || "-"}
                    </p>
                  </div>

                  <div
                    className={`w-[49%] max-[600px]:w-[100%] mb-3 ${
                      isLoginLanguageRTL ? "mr-[1%]" : "ml-[1%]"
                    }`}
                  >
                    <p className="font-[600] text-suva-gray text-sm">
                      {t("bioExtractorConfig.providerVersion")}
                    </p>
                    <p id="view_bio_extractor_provider_version" className="font-[600] text-vulcan text-base">
                      {configDetails.bioextractorProviderVersion || "-"}
                    </p>
                  </div>
                </div>

                <div className="flex flex-wrap py-1 max-450:flex-col">
                  <div className="mb-3 max-[600px]:w-[100%] w-[49%]">
                    <p className="font-[600] text-suva-gray text-sm">
                      {t("bioExtractorConfig.biometricModality")}
                    </p>
                    <p id="view_bio_extractor_modality" className="font-[600] text-vulcan text-base">
                      {getModalityLabel(configDetails.bioModality)}
                    </p>
                  </div>
                </div>

                <div className="flex flex-wrap py-1 max-450:flex-col">
                  <div
                    className={`w-[49%] max-[600px]:w-[100%] mb-3 ${
                      isLoginLanguageRTL ? "ml-[1%]" : "mr-[1%]"
                    }`}
                  >
                    <p className="font-[600] text-suva-gray text-sm">
                      {t("bioExtractorConfig.credentialDataFormat")}
                    </p>
                    <p id="view_bio_extractor_credential_data_format" className="font-[600] text-vulcan text-base">
                      {getCredentialDataFormatLabel(configDetails.credentialDataFormat)}
                    </p>
                  </div>

                  <div
                    className={`w-[49%] max-[600px]:w-[100%] mb-3 ${
                      isLoginLanguageRTL ? "mr-[1%]" : "ml-[1%]"
                    }`}
                  >
                    <p className="font-[600] text-suva-gray text-sm">
                      {t("bioExtractorConfig.attributeName")}
                    </p>
                    <p id="view_bio_extractor_attribute_name" className="font-[600] text-vulcan text-base">
                      {configDetails.attributeName || "-"}
                    </p>
                  </div>
                </div>
              </div>
              <hr className="h-px w-full bg-gray-200 border-0" />
              <div className={`flex justify-end py-8 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
                <button
                  id="view_bio_extractor_back_btn"
                  onClick={moveToConfigList}
                  className="h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center"
                >
                  {t("commons.goBack")}
                </button>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default ViewBioExtractorConfig;
