import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
  isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
  handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
  onPressEnterKey
} from '../../utils/AppUtils';
import ErrorMessage from '../common/ErrorMessage';
import Title from '../common/Title';
import LoadingIcon from '../common/LoadingIcon';
import rectangleGrid from '../../svg/rectangle_grid.svg';
import FilterButtons from '../common/FilterButtons';
import FtmListFilter from './FtmListFilter';
import SortingIcon from '../common/SortingIcon';
import Pagination from '../common/Pagination';

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
  const [activeSortAsc, setActiveSortAsc] = useState("crDtimes");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [isDescending, setIsDescending] = useState(false);
  const [firstIndex, setFirstIndex] = useState(0);
  const [ftmList, setFtmList] = useState([]);
  const [filteredftmList, setFilteredFtmList] = useState([]);
  const [viewFtmId, setViewFtmId] = useState(-1);
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
    const tablevalues = [
      { "partnerId": "P28394091", "make": "Make 01", "model": "Model 01", "crDtimes": "11/10/2025", "certificateExpiryDate": "11/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394092", "make": "Make 02", "model": "Model 02", "crDtimes": "11/10/2025", "certificateExpiryDate": "10/10/2025", "certificateUploadDate": "11/10/2024", "status": "deactivated"},
      { "partnerId": "P28394093", "make": "Make 03", "model": "Model 03", "crDtimes": "06/10/2025", "certificateExpiryDate": "09/10/2025", "certificateUploadDate": "11/10/2024", "status": "pending_approval"},
      { "partnerId": "P28394094", "make": "Make 04", "model": "Model 04", "crDtimes": "11/10/2025", "certificateExpiryDate": "12/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394095", "make": "Make 05", "model": "Model 05", "crDtimes": "12/10/2025", "certificateExpiryDate": "13/10/2025", "certificateUploadDate": "11/10/2024", "status": "rejected"},
      { "partnerId": "P28394096", "make": "Make 06", "model": "Model 06", "crDtimes": "07/10/2025", "certificateExpiryDate": "14/10/2025", "certificateUploadDate": "11/10/2024", "status": "deactivated"},
      { "partnerId": "P28394097", "make": "Make 07", "model": "Model 07", "crDtimes": "01/10/2025", "certificateExpiryDate": "15/10/2025", "certificateUploadDate": "11/10/2024", "status": "pending_approval"},
      { "partnerId": "P28394098", "make": "Make 08", "model": "Model 08", "crDtimes": "11/10/2025", "certificateExpiryDate": "16/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394099", "make": "Make 09", "model": "Model 09", "crDtimes": "11/10/2025", "certificateExpiryDate": "17/10/2025", "certificateUploadDate": "11/10/2024", "status": "deactivated"},
      { "partnerId": "P28394100", "make": "Make 10", "model": "Model 10", "crDtimes": "02/10/2025", "certificateExpiryDate": "18/10/2025", "certificateUploadDate": "11/10/2024", "status": "rejected"},
      { "partnerId": "P28394101", "make": "Make 11", "model": "Model 11", "crDtimes": "08/10/2025", "certificateExpiryDate": "19/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394102", "make": "Make 12", "model": "Model 12", "crDtimes": "12/10/2025", "certificateExpiryDate": "11/10/2025", "certificateUploadDate": "11/10/2024", "status": "deactivated"},
      { "partnerId": "P28394103", "make": "Make 13", "model": "Model 13", "crDtimes": "09/10/2025", "certificateExpiryDate": "11/10/2025", "certificateUploadDate": "11/10/2024", "status": "pending_approval"},
      { "partnerId": "P28394104", "make": "Make 14", "model": "Model 14", "crDtimes": "03/10/2025", "certificateExpiryDate": "01/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394105", "make": "Make 15", "model": "Model 15", "crDtimes": "09/10/2025", "certificateExpiryDate": "02/10/2025", "certificateUploadDate": "11/10/2024", "status": "rejected"},
      { "partnerId": "P28394106", "make": "Make 16", "model": "Model 16", "crDtimes": "12/10/2025", "certificateExpiryDate": "03/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394107", "make": "Make 17", "model": "Model 17", "crDtimes": "04/10/2025", "certificateExpiryDate": "04/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394108", "make": "Make 18", "model": "Model 18", "crDtimes": "11/10/2025", "certificateExpiryDate": "05/10/2025", "certificateUploadDate": "11/10/2024", "status": "pending_approval"},
      { "partnerId": "P28394109", "make": "Make 19", "model": "Model 19", "crDtimes": "10/10/2025", "certificateExpiryDate": "06/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394110", "make": "Make 20", "model": "Model 20", "crDtimes": "02/10/2025", "certificateExpiryDate": "07/10/2025", "certificateUploadDate": "11/10/2024", "status": "approved"},
      { "partnerId": "P28394111", "make": "Make 21", "model": "Model 21", "crDtimes": "05/10/2025", "certificateExpiryDate": "08/10/2025", "certificateUploadDate": "11/10/2024", "status": "pending_approval"},
      { "partnerId": "P28394112", "make": "Make 22", "model": "Model 22", "crDtimes": "07/10/2025", "certificateExpiryDate": "09/10/2025", "certificateUploadDate": "11/10/2024", "status": "rejected"}
    ];
    setFtmList(tablevalues);
    setFilteredFtmList(tablevalues);
  }, []);

  const tableHeaders = [
    { id: "partnerId", headerNameKey: 'ftmList.partnerId' },
    { id: "make", headerNameKey: "ftmList.make" },
    { id: "model", headerNameKey: "ftmList.model" },
    { id: "crDtimes", headerNameKey: "ftmList.createdDate" },
    { id: "certificateExpiryDate", headerNameKey: "ftmList.certificateExpiryDate" },
    { id: "certificateUploadDate", headerNameKey: "ftmList.certificateUploadDate" },
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
      const isDateCol = (header === "crDtimes");
      toggleSortAscOrder(header, isDateCol, filteredftmList, setFilteredFtmList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  const sortDescOrder = (header) => {
      const isDateCol = (header === "crDtimes");
      toggleSortDescOrder(header, isDateCol, filteredftmList, setFilteredFtmList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  //This part related to Pagination Logic
  let tableRows = filteredftmList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

  const showFtmDetails = (selectedFtmData) => {
    if (selectedFtmData.status !== "deactivated") {
      navigate('/partnermanagement/ftmChipProviderServices/viewFtmChipDetails');
    }
  }

  const viewFtmDetails = (selectedFtmData) => {
    navigate('/partnermanagement/ftmChipProviderServices/viewFtmChipDetails');
  }

  const showDeactivateFtm = (selectedFtmData) => {
    if (selectedFtmData.status === "approved") {
    }
  };

  const showManageCertificate = (selectedFtmData) => {
    if (selectedFtmData.status === "approved") {
      navigate('/partnermanagement/ftmChipProviderServices/manageFtmChipCertificate');
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
            <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
              <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
              </div>
            </div>
          )}
          <div className="flex-col mt-7">
            <div className="flex justify-between mb-5">
              <Title title='ftmList.ftmChipProviderServices' backLink='/partnermanagement' />
              { ftmList.length > 0 && (
                <button onClick={() => addFtm()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                  {t('ftmList.addFtmBtn')}
                </button>
              )}
            </div>
            {ftmList.length === 0 ? 
              <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                <div className="flex justify-between py-2 px-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                  <div className={`flex w-full justify-between`}>
                    <h6 className="px-2 mx-2">{t('ftmList.partnerId')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.make')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.model')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.createdDate')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.certificateExpiryDate')}</h6>
                    <h6 className="px-2 mx-2">{t('ftmList.certificateUploadDate')}</h6>
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
                              <th key={index} className={`py-4 px-2 text-xs text-[#6F6E6E] w-[14%] max-[1310px]:w-[16%]`}>
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
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{formatDate(ftm.crDtimes, 'date')}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.certificateExpiryDate}</td>
                                <td onClick={() => showFtmDetails(ftm)} className="px-2 mx-2">{ftm.certificateUploadDate}</td>
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
                                      <div className={`absolute w-[7%] ${currentArray.length - 1 === index ? '-bottom-2' : currentArray.length - 2 === index ? '-bottom-2' : 'top-7'} z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-4 text-right" : "right-4 text-left"}`}>
                                        <p onClick={() => viewFtmDetails(ftm)} className={`py-2 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewFtmDetails(ftm))}>
                                          {t('ftmList.view')}
                                        </p>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <p onClick={() => showManageCertificate(ftm)} className={`py-2 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${ftm.status === "approved" ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showManageCertificate(ftm))}>
                                          {t('ftmList.manageCertificate')}
                                        </p>
                                        <hr className="h-px bg-gray-200 border-0 mx-1" />
                                        <p onClick={() => showDeactivateFtm(ftm)} className={`py-2 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${ftm.status === "approved" ? 'text-crimson-red cursor-pointer' : 'text-[#A5A5A5] cursor-auto'} hover:bg-gray-100`} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivateFtm(ftm))}>
                                          {t('ftmList.deActivate')}
                                        </p>
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