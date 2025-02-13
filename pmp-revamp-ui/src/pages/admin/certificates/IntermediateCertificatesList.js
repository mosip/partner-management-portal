import CertificatesList from './CertificatesList';

function IntermediateCertificatesList() {

  return (
    <CertificatesList
      certificateType='intermediate'
      uploadCertificateBtnName='intermediateCertificateList.uploadIntermediateCaCertificate'
      subTitle='intermediateCertificateList.subTitle'
      downloadBtnName='intermediateCertificateList.downloadCertificate'
    />
  )
}

export default IntermediateCertificatesList;