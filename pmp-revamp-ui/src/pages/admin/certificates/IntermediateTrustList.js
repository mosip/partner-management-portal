import TrustList from './TrustList';

function IntermediateTrustList() {

  return (
    <TrustList
      trustType='intermediate'
      uploadTrustBtnName='intermediateTrustList.uploadIntermediateCaTrust'
      subTitle='intermediateTrustList.subTitle'
      downloadBtnName='intermediateTrustList.downloadTrust'
    />
  )
}

export default IntermediateTrustList;