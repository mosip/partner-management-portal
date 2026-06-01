import ViewAllNotifications from "./ViewAllNotifications";

function ViewMispLicenseKeyNotifications() {
    return (
        <ViewAllNotifications
            notificationType='MISP_LICENSE_KEY_EXPIRY'
        />
    )
}

export default ViewMispLicenseKeyNotifications;