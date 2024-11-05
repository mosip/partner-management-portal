import React from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { isLangRTL } from '../../../utils/AppUtils';
import { getUserProfile } from '../../../services/UserProfileService';

function DownloadJsonPopup({ title, downloadJsonFile, closePopUp, jsonData }) {
    const { t } = useTranslation();
    const navigate = useNavigate();
    
    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <div className='flex-col bg-white w-[45rem] min-h-[6rem] rounded-md relative top-[2.5rem]'>
                <div className='flex py-2 px-5 justify-between items-center'>
                    <h2 className='font-semibold text-sm'>{title}</h2>
                    <div className='flex gap-x-9 items-center'>
                        <button onClick={() => downloadJsonFile(jsonData)} className='text-xs px-[1.3rem] py-[0.8%] border font-normal rounded-md h-9 w-fix text-tory-blue bg-white border-blue-800 cursor-pointer'>
                            {t('commons.download')}
                        </button>
                        <p onClick={closePopUp} className='bg-gray-100 text-center h-6 w-5 text-sm font-normal rounded-[9rem] cursor-pointer'>X</p>
                    </div>
                </div>
                <hr className={`h-px w-full bg-gray-200 border-0`} />
                <div className='mx-6 my-2 max-h-[27rem] overflow-y-scroll'>
                    {jsonData}
                </div>
            </div>
        </div>
    )
}

export default DownloadJsonPopup;