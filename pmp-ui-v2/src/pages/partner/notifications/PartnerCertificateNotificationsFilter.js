
import { useState, useEffect } from 'react';
import DropdownComponent from '../../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownData, getOuterDivWidth, isLangRTL, validateInputRegex } from '../../../utils/AppUtils.js';
import TextInputComponent from '../../common/fields/TextInputComponent.js';
import { getUserProfile } from '../../../services/UserProfileService.js';
import CalendarInput from '../../common/CalendarInput.js';
import PropTypes from 'prop-types';

function PartnerCertificateNotificationsFilter({ onApplyFilter }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [partnerDomainData, setPartnerDomainData] = useState([]);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [partnerDomainDropdownData, setPartnerDomainDropdownData] = useState([
        { partnerDomain: 'AUTH' },
        { partnerDomain: 'DEVICE' },
        { partnerDomain: 'FTM' }
    ]);
    const [filters, setFilters] = useState({
        partnerDomain: "",
        issuedTo: "",
        issuedBy: "",
        expiryDate: ""
    });
    const [invalidIssuedBy, setInvalidIssuedBy] = useState("");
    const [invalidIssuedTo, setInvalidIssuedTo] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            setPartnerDomainData(
                createDropdownData("partnerDomain", "", true, partnerDomainDropdownData, t, t("viewAllNotifications.selectPartnerDomain"))
            );
        };
        fetchData();
    }, [t]);

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter
        }));
        if (fieldName === 'issuedBy') { validateInputRegex(selectedFilter, setInvalidIssuedBy, t); }
        if (fieldName === 'issuedTo') { validateInputRegex(selectedFilter, setInvalidIssuedTo, t); }
    };

    const handleExpiryDateChange = (newDateStr) => {
        onFilterChangeEvent("expiryDate", newDateStr);
    };
      
    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidIssuedBy || invalidIssuedTo;
    };

    const styles = {
        dropdownButton: "min-w-64",
    };

    const styleSet = {
        inputField: "min-w-64",
        inputLabel: "mb-2",
        outerDiv: "ml-4"
    };

    const calenderStyleSet = {
        datePicker: "h-9 p-1",
        outerDiv: `ml-4 ${getOuterDivWidth(t('viewAllNotifications.selectExpiryDate'))}`
    };

    return (
        <>
            <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
                <TextInputComponent
                    fieldName='issuedBy'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='viewAllNotifications.issuedBy'
                    placeHolderKey='viewAllNotifications.searchIssuedBy'
                    styleSet={styleSet}
                    id='cert_issued_by_domain_filter'
                    inputError={invalidIssuedBy}
                />
                <TextInputComponent
                    fieldName='issuedTo'
                    onTextChange={onFilterChangeEvent}
                    fieldNameKey='viewAllNotifications.issuedTo'
                    placeHolderKey='viewAllNotifications.searchIssuedTo'
                    styleSet={styleSet}
                    id='cert_issued_to_filter'
                    inputError={invalidIssuedTo}
                />
                <DropdownComponent
                    fieldName="partnerDomain"
                    dropdownDataList={partnerDomainData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey="viewAllNotifications.partnerDomain"
                    placeHolderKey="viewAllNotifications.selectPartnerDomain"
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id="cert_partner_domain_filter"
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
                    id='view_notifications_expiry_date_calender'
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

PartnerCertificateNotificationsFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default PartnerCertificateNotificationsFilter;