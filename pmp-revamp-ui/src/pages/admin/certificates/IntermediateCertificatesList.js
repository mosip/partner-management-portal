import CertificatesList from './CertificatesList';

function IntermediateCertificatesList() {
  return (
    <CertificatesList
      certificateType='intermediate'
      uploadCertificateBtnName='intermediateCertificateList.uploadIntermediateCaCertificate'
      subTitle='intermediateCertificateList.subTitle'
    />
  )
}

export default IntermediateCertificatesList;