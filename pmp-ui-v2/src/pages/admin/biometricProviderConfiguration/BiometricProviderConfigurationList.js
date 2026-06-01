import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import {
  formatDate,
  createRequest,
  getPartnerManagerUrl,
  handleMouseClickForDropdown,
  handleServiceErrors,
  isLangRTL,
  onPressEnterKey,
} from "../../../utils/AppUtils";
import { HttpService } from "../../../services/HttpService";
import Title from "../../common/Title";
import ErrorMessage from "../../common/ErrorMessage";
import LoadingIcon from "../../common/LoadingIcon";
import EmptyList from "../../common/EmptyList";
import FilterButtons from "../../common/FilterButtons";
import SortingIcon from "../../common/SortingIcon";
import Pagination from "../../common/Pagination";
import viewIcon from "../../../svg/view_icon.svg";
import deleteIcon from "../../../svg/delete_icon.svg";
import BiometricProviderConfigurationListFilter from "./BiometricProviderConfigurationListFilter";
import DeactivatePopup from "../../common/DeactivatePopup";

function BiometricProviderConfigurationList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
  const submenuRef = useRef([]);

  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const [expandFilter, setExpandFilter] = useState(false);
  const [applyFilter, setApplyFilter] = useState(false);
  const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
  const [actionId, setActionId] = useState(-1);
  const [order, setOrder] = useState("DESC");
  const [activeAscIcon, setActiveAscIcon] = useState("");
  const [activeDescIcon, setActiveDescIcon] = useState("createdDateTime");
  const [sortFieldName, setSortFieldName] = useState("createdDateTime");
  const [sortType, setSortType] = useState("desc");
  const [configurations, setConfigurations] = useState([]);
  const [totalRecords, setTotalRecords] = useState(0);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(
    sessionStorage.getItem("itemsPerPage")
      ? Number(sessionStorage.getItem("itemsPerPage"))
      : 8
  );
  const [filterAttributes, setFilterAttributes] = useState({
    configName: "",
    bioextractorProviderName: "",
    bioextractorProviderVersion: "",
    bioModality: "",
  });
  const [showActiveIndexDeletePopup, setShowActiveIndexDeletePopup] = useState(null);
  const [deleteRequest, setDeleteRequest] = useState({});
  const [selectedConfiguration, setSelectedConfiguration] = useState({});

  useEffect(() => {
    return handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
  }, []);

  const tableHeaders = [
    { id: "configName", headerName: t("bioExtractorConfig.configurationName") },
    { id: "bioextractorProviderName", headerName: t("bioExtractorConfig.providerName") },
    { id: "bioextractorProviderVersion", headerName: t("bioExtractorConfig.providerVersion") },
    { id: "bioModality", headerName: t("bioExtractorConfig.biometricModality") },
    { id: "createdDateTime", headerName: t("oidcClientsList.creationDate") },
    { id: "action", headerName: t("partnerList.action") },
  ];

  const getModalityLabel = (modality) => {
    if (!modality) return "-";
    const upperModality = String(modality).toUpperCase();
    if (upperModality === "FACE") return t("bioExtractorConfig.face");
    if (upperModality === "IRIS") return t("bioExtractorConfig.iris");
    if (upperModality === "FINGER") return t("bioExtractorConfig.finger");
    return upperModality;
  };

  const fetchConfigurations = async () => {
    setErrorCode("");
    setErrorMsg("");
    setActionId(-1);
    if (!dataLoaded) {
      setDataLoaded(false);
    } else {
      setTableDataLoaded(false);
    }
    try {
      const queryParams = new URLSearchParams();
      queryParams.append("sortFieldName", sortFieldName);
      queryParams.append("sortType", sortType);
      queryParams.append("pageSize", pageSize);
      queryParams.append("pageNo", pageNo);

      if (filterAttributes.configName) queryParams.append("configName", filterAttributes.configName);
      if (filterAttributes.bioextractorProviderName) queryParams.append("bioextractorProviderName", filterAttributes.bioextractorProviderName);
      if (filterAttributes.bioextractorProviderVersion) queryParams.append("bioextractorProviderVersion", filterAttributes.bioextractorProviderVersion);
      if (filterAttributes.bioModality) queryParams.append("bioModality", filterAttributes.bioModality);

      const url = `${getPartnerManagerUrl("/bio-extractor-configurations", process.env.NODE_ENV)}?${queryParams.toString()}`;
      const response = await HttpService.get(url);
      if (response?.data?.response) {
        const responsePayload = response.data.response;
        const data = responsePayload.data || responsePayload || [];
        const normalizedData = Array.isArray(data)
          ? data.map((item) => ({
              bioExtractorConfigurationId:
                item.bioExtractorConfigurationId || item.bio_extractor_configuration_id || item.id || null,
              configName: item.configName || "-",
              bioextractorProviderName: item.bioextractorProviderName || "-",
              bioextractorProviderVersion: item.bioextractorProviderVersion || "-",
              bioModality: item.bioModality || "-",
              createdDateTime: item.createdDateTime || item.createdDate || null,
            }))
          : [];
        setConfigurations(normalizedData);
        setTotalRecords(
          typeof responsePayload.totalResults === "number"
            ? responsePayload.totalResults
            : normalizedData.length
        );
      } else {
        handleServiceErrors(response?.data, setErrorCode, setErrorMsg);
      }
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        if (err.response?.data?.errors?.length > 0) {
          handleServiceErrors(err.response.data, setErrorCode, setErrorMsg);
        } else {
          setErrorMsg(err.toString());
        }
      } else if (!err.response) {
        setErrorMsg(t("commons.networkError"));
      }
    }
    if (!dataLoaded) {
      setDataLoaded(true);
    } else {
      setTableDataLoaded(true);
    }
  };

  useEffect(() => {
    fetchConfigurations();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [filterAttributes, pageNo, pageSize, sortFieldName, sortType]);

  const onApplyFilter = (updatedFilters) => {
    setApplyFilter(true);
    setFilterAttributes(updatedFilters);
    setPageNo(0);
    setIsApplyFilterClicked(true);
  };

  const onResetFilters = () => {
    setFilterAttributes({
      configName: "",
      bioextractorProviderName: "",
      bioextractorProviderVersion: "",
      bioModality: "",
    });
    setApplyFilter(false);
    setExpandFilter(false);
    setPageNo(0);
    setIsApplyFilterClicked(true);
  };

  const sortAscOrder = (header) => {
    if (order !== "ASC" || activeAscIcon !== header) {
      setSortFieldName(header);
      setSortType("asc");
      setOrder("ASC");
      setActiveDescIcon("");
      setActiveAscIcon(header);
    }
  };

  const sortDescOrder = (header) => {
    if (order !== "DESC" || activeDescIcon !== header) {
      setSortFieldName(header);
      setSortType("desc");
      setOrder("DESC");
      setActiveDescIcon(header);
      setActiveAscIcon("");
    }
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const gotoCreatePage = () => {
    navigate("/partnermanagement/admin/biometric-provider-configuration/create");
  };

  const onViewConfiguration = (configuration) => {
    sessionStorage.setItem("selectedBioExtractorConfig", JSON.stringify(configuration));
    setActionId(-1);
    navigate("/partnermanagement/admin/biometric-provider-configuration/view");
  };

  const deleteConfiguration = async (configuration, index) => {
    const configurationId = configuration?.bioExtractorConfigurationId;
    if (!configurationId) {
      setErrorMsg(t("commons.unexpectedError"));
      return;
    }

    setSelectedConfiguration(configuration);
    const request = createRequest(
      { status: "DELETED" },
      "mosip.pms.bioextractor.configuration.delete.patch",
      true
    );
    setDeleteRequest(request);
    setActionId(-1);
    setShowActiveIndexDeletePopup(index);
  };

  const closeDeletePopup = () => {
    setSelectedConfiguration({});
    setShowActiveIndexDeletePopup(null);
  };

  const onClickConfirmDelete = async () => {
    closeDeletePopup();
    await fetchConfigurations();
  };

  const setSubmenuRef = (index) => (el) => {
    submenuRef.current[index] = el;
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
      {!dataLoaded && <LoadingIcon />}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage
              id="bio_extractor_config_list_error_msg"
              errorCode={errorCode}
              errorMessage={errorMsg}
              clickOnCancel={cancelErrorMsg}
            />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between mb-5 max-470:flex-col">
              <Title
                title="dashboard.biometricProviderConfiguration"
                backLink="/partnermanagement"
              />
              {configurations.length > 0 && (
                <button
                  id="bio_extractor_config_create_btn_top"
                  type="button"
                  onClick={gotoCreatePage}
                  onKeyDown={(e) => onPressEnterKey(e, gotoCreatePage)}
                  className="min-h-10 h-auto whitespace-normal break-words leading-5 text-sm font-semibold text-white px-5 py-1 rounded-md bg-tory-blue max-850:mt-3 max-850:self-start"
                >
                  {t("bioExtractorConfig.createConfiguration", "Create Configuration")}
                </button>
              )}
            </div>
            {!applyFilter && configurations.length === 0 && tableDataLoaded ? (
              <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                <EmptyList
                  tableHeaders={tableHeaders.map((header) => ({ id: header.id, headerNameKey: header.headerName }))}
                  showCustomButton={true}
                  customButtonName={t(
                    "bioExtractorConfig.createConfiguration",
                    "Create Configuration"
                  )}
                  buttonId="bio_extractor_config_create_btn_center"
                  onClickButton={gotoCreatePage}
                  disableBtn={false}
                />
              </div>
            ) : (
              <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3">
                <FilterButtons
                  listTitle={t(
                    "bioExtractorConfig.bioExtractorConfigList",
                    "Biometric Extractor Provider Configurations"
                  )}
                  dataListLength={totalRecords}
                  filter={expandFilter}
                  onResetFilter={onResetFilters}
                  setFilter={setExpandFilter}
                />
                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                {expandFilter && (
                  <BiometricProviderConfigurationListFilter onApplyFilter={onApplyFilter} />
                )}
                {!tableDataLoaded ? (
                  <LoadingIcon />
                ) : applyFilter && configurations.length === 0 ? (
                  <EmptyList
                    tableHeaders={tableHeaders.map((header) => ({ id: header.id, headerNameKey: header.headerName }))}
                    showCustomButton={false}
                  />
                ) : (
                  <div className="mx-[1.5rem] overflow-x-auto">
                    <table className="w-full min-w-[900px] table-fixed">
                      <thead>
                        <tr>
                          {tableHeaders.map((header, index) => (
                            <th
                              key={index}
                              className={`px-2 py-4 text-sm font-semibold text-[#6F6E6E] ${
                                header.id === "action"
                                  ? "w-[6%] min-w-16"
                                  : header.id === "createdDateTime"
                                    ? "w-[14%] min-w-32"
                                    : "w-[20%] min-w-40"
                              }`}
                            >
                              <div
                                id={`${header.id}_header`}
                                className={`flex gap-x-0 items-center ${
                                  header.id === "action"
                                    ? "justify-center"
                                    : isLoginLanguageRTL
                                      ? "text-right"
                                      : "text-left"
                                }`}
                              >
                                {header.headerName}
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
                          ))}
                        </tr>
                      </thead>
                      <tbody>
                        {configurations.map((configuration, index) => (
                          <tr
                            id={`bio_extractor_config_list_item_${index + 1}`}
                            key={`${configuration.configName}-${index}`}
                            className={`border-t border-[#E5EBFA] text-[0.8rem] text-[#191919] font-semibold break-words`}
                          >
                            <td
                              className="px-2 py-3 whitespace-normal break-words leading-5 cursor-pointer"
                              tabIndex={0}
                              onClick={() => onViewConfiguration(configuration)}
                              onKeyDown={(e) =>
                                onPressEnterKey(e, () => onViewConfiguration(configuration))
                              }
                            >
                              {configuration.configName || "-"}
                            </td>
                            <td
                              className="px-2 py-3 whitespace-normal break-words leading-5 cursor-pointer"
                              tabIndex={0}
                              onClick={() => onViewConfiguration(configuration)}
                              onKeyDown={(e) =>
                                onPressEnterKey(e, () => onViewConfiguration(configuration))
                              }
                            >
                              {configuration.bioextractorProviderName || "-"}
                            </td>
                            <td
                              className="px-2 py-3 cursor-pointer"
                              tabIndex={0}
                              onClick={() => onViewConfiguration(configuration)}
                              onKeyDown={(e) =>
                                onPressEnterKey(e, () => onViewConfiguration(configuration))
                              }
                            >
                              {configuration.bioextractorProviderVersion || "-"}
                            </td>
                            <td
                              className="px-2 py-3 cursor-pointer"
                              tabIndex={0}
                              onClick={() => onViewConfiguration(configuration)}
                              onKeyDown={(e) =>
                                onPressEnterKey(e, () => onViewConfiguration(configuration))
                              }
                            >
                              {getModalityLabel(configuration.bioModality)}
                            </td>
                            <td
                              className="px-2 py-3 cursor-pointer"
                              tabIndex={0}
                              onClick={() => onViewConfiguration(configuration)}
                              onKeyDown={(e) =>
                                onPressEnterKey(e, () => onViewConfiguration(configuration))
                              }
                            >
                              {formatDate(configuration.createdDateTime, "date")}
                            </td>
                            <td className="px-2 py-3 text-center cursor-default">
                              <div ref={setSubmenuRef(index)}>
                                <button
                                  id={`bio_extractor_config_list_action_btn_${index + 1}`}
                                  onClick={(e) => {
                                    e.stopPropagation();
                                    setActionId(index === actionId ? -1 : index);
                                  }}
                                  className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}
                                >
                                  ...
                                </button>
                                {actionId === index && (
                                  <div
                                    className={`absolute w-[169px] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border ${
                                      isLoginLanguageRTL
                                        ? "left-10 text-right"
                                        : "right-11 text-left"
                                    }`}
                                  >
                                    <div
                                      role="button"
                                      className="flex items-center justify-between hover:bg-gray-100 h-7"
                                      onClick={(e) => {
                                        e.stopPropagation();
                                        onViewConfiguration(configuration);
                                      }}
                                      tabIndex="0"
                                      onKeyDown={(e) =>
                                        onPressEnterKey(e, () => onViewConfiguration(configuration))
                                      }
                                    >
                                      <p id="bio_extractor_config_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>
                                        {t("partnerList.view")}
                                      </p>
                                      <img
                                        src={viewIcon}
                                        alt=""
                                        className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`}
                                      />
                                    </div>
                                    <hr className="h-px bg-gray-100 border-0 mx-1" />
                                    <div
                                      role="button"
                                      className="flex items-center justify-between hover:bg-gray-100 cursor-pointer h-7"
                                      onClick={(e) => {
                                        e.stopPropagation();
                                        deleteConfiguration(configuration, index);
                                      }}
                                      tabIndex="0"
                                      onKeyDown={(e) =>
                                        onPressEnterKey(e, () => deleteConfiguration(configuration, index))
                                      }
                                    >
                                      <p
                                        id="bio_extractor_config_list_delete_btn"
                                        className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${
                                          isLoginLanguageRTL ? "pl-10" : "pr-10"
                                        }`}
                                      >
                                        {t("bioExtractorConfig.delete", "Delete")}
                                      </p>
                                      <img
                                        src={deleteIcon}
                                        alt=""
                                        className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"} w-5 h-5 object-contain`}
                                      />
                                    </div>
                                  </div>
                                )}
                                {showActiveIndexDeletePopup === index && (
                                  <DeactivatePopup
                                    closePopUp={closeDeletePopup}
                                    onClickConfirm={onClickConfirmDelete}
                                    popupData={{ ...selectedConfiguration, isDeleteBioExtractorConfig: true }}
                                    request={deleteRequest}
                                    headerMsg="bioExtractorConfig.deleteConfirmHeader"
                                    descriptionMsg="bioExtractorConfig.deleteConfirmDescription"
                                    headerKeyName={selectedConfiguration.configName}
                                  />
                                )}
                              </div>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
                <Pagination
                  dataListLength={totalRecords}
                  selectedRecordsPerPage={pageSize}
                  setSelectedRecordsPerPage={setPageSize}
                  isServerSideFilter={true}
                  getPaginationValues={(recordsPerPage, pageIndex) => {
                    if (pageNo !== pageIndex || pageSize !== recordsPerPage) {
                      setPageNo(pageIndex);
                      setPageSize(recordsPerPage);
                    }
                  }}
                  isApplyFilterClicked={isApplyFilterClicked}
                  setIsApplyFilterClicked={setIsApplyFilterClicked}
                />
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
}

export default BiometricProviderConfigurationList;
