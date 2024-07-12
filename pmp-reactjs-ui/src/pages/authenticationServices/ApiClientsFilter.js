import { useState, useEffect } from "react";
import DropdownComponent from '../common/fields/DropdownComponent.js';
import DropdownWithSearchComponent from "../common/fields/DropdownWithSearchComponent";
import { useTranslation } from 'react-i18next';
import { createDropdownData } from "../../utils/AppUtils.js";

function ApiClientsFilter({ filteredApiKeysList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [policyGroupData, setpolicyGroupData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [apiKeyLabelData, setApiKeyLabelData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setPartnerIdData(createDropdownData('partnerId', '', true, filteredApiKeysList, t));
            setpolicyGroupData(createDropdownData('policyGroupName', '', true, filteredApiKeysList, t));
            setPolicyNameData(createDropdownData('policyName', '', true, filteredApiKeysList, t));
            setApiKeyLabelData(createDropdownData('apiKeyLabel', '', true, filteredApiKeysList, t));
            setStatusData(createDropdownData('status', '', true, filteredApiKeysList, t));
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
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownComponent
                    fieldName='policyGroupName'
                    dropdownDataList={policyGroupData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='oidcClientsList.policyGroup'
                    placeHolderKey='oidcClientsList.selectPolicyGroup'
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyName' 
                    dropdownDataList={policyNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='oidcClientsList.policyName' 
                    placeHolderKey='oidcClientsList.selectPolicyName'
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownWithSearchComponent
                    fieldName='apiKeyLabel'
                    dropdownDataList={apiKeyLabelData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='apiKeysList.apiKeyLabel'
                    placeHolderKey='apiKeysList.selectApiKeyLabel'
                    searchKey='commons.search'
                    styleSet={styles}>
                </DropdownWithSearchComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='oidcClientsList.status' 
                    placeHolderKey='oidcClientsList.selectStatus'
                    styleSet={styles}> 
                </DropdownComponent>
            </div>
        </>
    )
}
export default ApiClientsFilter;