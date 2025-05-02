import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData, isLangRTL, validateInputRegex } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';

function AdminApiKeysListFilter({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [status, setStatus] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
        { status: 'activated' },
        { status: 'deactivated' }
    ]);
    const [filters, setFilters] = useState({
        partnerId: "",
        orgName: "",
        policyGroupName: "",
        policyName: "",
        apiKeyLabel: "",
        status: ""
    });
    const [invalidPartnerId, setInvalidPartnerId] = useState("");
    const [invalidOrgName, setInvalidOrgName] = useState("");
    const [invalidPolicyGroupName, setInvalidPolicyGroupName] = useState("");
    const [invalidPolicyName, setInvalidPolicyName] = useState("");
    const [invalidApiKeyLabel, setInvalidApiKeyLabel] = useState("");

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
        if (fieldName === 'partnerId') { validateInputRegex(selectedFilter, setInvalidPartnerId, t); }
        if (fieldName === 'orgName') { validateInputRegex(selectedFilter, setInvalidOrgName, t); }
        if (fieldName === 'policyGroupName') { validateInputRegex(selectedFilter, setInvalidPolicyGroupName, t); }
        if (fieldName === 'policyName') { validateInputRegex(selectedFilter, setInvalidPolicyName, t); }
        if (fieldName === 'apiKeyLabel') { validateInputRegex(selectedFilter, setInvalidApiKeyLabel, t); }
    };

    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidPartnerId
        || invalidOrgName || invalidPolicyGroupName || invalidPolicyName || invalidApiKeyLabel;
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
                inputError={invalidPartnerId}
            />
            <TextInputComponent
                fieldName="orgName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.orgName"
                placeHolderKey="partnerList.searchOrganisation"
                styleSet={styleSet}
                id="org_name_filter"
                inputError={invalidOrgName}
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
            <TextInputComponent
                fieldName="policyName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.policyName"
                placeHolderKey="policiesList.searchPolicyName"
                styleSet={styleSet}
                id="policy_name_filter"
                inputError={invalidPolicyName}
            />
            <TextInputComponent
                fieldName="apiKeyLabel"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="apiKeysList.apiKeyName"
                placeHolderKey="apiKeysList.searchApiKeyName"
                styleSet={styleSet}
                id="api_key_name_filter"
                inputError={invalidApiKeyLabel}
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
                    id="apply_filter_btn"
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
export default AdminApiKeysListFilter;