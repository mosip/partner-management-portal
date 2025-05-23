import AdminDevicesList from "./AdminDevicesList.js";

function AllDevicesList () {

    return (
        <AdminDevicesList
            title='dashboard.sbiDevice'
            subTitle='devicesList.listOfDevices'
            isLinkedDevicesList={false} 
        />
    );
}
export default AllDevicesList;