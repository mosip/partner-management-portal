import { getUserProfile } from "../../../services/UserProfileService";
import ViewAllNotifications from "../../admin/notifications/ViewAllNotifications";

function ViewFtmCertificateNotifications() {
    return (
        <ViewAllNotifications
            notificationType='ftm'
        />
    );
}
export default ViewFtmCertificateNotifications;