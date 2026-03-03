import { useEffect, useRef, useState } from "react";
import { useBlocker, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import {
  createDropdownData,
  getPartnerManagerUrl,
  handleServiceErrors,
  isLangRTL,
  trimAndReplace,
  validateInputRegex,
} from "../../../utils/AppUtils";
import { HttpService } from "../../../services/HttpService";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent";
import CopyIdPopUp from "../../common/CopyIdPopup";
import Confirmation from "../../common/Confirmation";
import BlockerPrompt from "../../common/BlockerPrompt";

function GenerateManualAdjudicationApiKey() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [partnerData, setPartnerData] = useState([]);
  const [policyList, setPolicyList] = useState([]);
  const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
  const [policiesDropdownData, setPoliciesDropdownData] = useState([]);
  const [partnerId, setPartnerId] = useState("");
  const [policyName, setPolicyName] = useState("");
  const [policyId, setPolicyId] = useState("");
  const [policyGroupName, setPolicyGroupName] = useState("");
  const [apiKeyName, setApiKeyName] = useState("");
  const [inputError, setInputError] = useState("");
  const [showPopup, setShowPopup] = useState(false);
  const [apiKeyId, setApiKeyId] = useState("");
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [generateApiKeySuccess, setGenerateApiKeySuccess] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});
  const hasFetchedPartners = useRef(false);

  const partnerTypeLabel = t("manualAdjudicationServices.partnerType");

  const blocker = useBlocker(({ currentLocation, nextLocation }) => {
    if (isSubmitClicked || generateApiKeySuccess) {
      setIsSubmitClicked(false);
      return false;
    }

    return (
      (partnerId !== "" || policyName !== "" || apiKeyName !== "") &&
      currentLocation.pathname !== nextLocation.pathname
    );
  });

  useEffect(() => {
    const shouldWarnBeforeUnload = () => {
      return partnerId !== "" || policyName !== "" || apiKeyName !== "";
    };

    const handleBeforeUnload = (event) => {
      if (shouldWarnBeforeUnload() && !isSubmitClicked && !generateApiKeySuccess ) {
        event.preventDefault();
        event.returnValue = "";
      }
    };

    window.addEventListener("beforeunload", handleBeforeUnload);

    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, [partnerId, policyName, apiKeyName, isSubmitClicked, generateApiKeySuccess]);

  useEffect(() => {
    if (hasFetchedPartners.current) {
      return;
    }

    hasFetchedPartners.current = true;
    const fetchManualAdjudicationPartners = async () => {
      try {
        setDataLoaded(false);
        const response = await HttpService.get(
          getPartnerManagerUrl(
            "/partners/v3?status=approved&partnerType=Manual_Adjudication&policyGroupAvailable=true",
            process.env.NODE_ENV,
          ),
        );
        if (response?.data?.response) {
          const approvedPartners = response.data.response;
          setPartnerData(approvedPartners);
        } else {
          handleServiceErrors(response?.data, setErrorCode, setErrorMsg);
        }
      } catch (err) {
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
        }
      } finally {
        setDataLoaded(true);
      }
    };

    fetchManualAdjudicationPartners();
  }, []);

  useEffect(() => {
    setPartnerIdDropdownData(
      createDropdownData("partnerId", "", false, partnerData, t),
    );
  }, [partnerData, t]);

  const cancelErrorMsg = () => {
    setErrorMsg("");
    setShowPopup(false);
  };

  const clickOnCancel = () => {
    navigate("/partnermanagement/admin/manual-adjudication-services/api-keys-list");
  };

  const clearForm = () => {
    setErrorCode("");
    setErrorMsg("");
    setPartnerId("");
    setPolicyGroupName("");
    setPolicyName("");
    setPolicyId("");
    setApiKeyName("");
    setInputError("");
    setPoliciesDropdownData([]);
    setPolicyList([]);
  };

  const getListOfPolicies = async (selectedPartner) => {
    try {
      setDataLoaded(false);

      const queryParams = new URLSearchParams();
      queryParams.append("partnerId", selectedPartner.partnerId);
      queryParams.append("partnerIdSearchType", "equals");
      queryParams.append("policyGroupName", selectedPartner.policyGroupName);
      queryParams.append("status", "approved");

      const response = await HttpService.get(
        `${getPartnerManagerUrl("/partner-policy-requests", process.env.NODE_ENV)}?${queryParams.toString()}`,
      );

      if (response?.data?.response) {
        const responsePolicies = response.data.response;
        const policies = Array.isArray(responsePolicies?.data)
          ? responsePolicies.data
          : [];

        setPolicyList(policies);

        setPoliciesDropdownData(
          createDropdownData("policyName", "policyDescription", false, policies, t)
        );
      } else {
        handleServiceErrors(response?.data, setErrorCode, setErrorMsg);
      }
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.response?.data?.errors?.[0]?.message || err.toString());
      }
    } finally {
      setDataLoaded(true);
    }
  };


  const onChangePartnerId = async (fieldName, selectedValue) => {
    setPartnerId(selectedValue);
    setPolicyName("");
    setPolicyId("");
    setPolicyGroupName("");
    setPolicyList([]);
    setPoliciesDropdownData([]);

    const selectedPartner = partnerData.find((item) => item.partnerId === selectedValue);
    if (selectedPartner) {
      setPolicyGroupName(selectedPartner.policyGroupName || "");
      if (selectedPartner.policyGroupName) {
        await getListOfPolicies(selectedPartner);
      }
    }
  };

  const onChangePolicyName = (fieldName, selectedValue) => {
    const selectedPolicy = policyList.find(
      (policy) => policy.policyName === selectedValue
    );

    if (selectedPolicy) {
      setPolicyName(selectedPolicy.policyName);
      setPolicyId(selectedPolicy.policyId);
    } else {
      setPolicyName(selectedValue);
      setPolicyId("");
    }
  };


  const onChangeApiKeyName = (value) => {
    setApiKeyName(value);
    validateInputRegex(value, setInputError, t);
  };

  const isFormValid = () => {
    return partnerId && policyId && apiKeyName.trim() && !inputError;
  };


  const clickOnSubmit = async () => {
    setShowPopup(false);
    setIsSubmitClicked(true);
    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);

    const request = {
      id: "mosip.pms.generate.api.key.post",
      version: "1.0",
      requestTime: new Date().toISOString(),
      request: {
        apiKeyName: trimAndReplace(apiKeyName),
      },
    };

    try {
      const response = await HttpService.post(
        getPartnerManagerUrl(
          `/partners/${partnerId}/policies/${policyId}/api-keys`,
          process.env.NODE_ENV
        ),
        request,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response?.data?.response) {
        const requireData = {
          title: "manualAdjudicationServices.generateApiKey",
          backUrl: "/partnermanagement/admin/manual-adjudication-services/api-keys-list",
          header: "generateApiKey.generateApiKeySuccessHeader",
          description: "generateApiKey.apiKeySuccessMsg",
          subNavigation: "dashboard.manualAdjudication",
        };

        setConfirmationData(requireData);
        setApiKeyId(response.data.response.apiKey);
        setShowPopup(true);
      } else {
        handleServiceErrors(response?.data, setErrorCode, setErrorMsg);
      }
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.response?.data?.errors?.[0]?.message || err.toString());
      }
    } finally {
      setDataLoaded(true);
      setIsSubmitClicked(false);
    }
  };


  const closePopUp = (state) => {
    setShowPopup(state);
    setGenerateApiKeySuccess(true);
  };

  const styleForSearch = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1",
    dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10",
  };

  const copyIdPopupStyle = {
    outerDiv: "!bg-opacity-[50%]",
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter max-[450px]:text-xs relative`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id="generate_manual_adjudication_api_key_error_msg"
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={cancelErrorMsg}
            />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between">
              <Title
                title="manualAdjudicationServices.generateApiKey"
                subTitle="dashboard.manualAdjudication"
                backLink="/partnermanagement/admin/manual-adjudication-services/api-keys-list"
              />
            </div>
            {!generateApiKeySuccess ? (
              <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                <div className="px-[2.5%] py-[2%]">
                  <p id="generate_manual_adjudication_api_key_mandatory_fields_msg" className="text-base text-[#3D4468]">
                    {t("requestPolicy.mandatoryFieldsMsg1")} <span className="text-crimson-red">*</span> {t("requestPolicy.mandatoryFieldsMsg2")}
                  </p>
                  <form>
                    <div className="flex flex-col">
                      <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                        <div className="flex-col w-[48%] max-[450px]:w-full">
                          <label id="generate_manual_adjudication_api_key_partner_type_label" className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                            {t("requestPolicy.partnerType")}<span className="text-crimson-red mx-1">*</span>
                          </label>
                          <input
                            id="generate_manual_adjudication_api_key_partner_type"
                            value={partnerTypeLabel}
                            readOnly
                            className="w-full h-10 px-2 py-3 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none"
                          />
                        </div>
                        <div className="flex-col w-[48%] max-[450px]:w-full">
                          <DropdownWithSearchComponent
                            fieldName="partnerId"
                            dropdownDataList={partnerIdDropdownData}
                            onDropDownChangeEvent={onChangePartnerId}
                            fieldNameKey="requestPolicy.partnerId*"
                            placeHolderKey="createOidcClient.selectPartnerId"
                            selectedDropdownValue={partnerId}
                            searchKey="commons.search"
                            styleSet={styleForSearch}
                            id="generate_manual_adjudication_api_key_partner_id"
                          />
                        </div>
                      </div>

                      <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-2 max-[450px]:flex-col">
                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                          <label id="generate_manual_adjudication_api_key_policy_group_label" className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                            {t("requestPolicy.policyGroup")}<span className="text-crimson-red mx-1">*</span>
                          </label>
                          <input
                            id="generate_manual_adjudication_api_key_policy_group"
                            value={policyGroupName}
                            placeholder={t("commons.partnersHelpText")}
                            readOnly
                            className="h-10 px-2 py-3 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none"
                          />
                        </div>
                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                          <DropdownWithSearchComponent
                            fieldName="policyName"
                            dropdownDataList={policiesDropdownData}
                            onDropDownChangeEvent={onChangePolicyName}
                            fieldNameKey="requestPolicy.policyName*"
                            placeHolderKey="generateApiKey.selectedPolicyName"
                            selectedDropdownValue={policyName}
                            searchKey="commons.search"
                            styleSet={styleForSearch}
                            disabled={!partnerId || !policyGroupName}
                            id="generate_manual_adjudication_api_key_policy_name"
                          />
                        </div>
                      </div>

                      <div className="my-4">
                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                          <label id="generate_manual_adjudication_api_key_name_label" className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>
                            {t("viewApiKeyDetails.apiKeyName")}<span className="text-crimson-red mx-1">*</span>
                          </label>
                          <input
                            value={apiKeyName}
                            onChange={(event) => onChangeApiKeyName(event.target.value)}
                            maxLength={36}
                            className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                            placeholder={t("generateApiKey.enterNameForApiKey")}
                            id="generate_manual_adjudication_api_key_name"
                          />
                          {inputError && (
                            <span id="generate_manual_adjudication_invalid_name" className="text-sm text-crimson-red font-semibold">
                              {inputError}
                            </span>
                          )}
                        </div>
                      </div>
                    </div>
                  </form>
                </div>

                <div className="border bg-medium-gray" />
                <div className="flex flex-row max-[450px]:flex-col px-[3%] py-5 justify-between max-[450px]:space-y-2">
                  <button
                    id="generate_manual_adjudication_clear_form"
                    onClick={clearForm}
                    className={`w-40 h-10 border-[#1447B2] ${isLoginLanguageRTL ? "ml-3 mr-2" : "mr-3 ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                  >
                    {t("requestPolicy.clearForm")}
                  </button>
                  <div className="flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end">
                    <button
                      id="generate_manual_adjudication_cancel_btn"
                      onClick={clickOnCancel}
                      className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                    >
                      {t("requestPolicy.cancel")}
                    </button>
                    <button
                      id="generate_manual_adjudication_submit_btn"
                      disabled={!isFormValid()}
                      onClick={clickOnSubmit}
                      className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? "bg-tory-blue text-white" : "border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed"}`}
                    >
                      {t("requestPolicy.submit")}
                    </button>
                    {showPopup && !errorMsg && (
                      <CopyIdPopUp
                        closePopUp={closePopUp}
                        subtitle={partnerId}
                        title={policyName}
                        id={apiKeyId}
                        header="apiKeysList.apiKey"
                        alertMsg="apiKeysList.apiKeyIdAlertMsg"
                        styleSet={copyIdPopupStyle}
                      />
                    )}
                  </div>
                </div>
              </div>
            ) : (
              <Confirmation id="generate_manual_adjudication_api_key_confirmation" confirmationData={confirmationData} />
            )}
          </div>
        </>
      )}
      <BlockerPrompt blocker={blocker} />
    </div>
  );
}

export default GenerateManualAdjudicationApiKey;
