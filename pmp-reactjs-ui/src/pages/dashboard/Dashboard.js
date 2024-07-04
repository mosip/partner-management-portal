import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { getUserProfile } from '../../services/UserProfileService.js';
import { isLangRTL } from '../../utils/AppUtils.js';
import { useTranslation } from "react-i18next";
import { getPartnerManagerUrl, createRequest, handleServiceErrors, moveToOidcClientsList } from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService.js';
import ErrorMessage from '../common/ErrorMessage.js';
import LoadingIcon from "../common/LoadingIcon.js";

import SelectPolicyPopup from './SelectPolicyPopup.js';

import partnerTypeRequestIcon from '../../svg/partner_type_request_icon.svg';
import orgUsersIcon from '../../svg/org_user_icon.svg';
import partnerCertificateIcon from '../../svg/partner_certificate_icon.svg';
import policiesIcon from '../../svg/policies_icon.svg';
import authServiceIcon from '../../svg/auth_services_icon.svg';

function Dashboard() {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [showPolicies, setShowPolicies] = useState(false);
  const [showAuthenticationServices, setShowAuthenticationServices] = useState(false);

  const [showPopup, setShowPopup] = useState(false);
  const closePopup = (reload) => {
    setShowPopup(false);
    if (reload) {
      window.location.reload();
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userProfile = getUserProfile();
        if (userProfile.partnerType === "AUTH_PARTNER") {
          setShowAuthenticationServices(true);
        }
        //1. verify that the logged in user's email is registered in PMS table or not
        // using the email id
        const verifyEmailRequest = createRequest({
          "emailId": userProfile.email
        });
        const response = await HttpService.put(getPartnerManagerUrl('/partners/email/verify', process.env.NODE_ENV), verifyEmailRequest);
        if (response && response.data && response.data.response) {
          const resData = response.data.response;
          console.log(`user's email exist in db: ${resData.emailExists}`);
          if (!resData.emailExists) {
            //2. if email does not exist then check if Policy Group selection is required for this Partner Type or not
            if (
              resData.policyRequiredPartnerTypes.indexOf(userProfile.partnerType) > -1) {
              console.log(`show policy group selection popup`);
              setShowPolicies(true);
              //3. show policy group selection popup
              //TODO show policy group selection popup
              setShowPopup(true);
            } else {
              //4. register the new user in PMS
              const registerUserRequest = createRequest({
                partnerId: userProfile.userName,
                organizationName: userProfile.orgName,
                address: userProfile.address,
                contactNumber: userProfile.phoneNumber,
                emailId: userProfile.email,
                partnerType: userProfile.partnerType,
                langCode: userProfile.langCode,
              });
              const registerUserResponse = await HttpService.post(getPartnerManagerUrl('/partners', process.env.NODE_ENV), registerUserRequest);
              const registerUserResponseData = registerUserResponse.data;
              if (registerUserResponseData && registerUserResponseData.response) {
                window.location.reload();
              } else {
                handleServiceErrors(registerUserResponseData, setErrorCode, setErrorMsg);
              }
            }
          }
          //if email exists then do nothing
          if (
            resData.policyRequiredPartnerTypes.indexOf(userProfile.partnerType) > -1) {
            setShowPolicies(true);
          } 
        } else {
          setErrorMsg(t('dashboard.verifyEmailError'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        setErrorMsg(err);
        setDataLoaded(true);
      }
    };
    fetchData();

  }, []);

  const partnerCertificatesList = () => {
    navigate('/partnermanagement/partnerCertificate')
  };

  const policies = () => {
    navigate('/partnermanagement/policies')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  return (
    <div className={`w-full mb-[2%] ${isLoginLanguageRTL ? "mr-28": "ml-20"} overflow-x-scroll relative`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <div className={`flex justify-end max-w-7xl mt-3 absolute ${isLoginLanguageRTL ?"left-2" :"right-2"}`}>
              <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-4">
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
              </div>
            </div>
          )}
          <div className="mb-6 mt-5 ml-[2%] text-lg font-semibold tracking-tight text-gray-700">
            <p >
              {t('dashboard.welcomeMsg', { firstName: getUserProfile().firstName, lastName: getUserProfile().lastName })}!
            </p>
          </div>
          <div className="flex mt-2 ml-[3%] flex-wrap break-words">
            {/* <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
              <div className="flex justify-center mb-5">
                <img src={partnerTypeRequestIcon} alt="" className="w-8 h-8"></img>
              </div>
              <div>
                <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                  {t('dashboard.partnerTypeRequest')}
                </h5>
                <p className="mb-3 text-xs font-normal text-gray-400">
                  {t('dashboard.partnerTypeRequestDesc')}
                </p>
              </div>
            </div>
            <div className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
              <div className="flex justify-center mb-5">
                <img src={orgUsersIcon} alt="" className="w-8 h-8"></img>
              </div>
              <div>
                <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                  {t('dashboard.organisationUsers')}
                </h5>
                <p className="mb-3 text-xs font-normal text-gray-400">
                  {t('dashboard.organisationUsersDesc')}
                </p>
              </div>
            </div> */}
            <div onClick={() => partnerCertificatesList()} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
              <div className="flex justify-center mb-5">
                <img src={partnerCertificateIcon} alt="" className="w-8 h-8"></img>
              </div>
              <div>
                <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600">
                  {t('dashboard.partnerCertificate')}
                </h5>
                <p className="mb-3 text-xs font-normal text-gray-400">
                  {t('dashboard.partnerCertificateDesc')}
                </p>
              </div>
            </div>
            {showPolicies && (
              <div onClick={() => policies()} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                <div className="flex justify-center mb-5">
                  <img src={policiesIcon} alt="" className="w-8 h-8"></img>
                </div>
                <div>
                  <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                    {t('dashboard.policies')}
                  </h5>
                  <p className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.policiesDesc')}
                  </p>
                </div>
              </div>
            )}
            {showAuthenticationServices && (
              <div onClick={() => moveToOidcClientsList(navigate)} className="w-[23.5%] min-h-[50%] p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
                <div className="flex justify-center mb-5">
                  <img src={authServiceIcon} alt="" className="w-8 h-8"></img>
                </div>
                <div>
                  <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
                    {t('dashboard.authenticationServices')}
                  </h5>
                  <p className="mb-3 text-xs font-normal text-gray-400">
                    {t('dashboard.authenticationServicesDesc')}
                  </p>
                </div>
              </div>
            )}
          </div>
          {showPopup && (
            <SelectPolicyPopup closePopup={closePopup} />
          )}

        </>)}
    </div>
  )
}

export default Dashboard;