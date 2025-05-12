import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { getOuterDivWidth, isLangRTL } from "../../../utils/AppUtils";
import { useState } from "react";
import CalendarInput from "../../common/CalendarInput";
import PropTypes from 'prop-types';
function WeeklyNotificationsFilter({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [filters, setFilters] = useState({
        createdFromDate: "",
        createdToDate: ""
    });

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter
        }));
    };

    const handleExpiryDateChange = (fieldName, newDateStr) => {
        onFilterChangeEvent(fieldName, newDateStr);
    };
      
    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "");
    };
    
    const createdFromDateStyleSet = {
        datePicker: "h-9 p-1",
        outerDiv: getOuterDivWidth(t('viewAllNotifications.selectCreatedFromDate'))
    };

    const createdToDateStyleSet = {
        datePicker: "h-9 p-1",
        outerDiv: getOuterDivWidth(t('viewAllNotifications.selectCreatedToDate'))
    };  

    return (
        <>
            <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
                <div className={`${isLoginLanguageRTL ? 'mr-4' : 'ml-4'}`}>
                    <CalendarInput
                        fieldName='createdFromDate'
                        label={t('viewAllNotifications.createdFromDate')}
                        showCalendar={isExpiryCalenderOpen}
                        setShowCalender={setIsExpiryCalenderOpen}
                        onChange={(newDateStr) => handleExpiryDateChange('createdFromDate', newDateStr)}
                        selectedDateStr={filters.createdFromDate }
                        isUsedAsFilter={true}
                        styleSet={createdFromDateStyleSet}
                        placeholderText={t('viewAllNotifications.selectCreatedFromDate')}
                        id='created_from_date_calender'
                    />
                </div>
                <div className={`${isLoginLanguageRTL ? 'mr-4' : 'ml-4'}`}>
                    <CalendarInput
                        fieldName='createdToDate'
                        label={t('viewAllNotifications.createdToDate')}
                        showCalendar={isExpiryCalenderOpen}
                        setShowCalender={setIsExpiryCalenderOpen}
                        onChange={(newDateStr) => handleExpiryDateChange('createdToDate', newDateStr)}
                        selectedDateStr={filters.createdToDate}
                        isUsedAsFilter={true}
                        styleSet={createdToDateStyleSet}
                        placeholderText={t('viewAllNotifications.selectCreatedToDate')}
                        id='created_to_date_calender'
                    />
                </div>
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
        </>
    );
}

WeeklyNotificationsFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default WeeklyNotificationsFilter;