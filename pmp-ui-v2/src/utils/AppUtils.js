import { getAppConfig } from "../services/ConfigService";
import { Trans } from "react-i18next";
import { HttpService } from "../services/HttpService";
import { getLoginRedirectUrl } from "../services/LoginRedirectService";
import { updateHeaderNotifications } from "../notificationsSlice";
import { v4 as uuidv4 } from 'uuid';

export const formatDate = (dateString, format) => {
    if (!dateString) return '-';

    const withoutOffset = dateString.replace(/([+-]\d{2}:\d{2})$/, "");
    let date = new Date(withoutOffset);
    date = new Date(date.getTime() - date.getTimezoneOffset() * 60 * 1000);
    switch (format) {
        case 'dateTime':
            return date.toLocaleString();
        case 'date':
            return date.toLocaleDateString();
        case 'time':
            return date.toLocaleTimeString();
        case 'iso':
            return date.toISOString();
        case 'dateInWords':
            return date.toLocaleDateString(navigator.language, {
                month: "long",
                day: "numeric",
                year: "numeric",
            });
        case 'dateMonthInWords':
            return date.toLocaleDateString(navigator.language, {
                month: "long",
                day: "2-digit",
            });
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
        "MANUAL_ADJUDICATION": 'partnerTypes.manualAdjudication',
        "PARTNER_ADMIN": 'partnerTypes.partnerAdmin',
    };

    if (partnerType) {
        partnerType = partnerType.toUpperCase();
        const description = partnerTypeMap[partnerType];
        if (description) {
            return t(description);
        }
    }
}

/**
 * Fetches partner types from the backend
 */
export const fetchPartnerTypes = async () => {
  try {
    const request = createRequest({
      "filters": [],
      "pagination": { "pageFetch": 100, "pageStart": 0 },
      "sort": []
    });

    const response = await HttpService.post(
      getPartnerManagerUrl(`/partners/partnertype/search`, process.env.NODE_ENV),
      request
    );

    if (response && response.data) {
      const responseData = response.data;
      if (responseData.response && responseData.response.data) {
        const partnerTypeCodes = responseData.response.data
          .map(item => item.code)
          .filter(Boolean)
          .map(code => code.toLowerCase());
        return new Set(partnerTypeCodes);
      }
    }
    
    console.error('Failed to fetch partner types from backend');
    return null;
  } catch (err) {
    console.error('Error fetching partner types from backend:', err);
    // Re-throw 403 errors so they can be handled specifically
    if (err.response?.status === 403) {
      throw err;
    }
    return null;
  }
};

export const getPartnerType = async (userProfile) => {
  try {
    // Fetch partner types from backend
    const validPartnerTypes = await fetchPartnerTypes();
    
    if (!validPartnerTypes) {
      return null;
    }

    const userRoles = (userProfile.roles ?? '')
        .split(',')                // turn "A,B,C" into ["A", "B", "C"]
        .map(role => role.trim())  // remove extra spaces from each role
        .filter(role => role);     // drop empty entries

    // Return first role that is a valid partner type (case-insensitive)
    return userRoles.find(role => validPartnerTypes.has(role.toLowerCase())) ?? null;
  } catch (err) {
    // Re-throw 403 errors so they can be handled specifically
    if (err.response?.status === 403) {
      throw err;
    }
    return null;
  }
}; 

export const getLanguageLabel = (languageCode, t) => {
    const languageMap = {
        "eng": "English",
        "hin": "हिन्दी",
        "ara": "العربية",
        "fra": "Français",
        "tam": "தமிழ்",
        "kan": "ಕನ್ನಡ"
    };

    if (languageCode) {
        const languageLabel = languageMap[languageCode.toLowerCase()];
        if (languageLabel) {
            return languageLabel;
        }
    }
    return languageCode; // fallback to code if no translation found
}

