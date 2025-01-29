import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData, isLangRTL } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';

function AdminOidcClientsFilter ({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [status, setStatus] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
      { status: 'ACTIVE' },
      { status: 'INACTIVE'}
    ]);
    const [filters, setFilters] = useState({
      partnerId: "",
      orgName: "",
      policyGroupName: "",
      policyName: "",
      clientNameEng: "",
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
        <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
            <TextInputComponent
                fieldName="partnerId"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.partnerId"
                placeHolderKey="partnerList.searchPartnerId"
                styleSet={styleSet}
                id="partner_id_filter"
            />
            <TextInputComponent
                fieldName="orgName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.orgName"
                placeHolderKey="partnerList.searchOrganisation"
                styleSet={styleSet}
                id="org_name_filter"
            />
            <TextInputComponent
                fieldName="policyGroupName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="policiesList.policyGroup"
                placeHolderKey="policiesList.searchPolicyGroup"
                styleSet={styleSet}
                id="policy_group_filter"
            />
            <TextInputComponent
                fieldName="policyName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.policyName"
                placeHolderKey="policiesList.searchPolicyName"
                styleSet={styleSet}
                id="policy_name_filter"
            />
            <TextInputComponent
                fieldName="clientNameEng"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.oidcClientName"
                placeHolderKey="oidcClientsList.searchOidcClientName"
                styleSet={styleSet}
                id="oidc_client_name_filter"
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
export default AdminOidcClientsFilter;