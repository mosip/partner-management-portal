import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import {
  getPartnerManagerUrl,
  handleServiceErrors,
  getStatusCode,
  handleMouseClickForDropdown,
  getPartnerTypeDescription,
  isLangRTL,
  onPressEnterKey,
  formatDate,
  bgOfStatus,
  onClickApplyFilter,
  resetPageNumber, setPageNumberAndPageSize, onResetFilter,
  getApproveRejectStatus,
  escapeKeyHandler,
  setSubmenuRef
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import FilterButtons from "../../common/FilterButtons";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import { HttpService } from "../../../services/HttpService";
import viewIcon from "../../../svg/view_icon.svg";
import EmptyList from "../../common/EmptyList";
import PolicyRequestsListFilter from "./PolicyRequestsListFilter";
import approveRejectIcon from "../../../svg/approve_reject_icon.svg";
import disabledApproveRejectIcon from "../../../svg/approve_reject_disabled_icon.svg";
import ApproveRejectPopup from "../../common/ApproveRejectPopup";

function PolicyRequestsList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [policyRequestsData, setPolicyRequestsData] = useState([]);
  const [order, setOrder] = useState("DESC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("createdDateTime");
  const [firstIndex, setFirstIndex] = useState(0);
  const [viewPartnerId, setViewPartnersId] = useState(-1);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
  const [sortFieldName, setSortFieldName] = useState("createdDateTime");
  const [sortType, setSortType] = useState("desc");
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
  const [triggerServerMethod, setTriggerServerMethod] = useState(false);
  const [totalRecords, setTotalRecords] = useState(0);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
  const [isFilterApplied, setIsFilterApplied] = useState(false);
  const [showActiveIndexPopup, setShowActiveIndexPopup] = useState(null);
  const [resetPageNo, setResetPageNo] = useState(false);
  const [selectedPolicyRequest, setSelectedPolicyRequest] = useState({});
  const [filters, setFilters] = useState({
    partnerId: null,
    partnerType: null,
    status: null,
    orgName: null,
    policyGroupName: null,
    policyId: null,
    policyName: null,
  });
  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewPartnersId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "partnerId", headerNameKey: "partnerPolicyMappingRequestList.partnerId" },
    { id: "partnerType", headerNameKey: "partnerPolicyMappingRequestList.partnerType" },
    { id: "orgName", headerNameKey: "partnerPolicyMappingRequestList.organisation" },
    { id: "policyId", headerNameKey: "partnerPolicyMappingRequestList.policyId" },
    { id: "policyName", headerNameKey: "partnerPolicyMappingRequestList.policyName" },
    { id: "policyGroupName", headerNameKey: "partnerPolicyMappingRequestList.policyGroupName" },
    { id: "createdDateTime", headerNameKey: "partnerPolicyMappingRequestList.creationDate" },
    { id: "status", headerNameKey: "partnerPolicyMappingRequestList.status" },
    { id: "action", headerNameKey: "partnerPolicyMappingRequestList.action" },
  ];

  const fetchPolicyRequestsListData = async () => {
    const queryParams = new URLSearchParams();
    queryParams.append('sortFieldName', sortFieldName);
    queryParams.append('sortType', sortType);
    queryParams.append('pageSize', pageSize);

    //reset page number to 0 if filter applied or page number is out of bounds
    const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
    queryParams.append('pageNo', effectivePageNo);
    setResetPageNo(false);

    if (filters.partnerId) queryParams.append('partnerId', filters.partnerId);
    if (filters.partnerType) queryParams.append('partnerType', filters.partnerType);
    if (filters.orgName) queryParams.append('orgName', filters.orgName);
    if (filters.policyGroupName) queryParams.append('policyGroupName', filters.policyGroupName);
    if (filters.policyId) queryParams.append('policyId', filters.policyId);
    if (filters.policyName) queryParams.append('policyName', filters.policyName);
    if (filters.status) queryParams.append('status', filters.status);

    const url = `${getPartnerManagerUrl('/partner-policy-requests', process.env.NODE_ENV)}?${queryParams.toString()}`;
    try {
      triggerServerMethod ? setTableDataLoaded(false) : setDataLoaded(false);
      const response = await HttpService.get(url);
      if (response) {
        const responseData = response.data;
        if (responseData && responseData.response) {
          const resData = responseData.response.data;
          setTotalRecords(responseData.response.totalResults);
          setPolicyRequestsData(resData);
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg(t('partnerPolicyMappingRequestList.errorInpartnerPolicyMappingRequestList'));
      }
      triggerServerMethod ? setTableDataLoaded(true) : setDataLoaded(true);
      setTriggerServerMethod(false);
    } catch (err) {
      console.error('Error fetching data:', err);
      if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
      }
      setTriggerServerMethod(false);
      triggerServerMethod ? setTableDataLoaded(true) : setDataLoaded(true);
    }
  }

  useEffect(() => {
    fetchPolicyRequestsListData();
  }, [sortFieldName, sortType, pageNo, pageSize]);

  useEffect(() => {

    if (isApplyFilterClicked) {
      fetchPolicyRequestsListData();
      setIsApplyFilterClicked(false);
    }
  }, [isApplyFilterClicked]);

  const onApplyFilter = (filters) => {
    onClickApplyFilter(filters, setIsFilterApplied, setResetPageNo, setTriggerServerMethod, setFilters, setIsApplyFilterClicked);
  };

  const getPaginationValues = (recordsPerPage, pageIndex) => {
    setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setTriggerServerMethod);
  }

  const viewPartnerPolicyRequestDetails = (selectedPartnerPolicyRequest) => {
    localStorage.setItem('selectedPartnerPolicyRequest', JSON.stringify(selectedPartnerPolicyRequest));
    navigate('/partnermanagement/admin/view-policy-request');
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    if (order !== 'ASC' || activeSortAsc !== header) {
      setTriggerServerMethod(true);
      setSortFieldName(header);
      setSortType("asc");
      setOrder("ASC");
      setActiveSortDesc("");
      setActiveSortAsc(header);
    }
  };

  const sortDescOrder = (header) => {
    if (order !== 'DESC' || activeSortDesc !== header) {
      setTriggerServerMethod(true);
      setSortFieldName(header);
      setSortType("desc");
      setOrder("DESC");
      setActiveSortDesc(header);
      setActiveSortAsc("");
    }
  };

  const onClickApproveReject = (responseData, status, selectedPolicyRequest) => {
    if (responseData !== "") {
      setSelectedPolicyRequest({});
      setShowActiveIndexPopup(null);
      // Update the specific row in the state with the new status
      setPolicyRequestsData((prevList) =>
        prevList.map(policyRequest =>
          policyRequest.id === selectedPolicyRequest.id ? { ...policyRequest, status: getApproveRejectStatus(status) } : policyRequest
        )
      );
    }
  }

  const closePolicyRequestPopup = () => {
    setShowActiveIndexPopup(null);
    setSelectedPolicyRequest({});
  };

  useEffect(() => {
    escapeKeyHandler(closePolicyRequestPopup);
  }, [showActiveIndexPopup]);

  const approveRejectPolicyRequest = (policyRequest, index) => {
    if (policyRequest.status === 'InProgress') {
      setShowActiveIndexPopup(index);
      setViewPartnersId(-1);
      setSelectedPolicyRequest(policyRequest);
    }
  };

  const styles = {
    loadingDiv: "!py-[20%]"
  }

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between mb-3">
              <Title title='partnerPolicyMappingRequestList.partnerPolicyMappingRequestTitle' backLink='/partnermanagement' />
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {!isFilterApplied && policyRequestsData.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                    <div className="flex w-full px-2">
                      <div className="flex w-full pl-[2%] pt-1 items-center justify-start font-semibold text-dark-blue text-base">
                        {t("partnerPolicyMappingRequestList.listOfPartnerPolicyMappingRequestTitle")}
                      </div>
                      <button disabled type="button"
                        className={`flex justify-center items-center w-[14%] text-sm py-2 mt-2 border border-[#D0D0D0] font-semibold rounded-md text-center min-w-fit px-2 bg-transparent text-[#D0D0D0] cursor-auto ${isLoginLanguageRTL ? "mr-3" : "ml-3"}`}>
                        {t("commons.filterBtn")}
                        <svg xmlns="http://www.w3.org/2000/svg" className={`${isLoginLanguageRTL ? "mr-2" : "ml-2"}`}
                          width="10" height="8" viewBox="0 0 10 8">
                          <path id="Polygon_8" data-name="Polygon 8"
                            d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z" transform="translate(10 8) rotate(180)"
                            fill="#D0D0D0"
                          />
                        </svg>
                      </button>
                    </div>
                  </div>
                  <EmptyList tableHeaders={tableHeaders} showCustomButton={false} />
                </div>
              ) : (
                <>
                  <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                    <FilterButtons
                      listTitle="partnerPolicyMappingRequestList.listOfPartnerPolicyMappingRequestTitle"
                      dataListLength={totalRecords}
                      filter={filter}
                      onResetFilter={onResetFilter}
                      setFilter={setFilter}
                    />
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {filter && (
                      <PolicyRequestsListFilter
                        onApplyFilter={onApplyFilter}
                        setErrorCode={setErrorCode}
                        setErrorMsg={setErrorMsg}
                      />
                    )}
                    {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                    {tableDataLoaded && isFilterApplied && policyRequestsData.length === 0 ?
                      <EmptyList tableHeaders={tableHeaders} showCustomButton={false} />
                      : (
                        <>
                          <div className="mx-[1.4rem] overflow-x-scroll">
                            <table className="table-fixed">
                              <thead>
                                <tr>
                                  {tableHeaders.map((header, index) => {
                                    return (
                                      <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[15%]">
                                        <div className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                          {t(header.headerNameKey)}
                                          {header.id !== "action" && (
                                            <SortingIcon
                                              headerId={header.id}
                                              sortDescOrder={sortDescOrder}
                                              sortAscOrder={sortAscOrder}
                                              order={order}
                                              activeSortDesc={activeSortDesc}
                                              activeSortAsc={activeSortAsc}
                                            />
                                          )}
                                        </div>
                                      </th>);
                                  })}
                                </tr>
                              </thead>
                              <tbody>
                                {policyRequestsData.map((policyRequest, index) => {
                                  return (
                                    <tr id={"partner_list_item" + (index + 1)} key={index}
                                      className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words "text-[#191919]`}>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{policyRequest.partnerId}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{getPartnerTypeDescription(policyRequest.partnerType, t)}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{policyRequest.orgName}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{policyRequest.policyId}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{policyRequest.policyName ? policyRequest.policyName : '-'}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{policyRequest.policyGroupName ? policyRequest.policyGroupName : '-'}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="px-2">{formatDate(policyRequest.createdDateTime, 'date')}</td>
                                      <td onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} className="whitespace-nowrap">
                                        <div className={`${bgOfStatus(policyRequest.status)} flex w-fit py-1.5 px-2 my-3 mx-1 text-xs font-semibold rounded-md`}>
                                          {getStatusCode(policyRequest.status, t)}
                                        </div>
                                      </td>
                                      <td className="text-center cursor-default">
                                        <div ref={setSubmenuRef(submenuRef, index)}>
                                          <button id={"partner_list_view" + (index + 1)} onClick={() => setViewPartnersId(index === viewPartnerId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center text-[#191919]`}>
                                            ...
                                          </button>
                                          {viewPartnerId === index && (
                                            <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                              <div role='button' disabled={policyRequest.status !== 'InProgress'} onClick={() => approveRejectPolicyRequest(policyRequest, index)} className={`flex justify-between ${policyRequest.status === 'InProgress' && 'hover:bg-gray-100'} `} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => approveRejectPolicyRequest(policyRequest, index))}>
                                                <p id="partner_details_view_btn" className={`py-1.5 px-4 ${policyRequest.status === 'InProgress' ? 'text-[#3E3E3E] cursor-pointer' : 'text-[#A5A5A5] cursor-default'} ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("approveRejectPopup.approveReject")}</p>
                                                <img src={policyRequest.status === 'InProgress' ? approveRejectIcon : disabledApproveRejectIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                              </div>
                                              <hr className="h-px bg-gray-100 border-0 mx-1" />
                                              <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewPartnerPolicyRequestDetails(policyRequest)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewPartnerPolicyRequestDetails(policyRequest))}>
                                                <p id="partner_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerPolicyMappingRequestList.view")}</p>
                                                <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                              </div>
                                            </div>
                                          )}
                                          {showActiveIndexPopup === index &&
                                            <ApproveRejectPopup
                                              popupData={{ ...selectedPolicyRequest, isPartnerPolicyRequest: true }}
                                              closePopUp={closePolicyRequestPopup}
                                              approveRejectResponse={(responseData, status) => onClickApproveReject(responseData, status, selectedPolicyRequest)}
                                              title={selectedPolicyRequest.policyName}
                                              subtitle={`# ${selectedPolicyRequest.policyId}`}
                                              header={t('partnerPolicyRequestApproveRejectPopup.header')}
                                              description={t('partnerPolicyRequestApproveRejectPopup.description')}
                                            />
                                          }
                                        </div>
                                      </td>
                                    </tr>
                                  );
                                })}
                              </tbody>
                            </table>
                          </div>
                          <Pagination
                            dataListLength={totalRecords}
                            selectedRecordsPerPage={selectedRecordsPerPage}
                            setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                            setFirstIndex={setFirstIndex}
                            isServerSideFilter={true}
                            getPaginationValues={getPaginationValues}
                          />
                        </>
                      )}
                  </div>
                </>
              )}
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default PolicyRequestsList;
