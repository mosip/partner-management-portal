import { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createRequest, getPartnerManagerUrl, handleServiceErrors, isLangRTL } from "../../../utils/AppUtils";
import { HttpService } from "../../../services/HttpService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import DropdownComponent from "../../common/fields/DropdownComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Confirmation from "../../common/Confirmation";

const EMPTY_ROW = { credentialType: "" };

const CREDENTIAL_TYPE_OPTIONS = ["auth", "qrcode", "euin", "reprint", "vercred", "opencrvs"];

function MapCredentialType() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { state } = useLocation();

  const userProfile = getUserProfile();
  const isLoginLanguageRTL = isLangRTL(userProfile?.locale || "en");

  const hasRequiredState = Boolean(state?.partnerId && state?.policyName);
  const selectedBioModalities = Array.isArray(state?.selectedBioModalities) ? state.selectedBioModalities : [];
  const selectedBioProviderConfigurations = Array.isArray(state?.selectedBioProviderConfigurations)
    ? state.selectedBioProviderConfigurations
    : [];

  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [rows, setRows] = useState([{ ...EMPTY_ROW }]);
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [requestPolicySuccess, setRequestPolicySuccess] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});

  const credentialTypeDropdownData = useMemo(
    () =>
      CREDENTIAL_TYPE_OPTIONS.map((value) => ({
        fieldCode: value,
        fieldValue: value,
      })),
    []
  );

  const hasUnsavedChanges = useMemo(
    () => rows.some((r) => Boolean((r.credentialType || "").trim())),
    [rows]
  );

  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    if (isSubmitClicked || requestPolicySuccess) return false;
    return hasUnsavedChanges && currentLocation.pathname !== nextLocation.pathname;
  });

  useEffect(() => {
    if (hasRequiredState) return;
    navigate("/partnermanagement/runtimeError");
  }, [hasRequiredState, navigate]);

  const clearForm = () => {
    setRows([{ ...EMPTY_ROW }]);
    setErrorCode("");
    setErrorMsg("");
  };

  const clickOnCancel = () => {
    navigate("/partnermanagement/policies/policies-list");
  };

  const updateRow = (index, fieldName, selectedValue) => {
    setRows((prev) => prev.map((row, i) => (i === index ? { ...row, [fieldName]: selectedValue } : row)));
  };

  const deleteRow = (index) => {
    setRows((prev) => prev.filter((_, i) => i !== index));
  };

  const isFormValid = () => {
    if (!hasRequiredState) return false;
    if (rows.length === 0) return false;
    return rows.every((r) => Boolean((r.credentialType || "").trim()));
  };

  const clickOnSubmit = async () => {
    setIsSubmitClicked(true);

    if (!isFormValid()) {
      setErrorMsg(t("mapCredentialType.validationMsg"));
      setIsSubmitClicked(false);
      return;
    }

    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);

    try {
      const credentialTypes = Array.from(new Set(rows.map((r) => String(r.credentialType || "").trim()).filter(Boolean)));
      const request = createRequest({});
      const buildUrl = (credentialType) =>
        getPartnerManagerUrl(
          `/partners/${state.partnerId}/credentialtype/${encodeURIComponent(credentialType)}/policies/${encodeURIComponent(
            state.policyName
          )}`,
          process.env.NODE_ENV
        );

      const tasks = credentialTypes.map((credentialType) => ({
        credentialType,
        promise: (async () => {
          const url = buildUrl(credentialType);
          const response = await HttpService.post(url, request, {
            headers: { "Content-Type": "application/json" },
          });
          const responseData = response?.data;
          if (responseData?.response === undefined) {
            const error = new Error("credentialTypeMappingFailed");
            error.responseData = responseData;
            throw error;
          }
          return true;
        })(),
      }));

      const results = await Promise.allSettled(tasks.map((t) => t.promise));

      const succeeded = tasks
        .filter((_, i) => results[i]?.status === "fulfilled")
        .map((t) => t.credentialType);
      const failed = tasks
        .filter((_, i) => results[i]?.status === "rejected")
        .map((t) => t.credentialType);

      if (failed.length > 0) {
        setErrorMsg(
          t("mapCredentialType.partialFailureMsg", {
            succeeded: succeeded.length,
            total: credentialTypes.length,
            failed: failed.join(", "),
          })
        );
        setIsSubmitClicked(false);
        setDataLoaded(true);
        return;
      }

      if (credentialTypes.length > 0) {
        const hasBioMappingContext =
          selectedBioModalities.length > 0 || selectedBioProviderConfigurations.length > 0;

        setConfirmationData({
          title: "mapCredentialType.title",
          backUrl: "/partnermanagement/policies/policies-list",
          header: hasBioMappingContext
            ? "mapCredentialType.successHeader"
            : "mapCredentialType.credentialOnlySuccessHeader",
          description: hasBioMappingContext
            ? "mapCredentialType.successMsgLine1"
            : "mapCredentialType.credentialOnlySuccessMsgLine1",
          description1: hasBioMappingContext
            ? "mapCredentialType.successMsgLine2"
            : "mapCredentialType.credentialOnlySuccessMsgLine2",
          subNavigation: "requestPolicy.policies",
        });
        setRequestPolicySuccess(true);
      }
    } catch (err) {
      if (err?.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      } else if (err?.response?.status !== 401) {
        setErrorMsg(t("mapCredentialType.saveError"));
      }
    } finally {
      setDataLoaded(true);
      setIsSubmitClicked(false);
    }
  };

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1",
    dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10",
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
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {blocker?.state === "blocked" && <BlockerPrompt blocker={blocker} />}
          {errorMsg && (
            <ErrorMessage
              id="map_credential_type_error_msg"
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={() => setErrorMsg("")}
            />
          )}

          {!requestPolicySuccess ? (
            <div className="flex-col mt-5">
              <Title title="mapCredentialType.title" subTitle="requestPolicy.requestPolicy" backLink="/partnermanagement/policies/policies-list" />

              <p
                id="map_credential_type_mandatory_mapping_msg"
                className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]"
              >
                {t("requestPolicy.mandatoryMappingBanner")}
              </p>

              <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md border border-[#2B66F6]">
                <div className="p-7">
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

                  {rows.map((row, index) => (
                    <div key={`credential-type-row-${index}`} className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                      <div className="flex flex-col w-full">
                        <DropdownComponent
                          fieldName="credentialType"
                          dropdownDataList={credentialTypeDropdownData}
                          onDropDownChangeEvent={(fieldName, selectedValue) => updateRow(index, fieldName, selectedValue)}
                          fieldNameKey="mapCredentialType.credentialType*"
                          placeHolderKey="mapCredentialType.selectCredentialType"
                          selectedDropdownValue={row.credentialType}
                          styleSet={styles}
                          id={`map_credential_type_${index + 1}`}
                        />
                      </div>
                      <div className="flex items-end justify-end">
                        {rows.length > 1 && (
                          <button
                            id={`map_credential_type_delete_${index + 1}`}
                            type="button"
                            onClick={() => deleteRow(index)}
                            className="h-10 px-4 border border-[#1447B2] rounded-md bg-white text-tory-blue text-sm font-semibold"
                          >
                            {t("mapCredentialType.delete")}
                          </button>
                        )}
                      </div>
                    </div>
                  ))}

                  <button
                    id="map_credential_type_add_more"
                    type="button"
                    onClick={() => setRows((prev) => [...prev, { ...EMPTY_ROW }])}
                    className="text-tory-blue font-semibold text-sm mt-2"
                  >
                    + {t("mapCredentialType.addMore")}
                  </button>

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
            </div>
          ) : (
            <Confirmation id="map_credential_type_confirmation" confirmationData={confirmationData} />
          )}
        </>
      )}
    </div>
  );
}

export default MapCredentialType;

