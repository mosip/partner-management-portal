import { useEffect, useMemo, useRef, useState } from "react";
import { useLocation, useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createRequest, getPartnerManagerUrl, isLangRTL } from "../../../utils/AppUtils";
import { HttpService } from "../../../services/HttpService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import DropdownComponent from "../../common/fields/DropdownComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Confirmation from "../../common/Confirmation";
import { getAppConfig } from "../../../services/ConfigService";



function hasMeaningfulBioMapping(item) {
  if (!item) return false;
  const extractor = item?.extractor ?? item?.mapping?.extractor ?? null;
  const provider =
    extractor?.provider ??
    item?.extractorProvider ??
    item?.provider ??
    item?.bioextractorProviderName ??
    item?.extractorProviderName ??
    "";
  const version =
    extractor?.version ??
    item?.extractorProviderVersion ??
    item?.version ??
    item?.bioextractorProviderVersion ??
    item?.extractorProviderVersion ??
    "";
  return Boolean(String(provider || "").trim()) || Boolean(String(version || "").trim());
}


function resolveBioConfigurationLabel(item) {
  const configuration =
    item?.bioExtractorConfigurationName ??
    item?.bioExtractorConfigName ??
    item?.configName ??
    item?.bio_extractor_configuration_name ??
    item?.configurationName ??
    item?.bioExtractorConfigurationId ??
    item?.bio_extractor_configuration_id ??
    item?.attributeName ??
    "";
  const trimmed = configuration === null || configuration === undefined ? "" : String(configuration).trim();
  return trimmed || "-";
}


function extractBioDisplayFromResponse(responsePayload) {
  const list = Array.isArray(responsePayload)
    ? responsePayload
    : (responsePayload?.extractors ??
        responsePayload?.data?.bioExtractors ??
        responsePayload?.data?.bioextractors ??
        responsePayload?.data ??
        responsePayload?.content ??
        responsePayload?.bioExtractors ??
        responsePayload?.bioextractors ??
        responsePayload?.response?.bioExtractors ??
        responsePayload?.response?.bioextractors ??
        responsePayload?.extractorList ??
        []);
  if (!Array.isArray(list)) return { modalities: [], configs: [] };

  const modalities = [];
  const configs = [];

  for (const item of list) {
    const rawModality =
      item?.bioModality ??
      item?.biometricModality ??
      item?.modality ??
      item?.bio_modality ??
      item?.biometric ??
      "";
    let modality = String(rawModality || "").trim().toUpperCase();
    if (modality === "FINGERPRINT") modality = "FINGER";

    const configLabel = resolveBioConfigurationLabel(item);

    const hasRow =
      hasMeaningfulBioMapping(item) ||
      Boolean(modality) ||
      (configLabel && configLabel !== "-");
    if (!hasRow) continue;

    modalities.push(modality || String(rawModality || "").trim() || "-");
    configs.push(configLabel);
  }

  return { modalities, configs };
}

