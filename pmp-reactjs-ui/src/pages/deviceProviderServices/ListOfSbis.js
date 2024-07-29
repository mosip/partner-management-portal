import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import Title from '../common/Title';
import { isLangRTL } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import rectangleGrid from '../../svg/rectangle_grid.svg';

function ListOfSbis() {

    const { t } = useTranslation();
    const navigate = useNavigate();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

    const addSbis = () => {
        navigate('/partnermanagement/deviceProviderServices/addSbis');
    };

    const styleForTitle = {
        backArrowIcon: "!mt-[4%]"
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} overflow-x-scroll font-inter`}>
            <>
                <div className="flex-col mt-7">
                    <div className="flex justify-between mb-5">
                        <Title title='deviceProviderServices.listOfSbisAndDevices' backLink='/partnermanagement' styleSet={styleForTitle} />
                    </div>
                    <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                        <div className="flex items-center justify-center p-24">
                            <div className="flex flex-col justify-center">
                                <img src={rectangleGrid} alt="" />
                                <button type="button" onClick={() => addSbis()}
                                    className={`text-white font-semibold mt-8 bg-tory-blue self-center rounded-md text-sm py-3 w-[60%]`}>
                                    {t('deviceProviderServices.addSbiDevices')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        </div>
    )
}

export default ListOfSbis;