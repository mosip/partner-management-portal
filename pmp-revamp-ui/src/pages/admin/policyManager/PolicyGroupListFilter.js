import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData } from "../../../utils/AppUtils.js";
import ApplyFilterButton from "../../common/ApplyFilterButton.js";

function PolicyGroupListFilter({ onApplyFilter }) {
    const { t } = useTranslation();
    const [status, setStatus] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
      { status: 'active' },
      { status: 'deactivated' }
    ]);
    const [filters, setFilters] = useState({
      id: "",
      name: "",
      desc: "",
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
        fieldName="id"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="policyGroupList.policyGroupId"
        placeHolderKey="policyGroupList.searchPolicyGroupId"
        styleSet={styleSet}
        id="policy_group_id_filter"
      />
      <TextInputComponent
        fieldName="name"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="policyGroupList.policyGroupName"
        placeHolderKey="policyGroupList.searchPolicyGroupName"
        styleSet={styleSet}
        id="policy_group_name_filter"
      />
      <TextInputComponent
        fieldName="desc"
        onTextChange={onFilterChangeEvent}
        fieldNameKey="policyGroupList.policyGroupDescription"
        placeHolderKey="policyGroupList.searchPolicyGroupDescription"
        styleSet={styleSet}
        id="policy_group_description_filter"
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
      <ApplyFilterButton
        filters={filters}
        onApplyFilter={onApplyFilter}
        areFiltersEmpty={areFiltersEmpty}
      />
    </div>
  );

}
export default PolicyGroupListFilter;