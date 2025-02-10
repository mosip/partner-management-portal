import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import ErrorMessage from '../../common/ErrorMessage.js';
import LoadingIcon from '../../common/LoadingIcon.js';
import DropdownWithSearchComponent from '../../common/fields/DropdownWithSearchComponent.js';
import FocusTrap from 'focus-trap-react';
import { HttpService } from '../../../services/HttpService.js';
import { getPolicyGroupList, getPolicyManagerUrl, createRequest, getPolicyDetails } from '../../../utils/AppUtils.js';
import SuccessMessage from '../../common/SuccessMessage.js';
import closeIcon from "../../../svg/close_icon.svg";

function ClonePolicyPopup ({policyDetails, closePopUp}) {
    const [selectedPolicyGroup, setSelectedPolicyGroup] = useState('');
    const [policyGroupList, setPolicyGroupList] = useState([]);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [cloneSuccess, setCloneSccesss] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(true);
    const { t } = useTranslation();

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    const changePolicyGroupSelection = (fieldName, selectedValue) => {
        setSelectedPolicyGroup(selectedValue);
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const cancelSuccessMsg = () => {
        setSuccessMsg("");
    };

    useEffect(() => {
        const fetchData = async () => {
            setDataLoaded(false);
            await getPolicyGroupList(HttpService, setPolicyGroupList, setErrorCode, setErrorMsg, t);
            setDataLoaded(true);
        };
        fetchData();
    }, []);

    const cancelPopUp = () => {
        closePopUp();
    };

    const clickOnClose = () => {
        window.location.reload();
    };

    const clickOnClone = async () => {
        setErrorCode("");
        setErrorMsg("");
        setCloneSccesss(false);
        setSuccessMsg("");
        setDataLoaded(false);
        const policyData = await getPolicyDetails(HttpService, policyDetails.policyId, setErrorCode, setErrorMsg);
        if (policyData !== null) {
            let request = createRequest({
                name: policyData.policyName,
                policyGroupName: selectedPolicyGroup,
                policyType: policyData.policyType,
                desc: policyData.policyDesc,
                policies: policyData.policies,
                version: policyData.version
            });
            try {
                const response = await HttpService({
                    url: getPolicyManagerUrl('/policies', process.env.NODE_ENV),
                    method: 'post',
                    baseURL: process.env.NODE_ENV !== 'production' ? '' : window._env_.REACT_APP_POLICY_MANAGER_API_BASE_URL,
                    data: request
                });
                if (response) {
                    const responseData = response.data;
                    if (responseData && responseData.response) {
                        setCloneSccesss(true);
                        setSuccessMsg(t('clonePolicyPopup.successMsg', { policyName: policyData.policyName, policyGroupName: selectedPolicyGroup }));
                    } else {
                        if (responseData && responseData.errors && responseData.errors.length > 0) {
                            const errorCode = responseData.errors[0].errorCode;
                            const errorMessage = responseData.errors[0].message;
                            if (errorCode === "PMS_POL_009") {
                                setErrorMsg(t('clonePolicyPopup.policyExistError'))
                            } else {
                                setErrorCode(errorCode);
                                setErrorMsg(errorMessage);
                            }
                        }
                    }
                    setDataLoaded(true);
                } else {
                    setDataLoaded(true);
                    setErrorMsg(t('clonePolicyPopup.errorInClonePolicy'));
                }
            } catch (err) {
                console.log("Error fetching data: ", err);
                if (err.response?.status && err.response.status !== 401) {
                    setErrorMsg(err.toString());
                }
                setDataLoaded(true);
            }
        } else {
            setDataLoaded(true);
            setErrorMsg(t('clonePolicyPopup.errorInPolicyDetails'))
        }
    };

    const styles = {
        outerDiv: "!ml-0 !mb-0",
        dropdownLabel: "!text-sm !my-2 mb-0",
        dropdownButton: "!w-full min-h-10 !rounded-md !text-sm !text-dark-blue",
        selectionBox: "",
        loadingDiv: "!py-[50%]"
    }

    const customStyle = {
        outerDiv: "!flex !justify-center !inset-0",
        innerDiv: "!flex !justify-between !items-center !w-full !min-h-12 !p-3 !-mb-2",
        cancelIcon: "!top-4 !mt-[3.25rem]"
    }

    return (
        <div className="fixed inset-0 w-full flex items-center justify-center bg-black bg-opacity-35 z-50 font-inter cursor-default">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className={`bg-white md:w-[25rem] w-[60%] h-fit rounded-xl shadow-sm`}>
                    {!dataLoaded && (
                        <LoadingIcon styleSet={styles}></LoadingIcon>
                    )}
                    {dataLoaded && (
                        <div className="relative">
                            <div className="px-6 py-3 flex justify-between">
                                <h3 className="text-lg font-bold text-[#333333]">{t('clonePolicyPopup.title')}</h3>
                                <button onClick={cancelPopUp}><img className='h-[25px] w-[25px]' src={closeIcon} alt='closeIcon'/></button>
                            </div> 
                            <div className="border-gray-200 border-opacity-75 border-t"></div>
                            {errorMsg && (
                                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} customStyle={customStyle}/>
                            )}
                            {successMsg && (
                                <SuccessMessage successMsg={successMsg} clickOnCancel={cancelSuccessMsg} customStyle={customStyle}/>
                            )}
                            <div className="py-2 px-6">
                                <p className="text-sm font-normal text-[#414141] break-words">{t('clonePolicyPopup.description1')} 
                                    <span className="font-bold"> {policyDetails.policyName}</span> {t('clonePolicyPopup.description2')}
                                </p>
                            </div>
                            <div className="w-full flex flex-col px-6 pb-6">
                                <DropdownWithSearchComponent
                                    fieldName='policyGroup'
                                    dropdownDataList={policyGroupList}
                                    onDropDownChangeEvent={changePolicyGroupSelection}
                                    selectedDropdownValue={selectedPolicyGroup}
                                    fieldNameKey='selectPolicyPopup.policyGroup*'
                                    placeHolderKey='selectPolicyPopup.title'
                                    searchKey='commons.search'
                                    selectPolicyPopup
                                    styleSet={styles}
                                    id="clone_policy_group_dropdown">
                                </DropdownWithSearchComponent>
                            </div>
                            <div className="border-[#E5EBFA] border-t mx-2"></div>
                            <div className="px-6 py-3 flex justify-between relative">
                                <button disabled={cloneSuccess} className={`w-36 h-10 m-1 ${cloneSuccess ? 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed' : 'border-[#1447B2] text-tory-blue bg-white'}  border rounded-lg  text-sm font-semibold relative z-60`}
                                    onClick={cancelPopUp}
                                    id="clone_policy_cancel">
                                    {t('commons.cancel')}
                                </button>
                                { !cloneSuccess ? 
                                    <button className={`w-36 h-10 m-1  border rounded-lg text-white text-sm font-semibold relative z-60 
                                        ${selectedPolicyGroup ? 'bg-tory-blue border-[#1447B2] cursor-pointer' : 'border-[#A5A5A5] bg-[#A5A5A5] cursor-not-allowed'}`}
                                        onClick={clickOnClone}
                                        disabled={!selectedPolicyGroup}
                                        id="clone_policy_button">
                                        {t('policiesList.clone')}
                                    </button> : 
                                    <button className={`w-36 h-10 m-1  border rounded-lg text-white text-sm font-semibold relative z-60 bg-tory-blue border-[#1447B2] cursor-pointer'`}
                                        onClick={clickOnClose}
                                        id="clone_policy_close_button">
                                        {t('commons.close')}
                                    </button>
                                }  
                            </div>
                        </div>
                    )}
                </div>
            </FocusTrap>
        </div>
    );

}

export default ClonePolicyPopup;