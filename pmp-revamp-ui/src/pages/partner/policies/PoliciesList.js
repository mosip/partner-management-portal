import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import {
  formatDate, getPartnerTypeDescription, getStatusCode, handleMouseClickForDropdown,
  toggleSortAscOrder, toggleSortDescOrder, bgOfStatus, getPartnerPolicyRequests
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import PoliciesFilter from './PoliciesFilter';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import FilterButtons from '../../common/FilterButtons';
import SortingIcon from '../../common/SortingIcon';
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
    { id: 1, keyName: 'partnerId', headerNameKey: 'policies.partnerId' },
    { id: 2, keyName: 'partnerType', headerNameKey: "policies.partnerType" },
    { id: 3, keyName: 'policyGroupName', headerNameKey: "policies.policyGroupName" },
    { id: 4, keyName: 'policyName', headerNameKey: "policies.policyName" },
    { id: 5, keyName: 'createdDateTime', headerNameKey: "policies.createdDate" },
    { id: 6, keyName: 'status', headerNameKey: "policies.status" },
    { id: 7, keyName: 'action', headerNameKey: 'policies.action' }
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
        setErrorMsg(err);
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
          <div className="flex-col mt-7">
            <div className="flex justify-between mb-3">
              <Title title='policies.policies' backLink='/partnermanagement' styleSet={style}/>

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

                    <div className="mx-[2%] overflow-x-scroll">
                      <table className="table-fixed">
                        <thead>
                          <tr>
                            {tableHeaders.map((header) => {
                              return (
                                <th key={header.id} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[16%]">
                                  <div id={`${header.headerNameKey}_header`} className="mx-2 flex gap-x-0 items-center">
                                    {t(header.headerNameKey)}
                                    {header.keyName !== "action" && (
                                      <SortingIcon
                                        id={`${header.headerNameKey}_sorting_icon`}
                                        headerId={header.keyName}
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
                              <tr id={'policy_list_item' + (index + 1)} key={partner.policyId} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${partner.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.partnerId}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyGroupName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{formatDate(partner.createdDateTime, 'date', true)}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="">
                                  <div className={`${bgOfStatus(partner.status)} flex w-fit py-1.5 px-2 m-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(partner.status, t)}
                                  </div>
                                </td>
                                <td className="text-center">
                                  <div ref={el => submenuRef.current[index] = el}>
                                    <p role='button' id={'policy_list_view' + (index + 1)} onClick={() => setViewPolicyId(index === viewPolicyId ? null : index)} className={`${isLoginLanguageRTL ? "ml-9" : "mr-9"} font-semibold mb-0.5 cursor-pointer`} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => setViewPolicyId(index === viewPolicyId ? null : index))}>
                                      ...</p>
                                    {
                                      viewPolicyId === index && (
                                        <div role='button' id='policy_list_view_card' onClick={() => showViewPolicyDetails(partner)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showViewPolicyDetails(partner))}
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