import { getUserProfile } from '../../services/UserProfileService';
import cancelIcon from '../../svg/cancel_icon.svg';
import { isLangRTL, onPressEnterKey } from '../../utils/AppUtils';

function SuccessMessage({ successMsg, clickOnCancel, customStyle}) {
    
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    return (
        <div className={`${customStyle ? customStyle.outerDiv : `flex justify-end max-w-7xl my-5 absolute ${isLoginLanguageRTL ? "left-0.5" : "right-0.5"}`}`}>
            <div className={` bg-fruit-salad ${customStyle ? customStyle.innerDiv : 'flex justify-between items-center rounded-xl max-w-[35rem] min-h-14 min-w-72 p-4'}`}>
                <div className={`${isLoginLanguageRTL ? 'ml-6':'mr-6'} w-[90%]`}>
                    <p className="text-sm/4 text-white break-words font-inter" dangerouslySetInnerHTML={{ __html: successMsg }}/>
                </div>
                <div className={`${isLoginLanguageRTL ? 'ml-3 mr-5 left-2' : 'mr-3 ml-5 right-2'} absolute ${(customStyle && customStyle.cancelIcon) ? customStyle.cancelIcon : 'top-4  mt-1'}`}>
                    <img role='button' id='success_msg_close_icon' src={cancelIcon} alt="close" className="cursor-pointer" onClick={clickOnCancel} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => clickOnCancel())} />
                </div>
            </div>
        </div>
    );
}

export default SuccessMessage;