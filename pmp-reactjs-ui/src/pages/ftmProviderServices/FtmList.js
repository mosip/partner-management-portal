import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import { HttpService } from '../../services/HttpService';
import {
  isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
  handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, bgOfStatus,
  onPressEnterKey, createRequest, populateDeactivatedStatus
} from '../../utils/AppUtils';
import ErrorMessage from '../common/ErrorMessage';
import Title from '../common/Title';
import LoadingIcon from '../common/LoadingIcon';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import FilterButtons from '../common/FilterButtons';
import FtmListFilter from './FtmListFilter';
import SortingIcon from '../common/SortingIcon';
import Pagination from '../common/Pagination';
import DeactivatePopup from '../common/DeactivatePopup';

function FtmList() {
  const navigate = useNavigate('');
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [filter, setFilter] = useState(false);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("createdDateTime");
  const [activeSortDesc, setActiveSortDesc] = useState("");
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
        const response = await HttpService.get(getPartnerManagerUrl('/partners/ftm-chip-details', process.env.NODE_ENV));
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
    { id: "partnerId", headerNameKey: 'ftmList.partnerId' },
    { id: "make", headerNameKey: "ftmList.make" },
    { id: "model", headerNameKey: "ftmList.model" },
    { id: "createdDateTime", headerNameKey: "ftmList.createdDate" },
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
    navigate('/partnermanagement/ftmChipProviderServices/addFtm');
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
      navigate('/partnermanagement/ftmChipProviderServices/viewFtmChipDetails');
    }
  }

  const viewFtmDetails = (selectedFtmData) => {
    selectedFtmData = {
      ...selectedFtmData,
      title: 'viewFtmChipDetails.viewFtmChipDetails',
      isViewFtmChipDetails: true
    }
    localStorage.setItem('selectedFtmData', JSON.stringify(selectedFtmData));
    navigate('/partnermanagement/ftmChipProviderServices/viewFtmChipDetails');
  }

  const showDeactivateFtm = (selectedFtmData) => {
    if (selectedFtmData.status === "approved") {
      const request = createRequest({
        ftmId: selectedFtmData.ftmId,
      }, "mosip.pms.deactivate.ftm.post", true);
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
      navigate('/partnermanagement/ftmChipProviderServices/manageFtmChipCertificate');
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
                <button onClick={() => addFtm()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                  {t('ftmList.addFtmBtn')}
                </button>
              )}
            </div>
            {ftmList.length === 0 ?
              <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg">
                <div className="flex justify-between py-2 px-2 pt-4 text-sm font-semibold text-[#6F6E6E] overflow-x-scroll no-scrollbar">
                  <div className={`flex w-full justify-between`}>
                    <h6 className="px-2 mx-2">{t('ftmList.partnerId')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.make')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.model')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.createdDate')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.certificateUploadDate')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.certificateExpiryDate')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.certExpiryStatus')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.status')}</h6>
                    <h6 className="px-2 mx-2 text-center">{t('ftmList.action')}</h6>
                  </div>
                </div>
                <hr className="h-px mx-3 bg-gray-200 border-0" />
                <div className="flex items-center justify-center p-24">
                  <div className="flex flex-col justify-center">
                    <img src={rectangleGrid} alt="" />
                    <button onClick={() => addFtm()} type="button"
                      className={`font-semibold mt-8 rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                      {t('ftmList.addFtmBtn')}
                    </button>
                  </div>
                </div>
              </div>
              :
              <>
                <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg">
                  <FilterButtons listTitle='ftmList.listOfFtm' dataList={filteredftmList} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
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
                          {tableHeaders.map((header, index) => {
                            return (
                              <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] w-[12%]`}>
                                <div className={`flex items-center gap-x-1 font-semibold ${header.id === "action" && 'justify-center'}`}>
                                  {t(header.headerNameKey)}
                                  {(header.id !== "action") && (
                                    <SortingIcon headerId={header.id} sortDescOrder={sortDescOrder} sortAscOrder={sortAscOrder} order={order} activeSortDesc={activeSortDesc} activeSortAsc={activeSortAsc}></SortingIcon>
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
                              <tr key={index} className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${(ftm.status === "deactivated") ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.partnerId}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.make}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.model}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{formatDate(ftm.createdDateTime, 'date', false)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{formatDate(ftm.certificateUploadDateTime, 'dateTime', false)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`px-2 mx-2 ${(ftm.isCertificateExpired && ftm.status !== "deactivated") && 'text-crimson-red font-bold'}`}>{formatDate(ftm.certificateExpiryDateTime, 'dateTime', false)}</td>
                                <td onClick={() => showFtmDetails(ftm)} className={`${isLoginLanguageRTL ? "pr-8 pl-4" : "pl-8 pr-4"} mx-2`}>{ftm.certificateExpiryStatus}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">
                                  <div className={`${bgOfStatus(ftm.status)} flex w-fit py-1.5 px-2 my-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(ftm.status, t)}
                                  </div>
                                </td>
                                <td className="px-2 mx-2">
                                  <div className="flex items-center justify-center relative" ref={el => submenuRef.current[index] = el}>
                                    <p onClick={() => setViewFtmId(index === viewFtmId ? null : index)} className="font-semibold mb-0.5 cursor-pointer text-[#1447B2]"
                                      tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewFtmId(index === viewFtmId ? null : index))}>
                                      ...</p>
                                    {viewFtmId === index && (
                                      <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-5'} z-50 bg-white text-xs text-start font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-6 text-right" : "right-6 text-left"}`}>
                                        <p onClick={() => viewFtmDetails(ftm)} className={`py-1 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewFtmDetails(ftm))}>
                                          {t('ftmList.view')}
                                        </p>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <p onClick={() => showManageCertificate(ftm)} className={`py-1 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${(ftm.status === "approved" || ftm.status === "pending_cert_upload") ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showManageCertificate(ftm))}>
                                          {t('ftmList.manageCertificate')}
                                        </p>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <p onClick={() => showDeactivateFtm(ftm)} className={`py-1 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${ftm.status === "approved" ? 'text-crimson-red cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivateFtm(ftm))}>
                                          {t('ftmList.deActivate')}
                                        </p>
                                        {showDeactivatePopup && (
                                          <DeactivatePopup closePopUp={closeDeactivatePopup} popupData={{ ...ftm, isDeactivateFtm: true }} request={deactivateRequest} headerMsg='deactivateFtmPopup.headerMsg' descriptionMsg='deactivateFtmPopup.description' />
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
                <Pagination dataList={filteredftmList} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
              </>
            }
          </div>
        </>
      )}
    </div>
  )
}

export default FtmList;