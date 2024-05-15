import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService.js';
import { useTranslation } from "react-i18next";
import { useEffect } from 'react';

function Dashboard() {
  const navigate = useNavigate();
  const { t, i18n } = useTranslation();

  useEffect(() => {
    const langCode = getUserProfile().langCode;
    if (langCode != null) {
      i18n.changeLanguage(langCode);
    }
  }, [i18n]);

  const partnerCertificatesList = () => {
    navigate('/partnermanagement/partnerCertificate')
  };

  return (
    <div className="w-full p-5 bg-anti-flash-white h-fit font-inter">
      <div className="mb-7 mt-4 ml-5 text-xl font-semibold tracking-tight text-gray-700">
        <p >
          {t('dashboard.welcomeMsg', { firstName: getUserProfile().firstName, lastName: getUserProfile().lastName })},
        </p>
      </div>
      <div className="flex mt-2 ml-7 flex-wrap">
        <div className="w-[18.5rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="40.618"
              height="40.618"
              viewBox="0 0 40.618 40.618"
            >
              <path
                id="library_books_FILL0_wght200_GRAD0_opsz24"
                d="M155.53-826.912h13.317V-833.3H155.53Zm0,12.832h16.633v-2.389H155.53Zm0-6.168h16.633v-2.389H155.53Zm-4.5,13.7a3.738,3.738,0,0,1-2.755-1.105,3.738,3.738,0,0,1-1.105-2.755V-836.14a3.738,3.738,0,0,1,1.105-2.755A3.738,3.738,0,0,1,151.028-840h25.731a3.738,3.738,0,0,1,2.755,1.105,3.739,3.739,0,0,1,1.105,2.755v25.731a3.738,3.738,0,0,1-1.105,2.755,3.738,3.738,0,0,1-2.755,1.105Zm0-2.389h25.731a1.405,1.405,0,0,0,1.011-.459,1.405,1.405,0,0,0,.459-1.011V-836.14a1.405,1.405,0,0,0-.459-1.011,1.405,1.405,0,0,0-1.011-.459H151.028a1.405,1.405,0,0,0-1.011.459,1.405,1.405,0,0,0-.459,1.011v25.731a1.405,1.405,0,0,0,.459,1.011A1.405,1.405,0,0,0,151.028-808.939Zm-7.168,9.557a3.738,3.738,0,0,1-2.755-1.105A3.738,3.738,0,0,1,140-803.241v-28.12h2.389v28.12a1.405,1.405,0,0,0,.459,1.011,1.405,1.405,0,0,0,1.011.459h28.12v2.389Zm5.7-38.229v0Z"
                transform="translate(-140 840)"
                fill="#1447b2"
              />
            </svg>
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
        <div className="w-[18.8rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="36.754"
              height="47.255"
              viewBox="0 0 36.754 47.255"
            >
              <path
                id="upload_file_FILL0_wght200_GRAD0_opsz24"
                d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                transform="translate(-200 840)"
                fill="#1347b2"
              />
            </svg>
          </div>
          <div>
            <h5 className="mb-2 text-sm font-semibold tracking-tight text-gray-600 ">
              {t('dashboard.organisationUsers')}
            </h5>
            <p className="mb-3 text-xs font-normal text-gray-400">
              {t('dashboard.organisationUsersDesc')}
            </p>
          </div>
        </div>
        <div onClick={() => partnerCertificatesList()} className="w-[18.5rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="42.784" height="47.184" viewBox="0 0 16.277 20.784">
              <path id="upload_file_FILL0_wght200_GRAD0_opsz24"
                d="M207.325-823.356h1.127v-5.29l2.367,2.367.8-.8-3.727-3.727-3.727,3.727.8.8,2.36-2.36Zm-5.5,3.641a1.763,1.763,0,0,1-1.3-.521,1.763,1.763,0,0,1-.521-1.3V-838.18a1.763,1.763,0,0,1,.521-1.3,1.763,1.763,0,0,1,1.3-.521h8.885l5.071,5.071v13.393a1.763,1.763,0,0,1-.521,1.3,1.763,1.763,0,0,1-1.3.521Zm8.322-14.65v-4.508H201.82a.663.663,0,0,0-.477.217.663.663,0,0,0-.217.477v16.644a.663.663,0,0,0,.217.477.663.663,0,0,0,.477.217h12.136a.663.663,0,0,0,.477-.217.663.663,0,0,0,.217-.477v-12.829Zm-9.015-4.508v0Z"
                transform="translate(-199.75 840.25)"
                fill="#1447b2" stroke="#1447b2" strokeWidth="0.5" />
            </svg>
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
        <div className="w-[18.8rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="45.695"
              height="40.618"
              viewBox="0 0 45.695 40.618"
            >
              <path
                id="dvr_FILL0_wght200_GRAD0_opsz24"
                d="M130.155-775.59a1.5,1.5,0,0,0,1.1-.461,1.5,1.5,0,0,0,.461-1.1,1.5,1.5,0,0,0-.461-1.1,1.5,1.5,0,0,0-1.1-.461,1.5,1.5,0,0,0-1.1.461,1.5,1.5,0,0,0-.461,1.1,1.5,1.5,0,0,0,.461,1.1A1.5,1.5,0,0,0,130.155-775.59Zm0-10.155a1.5,1.5,0,0,0,1.1-.461,1.5,1.5,0,0,0,.461-1.1,1.5,1.5,0,0,0-.461-1.1,1.5,1.5,0,0,0-1.1-.461,1.5,1.5,0,0,0-1.1.461,1.5,1.5,0,0,0-.461,1.1,1.5,1.5,0,0,0,.461,1.1A1.5,1.5,0,0,0,130.155-785.745Zm5.37,9.862H156.81v-2.539H135.525Zm0-10.155H156.81v-2.539H135.525Zm-.293,26.656v-5.077H124.1a3.972,3.972,0,0,1-2.927-1.174A3.972,3.972,0,0,1,120-768.56V-795.9a3.972,3.972,0,0,1,1.174-2.927A3.972,3.972,0,0,1,124.1-800h37.494a3.972,3.972,0,0,1,2.927,1.174A3.972,3.972,0,0,1,165.7-795.9v27.339a3.972,3.972,0,0,1-1.174,2.927,3.972,3.972,0,0,1-2.927,1.174H150.464v5.077ZM124.1-767h37.494a1.493,1.493,0,0,0,1.074-.488,1.493,1.493,0,0,0,.488-1.074V-795.9a1.493,1.493,0,0,0-.488-1.074,1.493,1.493,0,0,0-1.074-.488H124.1a1.493,1.493,0,0,0-1.074.488,1.493,1.493,0,0,0-.488,1.074v27.339a1.493,1.493,0,0,0,.488,1.074A1.493,1.493,0,0,0,124.1-767Zm-1.562,0v0Z"
                transform="translate(-120 800)"
                fill="#1447b2"
              />
            </svg>
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
        <div className="w-[18.5rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="40.618"
              height="40.618"
              viewBox="0 0 40.618 40.618"
            >
              <path
                id="library_books_FILL0_wght200_GRAD0_opsz24"
                d="M155.53-826.912h13.317V-833.3H155.53Zm0,12.832h16.633v-2.389H155.53Zm0-6.168h16.633v-2.389H155.53Zm-4.5,13.7a3.738,3.738,0,0,1-2.755-1.105,3.738,3.738,0,0,1-1.105-2.755V-836.14a3.738,3.738,0,0,1,1.105-2.755A3.738,3.738,0,0,1,151.028-840h25.731a3.738,3.738,0,0,1,2.755,1.105,3.739,3.739,0,0,1,1.105,2.755v25.731a3.738,3.738,0,0,1-1.105,2.755,3.738,3.738,0,0,1-2.755,1.105Zm0-2.389h25.731a1.405,1.405,0,0,0,1.011-.459,1.405,1.405,0,0,0,.459-1.011V-836.14a1.405,1.405,0,0,0-.459-1.011,1.405,1.405,0,0,0-1.011-.459H151.028a1.405,1.405,0,0,0-1.011.459,1.405,1.405,0,0,0-.459,1.011v25.731a1.405,1.405,0,0,0,.459,1.011A1.405,1.405,0,0,0,151.028-808.939Zm-7.168,9.557a3.738,3.738,0,0,1-2.755-1.105A3.738,3.738,0,0,1,140-803.241v-28.12h2.389v28.12a1.405,1.405,0,0,0,.459,1.011,1.405,1.405,0,0,0,1.011.459h28.12v2.389Zm5.7-38.229v0Z"
                transform="translate(-140 840)"
                fill="#1447b2"
              />
            </svg>
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
      </div>
      <hr className="h-px ml-7 mt-7 bg-gray-200 border-0" />
      <div className="flex mt-7 ml-7 justify-between text-sm text-gray-400">
        <div>
          <p>2024 © MOSIP - All rights reserved.</p>
        </div>
        <div className="flex justify-between">
          <p className="mr-7">Documentation</p>
          <p className="mr-7">MOSIP Community</p>
          <p className="mr-7">Contact Us</p>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;