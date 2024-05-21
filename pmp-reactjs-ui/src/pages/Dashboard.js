import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService.js';
import { useTranslation } from "react-i18next";
import partnerTypeRequestIcon from '../svg/partner_type_request_icon.svg';
import orgUsersIcon from '../svg/org_user_icon.svg';
import partnerCertificateIcon from '../svg/partner_certificate_icon.svg';
import policiesIcon from '../svg/policies_icon.svg';
import authServiceIcon from '../svg/auth_services_icon.svg';

function Dashboard() {
  const navigate = useNavigate();
  const { t } = useTranslation();

  const partnerCertificatesList = () => {
    navigate('/partnermanagement/partnerCertificate')
  };

  const policies = () => {
    navigate('/partnermanagement/policies')
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
            <img src={partnerTypeRequestIcon} alt=""></img>
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
            <img src={orgUsersIcon} alt=""></img>
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
            <img src={partnerCertificateIcon} alt=""></img>
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
        <div onClick={() => policies()} className="w-[18.8rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <img src={policiesIcon} alt=""></img>
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
            <img src={authServiceIcon} alt=""></img>
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
          <p>2024 © MOSIP - {t('footer.allRightsReserved')}</p>
        </div>
        <div className="flex justify-between">
        <p className="mr-7">{t('footer.documentation')}</p>
              <p className="mr-7">{t('footer.mosipCommunity')}</p>
              <p className="mr-7">{t('footer.contactUs')}</p>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;