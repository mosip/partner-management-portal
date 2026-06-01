import ViewAllNotifications from "../../admin/notifications/ViewAllNotifications";

function ViewPartnerCertificateNotifications() {
   

    return (
        <ViewAllNotifications
            notificationType='PARTNER_CERT_EXPIRY'
        />

    );
}

export default ViewPartnerCertificateNotifications;