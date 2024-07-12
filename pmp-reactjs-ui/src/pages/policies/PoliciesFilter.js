
import { useState, useEffect } from 'react';
import DropdownComponent from '../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownData } from '../../utils/AppUtils.js';

function PoliciesFilter({ filteredPoliciesList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [partnerTypeData, setPartnerTypeData] = useState([]);
    const [policyGroupNameData, setPolicyGroupNameData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setPartnerIdData(createDropdownData('partnerId', '', true, filteredPoliciesList, t));
            setPartnerTypeData(createDropdownData('partnerType', '', true, filteredPoliciesList, t));
            setPolicyGroupNameData(createDropdownData('policyGroupName', '', true, filteredPoliciesList, t));
            setPolicyNameData(createDropdownData('policyName', '', true, filteredPoliciesList, t));
            setStatusData(createDropdownData('status', '', true, filteredPoliciesList, t));
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
                    fieldNameKey='policies.partnerId' 
                    placeHolderKey='policies.selectPartnerId'
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='partnerType' 
                    dropdownDataList={partnerTypeData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.partnerType' 
                    placeHolderKey='policies.selectPartnerType'
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyGroupName' 
                    dropdownDataList={policyGroupNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyGroupName' 
                    placeHolderKey='policies.selectPolicyGroup'
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyName' 
                    dropdownDataList={policyNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyName' 
                    placeHolderKey='policies.selectPolicyName'
                    styleSet={styles}>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.status' 
                    placeHolderKey='policies.selectStatus'
                    styleSet={styles}> 
                </DropdownComponent>
            </div>
        </>
    );
}

export default PoliciesFilter;