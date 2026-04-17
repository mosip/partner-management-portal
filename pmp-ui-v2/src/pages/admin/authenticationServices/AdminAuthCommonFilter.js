import { useEffect, useMemo, useState } from "react";
import PropTypes from "prop-types";
import { useTranslation } from "react-i18next";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import {
  createDropdownData,
  getFilterDropdownStyle,
  getFilterTextFieldStyle,
  isLangRTL,
  validateInputRegex,
} from "../../../utils/AppUtils.js";
import { getUserProfile } from "../../../services/UserProfileService";

function AdminAuthCommonFilter({
  onApplyFilter,
  statusDropdownData,
  statusPlaceholderKey,
  extraTextField,
  applyButtonId,
}) {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  const [status, setStatus] = useState([]);

  const initialFilters = useMemo(
    () => ({
      partnerId: "",
      orgName: "",
      policyGroupName: "",
      policyName: "",
      [extraTextField.fieldName]: "",
      status: "",
    }),
    [extraTextField.fieldName]
  );

  const [filters, setFilters] = useState(initialFilters);

  const [invalidPartnerId, setInvalidPartnerId] = useState("");
  const [invalidOrgName, setInvalidOrgName] = useState("");
  const [invalidPolicyGroupName, setInvalidPolicyGroupName] = useState("");
  const [invalidPolicyName, setInvalidPolicyName] = useState("");
  const [invalidExtraField, setInvalidExtraField] = useState("");

  useEffect(() => {
    setStatus(createDropdownData("status", "", true, statusDropdownData, t, t(statusPlaceholderKey)));
  }, [statusDropdownData, statusPlaceholderKey, t]);

  const onFilterChangeEvent = (fieldName, selectedFilter) => {
    setFilters((prevFilters) => ({
      ...prevFilters,
      [fieldName]: selectedFilter,
    }));

    if (fieldName === "partnerId") validateInputRegex(selectedFilter, setInvalidPartnerId, t);
    if (fieldName === "orgName") validateInputRegex(selectedFilter, setInvalidOrgName, t);
    if (fieldName === "policyGroupName") validateInputRegex(selectedFilter, setInvalidPolicyGroupName, t);
    if (fieldName === "policyName") validateInputRegex(selectedFilter, setInvalidPolicyName, t);
    if (fieldName === extraTextField.fieldName) validateInputRegex(selectedFilter, setInvalidExtraField, t);
  };

  const areFiltersEmpty = () =>
    Object.values(filters).every((value) => value === "") ||
    invalidPartnerId ||
    invalidOrgName ||
    invalidPolicyGroupName ||
    invalidPolicyName ||
    invalidExtraField;

  return (
    <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
      <TextInputComponent
        fieldName="partnerId"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="oidcClientsList.partnerId"
        placeHolderKey="partnerList.searchPartnerId"
        styleSet={getFilterTextFieldStyle()}
        id="partner_id_filter"
        maxLength={36}
        inputError={invalidPartnerId}
      />
      <TextInputComponent
        fieldName="orgName"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="oidcClientsList.orgName"
        placeHolderKey="partnerList.searchOrganisation"
        styleSet={getFilterTextFieldStyle()}
        id="org_name_filter"
        maxLength={128}
        inputError={invalidOrgName}
      />
      <TextInputComponent
        fieldName="policyGroupName"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="policiesList.policyGroup"
        placeHolderKey="policiesList.searchPolicyGroup"
        styleSet={getFilterTextFieldStyle()}
        id="policy_group_filter"
        maxLength={128}
        inputError={invalidPolicyGroupName}
      />
      <TextInputComponent
        fieldName="policyName"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="oidcClientsList.policyName"
        placeHolderKey="policiesList.searchPolicyName"
        styleSet={getFilterTextFieldStyle()}
        id="policy_name_filter"
        maxLength={128}
        inputError={invalidPolicyName}
      />
      <TextInputComponent
        fieldName={extraTextField.fieldName}
        onTextChange={onFilterChangeEvent}
        fieldNameKey={extraTextField.fieldNameKey}
        placeHolderKey={extraTextField.placeHolderKey}
        styleSet={getFilterTextFieldStyle()}
        id={extraTextField.id}
        maxLength={extraTextField.maxLength}
        inputError={invalidExtraField}
      />
      <DropdownComponent
        fieldName="status"
        dropdownDataList={status}
        onDropDownChangeEvent={onFilterChangeEvent}
        fieldNameKey="partnerList.status"
        placeHolderKey="partnerList.selectStatus"
        styleSet={getFilterDropdownStyle()}
        isPlaceHolderPresent={true}
        id="status_filter"
      />

      <div className={`mt-6 mr-6 ${isLoginLanguageRTL ? "mr-auto" : "ml-auto"}`}>
        <button
          id={applyButtonId}
          onClick={() => onApplyFilter(filters)}
          type="button"
          disabled={areFiltersEmpty()}
          className={`h-10 text-sm font-semibold px-7 text-white rounded-md ml-6 
                ${areFiltersEmpty() ? "bg-[#A5A5A5] cursor-auto" : "bg-tory-blue"}`}
        >
          {t("partnerList.applyFilter")}
        </button>
      </div>
    </div>
  );
}

AdminAuthCommonFilter.propTypes = {
  onApplyFilter: PropTypes.func.isRequired,
  statusDropdownData: PropTypes.arrayOf(PropTypes.object).isRequired,
  statusPlaceholderKey: PropTypes.string.isRequired,
  extraTextField: PropTypes.shape({
    fieldName: PropTypes.string.isRequired,
    fieldNameKey: PropTypes.string.isRequired,
    placeHolderKey: PropTypes.string.isRequired,
    id: PropTypes.string.isRequired,
    maxLength: PropTypes.number.isRequired,
  }).isRequired,
  applyButtonId: PropTypes.string.isRequired,
};

export default AdminAuthCommonFilter;

