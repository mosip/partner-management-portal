import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createRequest, escapeKeyHandler, isLangRTL, onPressEnterKey } from "../../../utils/AppUtils";
import {
  getPartnerManagerUrl,
  handleServiceErrors,
  getStatusCode,
  handleMouseClickForDropdown,
  getPartnerTypeDescription,
  resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize, onResetFilter, setSubmenuRef
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import FilterButtons from "../../common/FilterButtons";
import PartnerListFilter from "./PartnersListFilter";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import { HttpService } from "../../../services/HttpService";
import DeactivatePopup from "../../common/DeactivatePopup";
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import EmptyList from "../../common/EmptyList";

function PartnersList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [partnersData, setPartnersData] = useState([]);
  const [order, setOrder] = useState("DESC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("");
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
  const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
  const [selectedPartner, setSelectedPartner] = useState({});
  const [deactivateRequest, setDeactivateRequest] = useState({});
  const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
  const [isFilterApplied, setIsFilterApplied] = useState(false);
  const [resetPageNo, setResetPageNo] = useState(false);
  const [filters, setFilters] = useState({
    partnerId: null,
    partnerType: null,
    status: null,
    orgName: null,
    emailAddress: null,
    certificateUploadStatus: null,
    policyGroupName: null,
  });
  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewPartnersId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "partnerId", headerNameKey: "partnerList.partnerId" },
    { id: "partnerType", headerNameKey: "partnerList.partnerType" },
    { id: "orgName", headerNameKey: "partnerList.organisation" },
    { id: "policyGroupName", headerNameKey: "partnerList.policyGroup" },
    { id: "emailAddress", headerNameKey: "partnerList.email" },
    { id: "certificateUploadStatus", headerNameKey: "partnerList.certUploadStatus" },
    { id: "status", headerNameKey: "partnerList.status" },
    { id: "action", headerNameKey: "partnerList.action" },
  ];

  const fetchPartnersData = async () => {
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
    if (filters.emailAddress) queryParams.append('emailAddress', filters.emailAddress);
    if (filters.certificateUploadStatus) queryParams.append('certificateUploadStatus', filters.certificateUploadStatus);
    if (filters.policyGroupName) queryParams.append('policyGroupName', filters.policyGroupName);

    // Check filters.status
    if (filters.status !== null) {
      if (filters.status === 'active') queryParams.append('isActive', true);
      else if (filters.status === 'deactivated') queryParams.append('isActive', false);
    }

    const url = `${getPartnerManagerUrl('/admin-partners', process.env.NODE_ENV)}?${queryParams.toString()}`;
    try {
      triggerServerMethod ? setTableDataLoaded(false) : setDataLoaded(false);
      const response = await HttpService.get(url);
      if (response) {
        const responseData = response.data;
        if (responseData && responseData.response) {
          const resData = responseData.response.data;
          setTotalRecords(responseData.response.totalResults);
          setPartnersData(resData);
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg(t('partnerList.errorInPartnersList'));
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
    fetchPartnersData();
  }, [sortFieldName, sortType, pageNo, pageSize]);

  useEffect(() => {

    if (isApplyFilterClicked) {
      fetchPartnersData();
      setIsApplyFilterClicked(false);
    }
  }, [isApplyFilterClicked]);

  const onApplyFilter = (filters) => {
    onClickApplyFilter(filters, setIsFilterApplied, setResetPageNo, setTriggerServerMethod, setFilters, setIsApplyFilterClicked);
  };

  const getPaginationValues = (recordsPerPage, pageIndex) => {
    setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setTriggerServerMethod);
  }

  const viewPartnerDetails = (selectedPartnerData) => {
    localStorage.setItem('selectedPartnerId', selectedPartnerData.partnerId);
    navigate('/partnermanagement/admin/view-partner-details')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    if (order !== 'ASC' || activeSortAsc !== header) {
      setTriggerServerMethod(true);
      setSortFieldName((header === 'status') ? 'isActive' : header);
      setSortType("asc");
      setOrder("ASC");
      setActiveSortDesc("");
      setActiveSortAsc(header);
    }
  };

  const sortDescOrder = (header) => {
    if (order !== 'DESC' || activeSortDesc !== header) {
      setTriggerServerMethod(true);
      setSortFieldName((header === 'status') ? 'isActive' : header);
      setSortType("desc");
      setOrder("DESC");
      setActiveSortDesc(header);
      setActiveSortAsc("");
    }
  };

  const style = {
    backArrowIcon: "!mt-[9%]",
  };

  const showDeactivatePartner = (selectedPartnerdata, index) => {
    if (selectedPartnerdata.isActive === true) {
      const request = createRequest({
        status: "De-Active"
      });
      setSelectedPartner(selectedPartnerdata);
      setViewPartnersId(-1);
      setDeactivateRequest(request);
      setShowActiveIndexDeactivatePopup(index);
    }
  };

  const closeDeactivatePopup = () => {
    setShowActiveIndexDeactivatePopup(null);
    setSelectedPartner({});
  }

  const onClickConfirmDeactivate = (deactivationResponse, selectedPartnerData) => {
    if (deactivationResponse && deactivationResponse.message) {
      setShowActiveIndexDeactivatePopup(null);
      setSelectedPartner({});
      // Update the specific row in the state with the new status
      setPartnersData((prevList) =>
        prevList.map(partner =>
          partner.partnerId === selectedPartnerData.partnerId ? { ...partner, isActive: false } : partner
        )
      );
    }
  };

  useEffect(() => {
    escapeKeyHandler(closeDeactivatePopup);
  }, [showActiveIndexDeactivatePopup]);

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
              <Title title="partnerList.partnerTitle" backLink="/partnermanagement" styleSet={style} />
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {!isFilterApplied && partnersData.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                    <div className="flex w-full px-2">
                      <div className="flex w-full pl-[2%] pt-1 items-center justify-start font-semibold text-dark-blue text-base">
                        {t("partnerList.listOfPartnerTitle")}
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
                      listTitle="partnerList.listOfPartnerTitle"
                      dataListLength={totalRecords}
                      filter={filter}
                      onResetFilter={onResetFilter}
                      setFilter={setFilter}
                    />
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {filter && (
                      <PartnerListFilter
                        onApplyFilter={onApplyFilter}
                        setErrorCode={setErrorCode}
                        setErrorMsg={setErrorMsg}
                      />
                    )}
                    {!tableDataLoaded && <LoadingIcon styleSet={styles}></LoadingIcon>}
                    {tableDataLoaded && isFilterApplied && partnersData.length === 0 ?
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
                                {partnersData.map((partner, index) => {
                                  return (
                                    <tr id={"partner_list_item" + (index + 1)} key={index}
                                      className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${partner.isActive === false ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)} className={`px-2`}>{partner.partnerId}</td>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)} className={`px-2`}>{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)} className={`px-2`}>{partner.orgName}</td>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)} className={`px-2`}>{partner.policyGroupName ? partner.policyGroupName : "-"}</td>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)} className={`px-2`}>{partner.emailAddress}</td>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)} className={`px-3 whitespace-nowrap ${partner.certificateUploadStatus === 'not_uploaded' && "text-[#BE1818]"}`}>
                                        {getStatusCode(partner.certificateUploadStatus, t)}
                                      </td>
                                      <td onClick={() => partner.isActive && viewPartnerDetails(partner)}>
                                        <div className={`${partner.isActive ? 'bg-[#D1FADF] text-[#155E3E]' : 'bg-[#EAECF0] text-[#525252]'} flex w-fit py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                          {partner.isActive ? t('statusCodes.activated') : t('statusCodes.deactivated')}
                                        </div>
                                      </td>
                                      <td className="text-center cursor-default">
                                        <div ref={setSubmenuRef(submenuRef, index)}>
                                          <button id={"partner_list_view" + (index + 1)} onClick={() => setViewPartnersId(index === viewPartnerId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center text-[#191919]`}>
                                            ...
                                          </button>
                                          {viewPartnerId === index && (
                                            <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                              <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewPartnerDetails(partner)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewPartnerDetails(partner))}>
                                                <p id="partner_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                              </div>
                                              <hr className="h-px bg-gray-100 border-0 mx-1" />
                                              <div role='button' className={`flex justify-between hover:bg-gray-100 ${partner.isActive === true ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePartner(partner, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showDeactivatePartner(partner, index))}>
                                                <p id="partner_deactive_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${partner.isActive === true ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                <img src={partner.isActive === true ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                              </div>
                                            </div>
                                          )}
                                          {showActiveIndexDeactivatePopup === index && (
                                            <DeactivatePopup
                                              onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedPartner)}
                                              closePopUp={closeDeactivatePopup}
                                              popupData={{ ...selectedPartner, isDeactivatePartner: true }}
                                              request={deactivateRequest}
                                              headerMsg={t('deactivatePartner.headerMsg', { partnerId: selectedPartner.partnerId, organisationName: selectedPartner.orgName })}
                                              descriptionMsg='deactivatePartner.description'
                                              headerKeyName={selectedPartner.orgName}
                                            />
                                          )}
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

export default PartnersList;
