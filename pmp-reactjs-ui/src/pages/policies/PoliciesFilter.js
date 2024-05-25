
import { useState, useEffect } from 'react';
import DropdownComponent from '../common/fields/DropdownComponent.js';

function PoliciesFilter({ filteredPoliciesList, onFilterChange }) {
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [partnerTypeData, setPartnerTypeData] = useState([]);
    const [policyGroupData, setPolicyGroupData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const getData = (fieldName) => {
            let dataArr = [];
            dataArr.push({
                fieldCode: "",
                fieldValue: ""
            });
            filteredPoliciesList.forEach(item => {
                let alreadyAdded = false;
                dataArr.forEach(item1 => {
                    if (item1.fieldValue === item[fieldName]) {
                        alreadyAdded = true;
                    }
                });
                if (!alreadyAdded) {
                    dataArr.push({
                        fieldCode: item[fieldName],
                        fieldValue: item[fieldName]
                    });
                }
            });
            return dataArr;
        }
        const fetchData = async () => {
            setPartnerIdData(getData('partnerId'));
            setPartnerTypeData(getData('partnerType'));
            setPolicyGroupData(getData('policyGroup'));
            setPolicyNameData(getData('policyName'));
            setStatusData(getData('status'));
        };
        fetchData();
    }, [filteredPoliciesList]);


    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        onFilterChange(fieldName, selectedFilter);
    }

    return (
        <>
            <div className="flex w-full p-2 justify-between">
                <DropdownComponent fieldName='partnerId' dropdownDataList={partnerIdData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.partnerId'></DropdownComponent>
                <DropdownComponent fieldName='partnerType' dropdownDataList={partnerTypeData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.partnerType'></DropdownComponent>
                <DropdownComponent fieldName='policyGroup' dropdownDataList={policyGroupData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.policyGroup'></DropdownComponent>
                <DropdownComponent fieldName='policyName' dropdownDataList={policyNameData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.policyName'></DropdownComponent>
                <DropdownComponent fieldName='status' dropdownDataList={statusData} onDropDownChangeEvent={onFilterChangeEvent} fieldNameKey='policies.status'></DropdownComponent>
            </div>
        </>
    );
}

export default PoliciesFilter;