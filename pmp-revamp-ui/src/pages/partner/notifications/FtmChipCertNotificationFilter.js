import { useTranslation } from "react-i18next";
import { getFilterTextFieldStyle, getOuterDivWidth, isLangRTL, validateInputRegex } from "../../../utils/AppUtils";
import { useState } from "react";
import TextInputComponent from "../../common/fields/TextInputComponent";
import CalendarInput from "../../common/CalendarInput";
import PropTypes from 'prop-types';
import { getUserProfile } from "../../../services/UserProfileService";

function FtmChipCertNotificationFilter( { onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [filters, setFilters] = useState({
        ftmId: "",
        make: "",
        model: "",
        expiryDate: ""
    });
    const [invalidFtmId, setInvalidFtmId] = useState("");
    const [invalidMake, setInvalidMake] = useState("");
    const [invalidModel, setInvalidModel] = useState("");

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter
        }));
        if (fieldName === 'ftmId') { validateInputRegex(selectedFilter, setInvalidFtmId, t); }
        if (fieldName === 'make') { validateInputRegex(selectedFilter, setInvalidMake, t); }
        if (fieldName === 'model') { validateInputRegex(selectedFilter, setInvalidModel, t); }
    };

    const handleExpiryDateChange = (newDateStr) => {
        onFilterChangeEvent("expiryDate", newDateStr);
    };
        
    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidFtmId || invalidMake || invalidModel;
    };

    const calenderStyleSet = {
        datePicker: "h-[2.4rem] p-1",
        outerDiv: `ml-4 ${getOuterDivWidth(t('viewAllNotifications.selectExpiryDate'))}`
    };

    return (
        <>
            <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
                <TextInputComponent
                    fieldName='ftmId'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='ftmList.ftmId'
                    placeHolderKey='ftmList.searchFtmId'
                    styleSet={getFilterTextFieldStyle()}
                    id='ftm_chip_cert_expiry_ftm_id_filter'
                    inputError={invalidFtmId}
                />
                <TextInputComponent
                    fieldName='make'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='ftmList.make'
                    placeHolderKey='ftmList.searchMake'
                    styleSet={getFilterTextFieldStyle()}
                    id='ftm_chip_cert_expiry_make_filter'
                    inputError={invalidMake}
                />
                <TextInputComponent
                    fieldName='model'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='ftmList.model'
                    placeHolderKey='ftmList.searchModel'
                    styleSet={getFilterTextFieldStyle()}
                    id='ftm_chip_cert_expiry_model_filter'
                    inputError={invalidModel}
                />
                <CalendarInput
                    fieldName='expiryDate'
                    label={t('viewAllNotifications.expiryDate')}
                    showCalendar={isExpiryCalenderOpen}
                    setShowCalender={setIsExpiryCalenderOpen}
                    onChange={handleExpiryDateChange}
                    selectedDateStr={filters.expiryDate}
                    isUsedAsFilter={true}
                    styleSet={calenderStyleSet}
                    placeholderText={t('viewAllNotifications.selectExpiryDate')}
                    id='ftm_chip_cert_expiry_date_calender'
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

FtmChipCertNotificationFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default FtmChipCertNotificationFilter;