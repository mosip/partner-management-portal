import { useNavigate } from 'react-router-dom';
import PoliciesList from "./PoliciesList";

function AuthPoliciesList() {
    const navigate = useNavigate('');

    const createAuthPolicy = () => {
        navigate('/partnermanagement/admin/policy-manager/create-auth-policy');
    };

    const viewAuthPolicy = (selectedPolicy) => {
        const requiredData = {
            policyId: selectedPolicy.policyId,
            header: 'viewAuthPoliciesList.viewAuthPolicy',
            subTitle: 'viewAuthPoliciesList.listOfAuthenticationPolicies',
            backLink: '/partnermanagement/admin/policy-manager/auth-policies-list'
        }
        localStorage.setItem('selectedPolicyAttributes', JSON.stringify(requiredData));
        navigate('/partnermanagement/admin/policy-manager/view-auth-policy');
    };

    const editAuthPolicy = (selectedPolicy) => {
        localStorage.setItem('policyId', selectedPolicy.policyId);
        navigate('/partnermanagement/admin/policy-manager/edit-auth-policy');
    };

    return (
        <PoliciesList
            policyType='Auth'
            createPolicyButtonName='policiesList.createAuthPolicy'
            createPolicy={createAuthPolicy}
            subTitle='policiesList.listOfAuthPolicies'
            fetchDataErrorMessage='policiesList.errorInAuthPolicies'
            viewPolicy={viewAuthPolicy}
            editPolicy={editAuthPolicy}
        />
            
    );
}

export default AuthPoliciesList;