export const getLanguageDisplayName = (languageCode, t) => {
    if (languageCode === '@none' || languageCode === 'default') {
        return t('createOidcClient.default');
    }

    const displayNameMap = {
        "eng": "English",
        "hin": "हिन्दी",
        "ara": "العربية",
        "fra": "Français",
        "tam": "தமிழ்",
        "kan": "ಕನ್ನಡ"
    };

    if (languageCode) {
        const displayName = displayNameMap[languageCode.toLowerCase()];
        if (displayName) {
            return displayName;
        }
    }
    return languageCode; // fallback to code if no display name found
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
        } else if (status === "active" || status === "activated") {
            return t('statusCodes.active');
        } else if (status === "pending_cert_upload") {
            return t('statusCodes.pendingCertUpload');
        } else if (status === "expired") {
            return t('statusCodes.expired');
        } else if (status === "uploaded") {
            return t('statusCodes.uploaded');
        } else if (status === "not_uploaded") {
            return t('statusCodes.notUploaded');
        } else if (status === "draft") {
            return t('statusCodes.draft');
        } else if (status === "valid") {
            return t('statusCodes.valid');
        } else if (status === "not_available") {
            return t('statusCodes.notAvailable');
        } else if (status === "partner_inactive") {
            return t('statusCodes.inactive');
        }
        else if (status === "-") {
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
        e.preventDefault();
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

export const isLangRTL = (locale) => {
    if (locale === 'ara') {
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
    navigate('/partnermanagement/policies/policies-list')
};

export const moveToOidcClientsList = (navigate) => {
    navigate('/partnermanagement/authentication-services/oidc-clients-list')
};

export const moveToApiKeysList = (navigate) => {
    navigate('/partnermanagement/authentication-services/api-keys-list')
};

export const moveToSbisList = (navigate) => {
    navigate('/partnermanagement/device-provider-services/sbi-list');
};

export const moveToMispPartnerServices = (navigate) => {
    navigate('/partnermanagement/admin/misp-partner-services/misp-license-list');
};

export const logout = async () => {
    sessionStorage.clear();
    let redirectUrl = process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL;

    try {
        const apiResp = await HttpService.get(getPartnerManagerUrl(`/authorize/admin/validateToken`, process.env.NODE_ENV));

        if (apiResp && apiResp.status === 200 && apiResp.data.response) {
            redirectUrl = redirectUrl + getPartnerManagerUrl(`/logout/user?redirecturi=` + btoa(window.location.origin + '/#/partnermanagement/dashboard'), process.env.NODE_ENV);
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
                    return dateB - dateA;
                });
            }
            else {
                sortedList = [...filteredList].sort((a, b) =>
                    a[sortItem].toLowerCase() < b[sortItem].toLowerCase() ? 1 : -1
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
                    return dateA - dateB;
                });
            }
            else {
                sortedList = [...filteredList].sort((a, b) =>
                    a[sortItem].toLowerCase() > b[sortItem].toLowerCase() ? 1 : -1
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

    const validateSingleUrl = (val, i) => {
        if (val === "") {
            return "";
        } else if (val.length > length) {
            return t('commons.urlTooLong', { length: length });
        } else if (!urlPattern.test(val.trim())) {
            return t('commons.invalidUrl');
        } else if (urlArr.some((url, idx) => url === val && idx !== i)) {
            return t('commons.duplicateUrl');
        } else if (/^\s+$/.test(val)) {
            return t('commons.invalidUrl');
        } else {
            return "";
        }
    };

    // Handle when value is an array
    if (Array.isArray(value)) {
        for (let i = 0; i < value.length; i++) {
            const error = validateSingleUrl(value[i], i);
            if (error) {
                return error;
            }
        }
        return "";
    }

    // Fallback for single string input
    return validateSingleUrl(value, index);
};

export const bgOfStatus = (status) => {
    if (status) {
        status = status.toLowerCase();
        if (status === "approved" || status === "active" || status === "activated") {
            return ("bg-[#D1FADF] text-[#155E3E]")
        }
        else if (status === "rejected") {
            return ("bg-[#FAD6D1] text-[#5E1515]")
        }
        else if (status === "inprogress" || status === 'pending_approval' || status === 'draft') {
            return ("bg-[#FEF1C6] text-[#6D1C00]")
        }
        else if (status === 'pending_cert_upload') {
            return ("bg-[#ee763060] text-[#6D1C00]")
        }
        else if (status === "deactivated" || status === "inactive") {
            return ("bg-[#EAECF0] text-[#525252]")
        }
    }
}
export const createDropdownData = (
    fieldName,
    fieldDesc,
    isBlankEntryRequired,
    dataList,
    t,
    defaultPlaceholder
) => {
    if (
        !Array.isArray(dataList) ||
        typeof fieldName !== "string" ||
        (fieldDesc && typeof fieldDesc !== "string")
    ) {
        return [];
    }

    let dataArr = [];
    if (isBlankEntryRequired) {
        dataArr.push({
            fieldCode: defaultPlaceholder,
            fieldValue: "",
        });
    }

    dataList.forEach(item => {

        // Prevent duplicates
        let alreadyAdded = dataArr.some(entry => entry.fieldValue === item[fieldName]);
        if (!alreadyAdded) {
            if (fieldName === "partnerType") {
                dataArr.push({
                    fieldCode: getPartnerTypeDescription(item[fieldName], t) || item[fieldName],
                    fieldValue: item[fieldName]
                });
            } else if (
                ["status", "certificateExpiryStatus", "certificateUploadStatus", "sbiExpiryStatus"].includes(fieldName)
            ) {
                dataArr.push({
                    fieldCode: getStatusCode(item[fieldName], t) || item[fieldName],
                    fieldValue: item[fieldName]
                });
            } else if (fieldName === "languageCode") {
                // Language dropdown variant using languageCode
                dataArr.push({
                    fieldCode: item.name,
                    fieldValue: item.languageCode
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
    // If placeholder is required, remove it from the array, otherwise use the entire array
    const dataToSort = isBlankEntryRequired ? dataArr.slice(1) : dataArr;

    // Sort the data
    const sortedData = dataToSort.sort((a, b) => a.fieldCode.localeCompare(b.fieldCode, undefined, { sensitivity: 'base' }));

    // Prepend the placeholder if required
    if (isBlankEntryRequired) {
        sortedData.unshift(dataArr[0]);
    }

    return sortedData;
}

export const getPartnerPolicyRequests = async (HttpService, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/partner-policy-requests`, process.env.NODE_ENV));
        if (response && response.data) {
            const responseData = response.data;
            if (responseData.response) {
                const resData = responseData.response.data;
                return resData;
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } else {
            return null;
        }
    } catch (error) {
        console.error('Error in getPartnerPolicyRequests:', error);
        return null;
    }
};

export const getApprovedAuthPartners = async (HttpService, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/partners/v3?status=approved&policyGroupAvailable=true&partnerType=Auth_Partner`, process.env.NODE_ENV));
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
        console.error('Error in getApprovedAuthPartnes:', error);
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
        else if (partnerType === "MISP_Partner".toUpperCase()) {
            return 'MISP';
        }
        else if (partnerType === "ABIS_Partner".toUpperCase()) {
            return 'AUTH';
        }
        else {
            return 'AUTH';
        }
    }
};

export const trimAndReplace = (str) => {
    return str.trim().replaceAll(/\s+/g, ' ');
};

export const getErrorMessage = (errorCode, t, errorMessage) => {
    const serverErrors = t('serverError', { returnObjects: true });

    if (errorCode && serverErrors[errorCode]) {
        return serverErrors[errorCode];
    } else {
        return errorMessage;
    }
};

export const getCertificate = async (HttpService, partnerId, setErrorCode, setErrorMsg, t) => {
    try {
        const isApiExist = await isCaSignedPartnerCertificateAvailable();
        const url = isApiExist ? `/partners/${partnerId}/certificate-data` : `/partners/${partnerId}/certificate`;
        const response = await HttpService.get(getPartnerManagerUrl(url, process.env.NODE_ENV));
        if (response && response.data) {
            const responseData = response.data
            if (responseData.response) {
                return responseData;
            } else if (response.data.errors && response.data.errors.length > 0) {
                const errorCode = response.data.errors[0].errorCode;
                if (errorCode === 'PMS_KKS_001') {
                    setErrorMsg(t('trustList.errorWhileDownloadingCertificate'));
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            }
        } else {
            return null;
        }
    } catch (error) {
        console.error('Error fetching certificate:', error);
        return null;
    }
};

export const downloadFile = (data, fileName, fileType) => {
    const blob = new Blob([data], { type: fileType });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = fileName;

    document.body.appendChild(link);
    link.click();

    // Cleanup
    window.URL.revokeObjectURL(url);
    document.body.removeChild(link);
};

export const resetPageNumber = (totalRecords, pageNo, pageSize, resetPageNo) => {
    const totalNumberOfPages = Math.ceil(totalRecords / pageSize);
    const effectivePageNo = pageNo >= totalNumberOfPages || resetPageNo ? 0 : pageNo;
    return effectivePageNo;
};

export const onClickApplyFilter = (updatedfilters, setApplyFilter, setResetPageNo, setFetchData, setFilters, setIsApplyFilterClicked) => {
    setApplyFilter(true);
    setResetPageNo(true);
    setFetchData(true);
    setFilters(updatedfilters);
    setIsApplyFilterClicked(true);
};

export const setPageNumberAndPageSize = (recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData) => {
    // console.log(recordsPerPage, pageIndex);
    if (pageNo !== pageIndex || pageSize !== recordsPerPage) {
        setPageNo(pageIndex);
        setPageSize(recordsPerPage);
        setFetchData(true);
    }
};

export const onResetFilter = () => {
    window.location.reload();
};

export const getPolicyGroupList = async (HttpService, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService({
            url: getPolicyManagerUrl('/policies/policy-groups', process.env.NODE_ENV),
            method: 'get',
            baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
        });
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                return responseData.response;
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                return null;
            }
        } else {
            setErrorMsg(t('selectPolicyPopup.policyGroupError'));
            return null;
        }
    } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
        return null;
    }
};

export const getPolicyDetails = async (HttpService, policyId, setErrorCode, setErrorMsg) => {
    try {
        const response = await HttpService({
            url: getPolicyManagerUrl(`/policies/${policyId}`, process.env.NODE_ENV),
            method: 'get',
            baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
        });
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response;
                return resData;
            }
            else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        }
        return null;
    } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
        return null;
    }
};

