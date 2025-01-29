import { useState, useEffect } from "react";
import DropdownComponent from '../../common/fields/DropdownComponent.js';
import DropdownWithSearchComponent from "../../common/fields/DropdownWithSearchComponent.js";
import { useTranslation } from 'react-i18next';
import { createDropdownData } from "../../../utils/AppUtils.js";

function FtmListFilter({ filteredFtmList, onFilterChange }) {
    const { t } = useTranslation();
    const [ftmIdData, setFtmIdData] = useState([]);
    const [partnerIdData, setPartnerIdData] = useState([]);
    const [makeData, setMakeData] = useState([]);
    const [modelData, setModelData] = useState([]);
    const [certificateExpiryData, setCertificateExpiryData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setFtmIdData(createDropdownData('ftmId', '', true, filteredFtmList, t, t('ftmList.selectFtmId')));
            setPartnerIdData(createDropdownData('partnerId', '', true, filteredFtmList, t, t('ftmList.selectPartnerId')));
            setMakeData(createDropdownData('make', '', true, filteredFtmList, t, t('ftmList.selectMakeName')));
            setModelData(createDropdownData('model', '', true, filteredFtmList, t, t('ftmList.selectModelName')));
            setCertificateExpiryData(createDropdownData('certificateExpiryStatus', '', true, filteredFtmList, t, t('ftmList.selectCertificateExpiryStatus')));
            setStatusData(createDropdownData('status', '', true, filteredFtmList, t, t('ftmList.selectStatus')));
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
            <div className="flex w-full p-2.5 justify-start bg-[#F7F7F7] flex-wrap">
                <DropdownComponent
                    fieldName='ftmId'
                    dropdownDataList={ftmIdData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='ftmList.ftmId'
                    placeHolderKey='ftmList.selectFtmId'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='ftm_id_filter'>
                </DropdownComponent>
                <DropdownComponent
                    fieldName='partnerId'
                    dropdownDataList={partnerIdData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='ftmList.partnerId'
                    placeHolderKey='ftmList.selectPartnerId'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='ftm_partner_id_filter'>
                </DropdownComponent>
                <DropdownWithSearchComponent 
                    fieldName='make' 
                    dropdownDataList={makeData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='ftmList.make' 
                    placeHolderKey='ftmList.selectMakeName'
                    searchKey='commons.search'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='ftm_make_name_filter'>
                </DropdownWithSearchComponent>
                <DropdownWithSearchComponent
                    fieldName='model'
                    dropdownDataList={modelData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='ftmList.model'
                    placeHolderKey='ftmList.selectModelName'
                    searchKey='commons.search'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='ftm_model_name_filter'>
                </DropdownWithSearchComponent>
                <DropdownComponent 
                    fieldName='certificateExpiryStatus' 
                    dropdownDataList={certificateExpiryData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='ftmList.certificateExpiryStatus' 
                    placeHolderKey='ftmList.selectCertificateExpiryStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='ftm_certificate_expiry_filter'> 
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='ftmList.status' 
                    placeHolderKey='ftmList.selectStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='ftm_status_filter'> 
                </DropdownComponent>
            </div>
        </>
    )
}

export default FtmListFilter;