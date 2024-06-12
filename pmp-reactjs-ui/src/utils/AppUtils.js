export const formatDate = (dateString, format) => {
    if (!dateString) return '-';
    const date = new Date(dateString);

    switch (format) {
        case 'dateTime':
            return date.toLocaleString();
        case 'date':
            return date.toLocaleDateString();
        case 'time':
            return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false });
        default:
            return '-';
    }
};

export const getPartnerTypeDescription = (partnerType, t) => {
    if (partnerType) {
        partnerType = partnerType.toUpperCase();
        if (partnerType === "Device_Provider".toUpperCase()) {
            return t('partnerTypes.deviceProvider');
        }
        else if (partnerType === "FTM_Provider".toUpperCase()) {
            return t('partnerTypes.ftmProvider');
        }
        else if (partnerType === "Auth_Partner".toUpperCase()) {
            return t('partnerTypes.authPartner');
        }
        else if (partnerType === "Credential_Partner".toUpperCase()) {
            return t('partnerTypes.credentialPartner');
        }
        else if (partnerType === "Online_Verification_Partner".toUpperCase()) {
            return t('partnerTypes.onlineVerficationPartner');
        }
        else if (partnerType === "ABIS_Partner".toUpperCase()) {
            return t('partnerTypes.abisPartner');
        }
        else if (partnerType === "MISP_Partner".toUpperCase()) {
            return t('partnerTypes.mispPartner');
        }
        else if (partnerType === "SDK_Partner".toUpperCase()) {
            return t('partnerTypes.sdkPartner');
        }
        else if (partnerType === "Print_Partner".toUpperCase()) {
            return t('partnerTypes.printPartner');
        }
        else if (partnerType === "Internal_Partner".toUpperCase()) {
            return t('partnerTypes.internalPartner');
        }
        else if (partnerType === "Manual_Adjudication".toUpperCase()) {
            return t('partnerTypes.manualAdjudication');
        }
    }
}

export const getStatusCode = (status, t) => {
    if(status) {
        status = status.toLowerCase();
        if (status === "approved") {
            return t('statusCodes.approved');
        } else if (status === "inprogress" || status === 'pending for approval') {
            return t('statusCodes.inProgress');
        } else if (status === "rejected") {
            return t('statusCodes.rejected');
        } else if (status === "deactivated") {
            return t('statusCodes.deactivated');
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

export const getPartnerManagerUrl = (url, env) => {
    let newUrl = '';
    if (env !== 'production') {
        newUrl = "/partnerapi" + url;
    } else {
        newUrl = url;
    }
    return newUrl;
}

export const getPolicyManagerUrl = (url, env) => {
    let newUrl = '';
    if (env !== 'production') {
        newUrl = "/policyapi" + url;
    } else {
        newUrl = url;
    }
    return newUrl;
}

export const createRequest = (requestData, id) => {
    const request = {
        id: id ? id : "",
        version: "1.0",
        requesttime: new Date().toISOString(),
        request: requestData
    };
    return request;
}

export const handleServiceErrors = (responseData, setErrorCode, setErrorMsg) => {
    if (responseData && responseData.errors && responseData.errors.length > 0) {
        const errorCode = responseData.errors[0].errorCode;
        const errorMessage = responseData.errors[0].message;
        setErrorCode(errorCode);
        setErrorMsg(errorMessage);
        console.error('Error:', errorMessage);
    }
}

export const isLangRTL = (langCode) => {
    if(langCode==='ara'){
    return true;
    }
    else{
        return false;
    }
}

export const moveToPolicies = (navigate) => {
    navigate('/partnermanagement/policies')
};

export const logout = async () => {
    localStorage.clear();
    let redirectUrl = process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL;
    redirectUrl = redirectUrl + getPartnerManagerUrl(`/logout/user?redirecturi=` + btoa(window.location.href), process.env.NODE_ENV);
    console.log(redirectUrl);
    window.location.href = redirectUrl;
}