export const handleFileChange = (event, setErrorCode, setErrorMsg, setSuccessMsg, setPolicyData, t) => {
    setErrorMsg("");
    setErrorCode("");
    setSuccessMsg("");
    const file = event.target.files[0];
    if (file?.type === "application/json") {
        const reader = new FileReader();
        reader.onload = () => {
            try {
                const data = JSON.parse(reader.result);
                setPolicyData(JSON.stringify(data, null, 2));
                setSuccessMsg(t('createPolicy.fileUploadSuccessMsg'));
            } catch (error) {
                console.log(`Exception while parsing json: ${error}`);
                setErrorMsg(t('createPolicy.jsonParseError'));
            }
        };
        reader.readAsText(file);
    } else {
        setErrorMsg(t('createPolicy.uploadFileError'));
    }
    event.target.value = '';
};

export const getClientNameDefault = (clientName) => {
    try {
        const jsonObj = JSON.parse(clientName);
        if (jsonObj['@none']) {
            return jsonObj['@none'];
        }
        // If "@none" is not present, return the original string
        return clientName;
    } catch {
        // If the string is not a valid JSON, return as it is
        return clientName;
    }
}

export const populateClientNames = (data) => {
    // Updating the status based on the condition
    const extractedList = data.map(item => {
        return {
            ...item,
            clientNameJson: item.clientName,
            clientNameEng: getClientNameDefault(item.clientName)
        };
    });
    return extractedList;
};

export const getClientNameLangMap = (clientName, clientNameLangMap) => {
    if (clientNameLangMap && typeof clientNameLangMap === 'object' && Object.keys(clientNameLangMap).length > 0) {
        return clientNameLangMap;
    } else {
        const newJsonObject = {
            eng: clientName
        }
        return newJsonObject;
    }
}

