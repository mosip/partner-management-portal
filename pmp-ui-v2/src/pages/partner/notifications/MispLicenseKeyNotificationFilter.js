import { useState } from "react";
import { getFilterTextFieldStyle, getOuterDivWidth, isLangRTL, validateInputRegex } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import PropTypes from 'prop-types';
import { useTranslation } from "react-i18next";
import TextInputComponent from "../../common/fields/TextInputComponent";
import CalendarInput from "../../common/CalendarInput";

function MispLicenseKeyNotificationFilter({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [filters, setFilters] = useState({
        mispLicenseKeyName: "",
        mispPartnerId: "",
        expiryDate: ""
    });
    const [invalidMispLicenseKeyName, setInvalidMispLicenseKeyName] = useState("");
    const [invalidMispPartnerId, setInvalidMispPartnerId] = useState("");

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter
        }));
        if (fieldName === 'mispLicenseKeyName') { validateInputRegex(selectedFilter, setInvalidMispLicenseKeyName, t); }
        if (fieldName === 'mispPartnerId') { validateInputRegex(selectedFilter, setInvalidMispPartnerId, t); }
    };

    const handleExpiryDateChange = (newDateStr) => {
        onFilterChangeEvent("expiryDate", newDateStr);
    };

    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidMispLicenseKeyName || invalidMispPartnerId;
    };

    const calenderStyleSet = {
        datePicker: `h-[2.4rem] p-1 ${isLoginLanguageRTL && 'pr-8'}`,
        outerDiv: `ml-4 ${getOuterDivWidth(t('partnerCertificatesList.searchExpiryDate'))}`
    };

    return (
        <>
            <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
                <TextInputComponent
                    fieldName='mispLicenseKeyName'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='mispLicenseList.mispLicenseKeyName'
                    placeHolderKey='mispLicenseList.searchMispLicenseKeyName'
                    styleSet={getFilterTextFieldStyle()}
                    id='misp_expiry_misp_license_key_name_filter'
                    maxLength={128}
                    inputError={invalidMispLicenseKeyName}
                />
                <TextInputComponent
                    fieldName='mispPartnerId'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='mispLicenseList.partnerId'
                    placeHolderKey='partnerList.searchPartnerId'
                    styleSet={getFilterTextFieldStyle()}
                    id='misp_expiry_partner_id_filter'
                    maxLength={36}
                    inputError={invalidMispPartnerId}
                />
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
                    id='misp_expiry_date_calender'
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

MispLicenseKeyNotificationFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default MispLicenseKeyNotificationFilter;