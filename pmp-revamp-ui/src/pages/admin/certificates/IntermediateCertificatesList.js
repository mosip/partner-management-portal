import { useNavigate } from 'react-router-dom';
import CertificatesList from './CertificatesList';

function IntermediateCertificatesList() {

  const navigate = useNavigate('');

  const uploadCertRequiredData = () => {
    const requiredData = {
      breadcrumb: 'intermediateCertificateList.subTitle',
      backLink: '/partnermanagement/admin/certificates/intermediate-ca-certificate-list'
    }
    localStorage.setItem('uploadCertificateAttributes', JSON.stringify(requiredData));
  };

  const viewIntermediateCertificateDetails = (selectedCertificateData) => {
    const requiredData = {
      certificateData: selectedCertificateData,
      certType: 'intermediate',
      header: 'viewCertificateDetails.viewIntermediateCaCertificateDetails',
      subTitle: 'intermediateCertificateList.subTitle',
      backLink: '/partnermanagement/admin/certificates/intermediate-ca-certificate-list'
    }
    localStorage.setItem('selectedCertificateAttributes', JSON.stringify(requiredData));
    navigate('/partnermanagement/admin/certificates/view-intermediate-ca-certificate-details');
  };

  return (
    <CertificatesList
      certificateType='intermediate'
      uploadCertificateBtnName='intermediateCertificateList.uploadIntermediateCaCertificate'
      subTitle='intermediateCertificateList.subTitle'
      viewCertificateDetails={viewIntermediateCertificateDetails}
      downloadBtnName='intermediateCertificateList.downloadCertificate'
      uploadCertRequiredData={uploadCertRequiredData}
    />
  )
}

export default IntermediateCertificatesList;