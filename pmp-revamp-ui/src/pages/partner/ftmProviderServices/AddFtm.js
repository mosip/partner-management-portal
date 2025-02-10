import React, { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../../common/fields/DropdownComponent';
import { getUserProfile } from '../../../services/UserProfileService';
import LoadingIcon from "../../common/LoadingIcon";
import ErrorMessage from "../../common/ErrorMessage";
import { createDropdownData, createRequest, getPartnerManagerUrl, getPartnerTypeDescription, handleServiceErrors, isLangRTL, getPartnerDomainType, trimAndReplace } from "../../../utils/AppUtils";
import Title from "../../common/Title";
import { HttpService } from "../../../services/HttpService";
import BlockerPrompt from "../../common/BlockerPrompt";
import Confirmation from "../../common/Confirmation";
import UploadCertificate from "../certificates/UploadCertificate";

function AddFtm() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [partnerData, setPartnerData] = useState([]);
  const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
  const [partnerId, setPartnerId] = useState("");
  const [partnerType, setPartnerType] = useState("");
  const [make, setMake] = useState('');
  const [model, setModel] = useState('');
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [showPopup, setShowPopup] = useState(false);
  const [uploadCertificateData, setUploadCertificateData] = useState({});
  const [addFtmSuccess, setAddFtmSuccess] = useState(false);
  const [confirmationData, setConfirmationData] = useState({});
  const [ftpChipDetailId, setFtpChipDetailId] = useState("");
  const [uploadCertificateRequest, setUploadCertificateRequest] = useState({});

  const blocker = useBlocker(
    ({ currentLocation, nextLocation }) => {
      if (isSubmitClicked || addFtmSuccess) {
        setIsSubmitClicked(false);
        return false;
      }
      return (
        (partnerId !== "" || make !== "" || model !== "") && currentLocation.pathname !== nextLocation.pathname
      );
    }
  );

  useEffect(() => {
    const shouldWarnBeforeUnload = () => {
      return partnerId !== "" ||
        make !== "" ||
        model !== "";
    };

    const handleBeforeUnload = (event) => {
      if (shouldWarnBeforeUnload() && !isSubmitClicked) {
        event.preventDefault();
        event.returnValue = '';
      }
    };

    window.addEventListener('beforeunload', handleBeforeUnload);

    return () => {
      window.removeEventListener('beforeunload', handleBeforeUnload);
    };
  }, [partnerId, make, model, isSubmitClicked]);

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const onChangePartnerId = async (fieldName, selectedValue) => {
    setPartnerId(selectedValue);
    const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
    if (selectedPartner) {
      setPartnerType(getPartnerTypeDescription("FTM_Provider", t));
    }
  };

  const onChangeMake = (value) => {
    setMake(value);
  }

  const onChangeModel = (value) => {
    setModel(value);
  }

  const clickOnUpload = () => {
    const request = {
      ftpProviderId: partnerId,
      ftpChipDeatilId: ftpChipDetailId,
      isItForRegistrationDevice: true,
      partnerDomain: getPartnerDomainType("FTM_Provider"),
    };
    setUploadCertificateRequest(request);
    setShowPopup(!showPopup);
  };

  const closePopup = (state, btnName) => {
    if (state && btnName === 'cancel') {
      setShowPopup(false);
    } else if (state && btnName === 'close') {
      navigate('/partnermanagement/ftm-chip-provider-services/ftm-list');
    }
  };

  const handleFormSubmit = (event) => {
    event.preventDefault();
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setDataLoaded(false);
        const response = await HttpService.get(getPartnerManagerUrl('/partners/v3?status=approved&partnerType=FTM_Provider', process.env.NODE_ENV));
        if (response) {
          const responseData = response.data;
          if (responseData && responseData.response) {
            const resData = responseData.response;
            setPartnerData(resData);
            setPartnerIdDropdownData(createDropdownData('partnerId', '', false, resData, t));
          } else {
            handleServiceErrors(response, setErrorCode, setErrorMsg);
          }
        } else {
          setErrorMsg(t('commons.errorInResponse'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        if (err.response?.status && err.response.status !== 401) {
          setErrorMsg(err.toString());
        }
      }
    };
    fetchData();
  }, []);

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1",
    dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10"
  };

  const clickOnSubmit = async () => {
    setIsSubmitClicked(true);
    setErrorCode("");
    setErrorMsg("");
    setDataLoaded(false);
    let request = createRequest(
      {
        ftpProviderId: partnerId,
        make: trimAndReplace(make),
        model: trimAndReplace(model)
      }
    );
    console.log(request);
    try {
      const response = await HttpService.post(getPartnerManagerUrl(`/ftpchipdetail`, process.env.NODE_ENV), request, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      if (response) {
        const responseData = response.data;
        console.log(responseData);
        if (responseData && responseData.response) {
          setFtpChipDetailId(responseData.response.id);
          const requiredDataForCertUpload = {
            partnerType: "FTM_Provider",
            uploadHeader: 'addFtm.uploadFtmCertHeader',
            reUploadHeader: 'addFtm.reUploadFtmCertHeader',
            successMessage: 'addFtm.uploadFtmCertSuccessMsg',
            isUploadFtmCertificate: true,
          }
          setUploadCertificateData(requiredDataForCertUpload);
          const requiredData = {
            title: "addFtm.addFtmChipDetails",
            backUrl: '/partnermanagement/ftm-chip-provider-services/ftm-list',
            header: "addFtm.addFtmSuccessHeader",
            description: "addFtm.addFtmSuccessMsg",
            description1: "addFtm.addFtmSuccessMsg1",
            subNavigation: 'ftmList.ftmChipProviderServices',
            customBtnName: "addFtm.uploadFtmCertificate",
          }
          setConfirmationData(requiredData);
          setAddFtmSuccess(true);
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg('addFtm.errorInAddingFtm')
      }
      setDataLoaded(true);
    } catch (err) {
      if (err.response?.status && err.response.status !== 401) {
        setErrorMsg(err.toString());
      }
      console.log("Error fetching data: ", err);
    }
    setIsSubmitClicked(false);
  };

  const clearForm = () => {
    setPartnerId("");
    setPartnerType("");
    setMake("");
    setModel("");
  };

  const isFormValid = () => {
    return partnerId && make.trim() && model.trim();
  };

  const clickOnCancel = () => {
    navigate('/partnermanagement/ftm-chip-provider-services/ftm-list')
  }

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter max-[450px]:text-xs relative`}>
      {!dataLoaded && (
        <LoadingIcon />
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
          )}
          <div className="flex-col mt-5">
            <div className="flex justify-between">
              <Title title='addFtm.addFtmChipDetails' subTitle='addFtm.listOfFtmChipDetails' backLink='/partnermanagement/ftm-chip-provider-services/ftm-list' />
            </div>
            {!addFtmSuccess ?
              <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
                <div className="px-[2.5%] py-[2%]">
                  <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                  <form onSubmit={handleFormSubmit}>
                    <div className="flex flex-col">
                      <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col max-[700px]:space-x-2">
                        <div className="flex-col w-[48%] max-[450px]:w-full">
                          <DropdownComponent
                            fieldName='partnerId'
                            dropdownDataList={partnerIdDropdownData}
                            onDropDownChangeEvent={onChangePartnerId}
                            fieldNameKey='requestPolicy.partnerId*'
                            placeHolderKey='createOidcClient.selectPartnerId'
                            selectedDropdownValue={partnerId}
                            styleSet={styles}
                            addInfoIcon
                            infoKey='addFtm.infoPartnerId'
                            id='add_ftm_partner_id'>
                          </DropdownComponent>
                        </div>
                        <div className="flex-col w-[48%] max-[450px]:w-full">
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                          <button disabled className="flex items-center justify-between w-full min-h-10 px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-dark-blue bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                            overflow-x-auto whitespace-normal no-scrollbar" type="button">
                            <span className={`w-full break-words ${partnerType ? 'text-dark-blue' : 'text-gray-400'} text-wrap text-start`}>{partnerType || t('commons.partnersHelpText')}</span>
                            <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                              <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                            </svg>
                          </button>
                        </div>
                      </div>
                      <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col max-[700px]:space-x-2">
                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.make')}<span className="text-crimson-red mx-1">*</span></label>
                          <input value={make} onChange={(e) => onChangeMake(e.target.value)} maxLength={36}
                            className="h-11 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                            placeholder={t('addFtm.enterMake')} id="add_ftm_make"/>
                        </div>
                        <div className="flex flex-col w-[48%] max-[450px]:w-full">
                          <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.model')}<span className="text-crimson-red mx-1">*</span></label>
                          <input value={model} onChange={(e) => onChangeModel(e.target.value)} maxLength={36}
                            className="h-11 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                            placeholder={t('addFtm.enterModel')} id="add_ftm_model"/>
                        </div>
                      </div>
                    </div>
                  </form>
                </div>
                <div className="border bg-medium-gray" />
                <div className="flex flex-row max-[560px]:flex-col px-[2%] py-5 justify-between max-[560px]:space-y-2">
                  <button id="add_ftm_clear_form" onClick={() => clearForm()} className={`w-40 max-[560px]:w-full h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2 max-[560px]:mr-0" : "ml-2 max-[560px]:ml-0"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                  <div className={`flex flex-row max-[560px]:flex-col space-x-3 max-[560px]:space-x-0 max-[560px]:space-y-2 w-full md:w-auto justify-end`}>
                    <button id="add_ftm_cancel_btn" onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 max-[560px]:w-full h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                    <button id="add_ftm_submit_btn" disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 max-[560px]:w-full h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                      {t('commons.submit')}
                    </button>
                  </div>
                </div>
              </div>
              : <>
                <Confirmation confirmationData={confirmationData} onClickFunction={clickOnUpload} />
                {
                  showPopup && (
                    <UploadCertificate header={t('addFtm.uploadFtmCertificate')} closePopup={closePopup} popupData={uploadCertificateData} request={uploadCertificateRequest} />
                  )
                }
              </>
            }
          </div>
        </>
      )}
      <BlockerPrompt blocker={blocker} />
    </div>
  )
}

export default AddFtm;