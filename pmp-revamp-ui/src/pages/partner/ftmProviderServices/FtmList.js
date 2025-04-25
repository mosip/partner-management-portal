import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { HttpService } from '../../../services/HttpService';
import {
  isLangRTL, getPartnerManagerUrl, formatDate, getStatusCode,
  handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, bgOfStatus,
  createRequest, populateDeactivatedStatus, setSubmenuRef,
  handleServiceErrors,
  isCaSignedPartnerCertificateAvailable
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import Title from '../../common/Title';
import LoadingIcon from '../../common/LoadingIcon';
import FilterButtons from '../../common/FilterButtons';
import FtmListFilter from './FtmListFilter';
import SortingIcon from '../../common/SortingIcon';
import Pagination from '../../common/Pagination';
import viewIcon from "../../../svg/view_icon.svg";
import manageCertificate from '../../../svg/manage_certificate_icon.svg';
import disableManageCertificate from '../../../svg/disabled_manage_certificate_icon.svg';
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import DeactivatePopup from '../../common/DeactivatePopup';
import EmptyList from '../../common/EmptyList';

function FtmList() {
  const navigate = useNavigate('');
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [filter, setFilter] = useState(false);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
  const [order, setOrder] = useState("DESC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("createdDateTime");
  const [isDescending, setIsDescending] = useState(false);
  const [firstIndex, setFirstIndex] = useState(0);
  const [ftmList, setFtmList] = useState([]);
  const [filteredftmList, setFilteredFtmList] = useState([]);
  const [viewFtmId, setViewFtmId] = useState(-1);
  const [selectedFtm, setSelectedFtm] = useState({});
  const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
  const [deactivateRequest, setDeactivateRequest] = useState({});
  const defaultFilterQuery = {
    partnerId: "",
    make: ""
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);
  const [downloadCertApiNotExist, setDownloadCertApiNotExist] = useState(false);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewFtmId(-1));
  }, [submenuRef]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const isApiExist = await isCaSignedPartnerCertificateAvailable();
        if (!isApiExist) {
          setDownloadCertApiNotExist(true);
        }
        setDataLoaded(false);
        const response = await HttpService.get(getPartnerManagerUrl('/ftpchipdetail', process.env.NODE_ENV));
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            const resData = responseData.response;
            let populatedData = populateCertificateExpiryStatus(resData);
            populatedData = populateDeactivatedStatus(populatedData, "status", "isActive");
            const sortedData = populatedData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
            setFtmList(sortedData);
            setFilteredFtmList(sortedData);
          } else {
            if (responseData.errors && responseData.errors.length > 0) {
              const errorCode = response.data.errors[0].errorCode;
              if (errorCode === 'PMS_KKS_001') {
                  setErrorMsg(t('ftmList.errorWhileFetchingFtmList'));
              } else {
                  handleServiceErrors(responseData, setErrorCode, setErrorMsg);
              }
            }
          }
        } else {
          setErrorMsg(t('ftmList.errorInFtmList'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
        }
      }
    };
    fetchData();
  }, []);

  const populateCertificateExpiryStatus = (data) => {
    // Updating the status based on the condition
    const updatedData = data.map(item => {
      if (item['isCertificateAvailable']) {
        if (item['isCertificateExpired'] === null) {
          return { ...item, certificateExpiryStatus: 'not_available' };
        } else if (item['isCertificateExpired']) {
          return { ...item, certificateExpiryStatus: 'expired' };
        } else {
          return { ...item, certificateExpiryStatus: 'valid' };
        }
      } else {
        return { ...item, certificateExpiryStatus: '-' };
      }
    });
    return updatedData;
  };

  const tableHeaders = [
    { id: "ftmId", headerNameKey: 'ftmList.ftmId' },
    { id: "partnerId", headerNameKey: 'ftmList.partnerId' },
    { id: "make", headerNameKey: "ftmList.make" },
    { id: "model", headerNameKey: "ftmList.model" },
    { id: "createdDateTime", headerNameKey: "ftmList.creationDate" },
    { id: "certificateUploadDateTime", headerNameKey: "ftmList.certificateUploadDate" },
    { id: "certificateExpiryDateTime", headerNameKey: "ftmList.certificateExpiryDate" },
    { id: "certificateExpiryStatus", headerNameKey: "ftmList.certExpiryStatus" },
    { id: "status", headerNameKey: "ftmList.status" },
    { id: "action", headerNameKey: 'ftmList.action' }
  ];

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const addFtm = () => {
    navigate('/partnermanagement/ftm-chip-provider-services/add-ftm');
  };

  //This part is related to Filter
  const onFilterChange = (fieldName, selectedFilter) => {
    setFilterQuery(oldFilterQuery => ({
      ...oldFilterQuery,
      [fieldName]: selectedFilter
    }));
  }
  useEffect(() => {
    let filteredRows = ftmList;
    Object.keys(filterQuery).forEach(key => {
      if (filterQuery[key] !== '') {
        filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
      }
    });
    setFilteredFtmList(filteredRows);
    setFirstIndex(0);
  }, [filterQuery, ftmList]);

  const onResetFilter = () => {
    window.location.reload();
  }

  const sortAscOrder = (header) => {
    const isDateCol = (header === "createdDateTime" || header === "certificateUploadDateTime" || header === "certificateExpiryDateTime");
    toggleSortAscOrder(header, isDateCol, filteredftmList, setFilteredFtmList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  const sortDescOrder = (header) => {
    const isDateCol = (header === "createdDateTime" || header === "certificateUploadDateTime" || header === "certificateExpiryDateTime");
    toggleSortDescOrder(header, isDateCol, filteredftmList, setFilteredFtmList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  //This part related to Pagination Logic
  let tableRows = filteredftmList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

  const showFtmDetails = (selectedFtmData) => {
    if (selectedFtmData.status !== "deactivated") {
      selectedFtmData = {
        ...selectedFtmData,
        title: 'viewFtmChipDetails.viewFtmChipDetails',
        isViewFtmChipDetails: true
      }
      localStorage.setItem('selectedFtmData', JSON.stringify(selectedFtmData));
      navigate('/partnermanagement/ftm-chip-provider-services/view-ftm-chip-details');
    }
  }

  const viewFtmDetails = (selectedFtmData) => {
    selectedFtmData = {
      ...selectedFtmData,
      title: 'viewFtmChipDetails.viewFtmChipDetails',
      isViewFtmChipDetails: true
    }
    localStorage.setItem('selectedFtmData', JSON.stringify(selectedFtmData));
    navigate('/partnermanagement/ftm-chip-provider-services/view-ftm-chip-details');
  }

  const showDeactivateFtm = (selectedFtmData, index) => {
    if (selectedFtmData.status === "approved") {
      const request = createRequest({
        status: "De-Activate",
      }, "mosip.pms.deactivate.ftm.patch", true);
      setViewFtmId(-1);
      setSelectedFtm(selectedFtmData);
      setDeactivateRequest(request);
      setShowActiveIndexDeactivatePopup(index);
    }
  };

  const closeDeactivatePopup = () => {
    setSelectedFtm({});
    setShowActiveIndexDeactivatePopup(null);
  };

  const showManageCertificate = (selectedFtmData) => {
    if (selectedFtmData.status === "approved" || selectedFtmData.status === "pending_cert_upload") {
      selectedFtmData = {
        ...selectedFtmData,
        title: 'manageFtmChipCertificate.manageFtmChipCertificate',
        isManageFtmCertificate: true
      }
      localStorage.setItem('selectedFtmData', JSON.stringify(selectedFtmData));
      navigate('/partnermanagement/ftm-chip-provider-services/manage-ftm-chip-certificate');
    }
  };

  const onClickConfirmDeactivate = (deactivationResponse, selectedFtm) => {
    if (deactivationResponse && !deactivationResponse.isActive) {
      setSelectedFtm({});
      setShowActiveIndexDeactivatePopup(null);
      // Update the specific row in the state with the new status
      setFilteredFtmList((prevList) =>
        prevList.map(ftm =>
          ftm.ftmId === selectedFtm.ftmId ? { ...ftm, status: "deactivated", isActive: false } : ftm
        )
      );
    }
  };

  const getCertificateExpiryStatus = (ftm) => {
    if (downloadCertApiNotExist && ftm.status !== "pending_cert_upload") {
      return t('statusCodes.notAvailable');
    } else {
      if (ftm.status !== 'pending_cert_upload') {
        return ftm.isCertificateExpired ? t('statusCodes.expired') : t('statusCodes.valid');
      } else {
        return '-';
      }
    }  
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between mb-5">
              <Title title='ftmList.listOfFtm' backLink='/partnermanagement' />
              {ftmList.length > 0 && (
                <button id='add_ftm_chip_btn' onClick={() => addFtm()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                  {t('ftmList.addFtmBtn')}
                </button>
              )}
            </div>
            {downloadCertApiNotExist && (
              <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                <div className="flex items-center justify-center p-2">
                  <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                    <p className="text-sm font-medium text-[#8B6105]">{t('ftmList.compatibilityMsg')}</p>
                  </div>
                </div>
              </div>
            )}
            {ftmList.length === 0 ?
              <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg">
                <EmptyList
                  tableHeaders={tableHeaders}
                  showCustomButton={true}
                  customButtonName='ftmList.addFtmBtn'
                  buttonId='add_ftm'
                  onClickButton={addFtm}
                />
              </div>
              :
              <>
                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                  <FilterButtons titleId='list_of_ftm' listTitle='ftmList.listOfFtm' dataListLength={filteredftmList.length} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                  <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                  {filter &&
                    <FtmListFilter
                      filteredFtmList={ftmList}
                      onFilterChange={onFilterChange}>
                    </FtmListFilter>
                  }
                  <div className="mx-[1.5rem] overflow-x-scroll">
                    <table className="table-fixed">
                      <thead>
                        <tr>
                          {tableHeaders.map((header, index) => {
                            return (
                              <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] w-[12%]`}>
                                <div id={`${header.headerNameKey}_header`} className={`flex items-center gap-x-1 font-semibold ${header.id === "action" && 'justify-center'}`}>
                                  {t(header.headerNameKey)}
                                  {(header.id !== "action") && (
                                    <SortingIcon
                                      id={`${header.headerNameKey}_sorting_icon`}
                                      headerId={header.id}
                                      sortDescOrder={sortDescOrder}
                                      sortAscOrder={sortAscOrder}
                                      order={order}
                                      activeSortDesc={activeSortDesc}
                                      activeSortAsc={activeSortAsc}
                                    />
                                  )}
                                </div>
                              </th>
                            )
                          })}
                        </tr>
                      </thead>
                      <tbody>
                        {
                          tableRows.map((ftm, index, currentArray) => {
                            return (
                              <tr id={'ftm_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${(ftm.status === "deactivated") ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.ftmId}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.partnerId}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.make}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.model}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`px-2 mx-2 max-1350:px-4  ${isLoginLanguageRTL ? "max-1350:text-right" : "max-1355:pl-7 max-1200:pl-5"}`}>{formatDate(ftm.createdDateTime, 'date')}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2 max-1530:text-center max-1530:px-4">{formatDate(ftm.certificateUploadDateTime, 'dateTime')}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`px-2 mx-2 max-1712:text-center max-1712:px-4 ${(ftm.isCertificateExpired && ftm.status !== "deactivated") && 'text-crimson-red font-bold'}`}>{(downloadCertApiNotExist && ftm.status !== "pending_cert_upload") ? t('statusCodes.notAvailable') : formatDate(ftm.certificateExpiryDateTime, 'dateTime')}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`${isLoginLanguageRTL ? "pr-8 pl-4" : "pl-8 pr-4"} mx-2`}>{getCertificateExpiryStatus(ftm)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">
                                  <div className={`${bgOfStatus(ftm.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(ftm.status, t)}
                                  </div>
                                </td>
                                <td className="px-2 mx-2 cursor-default">
                                  <div className="flex items-center justify-center relative" ref={setSubmenuRef(submenuRef, index)}>
                                    <button id={'ftm_list_action' + (index + 1)} onClick={() => setViewFtmId(index === viewFtmId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]">
                                      ...
                                    </button>
                                    {viewFtmId === index && (
                                      <div className={`absolute w-[7rem] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-5'} z-50 bg-white text-xs text-start font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-[0.7rem] text-right" : "right-[0.7rem] text-left"}`}>
                                        <div role='button' id='ftm_list_view' onClick={() => viewFtmDetails(ftm)} className={`flex justify-between py-2 w-full px-2 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                          <p>{t('ftmList.view')}</p>
                                          <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                        </div>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <div role='button' id='ftm_list_manage_certificate' onClick={() => showManageCertificate(ftm)} className={`flex justify-between py-2 w-full px-2 ${isLoginLanguageRTL ? "text-right" : "text-left"} ${(ftm.status === "approved" || ftm.status === "pending_cert_upload") ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`}>
                                          <p> {t('ftmList.manageCertificate')} </p>
                                          <img src={(ftm.status === "approved" || ftm.status === "pending_cert_upload") ? manageCertificate : disableManageCertificate} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                        </div>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <div role='button' id='ftm_list_deactivate' onClick={() => showDeactivateFtm(ftm, index)} className={`flex justify-between py-2 px-2 ${ftm.status === "approved" ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} >
                                          <p> {t('ftmList.deActivate')}</p>
                                          <img src={ftm.status === "approved" ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                        </div>
                                      </div>
                                    )}
                                    {showActiveIndexDeactivatePopup === index && (
                                      <DeactivatePopup
                                        closePopUp={closeDeactivatePopup}
                                        onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedFtm)}
                                        popupData={{ ...selectedFtm, isDeactivateFtm: true }}
                                        request={deactivateRequest}
                                        headerMsg='deactivateFtmPopup.headerMsg'
                                        descriptionMsg='deactivateFtmPopup.description'
                                      />
                                    )}
                                  </div>
                                </td>
                              </tr>
                            )
                          })
                        }
                      </tbody>
                    </table>
                  </div>
                </div>
                <Pagination dataListLength={filteredftmList.length} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
              </>
            }
          </div>
        </>
      )}
    </div>
  )
}

export default FtmList;