export const getOidcClientDetails = async (HttpService, clientId, setErrorCode, setErrorMsg) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/oidc-clients/${clientId}`, process.env.NODE_ENV));
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response;
                return resData;
            }
            else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        }
        return null;
    } catch (error) {
        console.error('Error in getOidcClientDetails:', error);
        return null;
    }
};

export const copyClientId = (data, textToCopied, setCopied) => {
    if (data.status === "ACTIVE") {
        navigator.clipboard.writeText(textToCopied).then(() => {
            setCopied(true);
            setTimeout(() => setCopied(false), 3000);
        }).catch(err => {
            console.error('Failed to copy text: ', err);
        });
    }
};

export const getApproveRejectStatus = (status) => {
    if (status === "approved") {
        return "approved";
    }
    if (status === "rejected") {
        return "rejected";
    }
};

export const updateActiveState = (status) => {
    if (status === "approved") {
        return true;
    }
    if (status === "rejected") {
        return false;
    }
};

export const fetchDeviceTypeDropdownData = async (setErrorCode, setErrorMsg, t) => {
    const request = createRequest({
        filters: [
            {
                columnName: "name",
                type: "unique",
                text: ""
            }
        ],
        optionalFilters: [],
        purpose: "REGISTRATION"
    });

    try {
        const response = await HttpService.post(getPartnerManagerUrl(`/devicedetail/deviceType/filtervalues`, process.env.NODE_ENV), request);
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                return responseData.response.filters;
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                return [];
            }
        } else {
            setErrorMsg(t('addDevices.errorInDeviceType'));
            return [];
        }
    } catch (err) {
        console.log("Error fetching data: ", err);
        return [];
    }
}

export const fetchDeviceSubTypeDropdownData = async (type, setErrorCode, setErrorMsg, t) => {
    const request = createRequest({
        filters: [
            {
                columnName: "deviceType",
                type: "unique",
                text: type
            }
        ],
        optionalFilters: [],
        purpose: "REGISTRATION"
    });
    try {
        const response = await HttpService.post(getPartnerManagerUrl(`/devicedetail/deviceSubType/filtervalues`, process.env.NODE_ENV), request);
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                return responseData.response.filters;
            } else {
                if (responseData && responseData.errors && responseData.errors.length > 0) {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
                return [];
            }
        } else {
            setErrorMsg(t('addDevices.errorInDeviceSubType'));
            return [];
        }
    } catch (err) {
        console.log("Error fetching data: ", err);
        return [];
    }
}

export const downloadCaTrust = async (HttpService, certificateId, trustType, setErrorCode, setErrorMsg, errorMsg, setSuccessMsg, t) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/trust-chain-certificates/${certificateId}/certificateFile`, process.env.NODE_ENV));
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response;
                const blob = new Blob([resData.p7bFile], { type: "application/x-pkcs7-certificates" });
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement("a");
                link.href = url;
                link.download = (trustType === 'root' ? "root-certificate.p7b" : "intermediate-certificate.p7b");

                document.body.appendChild(link);
                link.click();
                setSuccessMsg(trustType === 'root' ? t('uploadTrustCertificate.downloadRootCertSuccessMsg') : t('uploadTrustCertificate.downloadIntermediateCertSuccessMsg'));

                // CleanUP Code
                window.URL.revokeObjectURL(url);
                document.body.removeChild(link);
            }
            else {
                handleKeymanagerErrors(responseData, setErrorCode, setErrorMsg, t);
            }
        } else {
            setErrorMsg(t('viewCertificateDetails.errorIndownloadTrust'));
            console.log(errorMsg);

        }
    } catch (err) {
        console.error('Error fetching certificate Details:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
    }
};



export const handleEscapeKey = (closePopup) => {
    const handleEscape = (event) => {
        if (event.key === 'Escape') {
            closePopup();
        }
    };

    document.addEventListener('keydown', handleEscape);

    return () => {
        document.removeEventListener('keydown', handleEscape);
    };
};

export const formatPublicKey = (publicKeyString) => {
    try {
        const data = JSON.parse(publicKeyString);
        const jsonStr = JSON.stringify(data, null, 2);
        return jsonStr;
    } catch {
        return publicKeyString;
    }
}

export const handleKeymanagerErrors = (responseData, setErrorCode, setErrorMsg, t) => {
    if (responseData && responseData.errors && responseData.errors.length > 0) {
        const errorCode = responseData.errors[0].errorCode;
        const errorMessage = responseData.errors[0].message;
        if (errorCode === "PMS_KKS_001") {
          setErrorMsg(t('trustList.errorWhileDownloadingCertificate'));
        } else {
          setErrorCode(errorCode);
          setErrorMsg(errorMessage);
        }
        console.error('Error:', errorMessage);
    }
  }

export const setSubmenuRef = (refArray, index) => (el) => {
    if (el) refArray.current[index] = el;
};

export const isCaSignedPartnerCertificateAvailable = async () => {
    try {
        const configData = await getAppConfig();
        const isApiExist = configData.isCaSignedPartnerCertificateAvailable === "true" ? true : false;
        return isApiExist;
    } catch (error) {
        console.error("Error fetching or parsing app config: ", error);
        return false;
    }
};

export const isOidcClientAvailable = async () => {
    try {
        const configData = await getAppConfig();
        const isApiExist = configData.isOidcClientAvailable === "true" ? true : false;
        return isApiExist;
    } catch (error) {
        console.error("Error fetching or parsing app config: ", error);
        return false;
    }
};

export const isOidcClientAdditionalInfoRequired = async () => {
    try {
        const configData = await getAppConfig();
        const isRequired = configData.isOidcClientAdditionalInfoRequired === "true" ? true : false;
        return isRequired;
    } catch (error) {
        console.error("Error fetching or parsing app config: ", error);
        return false;
    }
};

