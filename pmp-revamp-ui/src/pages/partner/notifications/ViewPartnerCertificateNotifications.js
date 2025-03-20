import { getUserProfile } from "../../../services/UserProfileService";
import ViewAllNotifications from "../../admin/notifications/ViewAllNotifications";

function ViewPartnerCertificateNotifications() {
   

    return (
        <ViewAllNotifications
            notificationType='partner'
        />

    );
}

export default ViewPartnerCertificateNotifications;