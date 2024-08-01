import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from "react-router-dom";
import { getUserProfile } from '../../services/UserProfileService.js';
import Title from '../common/Title.js';
import { isLangRTL, createDropdownData } from '../../utils/AppUtils.js';
import LoadingIcon from "../common/LoadingIcon.js";
import ErrorMessage from '../common/ErrorMessage.js';
import SuccessMessage from "../common/SuccessMessage";
import DropdownComponent from '../common/fields/DropdownComponent.js';

function AddDevices() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [deviceEntries, setDeviceEntries] = useState([createEmptyDeviceEntry()]);
    const [addDeviceEnabled, setAddDeviceEnabled] = useState(false);
    const [backToSBIListEnabled, setBackToSBIListEnabled] = useState(false);

    function createEmptyDeviceEntry() {
        const resData = [{ "deviceType": "Finger" }, { "deviceType": "Face" }, { "deviceType": "Iris" }];
        return {
            deviceType: "",
            deviceSubType: "",
            make: "",
            model: "",
            deviceTypeDropdownData: createDropdownData('deviceType', '', false, resData, t),
            deviceSubTypeDropdownData: [],
            isSubmitted: false,
        };
    }

    const handleInputChange = (index, field, value) => {
        const newEntries = [...deviceEntries];
        newEntries[index][field] = value;
        if (field === 'deviceType') {
            createDeviceSubTypeList(index, value);
        }
        setDeviceEntries(newEntries);
        updateButtonStates();
    };

    const createDeviceSubTypeList = (index, type) => {
        let deviceSubTypeList = [];
        if (type === "Finger") {
            deviceSubTypeList = [{ "deviceSubType": "Slap" }, { "deviceSubType": "Single" }, { "deviceSubType": "Touchless" }];
        }
        if (type === "Face") {
            deviceSubTypeList = [{ "deviceSubType": "Full face" }];
        }
        if (type === "Iris") {
            deviceSubTypeList = [{ "deviceSubType": "Single" }, { "deviceSubType": "Double" }];
        }
        const newEntries = [...deviceEntries];
        newEntries[index].deviceSubTypeDropdownData = createDropdownData('deviceSubType', '', false, deviceSubTypeList, t);
        setDeviceEntries(newEntries);
    };

    const isFormValid = (index) => {
        const entry = deviceEntries[index];
        return entry.deviceType && entry.deviceSubType && entry.make && entry.model;
    };

    const clearForm = (index) => {
        const newEntries = [...deviceEntries];
        newEntries[index] = createEmptyDeviceEntry();
        setDeviceEntries(newEntries);
        updateButtonStates();
    };

    const submitForm = (index) => {
        const newEntries = [...deviceEntries];
        newEntries[index].isSubmitted = true;
        setDeviceEntries(newEntries);
        setSuccessMsg(t('addDevices.successMsg'));
        updateButtonStates();
    };

    const deleteEntry = (index) => {
        const newEntries = deviceEntries.filter((_, i) => i !== index);
        setDeviceEntries(newEntries);
        updateButtonStates();
    };

    const updateButtonStates = () => {
        const anySubmitted = deviceEntries.some(entry => entry.isSubmitted);
        setAddDeviceEnabled(anySubmitted);
        setBackToSBIListEnabled(anySubmitted);
    };

    const addDeviceEntry = () => {
        setSuccessMsg("");
        setDeviceEntries([...deviceEntries, createEmptyDeviceEntry()]);
        setAddDeviceEnabled(false);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    const clickOnBack = () => {
        navigate('/partnermanagement/deviceProviderServices/sbiList');
    }

    const styleForTitle = {
        backArrowIcon: "!mt-[5%]"
    };

    const styles = {
        dropdownLabel: "!text-base !mb-1",
        dropdownButton: "!min-w-[19.5rem] !h-11 !rounded-md !text-base !text-start",
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
                    {successMsg && (
                        <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-fruit-salad rounded-xl py-3 px-6 z-10">
                                <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg}></SuccessMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7">
                        <div className="flex justify-between mb-5">
                            <Title title='addDevices.addDevices' subTitle='deviceProviderServices.listOfSbisAndDevices' backLink='/partnermanagement/deviceProviderServices/sbiList' styleSet={styleForTitle}></Title>
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
                                    {!entry.isSubmitted && (
                                        <p className="text-base text-[#3D4468] px-6 py-2">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                    )}
                                    <form>
                                        <div className="flex justify-start flex-wrap p-2">
                                            <div className="flex-col">
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
                                            <div className="flex-col">
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
                                            <div className="flex flex-col min-w-[19.5rem] ml-4 mb-2">
                                                <label className={`block text-dark-blue text-base font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.make')}<span className="text-crimson-red mx-1">*</span></label>
                                                <input disabled={entry.isSubmitted} value={entry.make} onChange={(e) => handleInputChange(index, 'make', e.target.value)} maxLength={36}
                                                    className={`h-11 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue ${entry.isSubmitted ? 'bg-[#EBEBEB]' : 'bg-white'} leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
                                                    placeholder={t('addDevices.enterMake')} />
                                            </div>
                                            <div className="flex flex-col min-w-[19.5rem] ml-4 mb-2">
                                                <label className={`block text-dark-blue text-base font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.model')}<span className="text-crimson-red mx-1">*</span></label>
                                                <input disabled={entry.isSubmitted} value={entry.model} onChange={(e) => handleInputChange(index, 'model', e.target.value)} maxLength={36}
                                                    className={`h-11 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue ${entry.isSubmitted ? 'bg-[#EBEBEB]' : 'bg-white'} leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar`}
                                                    placeholder={t('addDevices.enterModel')} />
                                            </div>
                                        </div>
                                    </form>
                                    {!entry.isSubmitted && (
                                        <div className="flex px-5 py-4 justify-between">
                                            <div>
                                                <button disabled={!isFormValid(index)} onClick={() => submitForm(index)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid(index) ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                                                    {t('addDevices.submit')}
                                                </button>
                                                <button onClick={() => clearForm(index)} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>
                                                    {t('addDevices.clear')}
                                                </button>
                                            </div>
                                            <div className="flex flex-row items-center">
                                                <button onClick={() => deleteEntry(index)} disabled={index === 0 || entry.isSubmitted} className={`flex items-center ${index === 0 || entry.isSubmitted ? 'text-[#969696]' : 'text-tory-blue'} text-sm font-semibold`}>
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
                            {backToSBIListEnabled && (
                                <button onClick={clickOnBack} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-36 h-11 border rounded-md text-sm font-semibold border-[#1447B2] bg-tory-blue text-white`}>
                                    {t('addDevices.backToSBIList')}
                                </button>
                            )}
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}

export default AddDevices;