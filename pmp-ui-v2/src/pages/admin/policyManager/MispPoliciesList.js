import { useNavigate } from 'react-router-dom';
import PoliciesList from "./PoliciesList";

function MispPoliciesList() {
    const navigate = useNavigate('');

    const createMispPolicy = () => {
        navigate('/partnermanagement/policy-manager/create-misp-policy');
    };

    const viewMispPolicy = (selectedPolicy) => {
        const requiredData = {
            policyId: selectedPolicy.policyId,
            header: 'viewMispPoliciesList.viewMispPolicy',
            subTitle: 'viewMispPoliciesList.listOfMispPolicies',
            backLink: '/partnermanagement/policy-manager/misp-policies-list'
        }
        sessionStorage.setItem('selectedPolicyAttributes', JSON.stringify(requiredData));
        navigate('/partnermanagement/policy-manager/view-misp-policy');
    };

    const editMispPolicy = (selectedPolicy) => {
        sessionStorage.setItem('policyId', selectedPolicy.policyId);
        navigate('/partnermanagement/policy-manager/edit-misp-policy');
    };

    return (
        <PoliciesList
            policyType='MISP'
            createPolicyButtonName='policiesList.createMispPolicy'
            createPolicy={createMispPolicy}
            subTitle='policiesList.listOfMispPolicies'
            fetchDataErrorMessage='policiesList.errorInMispPolicies'
            viewPolicy={viewMispPolicy}
            editPolicy={editMispPolicy}
        />
            
    );
}

export default MispPoliciesList;