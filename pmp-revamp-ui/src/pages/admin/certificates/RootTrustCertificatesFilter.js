
import { useState, useEffect } from 'react';
import DropdownComponent from '../../common/fields/DropdownComponent.js';
import { useTranslation } from 'react-i18next';
import { createDropdownData, isLangRTL } from '../../../utils/AppUtils.js';
import TextInputComponent from '../../common/fields/TextInputComponent.js';
import { getUserProfile } from '../../../services/UserProfileService.js';

function RootTrustCertificatesFilter({ onFilterChange }) {
    const { t } = useTranslation();
    const [certificateIdData, setCertificateIdData] = useState([]);
    const [partnerDomainData, setPartnerDomainData] = useState([]);
    const [statusData, setStatusData] = useState([]);
    const [statusDropdownData, setStatusDropdownData] = useState([
        { status: 'active' },
        { status: 'deactivated' }
    ]);
    const [filters, setFilters] = useState({
        certificateId: "",
        partnerDomain: "",
        issuedTo: "",
        issuedBy: "",
        status: "",
    });

    useEffect(() => {
        const fetchData = async () => {
            setStatusData(
                createDropdownData("status", "", true, statusDropdownData, t, t("partnerList.selectStatus"))
            );
        };
        fetchData();
    }, [t]);

    const onFilterChangeEvent = (fieldName, selectedFilter) => {
        onFilterChange(fieldName, selectedFilter);
    }

    const styles = {
        dropdownButton: "min-w-64",
    };

    const styleSet = {
        inputField: "min-w-64",
        inputLabel: "mb-2",
        outerDiv: "ml-4"
    };

    return (
        <>
            <div className="flex w-full p-2 justify-start bg-[#F7F7F7] flex-wrap">
                <DropdownComponent
                    fieldName='certificateId'
                    dropdownDataList={certificateIdData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='rootTrustCertificate.certificateId'
                    placeHolderKey='rootTrustCertificate.selectCertificateId'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='root_cert_id_filter'
                />
                <DropdownComponent
                    fieldName='partnerDomain'
                    dropdownDataList={partnerDomainData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='rootTrustCertificate.partnerDomain'
                    placeHolderKey='rootTrustCertificate.selectPartnerDomain'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='root_cert_partner_domain_filter'
                />
                <TextInputComponent
                    fieldName='issuedTo'
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='rootTrustCertificate.issuedTo'
                    placeHolderKey='rootTrustCertificate.selectIssuedTo'
                    styleSet={styleSet}
                    id='root_cert_issued_to_filter'
                />
                <TextInputComponent
                    fieldName='issuedBy'
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='rootTrustCertificate.issuedBy'
                    placeHolderKey='rootTrustCertificate.selectIssuedBy'
                    styleSet={styleSet}
                    id='root_cert_issued_by_domain_filter'
                />
                <DropdownComponent
                    fieldName='status'
                    dropdownDataList={statusData}
                    onDropDownChangeEvent={onFilterChangeEvent}
                    fieldNameKey='rootTrustCertificate.status'
                    placeHolderKey='rootTrustCertificate.selectStatus'
                    styleSet={styles}
                    isPlaceHolderPresent={true}
                    id='root_cert_status_filter'
                />
            </div>
        </>
    );
}

export default RootTrustCertificatesFilter;