import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData, getFilterDropdownStyle, getFilterTextFieldStyle, isLangRTL, validateInputRegex } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';
import PropTypes from 'prop-types';

function AdminOidcClientsFilter ({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
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
    const [invalidPartnerId, setInvalidPartnerId] = useState("");
    const [invalidOrgName, setInvalidOrgName] = useState("");
    const [invalidPolicyGroupName, setInvalidPolicyGroupName] = useState("");
    const [invalidPolicyName, setInvalidPolicyName] = useState("");
    const [invalidClientName, setInvalidClientName] = useState("");

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
        if (fieldName === 'clientNameEng') { validateInputRegex(selectedFilter, setInvalidClientName, t); }
    };

    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidPartnerId
        || invalidOrgName || invalidPolicyGroupName || invalidPolicyName || invalidClientName;
    };

    return (
        <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
            <TextInputComponent
                fieldName="partnerId"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.partnerId"
                placeHolderKey="partnerList.searchPartnerId"
                styleSet={getFilterTextFieldStyle()}
                id="partner_id_filter"
                inputError={invalidPartnerId}
            />
            <TextInputComponent
                fieldName="orgName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.orgName"
                placeHolderKey="partnerList.searchOrganisation"
                styleSet={getFilterTextFieldStyle()}
                id="org_name_filter"
                inputError={invalidOrgName}
            />
            <TextInputComponent
                fieldName="policyGroupName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="policiesList.policyGroup"
                placeHolderKey="policiesList.searchPolicyGroup"
                styleSet={getFilterTextFieldStyle()}
                id="policy_group_filter"
                inputError={invalidPolicyGroupName}
            />
            <TextInputComponent
                fieldName="policyName"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.policyName"
                placeHolderKey="policiesList.searchPolicyName"
                styleSet={getFilterTextFieldStyle()}
                id="policy_name_filter"
                inputError={invalidPolicyName}
            />
            <TextInputComponent
                fieldName="clientNameEng"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="oidcClientsList.oidcClientName"
                placeHolderKey="oidcClientsList.searchOidcClientName"
                styleSet={getFilterTextFieldStyle()}
                id="oidc_client_name_filter"
                inputError={invalidClientName}
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

AdminOidcClientsFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default AdminOidcClientsFilter;