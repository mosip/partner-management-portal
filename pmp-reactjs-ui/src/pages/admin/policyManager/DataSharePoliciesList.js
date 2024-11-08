import { useNavigate } from 'react-router-dom';
import PoliciesList from "./PoliciesList";

function DataSharePoliciesList() {
    const navigate = useNavigate('');

    const createDataSharePolicy = () => {
        localStorage.setItem('policyType', 'DataShare');
        navigate('/partnermanagement/admin/policy-manager/create-data-share-policy');
    };

    const viewDataSharePolicy = (selectedPolicy) => {
        const requiredData = {
            policyId: selectedPolicy.policyId,
            header: 'viewDataSharePoliciesList.viewDataSharePolicy',
            subTitle: 'viewDataSharePoliciesList.listOfDataSharePolicies',
            backLink: '/partnermanagement/admin/policy-manager/data-share-policies-list'
        }
        localStorage.setItem('selectedPolicyData', JSON.stringify(requiredData));
        navigate('/partnermanagement/admin/policy-manager/view-data-share-policy');
    };

    return (
        <PoliciesList
            policyType = 'dataShare'
            createPolicyButtonName = 'policiesList.createDataSharePolicy'
            createPolicy = {createDataSharePolicy}
            subTitle = 'policiesList.listOfDataSharePolicies'
            fetchDataErrorMessage = 'policiesList.errorInDataSharePolicies'
            viewPolicy = {viewDataSharePolicy}>
        </PoliciesList>
    );
}

export default DataSharePoliciesList;