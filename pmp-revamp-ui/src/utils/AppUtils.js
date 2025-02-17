import { HttpService } from "../services/HttpService";
import { getLoginRedirectUrl } from "../services/LoginRedirectService";

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
            return t('statusCodes.activated');
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
                    fieldCode: getPartnerTypeDescription(item[fieldName], t) || item[fieldName],
                    fieldValue: item[fieldName]
                });
            } else if (fieldName === "status" || fieldName === "certificateExpiryStatus" || fieldName === "certificateUploadStatus" || fieldName === "sbiExpiryStatus") {
                dataArr.push({
                    fieldCode: getStatusCode(item[fieldName], t) || item[fieldName],
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
        const response = await HttpService.get(getPartnerManagerUrl('/partners/' + partnerId + '/certificate-data', process.env.NODE_ENV));
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

export const getPolicyGroupList = async (HttpService, setPolicyGroupList, setErrorCode, setErrorMsg, t) => {
    try {
        const response = await HttpService({
            url: getPolicyManagerUrl('/policies/policy-groups', process.env.NODE_ENV),
            method: 'get',
            baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL
        });
        if (response) {
            const responseData = response.data;
            if (responseData && responseData.response) {
                const resData = responseData.response;
                setPolicyGroupList(createDropdownData('name', 'description', false, resData, t));
            } else {
                handleServiceErrors(responseData, setErrorCode, setErrorMsg);
            }
        } else {
            setErrorMsg(t('selectPolicyPopup.policyGroupError'));
        }
    } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
            setErrorMsg(err.toString());
        }
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
                setErrorMsg(t('createPolicy.jsonParseError'));
            }
        };
        reader.readAsText(file);
    } else {
        setErrorMsg(t('createPolicy.uploadFileError'));
    }
    event.target.value = '';
};

export const getClientNameEng = (clientName) => {
    try {
        const jsonObj = JSON.parse(clientName);
        if (jsonObj['eng']) {
            return jsonObj['eng'];
        }
        if (jsonObj['@none']) {
            return jsonObj['@none'];
        }
        // If neither "eng" nor "@none" is present, return the original string
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
            clientNameEng: getClientNameEng(item.clientName)
        };
    });
    return extractedList;
};

export const getClientNameLangMap = (clientNameEng, clientNameJson) => {
    try {
        const jsonObject = JSON.parse(clientNameJson);
        const newJsonObject = {};
        Object.keys(jsonObject).forEach(key => {
            if (key !== '@none') {
                newJsonObject[key] = clientNameEng;
            }
        });
        return newJsonObject;
    } catch {
        const newJsonObject = {
            eng: clientNameEng
        }
        return newJsonObject;
    }
}

export const getOidcClientDetails = async (HttpService, clientId, setErrorCode, setErrorMsg) => {
    try {
        const response = await HttpService.get(getPartnerManagerUrl(`/oauth/client/${clientId}`, process.env.NODE_ENV));
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



export const escapeKeyHandler = (closePopup) => {
    // Define the Escape key handler
    const handleEscape = (e) => {
    if (e.key === 'Escape') {
        closePopup()
        // Cleanup the event listener
        return window.removeEventListener('keydown', handleEscape)
    }
    };
    
    // Add event listener when any handler condition is true
    window.addEventListener('keydown', handleEscape);
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