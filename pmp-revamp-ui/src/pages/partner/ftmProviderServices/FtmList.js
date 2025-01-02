import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { HttpService } from '../../../services/HttpService';
import {
  isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
  handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, bgOfStatus,
  onPressEnterKey, createRequest, populateDeactivatedStatus
} from '../../../utils/AppUtils';
import ErrorMessage from '../../common/ErrorMessage';
import Title from '../../common/Title';
import LoadingIcon from '../../common/LoadingIcon';
import FilterButtons from '../../common/FilterButtons';
import FtmListFilter from './FtmListFilter';
import SortingIcon from '../../common/SortingIcon';
import Pagination from '../../common/Pagination';
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
  const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
  const [deactivateRequest, setDeactivateRequest] = useState({});
  const defaultFilterQuery = {
    partnerId: "",
    make: ""
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewFtmId(-1));
  }, [submenuRef]);

  useEffect(() => {
    const fetchData = async () => {
      try {
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
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('ftmList.errorInFtmList'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        setErrorMsg(err);
      }
    };
    fetchData();
  }, []);

  const populateCertificateExpiryStatus = (data) => {
    // Updating the status based on the condition
    const updatedData = data.map(item => {
      if (item['isCertificateExpired'] === true) {
        return { ...item, certificateExpiryStatus: 'expired' };
      } else {
        return { ...item, certificateExpiryStatus: '-' };
      }
    });
    return updatedData;
  };

  const tableHeaders = [
    { id: 1, headerNameKey: 'ftmList.partnerId' },
    { id: 2, headerNameKey: "ftmList.make" },
    { id: 3, headerNameKey: "ftmList.model" },
    { id: 4, headerNameKey: "ftmList.createdDate" },
    { id: 5, headerNameKey: "ftmList.certificateUploadDate" },
    { id: 6, headerNameKey: "ftmList.certificateExpiryDate" },
    { id: 7, headerNameKey: "ftmList.certExpiryStatus" },
    { id: 8, headerNameKey: "ftmList.status" },
    { id: 9, headerNameKey: 'ftmList.action' }
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

  const showDeactivateFtm = (selectedFtmData) => {
    if (selectedFtmData.status === "approved") {
      const request = createRequest({
        status: "De-Activate",
      }, "mosip.pms.deactivate.ftm.patch", true);
      setDeactivateRequest(request);
      setShowDeactivatePopup(true);
      document.body.style.overflow = "hidden";
    }
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
        setViewFtmId(-1);
        setShowDeactivatePopup(false);
        // Update the specific row in the state with the new status
        setFtmList((prevList) =>
            prevList.map(ftm =>
                ftm.ftmId === selectedFtm.ftmId ? { ...ftm, status: "deactivated", isActive: false } : ftm
            )
        );
    }
  };

  const closeDeactivatePopup = () => {
    setViewFtmId(-1);
    setShowDeactivatePopup(false);
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
          <div className="flex-col mt-7">
            <div className="flex justify-between mb-5">
              <Title title='ftmList.listOfFtm' backLink='/partnermanagement' />
              {ftmList.length > 0 && (
                <button id='add_ftm_chip_btn' onClick={() => addFtm()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                  {t('ftmList.addFtmBtn')}
                </button>
              )}
            </div>
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
                  <div className="mx-[2%] overflow-x-scroll">
                    <table className="table-fixed">
                      <thead>
                        <tr>
                          {tableHeaders.map((header) => {
                            return (
                              <th key={header.id} className={`py-4 px-2 text-xs text-[#6F6E6E] w-[12%]`}>
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
                              <tr id={'ftm_list_item' + (index + 1)} key={ftm.ftmId} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${(ftm.status === "deactivated") ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.partnerId}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.make}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.model}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`px-2 mx-2 max-1350:px-4  ${isLoginLanguageRTL ? "max-1350:text-right" : "max-1355:pl-7 max-1200:pl-5"}`}>{formatDate(ftm.createdDateTime, 'date', true)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2 max-1530:text-center max-1530:px-4">{formatDate(ftm.certificateUploadDateTime, 'dateTime', false)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`px-2 mx-2 max-1712:text-center max-1712:px-4 ${(ftm.isCertificateExpired && ftm.status !== "deactivated") && 'text-crimson-red font-bold'}`}>{formatDate(ftm.certificateExpiryDateTime, 'dateTime', false)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`${isLoginLanguageRTL ? "pr-8 pl-4" : "pl-8 pr-4"} mx-2`}>{ftm.certificateExpiryStatus}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">
                                  <div className={`${bgOfStatus(ftm.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(ftm.status, t)}
                                  </div>
                                </td>
                                <td className="px-2 mx-2">
                                  <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                    <p id={'ftm_list_action' + (index + 1)} onClick={() => setViewFtmId(index === viewFtmId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]"
                                      tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => setViewFtmId(index === viewFtmId ? null : index))}>
                                      ...</p>
                                    {viewFtmId === index && (
                                      <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-5'} z-50 bg-white text-xs text-start font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-6 text-right" : "right-6 text-left"}`}>
                                        <p id='ftm_list_view' onClick={() => viewFtmDetails(ftm)} className={`py-1 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewFtmDetails(ftm))}>
                                          {t('ftmList.view')}
                                        </p>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <p id='ftm_list_manage_certificate' onClick={() => showManageCertificate(ftm)} className={`py-1 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${(ftm.status === "approved" || ftm.status === "pending_cert_upload") ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showManageCertificate(ftm))}>
                                          {t('ftmList.manageCertificate')}
                                        </p>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <p id='ftm_list_deactivate' onClick={() => showDeactivateFtm(ftm)} className={`py-1 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${ftm.status === "approved" ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showDeactivateFtm(ftm))}>
                                          {t('ftmList.deActivate')}
                                        </p>
                                        {showDeactivatePopup && (
                                          <DeactivatePopup closePopUp={closeDeactivatePopup} onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, ftm)} popupData={{ ...ftm, isDeactivateFtm: true }} request={deactivateRequest} headerMsg='deactivateFtmPopup.headerMsg' descriptionMsg='deactivateFtmPopup.description' />
                                        )}
                                      </div>
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