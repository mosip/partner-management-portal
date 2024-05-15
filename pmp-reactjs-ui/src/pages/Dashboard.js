import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../services/UserProfileService.js';
import { useTranslation } from "react-i18next";

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
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="40.618" height="40.618" viewBox="0 0 18.426 20.552">
              <path id="admin_panel_settings_24dp_FILL0_wght300_GRAD0_opsz24"
                d="M193.548-842.786a1.568,1.568,0,0,0,1.152-.474,1.568,1.568,0,0,0,.474-1.152,1.568,1.568,0,0,0-.474-1.152,1.568,1.568,0,0,0-1.152-.474,1.568,1.568,0,0,0-1.152.474,1.568,1.568,0,0,0-.474,1.152,1.568,1.568,0,0,0,.474,1.152A1.568,1.568,0,0,0,193.548-842.786Zm0,3.252a3.141,3.141,0,0,0,1.55-.388,3.3,3.3,0,0,0,1.143-1.038,5.25,5.25,0,0,0-1.284-.552,5.2,5.2,0,0,0-1.409-.19,5.2,5.2,0,0,0-1.409.19,5.25,5.25,0,0,0-1.284.552A3.3,3.3,0,0,0,192-839.922,3.141,3.141,0,0,0,193.548-839.534Zm-5.419,1.6a10.469,10.469,0,0,1-5.823-4.14A11.826,11.826,0,0,1,180-849.18v-6.236l8.129-3.043,8.129,3.043v6.14q-.379-.154-.8-.278a7.206,7.206,0,0,0-.829-.195v-4.535l-6.5-2.428-6.5,2.428v5.1a9.856,9.856,0,0,0,.406,2.813,11.129,11.129,0,0,0,1.12,2.566,10.19,10.19,0,0,0,1.706,2.143,8.5,8.5,0,0,0,2.153,1.516l.031-.01a5.948,5.948,0,0,0,.556,1.142,5.89,5.89,0,0,0,.777.992,1.122,1.122,0,0,0-.124.045A1.17,1.17,0,0,1,188.129-837.929Zm5.419.021a4.7,4.7,0,0,1-3.454-1.424,4.7,4.7,0,0,1-1.424-3.454,4.7,4.7,0,0,1,1.424-3.454,4.7,4.7,0,0,1,3.454-1.424A4.7,4.7,0,0,1,197-846.239a4.7,4.7,0,0,1,1.424,3.454A4.7,4.7,0,0,1,197-839.332,4.7,4.7,0,0,1,193.548-837.908ZM188.129-848.428Z"
                transform="translate(-180 858.46)" fill="#1447b2" />
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
            <svg xmlns="http://www.w3.org/2000/svg"
              width="46.745" height="47.255" viewBox="0 0 25.476 15.543">
              <path id="groups_24dp_FILL0_wght200_GRAD0_opsz24"
                d="M58.3-599.847V-601.8a3.266,3.266,0,0,1,.609-1.917,5.026,5.026,0,0,1,1.8-1.5,10.061,10.061,0,0,1,2.767-.95,17.753,17.753,0,0,1,3.487-.317,17.962,17.962,0,0,1,3.524.317,10.061,10.061,0,0,1,2.767.95,4.98,4.98,0,0,1,1.791,1.5,3.286,3.286,0,0,1,.6,1.917v1.951Zm18.907,0v-2.075a6.493,6.493,0,0,0-.182-1.564,5.858,5.858,0,0,0-.545-1.4q.543-.087.976-.125t.819-.038A6.238,6.238,0,0,1,82.086-604a3.076,3.076,0,0,1,1.691,2.657v1.5Zm-17.122-1.734H73.871v-.367q-.1-1.234-2.023-2.018a13.014,13.014,0,0,0-4.875-.784,13.014,13.014,0,0,0-4.875.784q-1.923.783-2.013,2.018Zm18.19-5.1a2.37,2.37,0,0,1-1.733-.735,2.359,2.359,0,0,1-.735-1.723,2.323,2.323,0,0,1,.735-1.731,2.414,2.414,0,0,1,1.739-.714,2.377,2.377,0,0,1,1.748.714,2.346,2.346,0,0,1,.714,1.721,2.4,2.4,0,0,1-.709,1.743A2.363,2.363,0,0,1,78.275-606.685Zm-11.29-1.1a3.689,3.689,0,0,1-2.7-1.109,3.652,3.652,0,0,1-1.117-2.693,3.652,3.652,0,0,1,1.109-2.709,3.694,3.694,0,0,1,2.694-1.094,3.684,3.684,0,0,1,2.709,1.09,3.662,3.662,0,0,1,1.093,2.7,3.722,3.722,0,0,1-1.09,2.7A3.621,3.621,0,0,1,66.985-607.785Zm0-1.734a1.95,1.95,0,0,0,1.453-.615,2.034,2.034,0,0,0,.6-1.469,1.985,1.985,0,0,0-.594-1.453,1.993,1.993,0,0,0-1.473-.6,2.02,2.02,0,0,0-1.453.595,1.966,1.966,0,0,0-.615,1.473,1.987,1.987,0,0,0,.615,1.453,2,2,0,0,0,1.469.615Zm-.007,8.507,0,0M66.973-611.587Z"
                transform="translate(-58.301 615.39)"
                fill="#1447b2" />
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
              width="42.748"
              height="47.184"
              viewBox="0 0 42.748 47.184"
            >
              <path
                id="quick_reference_FILL0_wght200_GRAD0_opsz24"
                d="M191.254-798.059h2.016v-7.864h-2.016Zm1.008-10.032a.8.8,0,0,0,.716-.267,1.06,1.06,0,0,0,.292-.741.936.936,0,0,0-.318-.716,1,1,0,0,0-.691-.292.974.974,0,0,0-.716.292.973.973,0,0,0-.292.716,1,1,0,0,0,.292.691A.936.936,0,0,0,192.263-808.09Zm-29.641-29.288v0Zm6.553,24.9h8.575q.418-.7.89-1.356t1-1.265h-10.46Zm0,10.485h6.448a8.88,8.88,0,0,1-.1-1.285q0-.63.045-1.336h-6.4Zm-4.94,9.175a4.1,4.1,0,0,1-3.022-1.212A4.1,4.1,0,0,1,160-797.05v-38.715a4.1,4.1,0,0,1,1.212-3.022A4.1,4.1,0,0,1,164.234-840H184.9l11.8,11.8v8q-.691-.192-1.321-.313a11.441,11.441,0,0,0-1.3-.171v-6.2H183.592v-10.485H164.234a1.542,1.542,0,0,0-1.109.5,1.542,1.542,0,0,0-.5,1.109v38.715a1.542,1.542,0,0,0,.5,1.109,1.542,1.542,0,0,0,1.109.5h13.717a14.446,14.446,0,0,0,.89,1.406,9.138,9.138,0,0,0,1.046,1.215Zm28.028-21.777a10.116,10.116,0,0,1,7.433,3.052,10.116,10.116,0,0,1,3.052,7.433,10.116,10.116,0,0,1-3.052,7.433,10.116,10.116,0,0,1-7.433,3.052,10.116,10.116,0,0,1-7.433-3.052,10.116,10.116,0,0,1-3.052-7.433,10.116,10.116,0,0,1,3.052-7.433A10.116,10.116,0,0,1,192.263-814.593Z"
                transform="translate(-160 840)"
                fill="#1447b2"
              />
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
        <div onClick={() => policies()} className="w-[18.8rem] h-60 p-6 mr-3 mb-4 pt-16 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
          <div className="flex justify-center mb-5">
            <svg xmlns="http://www.w3.org/2000/svg"
              width="45.695" height="40.618" viewBox="0 0 22.332 21.059">
              <g id="Group_58306" data-name="Group 58306" transform="translate(-594.275 -180)">
                <path id="Subtraction_1" data-name="Subtraction 1"
                  d="M13.9,19.594H2.261A2.213,2.213,0,0,1,0,17.333V2.261A2.213,2.213,0,0,1,2.261,0H17.333a2.212,2.212,0,0,1,2.261,2.261V9.372h-1.4V2.261a.825.825,0,0,0-.269-.592.83.83,0,0,0-.593-.269H2.261a.83.83,0,0,0-.593.269.826.826,0,0,0-.268.592V17.333a.829.829,0,0,0,.268.592.83.83,0,0,0,.593.269H13.9v1.4Zm-.854-6.754H4.9v-1.4h8.144v1.4Zm2.825-3.613H4.9v-1.4H15.868v1.4ZM12.7,5.474H4.9V3.924h7.8v1.55Z"
                  transform="translate(594.275 180)" fill="#1447b2" />
                <path id="encrypted_24dp_FILL0_wght300_GRAD0_opsz24"
                  d="M183.838-848.965a4.943,4.943,0,0,1-2.749-1.955A5.584,5.584,0,0,1,180-854.278v-2.945l2.074-.447,1.764-1.273,1.788,1.273,2.05.447v2.945a5.584,5.584,0,0,1-1.089,3.358A4.943,4.943,0,0,1,183.838-848.965Z"
                  transform="translate(428.93 1050.024)" fill="#1447b2" />
              </g>
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
              width="46.289" height="46.57" viewBox="0 0 16.289 20.57">
              <path id="encrypted_24dp_FILL0_wght300_GRAD0_opsz24"
                d="M186.777-845.2h2.735l-.614-3.429a1.845,1.845,0,0,0,.824-.688,1.838,1.838,0,0,0,.312-1.041,1.82,1.82,0,0,0-.554-1.335,1.82,1.82,0,0,0-1.335-.554,1.82,1.82,0,0,0-1.335.554,1.82,1.82,0,0,0-.554,1.335,1.838,1.838,0,0,0,.312,1.041,1.845,1.845,0,0,0,.824.688Zm1.368,7.309a10.489,10.489,0,0,1-5.834-4.147A11.848,11.848,0,0,1,180-849.163v-6.248l8.145-3.049,8.144,3.049v6.248a11.848,11.848,0,0,1-2.311,7.125A10.489,10.489,0,0,1,188.145-837.89Zm0-1.717a8.837,8.837,0,0,0,4.669-3.584,10.314,10.314,0,0,0,1.846-5.973v-5.131l-6.516-2.433-6.516,2.433v5.131a10.314,10.314,0,0,0,1.846,5.973A8.837,8.837,0,0,0,188.145-839.607ZM188.145-848.175Z"
                transform="translate(-180 858.46)"
                fill="#1447b2" />
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
          <p>2024 © MOSIP - {t('dashboard.mosipRights')}</p>
        </div>
        <div className="flex justify-between">
          <p className="mr-7">{t('dashboard.documentation')}</p>
          <p className="mr-7">{t('dashboard.mosipCommunity')}</p>
          <p className="mr-7">{t('dashboard.contactUs')}</p>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;