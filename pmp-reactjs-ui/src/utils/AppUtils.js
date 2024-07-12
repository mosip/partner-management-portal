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
const areAllValuesSame = (list, column) => {
    const firstValue = list[0][column];
    return list.every(item => item[column] === firstValue);
};

export const toggleSortDescOrder = (sortItem, isDateCol, filteredList, setFilteredList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc) => {
    if (areAllValuesSame(filteredList, sortItem)) {
        setOrder("DESC")
        setIsDescending(true);
        setActiveSortDesc(sortItem);
        setActiveSortAsc("");
    } else {
        if (order !== 'DESC' || activeSortDesc !== sortItem) {
            let sortedList;
            if (isDateCol) {
                sortedList = [...filteredList].sort((a, b) => {
                    const dateA = new Date(a[sortItem]);
                    const dateB = new Date(b[sortItem]);
                    return dateA - dateB;
                });
            }
            else {
                sortedList = [...filteredList].sort((a, b) =>
                    a[sortItem].toLowerCase() > b[sortItem].toLowerCase() ? 1 : -1
                );
            }
            setFilteredList(sortedList);
            setOrder("DESC")
            setIsDescending(true);
            setActiveSortDesc(sortItem);
            setActiveSortAsc("");
        }
    }
}

export const toggleSortAscOrder = (sortItem, isDateCol, filteredList, setFilteredList, order, setOrder, isDescending, setIsDescending, activeSortAsc, setActiveSortAsc, activeSortDesc, setActiveSortDesc) => {
    if (areAllValuesSame(filteredList, sortItem)) {
        setOrder("ASC")
        setIsDescending(false);
        setActiveSortDesc("");
        setActiveSortAsc(sortItem);
    } else {
        if (order !== 'ASC' || activeSortAsc !== sortItem) {
            let sortedList;
            if (isDateCol) {
                sortedList = [...filteredList].sort((a, b) => {
                    const dateA = new Date(a[sortItem]);
                    const dateB = new Date(b[sortItem]);
                    return dateB - dateA;
                });
            }
            else {
                sortedList = [...filteredList].sort((a, b) =>
                    a[sortItem].toLowerCase() < b[sortItem].toLowerCase() ? 1 : -1
                );
            }
            setFilteredList(sortedList);
            setOrder("ASC")
            setIsDescending(false);
            setActiveSortDesc("");
            setActiveSortAsc(sortItem);
        }
    }
};


export const validateName = (value, length, t) => {
    const regexPattern = /^(?!\s+$)[a-zA-Z0-9-_ ,.&()]*$/;
    if (value.length > length) {
        return t('commons.nameTooLong', {length: length});
    } else if (!regexPattern.test(value)) {
        return t('commons.specialCharNotAllowed');
    } else {
      return "";
    }
};

export const validateUrl = (index, value, length, newRedirectUrls, t) => {
    const urlPattern = /^(http|https):\/\/[^ "]+$/;
    if (value === "") {
        return "";
    } else if (value.length > length) {
        return t('commons.urlTooLong', {length: length});
    } else if (!urlPattern.test(value.trim())) {
        return t('commons.invalidUrl');
    } else if (newRedirectUrls.some((url, i) => url === value && i !== index)) {
        return t('commons.duplicateUrl');
    } else if (/^\s+$/.test(value)) {
        return t('commons.invalidUrl'); // Show error for input with only spaces
    } else {
        return "";
    }
}

export const bgOfStatus = (status) => {
    if (status === "approved" || status === "ACTIVE") {
      return ("bg-[#D1FADF] text-[#155E3E]")
    }
    else if (status === "rejected") {
      return ("bg-[#FAD6D1] text-[#5E1515]")
    }
    else if (status === "InProgress") {
      return ("bg-[#FEF1C6] text-[#6D1C00]")
    }
    else if (status === "deactivated" || status === "INACTIVE") {
      return ("bg-[#EAECF0] text-[#525252]")
    }
}

export const createDropdownData = (fieldName, fieldDesc, isBlankEntryRequired, dataList, t) => {
    let dataArr = [];
    if (isBlankEntryRequired) {
        dataArr.push({
            fieldCode: "",
            fieldValue: ""
        });
    }
    dataList.forEach(item => {
        let alreadyAdded = false;
        dataArr.forEach(item1 => {
            if (item1.fieldValue === item[fieldName]) {
                alreadyAdded = true;
            }
        });
        if (!alreadyAdded) {
            if (fieldName === "partnerType") {
                dataArr.push({
                    fieldCode: getPartnerTypeDescription(item[fieldName], t),
                    fieldValue: item[fieldName]
                });
            } else if (fieldName === "status") {
                dataArr.push({
                    fieldCode: getStatusCode(item[fieldName], t),
                    fieldValue: item[fieldName]
                });
            } else {
                if(fieldDesc) {
                    dataArr.push({
                        fieldCode: item[fieldName],
                        fieldValue: item[fieldName],
                        fieldDescription: item[fieldDesc]
                    });
                } else {
                    dataArr.push({
                        fieldCode: item[fieldName],
                        fieldValue: item[fieldName]
                    });
                }
            }
        }
    });
    return dataArr;
}

export const getAllApprovedAuthPartnerPolicies = async (HttpService, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApprovedAuthPartnerPolicies', process.env.NODE_ENV));
        if (response && response.data) {
            const responseData = response.data;
            if (responseData.response) {
                const resData = responseData.response;
                return resData;
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } else {
            return null;
        }
    } catch (error) {
        console.error('Error in getAllApprovedAuthPartnerPolicies:', error);
        return null;
    }
};