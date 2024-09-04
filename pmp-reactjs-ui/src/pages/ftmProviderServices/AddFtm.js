import React, { useState, useEffect } from "react";
import { useNavigate, useBlocker } from "react-router-dom";
import { useTranslation } from "react-i18next";
import DropdownComponent from '../common/fields/DropdownComponent';
import { getUserProfile } from '../../services/UserProfileService';
import LoadingIcon from "../common/LoadingIcon";
import ErrorMessage from "../common/ErrorMessage";
import { isLangRTL } from "../../utils/AppUtils";
import Title from "../common/Title";
import { HttpService } from "../../services/HttpService";
import file from '../../svg/file_icon.svg';
import BlockerPrompt from "../common/BlockerPrompt";

function AddFtm() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [dataLoaded, setDataLoaded] = useState(true);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [partnerIdDropdownData, setPartnerIdDropdownData] = useState([]);
  const [partnerId, setPartnerId] = useState("");
  const [partnerType, setPartnerType] = useState("");
  const [makeLabel, setMakeLabel] = useState('');
  const [modelLabel, setModelLabel] = useState('');
  const [isSubmitClicked, setIsSubmitClicked] = useState(false);
  let isCancelledClicked = false;

  const blocker = useBlocker(
    ({ currentLocation, nextLocation }) => {
      if (isSubmitClicked || isCancelledClicked) {
        setIsSubmitClicked(false);
        isCancelledClicked = false;
        return false;
      }
      return (
        (partnerId !== "" || makeLabel !== "" || modelLabel !== "" || currentLocation.pathname !== nextLocation.pathname)
      )
    }
  )

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const onChangeMakeLabel = (value) => {
    setMakeLabel(value);
  }

  const onChangeModelLabel = (value) => {
    setModelLabel(value);
  }

  const handleFormSubmit = (event) => {
    event.preventDefault();
  };

  const styles = {
    outerDiv: "!ml-0 !mb-0",
    dropdownLabel: "!text-sm !mb-1",
    dropdownButton: "!w-full min-h-10 !rounded-md !text-base !text-start",
    selectionBox: "!top-10"
  };

  const clearForm = () => {
    setPartnerId("");
    setPartnerType("");
    setMakeLabel("");
    setModelLabel("");
  };

  const isFormValid = () => {
    return partnerId && partnerType && makeLabel && modelLabel;
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
                          // dropdownDataList={partnerIdDropdownData}
                          // onDropDownChangeEvent={onChangePartnerId}
                          fieldNameKey='requestPolicy.partnerId*'
                          placeHolderKey='createOidcClient.selectPartnerId'
                          // selectedDropdownValue={partnerId}
                          styleSet={styles}
                          addInfoIcon
                          infoKey='addFtm.infoPartnerId'>
                        </DropdownComponent>
                      </div>
                      <div className="flex-col w-[48%] max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('requestPolicy.partnerType')}<span className="text-crimson-red mx-1">*</span></label>
                        <button disabled className="flex items-center justify-between w-full h-auto px-2 py-2 border border-[#C1C1C1] rounded-md text-base text-vulcan bg-platinum-gray leading-tight focus:outline-none focus:shadow-outline
                          overflow-x-auto whitespace-normal no-scrollbar" type="button">
                          <span className="w-full break-all break-normal break-words text-wrap text-start">{t("partnerTypes.ftmProvider")}</span>
                          <svg className={`w-3 h-2 ml-3 transform 'rotate-0' text-gray-500 text-base`} xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                            <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m1 1 4 4 4-4" />
                          </svg>
                        </button>
                      </div>
                    </div>
                    <div className="flex flex-row justify-between space-x-4 max-[450px]:space-x-0 my-[1%] max-[450px]:flex-col">
                      <div className="flex flex-col w-[48%] max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.make')}<span className="text-crimson-red mx-1">*</span></label>
                        <input value={makeLabel} onChange={(e) => onChangeMakeLabel(e.target.value)}
                          className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('generateApiKey.enterNameForApiKey')} />
                      </div>
                      <div className="flex flex-col w-[48%] max-[450px]:w-full">
                        <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{t('addDevices.model')}<span className="text-crimson-red mx-1">*</span></label>
                        <input value={modelLabel} onChange={(e) => onChangeModelLabel(e.target.value)} maxLength={36}
                          className="h-12 px-2 py-3 border border-[#707070] rounded-md text-md text-dark-blue bg-white leading-tight focus:outline-none focus:shadow-outline overflow-x-auto whitespace-nowrap no-scrollbar"
                          placeholder={t('generateApiKey.enterNameForApiKey')} />
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
                  <button disabled={!isFormValid()} className={`${isLoginLanguageRTL ? "ml-2" : "mr-2"} w-11/12 md:w-40 h-10 border-[#1447B2] border rounded-md text-sm font-semibold ${isFormValid() ? 'bg-tory-blue text-white' : 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-not-allowed'}`}>{t('requestPolicy.submit')}</button>
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