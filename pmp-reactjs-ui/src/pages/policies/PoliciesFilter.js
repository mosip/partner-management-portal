
import { useState, useEffect } from 'react';
import DropdownComponent from '../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownDataList } from '../../utils/AppUtils.js';

function PoliciesFilter({ filteredPoliciesList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [partnerTypeData, setPartnerTypeData] = useState([]);
    const [policyGroupNameData, setPolicyGroupNameData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setPartnerIdData(createDropdownDataList('partnerId', filteredPoliciesList, t));
            setPartnerTypeData(createDropdownDataList('partnerType', filteredPoliciesList, t));
            setPolicyGroupNameData(createDropdownDataList('policyGroup', filteredPoliciesList, t));
            setPolicyNameData(createDropdownDataList('policyName', filteredPoliciesList, t));
            setStatusData(createDropdownDataList('status', filteredPoliciesList, t));
        };
        fetchData();
    }, [filteredPoliciesList, t]);


    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        onFilterChange(fieldName, selectedFilter);
    }

    return (
        <>
            <div className="flex w-full p-2 justify-start bg-gray-50 flex-wrap">
                <DropdownComponent 
                    fieldName='partnerId' 
                    dropdownDataList={partnerIdData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.partnerId' 
                    placeHolderKey='policies.selectPartnerId'>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='partnerType' 
                    dropdownDataList={partnerTypeData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.partnerType' 
                    placeHolderKey='policies.selectPartnerType'>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyGroup' 
                    dropdownDataList={policyGroupNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyGroup' 
                    placeHolderKey='policies.selectPolicyGroup'>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyName' 
                    dropdownDataList={policyNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyName' 
                    placeHolderKey='policies.selectPolicyName'>
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.status' 
                    placeHolderKey='policies.selectStatus'> 
                </DropdownComponent>
            </div>
        </>
    );
}

export default PoliciesFilter;