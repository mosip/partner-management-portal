import RootCertificateList from './RootCertificateList';

function IntermediateRootTrustCertificatesList() {
  return (
    <RootCertificateList
      certificateType='intermediate'
      uploadCertificateButtonName='rootTrustCertificate.UploadCertBtn'
    />
  )
}

export default IntermediateRootTrustCertificatesList;