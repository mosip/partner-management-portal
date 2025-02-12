import { React, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import FocusTrap from 'focus-trap-react';

function ViewPolicyPopup({ title, downloadJsonFile, closePopUp, jsonData }) {
    const { t } = useTranslation();
    const previewData = JSON.stringify(jsonData, null, 2);

    useEffect(() => {
        document.body.style.overflow = "hidden";

        return () => {
            document.body.style.overflow = "auto";
        };
    }, []);

    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-[50%] z-50 font-inter cursor-default">
            <FocusTrap focusTrapOptions={{ initialFocus: false, allowOutsideClick: true }}>
                <div className='flex-col bg-white w-[45rem] max-h-[32rem] mx-[4%] mt-[5%] rounded-md relative'>
                    <div className='flex py-2 px-5 justify-between items-center'>
                        <h2 className='font-semibold text-sm'>{title}</h2>
                        <div className='flex gap-x-9 items-center'>
                            <button onClick={() => downloadJsonFile(previewData)} className='text-xs px-[1.3rem] py-[0.8%] border-2 font-semibold rounded-md h-9 w-fix text-tory-blue bg-white border-blue-800 cursor-pointer'>
                                {t('commons.download')}
                            </button>
                            <button onClick={closePopUp} className='bg-gray-100 text-center h-6 w-5 text-sm font-semibold rounded-[9rem] cursor-pointer'>
                                <p>X</p>
                            </button>
                        </div>
                    </div>
                    <hr className={`h-px w-full bg-gray-200 border-0`} />
                    <div className='mx-6 my-2 max-h-[27rem] overflow-y-scroll'>
                        <pre>{previewData}</pre>
                    </div>
                </div>
            </FocusTrap>
        </div>
    )
}

export default ViewPolicyPopup;