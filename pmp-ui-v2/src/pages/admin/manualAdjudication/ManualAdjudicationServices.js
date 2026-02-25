import AdminApiKeysList from '../authenticationServices/AdminApiKeysList.js';

function ManualAdjudicationServices() {
    return (
        <AdminApiKeysList
            partnerType="Manual_Adjudication"
            title="dashboard.manualAdjudication"
            returnPath="/partnermanagement/admin/manual-adjudication-services/api-keys-list"
            showTab={false}
            listTitle="manualAdjudicationServices.listOfManualAdjudicationApiKeys"
            errorMsgKey="manualAdjudicationServices.errorInManualAdjudicationList"
            generateApiKeyOptions={{
                path: '/partnermanagement/admin/manual-adjudication-services/generate-api-key',
                label: 'manualAdjudicationServices.generateApiKey',
                buttonId: 'generate_api_key',
                emptyButtonId: 'generate_api_key_empty',
            }}
        />
    );
}

export default ManualAdjudicationServices;
