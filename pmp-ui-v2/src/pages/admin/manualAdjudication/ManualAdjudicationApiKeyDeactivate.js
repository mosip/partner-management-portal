import { createRequest } from '../../../utils/AppUtils';

export const openManualAdjudicationDeactivatePopup = (
    selectedApiKeyData,
    index,
    setActionId,
    setSelectedApiKey,
    setDeactivateRequest,
    setShowActiveIndexDeactivatePopup
) => {
    if (selectedApiKeyData.status === 'activated') {
        const request = createRequest({
            status: 'De-active'
        }, 'mosip.pms.update.api.key.patch', true);

        setActionId(-1);
        setSelectedApiKey(selectedApiKeyData);
        setDeactivateRequest(request);
        setShowActiveIndexDeactivatePopup(index);
        document.body.style.overflow = 'hidden';
    }
};

export const handleManualAdjudicationDeactivateSuccess = (
    deactivationResponse,
    selectedApiKey,
    setSelectedApiKey,
    setShowActiveIndexDeactivatePopup,
    setApiKeysList
) => {
    if (deactivationResponse !== '') {
        setSelectedApiKey({});
        setShowActiveIndexDeactivatePopup(null);
        setApiKeysList((prevList) =>
            prevList.map((apiKey) =>
                (apiKey.apiKeyLabel === selectedApiKey.apiKeyLabel
                    && apiKey.policyId === selectedApiKey.policyId
                    && apiKey.partnerId === selectedApiKey.partnerId)
                    ? { ...apiKey, status: 'deactivated' }
                    : apiKey
            )
        );
    }
};

