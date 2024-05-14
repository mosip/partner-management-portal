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

export const getPartnerTypeDescription = (partnerType) => {
    if (partnerType) {
        if (partnerType === "Device_Provider") {
            return "Device Provider"
        }
        else if (partnerType === "FTM_Provider") {
            return "FTM Provider"
        }
        else if (partnerType === "Auth_Partner") {
            return "Authentication Partner"
        }
        else if (partnerType === "Credential_Partner") {
            return "Credential Partner"
        }
        else if (partnerType === "Online_Verification_Partner") {
            return "Online Verification Partner"
        }
        else if (partnerType === "ABIS_Partner") {
            return "ABIS Partner"
        }
        else if (partnerType === "MISP_Partner") {
            return "MISP Partner"
        }
        else if (partnerType === "SDK_Partner") {
            return "SDK Partner"
        }
        else if (partnerType === "Print_Partner") {
            return "Print Partner"
        }
        else if (partnerType === "Internal_Partner") {
            return "Internal Partner"
        }
        else if (partnerType === "Manual_Adjudication") {
            return "Manual Adjudication"
        }
    }
}

export const handleOutsideClick = (ref, callback) => {
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