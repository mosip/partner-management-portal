import { useState, useEffect } from "react";
import 'react-calendar/dist/Calendar.css';
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import Title from "../common/Title";
import {isLangRTL, getPartnerTypeDescription, moveToSbisList, getPartnerManagerUrl, createDropdownData,
    handleServiceErrors, createRequest} from "../../utils/AppUtils";
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import BlockerPrompt from "../common/BlockerPrompt";
import CalendarInput from "../common/CalendarInput";
import { HttpService } from "../../services/HttpService";

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
    const [createdDate, setCreatedDate] = useState(new Date());
    const [expiryDate, setExpiryDate] = useState(new Date());
    const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
    const [IsSubmitClicked, setIsSubmitClicked] = useState(false);

    let isCancelledClicked = false;

    const navigate = useNavigate();

    const blocker = useBlocker(
        ({ currentLocation, nextLocation }) => {
            if (IsSubmitClicked || isCancelledClicked) {
                isCancelledClicked = false;
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
                const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllApprovedDeviceProviderIds', process.env.NODE_ENV));
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        const resData = responseData.response;
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
                setErrorMsg(err);
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
            setPartnerType(getPartnerTypeDescription(selectedPartner.partnerType, t));
        }
    };

    const onHandleChangeCreateDate = (date) => {
        setCreatedDate(date);
    };

    const onHandleChangeExpiryDate = (date) => {
        setExpiryDate(date);
    };

    const styleForTitle = {
        backArrowIcon: "!mt-[4%]"
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
                swBinaryHash: binaryHash,
                swVersion: sbiVersion,
                swCreateDateTime: createdDate,
                swExpiryDateTime: expiryDate,
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
                    navigate('/partnermanagement/deviceProviderServices/sbiList')
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg('addSbis.errorInAddingSbi')
            }
            setDataLoaded(true);
        } catch (err) {
            setErrorMsg(err);
            console.log("Error fetching data: ", err);
        }
        setIsSubmitClicked(false);
    };

    const clearForm = () => {
        setPartnerId("");
        setPartnerType("");
        setSbiVersion("");
        setBinaryHash("");
        setCreatedDate(new Date());
        setExpiryDate(new Date());
    };

    const handleFormSubmit = (event) => {
        event.preventDefault();
    };

    const isFormValid = () => {
        return partnerId && sbiVersion && binaryHash
    };

    const clickOnCancel = () => {
        isCancelledClicked = true;
        moveToSbisList(navigate)
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter max-[450px]:text-xs`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <div className={`flex justify-end max-w-7xl sm:max-w-xl mb-5 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
                            <div className="flex justify-between items-center max-[800px]:w- min-h-14  max-[450px]:min-w-40 max-[450px]:min-h-40 bg-[#C61818] rounded-xl p-3">
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
                            </div>
                        </div>
                    )}
                    <div className="flex-col mt-7 font-inter">
                        <div className="flex justify-between">
                            <Title title='addSbis.addSbi' subTitle='deviceProviderServices.listOfSbisAndDevices' backLink='/partnermanagement/deviceProviderServices/sbiList' styleSet={styleForTitle}></Title>
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
                                                    infoKey='addSbis.partnerIdTooltip'>
                                                </DropdownComponent>
                                            </div>
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                                                <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                                                    overflow-x-auto whitespace-normal no-scrollbar" type="button">
                                                    <span className="w-full break-all break-normal break-words text-wrap text-start">{partnerType || t("partnerTypes.deviceProvider")}</span>
                                                    <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="flex justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addSbis.sbiVersion')}</label>
                                                <input value={sbiVersion} onChange={(e) => onChangeSbiVersion(e.target.value)} maxLength={64}
                                                    className="h-10 w-full px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                    placeholder={t('addSbis.enterVersionOfSoftware')} />
                                            </div>
                                            <div className="flex-col w-[48%] max-[450px]:w-full">
                                                <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addSbis.binaryHash')}</label>
                                                <input value={binaryHash} onChange={(e) => onChangeBinaryHash(e.target.value)} maxLength={36}
                                                    className="h-10 w-full px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                    placeholder={t('addSbis.enterBinaryHash')} />
                                            </div>
                                        </div>

                                        <div className="flex justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                                            <CalendarInput
                                                label={t('addSbis.sbiCreatedDate')}
                                                showCalendar={isCreateCalendarOpen}
                                                setShowCalender={setIsCreateCalendarOpen}
                                                onChange={onHandleChangeCreateDate}
                                                value={createdDate}
                                                styles={`absolute rounded-lg bg-white shadow-lg -mt-[24%] ${isLoginLanguageRTL ? "mr-56" : "ml-56"} w-auto h-auto`}
                                            />
                                            <CalendarInput
                                                label={t('addSbis.sbiExpiryDate')}
                                                showCalendar={isExpiryCalenderOpen}
                                                setShowCalender={setIsExpiryCalenderOpen}
                                                onChange={onHandleChangeExpiryDate}
                                                value={expiryDate}
                                                addInfoIcon
                                                styles={`absolute rounded-lg bg-white shadow-lg -mt-[24%] ${isLoginLanguageRTL ? "mr-56" : "ml-56"} w-auto h-auto`}
                                            />
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div className="border bg-medium-gray" />
                            <div className="flex flex-row px-[3%] py-[2%] justify-between max-[500px]:flex-col">
                                <button onClick={() => clearForm()} className="mr-2 w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold">{t('requestPolicy.clearForm')}</button>
                                <div className="flex flex-row space-x-3 w-full md:w-auto justify-between max-[500px]:flex-col">
                                    <button onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                                    <button disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
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