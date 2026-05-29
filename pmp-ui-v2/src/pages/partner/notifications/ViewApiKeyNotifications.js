import ViewAllNotifications from "../../admin/notifications/ViewAllNotifications";

function ViewApiKeyNotifications() {
    return (
        <ViewAllNotifications
            notificationType='API_KEY_EXPIRY'
        />
    );

}
export default ViewApiKeyNotifications;