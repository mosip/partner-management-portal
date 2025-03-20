import { getUserProfile } from "../../../services/UserProfileService";
import ViewAllNotifications from "../../admin/notifications/ViewAllNotifications";

function ViewSbiNotifications() {
    return (
        <ViewAllNotifications
            notificationType='sbi'
            partnerType= {getUserProfile().partnerType}
        />
    );
}
export default ViewSbiNotifications;