import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { createDropdownData, getFilterDropdownStyle, getFilterTextFieldStyle, isLangRTL, validateInputRegex } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService';
import PropTypes from 'prop-types';

function AdminFtmListFilter ({ onApplyFilter }) {
    const { t } = useTranslation();
    const [status, setStatus] = useState([]);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [statusDropdownData, setStatusDropdownData] = useState([
        { status: 'approved' },
        { status: 'rejected' },
        { status: 'pending_approval' },
        { status: 'pending_cert_upload' },
        { status: 'deactivated' }
    ]);
    const [filters, setFilters] = useState({
        partnerId: "",
        orgName: "",
        ftmId: "",
        make: "",
        model: "",
        status: ""
    });
    const [invalidPartnerId, setInvalidPartnerId] = useState("");
    const [invalidOrgName, setInvalidOrgName] = useState("");
    const [invalidFtmId, setInvalidFtmId] = useState("");
    const [invalidMake, setInvalidMake] = useState("");
    const [invalidModel, setInvalidModel] = useState("");

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
        if (fieldName === 'ftmId') { validateInputRegex(selectedFilter, setInvalidFtmId, t); }
        if (fieldName === 'make') { validateInputRegex(selectedFilter, setInvalidMake, t); }
        if (fieldName === 'model') { validateInputRegex(selectedFilter, setInvalidModel, t); }
    };

    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "") || invalidPartnerId 
        || invalidOrgName || invalidFtmId || invalidMake || invalidModel;
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
                fieldName="ftmId"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="ftmList.ftmId"
                placeHolderKey="ftmList.searchFtmId"
                styleSet={getFilterTextFieldStyle()}
                id="ftm_id_filter"
                inputError={invalidFtmId}
            />
            <TextInputComponent
                fieldName="make"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="ftmList.make"
                placeHolderKey="ftmList.searchMake"
                styleSet={getFilterTextFieldStyle()}
                id="make_filter"
                inputError={invalidMake}
            />
            <TextInputComponent
                fieldName="model"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="ftmList.model"
                placeHolderKey="ftmList.searchModel"
                styleSet={getFilterTextFieldStyle()}
                id="model_filter"
                inputError={invalidModel}
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

AdminFtmListFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default AdminFtmListFilter;