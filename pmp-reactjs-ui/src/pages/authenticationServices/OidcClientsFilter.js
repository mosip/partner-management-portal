import { useState, useEffect } from "react";
import DropdownComponent from '../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownData } from "../../utils/AppUtils.js";

function OidcClientsFilter({ filteredOidcClientsList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [oidcClientNameData, setOidcClientNameData] = useState([]);
    const [policyGroupData, setpolicyGroupData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setPartnerIdData(createDropdownData('partnerId', '', true, filteredOidcClientsList, t, t('oidcClientsList.selectPartnerId')));
            setOidcClientNameData(createDropdownData('oidcClientName', '', true, filteredOidcClientsList, t, t('oidcClientsList.selectOidcClientType')));
            setpolicyGroupData(createDropdownData('policyGroupName', '', true, filteredOidcClientsList, t, t('oidcClientsList.selectPolicyGroup')));
            setPolicyNameData(createDropdownData('policyName', '', true, filteredOidcClientsList, t, t('oidcClientsList.selectPolicyName')));
            setStatusData(createDropdownData('status', '', true, filteredOidcClientsList, t, t('oidcClientsList.selectStatus')));
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
            <div className="flex w-full p-2 justify-start bg-[#F7F7F7] flex-wrap">
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
                <DropdownComponent
                    fieldName='oidcClientName'
                    dropdownDataList={oidcClientNameData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='oidcClientsList.oidcClientName'
                    placeHolderKey='oidcClientsList.selectOidcClientType'
                    styleSet={styles}
                    isPlaceHolderPresent={true}>
                </DropdownComponent>
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

export default OidcClientsFilter;