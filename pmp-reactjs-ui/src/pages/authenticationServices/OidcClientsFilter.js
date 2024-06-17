import { useState, useEffect } from "react";
import DropdownComponent from '../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { getStatusCode } from "../../utils/AppUtils.js";

function OidcClientsFilter({ filteredOidcClientsList, onFilterChange }) {
    const { t } = useTranslation();
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [oidcClientNameData, setOidcClientNameData] = useState([]);
    const [policyGroupData, setpolicyGroupData] = useState([]);
    const [policyNameData, setPolicyNameData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const getData = (fieldName) => {
            let dataArr = [];
            dataArr.push({
                fieldCode: "",
                fieldValue: ""
            });
            filteredOidcClientsList.forEach(item => {
                let alreadyAdded = false;
                dataArr.forEach(item1 => {
                    if (item1.fieldValue === item[fieldName]) {
                        alreadyAdded = true;
                    }
                });
                if (!alreadyAdded) {
                    if (fieldName === "status") {
                        dataArr.push({
                            fieldCode: getStatusCode(item[fieldName], t),
                            fieldValue: item[fieldName]
                        });
                    } else {
                        dataArr.push({
                            fieldCode: item[fieldName],
                            fieldValue: item[fieldName]
                        });
                    }
                }
            });
            return dataArr;
        }
        const fetchData = async () => {
            setPartnerIdData(getData('partnerId'));
            setOidcClientNameData(getData('oidcClientName'));
            setpolicyGroupData(getData('policyGroupName'));
            setPolicyNameData(getData('policyName'));
            setStatusData(getData('status'));
        };
        fetchData();
    }, [t]);

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        onFilterChange(fieldName, selectedFilter);
    }

    const styles = {
        dropdownButton: "!text-[#343434]"
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
                <DropdownComponent
                    fieldName='oidcClientName'
                    dropdownDataList={oidcClientNameData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='oidcClientsList.oidcClientName'
                    placeHolderKey='oidcClientsList.selectOidcClientType'
                    styleSet={styles}>
                </DropdownComponent>
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

export default OidcClientsFilter;