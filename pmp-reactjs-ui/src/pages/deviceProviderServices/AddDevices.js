import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate, useBlocker } from "react-router-dom";
import { getUserProfile } from '../../services/UserProfileService.js';
import { HttpService } from '../../services/HttpService.js';
import Title from '../common/Title.js';
import { isLangRTL, createDropdownData, createRequest, getPartnerManagerUrl, handleServiceErrors, validateInput } from '../../utils/AppUtils.js';
import LoadingIcon from "../common/LoadingIcon.js";
import ErrorMessage from '../common/ErrorMessage.js';
import SuccessMessage from "../common/SuccessMessage";
import DropdownComponent from '../common/fields/DropdownComponent.js';
import WarningPopup from './WarningPopup.js';
import BlockerPrompt from "../common/BlockerPrompt";

function AddDevices() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [deviceEntries, setDeviceEntries] = useState([]);
    const [deviceTypeDropdownData, setDeviceTypeDropdownData] = useState([]);
    const [addDeviceEnabled, setAddDeviceEnabled] = useState(false);
    const [showPopup, setShowPopup] = useState(false);
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);
    const [isConfirmClicked, setIsConfirmClicked] = useState(false);
    const [previousPath, setPreviousPath] = useState(true);
    const [selectedSbidata, setSelectedSbidata] = useState(true);
    const [unexpectedError, setUnexpectedError] = useState(false);
    let isCancelledClicked = false;

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (isSubmitClicked || isCancelledClicked) {
                setIsSubmitClicked(false);
                isCancelledClicked = false;
                return false;
            }
            const checkValuesAreEntered = deviceEntries.some(entry => (
                (entry.deviceType !== "" ||
                entry.deviceSubType !== "" ||
                entry.make !== "" ||
                entry.model !== "") && !entry.isSubmitted
            ));
            return (
                checkValuesAreEntered &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            const checkValuesAreEntered = deviceEntries.some(entry => (
                entry.deviceType !== "" ||
                entry.deviceSubType !== "" ||
                entry.make !== "" ||
                entry.model !== ""
            ));
            return checkValuesAreEntered;
        };

        const handleBeforeUnload = (event) => {
            if (shouldWarnBeforeUnload() && !isConfirmClicked) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [deviceEntries, isConfirmClicked]);

    useEffect(() => {
        const selectedSbi = localStorage.getItem('selectedSbiData');
        const pathData = localStorage.getItem('previousPath');
        if (!selectedSbi) {
            setDataLoaded(true);
            setUnexpectedError(true);
            setErrorMsg(t('devicesList.unexpectedError'));
            return;
        }
        if (!pathData) {
            setErrorMsg(t('devicesList.unexpectedError'));
            return;
        }
        let sbiData = JSON.parse(selectedSbi);
        setSelectedSbidata(sbiData);
        let path = JSON.parse(pathData);
        setPreviousPath(path);
    }, []);

    useEffect(() => {
        async function initialize() {
            setDataLoaded(false);
            const deviceTypeData = await fetchDeviceTypeDropdownData();
            setDeviceTypeDropdownData(deviceTypeData);
            const initialEntry = await createEmptyDeviceEntry(deviceTypeData);
            setDeviceEntries([initialEntry]);
            setDataLoaded(true);
        }
        initialize();
    }, []);

    async function fetchDeviceTypeDropdownData() {
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
            setErrorMsg(err.message);
            console.log("Error fetching data: ", err);
            return [];
        }
    }

    async function fetchDeviceSubTypeDropdownData(type, index) {
        const newEntries = [...deviceEntries];
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
                        const errorCode = responseData.errors[0].errorCode;
                        const errorMessage = responseData.errors[0].message;
                        newEntries[index].errorCode = errorCode;
                        newEntries[index].errorMsg = errorMessage;
                        setDeviceEntries(newEntries);
                        console.error('Error:', errorMessage);
                    }
                    return [];
                }
            } else {
                newEntries[index].errorMsg = t('addDevices.errorInDeviceSubType');
                setDeviceEntries(newEntries);
                return [];
            }
        } catch (err) {
            newEntries[index].errorMsg = err;
            setDeviceEntries(newEntries);
            console.log("Error fetching data: ", err);
            return [];
        }
    }
    
    async function createEmptyDeviceEntry(deviceTypeData) {
        return {
            deviceType: "",
            deviceSubType: "",
            make: "",
            model: "",
            deviceTypeDropdownData: createDropdownData('fieldCode', '', false, deviceTypeData, t),
            deviceSubTypeDropdownData: [],
            isSubmitted: false,
            successMsg: "",
            errorCode: "",
            errorMsg: "",
            invalidMake: "",
            invalidModel: "",
        };
    }

    const handleInputChange = async (index, field, value) => {
        const newEntries = [...deviceEntries];
        newEntries[index][field] = value;
        if (field === 'make') {
            newEntries[index].invalidMake = validateInput(value, t);
        }
        if (field === 'model') {
            newEntries[index].invalidModel = validateInput(value, t);
        }
        if (field === 'deviceType') {
            const subtypeData = await fetchDeviceSubTypeDropdownData(value, index);
            newEntries[index].deviceSubTypeDropdownData = createDropdownData('fieldCode', '', false, subtypeData, t);
        }
        setDeviceEntries(newEntries);
        updateButtonStates();
    };

    const isFormValid = (index) => {
        const entry = deviceEntries[index];
        return entry.deviceType && entry.deviceSubType && entry.make && entry.model && !entry.invalidMake && !entry.invalidModel;
    };

    const clearForm = async (index) => {
        const newEntries = [...deviceEntries];
        newEntries[index] = await createEmptyDeviceEntry(deviceTypeDropdownData);
        setDeviceEntries(newEntries);
        updateButtonStates();
    };

    const submitForm = async (index, entry) => {
        const newEntries = [...deviceEntries];
        newEntries[index].successMsg = "";
        newEntries[index].errorMsg = "";
        newEntries[index].errorCode = "";
        setDeviceEntries(newEntries);
        setIsSubmitClicked(true);
        setDataLoaded(false);
        setErrorCode("");
        setErrorMsg("");
        const request = createRequest({
            id: null,
            deviceProviderId: getUserProfile().userName,
            deviceTypeCode: entry.deviceType,
            deviceSubTypeCode: entry.deviceSubType,
            make: entry.make.trim(),
            model: entry.model.trim()
        });
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/devicedetail`, process.env.NODE_ENV), request);
    
            if (response?.data?.response?.id) {
                addInactiveDeviceMappingToSbi(response.data.response.id, index);
            } else {
                const responseData = response.data;
                if (responseData && responseData.errors && responseData.errors.length > 0) {
                    const errorCode = responseData.errors[0].errorCode;
                    const errorMessage = responseData.errors[0].message;
                    newEntries[index].errorCode = errorCode;
                    newEntries[index].errorMsg = errorMessage;
                    setDeviceEntries(newEntries);
                    console.error('Error:', errorMessage);
                }
            }
        } catch (err) {
            newEntries[index].errorMsg = t('addDevices.errorInAddingDevice');
            setDeviceEntries(newEntries);
            console.error("Error fetching data: ", err);
        }
        setDataLoaded(true);
        setIsSubmitClicked(false);
    };
    
    const addInactiveDeviceMappingToSbi = async (deviceDetailId, index) => {
        const newEntries = [...deviceEntries];
        setDataLoaded(false);
        try {
            const request = createRequest({
                deviceDetailId: deviceDetailId,
                sbiId: selectedSbidata.sbiId,
                partnerId: selectedSbidata.partnerId
            }, "mosip.pms.add.inactive.device.mapping.to.sbi.id.post");
    
            const response = await HttpService.post(getPartnerManagerUrl(`/partners/addInactiveDeviceMappingToSbi`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    
            if (response?.data?.response) {
                newEntries[index].isSubmitted = true;
                newEntries[index].successMsg = t('addDevices.successMsg');
                setDeviceEntries(newEntries);
                updateButtonStates();
            } else {
                newEntries[index].errorMsg = t('addDevices.inActiveDeviceMappingToSbiError');
                setDeviceEntries(newEntries);
            }
        } catch (err) {
            newEntries[index].errorMsg = t('addDevices.inActiveDeviceMappingToSbiError');
            setDeviceEntries(newEntries);
            console.error('Error fetching data:', err);
        }
        setDataLoaded(true);
    };    

    const deleteEntry = (index) => {
        setErrorCode("");
        setErrorMsg("");
        const newEntries = deviceEntries.filter((_, i) => i !== index);
        setDeviceEntries(newEntries);
        updateButtonStates();
        setAddDeviceEnabled(true);
    };

    const updateButtonStates = () => {
        const allSubmitted = deviceEntries.every(entry => entry.isSubmitted);
        setAddDeviceEnabled(allSubmitted);
    };

    const addDeviceEntry = async () => {
        const newEntries = [...deviceEntries];
        newEntries[deviceEntries.length-1].successMsg = "";
        setDeviceEntries(newEntries);
        const allSubmitted = deviceEntries.every(entry => entry.isSubmitted);
        if (deviceEntries.length === 25 && allSubmitted) {
            setShowPopup(true);
        } else {
            const newEntry = await createEmptyDeviceEntry(deviceTypeDropdownData);
            setDeviceEntries([...deviceEntries, newEntry]);
            setAddDeviceEnabled(false);
        }

    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelError = (index) => {
        const newEntries = [...deviceEntries];
        newEntries[index].errorMsg = "";
        setDeviceEntries(newEntries);
    };

    const cancelSuccessMsg = (index) => {
        const newEntries = [...deviceEntries];
        newEntries[index].successMsg = "";
        setDeviceEntries(newEntries);
    };

    const clickOnBack = () => {
        if (previousPath.backToSbiList) {
            navigate('/partnermanagement/deviceProviderServices/sbiList');
        } else {
            navigate('/partnermanagement/deviceProviderServices/devicesList');
        }
    }

    const onClickConfirm = () => {
        setIsConfirmClicked(true);
    };

    useEffect(() => {
        if (isConfirmClicked) {
            window.location.reload();
        }
    }, [isConfirmClicked]);

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-base !mb-1",
        dropdownButton: "!w-full !h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title 
                                title='addDevices.addDevices' 
                                subTitle={previousPath.name} 
                                backLink={previousPath.path} 
                                status={!unexpectedError ? selectedSbidata.status : ''}
                                version={!unexpectedError ? selectedSbidata.sbiVersion : ''}
                                styleSet={styleForTitle}></Title>
                        </div>
                        <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                            <div className="flex items-center justify-center p-2">
                                <div className="p-2 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md w-full">
                                    <p className="text-sm font-medium text-[#8B6105]">{t('addDevices.guidence')}</p>
                                </div>
                            </div>
                        </div>
                        {deviceEntries.map((entry, index) => (
                            <div key={index} className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <div className="flex flex-col p-2">
                                    <div className={`flex justify-between ${entry.successMsg ? 'mb-16' : 'mb-2'} ${entry.errorMsg && 'mb-4'}`}>
                                        {!entry.isSubmitted && (
                                            <p className="text-base text-[#3D4468] px-6 py-2">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                        )}
                                        {entry.successMsg && (
                                            <div className={`flex justify-end max-w-7xl absolute ${isLoginLanguageRTL ? "left-0" : "right-6"}`}>
                                                <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-fruit-salad rounded-xl py-3 px-6 z-10">
                                                    <SuccessMessage successMsg={entry.successMsg} clickOnCancel={() => cancelSuccessMsg(index)}></SuccessMessage>
                                                </div>
                                            </div>
                                        )}
                                        {entry.errorMsg && (
                                            <div className={`flex justify-end max-w-7xl absolute ${isLoginLanguageRTL ? "left-0" : "right-6"}`}>
                                                <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
                                                    <ErrorMessage errorCode={entry.errorCode} errorMessage={entry.errorMsg} clickOnCancel={() => cancelError(index)}></ErrorMessage>
                                                </div>
                                            </div>
                                        )}
                                    </div>
                                    <form>
                                        <div className="flex justify-between max-[850px]:flex-wrap pl-5 pr-2 py-2">
                                            <div className="flex-col w-[23%] max-[850px]:w-[47%] max-[585px]:w-full">
                                                <DropdownComponent
                                                    fieldName='deviceType'
                                                    dropdownDataList={entry.deviceTypeDropdownData}
                                                    onDropDownChangeEvent={(fieldName, value) => handleInputChange(index, 'deviceType', value)}
                                                    fieldNameKey='addDevices.deviceType*'
                                                    placeHolderKey='addDevices.selectDeviceType'
                                                    selectedDropdownValue={entry.deviceType}
                                                    disabled={entry.isSubmitted}
                                                    changeDropdownBackground={entry.isSubmitted}
                                                    styleSet={styles}>
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex-col w-[23%] max-[850px]:w-[47%] max-[585px]:w-full">
                                                <DropdownComponent
                                                    fieldName='deviceSubType'
                                                    dropdownDataList={entry.deviceSubTypeDropdownData}
                                                    onDropDownChangeEvent={(fieldName, value) => handleInputChange(index, 'deviceSubType', value)}
                                                    fieldNameKey='addDevices.deviceSubType*'
                                                    placeHolderKey='addDevices.selectDeviceSubType'
                                                    selectedDropdownValue={entry.deviceSubType}
                                                    disabled={!entry.deviceType || entry.isSubmitted}
                                                    changeDropdownBackground={entry.isSubmitted}
                                                    styleSet={styles}>
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex flex-col w-[21.5%] max-[850px]:w-[47%] max-[585px]:w-full">
                                                <label className={`block text-dark-blue text-base font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.make')}<span className="text-crimson-red mx-1">*</span></label>
                                                <input disabled={entry.isSubmitted} value={entry.make} onChange={(e) => handleInputChange(index, 'make', e.target.value)} maxLength={36}
                                                    className={`h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue ${entry.isSubmitted ? 'bg-[#EBEBEB]' : 'bg-white'} leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
                                                    placeholder={t('addDevices.enterMake')} />
                                                {entry.invalidMake && <span className="text-sm text-crimson-red font-semibold">{entry.invalidMake}</span>}
                                            </div>
                                            <div className="flex flex-col w-[21.5%] max-[850px]:w-[47%] max-[585px]:w-full">
                                                <label className={`block text-dark-blue text-base font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.model')}<span className="text-crimson-red mx-1">*</span></label>
                                                <input disabled={entry.isSubmitted} value={entry.model} onChange={(e) => handleInputChange(index, 'model', e.target.value)} maxLength={36}
                                                    className={`h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue ${entry.isSubmitted ? 'bg-[#EBEBEB]' : 'bg-white'} leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
                                                    placeholder={t('addDevices.enterModel')} />
                                                {entry.invalidModel && <span className="text-sm text-crimson-red font-semibold">{entry.invalidModel}</span>}
                                            </div>
                                        </div>
                                    </form>
                                    {!entry.isSubmitted && (
                                        <div className="flex px-5 py-4 justify-between max-[400px]:flex-col">
                                            <div>
                                                <button disabled={!isFormValid(index)} onClick={() => submitForm(index, entry)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border-[#1447B2] border rounded-md text-sm font-semibold max-[600px]:mb-2 ${isFormValid(index) ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                                                    {t('addDevices.submit')}
                                                </button>
                                                <button onClick={() => clearForm(index)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>
                                                    {t('addDevices.clear')}
                                                </button>
                                            </div>
                                            <div className="flex flex-row items-center">
                                                <button onClick={() => deleteEntry(index)} disabled={index === 0 || entry.isSubmitted} className={`flex items-center ${index === 0 || entry.isSubmitted ? 'text-[#969696]' : 'text-tory-blue'} text-sm font-semibold max-[400px]:mt-2`}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="2" stroke={index === 0 || entry.isSubmitted ? '#969696' : '#1447b2'} className={`w-[18px] h-5 mr-1`}>
                                                        <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                                    </svg>
                                                    <span>{t('createOidcClient.delete')}</span>
                                                </button>
                                            </div>
                                        </div>
                                    )}
                                </div>
                            </div>
                        ))}
                        <div className=" w-full mt-6 border-2 bg-medium-gray"></div>
                        <div className="flex mt-3">
                            <button onClick={addDeviceEntry} disabled={!addDeviceEnabled} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border rounded-md text-sm font-semibold ${addDeviceEnabled ? 'border-[#1447B2] bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                                {t('addDevices.addDevice')}
                            </button>
                            {previousPath.backToSbiList ?
                                <button onClick={clickOnBack} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border rounded-md text-sm font-semibold border-[#1447B2] bg-tory-blue text-white`}>
                                    {t('addDevices.backToSBIList')}
                                </button>
                            :
                                <button onClick={clickOnBack} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border rounded-md text-sm font-semibold border-[#1447B2] bg-tory-blue text-white`}>
                                    {t('addDevices.backToViewDevices')}
                                </button>
                            }
                            {showPopup && (
                                <WarningPopup closePopUp={() => setShowPopup(false)} clickOnConfirm={onClickConfirm}></WarningPopup>
                            )}
                        </div>
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    )
}

export default AddDevices;