export const createDeactivateRequest = async (selectedClientdata, setTableDataLoaded, setDeactivateRequest, setErrorCode, setErrorMsg, t) => {
    setTableDataLoaded(false);
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/oidc-clients/${selectedClientdata.clientId}`, process.env.NODE_ENV));
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const clientData = responseData.response;
                const request = createRequest({
                    logoUri: clientData.logoUri,
                    redirectUris: clientData.redirectUris,
                    status: "INACTIVE",
                    grantTypes: clientData.grantTypes,
                    clientName: clientData.name,
                    clientAuthMethods: clientData.clientAuthMethods,
                    clientNameLangMap: getClientNameLangMap(clientData.name, clientData.clientNameLangMap)
                });
                setDeactivateRequest(request);
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } else {
            setErrorMsg(t('oidcClientsList.errorInOidcClientsList'))
        }
    } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
    }
    setTableDataLoaded(true);
};

export const buildClientNameLangMap = (entries, additionalConfigRequired, clientName) => {
    const langMap = {};

    const applyEntries = () => {
      entries?.forEach(entry => {
        if (entry.language && entry.text && entry.text.trim() !== '') {
          langMap[entry.language] = trimAndReplace(entry.text);
        }
      });
    };

    if (additionalConfigRequired) {
      // Case 1: Use entries ONLY
      applyEntries();
      return langMap;
    }

    // Case 2: additionalConfigRequired === false
    applyEntries();

    // If no valid entries found - fallback to English clientName
    if (Object.keys(langMap).length === 0) {
      langMap["eng"] = trimAndReplace(clientName);
    }

    return langMap;
};

export const isRootIntermediateCertAvailable = async () => {
    try {
        const configData = await getAppConfig();
        const isApiExist = configData.isRootIntermediateCertAvailable === "true" ? true : false;
        return isApiExist;
    } catch (error) {
        console.error("Error fetching or parsing app config: ", error);
        return false;
    }
};

export const checkCertificateExpired = (expiryDateTime) => { 
    if (!expiryDateTime) return false; // treat as non expired if no date

    const currentUTC = new Date().toISOString(); // current UTC time
    const expiryUTC = new Date(expiryDateTime).toISOString();

    return expiryUTC < currentUTC;
};

export const getWeeklySummaryDescription = (notification, isLoginLanguageRTL, t) => {
    const { certificateDetails = [], ftmDetails = [], sbiDetails = [], apiKeyDetails = [] } = notification.notificationDetails || {};

    const certificateList = Array.isArray(certificateDetails) ? certificateDetails : [];
    const ftmCertList = Array.isArray(ftmDetails) ? ftmDetails : [];
    const sbiList = Array.isArray(sbiDetails) ? sbiDetails : [];
    const apiKeyList = Array.isArray(apiKeyDetails) ? apiKeyDetails : [];

    const partnerCertCount = certificateList.length;
    const ftmCertCount = ftmCertList.length;
    const sbiCount = sbiList.length;
    const apiKeyCount = apiKeyList.length;

    const descriptionItems = [
        partnerCertCount > 0 && t('notificationPopup.partnerCertificates', { partnerCertCount }),
        ftmCertCount > 0 && t('notificationPopup.ftmCertificates', { ftmCertCount }),
        apiKeyCount > 0 && t('notificationPopup.apiKeys', { apiKeyCount }),
        sbiCount > 0 && t('notificationPopup.sbiDevices', { sbiCount }),
    ].filter(Boolean);

    return descriptionItems.length ? (
        <ul className={`list-disc ${isLoginLanguageRTL ? 'mr-6' : 'ml-6'}`}>
            {descriptionItems.map((item, index) => (
                <li key={index}>{item}</li>
            ))}
        </ul>
    ) : null;
};

export const getNotificationDescription = (notification, isLoginLanguageRTL, t) => {
    if (notification.notificationType === 'ROOT_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationsTab.rootCertExpiryDescription"
                values={{
                    certificateId: notification.notificationDetails.certificateDetails[0].certificateId,
                    issuedTo: notification.notificationDetails.certificateDetails[0].issuedTo,
                    issuedBy: notification.notificationDetails.certificateDetails[0].issuedBy,
                    partnerDomain: notification.notificationDetails.certificateDetails[0].partnerDomain,
                    expiryDateTime: formatDate(notification.notificationDetails.certificateDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );
    } else if (notification.notificationType === 'INTERMEDIATE_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationsTab.intermediateCertExpiryDescription"
                values={{
                    certificateId: notification.notificationDetails.certificateDetails[0].certificateId,
                    issuedTo: notification.notificationDetails.certificateDetails[0].issuedTo,
                    issuedBy: notification.notificationDetails.certificateDetails[0].issuedBy,
                    partnerDomain: notification.notificationDetails.certificateDetails[0].partnerDomain,
                    expiryDateTime: formatDate(notification.notificationDetails.certificateDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );
    } else if (notification.notificationType === 'PARTNER_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="partnerNotificationsTab.partnerCertExpiryDescription"
                values={{
                    issuedTo: notification.notificationDetails.certificateDetails[0].issuedTo,
                    issuedBy: notification.notificationDetails.certificateDetails[0].issuedBy,
                    partnerDomain: notification.notificationDetails.certificateDetails[0].partnerDomain,
                    expiryDateTime: formatDate(notification.notificationDetails.certificateDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );
    } else if (notification.notificationType === 'FTM_CHIP_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="viewAllNotifications.ftmChipCertExpiryDescription"
                values={{
                    make: notification.notificationDetails.ftmDetails[0].make,
                    model: notification.notificationDetails.ftmDetails[0].model,
                    ftmId: notification.notificationDetails.ftmDetails[0].ftmId,
                    expiryDateTime: formatDate(notification.notificationDetails.ftmDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );
    } else if (notification.notificationType === 'API_KEY_EXPIRY') {
        return (
            <Trans 
                i18nKey="viewAllNotifications.apiKeyExpiryDescription"
                values={{
                    apiKeyName: notification.notificationDetails.apiKeyDetails[0].apiKeyName,
                    policyName: notification.notificationDetails.apiKeyDetails[0].policyName,
                    expiryDateTime: formatDate(notification.notificationDetails.apiKeyDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );
    } else if (notification.notificationType === 'SBI_EXPIRY') {
        return (
            <Trans 
                i18nKey="viewAllNotifications.sbiExpiryDescription"
                values={{
                    sbiId: notification.notificationDetails.sbiDetails[0].sbiId,
                    sbiVersion: notification.notificationDetails.sbiDetails[0].sbiVersion,
                    expiryDateTime: formatDate(notification.notificationDetails.sbiDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );
    } else if (notification.notificationType === 'WEEKLY_SUMMARY') {
        return getWeeklySummaryDescription(notification, isLoginLanguageRTL, t);
    } else if (notification.notificationType === 'MISP_LICENSE_KEY_EXPIRY') {
        const mispLicenseKeyDetail = notification.notificationDetails?.mispLicenseKeyDetails?.[0] || null;
        return (
            <Trans 
                i18nKey="viewAllNotifications.mispLicenseKeyExpiryDescription"
                values={{
                    mispLicenseKeyName: mispLicenseKeyDetail?.mispLicenseKeyName || '-',
                    mispPartnerId: mispLicenseKeyDetail?.mispPartnerId || '-',
                    expiryDateTime: formatDate(mispLicenseKeyDetail?.expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className={`font-semibold md:whitespace-nowrap md:break-words break-all`} /> }}
            />
        );  
    }
};

export const getNotificationPanelDescription = (notification, isLoginLanguageRTL, t) => {
    if (notification.notificationType === 'ROOT_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationPopup.rootCertExpiryDescription"
                values={{
                    certificateId: notification.notificationDetails.certificateDetails[0].certificateId,
                    partnerDomain: notification.notificationDetails.certificateDetails[0].partnerDomain,
                    expiryDateTime: formatDate(notification.notificationDetails.certificateDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );
    } else if (notification.notificationType === 'INTERMEDIATE_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationPopup.intermediateCertExpiryDescription"
                values={{
                    certificateId: notification.notificationDetails.certificateDetails[0].certificateId,
                    partnerDomain: notification.notificationDetails.certificateDetails[0].partnerDomain,
                    expiryDateTime: formatDate(notification.notificationDetails.certificateDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );
    } else if (notification.notificationType === 'PARTNER_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationPopup.partnerCertExpiryDescription"
                values={{
                    partnerDomain: notification.notificationDetails.certificateDetails[0].partnerDomain,
                    expiryDateTime: formatDate(notification.notificationDetails.certificateDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );
    } else if (notification.notificationType === 'FTM_CHIP_CERT_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationPopup.ftmChipCertExpiryDescription"
                values={{
                    ftmId: notification.notificationDetails.ftmDetails[0].ftmId,
                    expiryDateTime: formatDate(notification.notificationDetails.ftmDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );
    } else if (notification.notificationType === 'API_KEY_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationPopup.apiKeyExpiryDescription"
                values={{
                    apiKeyName: notification.notificationDetails.apiKeyDetails[0].apiKeyName,
                    expiryDateTime: formatDate(notification.notificationDetails.apiKeyDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );
    } else if (notification.notificationType === 'SBI_EXPIRY') {
        return (
            <Trans 
                i18nKey="notificationPopup.sbiExpiryDescription"
                values={{
                    sbiId: notification.notificationDetails.sbiDetails[0].sbiId,
                    expiryDateTime: formatDate(notification.notificationDetails.sbiDetails[0].expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );
    } else if (notification.notificationType === 'WEEKLY_SUMMARY') {
        return getWeeklySummaryDescription (notification, isLoginLanguageRTL, t);
    } else if (notification.notificationType === 'MISP_LICENSE_KEY_EXPIRY') {
        const mispLicenseKeyDetail = notification.notificationDetails?.mispLicenseKeyDetails?.[0] || null;
        return (
            <Trans 
                i18nKey="notificationPopup.mispLicenseKeyExpiryDescription"
                values={{
                    mispLicenseKeyName: mispLicenseKeyDetail?.mispLicenseKeyName || '-',
                    mispPartnerId: mispLicenseKeyDetail?.mispPartnerId || '-',
                    expiryDateTime: formatDate(mispLicenseKeyDetail?.expiryDateTime, 'dateInWords')
                }}
                components={{ span: <span className="font-semibold break-words" /> }}
            />
        );  
    }
};

export const getNotificationTitle = (notification, t) => {
    if (notification.notificationType === 'ROOT_CERT_EXPIRY') {
        return t('notificationPopup.rootCertExpiry');
    } else if (notification.notificationType === 'INTERMEDIATE_CERT_EXPIRY') {
        return t('notificationPopup.intermediateCertExpiry');
    } else if (notification.notificationType === 'PARTNER_CERT_EXPIRY') {
        return t('notificationPopup.partnerCertExpiry');
    } else if (notification.notificationType === 'FTM_CHIP_CERT_EXPIRY') {
        return t('notificationPopup.ftmChipCertExpiry');
    } else if (notification.notificationType === 'API_KEY_EXPIRY') {
        return t('notificationPopup.apiKeyExpiry');
    } else if (notification.notificationType === 'SBI_EXPIRY') {
        return t('notificationPopup.sbiExpiry');
    } else if (notification.notificationType === 'WEEKLY_SUMMARY') {
        return t('notificationPopup.expiringItems') + " (" + formatDate(notification.createdDateTime, 'dateInWords') + t('notificationPopup.to') + formatDate(getWeeklySummaryDate(notification.createdDateTime), 'dateInWords') + ")";
    } else if (notification.notificationType === 'MISP_LICENSE_KEY_EXPIRY') {
        return t('notificationPopup.mispLicenseKeyExpiry');
    }
};

export const getWeeklySummaryDate = (createdDateTime) => {
    const date = new Date(createdDateTime);
    date.setDate(date.getDate() + 7);
    return date.toString();
};

export const dismissNotificationById = async (HttpService, id, setNotifications, fetchNotifications, setErrorCode, setErrorMsg, t) => {
    const request = createRequest({
        notificationStatus: "DISMISSED",
    }, "mosip.pms.dismiss.notification.patch", true);

    try {
        const response = await HttpService.patch(getPartnerManagerUrl(`/notifications/${id}`, process.env.NODE_ENV), request, {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                setNotifications((prevNotifications) =>
                    prevNotifications.filter((notif) => notif.notificationId !== id)
                );
                await fetchNotifications();
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } else {
            setErrorMsg(t('notificationPopup.errorInDismiss'));
        }
    } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
    }
};

export const fetchNotificationsList = async (dispatch) => {
    const queryParams = new URLSearchParams();
    queryParams.append('pageSize', 4);
    queryParams.append('pageNo', 0);
    queryParams.append('notificationStatus', 'active');
    const url = `${getPartnerManagerUrl('/notifications', process.env.NODE_ENV)}?${queryParams.toString()}`;
    try {
        const response = await HttpService.get(url);
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response.data;
                dispatch(updateHeaderNotifications(resData));
                return resData;
            } else {
                return [];
            }
        } else {
            return [];
        }
    } catch (err) {
        console.error('Error fetching data:', err);
        return [];
    }
};

export const fetchUserConsent = async () => {
    // Return immediately if consent was already confirmed in this session
    const userConsent = sessionStorage.getItem('userConsentGiven');
    if (userConsent === 'true') {
        return true;
    }

    // Consent not confirmed yet in this session, so verify from backend
    try {
        const response = await HttpService.get(
            getPartnerManagerUrl('/users/user-consent', process.env.NODE_ENV)
        );

        if (response?.data?.response) {
            const consentGiven = response.data.response.consentGiven;

            // Persist only a positive consent decision for the current session
            if (consentGiven) {
                sessionStorage.setItem('userConsentGiven', 'true');
            }

            return consentGiven;
        }

        return false;
    } catch (err) {
        console.error('Error fetching user consent:', err);
        return false;
    }
};


export const getOuterDivWidth = (text) => {
    if (text.length > 30) {
        return 'min-w-[21rem]';
    } else {
        return 'min-w-64';
    }
};

export const validateInputRegex = (input, setInputError, t) => {
    if (input === '' || validateInput(input)) {
        setInputError("");
    } else {
        setInputError(t('commons.inputError'));
    }
};

export const validateInput = (input) => {
    // Only allow letters (any language), digits, spaces, and .,@#&()-' characters
    const allowedPattern = /^[\p{L}\p{N}\p{M}\s.,@#&()\\\-_'?!":;=]+$/u;

    return allowedPattern.test(input);
}

export const validateUsernameRegex = (input, setInputError, t) => {
    if (input === '') {
        setInputError("");
        return;
    }
    
    // Check if username starts with a letter (any language)
    const startsWithLetter = /^[\p{L}]/u.test(input);
    
    if (!startsWithLetter) {
        setInputError(t('createPartner.usernameMustStartWithLetter'));
        return;
    }
    
    // Check if the rest of the username contains only valid characters
    const validPattern = /^[\p{L}][\p{L}\p{N}\p{M}._-]*$/u;
    
    if (validPattern.test(input)) {
        setInputError("");
    } else {
        setInputError(t('commons.inputError'));
    }
}

export const validateEmailRegex = (email, setEmailError, t) => {
    if (email === '') {
        setEmailError("");
        return;
    }
    
    // Email validation: alphanumeric characters and symbols ( . _ - +) are allowed. @ is mandatory
    const emailPattern = /^[\w-+]+(\.[\w]+)*@[\w-]+(\.[\w]+)*(\.[a-z]{2,})$/;
    
    if (emailPattern.test(email)) {
        setEmailError("");
    } else {
        setEmailError(t('createPartner.enterValidEmailAddress'));
    }
};

export const validateContactNumberRegex = (contactNumber, setContactNumberError, t) => {
    if (contactNumber === '') {
        setContactNumberError("");
        return;
    }
    
    // Contact number validation: only digits, spaces, hyphens, plus signs, and parentheses are allowed
    const contactNumberPattern = /^[0-9\s\-+()]+$/;
    
    if (contactNumberPattern.test(contactNumber)) {
        setContactNumberError("");
    } else {
        setContactNumberError(t('createPartner.enterValidContactNumber'));
    }
};

export const getFilterDropdownStyle = () => {
    const style = {
        dropdownButton: "min-w-64",
    };
    return style;
};

export const getFilterTextFieldStyle = () => {
    const styleSet = {
        inputField: "min-w-64",
        inputLabel: "mb-2",
        outerDiv: "ml-4 w-[16.5rem]"
    };
    return styleSet;
}

export const fetchPartnerDetails = async (HttpService, partnerId, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/admin-partners/${partnerId}`, process.env.NODE_ENV));
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response;
                return resData;
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                return null;
            }
        } else {
            setErrorMsg(t('viewPartnerDetails.errorInPartnerList'));
            return null;
        }
    } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
        return null;
    }
};

