import { useState, useEffect, useRef, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../../utils/AppUtils';
import {
  formatDate, getPartnerManagerUrl, getPartnerTypeDescription, getStatusCode, handleMouseClickForDropdown,
  toggleSortAscOrder, toggleSortDescOrder, bgOfStatus, getPartnerPolicyRequests, setSubmenuRef
} from '../../../utils/AppUtils';
import { HttpService } from '../../../services/HttpService';
import PoliciesFilter from './PoliciesFilter';
import ErrorMessage from '../../common/ErrorMessage';
import LoadingIcon from "../../common/LoadingIcon";
import FilterButtons from '../../common/FilterButtons';
import SortingIcon from '../../common/SortingIcon';
import viewIcon from "../../../svg/view_icon.svg";
import Pagination from '../../common/Pagination';
import Title from '../../common/Title';
import EmptyList from '../../common/EmptyList';

function PoliciesList() {

  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
  const navigate = useNavigate();
  const [filter, setFilter] = useState(false);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [policiesList, setPoliciesList] = useState([]);
  const [filteredPoliciesList, setFilteredPoliciesList] = useState([]);
  const [order, setOrder] = useState("DESC");
  const [activeSortAsc, setActiveSortAsc] = useState("");
  const [activeSortDesc, setActiveSortDesc] = useState("createdDateTime");
  const [firstIndex, setFirstIndex] = useState(0);
  const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
  const [isDescending, setIsDescending] = useState(false);
  const [viewPolicyId, setViewPolicyId] = useState(-1);
  const [actionEligibilityByKey, setActionEligibilityByKey] = useState({});
  const defaultFilterQuery = {
    partnerId: "",
    policyGroupName: ""
  };
  const [filterQuery, setFilterQuery] = useState({ ...defaultFilterQuery });
  const submenuRef = useRef([]);

  useEffect(() => {
    handleMouseClickForDropdown(submenuRef, () => setViewPolicyId(-1));
  }, [submenuRef]);

  const tableHeaders = useMemo(() => [
    { id: "partnerId", headerNameKey: 'policies.partnerId' },
    { id: "partnerType", headerNameKey: "policies.partnerType" },
    { id: "policyGroupName", headerNameKey: "policies.policyGroupName" },
    { id: "policyId", headerNameKey: "policies.policyId" },
    { id: "policyName", headerNameKey: "policies.policyName" },
    { id: "createdDateTime", headerNameKey: "policies.creationDate" },
    { id: "status", headerNameKey: "policies.status" },
    { id: "action", headerNameKey: 'policies.action' }
  ], []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const response = await getPartnerPolicyRequests(HttpService, setErrorCode, setErrorMsg, t);
        if (response) {
          const sortedData = response.sort((a, b) => new Date(b.createdDateTime) - new Date(a.createdDateTime));
          setPoliciesList(sortedData);
          setFilteredPoliciesList(sortedData);
        } else {
          setErrorMsg(t('policies.errorInPoliciesList'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
        }
      }
    };
    fetchData();
  }, [t]);

  const showRequestPolicy = () => {
    navigate('/partnermanagement/policies/request-policy')
  }

  const showViewPolicyDetails = (selectedPolicyData) => {
    sessionStorage.setItem('selectedPolicyAttributes', JSON.stringify(selectedPolicyData));
    navigate('/partnermanagement/policies/view-policy-details')
  };

  const showMapCredentialType = (selectedPolicyData) => {
    navigate('/partnermanagement/policies/map-credential-type', {
      state: {
        partnerId: selectedPolicyData?.partnerId,
        partnerType: selectedPolicyData?.partnerType,
        policyGroupName: selectedPolicyData?.policyGroupName,
        policyName: selectedPolicyData?.policyName,
        policyId: selectedPolicyData?.policyId,
      }
    });
  };

  const showAddBioextractors = (selectedPolicyData) => {
    const mappingKey =
      selectedPolicyData?.mappingKey ??
      selectedPolicyData?.mappingkey ??
      selectedPolicyData?.id ??
      selectedPolicyData?.mappingId ??
      "";

    navigate('/partnermanagement/policies/map-biometric-extractor-provider', {
      state: {
        partnerId: selectedPolicyData?.partnerId,
        partnerType: selectedPolicyData?.partnerType,
        policyGroupName: selectedPolicyData?.policyGroupName,
        policyName: selectedPolicyData?.policyName,
        policyId: selectedPolicyData?.policyId,
        mappingKey,
      }
    });
  };

  const getRowKey = (row) => `${row?.partnerId || ""}::${row?.policyId || ""}::${row?.policyName || ""}`;

  const isFinalStatus = (status) => {
    const normalized = String(status || "").toLowerCase();
    return normalized === "approved" || normalized === "rejected";
  };

  const ensureEligibilityLoaded = async (row) => {
    const key = getRowKey(row);
    if (!key || actionEligibilityByKey[key]?.loaded) return;

    // optimistic: allow until proven mapped (except approved/rejected)
    setActionEligibilityByKey((prev) => ({
      ...prev,
      [key]: { loaded: false, bioMapped: false, credentialMapped: false, loading: true },
    }));

    try {
      const partnerId = row?.partnerId;
      const policyId = row?.policyId;
      const policyName = row?.policyName;

      let bioMapped = false;
      let credentialMapped = false;

      const hasMeaningfulBioMapping = (item) => {
        if (!item) return false;
        const extractor = item.extractor ?? item?.data?.extractor ?? item?.mapping?.extractor ?? null;
        const provider =
          extractor?.provider ??
          item?.provider ??
          item?.bioextractorProviderName ??
          item?.extractorProviderName ??
          "";
        const version =
          extractor?.version ??
          item?.version ??
          item?.bioextractorProviderVersion ??
          item?.extractorProviderVersion ??
          "";
        return Boolean(String(provider || "").trim()) || Boolean(String(version || "").trim());
      };

      // bioextractors mapping check
      if (partnerId && policyId) {
        const url = getPartnerManagerUrl(`/partners/${partnerId}/bioextractors/${policyId}`, process.env.NODE_ENV);
        const res = await HttpService.get(url);
        const data = res?.data;
        if (data?.response) {
          const payload = data.response;
          const list = Array.isArray(payload)
            ? payload
            : (payload.extractors ?? payload.data ?? payload.bioExtractors ?? payload.bioextractors ?? []);
          bioMapped = Array.isArray(list) && list.some(hasMeaningfulBioMapping);
        } else {
          const firstCode = data?.errors?.[0]?.errorCode;
          // PMS_PRT_064 => not configured yet => not mapped
          if (firstCode && firstCode !== "PMS_PRT_064") {
            // unknown error: keep as not mapped (so user can try)
            bioMapped = false;
          }
        }
      }

      // credential type mapping check (reuse existing read endpoint used elsewhere)
      if (partnerId && policyId) {
        const url = getPartnerManagerUrl(`/partners/${partnerId}/policies/${policyId}/credential-types`, process.env.NODE_ENV);
        const res = await HttpService.get(url);
        const data = res?.data;
        if (data?.response !== undefined) {
          const payload = data.response;
          if (Array.isArray(payload)) credentialMapped = payload.map((x) => String(x ?? "").trim()).filter(Boolean).length > 0;
          else if (typeof payload === "string") credentialMapped = Boolean(payload.trim());
          else {
            const types = payload?.credentialTypes ?? payload?.credential_types ?? payload?.data?.credentialTypes ?? payload?.data?.credential_types;
            if (Array.isArray(types)) credentialMapped = types.map((x) => String(x ?? "").trim()).filter(Boolean).length > 0;
            else {
              const value = payload?.credentialType ?? payload?.credential_type ?? payload?.type ?? payload?.data?.credentialType ?? payload?.data?.credential_type;
              credentialMapped = Boolean(String(value || "").trim());
            }
          }
        }
      } else if (partnerId && policyName) {
        // If we don't have policyId, we can't reliably check. Keep enabled.
        credentialMapped = false;
      }

      setActionEligibilityByKey((prev) => ({
        ...prev,
        [key]: { loaded: true, bioMapped, credentialMapped, loading: false },
      }));
    } catch (e) {
      setActionEligibilityByKey((prev) => ({
        ...prev,
        [key]: { loaded: true, bioMapped: false, credentialMapped: false, loading: false },
      }));
    }
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  //This part is related to Sorting
  const sortAscOrder = (header) => {
    const isDateCol = (header === "createdDateTime") ? true : false;
    toggleSortAscOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  const sortDescOrder = (header) => {
    const isDateCol = (header === "createdDateTime") ? true : false;
    toggleSortDescOrder(header, isDateCol, filteredPoliciesList, setFilteredPoliciesList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc);
  }

  //This part is related to Filter
  const onFilterChange = (fieldName, selectedFilter) => {
    setFilterQuery(oldFilterQuery => ({
      ...oldFilterQuery,
      [fieldName]: selectedFilter
    }));

    //useEffect will be triggered which will do the filter 
  }
  useEffect(() => {
    let filteredRows = policiesList;
    const allowedKeys = tableHeaders.map(header => header.id);
    Object.keys(filterQuery).forEach(key => {
      if (allowedKeys.includes(key) && filterQuery[key] !== '') {
        filteredRows = filteredRows.filter(item => item[key] === filterQuery[key]);
      }
    });
    setFilteredPoliciesList(filteredRows);
    setFirstIndex(0);
  }, [filterQuery, policiesList, tableHeaders]);

  const onResetFilter = () => {
    window.location.reload();
  }

  //This  part related to Pagination logic
  let tableRows = filteredPoliciesList.slice(firstIndex, firstIndex + (selectedRecordsPerPage));

  const style = {
    backArrowIcon: "!mt-[9%]"
  }

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage id='polices_list_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between mb-3">
              <Title title='policies.policies' backLink='/partnermanagement' styleSet={style} />

              {policiesList.length > 0 ?
                <button id='policies_request_btn' onClick={() => showRequestPolicy()} type="button" className={`h-12 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md`}>
                  {t('policies.requestPolicyBtn')}
                </button>
                : null
              }
            </div>

            <div className="flex-col justify-center ml-3 h-full">
              {policiesList.length === 0
                ?
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                  <EmptyList
                    tableHeaders={tableHeaders}
                    showCustomButton={true}
                    customButtonName='policies.requestPolicyBtn'
                    buttonId='show_request_policy'
                    onClickButton={showRequestPolicy}
                  />
                </div>
                :
                <>
                  <div className="bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3">
                    <FilterButtons titleId='list_of_policies' listTitle='policies.listOfPolicies' dataListLength={filteredPoliciesList.length} filter={filter} onResetFilter={onResetFilter} setFilter={setFilter}></FilterButtons>
                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                    {filter &&
                      <PoliciesFilter
                        filteredPoliciesList={filteredPoliciesList}
                        onFilterChange={onFilterChange}>
                      </PoliciesFilter>}

                    <div className="mx-[1.4rem] overflow-x-scroll">
                      <table className="table-fixed">
                        <thead>
                          <tr>
                            {tableHeaders.map((header, index) => {
                              return (
                                <th key={index} className="py-4 px-2 text-sm text-left font-semibold text-[#6F6E6E] w-[15%]">
                                  <div id={`${header.headerNameKey}_header`} className="flex gap-x-0 items-center">
                                    {t(header.headerNameKey)}
                                    {header.id !== "action" && (
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
                                </th>
                              )
                            })}
                          </tr>
                        </thead>
                        <tbody>
                          {tableRows.map((partner, index) => {
                            return (
                              <tr id={'policy_list_item' + (index + 1)} key={index} className={`border-t border-[#E5EBFA] cursor-pointer text-[0.8rem] text-[#191919] font-semibold break-words ${partner.status.toLowerCase() === "deactivated" ? "text-[#969696]" : "text-[#191919]"}`}>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.partnerId}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{getPartnerTypeDescription(partner.partnerType, t)}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyGroupName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyId}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{partner.policyName}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">{formatDate(partner.createdDateTime, 'date')}</td>
                                <td onClick={() => showViewPolicyDetails(partner)} className="px-2">
                                  <div className={`${bgOfStatus(partner.status)} flex w-fit py-1.5 px-2  my-3 text-xs font-semibold rounded-md`}>
                                    {getStatusCode(partner.status, t)}
                                  </div>
                                </td>
                                <td className="text-center cursor-default">
                                  <div ref={setSubmenuRef(submenuRef, index)}>
                                    <button
                                      id={'policy_list_view' + (index + 1)}
                                      onClick={() => {
                                        const next = index === viewPolicyId ? null : index;
                                        setViewPolicyId(next);
                                        if (next !== null) ensureEligibilityLoaded(partner);
                                      }}
                                      className={`font-semibold mb-0.5 text-center cursor-pointer`}
                                    >
                                      ...
                                    </button>
                                    {viewPolicyId === index && (
                                      <div className={`border bg-white absolute text-xs font-semibold rounded-md shadow-md w-[12rem] px-1.5 py-2 z-20 ${isLoginLanguageRTL ? "left-[4.5rem] text-right" : "right-[4.5rem] text-left"}`}>
                                        <div
                                          role='button'
                                          id='policy_list_view_card'
                                          onClick={() => showViewPolicyDetails(partner)}
                                          tabIndex="0"
                                          onKeyDown={(e) => onPressEnterKey(e, () => showViewPolicyDetails(partner))}
                                          className="flex justify-between items-center cursor-pointer hover:bg-gray-100 px-2 py-1 rounded"
                                        >
                                          <p>{t('policies.view')}</p>
                                          <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                        </div>

                                        {String(partner?.partnerType ?? '').toUpperCase() === 'CREDENTIAL_PARTNER' && (
                                          <>
                                            {(() => {
                                              const key = getRowKey(partner);
                                              const eligibility = actionEligibilityByKey[key] || {};
                                              const finalStatus = isFinalStatus(partner?.status);
                                              const disableBio = finalStatus || Boolean(eligibility.bioMapped);
                                              const disableCredential = finalStatus || Boolean(eligibility.credentialMapped);

                                              const disabledItemClass = "text-[#A5A5A5] cursor-default pointer-events-none";
                                              const enabledItemClass = "cursor-pointer hover:bg-gray-100";

                                              return (
                                                <>
                                            <hr className="h-px bg-gray-100 border-0 my-1" />
                                            <div
                                              role="button"
                                              id="policy_list_add_bioextractors"
                                              onClick={() => !disableBio && showAddBioextractors(partner)}
                                              tabIndex="0"
                                              onKeyDown={(e) => !disableBio && onPressEnterKey(e, () => showAddBioextractors(partner))}
                                              className={`flex justify-between items-center px-2 py-1 rounded ${disableBio ? disabledItemClass : enabledItemClass}`}
                                            >
                                              <p className="flex items-center gap-2">
                                                <span className="text-base leading-none">+</span>
                                                <span>{t('mapBiometricExtractorProvider.addBioextractors')}</span>
                                              </p>
                                            </div>
                                            <hr className="h-px bg-gray-100 border-0 my-1" />
                                            <div
                                              role="button"
                                              id="policy_list_map_credential_type"
                                              onClick={() => !disableCredential && showMapCredentialType(partner)}
                                              tabIndex="0"
                                              onKeyDown={(e) => !disableCredential && onPressEnterKey(e, () => showMapCredentialType(partner))}
                                              className={`flex justify-between items-center px-2 py-1 rounded ${disableCredential ? disabledItemClass : enabledItemClass}`}
                                            >
                                              <p className="flex items-center gap-2">
                                                <span className="text-base leading-none">+</span>
                                                <span>{t('mapCredentialType.title')}</span>
                                              </p>
                                            </div>
                                                </>
                                              );
                                            })()}
                                          </>
                                        )}
                                      </div>
                                    )}
                                  </div>
                                </td>
                              </tr>
                            )
                          })
                          }
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <Pagination dataListLength={filteredPoliciesList.length} selectedRecordsPerPage={selectedRecordsPerPage} setSelectedRecordsPerPage={setSelectedRecordsPerPage} setFirstIndex={setFirstIndex}></Pagination>
                </>
              }
            </div>
          </div>

        </>
      )}
    </div>
  )

}

export default PoliciesList;