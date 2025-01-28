import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent";
import TextInputComponent from "../../common/fields/TextInputComponent";
import { useTranslation } from "react-i18next";
import { getUserProfile } from "../../../services/UserProfileService";
import { isLangRTL, createDropdownData } from "../../../utils/AppUtils";

function AdminSbiListFilter( {onApplyFilter} ) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [status, setStatus] = useState([]);
    const [sbiExpiryStatus, setSbiExpiryStatus] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
        { status: 'approved' },
        { status: 'rejected'},
        { status: 'pending_approval'},
        { status: 'deactivated'}
    ]);
    const [sbiExpiryStatusDropdownData, setSbiExpiryStatusDropdownData] = useState([
        { sbiExpiryStatus: 'expired' },
        { sbiExpiryStatus: 'valid'}
      ]);
    const [filters, setFilters] = useState({
      partnerId: "",
      orgName: "",
      sbiId: "",
      sbiVersion: "",
      status: "",
      sbiExpiryStatus: "",
    });

    useEffect(() => {
        const fetchData = async () => {
            setStatus(createDropdownData("status", "", true, statusDropdownData, t, t("partnerList.selectStatus")));
            setSbiExpiryStatus(createDropdownData("sbiExpiryStatus", "", true, sbiExpiryStatusDropdownData, t, t("sbiList.selectSbiExpiryStatus")));
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
        <div className="flex w-full p-2.5 justify-start bg-[#F7F7F7] flex-wrap">
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
                fieldName="sbiId"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="sbiList.sbiId"
                placeHolderKey="sbiList.searchSbiId"
                styleSet={styleSet}
                id="sbi_id_filter"
            />
            <TextInputComponent
                fieldName="sbiVersion"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="sbiList.sbiVersion"
                placeHolderKey="sbiList.searchVersion"
                styleSet={styleSet}
                id="sbi_version_filter"
            />
            <DropdownComponent
                fieldName="sbiExpiryStatus"
                dropdownDataList={sbiExpiryStatus}
                onDropDownChangeEvent={onFilterChangeEvent}
                fieldNameKey="sbiList.sbiExpiryStatus"
                placeHolderKey="sbiList.selectSbiExpiryStatus"
                styleSet={styles}
                isPlaceHolderPresent={true}
                id="sbi_expiry_status_filter"
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
export default AdminSbiListFilter;