import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { isLangRTL, createDropdownData } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';

function PoliciesListFilter({ onApplyFilter }) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
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
    };

    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "");
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
          />
          <TextInputComponent
            fieldName="policyName"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyName"
            placeHolderKey="policiesList.searchPolicyName"
            styleSet={styleSet}
            id="policy_name_filter"
          />
          <TextInputComponent
            fieldName="policyDescription"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyDescription"
            placeHolderKey="policiesList.searchPolicyDescription"
            styleSet={styleSet}
            id="policy_description_filter"
          />
          <TextInputComponent
            fieldName="policyGroupName"
            onTextChange={onFilterChangeEvent}
            fieldNameKey="policiesList.policyGroup"
            placeHolderKey="policiesList.searchPolicyGroup"
            styleSet={styleSet}
            id="policy_group_filter"
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

export default PoliciesListFilter;