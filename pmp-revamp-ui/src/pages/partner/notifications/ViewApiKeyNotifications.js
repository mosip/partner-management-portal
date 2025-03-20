import { getUserProfile } from "../../../services/UserProfileService";
import ViewAllNotifications from "../../admin/notifications/ViewAllNotifications";

function ViewApiKeyNotifications() {
    return (
        <ViewAllNotifications
            notificationType='apikey'
            partnerType= {getUserProfile().partnerType}
        />
    );

}
export default ViewApiKeyNotifications;