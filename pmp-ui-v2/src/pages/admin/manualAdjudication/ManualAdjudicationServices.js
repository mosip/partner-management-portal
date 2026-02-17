import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService';
import { isLangRTL} from '../../../utils/AppUtils';
import Title from '../../common/Title.js';
import EmptyList from '../../common/EmptyList.js';

function ManualAdjudicationServices() {
    const navigate = useNavigate();
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'manualAdjudicationServices.partnerId' },
        { id: "orgName", headerNameKey: "manualAdjudicationServices.orgName" },
        { id: "policyGroupName", headerNameKey: "manualAdjudicationServices.policyGroup" },
        { id: "policyName", headerNameKey: "manualAdjudicationServices.policyName" },
        { id: "apiKeyLabel", headerNameKey: "manualAdjudicationServices.apiKeyName" },
        { id: "createdDateTime", headerNameKey: "manualAdjudicationServices.creationDate" },
        { id: "status", headerNameKey: "manualAdjudicationServices.status" },
        { id: "action", headerNameKey: 'manualAdjudicationServices.action' }
    ];

    const generateApiKey = () => {
        navigate('/partnermanagement/admin/manual-adjudication-services/generate-api-key');
    };

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            <div className="flex-col mt-5">
                <div className="flex justify-between mb-5">
                    <Title title='dashboard.manualAdjudication' backLink='/partnermanagement' />
                </div>
                <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                    <EmptyList
                        tableHeaders={tableHeaders}
                        showCustomButton={true}
                        customButtonName='manualAdjudicationServices.generateApiKey'
                        buttonId='generate_api_key'
                        onClickButton={generateApiKey}
                    />
                </div>
            </div>
        </div>
    );
}

export default ManualAdjudicationServices;