import { useState, useEffect } from "react";
import DropdownComponent from '../common/fields/DropdownComponent.js';
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent.js";
import { useTranslation } from 'react-i18next';
import { createDropdownData } from "../../utils/AppUtils.js";

function ApiKeysFilter({ filteredApiKeysList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [policyGroupData, setpolicyGroupData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [apiKeyLabelData, setApiKeyLabelData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setPartnerIdData(createDropdownData('partnerId', '', true, filteredApiKeysList, t, t('oidcClientsList.selectPartnerId')));
            setpolicyGroupData(createDropdownData('policyGroupName', '', true, filteredApiKeysList, t, t('oidcClientsList.selectPolicyGroup')));
            setPolicyNameData(createDropdownData('policyName', '', true, filteredApiKeysList, t, t('oidcClientsList.selectPolicyName')));
            setApiKeyLabelData(createDropdownData('apiKeyLabel', '', true, filteredApiKeysList, t, t('apiKeysList.selectApiKeyLabel')));
            setStatusData(createDropdownData('status', '', true, filteredApiKeysList, t, t('oidcClientsList.selectStatus')));
        };
        fetchData();
    }, [t]);

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
                    fieldName='partnerId'
                    dropdownDataList={partnerIdData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='oidcClientsList.partnerId'
                    placeHolderKey='oidcClientsList.selectPartnerId'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownComponent>
                <DropdownComponent
                    fieldName='policyGroupName'
                    dropdownDataList={policyGroupData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='oidcClientsList.policyGroup'
                    placeHolderKey='oidcClientsList.selectPolicyGroup'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyName' 
                    dropdownDataList={policyNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='oidcClientsList.policyName' 
                    placeHolderKey='oidcClientsList.selectPolicyName'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownComponent>
                <DropdownWithSearchComponent
                    fieldName='apiKeyLabel'
                    dropdownDataList={apiKeyLabelData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='apiKeysList.apiKeyLabel'
                    placeHolderKey='apiKeysList.selectApiKeyLabel'
                    searchKey='commons.search'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownWithSearchComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='oidcClientsList.status' 
                    placeHolderKey='oidcClientsList.selectStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}> 
                </DropdownComponent>
            </div>
        </>
    )
}
export default ApiKeysFilter;