import React, { useEffect, useMemo, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import {
  formatDate,
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
import BiometricProviderConfigurationListFilter from "./BiometricProviderConfigurationListFilter";

function BiometricProviderConfigurationList() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
  const submenuRef = useRef([]);

  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(true);
  const [tableDataLoaded, setTableDataLoaded] = useState(true);
  const [expandFilter, setExpandFilter] = useState(false);
  const [applyFilter, setApplyFilter] = useState(false);
  const [actionId, setActionId] = useState(-1);
  const [order, setOrder] = useState("DESC");
  const [activeAscIcon, setActiveAscIcon] = useState("");
  const [activeDescIcon, setActiveDescIcon] = useState("createdDateTime");
  const [sortFieldName, setSortFieldName] = useState("createdDateTime");
  const [sortType, setSortType] = useState("DESC");
  const [configurations, setConfigurations] = useState([]);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(
    sessionStorage.getItem("itemsPerPage")
      ? Number(sessionStorage.getItem("itemsPerPage"))
      : 8
  );
  const [firstIndex, setFirstIndex] = useState(0);
  const [filterAttributes, setFilterAttributes] = useState({
    configName: "",
    bioextractorProviderName: "",
    bioextractorProviderVersion: "",
    bioModality: "",
  });

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
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
    setDataLoaded(false);
    try {
      const response = await HttpService.get(
        getPartnerManagerUrl("/bio-extractor-configurations", process.env.NODE_ENV)
      );
      if (response?.data?.response) {
        const data = response.data.response.data || response.data.response || [];
        const normalizedData = Array.isArray(data)
          ? data.map((item) => ({
              configName: item.configName || "-",
              bioextractorProviderName: item.bioextractorProviderName || "-",
              bioextractorProviderVersion: item.bioextractorProviderVersion || "-",
              bioModality: item.bioModality || "-",
              createdDateTime: item.createdDateTime || item.createdDate || null,
            }))
          : [];
        setConfigurations(normalizedData);
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
    setDataLoaded(true);
  };

  useEffect(() => {
    fetchConfigurations();
  }, []);

  const sortValue = (item, field) => {
    if (field === "createdDateTime") {
      const value = item[field];
      return value ? new Date(value).getTime() : 0;
    }
    if (field === "bioModality") {
      return getModalityLabel(item[field]).toLowerCase();
    }
    return String(item[field] || "").toLowerCase();
  };

  const filteredAndSortedConfigurations = useMemo(() => {
    const filtered = configurations.filter((item) => {
      const byConfigName = !filterAttributes.configName ||
        String(item.configName).toLowerCase().includes(filterAttributes.configName.toLowerCase());
      const byProviderName = !filterAttributes.bioextractorProviderName ||
        String(item.bioextractorProviderName).toLowerCase().includes(filterAttributes.bioextractorProviderName.toLowerCase());
      const byVersion = !filterAttributes.bioextractorProviderVersion ||
        String(item.bioextractorProviderVersion).toLowerCase().includes(filterAttributes.bioextractorProviderVersion.toLowerCase());
      const byModality = !filterAttributes.bioModality ||
        String(item.bioModality).toUpperCase() === String(filterAttributes.bioModality).toUpperCase();
      return byConfigName && byProviderName && byVersion && byModality;
    });

    const sorted = [...filtered].sort((a, b) => {
      const aValue = sortValue(a, sortFieldName);
      const bValue = sortValue(b, sortFieldName);
      if (aValue < bValue) return sortType === "ASC" ? -1 : 1;
      if (aValue > bValue) return sortType === "ASC" ? 1 : -1;
      return 0;
    });
    return sorted;
  }, [configurations, filterAttributes, sortFieldName, sortType]);

  const currentPageData = useMemo(() => {
    return filteredAndSortedConfigurations.slice(
      firstIndex,
      firstIndex + selectedRecordsPerPage
    );
  }, [filteredAndSortedConfigurations, firstIndex, selectedRecordsPerPage]);

  const onApplyFilter = (updatedFilters) => {
    setTableDataLoaded(false);
    setFilterAttributes(updatedFilters);
    setApplyFilter(true);
    setFirstIndex(0);
    setTableDataLoaded(true);
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
    setFirstIndex(0);
  };

  const sortAscOrder = (header) => {
    if (order !== "ASC" || activeAscIcon !== header) {
      setSortFieldName(header);
      setSortType("ASC");
      setOrder("ASC");
      setActiveDescIcon("");
      setActiveAscIcon(header);
    }
  };

  const sortDescOrder = (header) => {
    if (order !== "DESC" || activeDescIcon !== header) {
      setSortFieldName(header);
      setSortType("DESC");
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
  };

  const setSubmenuRef = (index) => (el) => {
    submenuRef.current[index] = el;
  };

  const styles = {
    loadingDiv: "!py-[20%]",
    outerDiv: "!bg-opacity-35",
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
                subTitle="dashboard.biometricProviderConfigurationDesc"
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
                  {t("bioExtractorConfig.createBioExtractorConfig", "Create Biometric Extractor Provider Configuration")}
                </button>
              )}
            </div>
            {!applyFilter && configurations.length === 0 ? (
              <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                <EmptyList
                  tableHeaders={tableHeaders.map((header) => ({ id: header.id, headerNameKey: header.headerName }))}
                  showCustomButton={true}
                  customButtonName="Create Biometric Extractor Provider Configuration"
                  buttonId="bio_extractor_config_create_btn_center"
                  onClickButton={gotoCreatePage}
                  disableBtn={false}
                />
              </div>
            ) : (
              <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded ? "py-6" : ""}`}>
                <FilterButtons
                  listTitle="List of Biometric Extractor Provider Configuration"
                  dataListLength={filteredAndSortedConfigurations.length}
                  filter={expandFilter}
                  onResetFilter={onResetFilters}
                  setFilter={setExpandFilter}
                />
                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                {expandFilter && (
                  <BiometricProviderConfigurationListFilter onApplyFilter={onApplyFilter} />
                )}
                {!tableDataLoaded ? (
                  <LoadingIcon styleSet={styles} />
                ) : (
                  <>
                    {applyFilter && filteredAndSortedConfigurations.length === 0 ? (
                      <EmptyList
                        tableHeaders={tableHeaders.map((header) => ({ id: header.id, headerNameKey: header.headerName }))}
                        showCustomButton={false}
                      />
                    ) : (
                      <div className="mx-[1.5rem] overflow-x-scroll">
                        <table className="table-fixed">
                          <thead>
                            <tr>
                              {tableHeaders.map((header, index) => (
                                <th key={index} className="py-4 text-sm font-semibold text-[#6F6E6E] w-[13%]">
                                  <div id={`${header.id}_header`} className={`mx-2 flex gap-x-0 items-center ${header.id === "action" ? "justify-center" : isLoginLanguageRTL ? "text-right" : "text-left"}`}>
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
                            {currentPageData.map((configuration, index) => (
                              <tr
                                id={`bio_extractor_config_list_item_${index + 1}`}
                                key={`${configuration.configName}-${index}`}
                                className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words`}
                              >
                                <td className="px-2 py-3">{configuration.configName || "-"}</td>
                                <td className="px-2 py-3">{configuration.bioextractorProviderName || "-"}</td>
                                <td className="px-2 py-3">{configuration.bioextractorProviderVersion || "-"}</td>
                                <td className="px-2 py-3">{getModalityLabel(configuration.bioModality)}</td>
                                <td className="px-2 py-3">{formatDate(configuration.createdDateTime, "date")}</td>
                                <td className="px-2 py-3 text-center cursor-default">
                                  <div ref={setSubmenuRef(index)}>
                                    <button
                                      id={`bio_extractor_config_list_action_btn_${index + 1}`}
                                      onClick={() => setActionId(index === actionId ? null : index)}
                                      className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}
                                    >
                                      ...
                                    </button>
                                    {actionId === index && (
                                      <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                        <div
                                          role="button"
                                          className="flex justify-between hover:bg-gray-100"
                                          onClick={() => onViewConfiguration(configuration)}
                                          tabIndex="0"
                                          onKeyDown={(e) => onPressEnterKey(e, () => onViewConfiguration(configuration))}
                                        >
                                          <p id="bio_extractor_config_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>
                                            {t("partnerList.view")}
                                          </p>
                                          <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                        </div>
                                      </div>
                                    )}
                                  </div>
                                </td>
                              </tr>
                            ))}
                          </tbody>
                        </table>
                      </div>
                    )}
                  </>
                )}
                <Pagination
                  dataListLength={filteredAndSortedConfigurations.length}
                  selectedRecordsPerPage={selectedRecordsPerPage}
                  setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                  setFirstIndex={setFirstIndex}
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
