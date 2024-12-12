import RootCertificateList from "./RootCertificateList";

function RootTrustCertificatesList() {

    return (
        <RootCertificateList
            certificateType = 'root'
            uploadCertificateButtonName='rootTrustCertificate.UploadCertBtn'
        />
    )
}

export default RootTrustCertificatesList;