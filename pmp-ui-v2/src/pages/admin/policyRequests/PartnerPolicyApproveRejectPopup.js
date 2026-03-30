import React, { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import FocusTrap from 'focus-trap-react';
import PropTypes from 'prop-types';
import { HttpService } from '../../../services/HttpService';
import {
  createRequest,
  getPartnerManagerUrl,
  getPartnerTypeDescription,
  handleEscapeKey,
  handleServiceErrors,
  isLangRTL,
} from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';
import LoadingIcon from '../../common/LoadingIcon';
import ErrorMessage from '../../common/ErrorMessage';
import close_icon from '../../../svg/close_icon.svg';

function PartnerPolicyApproveRejectPopup({
  popupData,
  closePopUp,
  approveRejectResponse,
  title,
  subtitle,
  header,
  description,
}) {
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

  const [errorCode, setErrorCode] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const [dataLoaded, setDataLoaded] = useState(true);

  const [bioLoading, setBioLoading] = useState(false);
  const [bioError, setBioError] = useState('');
  const [bioExtractors, setBioExtractors] = useState([]);

  const [credentialTypeLoading, setCredentialTypeLoading] = useState(false);
  const [credentialTypeError, setCredentialTypeError] = useState('');
  const [credentialType, setCredentialType] = useState('');

  const isCredentialPartner = useMemo(() => {
    const partnerType = (popupData?.partnerType ?? '').toString().toUpperCase();
    return partnerType === 'CREDENTIAL_PARTNER';
  }, [popupData?.partnerType]);

  useEffect(() => {
    document.body.style.overflow = 'hidden';
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, []);

  useEffect(() => {
    const removeListener = handleEscapeKey(() => closePopUp());
    return removeListener;
  }, [closePopUp]);

  useEffect(() => {
    const fetchBioExtractors = async () => {
      if (!isCredentialPartner) return;
      if (!popupData?.partnerId || !popupData?.policyId) return;

      setBioError('');
      setBioLoading(true);
      try {
        const url = getPartnerManagerUrl(
          `/partners/${popupData.partnerId}/bioextractors/${popupData.policyId}`,
          process.env.NODE_ENV
        );
        const response = await HttpService.get(url);
        const responseData = response?.data;
        if (responseData?.response) {
          const payload = responseData.response;
          const list = Array.isArray(payload)
            ? payload
            : (payload.extractors ??
                payload.data ??
                payload.bioExtractors ??
                payload.bioextractors ??
                payload.extractorList ??
                []);
          setBioExtractors(Array.isArray(list) ? list : []);
        } else {
          setBioExtractors([]);
          if (responseData) {
            handleServiceErrors(responseData, setErrorCode, setErrorMsg);
          } else {
            setBioError(t('commons.somethingWentWrong', 'Something went wrong.'));
          }
        }
      } catch (err) {
        if (err?.response?.status !== 401) {
          setBioExtractors([]);
          setBioError(err?.message || err?.toString() || t('commons.somethingWentWrong', 'Something went wrong.'));
        }
      } finally {
        setBioLoading(false);
      }
    };

    fetchBioExtractors();
  }, [isCredentialPartner, popupData?.partnerId, popupData?.policyId, t]);

  useEffect(() => {
    const fetchCredentialType = async () => {
      if (!isCredentialPartner) return;
      if (!popupData?.partnerId || !popupData?.policyId) return;

      setCredentialTypeError('');
      setCredentialType('');
      setCredentialTypeLoading(true);
      try {
        const url = getPartnerManagerUrl(
          `/partners/${popupData.partnerId}/policies/${popupData.policyId}/credential-type`,
          process.env.NODE_ENV
        );
        const response = await HttpService.get(url);
        const responseData = response?.data;

        if (responseData?.response !== undefined) {
          const payload = responseData.response;
          const value =
            (typeof payload === 'string' ? payload : null) ??
            payload?.credentialType ??
            payload?.credential_type ??
            payload?.type ??
            payload?.data?.credentialType ??
            payload?.data?.credential_type ??
            '';

          const types =
            payload?.credentialTypes ??
            payload?.credential_types ??
            payload?.data?.credentialTypes ??
            payload?.data?.credential_types ??
            null;

          if (Array.isArray(types)) {
            const first = types.find((x) => x !== null && x !== undefined && String(x).trim() !== '');
            setCredentialType(first ? String(first) : '');
          } else {
            setCredentialType(value ? String(value) : '');
          }
        } else if (responseData) {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        } else {
          setCredentialTypeError(t('commons.somethingWentWrong', 'Something went wrong.'));
        }
      } catch (err) {
        if (err?.response?.status !== 401) {
          setCredentialTypeError(err?.message || err?.toString() || t('commons.somethingWentWrong', 'Something went wrong.'));
        }
      } finally {
        setCredentialTypeLoading(false);
      }
    };

    fetchCredentialType();
  }, [isCredentialPartner, popupData?.partnerId, popupData?.policyId, t]);

  const cancelErrorMsg = () => setErrorMsg('');

  const closingPopUp = () => closePopUp();

  const normalizeBioRow = (item) => {
    const modality =
      item?.bioModality ??
      item?.biometricModality ??
      item?.modality ??
      item?.bio_modality ??
      item?.biometric ??
      '-';
    const configuration =
      item?.bioExtractorConfigurationName ??
      item?.bioExtractorConfigName ??
      item?.configName ??
      item?.bio_extractor_configuration_name ??
      item?.bioExtractorConfigurationId ??
      item?.bio_extractor_configuration_id ??
      item?.attributeName ??
      '-';
    const providerVersion =
      item?.bioextractorProviderVersion ??
      item?.providerVersion ??
      item?.version ??
      item?.provider_version ??
      item?.extractor?.version ??
      '-';
    const providerName =
      item?.bioextractorProviderName ??
      item?.providerName ??
      item?.name ??
      item?.provider_name ??
      item?.extractor?.provider ??
      '-';
    return { modality, configuration, providerVersion, providerName };
  };

  const handleStatusChange = async (status) => {
    setErrorCode('');
    setErrorMsg('');
    setDataLoaded(false);

    try {
      const request = createRequest({ status });
      const response = await HttpService.put(
        getPartnerManagerUrl(`/partners/policy/${popupData.id}`, process.env.NODE_ENV),
        request,
        { headers: { 'Content-Type': 'application/json' } }
      );
      const responseData = response.data;
      if (responseData && responseData.response) {
        approveRejectResponse(responseData.response, status);
      } else {
        setDataLoaded(true);
        handleServiceErrors(responseData, setErrorCode, setErrorMsg);
      }
    } catch (error) {
      if (error?.response?.status !== 401) {
        setDataLoaded(true);
        setErrorMsg(error.toString());
      }
    }
  };

  const customStyle = {
    outerDiv: '!flex !justify-end p-1',
    innerDiv: '!flex !justify-between !items-center !rounded-xl !min-h-12 !p-3 !m-1 !-mb-2 w-full',
  };

  const modalWidth = isCredentialPartner ? 'md:w-[48rem] w-[95%]' : 'md:w-[24rem] w-[55%]';
  const isDetailsLoading = isCredentialPartner && (bioLoading || credentialTypeLoading);

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter cursor-default mx-1 break-normal">
      <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
        <div className={`bg-white mx-auto rounded-lg shadow-sm h-fit ${modalWidth}`}>
          {!dataLoaded ? (
            <LoadingIcon styleSet={{ loadingDiv: '!py-[35%]' }} />
          ) : (
            <>
              <div className="relative">
                {errorMsg && (
                  <ErrorMessage
                    id="partner_policy_approve_reject_popup_error_msg"
                    errorCode={errorCode}
                    errorMessage={errorMsg}
                    clickOnCancel={cancelErrorMsg}
                    customStyle={customStyle}
                  />
                )}
                <div className="relative px-[1rem] pt-4 pb-2 w-full">
                  <div className="flex-col space-y-1 break-words pr-12 text-left">
                    <p className="text-sm font-bold text-[#191919]">{title}</p>
                    {subtitle && <p className="text-[#A5A5A5] text-xs">{subtitle}</p>}
                  </div>
                  <button
                    aria-label={t('commons.close')}
                    onClick={closingPopUp}
                    className="h-8 w-8 rounded-full bg-[#F2F4F8] flex items-center justify-center hover:cursor-pointer absolute top-3 right-4"
                  >
                    <img src={close_icon} alt="" className="h-4 w-4" />
                  </button>
                </div>

                <hr className="h-px bg-gray-100 border-[0.02rem]" />

                <div className="px-[1.5rem] py-3 text-center break-words">
                  <p className="text-base font-semibold text-black">{header}</p>
                  <p className="text-sm text-[#666666] py-3">{description}</p>
                </div>

                {isCredentialPartner && (
                  <div className="px-[1.5rem] pb-3">
                    <div className="border border-[#E5EBFA] rounded-lg p-4">
                      <div className={`grid grid-cols-2 gap-x-28 gap-y-4 max-[600px]:grid-cols-1 ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                        <div className="min-w-0">
                          <p className="text-[0.7rem] text-[#6F6E6E] font-semibold">{t('policies.partnerId')}</p>
                          <p className="text-base font-semibold text-[#191919] break-words">{popupData?.partnerId ?? '-'}</p>
                        </div>
                        <div className="min-w-0">
                          <p className="text-[0.7rem] text-[#6F6E6E] font-semibold">{t('policies.partnerType')}</p>
                          <p className="text-base font-semibold text-[#191919] break-words">
                            {getPartnerTypeDescription(popupData?.partnerType, t) ?? popupData?.partnerType ?? '-'}
                          </p>
                        </div>
                      </div>

                      <div className="mt-6 border border-[#E5EBFA] rounded-md overflow-hidden bg-white">
                        <div className="overflow-x-auto">
                        <table className="min-w-full table-fixed">
                          <thead className="bg-[#F7F9FF] text-[#6F6E6E] text-xs">
                            <tr>
                              <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioModalityLine1')}</span>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioModalityLine2')}</span>
                              </th>
                              <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorConfigLine1')}</span>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorConfigLine2')}</span>
                              </th>
                              <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderVersionLine1')}</span>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderVersionLine2')}</span>
                              </th>
                              <th className={`w-1/4 px-3 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderNameLine1')}</span>
                                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderNameLine2')}</span>
                              </th>
                            </tr>
                          </thead>
                          <tbody className="text-[#191919] text-sm">
                            {bioLoading && (
                              <tr>
                                <td colSpan={4} className="px-3 py-6">
                                  <LoadingIcon styleSet={{ loadingDiv: '!py-0' }} />
                                </td>
                              </tr>
                            )}
                            {!bioLoading && bioError && (
                              <tr>
                                <td colSpan={4} className="px-3 py-3 text-crimson-red font-semibold">
                                  {bioError}
                                </td>
                              </tr>
                            )}
                            {!bioLoading && !bioError && bioExtractors.length === 0 && (
                              <tr>
                                <td colSpan={4} className="px-3 py-3 text-[#6F6E6E] font-semibold">
                                  {t('partnerPolicyRequestApproveRejectPopup.noBioExtractors')}
                                </td>
                              </tr>
                            )}
                            {!bioLoading &&
                              !bioError &&
                              bioExtractors.map((item, idx) => {
                                const row = normalizeBioRow(item);
                                const stripe = idx % 2 === 1 ? 'bg-[#FBFCFF]' : 'bg-white';
                                return (
                                  <tr key={idx} className={stripe}>
                                    <td className={`px-3 py-3 font-semibold whitespace-nowrap ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.modality || '-'}</td>
                                    <td className={`px-3 py-3 font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.configuration || '-'}</td>
                                    <td className={`px-3 py-3 font-semibold whitespace-nowrap ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.providerVersion || '-'}</td>
                                    <td className={`px-3 py-3 font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.providerName || '-'}</td>
                                  </tr>
                                );
                              })}
                          </tbody>
                        </table>
                        </div>
                      </div>

                      <div className="mt-6">
                      <p className={`text-[0.7rem] text-[#6F6E6E] font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                        {t('partnerPolicyRequestApproveRejectPopup.credentialType')}
                      </p>
                      {credentialTypeLoading ? (
                        <p className={`text-base font-semibold text-[#191919] ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                          {t('commons.loading', 'Loading...')}
                        </p>
                      ) : credentialTypeError ? (
                        <p className={`text-sm font-semibold text-crimson-red ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                          {credentialTypeError}
                        </p>
                      ) : (
                        <p className={`text-base font-semibold text-[#191919] break-words ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                          {credentialType || t('commons.notAvailable', 'Not available')}
                        </p>
                      )}
                      </div>
                    </div>
                  </div>
                )}

                <hr className="h-px bg-gray-100 border-[0.02rem]" />

                <div className="flex items-center justify-between space-x-3 p-[6%]">
                  <button
                    onClick={() => handleStatusChange('rejected')}
                    type="button"
                    disabled={isDetailsLoading}
                    className={`w-36 h-10 border-[#1447B2] border rounded-md text-tory-blue ${
                      isDetailsLoading ? 'opacity-50 cursor-not-allowed' : ''
                    }`}
                  >
                    {t('approveRejectPopup.reject')}
                  </button>
                  <button
                    onClick={() => handleStatusChange('approved')}
                    type="button"
                    disabled={isDetailsLoading}
                    className={`w-36 h-10 border-[#1447B2] border rounded-md bg-tory-blue text-white ${
                      isDetailsLoading ? 'opacity-50 cursor-not-allowed' : ''
                    }`}
                  >
                    {t('approveRejectPopup.approve')}
                  </button>
                </div>
              </div>
            </>
          )}
        </div>
      </FocusTrap>
    </div>
  );
}

PartnerPolicyApproveRejectPopup.propTypes = {
  popupData: PropTypes.object.isRequired,
  closePopUp: PropTypes.func.isRequired,
  approveRejectResponse: PropTypes.func.isRequired,
  title: PropTypes.string.isRequired,
  subtitle: PropTypes.string,
  header: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
};

export default PartnerPolicyApproveRejectPopup;

