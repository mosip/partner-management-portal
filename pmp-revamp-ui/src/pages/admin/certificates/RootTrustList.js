import TrustList from "./TrustList";

function RootTrustList() {

    return (
        <TrustList
            trustType='root'
            uploadTrustBtnName='rootTrustList.uploadRootCaTrust'
            subTitle='rootTrustList.subTitle'
            downloadBtnName='rootTrustList.downloadTrust'
        />
    )
}

export default RootTrustList;