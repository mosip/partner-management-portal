import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';
import { getPartnerManagerUrl, formatDate, handleServiceErrors, getPartnerTypeDescription, getStatusCode, handleMouseClickForDropdown,
  toggleSortAscOrder, toggleSortDescOrder, moveToHome } from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import PoliciesFilter from './PoliciesFilter';
import ReactPaginate from 'react-paginate';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import rectangleGrid from '../../svg/rectangle_grid.svg';
import backArrow from '../../svg/back_arrow.svg';
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai"; // icons form react-icons
import { IconContext } from "react-icons"; // for customizing icons

function Policies() {

  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const navigate = useNavigate();
  const [firstTimeLoad, setFirstTimeLoad] = useState(false);
  const [filter, setFilter] = useState(false);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [policiesList, setPoliciesList] = useState([]);
  const [filteredPoliciesList, setFilteredPoliciesList] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [firstIndex, setFirstIndex] = useState(0);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const [isDescending, setIsDescending] = useState(true);
  const itemsPerPageOptions = [8, 16, 24, 32];
  const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
  const [viewPolicyId, setViewPolicyId] = useState(-1);
  const defaultFilterQuery = {
    partnerId: "",
    policyGroupName: ""
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef(null);
  const itemsCountSelectionRef = useRef(null);

  useEffect(() => {
     handleMouseClickForDropdown(submenuRef, () => setViewPolicyId(null));
     handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
  }, [submenuRef,itemsCountSelectionRef]);

  const tableHeaders = [
    { id: "partnerId", headerNameKey: 'policies.partnerId' },
    { id: "partnerType", headerNameKey: "policies.partnerType" },
    { id: "policyGroupName", headerNameKey: "policies.policyGroupName" },
    { id: "policyName", headerNameKey: "policies.policyName" },
    { id: "createDate", headerNameKey: "policies.createdDate" },
    { id: "status", headerNameKey: "policies.status" },
    { id: "action", headerNameKey: 'policies.action' }
  ];

  /*
  const tableValues = [
    { "partnerId": "P10001", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name1", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10002", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name2", "policyName": "KYC", "createDate": "2024-05-21T03:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10003", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name3", "policyName": "Full KYC", "createDate": "2024-05-21T02:16:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10004", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name4", "policyName": "Full KYC", "createDate": "2024-05-21T02:14:42.422+00:00", "status": "InProgress", "Action": "..." },
    { "partnerId": "P10005", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name5", "policyName": "KYC", "createDate": "2024-05-21T02:13:42.422+00:00", "status": "Deactivated", "Action": "..." },
    { "partnerId": "P10006", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name6", "policyName": "KYC1", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10007", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name7", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10008", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name8", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10009", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name9", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10010", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name10", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10011", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name11", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10012", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name11", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10013", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name11", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "InProgress", "Action": "..." },
    { "partnerId": "P10014", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name12", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Deactivated", "Action": "..." },
    { "partnerId": "P10015", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name12", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10016", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name10", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10017", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name13", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10018", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name14", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10019", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name16", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10020", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name16", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10021", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name17", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10022", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name14", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "InProgress", "Action": "..." },
    { "partnerId": "P10023", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name2", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Deactivated", "Action": "..." },
    { "partnerId": "P10024", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name1", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10025", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name18", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10026", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name19", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10027", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name20", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10028", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name21", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10029", "partnerType": "MISP_Partner", "policyGroup": "Policy Group Name22", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "rejected", "Action": "..." },
    { "partnerId": "P10030", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name23", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "approved", "Action": "..." },
    { "partnerId": "P10031", "partnerType": "AUTH_PARTNER", "policyGroup": "Policy Group Name24", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "InProgress", "Action": "..." }
  ];*/

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllRequestedPolicies', process.env.NODE_ENV));
        setFirstTimeLoad(true);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            const resData = responseData.response;
            const sortedData = resData.sort((a, b) => new Date(b.createDate) - new Date(a.createDate));
            setPoliciesList(sortedData);
            setFilteredPoliciesList(sortedData);
            // console.log('Response data:', policiesList.length);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('policies.errorInPoliciesList'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        setErrorMsg(err);
      }
    };
    fetchData();
  }, [firstTimeLoad, t]);

  const showRequestPolicy = () => {
    navigate('/partnermanagement/policies/requestPolicy')
  }

  const showViewPolicyDetails = (selectedPolicyData) => {
    localStorage.setItem('selectedPolicyData', JSON.stringify(selectedPolicyData));
    navigate('/partnermanagement/policies/viewPolicyDetails')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  function bgOfStatus(status) {
    if (status === "approved") {
      return ("bg-[#D1FADF] text-[#155E3E]")
    }
    else if (status === "rejected") {
      return ("bg-[#FAD6D1] text-[#5E1515]")
    }
    else if (status === "InProgress") {
      return ("bg-[#FEF1C6] text-[#6D1C00]")
    }
    else if (status === "deactivated") {
      return ("bg-[#EAECF0] text-[#525252]")
    }
  }

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    const isDateCol = (header === "createDate") ? true : false;
    toggleSortAscOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc);
}

  const sortDescOrder = (header) => {
    const isDateCol = (header === "createDate") ? true : false;
    toggleSortDescOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc);
  }

  //This part is related to Filter
  const onFilterChange = (fieldName, selectedFilter) => {
    //console.log(`onFilterChange called`);
    //console.log(`${fieldName} : ${selectedFilter}`);
    setFilterQuery(oldFilterQuery => ({
      ...oldFilterQuery,
      [fieldName]: selectedFilter
    }));

    //useEffect will be triggered which will do the filter 
  }
  useEffect(() => {
    let filteredRows = policiesList;
    Object.keys(filterQuery).forEach(key => {
      //console.log(`${key} : ${filterQuery[key]}`);
      if (filterQuery[key] !== '') {
        filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
      }
    });
    setFilteredPoliciesList(filteredRows);
    setFirstIndex(0);
  }, [filterQuery, policiesList]);

  const onResetFilter = () => {
    window.location.reload();
  }

  //This  part related to Pagination logic
  let tableRows = filteredPoliciesList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

  const handlePageChange = (event) => {
    const newIndex = (event.selected * selectedRecordsPerPage) % filteredPoliciesList.length;
    setFirstIndex(newIndex);
  };
  const changeItemsPerPage = (num) => {
    setIsItemsPerPageOpen(false);
    setSelectedRecordsPerPage(num);
    setFirstIndex(0);
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <div className={`flex justify-end max-w-7xl mb-5 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
              <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
              </div>
            </div>
          )}
          <div className="flex-col mt-7">
            <div className="flex justify-between mb-3">
              <div className="flex items-start gap-x-2">
                <img src={backArrow} alt="" onClick={() => moveToHome(navigate)} className={`mt-[9%] cursor-pointer ${isLoginLanguageRTL ? "rotate-180" : null}`} />
                <div className="flex-col">
                  <h1 className="font-semibold text-lg text-dark-blue">{t('policies.policies')}</h1>
                  <p onClick={() => moveToHome(navigate)} className="font-semibold text-tory-blue text-xs cursor-pointer">
                    {t('commons.home')}
                  </p>
                </div>
              </div>

              {policiesList.length > 0 ?
                <button onClick={() => showRequestPolicy()} type="button" className={`h-12 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md`}>
                  {t('policies.requestPolicyBtn')}
                </button>
                : null
              }
            </div>

            <div className="flex-col justify-center ml-3 h-full">
              {policiesList.length === 0
                ?
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="flex justify-between py-2 pt-4 text-sm font-medium text-[#6F6E6E]">
                    <div className="flex w-full justify-between">
                      <h6 className="ml-5 mr-3">{t('policies.partnerId')}</h6>
                      <h6>{t('policies.partnerType')}</h6>
                      <h6>{t('policies.policyGroupName')}</h6>
                      <h6>{t('policies.policyName')}</h6>
                      <h6>{t('policies.createdDate')}</h6>
                      <h6>{t('policies.status')}</h6>
                      <h6 className="mx-4">{t('policies.action')}</h6>
                    </div>
                  </div>

                  <hr className="h-px mx-3 bg-gray-200 border-0" />

                  <div className="flex items-center justify-center p-24">
                    <div className="flex flex-col items-center">
                      <img src={rectangleGrid} alt="" />
                      <button onClick={() => showRequestPolicy()} type="button" className="text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm h-11 px-5 py-3">
                        {t('policies.requestPolicyBtn')}
                      </button>
                    </div>
                  </div>
                </div>
                :
                  <>
                    <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3">
                      <div className="flex w-full p-2">
                        <div className="flex w-full pl-[2%] pt-1 items-center justify-start font-semibold text-dark-blue text-base" >
                          {t('policies.listOfPolicies') + ' (' + filteredPoliciesList.length + ")"}
                        </div>
                        <div className="w-full flex justify-end relative ">
                          {filter && <button onClick={() => onResetFilter()} type="button"
                            className={`flex ${isLoginLanguageRTL ? "ml-2" :"mr-2"} justify-center items-center w-[23%] text-sm py-3 font-semibold text-center text-tory-blue`}>
                            {t('policies.resetFilter')}
                          </button>}
                          <button onClick={() => setFilter(!filter)} type="button" className={`flex ${isLoginLanguageRTL ? "ml-2" :"mr-2"} justify-center items-center w-[20%] h-10 min-w-20 text-sm py-3 text-tory-blue border border-[#1447B2] font-semibold rounded-md text-center
                            ${filter ? 'bg-tory-blue text-white' : 'text-tory-blue bg-white'} `}>
                            {t('policies.filterBtn')}
                            <svg
                              xmlns="http://www.w3.org/2000/svg" className={`${filter ? 'rotate-180 text-white' : null} ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`}
                              width="10" height="8" viewBox="0 0 10 8">
                              <path id="Polygon_8"
                                data-name="Polygon 8"
                                d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                                transform="translate(10 8) rotate(180)" fill={`${filter ? '#ffff' : '#1447b2'}`} />
                            </svg>
                          </button>
                        </div>
                      </div>
                      <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                      {filter &&
                        <PoliciesFilter
                          filteredPoliciesList={filteredPoliciesList}
                          onFilterChange={onFilterChange}>
                        </PoliciesFilter>}

                      <div className="mx-[2%] overflow-x-scroll">
                        <table className="table-fixed">
                          <thead>
                            <tr>
                              {tableHeaders.map((header, index) => {
                                return (
                                  <th key={index} className="py-4 text-sm font-medium text-[#6F6E6E] w-[16%]">
                                    <div className="mx-2 flex gap-x-0 items-center">
                                      {t(header.headerNameKey)}
                                      {header.id !== "action" && (
                                        <div>
                                          <svg className="cursor-pointer mx-2 mb-0.5" onClick={() => sortAscOrder(header.id)} alt="Ascending"
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="8" height="8" viewBox="0 0 7 6">
                                            <path id="Polygon_3" data-name="Polygon 3" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                                              fill={`${(activeSortDesc === header.id && order === "ASC") ? "#1447b2" : "#969696"}`} />
                                          </svg>
                                          <svg className="cursor-pointer mx-2" onClick={() => sortDescOrder(header.id)} alt="Descending"
                                            xmlns="http://www.w3.org/2000/svg"
                                            width="8" height="8" viewBox="0 0 7 6">
                                            <path id="Polygon_4" data-name="Polygon 4" d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                                              transform="translate(7 6) rotate(180)" fill={`${(activeSortAsc === header.id && order === "DESC") ? "#1447b2" : "#969696"}`} />
                                          </svg>
                                        </div>
                                      )}
                                    </div>
                                  </th>
                                )
                              })}
                            </tr>
                          </thead>
                          <tbody>
                            {tableRows.map((partner, index) => {
                              return (
                                <tr key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-medium ${partner.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                  <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.partnerId}</td>
                                  <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                  <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyGroupName}</td>
                                  <td onClick={() => showViewPolicyDetails(partner)} className="px-2 break-all break-word">{partner.policyName}</td>
                                  <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{formatDate(partner.createDate, 'date')}</td>
                                  <td onClick={() => showViewPolicyDetails(partner)} className="">
                                    <div className={`${bgOfStatus(partner.status)} flex w-fit py-1.5 px-2 m-3 text-xs font-medium rounded-md`}>
                                      {getStatusCode(partner.status, t)}
                                    </div>
                                  </td>
                                  <td className="text-center">
                                    <div>
                                      <p onClick={() => setViewPolicyId(index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer`}>...</p>
                                      {
                                        viewPolicyId === index && (
                                          <div ref={submenuRef} onClick={() => showViewPolicyDetails(partner)}
                                            className={`absolute border bg-white text-xs font-medium rounded-md shadow-md w-[5%] p-2 z-20 items-center ${isLoginLanguageRTL ? "-mr-14" : null}`}>
                                            <p className="cursor-pointer">
                                              {t('policies.view')}
                                            </p>
                                          </div>
                                        )
                                      }
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
                    <div className="flex justify-between bg-[#FCFCFC] items-center h-9  mt-0.5 p-8 rounded-b-md shadow-md">
                      <div></div>
                      <ReactPaginate
                        containerClassName={"pagination"}
                        pageClassName={"page-item"}
                        activeClassName={"active"}
                        onPageChange={(event) => handlePageChange(event)}
                        pageCount={Math.ceil(filteredPoliciesList.length / selectedRecordsPerPage)}
                        breakLabel="..."
                        previousLabel={
                          <IconContext.Provider value={{ color: "#B8C1CC", size: "25px" }}>
                            {isLoginLanguageRTL ? <AiFillRightCircle /> : <AiFillLeftCircle />}
                          </IconContext.Provider>
                        }
                        nextLabel={
                          <IconContext.Provider value={{ color: "#B8C1CC", size: "25px" }}>
                            {isLoginLanguageRTL ? <AiFillLeftCircle /> : <AiFillRightCircle />}
                          </IconContext.Provider>
                        }
                      />
                      <div className="flex items-center gap-x-3">
                        <h6 className="text-gray-500 text-xs">{t('policies.itemsPerPage')}</h6>
                        <div>
                          {isItemsPerPageOpen && (
                            <div ref={itemsCountSelectionRef} className={`absolute bg-white text-xs text-tory-blue font-medium rounded-lg border-[2px] -mt-[130px] duration-700`}>
                              {itemsPerPageOptions.map((num, i) => {
                                return (
                                  <p key={i} onClick={() => changeItemsPerPage(num)}
                                    className={`px-3 py-2 cursor-pointer ${selectedRecordsPerPage === num ? 'bg-[#F2F5FC]' : 'hover:bg-[#F2F5FC]'}`}>
                                    {num}
                                  </p>
                                )
                              })
                              }
                            </div>
                          )}
                          <div className="cursor-pointer flex justify-between w-10 h-6 items-center 
                        text-xs border px-1 rounded-md border-[#1447b2] bg-white text-tory-blue font-medium"
                            onClick={() => setIsItemsPerPageOpen(!isItemsPerPageOpen)}>
                            <p>
                              {selectedRecordsPerPage}
                            </p>
                            <svg className={`${isItemsPerPageOpen ? "rotate-180 duration-500" : "duration-500"}`}
                              xmlns="http://www.w3.org/2000/svg"
                              width="10.359" height="5.697" viewBox="0 0 11.359 6.697">
                              <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                                d="M17.68,23.3,12,17.618,13.018,16.6l4.662,4.686,4.662-4.662,1.018,1.018Z"
                                transform="translate(-12 -16.6)" fill="#1447b2" />
                            </svg>
                          </div>
                        </div>
                      </div>
                    </div>
                  </>
              }
            </div>
          </div>

        </>
      )}

    </div>
  )

}

export default Policies;