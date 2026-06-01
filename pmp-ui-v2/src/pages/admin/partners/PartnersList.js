import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createRequest, isLangRTL, onPressEnterKey } from "../../../utils/AppUtils";
import {
  getPartnerManagerUrl,
  handleServiceErrors,
  getStatusCode,
  handleMouseClickForDropdown,
  getPartnerTypeDescription,
  getPartnerDomainType,
  resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize, onResetFilter, setSubmenuRef,
  fetchPartnerDetails
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import SuccessMessage from "../../common/SuccessMessage";
import Title from "../../common/Title";
import FilterButtons from "../../common/FilterButtons";
import PartnerListFilter from "./PartnersListFilter";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import { HttpService } from "../../../services/HttpService";
import DeactivatePopup from "../../common/DeactivatePopup";
import UploadCertificate from "../../partner/certificates/UploadCertificate";
import SelectPolicyPopup from "../../dashboard/SelectPolicyPopup";
import viewIcon from "../../../svg/view_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import EmptyList from "../../common/EmptyList";
import selectPolicyGroupIcon from "../../../svg/admin_select_policy_group_icon.svg";
import uploadOrReuploadIcon from "../../../svg/admin_partner_certicate_upload_or_reupload_icon.svg";
import disableUploadOrReuploadIcon from "../../../svg/admin_disabled_upload_or_reupload_icon.svg";
import disableSelectPolicyGroupIcon from "../../../svg/admin_disabled_select_policy_group_icon.svg";

function PartnersList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [partnersData, setPartnersData] = useState([]);
  const [order, setOrder] = useState("DESC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [viewPartnerId, setViewPartnersId] = useState(-1);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
  const [sortFieldName, setSortFieldName] = useState("createdDateTime");
  const [sortType, setSortType] = useState("desc");
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
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

  const [uploadCertificateData, setUploadCertificateData] = useState({});
  const [uploadCertificateRequest, setUploadCertificateRequest] = useState({});
  const [showActiveindexUploadCertificatePopup, setShowActiveindexUploadCertificatePopup] = useState(null);
  const [showActiveindexSelectPolicyGroupPopup, setShowActiveindexSelectPolicyGroupPopup] = useState(null);
  const [fetchingPartnerDetails, setFetchingPartnerDetails] = useState(null);
  const [selectedPartnerForPolicyGroup, setSelectedPartnerForPolicyGroup] = useState({});

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
      if (filters.status === 'active') queryParams.append('status', 'active');
      else if (filters.status === 'deactivated') queryParams.append('status', 'deactivated');
      else if (filters.status === 'partner_inactive') queryParams.append('status', 'inactive');
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

    if (isApplyFilterClicked && pageNo === 0) {
      fetchPartnersData();
      setIsApplyFilterClicked(false);
    }
  }, [isApplyFilterClicked]);

  const onApplyFilter = (filters) => {
    setSuccessMsg("");
    onClickApplyFilter(filters, setIsFilterApplied, setResetPageNo, setTriggerServerMethod, setFilters, setIsApplyFilterClicked);
  };

  const getPaginationValues = (recordsPerPage, pageIndex) => {
    setSuccessMsg("");
    setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setTriggerServerMethod);
  }

  const viewPartnerDetails = (selectedPartnerData) => {
    setSuccessMsg("");
    sessionStorage.setItem('selectedPartnerId', selectedPartnerData.partnerId);
    navigate('/partnermanagement/admin/partners/view-partner-details')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const cancelSuccessMsg = () => {
    setSuccessMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    if (order !== 'ASC' || activeSortAsc !== header) {
      setSuccessMsg("");
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
      setSuccessMsg("");
      setTriggerServerMethod(true);
      setSortFieldName(header);
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
    // Deactivate is only enabled for Active partners
    if (selectedPartnerdata.status === 'active') {
      setSuccessMsg("");
      const request = createRequest({
        status: "De-Active"
      });
      setSelectedPartner(selectedPartnerdata);
      setViewPartnersId(-1);
      setDeactivateRequest(request);
      setShowActiveIndexDeactivatePopup(index);
    }
  };

  const isDeactivateEnabled = (partner) => {
    return partner.status === 'active';
  };

  const isUploadCertificateEnabled = (partner) => {
    // Upload certificate is enabled for MISP_Partner, ABIS_Partner, Manual_Adjudication and Online_Verification_Partner
    // Disabled for deactivated state:
    return (partner.partnerType === "MISP_Partner" ||
      partner.partnerType === "ABIS_Partner" ||
      partner.partnerType === "Manual_Adjudication" ||
      partner.partnerType === "Online_Verification_Partner") &&
      !(partner.status === 'deactivated');
  };

  const isSelectPolicyGroupEnabled = (partner) => {
    // Select policy group is enabled for MISP_Partner when:
    // Disabled for Deactivated partners or when policy group is already selected
    return partner.partnerType === "MISP_Partner" &&
      partner.policyGroupId === null &&
      !(partner.status === 'deactivated');
  };

  const getPartnerStatusBgColor = (partner) => {
    if (partner.status === "inactive") {
      return 'bg-[#DFE9FF] text-[#384B75]';
    }
    else if (partner.status === 'deactivated') {
      return 'bg-[#EAECF0] text-[#525252]';
    } else {
      return 'bg-[#D1FADF] text-[#155E3E]';
    }
  };

  const getPartnerStatusText = (partner, t) => {
    if (partner.status === "inactive") {
      return t('statusCodes.inactive');
    } else if (partner.status === 'deactivated') {
      return t('statusCodes.deactivated');
    } else {
      return t('statusCodes.active');
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
          partner.partnerId === selectedPartnerData.partnerId ? { ...partner, status: 'deactivated' } : partner
        )
      );
    }
  };

  const createPartner = () => {
    setSuccessMsg("");
    navigate('/partnermanagement/admin/partners/create-partner');
  };

  // Upload/Reupload Certificate
  const handleUploadCertificate = async (partner, index) => {
    if (!isUploadCertificateEnabled(partner)) return;

    setSuccessMsg("");
    const isUploaded = partner.certificateUploadStatus === "uploaded";

    // Default to null; only fetch details if already uploaded
    let certificateUploadDateTime = null;
    if (isUploaded) {
      // Set loading state for this specific partner
      setFetchingPartnerDetails(index);

      const partnerDetails = await fetchPartnerDetails(
        HttpService,
        partner.partnerId,
        setErrorCode,
        setErrorMsg,
        t
      );
      certificateUploadDateTime = partnerDetails?.certificateUploadDateTime;
      setFetchingPartnerDetails(null);
    }

    // Prepare request payload
    const request = {
      partnerId: partner.partnerId,
      partnerDomain: getPartnerDomainType(partner.partnerType),
    };

    // Prepare certificate popup data
    const certificateData = {
      partnerType: partner.partnerType,
      uploadHeader: "uploadCertificate.uploadPartnerCertificate",
      isUploadPartnerCertificate: true,
      reUploadHeader: "uploadCertificate.reUploadPartnerCertificate",
      isCertificateAvailable: isUploaded,
      certificateUploadDateTime: certificateUploadDateTime,
      showPartnerIdSubtitle: partner.partnerType === "MISP_Partner" ||
        partner.partnerType === "ABIS_Partner" ||
        partner.partnerType === "Manual_Adjudication" ||
        partner.partnerType === "Online_Verification_Partner"
    };

    // Update UI state
    setShowActiveindexUploadCertificatePopup(index);
    setUploadCertificateRequest(request);
    setUploadCertificateData(certificateData);
    setViewPartnersId(-1);
  };

  // Select Policy Group
  const handleSelectPolicyGroup = (partner, index) => {
    if (isSelectPolicyGroupEnabled(partner)) {
      setSuccessMsg("");
      setSelectedPartnerForPolicyGroup(partner);
      setShowActiveindexSelectPolicyGroupPopup(index);
      setViewPartnersId(-1);
    }
  };

  // Close Upload Certificate Popup
  const closeUploadCertificatePopup = (state, btnName) => {
    if (state && btnName === 'cancel') {
      setShowActiveindexUploadCertificatePopup(null);
    } else if (state && btnName === 'close') {
      setShowActiveindexUploadCertificatePopup(null);
      // Update the specific row in the state with the new certificate status
      if (showActiveindexUploadCertificatePopup !== null) {
        setPartnersData((prevList) =>
          prevList.map((partner, index) =>
            index === showActiveindexUploadCertificatePopup ? {
              ...partner,
              certificateUploadStatus: 'uploaded',
              status: 'active'
            } : partner
          )
        );
      }
    }
  };

  // Close Select Policy Group Popup
  const closeSelectPolicyGroupPopup = () => {
    setShowActiveindexSelectPolicyGroupPopup(null);
    setSelectedPartnerForPolicyGroup({});
  };

  // Handle Select Policy Group Submit
  const onClickConfirmSelectPolicyGroup = (policyGroupResponse, selectedPartnerData) => {
    if (policyGroupResponse && policyGroupResponse.response) {
      setShowActiveindexSelectPolicyGroupPopup(null);
      setSelectedPartnerForPolicyGroup({});

      // Show success message with partner ID
      setSuccessMsg(t('partnerList.policyGroupLinkedSuccessMsg', { partnerId: selectedPartnerData.partnerId }));

      // Update the specific row in the state with the new policy group name
      setPartnersData((prevList) =>
        prevList.map(partner =>
          partner.partnerId === selectedPartnerData.partnerId
            ? {
              ...partner,
              policyGroupName: policyGroupResponse.response.policyGroupName,
              policyGroupId: policyGroupResponse.response.policyGroupId
            }
            : partner
        )
      );
    }
  };

  const styles = {
    loadingDiv: "!py-[20%]"
  }

  const successCustomStyle = {
    outerDiv: `flex justify-end w-full max-w-md my-3 absolute ${isLoginLanguageRTL ? "left-4" : "right-4"}`,
    innerDiv: "flex justify-between items-center rounded-xl w-full max-w-md min-h-14 min-w-72 p-4",
    cancelIcon: "!mt-3 !top-1"
  }

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage id='partners_list_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          {successMsg && (
            <SuccessMessage id='partners_list_success_msg' successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={successCustomStyle} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between mb-3">
              <Title title="partnerList.partnerTitle" backLink="/partnermanagement" styleSet={style} />
              {partnersData.length > 0 || isFilterApplied ?
                <button id='create_partner_btn' onClick={() => createPartner()} type="button" className={`h-10 text-sm font-semibold text-white px-7 rounded-md bg-tory-blue`}>
                  {t('partnerList.createPartner')}
                </button>
                : null
              }
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {!isFilterApplied && partnersData.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                    <div className="flex w-full px-2">
                      <div id='partners_list_sub_title' className="flex w-full pl-[2%] pt-1 items-center justify-start font-semibold text-dark-blue text-base">
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
                  <EmptyList
                    tableHeaders={tableHeaders}
                    showCustomButton={true}
                    customButtonName='partnerList.createMispPartner'
                    buttonId='create_partner_empty_btn'
                    onClickButton={createPartner}
                    disableBtn={false} />
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
                    {!tableDataLoaded ? (
                      <LoadingIcon styleSet={styles} />
                    ) : (
                      <>
                        {isFilterApplied && partnersData.length === 0 ?
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
                                            <div id={`${header.headerNameKey}_header`} className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                              {t(header.headerNameKey)}
                                              {!["action", "emailAddress"].includes(header.id) && (
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
                                          </th>);
                                      })}
                                    </tr>
                                  </thead>
                                  <tbody>
                                    {partnersData.map((partner, index) => {
                                      return (
                                        <tr id={"partner_list_item" + (index + 1)} key={index}
                                          className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words ${partner.status === 'deactivated' ? "text-[#969696]" : "text-[#191919] cursor-pointer"}`}>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)} className={`px-2`}>{partner.partnerId}</td>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)} className={`px-2`}>{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)} className={`px-2`}>{partner.orgName}</td>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)} className={`px-2`}>{partner.policyGroupName ? partner.policyGroupName : "-"}</td>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)} className={`px-2`}>{partner.emailAddress}</td>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)} className={`px-3 whitespace-nowrap ${partner.certificateUploadStatus === 'not_uploaded' && "text-[#BE1818]"}`}>
                                            {getStatusCode(partner.certificateUploadStatus, t)}
                                          </td>
                                          <td onClick={() => partner.status !== 'deactivated' && viewPartnerDetails(partner)}>
                                            <div className={`${getPartnerStatusBgColor(partner)} flex w-fit py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                              {getPartnerStatusText(partner, t)}
                                            </div>
                                          </td>
                                          <td className="text-center cursor-default my-3">
                                            <div ref={setSubmenuRef(submenuRef, index)}>
                                              <button id={"partner_list_view" + (index + 1)} onClick={() => setViewPartnersId(index === viewPartnerId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                ...
                                              </button>
                                              {viewPartnerId === index && (
                                                <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                                  <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewPartnerDetails(partner)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewPartnerDetails(partner))}>
                                                    <p id="partner_details_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                    <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                  </div>
                                                  <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                  <div role='button' className={`flex justify-between hover:bg-gray-100 ${isUploadCertificateEnabled(partner) && fetchingPartnerDetails !== index ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => handleUploadCertificate(partner, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => handleUploadCertificate(partner, index))}>
                                                    <p id="partner_upload_certificate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${isUploadCertificateEnabled(partner) && fetchingPartnerDetails !== index ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>
                                                      {t("partnerList.uploadOrReuploadCertificate")}
                                                    </p>
                                                    {fetchingPartnerDetails === index ? (
                                                      <LoadingIcon inline={true} styleSet={{ loadingDiv: `${isLoginLanguageRTL ? "pl-2" : "pr-2"}` }} />
                                                    ) : (
                                                      <img src={isUploadCertificateEnabled(partner) ? uploadOrReuploadIcon : disableUploadOrReuploadIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                    )}
                                                  </div>
                                                  <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                  <div role='button' className={`flex justify-between hover:bg-gray-100 ${isSelectPolicyGroupEnabled(partner) ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => handleSelectPolicyGroup(partner, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => handleSelectPolicyGroup(partner, index))}>
                                                    <p id="partner_select_policy_group_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${isSelectPolicyGroupEnabled(partner) ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>
                                                      {t("partnerList.selectPolicyGroup")}
                                                    </p>
                                                    <img src={isSelectPolicyGroupEnabled(partner) ? selectPolicyGroupIcon : disableSelectPolicyGroupIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                  </div>
                                                  <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                  <div role='button' className={`flex justify-between hover:bg-gray-100 ${isDeactivateEnabled(partner) ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => showDeactivatePartner(partner, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => showDeactivatePartner(partner, index))}>
                                                    <p id="partner_deactive_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${isDeactivateEnabled(partner) ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                    <img src={isDeactivateEnabled(partner) ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
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
                                              {showActiveindexUploadCertificatePopup === index && (
                                                <UploadCertificate
                                                  closePopup={closeUploadCertificatePopup}
                                                  popupData={uploadCertificateData}
                                                  request={uploadCertificateRequest}
                                                />
                                              )}
                                              {showActiveindexSelectPolicyGroupPopup === index && (
                                                <SelectPolicyPopup
                                                  isLinkPolicyGroupPopup={true}
                                                  partnerId={selectedPartnerForPolicyGroup.partnerId}
                                                  partnerType={selectedPartnerForPolicyGroup.partnerType}
                                                  onClose={closeSelectPolicyGroupPopup}
                                                  onSubmit={(policyGroupResponse) => onClickConfirmSelectPolicyGroup(policyGroupResponse, selectedPartnerForPolicyGroup)}
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
                            </>
                          )}
                      </>
                    )}
                    <Pagination
                      dataListLength={totalRecords}
                      selectedRecordsPerPage={selectedRecordsPerPage}
                      setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                      isServerSideFilter={true}
                      getPaginationValues={getPaginationValues}
                      isApplyFilterClicked={isApplyFilterClicked}
                      setIsApplyFilterClicked={setIsApplyFilterClicked}
                    />
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
