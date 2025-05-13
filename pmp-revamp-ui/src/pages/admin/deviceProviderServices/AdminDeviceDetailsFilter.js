import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { isLangRTL, createDropdownData, fetchDeviceTypeDropdownData, fetchDeviceSubTypeDropdownData, validateInputRegex, getFilterDropdownStyle, getFilterTextFieldStyle } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService.js';
import PropTypes from 'prop-types';

function AdminDeviceDetailsFilter({ onApplyFilter, setErrorCode, setErrorMsg, removeSbiFields}) {
    const { t } = useTranslation();
    const [status, setStatus] = useState([]);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [deviceTypeDropdownData, setDeviceTypeDropdownData] = useState([]);
    const [deviceSubTypeDropdownData, setDeviceSubTypeDropdownData] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
        { status: 'approved' },
        { status: 'rejected' },
        { status: 'pending_approval' },
        { status: 'deactivated' }
    ]);
    const [filters, setFilters] = useState({
        deviceId: "",
        partnerId: "",
        orgName: "",
        make: "",
        model: "",
        status: "",
        deviceType: "",
        deviceSubType: "",
        sbiId: "",
        sbiVersion: ""
    });
    const [invalidPartnerId, setInvalidPartnerId] = useState("");
    const [invalidOrgName, setInvalidOrgName] = useState("");
    const [invalidSbiId, setInvalidSbiId] = useState("");
    const [invalidSbiVersion, setInvalidSbiVersion] = useState("");
    const [invalidDeviceId, setInvalidDeviceId] = useState("");
    const [invalidMake, setInvalidMake] = useState("");
    const [invalidModel, setInvalidModel] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            const deviceTypeData = await fetchDeviceTypeDropdownData();
            setDeviceTypeDropdownData(createDropdownData("fieldCode", "", true, deviceTypeData, t, t("addDevices.selectDeviceType")));
            setStatus(
                createDropdownData("status", "", true, statusDropdownData, t, t("partnerList.selectStatus"))
            );
        };
        fetchData();
    }, [t]);

    const onFilterChangeEvent = async (fieldName, selectedFilter) => {
        setFilters((prevFilters) => ({
            ...prevFilters,
            [fieldName]: selectedFilter,
        }));
        if (fieldName === 'partnerId') { validateInputRegex(selectedFilter, setInvalidPartnerId, t); }
        if (fieldName === 'orgName') { validateInputRegex(selectedFilter, setInvalidOrgName, t); }
        if (fieldName === 'sbiId') { validateInputRegex(selectedFilter, setInvalidSbiId, t); }
        if (fieldName === 'sbiVersion') { validateInputRegex(selectedFilter, setInvalidSbiVersion, t); }
        if (fieldName === 'deviceId') { validateInputRegex(selectedFilter, setInvalidDeviceId, t); }
        if (fieldName === 'make') { validateInputRegex(selectedFilter, setInvalidMake, t); }
        if (fieldName === 'model') { validateInputRegex(selectedFilter, setInvalidModel, t); }
    
        // Check if fieldName is 'deviceType'
        if (fieldName === 'deviceType') {
            //clear deviceSubType dropdown data
            setDeviceSubTypeDropdownData([]);
            setFilters((prevFilters) => ({
                ...prevFilters,
                deviceSubType: null,
            }));
            console.log(filters);
            // return if no deviceType is selected
            if(selectedFilter === ""){
                setFilters((prevFilters) => ({
                    ...prevFilters,
                    deviceSubType: "",
                }));
                return;
            }
            try {
                // Fetch deviceSubType data 
                const subtypeData = await fetchDeviceSubTypeDropdownData(selectedFilter, setErrorCode, setErrorMsg, t);
    
                setDeviceSubTypeDropdownData(
                    createDropdownData('fieldCode', "", true, subtypeData, t,  t('addDevices.selectDeviceSubType'))
                );
            } catch (error) {
                console.error("Error fetching device subtypes:", error);
            }
        }
    };
    
    const areFiltersEmpty = () => {
        return Object.values(filters).every(value => value === "" || value === null || value === undefined)
            || invalidPartnerId || invalidOrgName || invalidSbiId || invalidSbiVersion || invalidDeviceId
            || invalidMake || invalidModel;
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
            { !removeSbiFields && (
                <>
                    <TextInputComponent
                        fieldName="sbiId"
                        onTextChange={onFilterChangeEvent}
                        fieldNameKey="sbiList.sbiId"
                        placeHolderKey="sbiList.searchSbiId"
                        styleSet={getFilterTextFieldStyle()}
                        id="sbi_id_filter"
                        inputError={invalidSbiId}
                    />
                    <TextInputComponent
                        fieldName="sbiVersion"
                        onTextChange={onFilterChangeEvent}
                        fieldNameKey="sbiList.sbiVersion"
                        placeHolderKey="sbiList.searchVersion"
                        styleSet={getFilterTextFieldStyle()}
                        id="sbi_version_filter"
                        inputError={invalidSbiVersion}
                    />
                </>
            )}
            <TextInputComponent
                fieldName="deviceId"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="devicesList.deviceId"
                placeHolderKey="devicesList.searchDeviceId"
                styleSet={getFilterTextFieldStyle()}
                id="device_id_filter"
                inputError={invalidDeviceId}
            />
            <DropdownComponent
                fieldName='deviceType'
                dropdownDataList={deviceTypeDropdownData}
                onDropDownChangeEvent={onFilterChangeEvent}
                fieldNameKey='addDevices.deviceType'
                placeHolderKey='addDevices.selectDeviceType'
                isPlaceHolderPresent={true}
                styleSet={getFilterDropdownStyle()}
                id='device_type_filter'>
            </DropdownComponent>
            <DropdownComponent
                fieldName='deviceSubType'
                dropdownDataList={deviceSubTypeDropdownData}
                onDropDownChangeEvent={onFilterChangeEvent}
                fieldNameKey='addDevices.deviceSubType'
                placeHolderKey='addDevices.selectDeviceSubType'
                styleSet={getFilterDropdownStyle()}
                disabled={filters.deviceType === ""}
                isPlaceHolderPresent={true}
                id='device_sub_type_filter'>
            </DropdownComponent>
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

AdminDeviceDetailsFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
    setErrorCode: PropTypes.func.isRequired,
    setErrorMsg: PropTypes.func.isRequired,
    removeSbiFields: PropTypes.bool.isRequired,
};

export default AdminDeviceDetailsFilter;