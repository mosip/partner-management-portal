import { useState, useEffect } from "react";
import DropdownComponent from "../../common/fields/DropdownComponent.js";
import TextInputComponent from "../../common/fields/TextInputComponent.js";
import { useTranslation } from "react-i18next";
import { isLangRTL, createDropdownData, fetchDeviceTypeDropdownData, fetchDeviceSubTypeDropdownData } from "../../../utils/AppUtils.js";
import { getUserProfile } from '../../../services/UserProfileService.js';

function AdminDeviceDetailsFilter({ onApplyFilter, setErrorCode, setErrorMsg, removeSbiFields}) {
    const { t } = useTranslation();
    const [status, setStatus] = useState([]);
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
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
        return Object.values(filters).every(value => value === "" || value === null || value === undefined);
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
        <div className="flex w-full p-3 justify-start bg-[#F7F7F7] flex-wrap">
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
            { !removeSbiFields && (
                <>
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
                </>
            )}
            <TextInputComponent
                fieldName="deviceId"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="devicesList.deviceId"
                placeHolderKey="devicesList.searchDeviceId"
                styleSet={styleSet}
                id="device_id_filter"
            />
            <DropdownComponent
                fieldName='deviceType'
                dropdownDataList={deviceTypeDropdownData}
                onDropDownChangeEvent={onFilterChangeEvent}
                fieldNameKey='addDevices.deviceType'
                placeHolderKey='addDevices.selectDeviceType'
                isPlaceHolderPresent={true}
                styleSet={styles}
                id='device_type_filter'>
            </DropdownComponent>
            <DropdownComponent
                fieldName='deviceSubType'
                dropdownDataList={deviceSubTypeDropdownData}
                onDropDownChangeEvent={onFilterChangeEvent}
                fieldNameKey='addDevices.deviceSubType'
                placeHolderKey='addDevices.selectDeviceSubType'
                styleSet={styles}
                disabled={filters.deviceType === ""}
                isPlaceHolderPresent={true}
                id='device_sub_type_filter'>
            </DropdownComponent>
            <TextInputComponent
                fieldName="make"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="ftmList.make"
                placeHolderKey="ftmList.searchMake"
                styleSet={styleSet}
                id="make_filter"
            />
            <TextInputComponent
                fieldName="model"
                onTextChange={onFilterChangeEvent}
                fieldNameKey="ftmList.model"
                placeHolderKey="ftmList.searchModel"
                styleSet={styleSet}
                id="model_filter"
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
export default AdminDeviceDetailsFilter;