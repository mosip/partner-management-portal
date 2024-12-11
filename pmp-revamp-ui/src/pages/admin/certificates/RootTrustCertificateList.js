import { useState, useEffect, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, onPressEnterKey } from "../../../utils/AppUtils";
import { formatDate, getStatusCode, handleMouseClickForDropdown, toggleSortAscOrder, toggleSortDescOrder, bgOfStatus, } from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import rectangleGrid from "../../../svg/rectangle_grid.svg";
import FilterButtons from "../../common/FilterButtons";
import RootTrustCertificatesFilter from "./RootTrustCertificatesFilter";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import RootTrustCertificateTab from "./RootTrustCertificateTab";
import EmptyList from "../../common/EmptyList";

function RootTrustCertificateList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [filteredCertificateData, setFilteredCertificateData] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("timeOfUpload");
  const [activeSortDesc, setActiveSortDesc] = useState("");
  const [firstIndex, setFirstIndex] = useState(0);
  const [isDescending, setIsDescending] = useState(false);
  const [viewCertificateId, setViewCertificateId] = useState(-1);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(8);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const defaultFilterQuery = {
    orgName: "",
    partnerDomain: "",
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewCertificateId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "certificateId", headerNameKey: "rootTrustCertificate.certificateId" },
    { id: "partnerDomain", headerNameKey: "rootTrustCertificate.partnerDomain", },
    { id: "issuedTo", headerNameKey: "rootTrustCertificate.issuedTo" },
    { id: "issuedBy", headerNameKey: "rootTrustCertificate.issuedBy" },
    { id: "validFrom", headerNameKey: "rootTrustCertificate.validFrom" },
    { id: "validTill", headerNameKey: "rootTrustCertificate.validTill" },
    { id: "timeOfUpload", headerNameKey: "rootTrustCertificate.timeOfUpload" },
    { id: "status", headerNameKey: "rootTrustCertificate.status" },
    { id: "action", headerNameKey: "rootTrustCertificate.action" },
  ];

 const rootTrustCertificatesList = [
  {certId: "10081", partnerDomain: "AUTH", issuedTo: "client_auth1", issuedBy: "mosip_auth1", validFrom: "2024-12-10T19:35:01.085+00:00", validTill: "2025-12-10T19:35:01.085+00:00", timeOfUpload: "2024-12-09T19:35:01.085+00:00", status: "active"}, 
  {certId: "10082", partnerDomain: "AUTH", issuedTo: "client_auth2", issuedBy: "mosip_auth1", validFrom: "2024-11-21T19:35:01.085+00:00", validTill: "2025-12-21T19:35:01.085+00:00", timeOfUpload: "2024-12-20T19:35:01.085+00:00", status: "deactivated"}, 
  {certId: "10083", partnerDomain: "DEVICE", issuedTo: "device_client3", issuedBy: "mosip_device1", validFrom: "2024-09-11T19:35:01.085+00:00", validTill: "2025-12-09T19:35:01.085+00:00", timeOfUpload: "2024-12-11T19:35:01.085+00:00", status: "active"}, 
  {certId: "10084", partnerDomain: "FTM", issuedTo: "client_ftm2", issuedBy: "mosip_auth1", validFrom: "2024-12-10T19:35:31.085+00:00", validTill: "2025-12-10T19:35:01.085+00:00", timeOfUpload: "2024-12-09T19:35:01.085+00:00", status: "deactivated"}, 
  {certId: "10085", partnerDomain: "DEVICE", issuedTo: "device_client1", issuedBy: "mosip_device1", validFrom: "2024-12-08T19:35:01.085+00:00", validTill: "2025-12-08T19:35:01.085+00:00", timeOfUpload: "2024-12-05T19:35:01.085+00:00", status: "deactivated"}, 
  {certId: "10086", partnerDomain: "AUTH", issuedTo: "client_auth3", issuedBy: "mosip_auth1", validFrom: "2024-12-10T19:35:01.085+00:00", validTill: "2025-12-10T19:35:01.085+00:00", timeOfUpload: "2024-12-07T19:35:01.085+00:00", status: "active"}, 
  {certId: "10087", partnerDomain: "DEVICE", issuedTo: "device_client1", issuedBy: "mosip_device1", validFrom: "2024-12-19T19:35:01.085+00:00", validTill: "2025-12-19T19:35:01.085+00:00", timeOfUpload: "2024-12-08T19:35:01.085+00:00", status: "active"}, 
  {certId: "10088", partnerDomain: "FTM", issuedTo: "client_ftm3", issuedBy: "mosip_auth1", validFrom: "2024-12-27T19:35:01.085+00:00", validTill: "2025-12-27T19:35:01.085+00:00", timeOfUpload: "2024-12-24T19:35:01.085+00:00", status: "deactivated"}, 
  {certId: "10089", partnerDomain: "AUTH", issuedTo: "client_auth2", issuedBy: "mosip_auth1", validFrom: "2024-12-14T19:35:01.085+00:00", validTill: "2025-12-14T19:35:01.085+00:00", timeOfUpload: "2024-12-13T19:35:01.085+00:00", status: "active"}, 
  {certId: "100810", partnerDomain: "FTM", issuedTo: "client_ftm2", issuedBy: "mosip_auth1", validFrom: "2024-12-03T19:35:01.085+00:00", validTill: "2025-12-03T19:35:01.085+00:00", timeOfUpload: "2024-12-03T19:35:01.085+00:00", status: "active"}, 
  {certId: "100811", partnerDomain: "DEVICE", issuedTo: "device_client1", issuedBy: "mosip_device1", validFrom: "2024-12-06T19:35:01.085+00:00", validTill: "2025-12-06T19:35:01.085+00:00", timeOfUpload: "2024-12-11T19:35:01.085+00:00", status: "deactivated"}, 
  {certId: "100812", partnerDomain: "DEVICE", issuedTo: "device_client1", issuedBy: "mosip_device1", validFrom: "2024-12-01T19:35:01.085+00:00", validTill: "2025-12-01T19:35:01.085+00:00", timeOfUpload: "2024-12-04T19:35:01.085+00:00", status: "active"}, 
  {certId: "100813", partnerDomain: "AUTH", issuedTo: "client_auth3", issuedBy: "mosip_auth1", validFrom: "2024-12-22T19:35:01.085+00:00", validTill: "2025-12-22T19:35:01.085+00:00", timeOfUpload: "2024-12-20T19:35:01.085+00:00", status: "active"}, 
  {certId: "100814", partnerDomain: "AUTH", issuedTo: "client_auth3", issuedBy: "mosip_auth1", validFrom: "2024-12-05T19:35:01.085+00:00", validTill: "2025-12-15T19:35:01.085+00:00", timeOfUpload: "2024-12-04T19:35:01.085+00:00", status: "active"}, 
  {certId: "100815", partnerDomain: "FTM", issuedTo: "client_ftm1", issuedBy: "mosip_auth1", validFrom: "2024-12-18T19:35:01.085+00:00", validTill: "2025-12-18T19:35:01.085+00:00", timeOfUpload: "2024-12-15T19:35:01.085+00:00", status: "deactivated"}, 
];

  const showUploadCertificate = () => {
    navigate('/partnermanagement/admin/certificates/upload-root-trust-certificate')
  };

  const showCertificateDetails = (selectedCertificateData) => {

  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting

  const style = {
    backArrowIcon: "!mt-[9%]",
  };

  const onResetFilter = () => {
    window.location.reload();
  };

  //This  part related to Pagination logic
  let tableRows = filteredCertificateData.slice(
    firstIndex,
    firstIndex + selectedRecordsPerPage
  );

  const showDeactivateCertificate = (selectedClientdata) => {

  };

  const styles = {
    loadingDiv: "!py-[20%]"
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded &&
        <LoadingIcon />
      }
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-7">
            <div className="justify-between mb-5 flex-col">
              <div className="flex justify-between">
                <Title title="rootTrustCertificate.rootOfTrustCertificates" backLink="/partnermanagement" styleSet={style} />
                {rootTrustCertificatesList.length === 0 ?
                  <button onClick={showUploadCertificate} id='upload_root_trust_certificate_btn' type="button" className="h-10 text-sm px-3 font-semibold text-white bg-tory-blue rounded-md max-330:h-fit">
                    {t('rootTrustCertificate.UploadCertBtn')}
                  </button>
                  : null
                }
              </div>

              <RootTrustCertificateTab
                activeRootTustCertificates={true}
                rootTustCertificatesPath={'/partnermanagement/admin/certificates/root-trust-certificate'}
                activeIntermediateTrustCertificates={false}
                intermediateTrustCertificatesPath={''}
              />
              {rootTrustCertificatesList.length !== 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <EmptyList
                    tableHeaders={tableHeaders}
                    showCustomButton={true}
                    customButtonName='rootTrustCertificate.UploadCertBtn'
                    buttonId='upload_root_trust_certificate_btn'
                    onClickButton={showUploadCertificate}
                  />
                </div>
              ) : (
                <>
                  <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                    <FilterButtons
                      listTitle="rootTrustCertificateList.rootTrustCertificateList"
                      dataListLength={filteredCertificateData.length}
                      filter={filter}
                      onResetFilter={onResetFilter}
                      setFilter={setFilter}
                    />
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {filter && (
                      <RootTrustCertificatesFilter
                      // onFilterChange={onFilterChange}
                      />
                    )}
                    {!tableDataLoaded && <LoadingIcon styleSet={styles} />}
                    {tableDataLoaded && rootTrustCertificatesList.length !== 0 ?
                      <EmptyList
                        tableHeaders={tableHeaders}
                      />
                      : (
                        <>
                          <div className="mx-[2%] overflow-x-scroll">
                            <table className="table-fixed">
                              <thead>
                                <tr>
                                  {tableHeaders.map((header, index) => {
                                    return (
                                      <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[14%]">
                                        <div className="flex gap-x-0 items-center">
                                          {t(header.headerNameKey)}
                                          {header.id !== "action" && (
                                            <SortingIcon
                                              headerId={header.id}
                                              // sortDescOrder={sortDescOrder}
                                              // sortAscOrder={sortAscOrder}
                                              order={order}
                                              activeSortDesc={activeSortDesc}
                                              activeSortAsc={activeSortAsc}
                                            />
                                          )}
                                        </div>
                                      </th>
                                    );
                                  })}
                                </tr>
                              </thead>
                              <tbody>
                                {rootTrustCertificatesList.map((certificate, index) => {
                                  return (
                                    <tr id={"root_certificate_list_item" + (index + 1)} key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${certificate.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`${isLoginLanguageRTL ? 'pr-[0.2rem]' : 'pl-[0.2rem]'}`}>{certificate.certId}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`${isLoginLanguageRTL ? 'pr-[0.2rem]' : 'pl-[0.2rem]'}`}>{certificate.partnerDomain}</td>
                                      <td onClick={() => showCertificateDetails(certificate)}>{certificate.issuedTo}</td>
                                      <td onClick={() => showCertificateDetails(certificate)}>{certificate.issuedBy}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className="px-1">{formatDate(certificate.validFrom, "date", false)}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`${isLoginLanguageRTL ? 'pr-[0.2rem]' : 'pl-[0.2rem]'}`}>{formatDate(certificate.validTill, "date", false)}</td>
                                      <td onClick={() => showCertificateDetails(certificate)}>{formatDate(certificate.timeOfUpload, "dateTime", false)}</td>
                                      <td onClick={() => showCertificateDetails(certificate)}>
                                        <div className={`${bgOfStatus(certificate.status)} flex w-fit py-1.5 px-2 my-2 text-xs font-semibold rounded-md`}>
                                          {getStatusCode(certificate.status, t)}
                                        </div>
                                      </td>
                                      <td className="text-center">
                                        <div ref={(el) => (submenuRef.current[index] = el)}>
                                          <p id={"root_certificate_list_view" + (index + 1)} onClick={() => setViewCertificateId(index === viewCertificateId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setViewCertificateId(index === viewCertificateId ? null : index))}
                                          >
                                            ...
                                          </p>
                                          {viewCertificateId === index && (
                                            <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                              <p id="root_certificate_details_view_btn" onClick={() => showCertificateDetails(certificate)} className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}
                                                tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showCertificateDetails(certificate))}
                                              >
                                                {t("rootTrustCertificate.view")}
                                              </p>
                                              <hr className="h-px bg-gray-100 border-0 mx-1" />
                                              <p id="root_certificate_deactive_btn" onClick={() => showDeactivateCertificate(certificate)}
                                                className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${certificate.status === "active" ? "cursor-pointer" : "text-[#A5A5A5] cursor-auto"} hover:bg-gray-100`}
                                                tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showDeactivateCertificate(certificate))}
                                              >
                                                {t("rootTrustCertificate.deActivate")}
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
                          <Pagination
                            dataListLength={filteredCertificateData.length}
                            selectedRecordsPerPage={selectedRecordsPerPage}
                            setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                            setFirstIndex={setFirstIndex}
                          />
                        </>
                      )
                    }
                  </div>
                </>
              )}
            </div>
          </div>
        </>
      )}
    </div>
  )
}

export default RootTrustCertificateList;