// OIDC Client Helper Functions
/**
 * Creates a new entry object for language-based fields (client name, purpose title/subtitle)
 */
export const createOidcClientEntry = (language, type) => {
    const baseId = uuidv4();
    let uniqueId;
    
    switch (type) {
        case 'clientName':
            uniqueId = `oidc_name_${baseId}`;
            break;
        case 'purposeTitle':
            uniqueId = `purpose_title_${baseId}`;
            break;
        case 'purposeSubtitle':
            uniqueId = `purpose_subtitle_${baseId}`;
            break;
        default:
            uniqueId = `entry_${type}_${baseId}`;
    }
    
    return {
        id: uniqueId,
        language: language,
        text: ''
    };
};

/**
 * Finds an available language from the dropdown data that hasn't been used
 */
export const findAvailableOidcLanguage = (usedLanguages, languageDropdownData) => {
    return languageDropdownData.find(lang => !usedLanguages.includes(lang.fieldValue));
};

/**
 * Validates entry text for language-based fields
 */
export const validateOidcEntryText = (value, entry, requiredErrorKey, errors, setErrors, t) => {
    const newErrors = { ...errors };
    let inputError = "";

    validateInputRegex(value, (error) => {
        inputError = error;
    }, t);

    // Determine which error to show (priority: required > regex)
    if (value.trim() === '') {
        newErrors[entry.id] = t(requiredErrorKey);
    } else if (inputError) {
        newErrors[entry.id] = inputError;
    } else {
        delete newErrors[entry.id];
    }
    setErrors(newErrors);
};

