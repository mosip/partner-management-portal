import { useState } from "react";
import { getFilterTextFieldStyle, getOuterDivWidth, isLangRTL, validateInputRegex } from "../../../utils/AppUtils";
import { getUserProfile } from "../../../services/UserProfileService";
import PropTypes from 'prop-types';
import { useTranslation } from "react-i18next";
import TextInputComponent from "../../common/fields/TextInputComponent";
import CalendarInput from "../../common/CalendarInput";

function SbiNotificationFilter({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [filters, setFilters] = useState({
        sbiId: "",
        sbiVersion: "",
        expiryDate: ""
    });
    const [invalidSbiId, setInvalidSbiId] = useState("");
    const [invalidSbiVersion, setInvalidSbiVersion] = useState("");

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter
        }));
        if (fieldName === 'sbiId') { validateInputRegex(selectedFilter, setInvalidSbiId, t); }
        if (fieldName === 'sbiVersion') { validateInputRegex(selectedFilter, setInvalidSbiVersion, t); }
    };

    const handleExpiryDateChange = (newDateStr) => {
        onFilterChangeEvent("expiryDate", newDateStr);
    };
        
    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidSbiId || invalidSbiVersion;
    };

    const calenderStyleSet = {
        datePicker: `h-[2.4rem] p-1 ${isLoginLanguageRTL && 'pr-8'}`,
        outerDiv: `ml-4 ${getOuterDivWidth(t('partnerCertificatesList.searchExpiryDate'))}`
    };

    return (
        <>
            <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
                <TextInputComponent
                    fieldName='sbiId'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='sbiList.sbiId'
                    placeHolderKey='sbiList.searchSbiId'
                    styleSet={getFilterTextFieldStyle()}
                    id='sbi_expiry_sbi_id_filter'
                    inputError={invalidSbiId}
                />
                <TextInputComponent
                    fieldName='sbiVersion'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='sbiList.sbiVersion'
                    placeHolderKey='sbiList.searchVersion'
                    styleSet={getFilterTextFieldStyle()}
                    id='sbi_expiry_sbi_version_filter'
                    inputError={invalidSbiVersion}
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
                    id='sbi_expiry_date_calender'
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

SbiNotificationFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default SbiNotificationFilter;