import CertificatesList from "./CertificatesList";

function RootCertificatesList() {

    return (
        <CertificatesList
            certificateType = 'root'
            uploadCertificateBtnName='rootCertificateList.uploadRootCaCertificate'
            subTitle='rootCertificateList.subTitle'
        />
    )
}

export default RootCertificatesList;