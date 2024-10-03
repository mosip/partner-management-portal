import { useState, useEffect, useRef } from "react";
// import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../services/UserProfileService";
import { isLangRTL, onPressEnterKey } from "../../utils/AppUtils";
import {
  // getPartnerManagerUrl,
  formatDate,
  // handleServiceErrors,
  // getPartnerTypeDescription,
  getStatusCode,
  handleMouseClickForDropdown,
  toggleSortAscOrder,
  toggleSortDescOrder,
  bgOfStatus,
} from "../../utils/AppUtils";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import Title from "../common/Title";
import rectangleGrid from "../../svg/rectangle_grid.svg";
import FilterButtons from "../common/FilterButtons";
import RootTrustFilter from "./RootTrustFilter";
import SortingIcon from "../common/SortingIcon";
import Pagination from "../common/Pagination";
// import DeactivatePopup from "../common/DeactivatePopup";

function RootTrustCertificateList() {
  const { t } = useTranslation();
  const [filter, setFilter] = useState(false);
  // const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [certificateData, setCertificateData] = useState([]);
  const [filteredCertificateData, setFilteredCertificateData] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("createdDateTime");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [firstIndex, setFirstIndex] = useState(0);
  const [isDescending, setIsDescending] = useState(false);
  const [viewCertificateId, setViewCertificateId] = useState(-1);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const defaultFilterQuery = {
    orgName: "",
    partnerDomain: "",
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);
  // const [showDeactivatePopup, setShowDeactivatePopup] = useState(false);
  // const [deactivateRequest, setDeactivateRequest] = useState({});

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewCertificateId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "orgName", headerNameKey: "rootTrustCertificate.organisation" },
    {
      id: "partnerDomain",
      headerNameKey: "rootTrustCertificate.partnerDomain",
    },
    { id: "issuedTo", headerNameKey: "rootTrustCertificate.issuedTo" },
    { id: "issuedBy", headerNameKey: "rootTrustCertificate.issuedBy" },
    { id: "validFrom", headerNameKey: "rootTrustCertificate.validFrom" },
    { id: "validTill", headerNameKey: "rootTrustCertificate.validTill" },
    { id: "timeOfUpload", headerNameKey: "rootTrustCertificate.timeOfUpload" },
    { id: "status", headerNameKey: "rootTrustCertificate.status" },
    { id: "action", headerNameKey: "rootTrustCertificate.action" },
  ];

  const trustCertDummyData = [
    {
      orgName: "P23423049",
      partnerDomain: "DEVICE_V1",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423045",
      partnerDomain: "DEVICE_V2",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "rejected",
    },
    {
      orgName: "P23423045",
      partnerDomain: "DEVICE_V3",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P234230456",
      partnerDomain: "DEVICE_V4",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "InProgress",
    },
    {
      orgName: "P23423034",
      partnerDomain: "DEVICE_V5",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423064",
      partnerDomain: "DEVICE_V6",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "rejected",
    },
    {
      orgName: "P23423064",
      partnerDomain: "DEVICE_V7",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423045",
      partnerDomain: "DEVICE_V8",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423065",
      partnerDomain: "DEVICE_V9",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "InProgress",
    },
    {
      orgName: "P23423076",
      partnerDomain: "DEVICE_V10",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423026",
      partnerDomain: "DEVICE_V11",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "rejected",
    },
    {
      orgName: "P23423029",
      partnerDomain: "DEVICE_V12",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423029",
      partnerDomain: "DEVICE_V13",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "approved",
    },
    {
      orgName: "P23423029",
      partnerDomain: "DEVICE_V14",
      issuedTo: "MOSIP",
      issuedBy: "Mosip",
      validFrom: "2024-09-13T04:25:39.046+00:00",
      validTill: "2024-09-13T04:25:39.046+00:00",
      timeOfUpload: "2025-09-13T04:25:39.046+00:00",
      status: "InProgress",
    },
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        //   const response = await HttpService.get(getPartnerManagerUrl('/partners/policy-requests', process.env.NODE_ENV));
        //   if (response) {
        //     const responseData = response.data;
        //     if (responseData && responseData.response) {
        //       const resData = responseData.response;
        //       const sortedData = resData.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
        //       setPoliciesList(sortedData);
        //       setFilteredPoliciesList(sortedData);
        //     } else {
        //       handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        //     }
        //   } else {
        //     setErrorMsg(t('policies.errorInPoliciesList'));
        //   }

        const sortedData = trustCertDummyData.sort(
          (a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime)
        );
        setCertificateData(sortedData);
        setFilteredCertificateData(sortedData);
        setInterval(() => {
          setDataLoaded(true);
        }, 1000);
      } catch (err) {
        console.error("Error fetching data:", err);
        setErrorMsg(err);
      }
    };
    fetchData();
  }, []);

  const showUploadCertificate = () => {
    // navigate("/partnermanagement/policies/requestPolicy");
  };

  const showCertificateDetails = (selectedCertificateData) => {
    localStorage.setItem(
      "selectedCertificateData",
      JSON.stringify(selectedCertificateData)
    );
    // navigate("/partnermanagement/policies/viewPolicyDetails");
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    const isDateCol = header === "createdDateTime" ? true : false;
    toggleSortAscOrder(
      header,
      isDateCol,
      filteredCertificateData,
      setFilteredCertificateData,
      order,
      setOrder,
      isDescending,
      setIsDescending,
      activeSortAsc,
      setActiveSortAsc,
      activeSortDesc,
      setActiveSortDesc
    );
  };

  const sortDescOrder = (header) => {
    const isDateCol = header === "createdDateTime" ? true : false;
    toggleSortDescOrder(
      header,
      isDateCol,
      filteredCertificateData,
      setFilteredCertificateData,
      order,
      setOrder,
      isDescending,
      setIsDescending,
      activeSortAsc,
      setActiveSortAsc,
      activeSortDesc,
      setActiveSortDesc
    );
  };

  const style = {
    backArrowIcon: "!mt-[9%]",
  };

  useEffect(() => {
    let filteredRows = certificateData;
    Object.keys(filterQuery).forEach((key) => {
      if (filterQuery[key] !== "") {
        filteredRows = filteredRows.filter(
          (item) => item[key] === filterQuery[key]
        );
      }
    });
    setFilteredCertificateData(filteredRows);
    setFirstIndex(0);
  }, [filterQuery, certificateData]);

  const onResetFilter = () => {
    window.location.reload();
  };

  //This  part related to Pagination logic
  let tableRows = filteredCertificateData.slice(
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

  const showDeactivateCertificate = (selectedClientdata) => {
    if (selectedClientdata.status === "ACTIVE") {
      // const request = createRequest({
      //   logoUri: selectedClientdata.logoUri,
      //   redirectUris: selectedClientdata.redirectUris,
      //   status: "INACTIVE",
      //   grantTypes: selectedClientdata.grantTypes,
      //   clientName: selectedClientdata.clientName,
      //   clientAuthMethods: selectedClientdata.clientAuthMethods,
      //   clientNameLangMap: {
      //     eng: selectedClientdata.clientName,
      //   },
      // });
      // setDeactivateRequest(request);
      // setShowDeactivatePopup(true);
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
                title="rootTrustCertificate.rootTrustCertTitle"
                backLink="/partnermanagement"
                styleSet={style}
              ></Title>

              {certificateData.length > 0 ? (
                <button
                  id="root_certificate_upload_btn"
                  onClick={() => showUploadCertificate()}
                  type="button"
                  className={`h-12 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md`}
                >
                  {t("rootTrustCertificate.UploadCertBtn")}
                </button>
              ) : null}
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {certificateData.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="flex justify-between py-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                    <div className="flex w-full justify-between">
                      <h6 className="ml-5 mr-3">
                        {t("rootTrustCertificate.organisation")}
                      </h6>
                      <h6>{t("rootTrustCertificate.partnerDomain")}</h6>
                      <h6>{t("rootTrustCertificate.issuedTo")}</h6>
                      <h6>{t("rootTrustCertificate.issuedBy")}</h6>
                      <h6>{t("rootTrustCertificate.validFrom")}</h6>
                      <h6>{t("rootTrustCertificate.validTill")}</h6>
                      <h6>{t("rootTrustCertificate.timeOfUpload")}</h6>
                      <h6>{t("rootTrustCertificate.status")}</h6>
                      <h6 className="mx-4">
                        {t("rootTrustCertificate.action")}
                      </h6>
                    </div>
                  </div>

                  <hr className="h-px mx-3 bg-gray-200 border-0" />

                  <div className="flex items-center justify-center p-24">
                    <div className="flex flex-col items-center">
                      <img src={rectangleGrid} alt="" />
                      <button
                        id="root_certificate_upload_btn"
                        onClick={() => showUploadCertificate()}
                        type="button"
                        className="text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm h-11 px-5 py-3"
                      >
                        {t("rootTrustCertificate.UploadCertBtn")}
                      </button>
                    </div>
                  </div>
                </div>
              ) : (
                <>
                  <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3">
                    <FilterButtons
                      listTitle="rootTrustCertificate.listOfCertificates"
                      dataList={filteredCertificateData}
                      filter={filter}
                      onResetFilter={onResetFilter}
                      setFilter={setFilter}
                    ></FilterButtons>
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {filter && (
                      <RootTrustFilter
                        filteredCertificateData={filteredCertificateData}
                        onFilterChange={onFilterChange}
                      ></RootTrustFilter>
                    )}

                    <div className="mx-[2%] overflow-x-scroll">
                      <table className="table-fixed">
                        <thead>
                          <tr>
                            {tableHeaders.map((header, index) => {
                              return (
                                <th
                                  key={index}
                                  className="py-4 text-sm font-semibold text-[#6F6E6E] w-[14%]"
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
                          {tableRows.map((certificate, index) => {
                            return (
                              <tr
                                id={"root_certificate_list_item" + (index + 1)}
                                key={index}
                                className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${
                                  certificate.status.toLowerCase() ===
                                  "deactivated"
                                    ? "text-[#969696]"
                                    : "text-[#191919]"
                                }`}
                              >
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {certificate.orgName}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {certificate.partnerDomain}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {certificate.issuedTo}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {certificate.issuedBy}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {formatDate(
                                    certificate.validFrom,
                                    "date",
                                    false
                                  )}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {formatDate(
                                    certificate.validTill,
                                    "date",
                                    false
                                  )}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className="px-2"
                                >
                                  {formatDate(
                                    certificate.timeOfUpload,
                                    "date",
                                    false
                                  )}
                                </td>
                                <td
                                  onClick={() =>
                                    showCertificateDetails(certificate)
                                  }
                                  className=""
                                >
                                  <div
                                    className={`${bgOfStatus(
                                      certificate.status
                                    )} flex w-fit py-1.5 px-2 m-3 text-xs font-semibold rounded-md`}
                                  >
                                    {getStatusCode(certificate.status, t)}
                                  </div>
                                </td>
                                <td className="text-center">
                                  <div
                                    ref={(el) =>
                                      (submenuRef.current[index] = el)
                                    }
                                  >
                                    <p
                                      id={
                                        "root_certificate_list_view" +
                                        (index + 1)
                                      }
                                      onClick={() =>
                                        setViewCertificateId(
                                          index === viewCertificateId
                                            ? null
                                            : index
                                        )
                                      }
                                      className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                      tabIndex="0"
                                      onKeyPress={(e) =>
                                        onPressEnterKey(e, () =>
                                          setViewCertificateId(
                                            index === viewCertificateId
                                              ? null
                                              : index
                                          )
                                        )
                                      }
                                    >
                                      ...
                                    </p>
                                    {viewCertificateId === index && (
                                      <div
                                        className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${
                                          isLoginLanguageRTL
                                            ? "left-9 text-right"
                                            : "right-9 text-left"
                                        }`}
                                      >
                                        <p
                                          id="oidc_details_view_btn"
                                          onClick={() =>
                                            showCertificateDetails(certificate)
                                          }
                                          className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${
                                            isLoginLanguageRTL
                                              ? "pl-10"
                                              : "pr-10"
                                          }`}
                                          tabIndex="0"
                                          onKeyPress={(e) =>
                                            onPressEnterKey(e, () =>
                                              showCertificateDetails(
                                                certificate
                                              )
                                            )
                                          }
                                        >
                                          {t("rootTrustCertificate.view")}
                                        </p>
                                        <hr className="h-px bg-gray-100 border-0 mx-1" />
                                        <p
                                          id="oidc_deactive_btn"
                                          onClick={() =>
                                            showDeactivateCertificate(certificate)
                                          }
                                          className={`py-1.5 px-4 ${
                                            isLoginLanguageRTL
                                              ? "pl-10"
                                              : "pr-10"
                                          } ${
                                            certificate.status === "approved"
                                              ? "text-crimson-red cursor-pointer"
                                              : "text-[#A5A5A5] cursor-auto"
                                          } hover:bg-gray-100`}
                                          tabIndex="0"
                                          onKeyPress={(e) =>
                                            onPressEnterKey(e, () =>
                                              showDeactivateCertificate(
                                                certificate
                                              )
                                            )
                                          }
                                        >
                                          {t("rootTrustCertificate.deActivate")}
                                        </p>
                                        {/* {showDeactivatePopup && (
                                          <DeactivatePopup
                                            closePopUp={closeDeactivatePopup}
                                            popupData={client}
                                            request={deactivateRequest}
                                            headerMsg="deactivateOidcClient.oidcClientName"
                                            descriptionMsg="deactivateOidcClient.description"
                                            headerKeyName={
                                              client.oidcClientName
                                            }
                                          ></DeactivatePopup>
                                        )} */}
                                      </div>
                                      // <div
                                      //   id="root_certificate_list_view_card"
                                      //   onClick={() =>
                                      //     showCertificateDetails(certificate)
                                      //   }
                                      //   tabIndex="0"
                                      //   onKeyPress={(e) =>
                                      //     onPressEnterKey(e, () =>
                                      //       showCertificateDetails(certificate)
                                      //     )
                                      //   }
                                      //   className={`absolute border bg-white text-xs font-semibold rounded-md shadow-md w-fit p-2 z-20 items-center ${
                                      //     isLoginLanguageRTL
                                      //       ? "mr-16 left-[5.5rem] max-[800px]:left-20 max-[400px]:left-8 text-right"
                                      //       : "text-left"
                                      //   }`}
                                      // >
                                      //   <p className="cursor-pointer">
                                      //     {t("policies.view")}
                                      //   </p>
                                      // </div>
                                    )}
                                  </div>
                                </td>
                              </tr>
                            );
                          })}
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <Pagination
                    dataList={filteredCertificateData}
                    selectedRecordsPerPage={selectedRecordsPerPage}
                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                    setFirstIndex={setFirstIndex}
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

export default RootTrustCertificateList;
