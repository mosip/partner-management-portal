import { useState, useEffect, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import {
  formatDate, isLangRTL, onClickApplyFilter, onPressEnterKey,
  handleMouseClickForDropdown, setPageNumberAndPageSize,
  onResetFilter,
  resetPageNumber,
  getPartnerManagerUrl,
  handleServiceErrors
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import FilterButtons from "../../common/FilterButtons";
import CertificatesFilter from "./CertificatesFilter";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import CertificateTab from "./CertificateTab";
import EmptyList from "../../common/EmptyList";
import { HttpService } from "../../../services/HttpService";
import viewIcon from "../../../svg/view_icon.svg";
import downloadIcon from "../../../svg/download.svg";

function CertificatesList({ certificateType, uploadCertificateBtnName, subTitle, downloadBtnName}) {

  const { t } = useTranslation();
  const navigate = useNavigate();
  const [expandFilter, setExpandFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [certificatesList, setCertificatesList] = useState([]);
  const [order, setOrder] = useState("DESC");
  const [activeAscIcon, setActiveAscIcon] = useState("");
  const [activeDescIcon, setActiveDescIcon] = useState("uploadedDateTime");
  const [actionId, setActionId] = useState(-1);
  const [sortFieldName, setSortFieldName] = useState("uploadedDateTime");
  const [sortType, setSortType] = useState("desc");

  const [firstIndex, setFirstIndex] = useState(0);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
  const [fetchData, setFetchData] = useState(false);
  const [resetPageNo, setResetPageNo] = useState(false);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(localStorage.getItem('itemsPerPage') ? Number(localStorage.getItem('itemsPerPage')) : 8);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const [totalRecords, setTotalRecords] = useState(0);
  const [applyFilter, setApplyFilter] = useState(false);
  const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);

  const [filterAttributes, setFilterAttributes] = useState({
    certificateId: null,
    partnerDomain: null,
    issuedTo: null,
    issuedBy: null,
    validityStatus: null
  });

  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "certificateId", headerNameKey: "certificatesList.certificateId" },
    { id: "partnerDomain", headerNameKey: "certificatesList.partnerDomain", },
    { id: "issuedTo", headerNameKey: "certificatesList.issuedTo" },
    { id: "issuedBy", headerNameKey: "certificatesList.issuedBy" },
    { id: "validFrom", headerNameKey: "certificatesList.validFrom" },
    { id: "validTill", headerNameKey: "certificatesList.validTill" },
    { id: "uploadedDateTime", headerNameKey: "certificatesList.timeOfUpload" },
    { id: "validityStatus", headerNameKey: "certificatesList.validityStatus" },
    { id: "action", headerNameKey: "certificatesList.action" },
  ];

  const fetchCertificatesList = async () => {
    const queryParams = new URLSearchParams();
    queryParams.append('caCertificateType', certificateType)
    queryParams.append('sortFieldName', sortFieldName);
    queryParams.append('sortType', sortType);
    queryParams.append('pageSize', pageSize);

    //reset page number to 0 if filter applied or page number is out of bounds
    const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
    queryParams.append('pageNo', effectivePageNo);
    setResetPageNo(false);

    if (filterAttributes.certificateId) queryParams.append('certificateId', filterAttributes.certificateId);
    if (filterAttributes.partnerDomain) queryParams.append('partnerDomain', filterAttributes.partnerDomain);
    if (filterAttributes.issuedTo) queryParams.append('issuedTo', filterAttributes.issuedTo);
    if (filterAttributes.issuedBy) queryParams.append('issuedBy', filterAttributes.issuedBy);
    // Check filters.status
    if (filterAttributes.validityStatus !== null) {
      if (filterAttributes.validityStatus === 'valid') queryParams.append('validityStatus', true);
      else if (filterAttributes.validityStatus === 'expired') queryParams.append('validityStatus', false);
    }

    const url = `${getPartnerManagerUrl('/partners/root-certificates', process.env.NODE_ENV)}?${queryParams.toString()}`;
    try {
        fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
        const response = await HttpService.get(url);
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response.data;
                setTotalRecords(responseData.response.totalResults);
                setCertificatesList(resData);
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } else {
            setErrorMsg(t('certificatesList.errorInCertificateList'));
        }
        fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
        setFetchData(false);
    } catch (err) {
        setFetchData(false);
        fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
        console.error('Error fetching data:', err);
        setErrorMsg(err);
    }
  }

  useEffect(() => {
    fetchCertificatesList();
  }, [sortFieldName, sortType, pageNo, pageSize]);

  useEffect(() => {
    if (isApplyFilterClicked) {
      fetchCertificatesList();
      setIsApplyFilterClicked(false);
    }
  }, [isApplyFilterClicked]);

  const onApplyFilter = (updatedfilters) => {
    onClickApplyFilter(updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
  };

  const getPaginationValues = (recordsPerPage, pageIndex) => {
    setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
  };

  const sortAscOrder = (header) => {
    if (order !== 'ASC' || activeAscIcon !== header) {
      setFetchData(true);
      setSortFieldName(header);
      setSortType('ASC');
      setOrder("ASC");
      setActiveDescIcon("");
      setActiveAscIcon(header);
    }
  };

  const sortDescOrder = (header) => {
    if (order !== 'DESC' || activeDescIcon !== header) {
      setFetchData(true);
      setSortFieldName(header);
      setSortType('DESC');
      setOrder("DESC");
      setActiveDescIcon(header);
      setActiveAscIcon("");
    }
  };

  const showUploadCertificate = () => {
    navigate('/partnermanagement/admin/certificates/upload-root-trust-certificate')
  };

  const showCertificateDetails = (selectedCertificateData) => {

  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const styles = {
    loadingDiv: "!py-[20%]",
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
                <Title title="certificatesList.certificateTrustStore" backLink="/partnermanagement" />
                {certificatesList.length !== 0 ?
                  <button onClick={showUploadCertificate} id='upload_certificate_btn' type="button" className="h-auto text-sm px-3 font-semibold text-white bg-tory-blue rounded-md">
                    {t(uploadCertificateBtnName)}
                  </button>
                  : null
                }
              </div>

              <CertificateTab
                activeRootCA={certificateType === 'root' ? true : false}
                rootCertificatesPath={'/partnermanagement/admin/certificates/root-ca-certificate-list'}
                activeIntermediateCA={certificateType === 'intermediate' ? true : false}
                intermediateCertificatesPath={'/partnermanagement/admin/certificates/intermediate-ca-certificate-list'}
              />
              {!applyFilter && certificatesList.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <EmptyList
                    tableHeaders={tableHeaders}
                    showCustomButton={!applyFilter}
                    customButtonName={uploadCertificateBtnName}
                    buttonId='upload_certificate_btn'
                    onClickButton={showUploadCertificate}
                  />
                </div>
              ) : (
                <>
                  <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                    <FilterButtons
                      listTitle={subTitle}
                      dataListLength={totalRecords}
                      filter={expandFilter}
                      onResetFilter={onResetFilter}
                      setFilter={setExpandFilter}
                    />
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {expandFilter && (
                      <CertificatesFilter onApplyFilter={onApplyFilter} />
                    )}
                    {!tableDataLoaded && <LoadingIcon styleSet={styles} />}
                    {tableDataLoaded && applyFilter && certificatesList.length === 0 ?
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
                                        <div className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                          {t(header.headerNameKey)}
                                          {header.id !== "action" && (
                                            <SortingIcon
                                              headerId={header.id}
                                              sortDescOrder={sortDescOrder}
                                              sortAscOrder={sortAscOrder}
                                              order={order}
                                              activeSortDesc={activeDescIcon}
                                              activeSortAsc={activeAscIcon}
                                            />
                                          )}
                                        </div>
                                      </th>
                                    );
                                  })}
                                </tr>
                              </thead>
                              <tbody>
                                {certificatesList.map((certificate, index) => {
                                  return (
                                    <tr id={"certificate_list_item" + (index + 1)} key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words`}>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2`}>{certificate.certId}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2`}>{certificate.partnerDomain}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2 break-all`}>{certificate.issuedTo}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2 break-all`}>{certificate.issuedBy}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2`}>{formatDate(certificate.validFromDate, "dateTime", true)}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2`}>{formatDate(certificate.validTillDate, "dateTime", true)}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2`}>{formatDate(certificate.uploadTime, "dateTime", true)}</td>
                                      <td onClick={() => showCertificateDetails(certificate)} className={`px-2 ${certificate.status === false && 'text-crimson-red'}`}>{certificate.status === true ? t('statusCodes.valid') : t('statusCodes.expired')}</td>
                                      <td className="text-center">
                                        <div ref={(el) => (submenuRef.current[index] = el)}>
                                          <p id={"certificate_list_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}
                                            tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => setActionId(index === actionId ? null : index))}
                                          >
                                            ...
                                          </p>
                                          {actionId === index && (
                                            <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                              <div className="flex justify-between hover:bg-gray-100 px-2 py-2" onClick={() => showCertificateDetails(certificate)} tabIndex="0" onKeyPress={(e) => onPressEnterKey(e, () => showCertificateDetails(certificate))}>
                                                  <p id="certificate_list_view_btn" className={`cursor-pointer text-[#3E3E3E]`}>{t("partnerList.view")}</p>
                                                  <img src={viewIcon} alt="" className={``}></img>
                                              </div>
                                              <hr className="h-px bg-gray-100 border-0 mx-1" />
                                              <div className="flex justify-between hover:bg-gray-100 px-2 py-2" tabIndex="0">
                                                  <p id="certificate_list_view_btn" className={`max-w-28 cursor-pointer text-[#3E3E3E]`}>{t(downloadBtnName)}</p>
                                                  <img src={downloadIcon} alt="" className={``}></img>
                                              </div>
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
                            dataListLength={totalRecords}
                            selectedRecordsPerPage={selectedRecordsPerPage}
                            setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                            setFirstIndex={setFirstIndex}
                            isServerSideFilter={true}
                            getPaginationValues={getPaginationValues}
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

export default CertificatesList;
