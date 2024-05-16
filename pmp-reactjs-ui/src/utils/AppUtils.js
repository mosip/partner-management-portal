export const formatDate = (dateString, format) => {
    if (!dateString) return '-';
    const date = new Date(dateString);
    if (format === 'dateTime') {
        return date.toLocaleString();
    } else if (format === 'date') {
        return date.toLocaleDateString();
    } else {
        return '-';
    }
};

export const getPartnerTypeDescription = (partnerType, t) => {
    if (partnerType) {
        if (partnerType === "Device_Provider") {
            return t('partnerTypes.deviceProvider');
        }
        else if (partnerType === "FTM_Provider") {
            return t('partnerTypes.ftmProvider');
        }
        else if (partnerType === "Auth_Partner") {
            return t('partnerTypes.authPartner');
        }
        else if (partnerType === "Credential_Partner") {
            return t('partnerTypes.credentialPartner');
        }
        else if (partnerType === "Online_Verification_Partner") {
            return t('partnerTypes.onlineVerficationPartner');
        }
        else if (partnerType === "ABIS_Partner") {
            return t('partnerTypes.abisPartner');
        }
        else if (partnerType === "MISP_Partner") {
            return t('partnerTypes.mispPartner');
        }
        else if (partnerType === "SDK_Partner") {
            return t('partnerTypes.sdkPartner');
        }
        else if (partnerType === "Print_Partner") {
            return t('partnerTypes.printPartner');
        }
        else if (partnerType === "Internal_Partner") {
            return t('partnerTypes.internalPartner');
        }
        else if (partnerType === "Manual_Adjudication") {
            return t('partnerTypes.manualAdjudication');
        }
    }
}

export const handleMouseClickForDropdown = (ref, callback) => {
    const handleClickOutside = (event) => {
        if (ref.current && !ref.current.contains(event.target)) {
            callback();
        }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
        document.removeEventListener('mousedown', handleClickOutside);
    };
};