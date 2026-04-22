import PropTypes from 'prop-types';
import AdminAuthCommonFilter from "./AdminAuthCommonFilter.js";

function AdminOidcClientsFilter ({ onApplyFilter }) {
    return (
        <AdminAuthCommonFilter
            onApplyFilter={onApplyFilter}
            statusDropdownData={[{ status: 'ACTIVE' }, { status: 'INACTIVE' }]}
            statusPlaceholderKey="partnerList.selectStatus"
            extraTextField={{
                fieldName: "clientNameEng",
                fieldNameKey: "oidcClientsList.oidcClientName",
                placeHolderKey: "oidcClientsList.searchOidcClientName",
                id: "oidc_client_name_filter",
                maxLength: 256,
            }}
            applyButtonId="apply_filter__btn"
        />
    );
}

AdminOidcClientsFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default AdminOidcClientsFilter;