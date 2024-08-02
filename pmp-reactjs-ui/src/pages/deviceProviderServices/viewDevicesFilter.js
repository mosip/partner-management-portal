import { useState, useEffect } from "react";
import DropdownComponent from '../common/fields/DropdownComponent.js';
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent.js";
import { useTranslation } from 'react-i18next';
import { createDropdownData } from "../../utils/AppUtils.js";

function ViewDevicesFilter({ filteredDevicesList, onFilterChange }) {
    const { t } = useTranslation();
    const [deviceTypeData, setDeviceTypeData] = useState([]);
    const [deviceSubTypeData, setDeviceSubTypeData] = useState([]);
    const [makeData, setMakeData] = useState([]);
    const [modelData, setModelData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setDeviceTypeData(createDropdownData('deviceTypeCode', '', true, filteredDevicesList, t, t('viewDevices.selectDeviceType')));
            setDeviceSubTypeData(createDropdownData('deviceSubTypeCode', '', true, filteredDevicesList, t, t('viewDevices.selectDeviceSubType')));
            setMakeData(createDropdownData('make', '', true, filteredDevicesList, t, t('viewDevices.selectMakeName')));
            setModelData(createDropdownData('model', '', true, filteredDevicesList, t, t('viewDevices.selectModelName')));
            setStatusData(createDropdownData('status', '', true, filteredDevicesList, t, t('viewDevices.selectStatus')));
        };
        fetchData();
    }, []);

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        onFilterChange(fieldName, selectedFilter);
    }

    const styles = {
        dropdownButton: "!text-[#343434] min-w-72"
    }

    return (
        <>
            <div className="flex w-full p-2 justify-start bg-gray-50 flex-wrap">
                <DropdownComponent
                    fieldName='deviceType'
                    dropdownDataList={deviceTypeData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='viewDevices.deviceType'
                    placeHolderKey='viewDevices.selectDeviceType'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownComponent>
                <DropdownComponent
                    fieldName='deviceSubType'
                    dropdownDataList={deviceSubTypeData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='viewDevices.deviceSubType'
                    placeHolderKey='viewDevices.selectDeviceSubType'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownComponent>
                <DropdownWithSearchComponent 
                    fieldName='make' 
                    dropdownDataList={makeData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='viewDevices.make' 
                    placeHolderKey='viewDevices.selectMakeName'
                    searchKey='commons.search'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownWithSearchComponent>
                <DropdownWithSearchComponent
                    fieldName='model'
                    dropdownDataList={modelData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='viewDevices.model'
                    placeHolderKey='viewDevices.selectModelName'
                    searchKey='commons.search'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownWithSearchComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='viewDevices.status' 
                    placeHolderKey='viewDevices.selectStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}> 
                </DropdownComponent>
            </div>
        </>
    )
}

export default ViewDevicesFilter;