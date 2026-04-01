import React, { useEffect, useMemo, useState } from 'react';
import PropTypes from 'prop-types';
import LoadingIcon from '../../common/LoadingIcon';
import { HttpService } from '../../../services/HttpService';
import { getPartnerManagerUrl } from '../../../utils/AppUtils';

function CredentialPartnerPolicyDetails({
  t,
  isLoginLanguageRTL,
  partnerId,
  policyId,
  partnerTypeLabel,
  enabled,
  variant,
  onLoadingChange,
}) {
  const [bioLoading, setBioLoading] = useState(() => Boolean(enabled));
  const [bioError, setBioError] = useState('');
  const [bioExtractors, setBioExtractors] = useState([]);

  const [credentialTypeLoading, setCredentialTypeLoading] = useState(() => Boolean(enabled));
  const [credentialTypeError, setCredentialTypeError] = useState('');
  const [credentialType, setCredentialType] = useState('');

  const isLoading = useMemo(
    () => Boolean(enabled) && (bioLoading || credentialTypeLoading),
    [enabled, bioLoading, credentialTypeLoading]
  );

  useEffect(() => {
    if (enabled) return;
    setBioLoading(false);
    setCredentialTypeLoading(false);
  }, [enabled]);

  useEffect(() => {
    if (!onLoadingChange) return;
    onLoadingChange(isLoading);
  }, [isLoading, onLoadingChange]);

  const getModalityLabel = (modality) => {
    if (!modality) return '-';
    const upperModality = String(modality).toUpperCase();
    if (upperModality === 'FACE') return t('bioExtractorConfig.face');
    if (upperModality === 'IRIS') return t('bioExtractorConfig.iris');
    if (upperModality === 'FINGER' || upperModality === 'FINGERPRINT') return t('bioExtractorConfig.finger');
    return upperModality;
  };

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

  useEffect(() => {
    const fetchBioExtractors = async () => {
      if (!enabled) {
        setBioLoading(false);
        return;
      }
      if (!partnerId || !policyId) {
        setBioLoading(false);
        return;
      }

      setBioError('');
      setBioExtractors([]);
      setBioLoading(true);
      try {
        const url = getPartnerManagerUrl(`/partners/${partnerId}/bioextractors/${policyId}`, process.env.NODE_ENV);
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
          const firstCode = responseData?.errors?.[0]?.errorCode;
          if (firstCode === 'PMS_PRT_064') {
            setBioError(
              t(
                'partnerPolicyRequestApproveRejectPopup.extractorsNotConfigured',
                'Extractors are not configured.'
              )
            );
          } else {
            setBioError(t('commons.somethingWentWrong', 'Something went wrong.'));
          }
        }
      } catch (err) {
        if (err?.response?.status !== 401) {
          setBioError(err?.message || err?.toString() || t('commons.somethingWentWrong', 'Something went wrong.'));
        }
      } finally {
        setBioLoading(false);
      }
    };

    fetchBioExtractors();
  }, [enabled, partnerId, policyId, t]);

  useEffect(() => {
    const fetchCredentialType = async () => {
      if (!enabled) {
        setCredentialTypeLoading(false);
        return;
      }
      if (!partnerId || !policyId) {
        setCredentialTypeLoading(false);
        return;
      }

      setCredentialTypeError('');
      setCredentialType('');
      setCredentialTypeLoading(true);
      try {
        const url = getPartnerManagerUrl(`/partners/${partnerId}/policies/${policyId}/credential-types`, process.env.NODE_ENV);
        const response = await HttpService.get(url);
        const responseData = response?.data;

        if (responseData?.response !== undefined) {
          const payload = responseData.response;

          // Some deployments may return a bare array or bare string in `response`
          // instead of an object containing `credentialTypes`.
          if (Array.isArray(payload)) {
            const cleaned = payload
              .map((x) => (x === null || x === undefined ? '' : String(x).trim()))
              .filter(Boolean);
            setCredentialType(cleaned.join(', '));
            return;
          }
          if (typeof payload === 'string') {
            setCredentialType(payload.trim());
            return;
          }

          const types =
            payload?.credentialTypes ??
            payload?.credential_types ??
            payload?.data?.credentialTypes ??
            payload?.data?.credential_types ??
            null;

          if (Array.isArray(types)) {
            const cleaned = types
              .map((x) => (x === null || x === undefined ? '' : String(x).trim()))
              .filter(Boolean);
            setCredentialType(cleaned.join(', '));
            return;
          }

          const value =
            (typeof payload === 'string' ? payload : null) ??
            payload?.credentialType ??
            payload?.credential_type ??
            payload?.type ??
            payload?.data?.credentialType ??
            payload?.data?.credential_type ??
            '';

          setCredentialType(value ? String(value) : '');
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
  }, [enabled, partnerId, policyId, t]);

  const table = (
    <div className="mt-6 border border-[#E5EBFA] rounded-md overflow-hidden bg-white">
      <div className="overflow-x-auto">
        <table className="min-w-full table-fixed">
          <thead className="bg-[#F7F9FF] text-[#6F6E6E] text-xs">
            <tr>
              <th className={`w-[18%] px-4 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioModalityLine1')}</span>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioModalityLine2')}</span>
              </th>
              <th className={`w-[34%] px-4 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorConfigLine1')}</span>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorConfigLine2')}</span>
              </th>
              <th className={`w-[24%] px-4 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderVersionLine1')}</span>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderVersionLine2')}</span>
              </th>
              <th className={`w-[24%] px-4 py-2 font-semibold whitespace-normal leading-tight align-top ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderNameLine1')}</span>
                <span className="block whitespace-nowrap">{t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderNameLine2')}</span>
              </th>
            </tr>
          </thead>
          <tbody className="text-[#191919] text-sm">
            {bioLoading && (
              <tr>
                <td colSpan={4} className="px-4 py-6">
                  <LoadingIcon styleSet={{ loadingDiv: '!py-0' }} />
                </td>
              </tr>
            )}
            {!bioLoading && bioError && (
              <tr>
                <td colSpan={4} className="px-4 py-3 text-crimson-red font-semibold">
                  {bioError}
                </td>
              </tr>
            )}
            {!bioLoading && !bioError && bioExtractors.length === 0 && (
              <tr>
                <td colSpan={4} className="px-4 py-3 text-[#6F6E6E] font-semibold">
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
                    <td className={`px-4 py-3 font-semibold whitespace-nowrap ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{getModalityLabel(row.modality)}</td>
                    <td className={`px-4 py-3 font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.configuration || '-'}</td>
                    <td className={`px-4 py-3 font-semibold whitespace-nowrap ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.providerVersion || '-'}</td>
                    <td className={`px-4 py-3 font-semibold ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>{row.providerName || '-'}</td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      </div>
    </div>
  );

  const credentialTypeBlock = (
    <div className="mt-6">
      <p
        className={`text-[#6F6E6E] font-semibold ${
          variant === 'view' ? 'text-sm' : 'text-[0.7rem]'
        } ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}
      >
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
  );

  if (variant === 'popup') {
    return (
      <div className="border border-[#E5EBFA] rounded-lg p-4">
        <div className={`grid grid-cols-2 gap-x-28 gap-y-4 max-[600px]:grid-cols-1 ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
          <div className="min-w-0">
            <p className="text-[0.7rem] text-[#6F6E6E] font-semibold">{t('policies.partnerId')}</p>
            <p className="text-base font-semibold text-[#191919] break-words">{partnerId ?? '-'}</p>
          </div>
          <div className="min-w-0">
            <p className="text-[0.7rem] text-[#6F6E6E] font-semibold">{t('policies.partnerType')}</p>
            <p className="text-base font-semibold text-[#191919] break-words">{partnerTypeLabel ?? '-'}</p>
          </div>
        </div>
        {table}
        {credentialTypeBlock}
      </div>
    );
  }

  return (
    <div>
      <div className="border border-[#E5EBFA] rounded-lg p-4">
        <p className={`text-sm font-semibold text-[#191919] ${isLoginLanguageRTL ? 'text-right' : 'text-left'}`}>
          {t('partnerPolicyRequestApproveRejectPopup.bioExtractorProviderMapping')}
        </p>
        {table}
      </div>
      <hr className="h-px w-full bg-gray-200 border-0 my-6" />
      {credentialTypeBlock}
    </div>
  );
}

CredentialPartnerPolicyDetails.propTypes = {
  t: PropTypes.func.isRequired,
  isLoginLanguageRTL: PropTypes.bool.isRequired,
  partnerId: PropTypes.string,
  policyId: PropTypes.string,
  partnerTypeLabel: PropTypes.string,
  enabled: PropTypes.bool,
  variant: PropTypes.oneOf(['popup', 'view']).isRequired,
  onLoadingChange: PropTypes.func,
};

export default CredentialPartnerPolicyDetails;

