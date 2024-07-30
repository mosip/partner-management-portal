import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../utils/AppUtils';
import {
  getPartnerManagerUrl, formatDate, handleServiceErrors, getPartnerTypeDescription, getStatusCode, handleMouseClickForDropdown,
  toggleSortAscOrder, toggleSortDescOrder, bgOfStatus
} from '../../utils/AppUtils';
import { HttpService } from '../../services/HttpService';
import PoliciesFilter from './PoliciesFilter';
import ErrorMessage from '../common/ErrorMessage';
import LoadingIcon from "../common/LoadingIcon";
import rectangleGrid from '../../svg/rectangle_grid.svg';
import FilterButtons from '../common/FilterButtons';
import SortingIcon from '../common/SortingIcon';
import Pagination from '../common/Pagination';
import Title from '../common/Title';

function PoliciesList() {

  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [policiesList, setPoliciesList] = useState([]);
  const [filteredPoliciesList, setFilteredPoliciesList] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("createDate");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [firstIndex, setFirstIndex] = useState(0);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const [isDescending, setIsDescending] = useState(false);
  const [viewPolicyId, setViewPolicyId] = useState(-1);
  const defaultFilterQuery = {
    partnerId: "",
    policyGroupName: ""
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewPolicyId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "partnerId", headerNameKey: 'policies.partnerId' },
    { id: "partnerType", headerNameKey: "policies.partnerType" },
    { id: "policyGroupName", headerNameKey: "policies.policyGroupName" },
    { id: "policyName", headerNameKey: "policies.policyName" },
    { id: "createDate", headerNameKey: "policies.createdDate" },
    { id: "status", headerNameKey: "policies.status" },
    { id: "action", headerNameKey: 'policies.action' }
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllRequestedPolicies', process.env.NODE_ENV));
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            const resData = responseData.response;
            const sortedData = resData.sort((a, b) => new Date(b.createDate) - new Date(a.createDate));
            setPoliciesList(sortedData);
            setFilteredPoliciesList(sortedData);
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
  }, []);

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

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    const isDateCol = (header === "createDate") ? true : false;
    toggleSortAscOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  const sortDescOrder = (header) => {
    const isDateCol = (header === "createDate") ? true : false;
    toggleSortDescOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  //This part is related to Filter
  const onFilterChange = (fieldName, selectedFilter) => {
    setFilterQuery(oldFilterQuery => ({
      ...oldFilterQuery,
      [fieldName]: selectedFilter
    }));

    //useEffect will be triggered which will do the filter 
  }
  useEffect(() => {
    let filteredRows = policiesList;
    Object.keys(filterQuery).forEach(key => {
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

  const style = {
    backArrowIcon: "!mt-[9%]"
  }

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
              <Title title='policies.policies' backLink='/partnermanagement' styleSet={style}></Title>

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
                  <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
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
                    <FilterButtons listTitle='policies.listOfPolicies' dataList={filteredPoliciesList} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
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
                                <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[16%]">
                                  <div className="mx-2 flex gap-x-0 items-center">
                                    {t(header.headerNameKey)}
                                    {header.id !== "action" && (
                                      <SortingIcon headerId={header.id} sortDescOrder={sortDescOrder} sortAscOrder={sortAscOrder} order={order} activeSortDesc={activeSortDesc} activeSortAsc={activeSortAsc}></SortingIcon>
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
                              <tr key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${partner.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.partnerId}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyGroupName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{formatDate(partner.createDate, 'date')}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="">
                                  <div className={`${bgOfStatus(partner.status)} flex w-fit py-1.5 px-2 m-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(partner.status, t)}
                                  </div>
                                </td>
                                <td className="text-center">
                                  <div ref={el => submenuRef.current[index] = el}>
                                    <p onClick={() => setViewPolicyId(index === viewPolicyId ? null : index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer`} tabIndex="0" onKeyPress={(e)=> onPressEnterKey(e, () => setViewPolicyId(index === viewPolicyId ? null : index))}>
                                      ...</p>
                                    {
                                      viewPolicyId === index && (
                                        <div onClick={() => showViewPolicyDetails(partner)} tabIndex="0" onKeyPress={(e)=> onPressEnterKey(e, () => showViewPolicyDetails(partner))}
                                          className={`absolute border bg-white text-xs font-semibold rounded-md shadow-md w-fit p-2 z-20 items-center ${isLoginLanguageRTL ? "mr-16 left-[5.5rem] max-[800px]:left-20 max-[400px]:left-8 text-right" : "right-20 text-left"}`}>
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
                  <Pagination dataList={filteredPoliciesList} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                </>
              }
            </div>
          </div>

        </>
      )}

    </div>
  )

}

export default PoliciesList;