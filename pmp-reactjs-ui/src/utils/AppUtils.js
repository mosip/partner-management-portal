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
        } else if (status === "deactivated" || status === "inactive") {
            return t('statusCodes.deactivated');
        } else if (status === "active") {
            return t('statusCodes.activated');
        }
    }

}

export const getGrantTypes = (type, t) => {
    if (type) {
        if (type === "authorization_code") {
            return t('createOidcClient.authorization_code');
        } else {
            return type;
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

export const isLangFra = (langCode) => {
    if(langCode==='fra'){
    return true;
    }
    else{
        return false;
    }
}

export const moveToHome = (navigate) => {
    navigate('/partnermanagement')
};

export const moveToPolicies = (navigate) => {
    navigate('/partnermanagement/policies')
};

export const moveToOidcClientsList = (navigate) => {
    navigate('/partnermanagement/authenticationServices/oidcClientsList')
};

export const moveToApiKeysList = (navigate) => {
    navigate('/partnermanagement/authenticationServices/apiKeysList')
};

export const logout = async () => {
    localStorage.clear();
    let redirectUrl = process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL;
    redirectUrl = redirectUrl + getPartnerManagerUrl(`/logout/user?redirecturi=` + btoa(window.location.href), process.env.NODE_ENV);
    console.log(redirectUrl);
    window.location.href = redirectUrl;
}

export const toggleSortDescOrder = (sortItem, isDateCol, filteredList, setFilteredList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc) => {
    if (order === 'ASC') {
        if (isDateCol) {
            const sortedList = [...filteredList].sort((a, b) => {
                const dateA = new Date(a[sortItem]);
                const dateB = new Date(b[sortItem]);
                return isDescending ? dateA - dateB : dateB - dateA;
            });
            setFilteredList(sortedList);
            setOrder("DESC")
            setIsDescending(!isDescending);
            setActiveSortDesc(sortItem);
            setActiveSortAsc(sortItem);
        }
        else {
            const sortedList = [...filteredList].sort((a, b) =>
                a[sortItem].toLowerCase() > b[sortItem].toLowerCase() ? 1 : -1
            );
            setFilteredList(sortedList);
            setOrder("DESC")
            setActiveSortDesc(sortItem);
            setActiveSortAsc(sortItem);
        }
    }
}

export const toggleSortAscOrder = (sortItem, isDateCol, filteredList, setFilteredList, order, setOrder, isDescending, setIsDescending, setActiveSortAsc, setActiveSortDesc) => {
    if (order === 'DESC') {
        if (isDateCol) {
            const sortedList = [...filteredList].sort((a, b) => {
                const dateA = new Date(a[sortItem]);
                const dateB = new Date(b[sortItem]);
                return isDescending ? dateA - dateB : dateB - dateA;
            });

            setFilteredList(sortedList);
            setOrder("ASC")
            setIsDescending(!isDescending);
            setActiveSortDesc(sortItem);
            setActiveSortAsc(sortItem);
        }
        else {
            const sortedList = [...filteredList].sort((a, b) =>
                a[sortItem].toLowerCase() < b[sortItem].toLowerCase() ? 1 : -1
            );
            setFilteredList(sortedList);
            setOrder("ASC")
            setActiveSortDesc(sortItem);
            setActiveSortAsc(sortItem);
        }
    }
};