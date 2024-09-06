import React, { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import { createDropdownData, createRequest, getPartnerManagerUrl, getPartnerTypeDescription, handleServiceErrors, isLangRTL } from "../../utils/AppUtils";
import Title from "../common/Title";
import { HttpService } from "../../services/HttpService";
import BlockerPrompt from "../common/BlockerPrompt";

function AddFtm() {
  const dropdownData = [
    { fieldCode: 'anilftmtest', fieldValue: 'anilftmtest' },
    { fieldCode: 'ftmtest3', fieldValue: 'ftmtest3' }
  ];
  const ftmPartnersData = [
    { isCertificateAvailable: true, partnerId: "anilftmtest", partnerType: "FTM_Provider" },
    { isCertificateAvailable: true, partnerId: "ftmtest3", partnerType: "FTM_Provider" }
  ];

  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [partnerData, setPartnerData] = useState(ftmPartnersData);
  const [partnerIdDropdownData, setPartnerIdDropdownData] = useState(dropdownData);
  const [partnerId, setPartnerId] = useState("");
  const [partnerType, setPartnerType] = useState("");
  const [make, setMake] = useState('');
  const [model, setModel] = useState('');
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  const [selectedFtmPartnerData, setSelectedFtmPartnerData] = useState({});
  let isCancelledClicked = false;

  const blocker = useBlocker(
    ({ currentLocation, nextLocation }) => {
      if (isSubmitClicked || isCancelledClicked) {
        setIsSubmitClicked(false);
        isCancelledClicked = false;
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

  }, [partnerId, make, model, isSubmitClicked]);

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const onChangePartnerId = async (fieldName, selectedValue) => {
    setPartnerId(selectedValue);
    const selectedPartner = partnerData.find(item => item.partnerId === selectedValue);
    if (selectedPartner) {
      setSelectedFtmPartnerData(selectedPartner);
      setPartnerType(getPartnerTypeDescription(selectedPartner.partnerType, t));
    }
  };

  const onChangeMake = (value) => {
    setMake(value);
  }

  const onChangeModel = (value) => {
    setModel(value);
  }

  const handleFormSubmit = (event) => {
    event.preventDefault();
  };

  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       setDataLoaded(false);
  //       const resData = await getAllApprovedFtmProviderIds(HttpService, setErrorCode, setErrorMsg, t);
  //       if (resData) {
  //         setPartnerData(resData);
  //         setPartnerIdDropdownData(createDropdownData('partnerId', '', false.resData, t));
  //       } else {
  //         setErrorMsg(t('commons.errorInResponse'));
  //       }
  //     } catch (err) {
  //       console.error('Error fetching data:', err);
  //     } finally {
  //       setDataLoaded(true);
  //     }
  //   };
  //   fetchData();
  // }, []);

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
        make: make.trim(),
        model: model.trim()
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
          const confirmationData = {
            title: "addFtm.addFtmChipDetails",
            backUrl: "/partnermanagement/ftmChipProviderServices/ftmList",
            header: "addFtm.addFtmSuccessHeader",
            description: "addFtm.addFtmSuccessMsg",
            subNavigation: 'ftmList.ftmChipProviderServices',
            uploadFtm: "addFtm.uploadFtmCertificate",
            ftmPartnerData: selectedFtmPartnerData,
            styleSet: {
              imgIconLtr: "ml-[38%] max-[450px]:mr-12",
              imgIconRtl: "mr-[31%] max-[450px]:mr-12"
            }
          }
          localStorage.setItem('confirmationData', JSON.stringify(confirmationData));
          navigate('/partnermanagement/ftmChipProviderServices/addFtmConfirmation')
        } else {
          handleServiceErrors(responseData, setErrorCode, setErrorMsg);
        }
      } else {
        setErrorMsg('addFtm.errorInAddingFtm')
      }
      setDataLoaded(true);
    } catch (err) {
      setErrorMsg(err);
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
    isCancelledClicked = true;
    navigate('/partnermanagement/ftmChipProviderServices/ftmList')
  }

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter max-[450px]:text-xs`}>
      {!dataLoaded && (
        <LoadingIcon />
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <div className={`flex justify-end max-w-7xl sm:max-w-xl mb-5 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
              <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 max-[450px]:min-w-40 max-[450px]:min-h-40 bg-[#C61818] rounded-xl p-3">
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
              </div>
            </div>
          )}
          <div className="flex-col mt-7">
            <div className="flex justify-between">
              <Title title='addFtm.addFtmChipDetails' subTitle='ftmList.ftmChipProviderServices' backLink='/partnermanagement/ftmChipProviderServices/ftmList' />
            </div>
            <div className="w-[100%] bg-snow-white mt-[1.5%] rounded-lg shadow-md">
              <div className="px-[2.5%] py-[2%]">
                <p className="text-base text-[#3D4468]">{t('requestPolicy.mandatoryFieldsMsg1')} <span className="text-crimson-red">*</span> {t('requestPolicy.mandatoryFieldsMsg2')}</p>
                <form onSubmit={handleFormSubmit}>
                  <div className="flex flex-col">
                    <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
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
                          infoKey='addFtm.infoPartnerId'>
                        </DropdownComponent>
                      </div>
                      <div className="flex-col w-[48%] max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                        <button disabled className="flex items-center justify-between w-full min-h-10 px-2 py-[0.63rem] border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-normal no-scrollbar" type="button">
                          {partnerId &&
                            <>
                              <span className="w-full break-all break-normal break-words text-wrap text-start">{t("partnerTypes.ftmProvider")}</span>
                              <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                              </svg>
                            </>
                          }
                        </button>
                      </div>
                    </div>
                    <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                      <div className="flex flex-col w-[48%] max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.make')}<span className="text-crimson-red mx-1">*</span></label>
                        <input value={make} onChange={(e) => onChangeMake(e.target.value)}
                          className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('addFtm.enterMake')} />
                      </div>
                      <div className="flex flex-col w-[48%] max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.model')}<span className="text-crimson-red mx-1">*</span></label>
                        <input value={model} onChange={(e) => onChangeModel(e.target.value)} maxLength={36}
                          className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('addFtm.enterModel')} />
                      </div>
                    </div>
                  </div>
                </form>
              </div>
              <div className="border bg-medium-gray" />
              <div className="flex flex-row max-[450px]:flex-col px-[2%] py-5 justify-between max-[450px]:space-y-2">
                <button onClick={() => clearForm()} className={`w-40 h-10 mr-3 border-[#1447B2] ${isLoginLanguageRTL ? "mr-2" : "ml-2"} border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.clearForm')}</button>
                <div className={`flex flex-row max-[450px]:flex-col space-x-3 max-[450px]:space-x-0 max-[450px]:space-y-2 w-full md:w-auto justify-end`}>
                  <button onClick={() => clickOnCancel()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md bg-white text-tory-blue text-sm font-semibold`}>{t('requestPolicy.cancel')}</button>
                  <button disabled={!isFormValid()} onClick={() => clickOnSubmit()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>
                    {t('commons.submit')}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </>
      )}
      <BlockerPrompt blocker={blocker} />
    </div>
  )
}

export default AddFtm;