/**
 * Gets placeholder text based on language code and field type
 */
export const getOidcPlaceholderForLanguage = (languageCode, fieldType, t) => {
    if (!languageCode || languageCode === 'default') {
        const fallbackKey = `createOidcClient.enter${fieldType}Default`;
        return t(fallbackKey);
    }
    
    const langCode = languageCode.toLowerCase();
    
    // Use the new translation keys that have text in the target language
    const placeholderKey = `createOidcClient.enter${fieldType}In${langCode.charAt(0).toUpperCase() + langCode.slice(1)}`;
    const fallbackKey = `createOidcClient.enter${fieldType}Default`;
    
    // Get translation (all translation files have the same keys with target language text)
    let placeholder = t(placeholderKey);
    if (placeholder === placeholderKey) {
        placeholder = t(fallbackKey);
    }
    
    return placeholder;
};

/**
 * Gets available languages for a specific entry, excluding already used languages
 */
export const getAvailableOidcLanguages = (currentEntryId, entries, languageDropdownData, excludeDefault = false) => {
    const currentEntry = entries.find(e => e.id === currentEntryId);
    const currentLanguage = currentEntry ? currentEntry.language : '';
    const usedLanguages = entries
        .filter(e => e.id !== currentEntryId && e.language)
        .map(e => e.language);
    
    return languageDropdownData.filter(lang => {
        if (excludeDefault && lang.fieldValue === 'default') {
            return false;
        }
        return !usedLanguages.includes(lang.fieldValue) || lang.fieldValue === currentLanguage;
    });
};

