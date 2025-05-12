import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { isLangRTL, createDropdownData, validateInputRegex } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';
import PropTypes from 'prop-types';

function PoliciesListFilter({ onApplyFilter }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [status, setStatus] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
      { status: 'activated' },
      { status: 'deactivated'},
      { status: 'draft'}
    ]);
    const [filters, setFilters] = useState({
      policyId: "",
      policyName: "",
      policyDescription: "",
      policyGroupName: "",
      status: "",
    });
    const [invalidPolicyId, setInvalidPolicyId] = useState("");
    const [invalidPolicyName, setInvalidPolicyName] = useState("");
    const [invalidPolicyDesc, setInvalidPolicyDesc] = useState("");
    const [invalidPolicyGroupName, setInvalidPolicyGroupName] = useState("");

    useEffect(() => {
      const fetchData = async () => {
        setStatus(
          createDropdownData("status", "", true, statusDropdownData, t, t("partnerList.selectStatus"))
        );
      };
  
      fetchData();
    }, [t]);

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
      setFilters((prevFilters) => ({
        ...prevFilters,
        [fieldName]: selectedFilter
      }));
      if (fieldName === 'policyId') { validateInputRegex(selectedFilter, setInvalidPolicyId, t); }
      if (fieldName === 'policyName') { validateInputRegex(selectedFilter, setInvalidPolicyName, t); }
      if (fieldName === 'policyDescription') { validateInputRegex(selectedFilter, setInvalidPolicyDesc, t); }
      if (fieldName === 'policyGroupName') { validateInputRegex(selectedFilter, setInvalidPolicyGroupName, t); }
    };

    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidPolicyId || invalidPolicyName
          || invalidPolicyDesc || invalidPolicyGroupName;
      };
    
    const styles = {
      dropdownButton: "min-w-64",
    };
    
    const styleSet = {
      inputField: "min-w-64",
      inputLabel: "mb-2",
      outerDiv: "ml-4"
    };

    return (
        <div className="flex w-full p-2.5 justify-start bg-[#F7F7F7] flex-wrap">
          <TextInputComponent
            fieldName="policyId"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyId"
            placeHolderKey="policiesList.searchPolicyId"
            styleSet={styleSet}
            id="policy_id_filter"
            inputError={invalidPolicyId}
          />
          <TextInputComponent
            fieldName="policyName"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyName"
            placeHolderKey="policiesList.searchPolicyName"
            styleSet={styleSet}
            id="policy_name_filter"
            inputError={invalidPolicyName}
          />
          <TextInputComponent
            fieldName="policyDescription"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyDescription"
            placeHolderKey="policiesList.searchPolicyDescription"
            styleSet={styleSet}
            id="policy_description_filter"
            inputError={invalidPolicyDesc}
          />
          <TextInputComponent
            fieldName="policyGroupName"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyGroup"
            placeHolderKey="policiesList.searchPolicyGroup"
            styleSet={styleSet}
            id="policy_group_filter"
            inputError={invalidPolicyGroupName}
          />
          <DropdownComponent
            fieldName="status"
            dropdownDataList={status}
            onDropDownChangeEvent={onFilterChangeEvent}
            fieldNameKey="partnerList.status"
            placeHolderKey="partnerList.selectStatus"
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
              {t("partnerList.applyFilter")}
            </button>
          </div>
        </div>
    );
}

PoliciesListFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default PoliciesListFilter;