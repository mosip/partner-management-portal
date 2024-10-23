
import { useState, useEffect } from 'react';
import DropdownComponent from '../../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownData } from '../../../utils/AppUtils.js';

function RootTrustCertificatesFilter({ filteredCertificateData, onFilterChange }) {
    const { t } = useTranslation();
    const [organisationNameData, setOrganisationNameData] = useState([]);
    const [partnerDomainData, setPartnerDomainData] = useState([]);
    const [statusData, setStatusData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            setOrganisationNameData(createDropdownData('orgName', '', true, filteredCertificateData, t, t('rootTrustCertificate.selectOrganisation')));
            setPartnerDomainData(createDropdownData('partnerDomain', '', true, filteredCertificateData, t, t('rootTrustCertificate.selectPartnerDomain')));
            setStatusData(createDropdownData('status', '', true, filteredCertificateData, t, t('rootTrustCertificate.selectStatus')));
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
                    fieldName='orgName' 
                    dropdownDataList={organisationNameData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='rootTrustCertificate.organisation' 
                    placeHolderKey='rootTrustCertificate.selectOrganisation'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='root_cert_organisation_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='partnerDomain' 
                    dropdownDataList={partnerDomainData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='rootTrustCertificate.partnerDomain' 
                    placeHolderKey='rootTrustCertificate.selectPartnerDomain'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='root_cert_partner_domain_filter'
                    >
                </DropdownComponent>
                <DropdownComponent 
                    fieldName='status' 
                    dropdownDataList={statusData} 
                    onDropDownChangeEvent={onFilterChangeEvent} 
                    fieldNameKey='rootTrustCertificate.status' 
                    placeHolderKey='rootTrustCertificate.selectStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='root_cert_status_filter'
                    > 
                </DropdownComponent>
            </div>
        </>
    );
}

export default RootTrustCertificatesFilter;