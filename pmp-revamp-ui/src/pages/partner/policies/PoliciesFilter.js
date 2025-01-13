
import { useState, useEffect } from 'react';
import DropdownComponent from '../../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownData } from '../../../utils/AppUtils.js';

function PoliciesFilter({ filteredPoliciesList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [partnerTypeData, setPartnerTypeData] = useState([]);
    const [policyGroupNameData, setPolicyGroupNameData] = useState([]);
    const [policyIdData, setPolicyIdData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setPartnerIdData(createDropdownData('partnerId', '', true, filteredPoliciesList, t, t('policies.selectPartnerId')));
            setPartnerTypeData(createDropdownData('partnerType', '', true, filteredPoliciesList, t, t('policies.selectPartnerType')));
            setPolicyGroupNameData(createDropdownData('policyGroupName', '', true, filteredPoliciesList, t, t('policies.selectPolicyGroup')));
            setPolicyIdData(createDropdownData('policyId', '', true, filteredPoliciesList, t, t('policies.selectPolicyId')));
            setPolicyNameData(createDropdownData('policyName', '', true, filteredPoliciesList, t, t('policies.selectPolicyName')));
            setStatusData(createDropdownData('status', '', true, filteredPoliciesList, t, t('policies.selectStatus')));
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
                    fieldNameKey='policies.partnerId' 
                    placeHolderKey='policies.selectPartnerId'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='policy_partner_id_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='partnerType' 
                    dropdownDataList={partnerTypeData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.partnerType' 
                    placeHolderKey='policies.selectPartnerType'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='policy_partner_type_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyGroupName' 
                    dropdownDataList={policyGroupNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyGroupName' 
                    placeHolderKey='policies.selectPolicyGroup'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='policy_group_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyId' 
                    dropdownDataList={policyIdData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyId' 
                    placeHolderKey='policies.selectPolicyId'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='policy_id_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='policyName' 
                    dropdownDataList={policyNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.policyName' 
                    placeHolderKey='policies.selectPolicyName'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='policy_name_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='policies.status' 
                    placeHolderKey='policies.selectStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='policy_status_filter'
                    > 
                </DropdownComponent>
            </div>
        </>
    );
}

export default PoliciesFilter;