import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { isLangRTL, createDropdownData, createRequest, getPartnerManagerUrl, handleServiceErrors } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';
import { HttpService } from "../../../services/HttpService.js";

function PolicyRequestsListFilter({ onApplyFilter, setErrorCode, setErrorMsg }) {
  const { t } = useTranslation();

  const [partnerType, setPartnerType] = useState([]);
  const [status, setStatus] = useState([]);
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [statusDropdownData, setStatusDropdownData] = useState([
    { status: 'approved' },
    { status: 'rejected' },
    { status: 'InProgress' }
  ]);
  const [filters, setFilters] = useState({
    partnerId: "",
    partnerType: "",
    status: "",
    orgName: "",
    policyId: "",
    policyName: "",
    policyGroupName: ""
  });

  useEffect(() => {
    const fetchData = async () => {
      const partnerTypeDropdownData = await fetchPartnerTypeData();
      setPartnerType(
        createDropdownData("partnerType", "", true, partnerTypeDropdownData, t, t("partnerPolicyMappingRequestList.selectPartnerType"))
      );
      setStatus(
        createDropdownData("status", "", true, statusDropdownData, t, t("partnerPolicyMappingRequestList.selectStatus"))
      );
    };

    fetchData();
  }, [t]);

  async function fetchPartnerTypeData() {
    const request = createRequest({
      "filters": [],
      "pagination": { "pageFetch": 100, "pageStart": 0 },
      "sort": []
    });

    try {
      const response = await HttpService.post(
        getPartnerManagerUrl(`/partners/partnertype/search`, process.env.NODE_ENV),
        request
      );

      if (response && response.data) {
        const responseData = response.data;
        if (responseData.response && responseData.response.data) {
          const partnerTypeData = responseData.response.data.map(item => ({
            partnerType: item.code
          }));
          return partnerTypeData;
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          return [];
        }
      } else {
        setErrorMsg(t('partnerPolicyMappingRequestList.errorInpartnerPolicyMappingRequestList'));
        return [];
      }
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.message || t('partnerPolicyMappingRequestList.errorInpartnerPolicyMappingRequestList'));
      }
      console.error("Error fetching partner type data: ", err);
      return [];
    }
  }


  const onFilterChangeEvent = (fieldName, selectedFilter) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [fieldName]: selectedFilter
    }));
  };

  const areFiltersEmpty = () => {
    return Object.values(filters).every(value => value === "");
  };

  const styles = {
    dropdownButton: "min-w-64",
  };

  const styleSet = {
    inputField: "min-w-64 h-9",
    inputLabel: "mb-2",
    outerDiv: "ml-4"
  };

  return (
    <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
      <TextInputComponent
        fieldName="partnerId"
        onTextChange={onFilterChangeEvent}
        textBoxValue={filters.partnerId}
        fieldNameKey="partnerPolicyMappingRequestList.partnerId"
        placeHolderKey="partnerPolicyMappingRequestList.searchPartnerId"
        styleSet={styleSet}
        id="partner_id_filter"
      />
      <DropdownComponent
        fieldName="partnerType"
        dropdownDataList={partnerType}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="partnerPolicyMappingRequestList.partnerType"
        placeHolderKey="partnerPolicyMappingRequestList.selectPartnerType"
        styleSet={styles}
        isPlaceHolderPresent={true}
        id="partner_type_filter"
      />
      <TextInputComponent
        fieldName="orgName"
        onTextChange={onFilterChangeEvent}
        textBoxValue={filters.orgName}
        fieldNameKey="partnerPolicyMappingRequestList.organisation"
        placeHolderKey="partnerPolicyMappingRequestList.searchOrganisation"
        styleSet={styleSet}
        id="partner_organisation_filter"
      />
      <TextInputComponent
        fieldName="policyId"
        onTextChange={onFilterChangeEvent}
        textBoxValue={filters.policyId}
        fieldNameKey="partnerPolicyMappingRequestList.policyId"
        placeHolderKey="partnerPolicyMappingRequestList.searchPolicyId"
        styleSet={styleSet}
        id="policy_id_filter"
      />
      <TextInputComponent
        fieldName="policyName"
        onTextChange={onFilterChangeEvent}
        textBoxValue={filters.policyName}
        fieldNameKey="partnerPolicyMappingRequestList.policyName"
        placeHolderKey="partnerPolicyMappingRequestList.searchPolicyName"
        styleSet={styleSet}
        id="policy_name_filter"
      />
      <TextInputComponent
        fieldName="policyGroupName"
        onTextChange={onFilterChangeEvent}
        textBoxValue={filters.policyGroupName}
        fieldNameKey="partnerPolicyMappingRequestList.policyGroupName"
        placeHolderKey="partnerPolicyMappingRequestList.searchPolicyGroup"
        styleSet={styleSet}
        id="policy_group_filter"
      />
      <DropdownComponent
        fieldName="status"
        dropdownDataList={status}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="partnerPolicyMappingRequestList.status"
        placeHolderKey="partnerPolicyMappingRequestList.selectStatus"
        styleSet={styles}
        isPlaceHolderPresent={true}
        id="status_filter"
      />
      <div className={`mt-6 mr-6 ${isLoginLanguageRTL ? "mr-auto" : "ml-auto"}`}>
        <button
          id="apply_filter__btn"
          onClick={() => onApplyFilter(filters)}
          type="button"
          disabled={areFiltersEmpty()}
          className={`h-10 text-sm font-semibold px-7 text-white rounded-md ml-6 
          ${areFiltersEmpty() ? 'bg-[#A5A5A5] cursor-auto' : 'bg-tory-blue'}`}
        >
          {t("partnerPolicyMappingRequestList.applyFilter")}
        </button>
      </div>
    </div>
  );
}

export default PolicyRequestsListFilter;
