import React, { useEffect, useState, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../services/UserProfileService';
import {
  isLangRTL, handleServiceErrors, getPartnerManagerUrl, formatDate, getStatusCode,
  handleMouseClickForDropdown, toggleSortDescOrder, toggleSortAscOrder, createRequest, bgOfStatus,
  onPressEnterKey
} from '../../utils/AppUtils';
import ErrorMessage from '../common/ErrorMessage';
import Title from '../common/Title';
import LoadingIcon from '../common/LoadingIcon';
import rectangleGrid from '../../svg/rectangle_grid.svg';

function FtmList() {
  const navigate = useNavigate('');
  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);


  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  const addFtm = () => {
    navigate('/partnermanagement/ftmChipProviderServices/addFtm');
  };

  return (
    <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
      <>
        {errorMsg && (
          <div className={`flex justify-end max-w-7xl mb-5 mt-2 absolute ${isLoginLanguageRTL ? "left-0" : "right-2"}`}>
            <div className="flex justify-between items-center max-w-[35rem] min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 z-10">
              <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
            </div>
          </div>
        )}
        <div className="flex-col mt-7">
          <div className="flex justify-between mb-5">
            <Title title='ftmList.ftmChipProviderServices' backLink='/partnermanagement' />
            {/* {
              <button onClick={() => addFtm()} type="button" className="h-10 text-sm font-semibold px-7 text-white bg-tory-blue rounded-md">
                {t('devicesList.addDevices')}
              </button>
            } */}
          </div>
          <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
            {
              <div className="flex justify-between py-2 px-2 pt-4 text-sm font-semibold text-[#6F6E6E]">
                <div className={`flex w-full justify-between`}>
                  <h6 className="px-2 mx-2">{t('devicesList.deviceType')}</h6>
                  <h6 className="px-2 mx-2">{t('devicesList.deviceSubType')}</h6>
                  <h6 className="px-2 mx-2">{t('devicesList.make')}</h6>
                  <h6 className="px-2 mx-2">{t('devicesList.model')}</h6>
                  <h6 className="px-2 mx-2">{t('devicesList.status')}</h6>
                  <h6 className="px-2 mx-2 text-center">{t('devicesList.action')}</h6>
                </div>
              </div>
            }

            <hr className="h-px mx-3 bg-gray-200 border-0" />

            <div className="flex items-center justify-center p-24">
              <div className="flex flex-col justify-center">
                <img src={rectangleGrid} alt="" />
                <button onClick={() => addFtm()} type="button"
                  className={`font-semibold mt-8 rounded-md text-sm mx-8 py-3 bg-tory-blue text-white`}>
                  {t('devicesList.addDevices')}
                </button>
              </div>
            </div>
          </div>
        </div>
      </>
    </div>
  )
}

export default FtmList;