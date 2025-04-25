import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL } from '../../../utils/AppUtils';
import {
  formatDate, getPartnerTypeDescription, getStatusCode, handleMouseClickForDropdown,
  toggleSortAscOrder, toggleSortDescOrder, bgOfStatus, getPartnerPolicyRequests, setSubmenuRef
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import PoliciesFilter from './PoliciesFilter';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import FilterButtons from '../../common/FilterButtons';
import SortingIcon from '../../common/SortingIcon';
import viewIcon from "../../../svg/view_icon.svg";
import Pagination from '../../common/Pagination';
import Title from '../../common/Title';
import EmptyList from '../../common/EmptyList';

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
  const [order, setOrder] = useState("DESC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("createdDateTime");
  const [firstIndex, setFirstIndex] = useState(0);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
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
    { id: "policyId", headerNameKey: "policies.policyId" },
    { id: "policyName", headerNameKey: "policies.policyName" },
    { id: "createdDateTime", headerNameKey: "policies.creationDate" },
    { id: "status", headerNameKey: "policies.status" },
    { id: "action", headerNameKey: 'policies.action' }
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const response = await getPartnerPolicyRequests(HttpService, setErrorCode, setErrorMsg, t);
        if (response) {
          const sortedData = response.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
          setPoliciesList(sortedData);
          setFilteredPoliciesList(sortedData);
        } else {
          setErrorMsg(t('policies.errorInPoliciesList'));
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

  const showRequestPolicy = () => {
    navigate('/partnermanagement/policies/request-policy')
  }

  const showViewPolicyDetails = (selectedPolicyData) => {
    localStorage.setItem('selectedPolicyAttributes', JSON.stringify(selectedPolicyData));
    navigate('/partnermanagement/policies/view-policy-details')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    const isDateCol = (header === "createdDateTime") ? true : false;
    toggleSortAscOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  const sortDescOrder = (header) => {
    const isDateCol = (header === "createdDateTime") ? true : false;
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
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between mb-3">
              <Title title='policies.policies' backLink='/partnermanagement' styleSet={style} />

              {policiesList.length > 0 ?
                <button id='policies_request_btn' onClick={() => showRequestPolicy()} type="button" className={`h-12 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md`}>
                  {t('policies.requestPolicyBtn')}
                </button>
                : null
              }
            </div>

            <div className="flex-col justify-center ml-3 h-full">
              {policiesList.length === 0
                ?
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <EmptyList
                    tableHeaders={tableHeaders}
                    showCustomButton={true}
                    customButtonName='policies.requestPolicyBtn'
                    buttonId='show_request_policy'
                    onClickButton={showRequestPolicy}
                  />
                </div>
                :
                <>
                  <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3">
                    <FilterButtons titleId='list_of_policies' listTitle='policies.listOfPolicies' dataListLength={filteredPoliciesList.length} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {filter &&
                      <PoliciesFilter
                        filteredPoliciesList={filteredPoliciesList}
                        onFilterChange={onFilterChange}>
                      </PoliciesFilter>}

                    <div className="mx-[1.4rem] overflow-x-scroll">
                      <table className="table-fixed">
                        <thead>
                          <tr>
                            {tableHeaders.map((header, index) => {
                              return (
                                <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[16%]">
                                  <div id={`${header.headerNameKey}_header`} className="mx-2 text-left flex gap-x-0 items-center">
                                    {t(header.headerNameKey)}
                                    {header.id !== "action" && (
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
                          {tableRows.map((partner, index) => {
                            return (
                              <tr id={'policy_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${partner.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.partnerId}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyGroupName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyId}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{formatDate(partner.createdDateTime, 'date')}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="">
                                  <div className={`${bgOfStatus(partner.status)} flex w-fit py-1.5 px-2 m-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(partner.status, t)}
                                  </div>
                                </td>
                                <td className="text-center cursor-default">
                                  <div ref={setSubmenuRef(submenuRef, index)}>
                                    <button id={'policy_list_view' + (index + 1)} onClick={() => setViewPolicyId(index === viewPolicyId ? null : index)} className={`font-semibold mb-0.5 text-center cursor-pointer`}>
                                      ...
                                    </button>
                                    {
                                      viewPolicyId === index && (
                                        <div role='button' id='policy_list_view_card' onClick={() => showViewPolicyDetails(partner)}
                                          className={`flex justify-between border bg-white absolute text-xs font-semibold rounded-md shadow-md w-[6rem] px-1.5 py-2 z-20 items-center cursor-pointer ${isLoginLanguageRTL ? "left-[4.5rem] text-right" : "right-[4.5rem] text-left"}`}>
                                          <p> {t('policies.view')} </p>
                                          <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
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
                  <Pagination dataListLength={filteredPoliciesList.length} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
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