import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, onPressEnterKey } from '../../utils/AppUtils';

function FilterButtons({ listTitle, dataListLength, filter, onResetFilter, setFilter}) {

    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    return (
        <div className="flex w-full p-2">
            <div className="flex w-full pl-[2%] pt-1 items-center justify-start font-semibold text-dark-blue text-base" >
            {t(listTitle) + ' (' + dataListLength + ")"}
            </div>
            <div className="w-full flex justify-end relative items-center">
            {filter && <p id='filter_reset_btn' onClick={onResetFilter} type="button" tabIndex="0" onKeyPress={(e) => onPressEnterKey(e,onResetFilter)}
                className={`flex ${isLoginLanguageRTL ? "ml-[8%]" : "mr-[8%]"} mt-1.5 justify-center items-center text-sm h-0 font-semibold text-center text-tory-blue cursor-pointer`}>
                {t('commons.resetFilter')}
            </p>}
            <button id='filter_btn' disabled={filter} onClick={() => setFilter(!filter)} type="button" className={`flex justify-center items-center w-[23%] text-sm py-2 mt-2 text-tory-blue border border-[#1447B2] font-semibold rounded-md text-center min-w-fit px-2
                ${filter ? 'border-[#A5A5A5] bg-[#A5A5A5] text-white cursor-auto' : 'text-tory-blue bg-white'} ${isLoginLanguageRTL ? "mr-3" : "ml-3"}`}>
                {t('commons.filterBtn')}
                <svg
                xmlns="http://www.w3.org/2000/svg" className={`${filter ? 'rotate-180 text-white' : null} ${isLoginLanguageRTL ? "mr-2" : "ml-2"}`}
                width="10" height="8" viewBox="0 0 10 8">
                <path id="Polygon_8"
                    data-name="Polygon 8"
                    d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                    transform="translate(10 8) rotate(180)" fill={`${filter ? '#ffff' : '#1447b2'}`} />
                </svg>
            </button>
            </div>
        </div>
    )
}

 export default FilterButtons;