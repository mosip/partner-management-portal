import React, { useEffect, useRef, useState } from 'react';
import { useNavigate, useBlocker } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import {
    getPartnerManagerUrl,
    handleMouseClickForDropdown,
    handleServiceErrors,
    isLangRTL,
    onPressEnterKey,
    validateInputRegex,
} from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import BlockerPrompt from '../../common/BlockerPrompt';
import Title from '../../common/Title';
import LoadingIcon from '../../common/LoadingIcon';
import ErrorMessage from '../../common/ErrorMessage';
import { HttpService } from '../../../services/HttpService';
import Confirmation from '../../common/Confirmation';

const LIST_ROUTE = '/partnermanagement/admin/biometric-provider-configuration/list';

const MODALITY_OPTIONS = [
    { value: 'FACE', labelKey: 'bioExtractorConfig.face' },
    { value: 'FINGER', labelKey: 'bioExtractorConfig.finger' },
    { value: 'IRIS', labelKey: 'bioExtractorConfig.iris' },
];

function CreateBioExtractorConfig() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const [dataLoaded, setDataLoaded] = useState(true);
    const [errorCode, setErrorCode] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [createSuccess, setCreateSuccess] = useState(false);
    const [confirmationData, setConfirmationData] = useState({});
    const [isSubmitClicked, setIsSubmitClicked] = useState(false);

    const [configName, setConfigName] = useState('');
    const [providerName, setProviderName] = useState('');
    const [providerVersion, setProviderVersion] = useState('');
    const [modality, setModality] = useState('');

    const [invalidConfigName, setInvalidConfigName] = useState('');
    const [invalidProviderName, setInvalidProviderName] = useState('');
    const [invalidProviderVersion, setInvalidProviderVersion] = useState('');

    const [modalityOpen, setModalityOpen] = useState(false);
    const modalityRef = useRef(null);

    useEffect(() => {
        return handleMouseClickForDropdown(modalityRef, () => setModalityOpen(false));
    }, []);

    const blocker = useBlocker(({ currentLocation, nextLocation }) => {
        if (isSubmitClicked || createSuccess) {
            setIsSubmitClicked(false);
            return false;
        }
        const isDirty =
            configName !== '' ||
            providerName !== '' ||
            providerVersion !== '' ||
            modality !== '';
        return isDirty && currentLocation.pathname !== nextLocation.pathname;
    });

    useEffect(() => {
        const isDirty = () =>
            configName !== '' ||
            providerName !== '' ||
            providerVersion !== '' ||
            modality !== '';

        const handleBeforeUnload = (event) => {
            if (isDirty() && !isSubmitClicked) {
                event.preventDefault();
                event.returnValue = '';
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);
        return () => window.removeEventListener('beforeunload', handleBeforeUnload);
    }, [configName, providerName, providerVersion, modality, isSubmitClicked]);

    const isFormValid = () =>
        configName.trim() &&
        providerName.trim() &&
        providerVersion.trim() &&
        modality &&
        !invalidConfigName &&
        !invalidProviderName &&
        !invalidProviderVersion;

    const onChangeConfigName = (value) => {
        setConfigName(value);
        validateInputRegex(value, setInvalidConfigName, t);
    };

    const onChangeProviderName = (value) => {
        setProviderName(value);
        validateInputRegex(value, setInvalidProviderName, t);
    };

    const onChangeProviderVersion = (value) => {
        setProviderVersion(value);
        validateInputRegex(value, setInvalidProviderVersion, t);
    };

    const clearForm = () => {
        setErrorCode('');
        setErrorMsg('');
        setConfigName('');
        setProviderName('');
        setProviderVersion('');
        setModality('');
        setInvalidConfigName('');
        setInvalidProviderName('');
        setInvalidProviderVersion('');
    };

    const clickOnCancel = () => {
        navigate(LIST_ROUTE);
    };

    const cancelErrorMsg = () => {
        setErrorMsg('');
    };

    const clickOnSubmit = async () => {
        setErrorCode('');
        setErrorMsg('');
        setDataLoaded(false);
        setIsSubmitClicked(true);

        try {
            const payload = {
                id: "mosip.pms.bioextractor.configurations.post",
                version: "1.0",
                requestTime: new Date().toISOString(),
                metadata: {},
                request: {
                    configName: configName.trim(),
                    bioextractorProviderName: providerName.trim(),
                    bioextractorProviderVersion: providerVersion.trim(),
                    bioModality: modality,
                },
            };

            let response = null;
            try {
                response = await HttpService.post(
                    getPartnerManagerUrl('/bio-extractor-configurations', process.env.NODE_ENV),
                    payload,
                    { headers: { 'Content-Type': 'application/json' } }
                );
            } catch (postErr) {
                if (postErr?.response?.status === 404) {
                    response = await HttpService.post(
                        getPartnerManagerUrl('/bioextractor/configurations', process.env.NODE_ENV),
                        payload,
                        { headers: { 'Content-Type': 'application/json' } }
                    );
                } else {
                    throw postErr;
                }
            }

            if (response && response.data) {
                const responseData = response.data;
                if (responseData.response) {
                    setConfirmationData({
                        backUrl: LIST_ROUTE,
                        header: 'bioExtractorConfig.createConfigSuccessHeader',
                    });
                    setCreateSuccess(true);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('bioExtractorConfig.errorInCreateConfig'));
            }
        } catch (err) {
            if (err.response?.status && err.response.status !== 401) {
                const status = err.response?.status;
                const url = err.config?.url;
                setErrorMsg(url ? `${err.toString()} (url: ${url})` : err.toString());
            }
            console.error('Error creating biometric extractor config:', err);
        }

        setDataLoaded(true);
        setIsSubmitClicked(false);
    };

    const handleFormSubmit = (event) => {
        event.preventDefault();
    };

    const getModalityLabel = (value) => {
        const option = MODALITY_OPTIONS.find((o) => o.value === value);
        return option ? t(option.labelKey) : '';
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? 'mr-28 ml-5' : 'ml-28 mr-5'} overflow-x-scroll font-inter`}>
            {!dataLoaded && <LoadingIcon />}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage
                            id='create_bio_extractor_config_error_msg'
                            errorCode={errorCode}
                            errorMessage={errorMsg}
                            clickOnCancel={cancelErrorMsg}
                        />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between">
                            <Title
                                title='bioExtractorConfig.createBioExtractorConfig'
                                subTitle='dashboard.biometricProviderConfiguration'
                                backLink={LIST_ROUTE}
                            />
                        </div>

                        {!createSuccess ? (
                            <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                                <div className="px-[2.5%] py-[2%]">
                                    <p
                                        id='create_bio_extractor_config_mandatory_msg'
                                        className="text-base text-[#3D4468]"
                                    >
                                        {t('requestPolicy.mandatoryFieldsMsg1')}{' '}
                                        <span className="text-crimson-red">*</span>{' '}
                                        {t('requestPolicy.mandatoryFieldsMsg2')}
                                    </p>

                                    <form onSubmit={handleFormSubmit}>
                                        <div className="flex flex-col">

                                            <div
                                                className={`flex flex-row justify-between space-x-4 ${isLoginLanguageRTL && 'space-x-reverse'} my-4 max-[450px]:flex-col max-[450px]:space-x-0 max-[450px]:space-y-4`}
                                            >
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <label
                                                        id='config_name_label'
                                                        className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? 'mr-1' : 'ml-1'}`}
                                                    >
                                                        {t('bioExtractorConfig.configurationName')}
                                                        <span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <input
                                                        id='config_name_input'
                                                        value={configName}
                                                        onChange={(e) => onChangeConfigName(e.target.value)}
                                                        maxLength={128}
                                                        className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                        placeholder={t('bioExtractorConfig.enterConfigurationName')}
                                                        data-placeholder-id="config_name_placeholder"
                                                    />
                                                    {invalidConfigName && (
                                                        <span
                                                            id='config_name_invalid_error'
                                                            className="text-sm text-crimson-red font-semibold mt-1"
                                                        >
                                                            {invalidConfigName}
                                                        </span>
                                                    )}
                                                </div>

                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <label
                                                        id='provider_name_label'
                                                        className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? 'mr-1' : 'ml-1'}`}
                                                    >
                                                        {t('bioExtractorConfig.providerName')}
                                                        <span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <input
                                                        id='provider_name_input'
                                                        value={providerName}
                                                        onChange={(e) => onChangeProviderName(e.target.value)}
                                                        maxLength={128}
                                                        className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                        placeholder={t('bioExtractorConfig.enterProviderName')}
                                                        data-placeholder-id="provider_name_placeholder"
                                                    />
                                                    {invalidProviderName && (
                                                        <span
                                                            id='provider_name_invalid_error'
                                                            className="text-sm text-crimson-red font-semibold mt-1"
                                                        >
                                                            {invalidProviderName}
                                                        </span>
                                                    )}
                                                </div>
                                            </div>

                                            <div
                                                className={`flex flex-row justify-between space-x-4 ${isLoginLanguageRTL && 'space-x-reverse'} my-4 max-[450px]:flex-col max-[450px]:space-x-0 max-[450px]:space-y-4`}
                                            >
                                                <div className="flex flex-col w-[48%] max-[450px]:w-full">
                                                    <label
                                                        id='provider_version_label'
                                                        className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? 'mr-1' : 'ml-1'}`}
                                                    >
                                                        {t('bioExtractorConfig.providerVersion')}
                                                        <span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <input
                                                        id='provider_version_input'
                                                        value={providerVersion}
                                                        onChange={(e) => onChangeProviderVersion(e.target.value)}
                                                        maxLength={64}
                                                        className="h-10 px-2 py-3 border border-[#707070] rounded-md text-base text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                                                        placeholder={t('bioExtractorConfig.enterProviderVersion')}
                                                        data-placeholder-id="provider_version_placeholder"
                                                    />
                                                    {invalidProviderVersion && (
                                                        <span
                                                            id='provider_version_invalid_error'
                                                            className="text-sm text-crimson-red font-semibold mt-1"
                                                        >
                                                            {invalidProviderVersion}
                                                        </span>
                                                    )}
                                                </div>

                                                <div
                                                    ref={modalityRef}
                                                    className="flex flex-col w-[48%] max-[450px]:w-full relative"
                                                >
                                                    <label
                                                        id='modality_label'
                                                        className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? 'mr-1' : 'ml-1'}`}
                                                    >
                                                        {t('bioExtractorConfig.biometricModality')}
                                                        <span className="text-crimson-red mx-1">*</span>
                                                    </label>
                                                    <button
                                                        id='modality_dropdown_btn'
                                                        type="button"
                                                        onClick={() => setModalityOpen((prev) => !prev)}
                                                        onKeyDown={(e) =>
                                                            onPressEnterKey(e, () => setModalityOpen((prev) => !prev))
                                                        }
                                                        aria-haspopup="listbox"
                                                        aria-expanded={modalityOpen}
                                                        className="flex items-center justify-between h-10 px-2 py-2 border border-[#707070] rounded-md text-base bg-white leading-tight focus:outline-none focus:shadow-outline"
                                                    >
                                                        <span className={modality ? 'text-dark-blue' : 'text-gray-400'}>
                                                            {modality
                                                                ? getModalityLabel(modality)
                                                                : t('bioExtractorConfig.selectBiometricModality')}
                                                        </span>
                                                        <svg
                                                            className={`w-3 h-2 ${isLoginLanguageRTL ? 'mr-3' : 'ml-3'} transform ${modalityOpen ? 'rotate-180' : 'rotate-0'} text-gray-500 flex-shrink-0`}
                                                            xmlns="http://www.w3.org/2000/svg"
                                                            fill="none"
                                                            viewBox="0 0 10 6"
                                                        >
                                                            <path
                                                                stroke="currentColor"
                                                                strokeLinecap="round"
                                                                strokeLinejoin="round"
                                                                strokeWidth="2"
                                                                d="m1 1 4 4 4-4"
                                                            />
                                                        </svg>
                                                    </button>
                                                    {modalityOpen && (
                                                        <div
                                                            id='modality_dropdown_options'
                                                            role="listbox"
                                                            className="absolute top-[4.5rem] z-10 w-full bg-white border border-[#707070] rounded-md shadow-lg"
                                                        >
                                                            {MODALITY_OPTIONS.map((option) => (
                                                                <button
                                                                    key={option.value}
                                                                    id={`modality_option_${option.value.toLowerCase()}`}
                                                                    type="button"
                                                                    role="option"
                                                                    aria-selected={modality === option.value}
                                                                    onClick={() => {
                                                                        setModality(option.value);
                                                                        setModalityOpen(false);
                                                                    }}
                                                                    onKeyDown={(e) =>
                                                                        onPressEnterKey(e, () => {
                                                                            setModality(option.value);
                                                                            setModalityOpen(false);
                                                                        })
                                                                    }
                                                                    className={`w-full text-left px-4 py-2 text-sm hover:bg-[#F0F5FF] ${
                                                                        modality === option.value
                                                                            ? 'bg-[#F0F5FF] text-tory-blue font-semibold'
                                                                            : 'text-dark-blue'
                                                                    }`}
                                                                >
                                                                    {t(option.labelKey)}
                                                                </button>
                                                            ))}
                                                        </div>
                                                    )}
                                                </div>
                                            </div>

                                        </div>
                                    </form>
                                </div>

                                <div className="border bg-medium-gray" />

                                <div className="flex flex-row max-[450px]:flex-col px-[3%] py-9 justify-between max-[450px]:space-y-2">
                                    <button
                                        id='create_bio_extractor_config_clear_form_btn'
                                        type="button"
                                        onClick={() => clearForm()}
                                        onKeyDown={(e) => onPressEnterKey(e, clearForm)}
                                        className={`w-40 h-10 border-[#1447B2] ${isLoginLanguageRTL ? 'mr-2' : 'ml-2'} border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                                        tabIndex="0"
                                    >
                                        {t('requestPolicy.clearForm')}
                                    </button>
                                    <div
                                        className={`flex flex-row max-[450px]:flex-col space-x-3 ${isLoginLanguageRTL && 'space-x-reverse'} max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end`}
                                    >
                                        <button
                                            id='create_bio_extractor_config_cancel_btn'
                                            type="button"
                                            onClick={() => clickOnCancel()}
                                            onKeyDown={(e) => onPressEnterKey(e, clickOnCancel)}
                                            className={`${isLoginLanguageRTL ? 'ml-2' : 'mr-2'} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}
                                        >
                                            {t('requestPolicy.cancel')}
                                        </button>
                                        <button
                                            id='create_bio_extractor_config_submit_btn'
                                            type="button"
                                            disabled={!isFormValid()}
                                            onClick={() => clickOnSubmit()}
                                            onKeyDown={(e) => onPressEnterKey(e, clickOnSubmit)}
                                            className={`${isLoginLanguageRTL ? 'ml-2' : 'mr-2'} w-11/12 md:w-40 h-10 border rounded-md text-sm font-semibold ${
                                                isFormValid()
                                                    ? 'bg-tory-blue border-[#1447B2] text-white'
                                                    : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'
                                            }`}
                                        >
                                            {t('requestPolicy.submit')}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ) : (
                            <Confirmation
                                id='create_bio_extractor_config_confirmation'
                                confirmationData={confirmationData}
                            />
                        )}
                    </div>
                </>
            )}
            <BlockerPrompt blocker={blocker} />
        </div>
    );
}

export default CreateBioExtractorConfig;
