import { useEffect, useState } from "react";
import { createDropdownData, getFilterDropdownStyle, getFilterTextFieldStyle, getOuterDivWidth, getPartnerManagerUrl, handleServiceErrors, isLangRTL, validateInputRegex } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import PropTypes from 'prop-types';
import { useTranslation } from "react-i18next";
import TextInputComponent from "../../common/fields/TextInputComponent";
import CalendarInput from "../../common/CalendarInput";
import DropdownComponent from "../../common/fields/DropdownComponent";
import { HttpService } from "../../../services/HttpService";

function ApiKeyNotificationFilter({ onApplyFilter, setErrorCode, setErrorMsg }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [policyNameDropdownData, setPolicyNameDropdownData] = useState([]);
    const [filters, setFilters] = useState({
        apiKeyName: "",
        policyName: "",
        expiryDate: ""
    });
    const [invalidApiKeyName, setInvalidApiKeyName] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            const policyNameData = await fetchPolicyNameDropdownData();
            setPolicyNameDropdownData(createDropdownData("policyName", "", true, policyNameData, t, t("policies.selectPolicyName")));
        };
        fetchData();
    }, [t]);

    const fetchPolicyNameDropdownData = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('status', 'approved');
        const url = `${getPartnerManagerUrl('/partner-policy-requests', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    return responseData.response.data;
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                    return [];
                }
            } else {
                setErrorMsg(t('commons.errorInResponse'));
                return [];
            }
        } catch (err) {
            console.log("Error fetching data: ", err);
            return [];
        }
    }

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter
        }));
        if (fieldName === 'apiKeyName') { validateInputRegex(selectedFilter, setInvalidApiKeyName, t); }
    };

    const handleExpiryDateChange = (newDateStr) => {
        onFilterChangeEvent("expiryDate", newDateStr);
    };
        
    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidApiKeyName;
    };

    const calenderStyleSet = {
        datePicker: `h-[2.4rem] p-1 ${isLoginLanguageRTL && 'pr-8'}`,
        outerDiv: `ml-4 ${getOuterDivWidth(t('partnerCertificatesList.searchExpiryDate'))}`
    };
    return (
        <>
            <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
                <TextInputComponent
                    fieldName='apiKeyName'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='apiKeysList.apiKeyName'
                    placeHolderKey='apiKeysList.searchApiKeyName'
                    styleSet={getFilterTextFieldStyle()}
                    id='api_key_expiry_api_key_name_filter'
                    inputError={invalidApiKeyName}
                />
                <DropdownComponent
                    fieldName='policyName'
                    dropdownDataList={policyNameDropdownData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='policiesList.policyName'
                    placeHolderKey='policies.selectPolicyName'
                    isPlaceHolderPresent={true}
                    styleSet={getFilterDropdownStyle()}
                    id='api_key_expiry_policy_name_filter'>
                </DropdownComponent>
                <CalendarInput
                    fieldName='expiryDate'
                    label={t('partnerCertificatesList.expiryDate')}
                    showCalendar={isExpiryCalenderOpen}
                    setShowCalender={setIsExpiryCalenderOpen}
                    onChange={handleExpiryDateChange}
                    selectedDateStr={filters.expiryDate}
                    isUsedAsFilter={true}
                    styleSet={calenderStyleSet}
                    placeholderText={t('partnerCertificatesList.searchExpiryDate')}
                    id='api_key_expiry_date_calender'
                />
                <div className={`mt-6 mr-6 ${isLoginLanguageRTL ? "mr-auto" : "ml-auto"}`}>
                    <button
                        id="apply_filter__btn"
                        onClick={() => onApplyFilter(filters)}
                        type="button"
                        disabled={areFiltersEmpty()}
                        className={`h-10 text-sm font-semibold px-7 text-white rounded-md ml-6 
                        ${areFiltersEmpty() ? 'bg-[#A5A5A5] cursor-auto' : 'bg-tory-blue'}`}>
                        {t("partnerList.applyFilter")}
                    </button>
                </div>
            </div>
        </>
    );
}

ApiKeyNotificationFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default ApiKeyNotificationFilter;