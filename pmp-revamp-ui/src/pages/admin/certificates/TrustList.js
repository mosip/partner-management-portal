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
  downloadCaTrust,
  setSubmenuRef,
  handleServiceErrors
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import Title from "../../common/Title";
import FilterButtons from "../../common/FilterButtons";
import TrustFilter from "./TrustFilter";
import SortingIcon from "../../common/SortingIcon";
import viewIcon from "../../../svg/view_icon.svg";
import Pagination from "../../common/Pagination";
import TrustTab from "./TrustTab";
import EmptyList from "../../common/EmptyList";
import { HttpService } from "../../../services/HttpService";
import downloadIcon from "../../../svg/download.svg";
import disableDownloadIcon from "../../../svg/disable_download.svg";
import SuccessMessage from "../../common/SuccessMessage";

function TrustList({ trustType, uploadTrustBtnName, subTitle, downloadBtnName }) {

  const { t } = useTranslation();
  const navigate = useNavigate();
  const [expandFilter, setExpandFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [trustDataList, setTrustDataList] = useState([]);
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
    issuedBy: null
  });

  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
  }, [submenuRef]);

  const tableHeaders = [
    { id: "certificateId", headerNameKey: "trustList.certificateId" },
    { id: "partnerDomain", headerNameKey: "trustList.partnerDomain", },
    { id: "issuedTo", headerNameKey: "trustList.issuedTo" },
    { id: "issuedBy", headerNameKey: "trustList.issuedBy" },
    { id: "validFrom", headerNameKey: "trustList.validFrom" },
    { id: "validTill", headerNameKey: "trustList.validTo" },
    { id: "uploadedDateTime", headerNameKey: "trustList.timeOfUpload" },
    { id: "validityStatus", headerNameKey: "trustList.validityStatus" },
    { id: "action", headerNameKey: "trustList.action" },
  ];

  const fetchTrustList = async () => {
    const queryParams = new URLSearchParams();
    queryParams.append('caCertificateType', trustType)
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

    const url = `${getPartnerManagerUrl('/trust-chain-certificates', process.env.NODE_ENV)}?${queryParams.toString()}`;
    try {
      fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
      const response = await HttpService.get(url);
      if (response) {
        const responseData = response.data;
        if (responseData && responseData.response) {
          const resData = responseData.response.data;
          setTotalRecords(responseData.response.totalResults);
          setTrustDataList(resData);
        } else {
          if (responseData.errors && responseData.errors.length > 0) {
            const errorCode = response.data.errors[0].errorCode;
            if (errorCode === 'PMS_KKS_001') {
                setErrorMsg(t('partnerCertificatesList.errorWhileFetchingCertificateList'));
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
          }
        }
      } else {
        setErrorMsg(t('trustList.errorInCertificateList'));
      }
      fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
      setFetchData(false);
    } catch (err) {
      console.error('Error fetching data:', err);
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      }
      setFetchData(false);
      fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
    }
  }

  useEffect(() => {
    fetchTrustList();
  }, [sortFieldName, sortType, pageNo, pageSize]);

  useEffect(() => {
    if (isApplyFilterClicked) {
      fetchTrustList();
      setIsApplyFilterClicked(false);
    }
  }, [isApplyFilterClicked]);

  const onApplyFilter = (updatedfilters) => {
    onClickApplyFilter(updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
  };

  const getPaginationValues = (recordsPerPage, pageIndex) => {
    setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
  };
  const uploadTrustRequiredData = () => {
    let breadcrumb = '';
    let backLink = '';

    if (trustType === 'root') {
      breadcrumb = 'rootTrustList.subTitle';
      backLink = '/partnermanagement/admin/certificates/root-ca-certificate-list';
    } else if (trustType === 'intermediate') {
      breadcrumb = 'intermediateTrustList.subTitle';
      backLink = '/partnermanagement/admin/certificates/intermediate-ca-certificate-list';
    }

    const requiredData = { breadcrumb, backLink };
    localStorage.setItem('uploadTrustAttributes', JSON.stringify(requiredData));
  };

  const viewTrustDetails = (selectedData) => {
    let trustType = '';
    let header = '';
    let subTitle = '';
    let backLink = '';
    let navigateUrl = '';

    if (trustType === 'root') {
      trustType = 'root';
      header = 'viewCertificateDetails.viewRootCaCertificateDetails';
      subTitle = 'rootTrustList.subTitle';
      backLink = '/partnermanagement/admin/certificates/root-ca-certificate-list';
      navigateUrl = '/partnermanagement/admin/certificates/view-root-ca-certificate-details';
    } else if (trustType === 'intermediate') {
      trustType = 'intermediate';
      header = 'viewCertificateDetails.viewIntermediateCaCertificateDetails';
      subTitle = 'intermediateTrustList.subTitle';
      backLink = '/partnermanagement/admin/certificates/intermediate-ca-certificate-list';
      navigateUrl = '/partnermanagement/admin/certificates/view-intermediate-ca-certificate-details';
    }

    const requiredData = {
      trustData: selectedData,
      trustType,
      header,
      subTitle,
      backLink
    };

    localStorage.setItem('selectedTrustAttributes', JSON.stringify(requiredData));
    navigate(navigateUrl);
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

  const showUploadTrust = () => {
    uploadTrustRequiredData();
    navigate('/partnermanagement/admin/certificates/upload-trust-certificate')
  };

  const onClickDownload = (trustData) => {
    if (trustData.status === true) {
      downloadCaTrust(HttpService, trustData.certId, trustType, setErrorCode, setErrorMsg, errorMsg, setSuccessMsg, t);
    }
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const cancelSuccessMsg = () => {
    setSuccessMsg("");
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
          {successMsg && (
            <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="justify-between mb-5 flex-col">
              <div className="flex justify-between">
                <Title title="trustList.certificateTrustStore" backLink="/partnermanagement" />
                {trustDataList.length !== 0 ?
                  <button onClick={showUploadTrust} id={uploadTrustBtnName} type="button" className="h-auto text-sm px-3 font-semibold text-white bg-tory-blue rounded-md">
                    {t('uploadTrustCertificate.uploadTrustCertificate')}
                  </button>
                  : null
                }
              </div>

              <TrustTab
                activeRootCA={trustType === 'root' ? true : false}
                rootCertificatesPath={'/partnermanagement/admin/certificates/root-ca-certificate-list'}
                activeIntermediateCA={trustType === 'intermediate' ? true : false}
                intermediateCertificatesPath={'/partnermanagement/admin/certificates/intermediate-ca-certificate-list'}
              />
              {!applyFilter && trustDataList.length === 0 ? (
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <EmptyList
                    tableHeaders={tableHeaders}
                    showCustomButton={!applyFilter}
                    customButtonName='uploadTrustCertificate.uploadTrustCertificate'
                    buttonId='upload_certificate_btn'
                    onClickButton={showUploadTrust}
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
                      <TrustFilter onApplyFilter={onApplyFilter} />
                    )}
                    {!tableDataLoaded ? (
                      <LoadingIcon styleSet={styles} />
                    ) : (
                      <>
                        {applyFilter && trustDataList.length === 0 ?
                          <EmptyList tableHeaders={tableHeaders}/>
                          : (
                            <>
                              <div className="mx-[1.4rem] overflow-x-scroll">
                                <table className="table-fixed">
                                  <thead>
                                    <tr>
                                      {tableHeaders.map((header, index) => {
                                        return (
                                          <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[14%]">
                                            <div className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                              {t(header.headerNameKey)}
                                              {header.id !== "action" && header.id !== "validityStatus" && (
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
                                    {trustDataList.map((trustData, index) => {
                                      return (
                                        <tr id={"certificate_list_item" + (index + 1)} key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words`}>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2`}>{trustData.certId}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2`}>{trustData.partnerDomain}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2 break-all`}>{trustData.issuedTo}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2 break-all`}>{trustData.issuedBy}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2`}>{formatDate(trustData.validFromDate, "dateTime")}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2`}>{formatDate(trustData.validTillDate, "dateTime")}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2`}>{formatDate(trustData.uploadTime, "dateTime")}</td>
                                          <td onClick={() => viewTrustDetails(trustData)} className={`px-2 ${trustData.status === false && 'text-crimson-red'}`}>{trustData.status === true ? t('statusCodes.valid') : t('statusCodes.expired')}</td>
                                          <td className="text-center cursor-default">
                                            <div ref={setSubmenuRef(submenuRef, index)}>
                                              <button id={"certificate_list_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 cursor-pointer text-center`}>
                                                ...
                                              </button>
                                              {actionId === index && (
                                                <div className={`absolute w-auto z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-9 text-right" : "right-9 text-left"}`}>
                                                  <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewTrustDetails(trustData)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewTrustDetails(trustData))}>
                                                    <p id="root_certificate_details_view_btn" className={`py-1.5 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10 pr-2" : "pr-10 pl-2"}`}>{t("trustList.view")}</p>
                                                    <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                  </div>
                                                  <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                  <div role='button' className={`flex justify-between hover:bg-gray-100 px-2 py-2 ${trustData.status === true ? 'cursor-pointer' : 'cursor-default'}`}
                                                    onClick={() => onClickDownload(trustData)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => onClickDownload(trustData))}>
                                                    <p id="certificate_list_view_btn" className={`${trustData.status === true ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t(downloadBtnName)}</p>
                                                    <img src={trustData.status === true ? downloadIcon : disableDownloadIcon} alt="" className={`${isLoginLanguageRTL ? 'pr-[1rem]' : 'pl-[1rem]'}`} />
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
                            </>
                          )
                        }
                      </>
                    )}
                    <Pagination
                      dataListLength={totalRecords}
                      selectedRecordsPerPage={selectedRecordsPerPage}
                      setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                      setFirstIndex={setFirstIndex}
                      isServerSideFilter={true}
                      getPaginationValues={getPaginationValues}
                    />
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

export default TrustList;
