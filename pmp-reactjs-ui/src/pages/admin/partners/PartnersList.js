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
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import rectangleGrid from "../../../svg/rectangle_grid.svg";
import FilterButtons from "../../common/FilterButtons";
import PartnerListFilter from "./PartnersListFilter";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import { HttpService } from "../../../services/HttpService";
import DeactivatePopup from "../../common/DeactivatePopup";

function PartnersList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [partnersData, setPartnersData] = useState([]);
  const [filteredPartnersData, setFilteredPartnersData] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [firstIndex, setFirstIndex] = useState(0);
  const [viewPartnerId, setViewPartnersId] = useState(-1);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const [sortFieldName, setSortFieldName] = useState("createdDateTime");
  const [sortType, setSortType] = useState("desc");
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(8);
  const [triggerServerMethod, setTriggerServerMethod] = useState(false);
  const [totalRecords, setTotalRecords] = useState(0);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
  const [deactivateRequest, setDeactivateRequest] = useState({});
  const defaultFilterQuery = {
    orgName: "",
    partnerType: "",
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
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

  useEffect(() => {
    const fetchData = async () => {
      const queryParams = new URLSearchParams();
      queryParams.append('sortFieldName', sortFieldName);
      queryParams.append('sortType', sortType);
      queryParams.append('pageNo', pageNo);
      queryParams.append('pageSize', pageSize);

      const url = `${getPartnerManagerUrl('/partners/v3', process.env.NODE_ENV)}?${queryParams.toString()}`;
      try {
        triggerServerMethod ? setTableDataLoaded(false) : setDataLoaded(false);
        const response = await HttpService.get(url);
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            const resData = responseData.response.data;
            setTotalRecords(responseData.response.totalResults);
            setPartnersData(resData);
            setFilteredPartnersData(resData);
          } else {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('partnerList.errorInPartnersList'));
        }
        triggerServerMethod ? setTableDataLoaded(true) : setDataLoaded(true);
        setTriggerServerMethod(false);
      } catch (err) {
        triggerServerMethod ? setTableDataLoaded(true) : setDataLoaded(true);
        console.error('Error fetching data:', err);
        setErrorMsg(err);
      }
    }
    fetchData();
  }, [sortFieldName, sortType, pageNo, pageSize]);
  
  const onApplyFilter = (filters) => {
    console.log('Filters sent from child:', filters);
  };

  const getPaginationValues = (recordsPerPage, pageIndex) => {
    // console.log(recordsPerPage, pageIndex);
    if (pageNo !== pageIndex || pageSize !== recordsPerPage) {
      setPageNo(pageIndex);
      setPageSize(recordsPerPage);
      setTriggerServerMethod(true);
    }
  }

  const viewPartnerDetails = (selectedPartnerData) => {
    localStorage.setItem('selectedPartnerId', selectedPartnerData.partnerId);
    navigate('/partnermanagement/admin/viewPartnerDetails')
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    if (order !== 'ASC' || activeSortAsc !== header) {
      setTriggerServerMethod(true);
      setSortFieldName((header === 'status') ? 'isActive' : header);
      setSortType("desc");
      setOrder("ASC");
      setActiveSortDesc("");
      setActiveSortAsc(header);
    }
  };

  const sortDescOrder = (header) => {
    if (order !== 'DESC' || activeSortDesc !== header) {
      setTriggerServerMethod(true);
      setSortFieldName((header === 'status') ? 'isActive' : header);
      setSortType("asc");
      setOrder("DESC");
      setActiveSortDesc(header);
      setActiveSortAsc("");
    }
  };

  const style = {
    backArrowIcon: "!mt-[9%]",
  };

  useEffect(() => {
    let filteredRows = partnersData;
    Object.keys(filterQuery).forEach((key) => {
      if (filterQuery[key] !== "") {
        filteredRows = filteredRows.filter(
          (item) => item[key] === filterQuery[key]
        );
      }
    });
    setFilteredPartnersData(filteredRows);
    setFirstIndex(0);
  }, [filterQuery, partnersData]);

  const onResetFilter = () => {
    window.location.reload();
  };

  //This part is related to Filter
  const onFilterChange = (fieldName, selectedFilter) => {
    setFilterQuery((oldFilterQuery) => ({
      ...oldFilterQuery,
      [fieldName]: selectedFilter,
    }));
    //useEffect will be triggered which will do the filter
  };

  const showDeactivatePartner = (selectedPartnerdata) => {
    if (selectedPartnerdata.isActive === true) {
      const request = createRequest({
        status: "De-Active"
      });
      setDeactivateRequest(request);
      setShowDeactivatePopup(true);
      document.body.style.overflow = "hidden";
    }
  };

  const closeDeactivatePopup = () => {
    setViewPartnersId(-1);
    setShowDeactivatePopup(false);
  }

  const styles = {
    loadingDiv: "!py-[20%]"
  }

  return (
    <div
      className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"
        } overflow-x-scroll font-inter`}
    >
      {!dataLoaded && <LoadingIcon></LoadingIcon>}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-7">
            <div className="flex justify-between mb-3">
              <Title title="partnerList.partnerTitle" subTitle2="partnerList.partnerTitle" backLink="/partnermanagement" styleSet={style} />
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {partnersData.length === 0 ? (
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
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    <div className="flex justify-between mt-5">
                      <div className="flex w-full justify-between font-[400] text-[14px]">
                        <h6 className="ml-5 mr-3"> {t("partnerList.partnerId")}</h6>
                        <h6>{t("partnerList.partnerType")}</h6>
                        <h6>{t("partnerList.organisation")}</h6>
                        <h6>{t("partnerList.policyGroup")}</h6>
                        <h6>{t("partnerList.email")}</h6>
                        <h6>{t("partnerList.certUploadStatus")}</h6>
                        <h6>{t("partnerList.status")}</h6>
                        <h6 className="mx-4">{t("partnerList.action")}</h6>
                      </div>
                    </div>
                  </div>

                  <hr className="h-px mx-3 bg-gray-200 border-0" />

                  <div className="flex items-center justify-center p-24">
                    <div className="flex flex-col items-center">
                      <img src={rectangleGrid} alt="" />
                      <p className="text-[#A1A1A1] mt-3">{t("partnerList.noData")}</p>
                    </div>
                  </div>
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
                    {tableDataLoaded && (
                      <div className="mx-[2%] overflow-x-scroll">
                        <table className="table-fixed">
                          <thead>
                            <tr>
                              {tableHeaders.map((header, index) => {
                                return (
                                  <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[15%]">
                                    <div className="mx-2 flex gap-x-0 items-center">
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
                            {filteredPartnersData.map((partner, index) => {
                              return (
                                <tr id={"partner_list_item" + (index + 1)} key={index}
                                  className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${partner.isActive === false ? "text-[#969696]" : "text-[#191919]"}`}>
                                  <td onClick={() => viewPartnerDetails(partner)} className="px-2 break-all">{partner.partnerId}</td>
                                  <td onClick={() => viewPartnerDetails(partner)} className="px-2 break-all">{partner.partnerType}</td>
                                  <td onClick={() => viewPartnerDetails(partner)} className="px-2 break-all">{partner.orgName}</td>
                                  <td onClick={() => viewPartnerDetails(partner)} className="px-2 break-all">{partner.policyGroupName ? partner.policyGroupName : "-"}</td>
                                  <td onClick={() => viewPartnerDetails(partner)} className="px-2 break-all">{partner.emailAddress}</td>
                                  <td onClick={() => viewPartnerDetails(partner)} className={`px-3 break-all ${partner.certificateUploadStatus === 'not_uploaded' && "text-[#BE1818]"}`}>
                                    {getStatusCode(partner.certificateUploadStatus, t)}
                                  </td>
                                  <td onClick={() => viewPartnerDetails(partner)} className="break-all">
                                    <div className={`${partner.isActive ? 'bg-[#D1FADF] text-[#155E3E]' : 'bg-[#EAECF0] text-[#525252]'} flex w-fit py-1.5 px-2 m-3 text-xs font-semibold rounded-md`}>
                                      {partner.isActive ? t('statusCodes.activated') : t('statusCodes.deactivated')}
                                    </div>
                                  </td>
                                  <td className="text-center break-all">
                                    <div ref={(el) => (submenuRef.current[index] = el)}>
                                      <p id={"partner_list_view" + (index + 1)} onClick={() => setViewPartnersId(index === viewPartnerId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                        tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewPartnersId(index === viewPartnerId ? null : index))}
                                      >
                                        ...
                                      </p>
                                      {viewPartnerId === index && (
                                        <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                          <p id="partner_details_view_btn" onClick={() => viewPartnerDetails(partner)} className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}
                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => viewPartnerDetails(partner))}
                                          >
                                            {t("partnerList.view")}
                                          </p>
                                          <hr className="h-px bg-gray-100 border-0 mx-1" />
                                          <p id="partner_deactive_btn" onClick={() => showDeactivatePartner(partner)} className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${partner.isActive === true ? "text-crimson-red hover:bg-gray-100 cursor-pointer" : "text-[#A5A5A5] cursor-default"}`}
                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivatePartner(partner))}
                                          >
                                            {t("partnerList.deActivate")}
                                          </p>
                                          {showDeactivatePopup && (
                                            < DeactivatePopup
                                              closePopUp={closeDeactivatePopup}
                                              popupData={{ ...partner, isDeactivatePartner: true }}
                                              request={deactivateRequest}
                                              headerMsg={t('deactivatePartner.headerMsg', { partnerId: partner.partnerId, organizationName: partner.orgName })}
                                              descriptionMsg='deactivatePartner.description'
                                              headerKeyName={partner.orgName}
                                            />
                                          )}
                                        </div>
                                      )}
                                    </div>
                                  </td>
                                </tr>
                              );
                            })}
                          </tbody>
                        </table>
                      </div>
                    )}
                  </div>
                  <Pagination
                    dataListLength={totalRecords}
                    selectedRecordsPerPage={selectedRecordsPerPage}
                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                    setFirstIndex={setFirstIndex}
                    isServerSideFilter={true}
                    getPaginationValues={getPaginationValues}
                  ></Pagination>
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
