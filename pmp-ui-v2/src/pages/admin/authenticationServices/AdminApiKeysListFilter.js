import PropTypes from 'prop-types';
import AdminAuthCommonFilter from "./AdminAuthCommonFilter.js";

function AdminApiKeysListFilter({ onApplyFilter }) {
    return (
        <AdminAuthCommonFilter
            onApplyFilter={onApplyFilter}
            statusDropdownData={[{ status: 'activated' }, { status: 'deactivated' }]}
            statusPlaceholderKey="partnerList.selectStatus"
            extraTextField={{
                fieldName: "apiKeyLabel",
                fieldNameKey: "apiKeysList.apiKeyName",
                placeHolderKey: "apiKeysList.searchApiKeyName",
                id: "api_key_name_filter",
                maxLength: 36,
            }}
            applyButtonId="apply_filter_btn"
        />
    );
}

AdminApiKeysListFilter.propTypes = {
    onApplyFilter: PropTypes.func.isRequired,
};

export default AdminApiKeysListFilter;
