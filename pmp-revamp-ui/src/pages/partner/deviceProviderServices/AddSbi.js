import { useState, useEffect } from "react";
import 'react-datepicker/dist/react-datepicker.css';
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../../common/fields/DropdownComponent';
import { getUserProfile } from '../../../services/UserProfileService';
import Title from "../../common/Title";
import {
    isLangRTL, getPartnerTypeDescription, moveToSbisList, getPartnerManagerUrl, createDropdownData,
    handleServiceErrors, createRequest, trimAndReplace
} from "../../../utils/AppUtils";
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import BlockerPrompt from "../../common/BlockerPrompt";
import CalendarInput from "../../common/CalendarInput";
import { HttpService } from "../../../services/HttpService";

function AddSbi() {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [partnerData, setPartnerData] = useState([]);
    const [partnerType, setPartnerType] = useState("");
    const [sbiVersion, setSbiVersion] = useState("");
    const [binaryHash, setBinaryHash] = useState("");
    const [partnerId, setPartnerId] = useState("");
    const [isCreateCalendarOpen, setIsCreateCalendarOpen] = useState(false);
    const [isExpiryCalenderOpen, setIsExpiryCalenderOpen] = useState(false);
    const [createdDate, setCreatedDate] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [IsSubmitClicked, setIsSubmitClicked] = useState(false);

    const navigate = useNavigate();

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (IsSubmitClicked) {
                setIsSubmitClicked(false);
                return false;
            }
            return (
                (partnerId !== "" || sbiVersion !== "" || binaryHash !== "") &&
                currentLocation.pathname !== nextLocation.pathname
            );
        }
    );

    useEffect(() => {
        const shouldWarnBeforeUnload = () => {
            return partnerId !== "" ||
                sbiVersion !== "" ||
                binaryHash !== "" ||
                createdDate !== "" ||
                expiryDate !== "";
        };

        const handleBeforeUnload = (event) => {
            if (shouldWarnBeforeUnload() && !IsSubmitClicked) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [partnerId, sbiVersion, binaryHash, createdDate, expiryDate, IsSubmitClicked]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setDataLoaded(false);
                const response = await HttpService.get(getPartnerManagerUrl('/partners/v3?status=approved&partnerType=Device_Provider', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
                        setPartnerData(resData);
                        setPartnerIdDropdownData(createDropdownData('partnerId', '', false, resData, t));
                    } else {
                        handleServiceErrors(response, setErrorCode, setErrorMsg);
                    }
                } else {
                    setErrorMsg(t('commons.errorInResponse'));
                }
                setDataLoaded(true);
            } catch (err) {
                console.error('Error fetching data:', err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
            }
        };
        fetchData();
    }, []);

    const onChangeSbiVersion = (value) => {
        setSbiVersion(value)
    };

    const onChangeBinaryHash = (value) => {
        setBinaryHash(value)
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const onChangePartnerId = async (fieldName, selectedValue) => {
        setPartnerId(selectedValue);
        const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
        if (selectedPartner) {
            setPartnerType(getPartnerTypeDescription("Device_Provider", t));
        }
    };

    const onHandleChangeCreateDate = (dateStr) => {
        console.log(`onHandleChangeCreateDate ${dateStr}`);
        setCreatedDate(dateStr);
    };

    const onHandleChangeExpiryDate = (dateStr) => {
        console.log(`onHandleChangeExpiryDate ${dateStr}`);
        setExpiryDate(dateStr);
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !mb-1",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
        selectionBox: "!top-10"
    };

    const clickOnSubmit = async () => {
        setIsSubmitClicked(true);
        setErrorCode("");
        setErrorMsg("");
        setDataLoaded(false);
        let request = createRequest(
            {
                swBinaryHash: binaryHash.trim(),
                swVersion: trimAndReplace(sbiVersion),
                swCreateDateTime: createdDate === "" ? new Date().toISOString() : createdDate,
                swExpiryDateTime: expiryDate === "" ? new Date().toISOString() : expiryDate,
                providerId: partnerId
            }
        );
        console.log(request);
        try {
            const response = await HttpService.post(getPartnerManagerUrl(`/securebiometricinterface`, process.env.NODE_ENV), request, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (response) {
                const responseData = response.data;
                console.log(responseData);
                if (responseData && responseData.response) {
                    navigate('/partnermanagement/device-provider-services/sbi-list')
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg('addSbis.errorInAddingSbi')
            }
            setDataLoaded(true);
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            console.log("Error fetching data: ", err);
        }
        setIsSubmitClicked(false);
    };

    const clearForm = () => {
        setPartnerId("");
        setPartnerType("");
        setSbiVersion("");
        setBinaryHash("");
        setCreatedDate("");
        setExpiryDate("");
    };

    const handleFormSubmit = (event) => {
        event.preventDefault();
    };

    const isFormValid = () => {
        return partnerId && sbiVersion.trim() && binaryHash.trim();
    };

    const clickOnCancel = () => {
        moveToSbisList(navigate)
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter max-[450px]:text-xs relative`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5 font-inter">
                        <div className="flex justify-between">
                            <Title title='addSbis.addSbiDetails' subTitle='sbiList.listOfSbi' backLink='/partnermanagement/device-provider-services/sbi-list' />
                        </div>
                        <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                            <div className="px-[2.5%] py-[2%]">
                                <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                                <div className="p-1 bg-[#FFF7E5] border-2 border-[#EDDCAF] rounded-md mt-[1%] w-full">
                                    <p className="text-sm text-[#8B6105]">{t('addSbis.guidence')}</p>
                                </div>
                                <form onSubmit={handleFormSubmit}>
                                    <div className="flex flex-col">
                                        <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <DropdownComponent
                                                    fieldName='partnerId'
                                                    dropdownDataList={partnerIdDropdownData}
                                                    onDropDownChangeEvent={onChangePartnerId}
                                                    fieldNameKey='requestPolicy.partnerId*'
                                                    placeHolderKey='createOidcClient.selectPartnerId'
                                                    selectedDropdownValue={partnerId}
                                                    styleSet={styles}
                                                    addInfoIcon
                                                    infoKey='addSbis.partnerIdTooltip'
                                                    id="add_sbi_partner_id"
                                                    >
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full min-h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                    <span className={`w-full break-words ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{partnerType || t('commons.partnersHelpText')}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex justify-between space-x-4 max-[450px]:space-x-0 max-[450px]:flex-col">
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addSbis.sbiVersion')} <span className="text-crimson-red">*</span></label>
                                                <input id="add_sbi_software_version_input" value={sbiVersion} onChange={(e) => onChangeSbiVersion(e.target.value)} maxLength={64}
                                                    className="h-10 w-full px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                    placeholder={t('addSbis.enterVersionOfSoftware')} />
                                            </div>
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addSbis.binaryHash')} <span className="text-crimson-red">*</span></label>
                                                <input id="binary_hash_input" value={binaryHash} onChange={(e) => onChangeBinaryHash(e.target.value)} maxLength={26}
                                                    className="h-10 w-full px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                    placeholder={t('addSbis.enterBinaryHash')} />
                                            </div>
                                        </div>

                                        <div className="flex justify-between space-x-4 max-[450px]:space-x-0 my-[1%]">
                                            <CalendarInput
                                                label={t('addSbis.sbiCreatedDate')}
                                                showCalendar={isCreateCalendarOpen}
                                                setShowCalender={setIsCreateCalendarOpen}
                                                onChange={onHandleChangeCreateDate}
                                                selectedDateStr={createdDate}
                                                addInfoIcon
                                                infoKey='addSbis.dateFormatInfoKey'
                                                containsAsterisk
                                                id="sbi_created_date_calender"
                                            />
                                            <CalendarInput
                                                label={t('addSbis.sbiExpiryDate')}
                                                showCalendar={isExpiryCalenderOpen}
                                                setShowCalender={setIsExpiryCalenderOpen}
                                                onChange={onHandleChangeExpiryDate}
                                                selectedDateStr={expiryDate}
                                                addInfoIcon
                                                infoKey='addSbis.expiryDateInfoKey'
                                                infoKey1='addSbis.dateFormatInfoKey'
                                                containsAsterisk
                                                id='sbi_expiry_date_calender'
                                            />
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row px-[3%] py-[2%] justify-between max-[500px]:flex-col max-[500px]:items-center">
                                <button id="add_sbi_form_clear_btn" onClick={() => clearForm()} className="mr-2 w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold max-[500px]:mb-2">{t('requestPolicy.clearForm')}</button>
                                <div className="flex flex-row space-x-3 w-full md:w-auto justify-between max-[500px]:flex-col max-[500px]:space-x-0 max-[500px]:items-center">
                                    <button id="add_sbi_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold max-[500px]:mb-2`}>{t('requestPolicy.cancel')}</button>
                                    <button id="add_sbi_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                                        {t('commons.submit')}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            )
            }
            <BlockerPrompt blocker={blocker} />
        </div >
    )
}

export default AddSbi;