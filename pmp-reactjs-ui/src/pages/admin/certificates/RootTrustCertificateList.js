import { useState, useEffect, useRef } from "react";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { createDropdownData, isLangRTL, onPressEnterKey } from "../../../utils/AppUtils";
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
import file from '../../../svg/file_icon.svg';
import RootTrustCertificatesFilter from "./RootTrustCertificatesFilter";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import DropdownComponent from "../../common/fields/DropdownComponent";
import fileUploadImg from '../../../svg/file_upload_certificate.svg';
import fileDescription from '../../../svg/file_description.svg';
import UploadCertificate from "../../partner/certificates/UploadCertificate";
import SuccessMessage from "../../common/SuccessMessage";

function RootTrustCertificateList() {
  const { t } = useTranslation();
  const [filter, setFilter] = useState(false);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [successMsg, setSuccessMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [showUploadCertificatePortal, setShowUploadCertificatePortal] = useState(false);
  const [certificateData, setCertificateData] = useState([]);
  const [uploading, setUploading] = useState(false);
  const [removeLastUploadData, setRemoveLastUploadData] = useState(false);
  const [fileName, setFileName] = useState('');
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const [uploadFailure, setUploadFailure] = useState(false);
  const [filteredCertificateData, setFilteredCertificateData] = useState([]);
  const [order, setOrder] = useState("ASC");
  const [activeSortAsc, setActiveSortAsc] = useState("timeOfUpload");
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

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);

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

        const sortedData = trustCertDummyData.sort(
          (a, b) => new Date(b.timeOfUpload) - new Date(a.timeOfUpload)
        );
        setCertificateData(sortedData);
        setFilteredCertificateData(sortedData);
        setDataLoaded(true);
      } catch (err) {
        console.error("Error fetching data:", err);
        setErrorMsg(err);
      }
    };
    fetchData();
  }, []);

  const showUploadCertificate = () => {
  };

  const uploadRootTrustCertificate = () => {
    setShowUploadCertificatePortal(true);
  }

  const showCertificateDetails = (selectedCertificateData) => {
    localStorage.setItem(
      "selectedCertificateData",
      JSON.stringify(selectedCertificateData)
    );
  };

  const cancelUpload = () => {
    setFileName("");
    setUploading(false);
  };
  const removeUpload = () => {
    setFileName("");
    setUploadSuccess(false);
    setUploading(false);
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
    setErrorCode("");
  };
  const cancelSuccessMsg = () => {
    setSuccessMsg("");
  };

  const handleFileChange = (event) => {
    setErrorMsg("");
    setErrorCode("");
    const file = event.target.files[0];
    if (file) {
      const fileName = file.name;
      const fileExtension = fileName.split('.').pop().toLowerCase();
      if (fileExtension === 'cer' || fileExtension === 'pem') {
        const reader = new FileReader();
        reader.onload = (e) => {
          const fileData = e.target.result;
          setUploading(true);
          setRemoveLastUploadData(true);
          setFileName(fileName);
          setCertificateData(fileData);
          setTimeout(() => {
            setUploading(false);
          }, 2000);
        }
        reader.readAsText(file);
      } else {
        setUploadFailure(true);
        setErrorMsg(t('uploadCertificate.fileUploadError'));
      }
    }
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    const isDateCol = header === "timeOfUpload" ? true : false;
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
    const isDateCol = header === "timeOfUpload" ? true : false;
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

  const onDomainChangeEvent = () => {

  };

  const style = {
    backArrowIcon: "!mt-[9%]",
  };

  const uploadCertificateDropdownStyle = {
    dropdownButton: "!text-[#343434] !w-[23rem]",
    dropdownLabel: "!mb-1 !mt-5"
  }

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
      document.body.style.overflow = "hidden";
    }
  };

  const errorcustomStyle = {
    outerDiv: "!flex !justify-center !inset-0",
    innerDiv: "!flex !justify-between !items-center !rounded-none !bg-moderate-red !w-full !min-h-[3rem] !h-fit !px-4 !py-[10px]"
  }

  const successcustomStyle = {
    outerDiv: "!flex !justify-center !inset-0",
    innerDiv: "!flex !justify-between !items-center !rounded-none !w-full !min-h-[3rem] !h-fit !px-4 !py-[10px]"
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

              {certificateData.length > 0 && (
                <button
                  id="root_certificate_upload_btn"
                  onClick={() => showUploadCertificate()}
                  type="button"
                  className={`h-12 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md`}
                >
                  {t("rootTrustCertificate.UploadCertBtn")}
                </button>
              )}
            </div>
            <div className="flex-col justify-center ml-3 h-full">
              {certificateData.length === 0 ? (
                <div className={`bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center`}>
                  {!showUploadCertificatePortal ?
                    <>
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
                            onClick={() => uploadRootTrustCertificate()}
                            type="button"
                            className="text-white font-semibold mt-8 bg-tory-blue rounded-md text-sm h-11 px-5 py-3">
                            {t("rootTrustCertificate.UploadCertBtn")}
                          </button>
                        </div>
                      </div>
                    </> :
                    <>
                      <div className="w-[100%] bg-snow-white rounded-lg shadow-md p-[1%]">
                        <div className="m-[1%] shadow-md pb-[1%] rounded-lg">
                          <div className={`flex items-center shadow-lg rounded-lg justify-between`}>
                            <div className="flex-col items-center w-full">
                              <div className="flex items-center bg-[#edf2fc] p-[0.5rem]">
                                <img src={file} className="h-8" alt="" />
                                <div className="flex-col p-3 items-center">
                                  <h6 className={`text-sm font-semibold text-charcoal-gray`}>
                                    {t('rootTrustCertificate.rootTrustCertTitle')}
                                  </h6>
                                  <p className="text-xs text-light-gray">{t('partnerCertificatesList.certificateFormatMsg')}</p>
                                </div>
                              </div>
                              <hr className="border bg-medium-gray " />
                              <div className="flex items-center p-3 bg-white rounded-lg gap-x-10">
                                <div className="flex-col">
                                  <p className="font-semibold text-xs text-dim-gray">{t('uploadRootofTrustCertificate.partnerDomain')}</p>
                                  <p className="font-bold text-sm text-charcoal-gray">-</p>
                                </div>
                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[5%]" : "ml-[5%]"}`}>
                                  <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.expiryDate')}</p>
                                  <p className="font-semibold text-sm text-charcoal-gray">-</p>
                                </div>
                                <div className={`flex-col ${isLoginLanguageRTL ? "mr-[10%]" : "ml-[10%]"}`}>
                                  <p className="font-semibold text-xs text-dim-gray">{t('partnerCertificatesList.timeOfUpload')}</p>
                                  <p className="font-semibold text-sm text-charcoal-gray">-</p>
                                </div>
                              </div>
                              <div className="relative">
                                {uploadFailure && errorMsg && (
                                  <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={errorcustomStyle} />
                                )}
                                {uploadSuccess && successMsg && (
                                  <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={successcustomStyle} />
                                )}
                              </div>
                            </div>
                          </div>
                          <div className={`flex-col pt-[0.5rem] justify-center w-[47rem] ${isLoginLanguageRTL ? 'pr-[23rem]' : 'pl-[23rem]'}`}>
                            <DropdownComponent
                              fieldNameKey='uploadRootofTrustCertificate.partnerDomain'
                              onDropDownChangeEvent={onDomainChangeEvent}
                              dropdownDataList={[
                                { fieldValue: '1', fieldCode: 'FTM' },
                                { fieldValue: '2', fieldCode: 'DEVICE' },
                                { fieldValue: '3', fieldCode: 'AUTH' }
                              ]}
                              placeHolderKey='uploadRootofTrustCertificate.dropdownPlaceholder'
                              isPlaceHolderPresent={true}
                              styleSet={uploadCertificateDropdownStyle}
                              id='partnerDomain_selector_dropdown'
                            />
                            <div className={`flex items-center justify-center w-[23rem] mt-[1.5rem] min-h-40 h-fit border-2 border-[#9CB2E0] rounded-xl bg-[#F8FBFF] bg-opacity-100 text-center cursor-pointer ${isLoginLanguageRTL ? 'mr-[0.2rem]' : 'ml-[1rem]'}`}>
                              {uploading && (
                                <div className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                  <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-800" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                  </svg>
                                  <h5 className="text-charcoal-gray text-sm font-semibold">
                                    {t('uploadCertificate.selectingFile')}
                                  </h5>
                                  <p className="text-sm font-semibold text-tory-blue">
                                    {t('uploadCertificate.cancel')}
                                  </p>
                                </div>
                              )}
                              {!uploading && fileName === '' && (
                                <div id='upload_certificate_card' className={`flex flex-col items-center justify-center w-full min-h-36 cursor-pointer`}>
                                  <label htmlFor="fileInput" tabIndex={0} onKeyPress={(e) => (e.key === 'Enter' || e.key === ' ') && document.getElementById('fileInput').click()} className="flex flex-col items-center w-full min-h-36 justify-center cursor-pointer">
                                    <img src={fileUploadImg} alt="" className="mb-2 w-10 h-10" />
                                    <h5 className="text-charcoal-gray text-base font-normal">
                                      {t('uploadCertificate.selectCertificate')}
                                    </h5>
                                    <p className="text-xs text-light-gray">
                                      {t('uploadCertificate.certificateFormat')}
                                    </p>
                                  </label>
                                  <input id="fileInput" type="file" className="hidden" accept=".cer,.pem" onChange={handleFileChange} />
                                </div>
                              )}
                              {!uploading && fileName && (
                                <div id='remove_certificate_card' className={`flex flex-col items-center justify-center mb-1 cursor-pointer`}>
                                  <label htmlFor="fileInput" className="flex flex-col items-center justify-center cursor-pointer">
                                    <img src={fileDescription} alt="" className="w-10 h-10 mb-1" />
                                  </label>
                                  <h5 className="text-charcoal-gray text-sm font-semibold">
                                    {fileName}
                                  </h5>
                                  <p id='remove_certificate_btn' className="text-sm font-semibold text-tory-blue" onClick={removeUpload}>
                                    {t('uploadCertificate.remove')}
                                  </p>
                                </div>
                              )}
                            </div>
                          </div>
                          <hr className="border bg-medium-gray mt-[2rem]" />
                          <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end m-4`}>
                            <button id="upload_admine_certificate_cancel_btn" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-/12 md:w-24 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('commons.cancel')}</button>
                            <button id="upload_admine_certificate_btn" className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-8/12 md:w-24 h-10 border-[#1447B2] border rounded-md text-sm bg-tory-blue text-white font-semibold`}>{t('commons.goBack')}</button>
                          </div>
                        </div>
                      </div>
                    </>
                  }
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
                      <RootTrustCertificatesFilter
                        filteredCertificateData={filteredCertificateData}
                        onFilterChange={onFilterChange}
                      ></RootTrustCertificatesFilter>
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
                                className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${certificate.status.toLowerCase() ===
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
                                        className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL
                                          ? "left-9 text-right"
                                          : "right-9 text-left"
                                          }`}
                                      >
                                        <p
                                          id="root_certificate_details_view_btn"
                                          onClick={() =>
                                            showCertificateDetails(certificate)
                                          }
                                          className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] hover:bg-gray-100 ${isLoginLanguageRTL
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
                                          id="root_certificate_deactive_btn"
                                          onClick={() =>
                                            showDeactivateCertificate(certificate)
                                          }
                                          className={`py-1.5 px-4 ${isLoginLanguageRTL
                                            ? "pl-10"
                                            : "pr-10"
                                            } ${certificate.status === "approved"
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