function MapCredentialType() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { state } = useLocation();

  const userProfile = getUserProfile();
  const isLoginLanguageRTL = isLangRTL(userProfile?.locale || "en");

  const policyIdForApi = state?.policyId ?? state?.policy_id ?? "";
  const requestIdForBioApi =
    state?.mappingKey ??
    state?.mappingkey ??
    state?.partnerPolicyRequestId ??
    state?.requestId ??
    "";

  const hasRequiredState = Boolean(state?.partnerId && state?.policyName && policyIdForApi && requestIdForBioApi);

  const [selectedBioModalities, setSelectedBioModalities] = useState(() =>
    Array.isArray(state?.selectedBioModalities) ? state.selectedBioModalities : []
  );
  const [selectedBioProviderConfigurations, setSelectedBioProviderConfigurations] = useState(() =>
    Array.isArray(state?.selectedBioProviderConfigurations) ? state.selectedBioProviderConfigurations : []
  );

  const needsBioFetch = useMemo(
    () =>
      !(Array.isArray(state?.selectedBioModalities) && state.selectedBioModalities.length > 0) &&
      Boolean(requestIdForBioApi),
    [requestIdForBioApi, state?.selectedBioModalities]
  );

  const [bioMetaLoaded, setBioMetaLoaded] = useState(() => !needsBioFetch);

  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [credentialType, setCredentialType] = useState("");
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [requestPolicySuccess, setRequestPolicySuccess] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});
  const [allowedCredentialTypes, setAllowedCredentialTypes] = useState([]);
  const isSubmittingRef = useRef(false);


  const credentialTypeDropdownData = useMemo(
    () => [
      { fieldCode: t("mapCredentialType.selectCredentialType"), fieldValue: "" },
      ...allowedCredentialTypes.map((value) => ({
        fieldCode: value,
        fieldValue: value,
      })),
    ],
    [allowedCredentialTypes, t]
  );
  useEffect(() => {
    let cancelled = false;

    (async () => {
      try {
        const appConfig = await getAppConfig();
        if (cancelled) return;

      const credentialTypes =
        appConfig?.allowedCredentialTypes ??
        appConfig?.["pmp.allowed.credential.types"] ??
        "";

      const normalizedCredentialTypes =
        (Array.isArray(credentialTypes)
          ? credentialTypes
          : String(credentialTypes).split(","))
          .map(value => String(value || "").trim())
          .filter(Boolean);

        setAllowedCredentialTypes(normalizedCredentialTypes);
      } catch (error) {
        if (cancelled) return;
        console.error("Error fetching allowed credential types:", error);
        setAllowedCredentialTypes([]);
        setErrorMsg(t("mapCredentialType.saveError"));
      }
    })();

    return () => {
      cancelled = true;
    };
  }, [t]);

  useEffect(() => {
    if (!needsBioFetch) return;

    let cancelled = false;
    (async () => {
      try {
        const url = getPartnerManagerUrl(
          `/partner-policy-requests/${requestIdForBioApi}/bio-extractors-request`,
          process.env.NODE_ENV
        );
        const res = await HttpService.get(url);
        if (cancelled) return;
        if (res?.data?.response) {
          const { modalities, configs } = extractBioDisplayFromResponse(res.data.response);
          setSelectedBioModalities(modalities);
          setSelectedBioProviderConfigurations(configs);
        }
      } catch {

      } finally {
        if (!cancelled) setBioMetaLoaded(true);
      }
    })();

    return () => {
      cancelled = true;
    };
  }, [needsBioFetch, requestIdForBioApi]);

  const hasUnsavedChanges = useMemo(() => Boolean((credentialType || "").trim()), [credentialType]);

 const blocker = null;

  useEffect(() => {
    if (hasRequiredState) return;
    navigate("/partnermanagement/runtimeError");
  }, [hasRequiredState, navigate]);

  const clearForm = () => {
    setCredentialType("");
    setErrorCode("");
    setErrorMsg("");
  };

  const clickOnCancel = () => {
    navigate("/partnermanagement/policies/policies-list");
  };

  const updateCredentialType = (fieldName, selectedValue) => {
    setCredentialType(selectedValue ?? "");
  };

  const isFormValid = () => {
    if (!hasRequiredState) return false;
    return Boolean(String(credentialType || "").trim());
  };

  const clickOnSubmit = async () => {
    if (isSubmittingRef.current) return;
    isSubmittingRef.current = true;
    setIsSubmitClicked(true);

    if (!isFormValid()) {
      setErrorMsg(t("mapCredentialType.validationMsg"));
      setIsSubmitClicked(false);
      isSubmittingRef.current = false;
      return;
    }

    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);

    try {
      const ct = String(credentialType || "").trim().toLowerCase();
      const request = createRequest({
        partnerPolicyRequestId: requestIdForBioApi,
        credentialType: ct,
       },
        "mosip.pms.partners.credentialtypes.request.post"
    );
        const timestamp = new Date().toISOString();
    request.requestTime = timestamp;
      const url = getPartnerManagerUrl(
        `/partners/${state.partnerId}/policies/${policyIdForApi}/credential-types-request`,
        process.env.NODE_ENV
      );

      let responseData;
      let httpStatus;
      try {
        const response = await HttpService.post(url, request, {
          headers: { "Content-Type": "application/json" },
        });
        responseData = response?.data;
        httpStatus = response?.status;
      } catch (err) {
        responseData = err?.response?.data;
        httpStatus = err?.response?.status;
        if (!responseData) throw err;
      }

      const is2xx = typeof httpStatus === "number" && httpStatus >= 200 && httpStatus < 300;

      // Don't let non-2xx responses fall through to success confirmation,
      // even if the payload isn't in our usual { errors: [...] } envelope.
      if (!is2xx) {
        if (responseData?.errors?.length) {
          setErrorCode(responseData?.errors?.[0]?.errorCode || "");
          setErrorMsg(responseData?.errors?.[0]?.message || t("mapCredentialType.saveError"));
        } else {
          setErrorCode("");
          setErrorMsg(t("mapCredentialType.saveError"));
        }
        setIsSubmitClicked(false);
        setDataLoaded(true);
        isSubmittingRef.current = false;
        return;
      }

      if (responseData?.errors?.length) {
        setErrorCode(responseData?.errors?.[0]?.errorCode || "");
        setErrorMsg(responseData?.errors?.[0]?.message || t("mapCredentialType.saveError"));
        setIsSubmitClicked(false);
        setDataLoaded(true);
        isSubmittingRef.current = false;
        return;
      }

      setConfirmationData({
        title: "mapCredentialType.title",
        backUrl: "/partnermanagement/policies/policies-list",
        header: "mapCredentialType.successHeader",
        description: "mapCredentialType.successMsgLine1",
        description1: "mapCredentialType.successMsgLine2",
        subNavigation: "requestPolicy.policies",
      });
      setRequestPolicySuccess(true);
    } catch (err) {
      if (err?.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      } else if (err?.response?.status !== 401) {
        setErrorMsg(t("mapCredentialType.saveError"));
      }
    } finally {
      setDataLoaded(true);
      setIsSubmitClicked(false);
      isSubmittingRef.current = false;
    }
  };

  const credentialTypeDropdownStyles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1 !block !font-semibold !text-[#3D4468]",
    dropdownButton: "!w-full !min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10",
    optionsList: "!max-h-28",
  };

  const getModalityLabel = (modality) => {
    const upper = String(modality || "").toUpperCase();
    if (upper === "FACE") return t("bioExtractorConfig.face");
    if (upper === "IRIS") return t("bioExtractorConfig.iris");
    if (upper === "FINGER" || upper === "FINGERPRINT") return t("bioExtractorConfig.finger");
    return upper || "-";
  };

  const modalityCsv = selectedBioModalities.length > 0 ? selectedBioModalities.map(getModalityLabel).join(", ") : "";
  const providerCfgCsv = selectedBioProviderConfigurations.length > 0 ? selectedBioProviderConfigurations.join(", ") : "";

  if (!hasRequiredState) return null;

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-auto relative font-inter`}>
      {(!dataLoaded || !bioMetaLoaded) && <LoadingIcon />}
      {dataLoaded && bioMetaLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id="map_credential_type_error_msg"
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={() => setErrorMsg("")}
            />
          )}

          <div className="flex-col mt-5">
            <Title
              title={requestPolicySuccess ? "requestPolicy.requestPolicy" : "mapCredentialType.title"}
              subTitle={requestPolicySuccess ? "requestPolicy.policies" : "requestPolicy.requestPolicy"}
              backLink="/partnermanagement/policies/policies-list"
            />

            {!requestPolicySuccess ? (
              <>
              <p
                id="map_credential_type_mandatory_mapping_msg"
                className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]"
              >
                {t("requestPolicy.mandatoryMappingBanner")}
              </p>

              <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md overflow-visible">
                <div className="p-7 overflow-visible">
                  <p className="text-base text-[#3D4468]">
                    {t("requestPolicy.mandatoryFieldsMsg1")} <span className="text-crimson-red">*</span> {t("requestPolicy.mandatoryFieldsMsg2")}
                  </p>

                  <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                    <div className="flex flex-col w-full">
                      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                        {t("requestPolicy.partnerId")}
                        <span className="text-crimson-red mx-1">*</span>
                      </label>
                      <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                        <span className="w-full break-words text-wrap text-start">{state.partnerId || "-"}</span>
                      </button>
                    </div>
                    <div className="flex flex-col w-full">
                      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                        {t("requestPolicy.partnerType")}
                        <span className="text-crimson-red mx-1">*</span>
                      </label>
                      <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                        <span className="w-full break-words text-wrap text-start">{state.partnerType || "-"}</span>
                      </button>
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                    <div className="flex flex-col w-full">
                      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                        {t("requestPolicy.policyGroup")}
                        <span className="text-crimson-red mx-1">*</span>
                      </label>
                      <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                        <span className="w-full break-words text-wrap text-start">{state.policyGroupName || "-"}</span>
                      </button>
                    </div>
                    <div className="flex flex-col w-full">
                      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                        {t("requestPolicy.policyName")}
                        <span className="text-crimson-red mx-1">*</span>
                      </label>
                      <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                        <span className="w-full break-words text-wrap text-start">{state.policyName || "-"}</span>
                      </button>
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-4 my-4 max-[450px]:grid-cols-1">
                    <div className="flex flex-col w-full">
                      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                        {t("mapBiometricExtractorProvider.biometricModality")}
                        <span className="text-crimson-red mx-1">*</span>
                      </label>
                      <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                        <span className="w-full break-words text-wrap text-start">
                          {modalityCsv || t("mapBiometricExtractorProvider.autoPopulatedValue")}
                        </span>
                      </button>
                    </div>
                    <div className="flex flex-col w-full">
                      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                        {t("mapBiometricExtractorProvider.biometricProviderConfiguration")}
                        <span className="text-crimson-red mx-1">*</span>
                      </label>
                      <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                        <span className="w-full break-words text-wrap text-start">
                          {providerCfgCsv || t("mapBiometricExtractorProvider.autoPopulatedValue")}
                        </span>
                      </button>
                    </div>
                  </div>

                  <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                    <div className="flex flex-col w-full">
                      <DropdownComponent
                        fieldName="credentialType"
                        dropdownDataList={credentialTypeDropdownData}
                        onDropDownChangeEvent={updateCredentialType}
                        fieldNameKey="mapCredentialType.credentialType*"
                        placeHolderKey="mapCredentialType.selectCredentialType"
                        selectedDropdownValue={credentialType}
                        styleSet={credentialTypeDropdownStyles}
                        isPlaceHolderPresent={true}
                        id="map_credential_type_1"
                      />
                    </div>
                    <div className="flex items-end justify-end" aria-hidden="true" />
                  </div>

                  <div className="flex flex-col md:flex-row justify-between mt-8 border-t border-[#D5D8E3] pt-5">
                    <div className="flex flex-wrap justify-start">
                      <button
                        id="map_credential_type_clear_btn"
                        onClick={clearForm}
                        type="button"
                        className={`w-40 h-10 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                      >
                        {t("requestPolicy.clearForm")}
                      </button>
                    </div>

                    <div className="flex flex-row space-x-3 w-full md:w-auto justify-end mt-4 md:mt-0">
                      <button
                        id="map_credential_type_cancel_btn"
                        onClick={clickOnCancel}
                        type="button"
                        className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                      >
                        {t("requestPolicy.cancel")}
                      </button>
                      <button
                        id="map_credential_type_submit_btn"
                        disabled={!isFormValid()}
                        onClick={clickOnSubmit}
                        type="button"
                        className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border rounded-md text-sm font-semibold ${
                          isFormValid()
                            ? "border-[#1447B2] bg-tory-blue text-white"
                            : "border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed"
                        }`}
                      >
                        {t("requestPolicy.submit")}
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              </>
            ) : (
              <Confirmation id="map_credential_type_confirmation" confirmationData={confirmationData} />
            )}
          </div>
        </>
      )}
    </div>
  );
}

export default MapCredentialType;
