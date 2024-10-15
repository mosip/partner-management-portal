import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, onPressEnterKey, createRequest, getPartnerManagerUrl } from "../../../utils/AppUtils";
import {
  formatDate,
  getStatusCode,
  handleMouseClickForDropdown,
  toggleSortAscOrder,
  toggleSortDescOrder,
  bgOfStatus,
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

function PartnersList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const langCode = getUserProfile().langCode
  const isLoginLanguageRTL = isLangRTL(langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const [partnersData, setPartnersData] = useState([]);
  const [filteredPartnersData, setFilteredPartnersData] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("timeOfUpload");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [firstIndex, setFirstIndex] = useState(0);
  const [isDescending, setIsDescending] = useState(false);
  const [viewPartnerId, setViewPartnersId] = useState(-1);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const defaultFilterQuery = {
    orgName: "",
    partnerType: "",
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);
  const [serverRequest, setServerRequest] = useState({"filters": [], "sort": [], "pagination": {"pageStart":firstIndex, "pageFetch":selectedRecordsPerPage}, "partnerType": 'all'});
  const [triggerServerMethod, setTriggerServerMethod] = useState(false);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewPartnersId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "partnerID", headerNameKey: "partnerList.partnerId" },
    { id: "partnerType", headerNameKey: "partnerList.partnerType" },
    { id: "orgName", headerNameKey: "partnerList.organisation" },
    { id: "emailID", headerNameKey: "partnerList.email" },
    { id: "policyGroup", headerNameKey: "partnerList.policyGroup" },
    { id: "certUploadStatus", headerNameKey: "partnerList.certUploadStatus" },
    { id: "status", headerNameKey: "partnerList.status" },
    { id: "action", headerNameKey: "partnerList.action" },
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);

        const partnersDummyData = [
          {
            partnerID: "P23423029",
            orgName: "MOSIP",
            partnerType: "DEVICE_Provider",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423089",
            orgName: "MOSIP444454",
            partnerType: "MANUAL_ADJUDICATION",
            emailID: "abhnc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "deactivated",
          },
          {
            partnerID: "P23425029",
            orgName: "MOSIP4353243",
            partnerType: "MANUAL_ADJUDICATION",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "notUploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423529",
            orgName: "MOSIP5423",
            partnerType: "MANUAL_ADJUDICATION",
            emailID: "asbc@mock.co.in",
            policyGroup: "mpolicfccygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP3434",
            partnerType: "INTERNAL_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "notUploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP3434",
            partnerType: "PRINT_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "deactivated",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP33343",
            partnerType: "SDK_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "deactivated",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP34324",
            partnerType: "MISP_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "notUploaded",
            status: "deactivated",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP23434",
            partnerType: "ABIS_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP3243",
            partnerType: "ONLINE_VERIFICATION_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "notUploaded",
            status: "deactivated",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP343",
            partnerType: "CREDENTIAL_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP324444",
            partnerType: "AUTH_PARTNER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "notUploaded",
            status: "ACTIVE",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP3432",
            partnerType: "FTM_PROVIDER",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "uploaded",
            status: "deactivated",
          },
          {
            partnerID: "P23423029",
            orgName: "MOSIP123",
            partnerType: "DEVICE_Provider",
            emailID: "abc@mock.co.in",
            policyGroup: "mpolicygroup-default-cert",
            certUploadStatus: "notUploaded",
            status: "ACTIVE",
          },
        ];

        const sortedData = partnersDummyData.sort(
          (a, b) => new Date(b.certExpiryDate) - new Date(a.certExpiryDate)
        );
        setPartnersData(sortedData);
        setFilteredPartnersData(sortedData);
        setDataLoaded(true);
      } catch (err) {
        console.error("Error fetching data:", err);
        setErrorMsg(err);
      }
    };
    fetchData();
  }, []);

  const showPartnerDetails = (selectedPartnerData) => {};

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  // server side filter code
  useEffect(() =>{
    const request = createRequest({...serverRequest});
    try {
      if(triggerServerMethod){
        setTableDataLoaded(false)
        const apiResp = HttpService.post(getPartnerManagerUrl("/partners/search", process.env.NODE_ENV), request);
        apiResp.then(res =>{
          if(res.data.response){
            let filteredData = res.data.response
            setTableDataLoaded(true)
            // setFilteredPartnersData(res)
          }else{
            setTableDataLoaded(true)
            if (res.data.errors[0].message === null) {
              setErrorMsg(t('partnerList.unableToFilter'));
            }else{
              const errorCode = res.data.errors[0].errorCode;
              const errorMessage = res.data.errors[0].message;
              setErrorCode(errorCode)
              setErrorMsg(errorMessage);
            }
          }
          setTriggerServerMethod(false)
        })
      }
    } catch (error) {
      setTableDataLoaded(true);
      setErrorMsg(error);
      console.log("Unable to filter partners data" + error)
    }
    
  }, [serverRequest]);

  const getPaginationValues = (recordsPerPage, pageIndex) =>{
    setTriggerServerMethod(true);
    if(firstIndex !== pageIndex || selectedRecordsPerPage !== recordsPerPage)
      setServerRequest(prevData => ({...prevData, pagination:{pageStart: pageIndex, pageFetch: recordsPerPage}}))
  }

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    setTriggerServerMethod(true);
    const isDateCol = header === "timeOfUpload" ? true : false;
    const isServerRequest = true;
    setServerRequest(prevData => ({...prevData, sort:[{sortField: 'id',sortType: 'asc'}]}))
    toggleSortAscOrder(
      header,
      isDateCol,
      filteredPartnersData,
      setFilteredPartnersData,
      order,
      setOrder,
      isDescending,
      setIsDescending,
      activeSortAsc,
      setActiveSortAsc,
      activeSortDesc,
      setActiveSortDesc,
      isServerRequest
    );
  };

  const sortDescOrder = (header) => {
    setTriggerServerMethod(true);
    const isDateCol = header === "timeOfUpload" ? true : false;
    const isServerRequest = true;
    setServerRequest(prevData => ({...prevData, sort:[{sortField: 'id',sortType: 'desc'}]}))
    toggleSortDescOrder(
      header,
      isDateCol,
      filteredPartnersData,
      setFilteredPartnersData,
      order,
      setOrder,
      isDescending,
      setIsDescending,
      activeSortAsc,
      setActiveSortAsc,
      activeSortDesc,
      setActiveSortDesc,
      isServerRequest
    );
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

  //This  part related to Pagination logic
  let tableRows = filteredPartnersData.slice(
    firstIndex,
    firstIndex + selectedRecordsPerPage
  );

  //This part is related to Filter
  const onFilterChange = (fieldName, selectedFilter) => {
    setFilterQuery((oldFilterQuery) => ({
      ...oldFilterQuery,
      [fieldName]: selectedFilter,
    }));
    //useEffect will be triggered which will do the filter
  };

  const showDeactivatePartner = (selectedClientdata) => {
    if (selectedClientdata.status === "ACTIVE") {
      document.body.style.overflow = "hidden";
    }
  };

  return (
    <div
      className={`mt-2 w-[100%] ${
        isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"
      } overflow-x-scroll font-inter`}
    >
      {!dataLoaded && <LoadingIcon></LoadingIcon>}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={cancelErrorMsg}
            />
          )}
          <div className="flex-col mt-7">
            <div className="flex justify-between mb-3">
              <Title
                title="partnerList.partnerTitle"
                subTitle2="partnerList.partnerTitle"
                backLink="/partnermanagement"
                styleSet={style}
              />
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {partnersData.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                    <div className="flex w-full px-2">
                      <div className="flex w-full pl-[2%] pt-1 items-center justify-start font-semibold text-dark-blue text-base">
                        {t("partnerList.listOfPartnerTitle")}
                      </div>
                      <button
                        disabled
                        type="button"
                        className={`flex justify-center items-center w-[14%] text-sm py-2 mt-2 border border-[#D0D0D0] font-semibold rounded-md text-center min-w-fit px-2 bg-transparent text-[#D0D0D0] cursor-auto ${
                          isLoginLanguageRTL ? "mr-3" : "ml-3"
                        }`}
                      >
                        {t("commons.filterBtn")}
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          className={`${isLoginLanguageRTL ? "mr-2" : "ml-2"}`}
                          width="10"
                          height="8"
                          viewBox="0 0 10 8"
                        >
                          <path
                            id="Polygon_8"
                            data-name="Polygon 8"
                            d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                            transform="translate(10 8) rotate(180)"
                            fill="#D0D0D0"
                          />
                        </svg>
                      </button>
                    </div>
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    <div className="flex justify-between mt-5">
                      <div className="flex w-full justify-between font-[400] text-[14px]">
                        <h6 className="ml-5 mr-3">
                          {t("partnerList.partnerId")}
                        </h6>
                        <h6>{t("partnerList.partnerType")}</h6>
                        <h6>{t("partnerList.organisation")}</h6>
                        <h6>{t("partnerList.policyGroup")}</h6>
                        <h6>{t("partnerList.email")}</h6>
                        <h6>{t("partnerList.certExpiryDate")}</h6>
                        <h6>{t("partnerList.status")}</h6>
                        <h6 className="mx-4">{t("partnerList.action")}</h6>
                      </div>
                    </div>
                  </div>
                  <hr className="h-px mx-3 bg-gray-200 border-0" />
                  <div className="flex items-center justify-center p-24">
                    <div className="flex flex-col items-center">
                      <img src={rectangleGrid} alt="" />
                      <p className="text-[#A1A1A1] mt-3">
                        {t("partnerList.noData")}
                      </p>
                    </div>
                  </div>
                </div>
              ) : (
                <>
                  <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "pb-6"}`}>
                    <FilterButtons
                      listTitle="partnerList.listOfPartnerTitle"
                      dataList={filteredPartnersData}
                      filter={filter}
                      onResetFilter={onResetFilter}
                      setFilter={setFilter}
                    ></FilterButtons>
                    {tableDataLoaded && <hr className="h-0.5 mt-3 bg-gray-200 border-0" />}
                    {filter && (
                      <PartnerListFilter
                        filteredPartnersData={filteredPartnersData}
                        onFilterChange={onFilterChange}
                      ></PartnerListFilter>
                    )}

                  {!tableDataLoaded && <LoadingIcon></LoadingIcon>}
                  {tableDataLoaded && (
                    <div className="mx-[2%] overflow-x-scroll">
                      <table className="table-fixed">
                        <thead>
                          <tr>
                            {tableHeaders.map((header, index) => {
                              return (
                                <th
                                  key={index}
                                  className="py-4 text-sm font-semibold text-[#6F6E6E] w-[15%]"
                                >
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
                                      ></SortingIcon>
                                    )}
                                  </div>
                                </th>
                              );
                            })}
                          </tr>
                        </thead>
                        <tbody>
                          {tableRows.map((partner, index) => {
                            return (
                              <tr
                                id={"partner_list_item" + (index + 1)}
                                key={index}
                                className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${
                                  partner.status.toLowerCase() ==="deactivated"
                                    ? "text-[#969696]"
                                    : "text-[#191919]"
                                }`}
                              >
                                <td
                                  onClick={() => showPartnerDetails(partner)}
                                  className="px-2 break-all"
                                >
                                  {partner.partnerID}
                                </td>
                                <td
                                  onClick={() => showPartnerDetails(partner)}
                                  className="px-2 break-all"
                                >
                                  {partner.partnerType}
                                </td>
                                <td
                                  onClick={() => showPartnerDetails(partner)}
                                  className="px-2 break-all"
                                >
                                  {partner.orgName}
                                </td>
                                <td
                                  onClick={() => showPartnerDetails(partner)}
                                  className="px-2 break-all"
                                >
                                  {partner.emailID}
                                </td>
                                <td
                                  onClick={() => showPartnerDetails(partner)}
                                  className="px-2 break-all"
                                >
                                  {partner.policyGroup}
                                </td>
                                <td
                                  onClick={() =>
                                    showPartnerDetails(partner)
                                  }
                                  className={`px-3 break-all ${langCode === 'fra' && 'max-1200:text-start max-1950:text-center'} ${partner.certUploadStatus === "notUploaded" && "text-[#BE1818]"}`}
                                >
                                  {getStatusCode(partner.certUploadStatus, t)}
                                </td>
                                <td
                                  onClick={() =>
                                    showPartnerDetails(partner)
                                  }
                                  className="break-all"
                                >
                                  <div
                                    className={`${bgOfStatus(
                                      partner.status
                                    )} flex w-fit py-1.5 px-2 m-3 text-xs font-semibold rounded-md`}
                                  >
                                    {getStatusCode(partner.status, t)}
                                  </div>
                                </td>
                                <td className="text-center break-all">
                                  <div
                                    ref={(el) =>
                                      (submenuRef.current[index] = el)
                                    }
                                  >
                                    <p
                                      id={"partner_list_view" + (index + 1)}
                                      onClick={() =>
                                        setViewPartnersId(
                                          index === viewPartnerId
                                            ? null
                                            : index
                                        )
                                      }
                                      className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                      tabIndex="0"
                                      onKeyPress={(e) =>
                                        onPressEnterKey(e, () =>
                                          setViewPartnersId(
                                            index === viewPartnerId
                                              ? null
                                              : index
                                          )
                                        )
                                      }
                                    >
                                      ...
                                    </p>
                                    {viewPartnerId === index && (
                                      <div
                                        className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${
                                          isLoginLanguageRTL
                                            ? "left-9 text-right"
                                            : "right-9 text-left"
                                        }`}
                                      >
                                        <p
                                          id="partner_details_view_btn"
                                          onClick={() =>
                                            showPartnerDetails(partner)
                                          }
                                          className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${
                                            isLoginLanguageRTL
                                              ? "pl-10"
                                              : "pr-10"
                                          }`}
                                          tabIndex="0"
                                          onKeyPress={(e) =>
                                            onPressEnterKey(e, () =>
                                              showPartnerDetails(partner)
                                            )
                                          }
                                        >
                                          {t("partnerList.view")}
                                        </p>
                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                        <p
                                          id="partner_deactive_btn"
                                          onClick={() =>
                                            showDeactivatePartner(partner)
                                          }
                                          className={`py-1.5 px-4 ${
                                            isLoginLanguageRTL
                                              ? "pl-10"
                                              : "pr-10"
                                          } ${
                                            partner.status === "approved"
                                              ? "text-crimson-red cursor-pointer"
                                              : "text-[#A5A5A5] cursor-auto"
                                          } hover:bg-gray-100`}
                                          tabIndex="0"
                                          onKeyPress={(e) =>
                                            onPressEnterKey(e, () =>
                                              showDeactivatePartner(partner)
                                            )
                                          }
                                        >
                                          {t("partnerList.deActivate")}
                                        </p>
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
                    dataList={filteredPartnersData}
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