/**
 * Initializes User Info Response Type dropdown data
 */
export const initializeUserInfoResponseTypeDropdown = (t) => {
    const userInfoResponseTypeData = [
        { fieldCode: t('createOidcClient.jws'), fieldValue: 'JWS' },
        { fieldCode: t('createOidcClient.jwe'), fieldValue: 'JWE' }
    ];
    // Add blank entry
    const dropdownData = createDropdownData("fieldValue", "", true, userInfoResponseTypeData, t, t("createOidcClient.selectUserInfoResponseType"));
    
    const finalData = dropdownData.map(item => {
        if (item.fieldValue === '') return item; // Keep blank entry as is
        const originalItem = userInfoResponseTypeData.find(d => d.fieldValue === item.fieldValue);
        return originalItem ? { ...item, fieldCode: originalItem.fieldCode } : item;
    });
    return finalData;
};

/**
 * Initializes Purpose Type dropdown data
 */
export const initializePurposeTypeDropdown = (t) => {
    const purposeTypeData = [
        { fieldCode: t('createOidcClient.login'), fieldValue: 'login' },
        { fieldCode: t('createOidcClient.link'), fieldValue: 'link' },
        { fieldCode: t('createOidcClient.verify'), fieldValue: 'verify' }
    ];
    // Add blank entry
    const dropdownData = createDropdownData("fieldValue", "", true, purposeTypeData, t, t("createOidcClient.selectPurposeType"));
    const finalData = dropdownData.map(item => {
        if (item.fieldValue === '') return item; // Keep blank entry as is
        const originalItem = purposeTypeData.find(d => d.fieldValue === item.fieldValue);
        return originalItem ? { ...item, fieldCode: originalItem.fieldCode } : item;
    });
    return finalData;
};

/**
 * Initializes Language dropdown data
 */
export const initializeLanguageDropdown = async (t) => {
    try {
        const appConfig = await getAppConfig();
        const supportedLanguages = appConfig && appConfig.supportedOidcLanguages;
        let languageCodes = [];
        if (Array.isArray(supportedLanguages)) {
            languageCodes = supportedLanguages;
        } else if (typeof supportedLanguages === 'string') {
            languageCodes = supportedLanguages.split(',').map(code => code.trim()).filter(code => code);
        }

        const languageData = languageCodes.map(langCode => ({
            languageCode: langCode,
            name: getLanguageDisplayName(langCode, t)
        }));

        // Add "Default" option at the beginning
        const defaultOption = { languageCode: 'default', name: t('createOidcClient.default') };
        const allLanguages = [defaultOption, ...languageData.map(lang => ({
            languageCode: lang.languageCode,
            name: lang.name
        }))];

        return createDropdownData('languageCode', 'name', false, allLanguages, t);
    } catch (err) {
        console.error('Error fetching languages:', err);
        return [];
    }
};
