import { useEffect, useMemo, useState } from "react";
import { useBlocker, useLocation, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { createRequest, getPartnerManagerUrl, handleServiceErrors, isLangRTL } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import { HttpService } from "../../../services/HttpService";
import Title from "../../common/Title";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import DropdownComponent from "../../common/fields/DropdownComponent";
import BlockerPrompt from "../../common/BlockerPrompt";
import Confirmation from "../../common/Confirmation";

const EMPTY_MAPPING_ROW = {
  credentialType: ""
};

function MapCredentialType() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { state } = useLocation();
  const userProfile = getUserProfile();
  const isLoginLanguageRTL = isLangRTL(userProfile?.locale || "en");
  const hasRequiredState = Boolean(state?.partnerId && state?.policyName && state?.mappingKey);

  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [rows, setRows] = useState([{ ...EMPTY_MAPPING_ROW }]);
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [showConfirmation, setShowConfirmation] = useState(false);

  const policyDetails = {
    partnerId: state?.partnerId || "",
    partnerType: state?.partnerType || "",
    policyGroupName: state?.policyGroupName || "",
    policyName: state?.policyName || "",
    policyId: state?.policyId || "",
    mappingKey: state?.mappingKey || "",
    partnerComment: state?.partnerComment || "",
    biometricMappings: state?.biometricMappings || []
  };
  const selectedBiometricModalities = policyDetails.biometricMappings
  .map((item) => item.biometricModality)
  .filter(Boolean)
  .join(", ");

const selectedBiometricProviderConfigurations = policyDetails.biometricMappings
  .map((item) => item.biometricProviderConfiguration)
  .filter(Boolean)
  .join(", ");

  const credentialTypeDropdownData = useMemo(
    () => [
      "qrcode",
      "euin",
      "reprint",
      "auth",
      "eUIN_with_faceQR",
      "eUIN_with_QR",
      "PDFCard",
      "vercred"
    ].map((credentialType) => ({
      fieldCode: credentialType,
      fieldValue: credentialType
    })),
    []
  );

  const shouldBlockNavigation = () => {
    return rows.some((row) => row.credentialType.toUpperCase());
  };

  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    if (isSubmitClicked || showConfirmation) {
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

  if (!hasRequiredState) {
    return null;
  }

  const isFormValid = () => {
    return Boolean(rows[0]?.credentialType);
  };

  const updateRow = (index, field, value) => {
    setRows((prevRows) =>
      prevRows.map((row, rowIndex) =>
        rowIndex === index
          ? {
              ...row,
              [field]: value
            }
          : row
      )
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

  const clickOnSubmit = async () => {
    setIsSubmitClicked(true);

    if (!isFormValid()) {
      setErrorMsg(t("mapCredentialType.validationMsg"));
      setIsSubmitClicked(false);
      return;
    }

    setDataLoaded(false);
    setErrorCode("");
    setErrorMsg("");

    try {
      const payload = createRequest({
        policyMappingRequestId: policyDetails.mappingKey
      });

        const row = rows[0]; // take only first

        await HttpService.post(
        getPartnerManagerUrl(
            `/partners/${policyDetails.partnerId}/credentialtype/${encodeURIComponent(row.credentialType.toLowerCase())}/policies/${encodeURIComponent(policyDetails.policyName)}`,
            process.env.NODE_ENV
        ),
        payload
        );

      setShowConfirmation(true);
    } catch (err) {
      handleServiceErrors(err?.response?.data, setErrorCode, setErrorMsg);
      if (!err?.response?.data?.errors) {
        setErrorMsg(t("mapCredentialType.saveError"));
      }
    } finally {
      setDataLoaded(true);
      setIsSubmitClicked(false);
    }
  };

  const confirmationData = {
    title: "requestPolicy.requestPolicy",
    backUrl: "/partnermanagement/policies/policies-list",
    header: "mapCredentialType.successHeader",
    description: "mapCredentialType.successDescription",
    subNavigation: "requestPolicy.policies"
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll relative font-inter`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id='map_credential_type_error_msg'
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={() => setErrorMsg("")}
            />
          )}

       
            <Title
              title='mapCredentialType.title'
              subTitle='requestPolicy.requestPolicy'
              backLink='/partnermanagement/policies/policies-list'
            />
            {!showConfirmation && (
              <>
                <p id='map_credential_type_mandatory_mapping_msg' className="mt-3 rounded-md border border-[#F7D18D] bg-[#FFF8EA] px-3 py-2 text-sm text-[#684B00]">
                  {t("requestPolicy.mandatoryMappingBanner")}
                </p>

                <div className="w-[100%] bg-snow-white mt-[1%] rounded-lg shadow-md">
                  <div className="p-7">
                    <p className="text-base text-[#3D4468]">{t("requestPolicy.mandatoryFieldsMsg1")} <span className="text-crimson-red">*</span> {t("requestPolicy.mandatoryFieldsMsg2")}</p>

                    <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                      <div className="flex flex-col w-full max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t("requestPolicy.partnerId")}<span className="text-crimson-red mx-1">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                          <span className="w-full break-words text-wrap text-start">{policyDetails.partnerId}</span>
                        </button>
                      </div>

                      <div className="flex flex-col w-full max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t("requestPolicy.partnerType")}<span className="text-crimson-red mx-1">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                          <span className="w-full break-words text-wrap text-start">{policyDetails.partnerType}</span>
                        </button>
                      </div>
                    </div>

                    <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                      <div className="flex flex-col w-full max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t("requestPolicy.policyGroup")}<span className="text-crimson-red mx-1">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                          <span className="w-full break-words text-wrap text-start">{policyDetails.policyGroupName}</span>
                        </button>
                      </div>

                      <div className="flex flex-col w-full max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t("requestPolicy.policyName")}<span className="text-crimson-red mx-1">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight overflow-x-auto whitespace-normal no-scrollbar" type="button">
                          <span className="w-full break-words text-wrap text-start">{policyDetails.policyName}</span>
                        </button>
                      </div>
                    </div>

                    <div className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                        {/* Biometric Modality */}
                        <div className="flex flex-col w-full">
                            <label className="block text-dark-blue text-sm font-semibold mb-1">
                            {t("requestPolicy.biometricModality")}
                            <span className="text-crimson-red mx-1">*</span>
                            </label>
                            <button
                            disabled
                            className="flex items-center justify-between w-full px-2 py-2 border border-[#C1C1C1] rounded-md bg-platinum-gray text-dark-blue"
                            >
                            <span className="w-full text-start">
                                {selectedBiometricModalities  || t("requestPolicy.autoPopulatedValue")}
                            </span>
                            </button>
                        </div>

                        {/* Biometric Provider */}
                        <div className="flex flex-col w-full">
                            <label className="block text-dark-blue text-sm font-semibold mb-1">
                            {t("requestPolicy.biometricProviderConfiguration")}
                            <span className="text-crimson-red mx-1">*</span>
                            </label>
                            <button
                            disabled
                            className="flex items-center justify-between w-full px-2 py-2 border border-[#C1C1C1] rounded-md bg-platinum-gray text-dark-blue"
                            >
                            <span className="w-full text-start">
                                {selectedBiometricProviderConfigurations || t("requestPolicy.autoPopulatedValue")}
                            </span>
                            </button>
                        </div>

                        </div>


                    {rows.map((row, index) => {
                      const styleSet = {
                        outerDiv: "!ml-0 !mb-3 mt-4",
                        dropdownLabel: "!text-sm !mb-1",
                        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
                        selectionBox: "!top-10"
                      };

                      return (
                        <div key={`credential-type-row-${index}`} className="grid grid-cols-2 gap-4 my-2 max-[450px]:grid-cols-1">
                          <DropdownComponent
                            fieldName='credentialType'
                            dropdownDataList={credentialTypeDropdownData}
                            onDropDownChangeEvent={(fieldName, selectedValue) => updateRow(index, fieldName, selectedValue)}
                            fieldNameKey='mapCredentialType.credentialType*'
                            placeHolderKey='mapCredentialType.selectCredentialType'
                            selectedDropdownValue={row.credentialType}
                            styleSet={styleSet}
                            id={`map_credential_type_${index + 1}`}
                          />

                          <div className="flex w-full items-center mt-4">
                            {rows.length > 1 && (
                              <div className="ml-auto">
                                <button
                                  type='button'
                                  className='flex items-center gap-1 text-[#1447B2] text-sm font-medium'
                                  onClick={() => setRows((prevRows) => prevRows.filter((_, rowIndex) => rowIndex !== index))}
                                >
                                  <svg xmlns='http://www.w3.org/2000/svg' className='w-4 h-4' fill='none' viewBox='0 0 24 24' stroke='currentColor' strokeWidth={2}>
                                    <path strokeLinecap='round' strokeLinejoin='round' d='M6 7h12M9 7V4h6v3M5 7h14l-1 14H6L5 7z' />
                                    <path strokeLinecap='round' strokeLinejoin='round' d='M10 11l4 4M14 11l-4 4' />
                                  </svg>
                                  {t("mapCredentialType.delete")}
                                </button>
                              </div>
                            )}
                          </div>
                        </div>
                      );
                    })}
                  </div>

                  <div className='border bg-medium-gray' />

                  <div className='flex flex-row px-[3%] py-5 justify-between'>
                    <button id='map_credential_type_clear_btn' onClick={clearForm} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t("requestPolicy.clearForm")}</button>
                    <div className='flex flex-row space-x-3 w-full md:w-auto justify-end'>
                      <button id='map_credential_type_cancel_btn' onClick={clickOnCancel} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t("requestPolicy.cancel")}</button>
                      <button id='map_credential_type_submit_btn' disabled={!isFormValid()} onClick={clickOnSubmit} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border rounded-md text-sm font-semibold ${isFormValid() ? "border-[#1447B2] bg-tory-blue text-white" : "border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed"}`}>{t("requestPolicy.submit")}</button>
                    </div>
                  </div>
                </div>
              </>
            )}

            {showConfirmation && (
              <Confirmation
                id='map_credential_type_confirmation'
                confirmationData={confirmationData}
              />
            )}
        </>
      )}
      <BlockerPrompt blocker={blocker} message={t("unsavedChangesPopup.message")} />
    </div>
  );
}

export default MapCredentialType;
