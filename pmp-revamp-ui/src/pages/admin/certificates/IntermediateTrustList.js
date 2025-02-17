import TrustList from './TrustList';

function IntermediateTrustList() {

  return (
    <TrustList
      trustListType='intermediate'
      uploadTrustBtnName='intermediateTrustList.uploadIntermediateCaTrust'
      subTitle='intermediateTrustList.subTitle'
      downloadBtnName='intermediateTrustList.downloadTrust'
    />
  )
}

export default IntermediateTrustList;