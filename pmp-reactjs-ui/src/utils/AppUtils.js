import { HttpService } from "../services/HttpService";
import { getLoginRedirectUrl } from "../services/LoginRedirectService";

export const formatDate = (dateString, format, isTimeInUTC) => {
    if (!dateString) return '-';
    let date = new Date(dateString);
    if (isTimeInUTC) {
        date = new Date(date.getTime() - date.getTimezoneOffset()*60*1000);	
    }
    switch (format) {
        case 'dateTime':
            return date.toLocaleString();
        case 'date':
            return date.toLocaleDateString();
        case 'time':
            return date.toLocaleTimeString();
        case 'iso':
            return date.toISOString();
        default:
            return '-';
    }
};

export const getPartnerTypeDescription = (partnerType, t) => {
    const partnerTypeMap = {
        "DEVICE_PROVIDER": 'partnerTypes.deviceProvider',
        "FTM_PROVIDER": 'partnerTypes.ftmProvider',
        "AUTH_PARTNER": 'partnerTypes.authPartner',
        "CREDENTIAL_PARTNER": 'partnerTypes.credentialPartner',
        "ONLINE_VERIFICATION_PARTNER": 'partnerTypes.onlineVerficationPartner',
        "ABIS_PARTNER": 'partnerTypes.abisPartner',
        "MISP_PARTNER": 'partnerTypes.mispPartner',
        "SDK_PARTNER": 'partnerTypes.sdkPartner',
        "PRINT_PARTNER": 'partnerTypes.printPartner',
        "INTERNAL_PARTNER": 'partnerTypes.internalPartner',
        "MANUAL_ADJUDICATION": 'partnerTypes.manualAdjudication'
    };

    if (partnerType) {
        partnerType = partnerType.toUpperCase();
        const description = partnerTypeMap[partnerType];
        if (description) {
            return t(description);
        }
    }
}

export const getStatusCode = (status, t) => {
    if (status) {
        status = status.toLowerCase();
        if (status === "approved") {
            return t('statusCodes.approved');
        } else if (status === "inprogress" || status === 'pending for approval' || status === 'pending_approval') {
            return t('statusCodes.inProgress');
        } else if (status === "rejected") {
            return t('statusCodes.rejected');
        } else if (status === "deactivated" || status === "inactive") {
            return t('statusCodes.deactivated');
        } else if (status === "active") {
            return t('statusCodes.activated');
        } else if (status === "pending_cert_upload") {
            return t('statusCodes.pendingCertUpload');
        } else if (status === "expired") {
            return t('statusCodes.expired');
        } else if (status === "uploaded"){
            return t('statusCodes.uploaded');
        } else if (status === "notuploaded"){
            return t('statusCodes.notUploaded');
        } else if (status === "-") {
            return "-"
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

export const onPressEnterKey = (e, action) => {
    if (e.key === 'Enter') {
        return action();
    }
}

export const handleMouseClickForDropdown = (refs, callback) => {
    const handleClickOutside = (event) => {
        if (Array.isArray(refs.current)) {
            if (refs.current.every(ref => ref && !ref.contains(event.target))) {
                callback();
            }
        } else
            if (refs.current && !refs.current.contains(event.target)) {
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

export const createRequest = (requestData, id, useCamelCase = false) => {
    const request = {
        id: id ? id : "",
        version: "1.0",
        [useCamelCase ? "requestTime" : "requesttime"]: new Date().toISOString(),
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
    if (langCode === 'ara') {
        return true;
    }
    else {
        return false;
    }
}

export const moveToHome = (navigate) => {
    navigate('/partnermanagement')
};

export const moveToPolicies = (navigate) => {
    navigate('/partnermanagement/policies/policiesList')
};

export const moveToOidcClientsList = (navigate) => {
    navigate('/partnermanagement/authenticationServices/oidcClientsList')
};

export const moveToApiKeysList = (navigate) => {
    navigate('/partnermanagement/authenticationServices/apiKeysList')
};

export const moveToSbisList = (navigate) => {
    navigate('/partnermanagement/deviceProviderServices/sbiList');
};

export const logout = async () => {
    localStorage.clear();
    let redirectUrl = process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL;

    try {
        const apiResp = await HttpService.get(getPartnerManagerUrl(`/authorize/admin/validateToken`, process.env.NODE_ENV));

        if (apiResp && apiResp.status === 200 && apiResp.data.response) {
            redirectUrl = redirectUrl + getPartnerManagerUrl(`/logout/user?redirecturi=` + btoa(window.location.href), process.env.NODE_ENV);
        } else {
            console.error('Token validation failed');
            redirectUrl = redirectUrl + getLoginRedirectUrl(window.location.href);
        }
    } catch (error) {
        console.error('Error during token validation:', error);
        redirectUrl = redirectUrl + getLoginRedirectUrl(window.location.href);
    }

    window.location.href = redirectUrl;
};
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

export const validateUrl = (index, value, length, urlArr, t) => {
    const urlPattern = /^(http|https):\/\/[^ "]+$/;
    if (value === "") {
        return "";
    } else if (value.length > length) {
        return t('commons.urlTooLong', { length: length });
    } else if (!urlPattern.test(value.trim())) {
        return t('commons.invalidUrl');
    } else if (urlArr.some((url, i) => url === value && i !== index)) {
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
    else if (status === "InProgress" || status === 'pending_approval') {
        return ("bg-[#FEF1C6] text-[#6D1C00]")
    }
    else if (status === 'pending_cert_upload') {
        return ("bg-[#ee763060] text-[#6D1C00]")
    }
    else if (status === "deactivated" || status === "INACTIVE") {
        return ("bg-[#EAECF0] text-[#525252]")
    }
}

export const createDropdownData = (fieldName, fieldDesc, isBlankEntryRequired, dataList, t, defaultPlaceholder) => {
    let dataArr = [];
    if (isBlankEntryRequired) {
        dataArr.push({
            fieldCode: defaultPlaceholder,
            fieldValue: "",
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
            } else if (fieldName === "status" || fieldName === "certificateExpiryStatus") {
                dataArr.push({
                    fieldCode: getStatusCode(item[fieldName], t),
                    fieldValue: item[fieldName]
                });
            } else {
                if (fieldDesc) {
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

export const getAuthPartnerPolicies = async (HttpService, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl('/partners/auth-partners-policies', process.env.NODE_ENV));
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
        console.error('Error in getAuthPartnerPolicies:', error);
        return null;
    }
};

export const populateDeactivatedStatus = (data, statusAttributeName, activeAttributeName) => {
    // Updating the status based on the condition
    const updatedData = data.map(item => {
        if (item[statusAttributeName] === 'approved' && item[activeAttributeName] === false) {
            return { ...item, [statusAttributeName]: 'deactivated' };
        }
        return item;
    });
    return updatedData;
};

export const getPartnerDomainType = (partnerType) => {
    if (partnerType) {
        partnerType = partnerType.toUpperCase();
        if (partnerType === "Device_Provider".toUpperCase()) {
            return 'DEVICE';
        }
        else if (partnerType === "FTM_Provider".toUpperCase()) {
            return 'FTM';
        }
        else if (partnerType === "MISP_type".toUpperCase()) {
            return 'MISP';
        }
        else {
            return 'AUTH';
        }
    }
};

export const trimAndReplace = (str) => {
    return str.trim().replace(/\s+/g, ' ');
};