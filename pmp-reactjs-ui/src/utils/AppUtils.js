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
        } else if (status === "inprogress") {
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

export const createDropdownDataList = (fieldName, dataList, t) => {
    let dataArr = [];
    dataArr.push({
        fieldCode: "",
        fieldValue: ""
    });
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
                if (item.descr) {
                    dataArr.push({
                        fieldCode: item[fieldName],
                        fieldValue: item[fieldName],
                        fieldDescription: item.descr
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

export const moveToPolicies = (navigate) => {
    navigate('/partnermanagement/policies')
};