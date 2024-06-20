import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService.js';
import { isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode, handleMouseClickForDropdown } from '../../utils/AppUtils.js';
import { HttpService } from '../../services/HttpService.js';
import LoadingIcon from "../common/LoadingIcon.js";
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai"; // icons form react-icons
import { IconContext } from "react-icons"; // for customizing icons
import rectangleGrid from '../../svg/rectangle_grid.svg';
import ReactPaginate from 'react-paginate';
import CopyIdPopUp from './CopyIdPopUp.js';
import OidcClientsFilter from './OidcClientsFilter.js';
import DeactivateOidcClient from './DeactivateOidcClient.js';

function ApiKeysList({ activeApiKey }) {
  const navigate = useNavigate('');
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [filter, setFilter] = useState(false);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [isDescending, setIsDescending] = useState(true);
  const [showPopup, setShowPopup] = useState(false);
  const [firstIndex, setFirstIndex] = useState(0);
  const itemsPerPageOptions = [8, 16, 24, 32];
  const [apiKeysList, setapiKeysList] = useState([]);
  const [filteredApiKeysList, setFilteredapiKeysList] = useState([]);
  const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
  const [currentClient, setCurrentClient] = useState(null);
  const [viewClientId, setViewClientId] = useState(-1);
  const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
  const defaultFilterQuery = {
    partnerId: "",
    policyGroupName: ""
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef(null);
  const itemsCountSelectionRef = useRef(null);

  const tableValues = [
    { "partnerId": "P28394091", "policyGroup": "Policy Group 01", "policyName": "Full KYC", "oidcClientName": "Client 13", "createdDate": "11/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394092", "policyGroup": "Policy Group 02", "policyName": "KYC", "oidcClientName": "Client 22", "createdDate": "21/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    { "partnerId": "P28394093", "policyGroup": "Policy Group 03", "policyName": "KYC1", "oidcClientName": "Client 11", "createdDate": "06/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    { "partnerId": "P28394094", "policyGroup": "Policy Group 04", "policyName": "Full KYC", "oidcClientName": "Client 03", "createdDate": "30/09/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394095", "policyGroup": "Policy Group 05", "policyName": "KYC1", "oidcClientName": "Client 05", "createdDate": "12/10/2025", "status": "Rejected", "oidcClientId": "0" },
    { "partnerId": "P28394096", "policyGroup": "Policy Group 06", "policyName": "KYC", "oidcClientName": "Client 16", "createdDate": "07/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    { "partnerId": "P28394097", "policyGroup": "Policy Group 07", "policyName": "Full KYC", "oidcClientName": "Client 07", "createdDate": "01/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    { "partnerId": "P28394098", "policyGroup": "Policy Group 08", "policyName": "KYC", "oidcClientName": "Client 04", "createdDate": "17/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394099", "policyGroup": "Policy Group 09", "policyName": "KYC1", "oidcClientName": "Client 12", "createdDate": "13/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    { "partnerId": "P28394100", "policyGroup": "Policy Group 10", "policyName": "KYC", "oidcClientName": "Client 09", "createdDate": "02/10/2025", "status": "Rejected", "oidcClientId": "0" },
    { "partnerId": "P28394101", "policyGroup": "Policy Group 11", "policyName": "Full KYC", "oidcClientName": "Client 02", "createdDate": "08/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394102", "policyGroup": "Policy Group 12", "policyName": "KYC", "oidcClientName": "Client 06", "createdDate": "18/10/2025", "status": "Deactivated", "oidcClientId": "0" },
    { "partnerId": "P28394103", "policyGroup": "Policy Group 13", "policyName": "KYC", "oidcClientName": "Client 01", "createdDate": "14/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    { "partnerId": "P28394104", "policyGroup": "Policy Group 14", "policyName": "Full KYC", "oidcClientName": "Client 10", "createdDate": "03/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394105", "policyGroup": "Policy Group 15", "policyName": "KYC1", "oidcClientName": "Client 08", "createdDate": "09/10/2025", "status": "Rejected", "oidcClientId": "0" },
    { "partnerId": "P28394106", "policyGroup": "Policy Group 16", "policyName": "Full KYC", "oidcClientName": "Client 20", "createdDate": "19/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394107", "policyGroup": "Policy Group 17", "policyName": "KYC", "oidcClientName": "Client 17", "createdDate": "04/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394108", "policyGroup": "Policy Group 18", "policyName": "Full KYC", "oidcClientName": "Client 14", "createdDate": "15/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    { "partnerId": "P28394109", "policyGroup": "Policy Group 19", "policyName": "KYC", "oidcClientName": "Client 19", "createdDate": "10/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394110", "policyGroup": "Policy Group 20", "policyName": "Full KYC", "oidcClientName": "Client 18", "createdDate": "20/10/2025", "status": "Approved", "oidcClientId": "1" },
    { "partnerId": "P28394111", "policyGroup": "Policy Group 21", "policyName": "KYC1", "oidcClientName": "Client 21", "createdDate": "05/10/2025", "status": "Pending for Approval", "oidcClientId": "0" },
    { "partnerId": "P28394112", "policyGroup": "Policy Group 22", "policyName": "Full KYC", "oidcClientName": "Client 15", "createdDate": "16/10/2025", "status": "Rejected", "oidcClientId": "0" }

  ];

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewClientId(null));
    handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
  }, [submenuRef, itemsCountSelectionRef]);

  useEffect(() => {
    const resData = tableValues;
    const sortedData = resData.sort((a, b) => new Date(b.crDtimes) - new Date(a.crDtimes));
    setapiKeysList(sortedData);
    setFilteredapiKeysList(apiKeysList)
  }, []);

  const tableHeaders = [
    { id: "partnerId", headerNameKey: 'apiKeysList.partnerId' },
    { id: "policyName", headerNameKey: "apiKeysList.policyName" },
    { id: "apiKeylabel", headerNameKey: "apiKeysList.apiKeylabel" },
    { id: "expiryDate", headerNameKey: "apiKeysList.expiryDate" },
    { id: "status", headerNameKey: "apiKeysList.status" },
    { id: "action", headerNameKey: 'apiKeysList.action' }
  ];

  const gennerateApiKey = () => {
    navigate('/partnermanagement/generateApiKey')
  }

  return (
    <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center overflow-x-scroll">
      {
        activeApiKey && (
          <div className="flex justify-between py-2 pt-4 text-sm font-medium text-[#6F6E6E]">
            <div className={`flex sm:gap-x-3 md:gap-x-8 lg:gap-x-16 xl:gap-x-24`}>
              <h6 className="ml-5">{t('apiKeysList.partnerId')}</h6>
              <h6>{t('apiKeysList.policyName')}</h6>
              <h6>{t('apiKeysList.apiKeylabel')}</h6>
              <h6>{t('apiKeysList.expiryDate')}</h6>
              <h6>{t('apiKeysList.status')}</h6>
              <h6 className="mr-5">{t('apiKeysList.action')}</h6>
            </div>
          </div>)
      }

      <hr className="h-px mx-3 bg-gray-200 border-0" />

      <div className="flex items-center justify-center p-24">
        <div className="flex flex-col justify-center">
          <img src={rectangleGrid} alt="" />
          {activeApiKey &&
            (<button onClick={() => gennerateApiKey()} type="button"
              className={`text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm mx-8 py-3`}>
              {t('authenticationServices.generateApiKeyBtn')}
            </button>)
          }
        </div>
      </div>
    </div>
  )
}

export default ApiKeysList;