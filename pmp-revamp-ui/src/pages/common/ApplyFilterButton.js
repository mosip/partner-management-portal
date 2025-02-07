import React from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL } from '../../utils/AppUtils';

function ApplyFilterButton({ filters, onApplyFilter, areFiltersEmpty }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <div className={`mt-6 mr-6 ${isLoginLanguageRTL ? "mr-auto" : "ml-auto"}`}>
            <button
                id="apply_filter_btn"
                type="button"
                disabled={areFiltersEmpty()}
                onClick={() => onApplyFilter(filters)}
                className={`h-10 text-sm font-semibold px-7 text-white rounded-md ml-6 ${areFiltersEmpty() ? 'bg-[#A5A5A5] cursor-auto' : 'bg-tory-blue'}`}
            >
                {t("partnerList.applyFilter")}
            </button>
        </div>
    )
}

export default ApplyFilterButton;