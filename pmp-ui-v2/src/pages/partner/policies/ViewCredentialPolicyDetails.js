import React, { useEffect, useMemo, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { HttpService } from "../../../services/HttpService";
import { bgOfStatus, formatDate, getPartnerManagerUrl, getStatusCode, isLangRTL } from "../../../utils/AppUtils";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";


function ViewPolicyDetails() {
      const { t } = useTranslation();
      const NOT_MAPPED = t("common.notMapped");
      const navigate = useNavigate();
      const { state } = useLocation();
      const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
      const [policyDetails, setPolicyDetails] = useState({});
      const [mappedPolicy, setMappedPolicy] = useState({});
      const [dataLoaded, setDataLoaded] = useState(true);
      const [errorMsg, setErrorMsg] = useState("");

  useEffect(() => {
    const selectedPolicy = state?.selectedPolicyData;
    if (selectedPolicy) {
      setPolicyDetails(selectedPolicy);
      return;
    }

    const partnerData = sessionStorage.getItem("selectedPolicyAttributes");
    if (partnerData) {
      try {
        const parsedData = JSON.parse(partnerData);
        setPolicyDetails(parsedData);
      } catch (error) {
        console.error("Error in viewPolicyDetails page :", error);
        navigate("/partnermanagement/policies/policies-list");
      }
      return;
    }
    

    navigate("/partnermanagement/policies/policies-list");
  }, [navigate, state]);

useEffect(() => {
  const fetchAllData = async () => {
  const partnerId = policyDetails?.partnerId;
  const policyId = policyDetails?.policyId;

  if (!partnerId || !policyId) return;

  try {
    setDataLoaded(false);

    // ✅ Credential Types API
    const credentialResponse = await HttpService.get(
      getPartnerManagerUrl(
        `/partners/${partnerId}/policies/${policyId}/credential-types`,
        process.env.NODE_ENV
      )
    );

    const credentialData = credentialResponse?.data?.response || {};

    // ✅ Bio Extractors API
    const bioResponse = await HttpService.get(
      getPartnerManagerUrl(
        `/partners/${partnerId}/bioextractors/${policyId}`,
        process.env.NODE_ENV
      )
    );

    const bioExtractors =
      bioResponse?.data?.response?.extractors ||
      bioResponse?.data?.response ||
      [];

    setMappedPolicy({
      extractors: bioExtractors,
      credentialTypes: credentialData   // 👈 important
    });

  } catch (error) {
    console.error("Error fetching data:", error);
    setErrorMsg(t("viewPolicyDetails.mappedPolicyFetchError"));
  } finally {
    setDataLoaded(true);
  }
};

  fetchAllData();

}, [policyDetails]);

const resolvedPolicyDetails = policyDetails;

    const viewLabel = (value) => value || NOT_MAPPED;

const biometricMappings = useMemo(() => {
  return (mappedPolicy?.extractors || []).map((mapping) => ({
    biometricModality:
      mapping?.bioModality ||
      mapping?.biometric ||
      mapping?.bio_modality ||
      NOT_MAPPED,

    biometricProviderConfiguration:
      mapping?.configName ||
      mapping?.bioExtractorConfigurationName ||
      mapping?.bioExtractorConfigName ||
      mapping?.attributeName ||
      NOT_MAPPED,

    providerVersion:
      mapping?.bioextractorProviderVersion ||
      mapping?.extractor?.version ||
      NOT_MAPPED,

    providerName:
      mapping?.bioextractorProviderName ||
      mapping?.extractor?.provider ||
      NOT_MAPPED
  }));
}, [mappedPolicy, NOT_MAPPED]);

  const credentialType =
  mappedPolicy?.credentialTypes?.credentialTypes?.join(", ") || "";

  return (
    <div className={`w-full p-5 bg-anti-flash-white h-full break-words font-inter mb-[2%] ${isLoginLanguageRTL ? "mr-20 ml-1" : "ml-20 mr-1"} overflow-x-scroll`}>
      {!dataLoaded && <LoadingIcon />}
      {errorMsg && (
        <ErrorMessage
          id='view_policy_details_error_msg'
          errorCode=''
          errorMessage={errorMsg}
          clickOnCancel={() => setErrorMsg("")}
        />
      )}
      <div className="flex justify-between mb-5">
        <Title title='viewPolicyDetails.viewPolicyDetails' subTitle='viewPolicyDetails.policySection' backLink='/partnermanagement/policies/policies-list' />
      </div>

      <div className="bg-snow-white h-fit mt-1 rounded-t-xl shadow-lg mx-4 px-4 pb-4">
        <div className={`flex-col ${isLoginLanguageRTL ? "pr-8" : "pl-8"} pt-6 pb-5`}>
          <p id='view_policy_details_policy_name_title' className='text-lg text-dark-blue mb-3 font-semibold'>
            {viewLabel(resolvedPolicyDetails.policyName)}
          </p>
          <div className='flex items-center justify-start flex-wrap gap-y-2'>
            <div id='view_policy_details_status' className={`${bgOfStatus(resolvedPolicyDetails.status)} flex w-fit py-1.5 px-3 text-sm rounded-md font-semibold`}>
              {getStatusCode(resolvedPolicyDetails.status, t)}
            </div>
            <div id='view_policy_details_created_on' className={`font-semibold ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-sm text-dark-blue`}>
              {t("viewPolicyDetails.createdOn")} {formatDate(resolvedPolicyDetails.createdDateTime, "date")}
            </div>
            <div className='mx-3 text-gray-300'>|</div>
            <div id='view_policy_details_created_date-time' className='font-semibold text-sm text-dark-blue'>
              {formatDate(resolvedPolicyDetails.createdDateTime, "time")}
            </div>
          </div>
        </div>

      <div className="px-8 py-6 space-y-6">

        {/* ROW 1 */}
        <div className="grid grid-cols-2 gap-10">
          <div>
            <p className="text-suva-gray text-sm font-semibold">
              {t("viewPolicyDetails.partnerId")}
            </p>
            <p className="text-vulcan text-base font-semibold">
              {viewLabel(resolvedPolicyDetails.partnerId)}
            </p>
          </div>

          <div>
            <p className="text-suva-gray text-sm font-semibold">
              {t("viewPolicyDetails.partnerType")}
            </p>
            <p className="text-vulcan text-base font-semibold">
              {t("partnerTypes.credentialPartner")}
            </p>
          </div>
        </div>

        <hr className="border-gray-200" />

        {/* ROW 2 */}
        <div className="grid grid-cols-2 gap-10">
          <div>
            <p className="text-suva-gray text-sm font-semibold">
              {t("viewPolicyDetails.policyGroupName")}
            </p>
            <p className="text-vulcan text-base font-semibold">
              {viewLabel(resolvedPolicyDetails.policyGroupName)}
            </p>
          </div>

          <div>
            <p className="text-suva-gray text-sm font-semibold">
              {t("viewPolicyDetails.policyName")}
            </p>
            <p className="text-vulcan text-base font-semibold">
              {viewLabel(resolvedPolicyDetails.policyName)}
            </p>
          </div>
        </div>

        {/* ROW 3 */}
        <div className="grid grid-cols-2 gap-10">
          <div>
            <p className="text-suva-gray text-sm font-semibold">
              {t("viewPolicyDetails.policyGroupDescription")}
            </p>
            <p className="text-vulcan text-base font-semibold">
              {viewLabel(resolvedPolicyDetails.policyGroupDescription)}
            </p>
          </div>

          <div>
            <p className="text-suva-gray text-sm font-semibold">
              {t("viewPolicyDetails.policyNameDescription")}
            </p>
            <p className="text-vulcan text-base font-semibold">
              {viewLabel(resolvedPolicyDetails.policyDescription)}
            </p>
          </div>
        </div>

      </div>

                    <div className='mt-6 border border-[#D5D8E3] rounded-md'>

            {/* HEADER */}
            <div className='px-6 py-4 bg-anti-flash-white border-b border-[#D5D8E3]'>
              <p className='font-semibold text-vulcan text-base'>
                {t("viewPolicyDetails.biometricMapping")}
              </p>
            </div>
            <div className='p-6'>  

          <table className='text-left text-sm border border-[#E7E7E7] w-full mx-auto border-separate border-spacing-0'>

                <thead className='text-suva-gray'>
                  <tr>
                    <th className='p-3 font-semibold border-b border-[#E7E7E7]'>
                      {t("viewPolicyDetails.biometricModality")}
                    </th>
                    <th className='p-3 font-semibold border-b border-[#E7E7E7]'>
                      {t("viewPolicyDetails.biometricProviderConfiguration")}
                    </th>
                    <th className='p-3 font-semibold border-b border-[#E7E7E7]'>
                      {t("viewPolicyDetails.biometricProviderVersion")}
                    </th>
                    <th className='p-3 font-semibold border-b border-[#E7E7E7]'>
                      {t("viewPolicyDetails.biometricProviderName")}
                    </th>
                  </tr>
                </thead>

                <tbody>
                  {biometricMappings.length > 0 ? (
                    biometricMappings.map((mapping, index) => (
                      <tr key={index} className='even:bg-[#F8F8F8]'>
                        <td className='p-3 border-b border-[#E7E7E7] break-words max-w-[200px] whitespace-normal'>
                          {viewLabel(mapping.biometricModality)}
                        </td>
                        <td className='p-3 border-b border-[#E7E7E7] break-words max-w-[200px] whitespace-normal'>
                          {viewLabel(mapping.biometricProviderConfiguration)}
                        </td>
                        <td className='p-3 border-b border-[#E7E7E7] break-words max-w-[200px] whitespace-normal'>
                          {viewLabel(mapping.providerVersion)}
                        </td>
                        <td className='p-3 border-b border-[#E7E7E7] break-words max-w-[200px] whitespace-normal'>
                          {viewLabel(mapping.providerName)}
                        </td>
                      </tr>
                    ))
                  ) : (
                    <tr>
                      <td className='p-3 border-b'>{NOT_MAPPED}</td>
                      <td className='p-3 border-b'>{NOT_MAPPED}</td>
                      <td className='p-3 border-b'>{NOT_MAPPED}</td>
                      <td className='p-3 border-b'>{NOT_MAPPED}</td>
                    </tr>
                  )}
                </tbody>

              </table>
            
            </div>

          </div>

          <hr className='h-px w-full bg-gray-200 border-0 mt-6' />

          <div className='mt-6 px-6 py-4'>
            <p className='font-semibold text-suva-gray text-sm'>{t("viewPolicyDetails.credentialType")}</p>
            <p className='font-semibold text-vulcan text-base'>{viewLabel(credentialType)}</p>
          </div>
          <hr className='h-px w-full bg-gray-200 border-0' />

<div className='mt-6 mb-3'>
  <p className='font-semibold text-vulcan text-base mb-4'>
    {t("viewPolicyDetails.comments")}
  </p>

  <div className='space-y-4'>

    {/* Admin Comment */}
    <div className='flex font-semibold'>
      <span
  className={`relative w-8 h-8 flex justify-center items-center ${
    isLoginLanguageRTL ? "ml-3" : "mr-3"
  }`}
>
<span
  className={`absolute top-1/2 translate-y-10 w-[2px] h-[60px] bg-gray-200 ${
    isLoginLanguageRTL ? "right-4" : "left-4"
  }`}
></span>

  <img
    src={require("../../../svg/admin.png")}
    alt="admin"
    className="w-8 h-8"
  />
</span>

      <div className='flex flex-col p-4 rounded-md bg-floral-white w-full'>
        <h4 className='text-sm text-[#031640]'>
          {t("viewPolicyDetails.partnerAdmin")}
        </h4>

        {resolvedPolicyDetails.adminComments ? (
  <p className='text-sm mt-3 text-vulcan'>
    {resolvedPolicyDetails.adminComments}
  </p>
) : (
  <div className='flex items-center mt-3'>
    <div
      className={`${bgOfStatus(resolvedPolicyDetails.status)} 
      px-3 py-1 rounded-md text-sm font-semibold`}
    >
      {getStatusCode(resolvedPolicyDetails.status, t)}
    </div>
  </div>
)}

        {resolvedPolicyDetails.updatedDateTime && (
          <div className='flex mt-4'>
            <div className='text-sm text-dark-blue'>
              {formatDate(resolvedPolicyDetails.updatedDateTime, "date")}
            </div>
            <div className='mx-3 text-gray-300'>|</div>
            <div className='text-sm text-dark-blue'>
              {formatDate(resolvedPolicyDetails.updatedDateTime, "time")}
            </div>
          </div>
        )}
      </div>
    </div>

    {/* Partner Comment */}
    <div className='flex font-semibold'>
      <span className={`w-8 h-8 ${isLoginLanguageRTL ? "ml-3" : "mr-3"}`}>
        <img src={require("../../../svg/partner.png")} alt="partner" className="w-8 h-8" />
      </span>

      <div className='flex flex-col p-4 rounded-md bg-alice-green w-full'>
        <h4 className='text-sm text-[#031640]'>
          {t("viewPolicyDetails.partnerUser")}
        </h4>

        <p className='text-sm mt-3 text-vulcan'>
          {resolvedPolicyDetails.partnerComment || t("viewPolicyDetails.noComments")}
        </p>

        {resolvedPolicyDetails.createdDateTime && (
          <div className='flex mt-4'>
            <div className='text-sm text-dark-blue'>
              {formatDate(resolvedPolicyDetails.createdDateTime, "date")}
            </div>
            <div className='mx-3 text-gray-300'>|</div>
            <div className='text-sm text-dark-blue'>
              {formatDate(resolvedPolicyDetails.createdDateTime, "time")}
            </div>
          </div>
        )}
      </div>
    </div>

  </div>
</div>

          
        

        <hr className='h-px w-full bg-gray-200 border-0' />
        <div className={`flex justify-end py-5 ${isLoginLanguageRTL ? "ml-8" : "mr-8"}`}>
          <button
            id='view_policy_back_btn'
            onClick={() => navigate(-1)}
            className='h-10 w-28 text-sm p-3 py-2 text-tory-blue bg-white border border-blue-800 font-semibold rounded-md text-center'
          >
            {t("viewPolicyDetails.back")}
          </button>
        </div>
      </div>
      </div>
   
  );
}

export default ViewPolicyDetails;