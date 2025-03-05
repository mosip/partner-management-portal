import ViewAllNotifications from "./ViewAllNotifications";

function ViewIntermediateTrustNotifications() {
    return (
        <ViewAllNotifications
            notificationType='INTERMEDIATE_CERT_EXPIRY'
        />
    )
}

export default ViewIntermediateTrustNotifications;