import { useTranslation } from "react-i18next";
import rectangleGrid from "../../svg/rectangle_grid.svg";

function EmptyList({tableHeaders, showCustomButton, customButtonName, onClickButton}) {
    const { t } = useTranslation();
    return (
        <>
            <hr className="h-0.5 bg-gray-200 border-0" />
            <div className="flex justify-between mt-5">
                <div className="flex w-full justify-between font-[400] text-[14px] m-auto overflow-x-auto no-scrollbar">
                    { tableHeaders.map((header, index) => (
                        <h6 key={index} className="mx-5">
                            {t(header.headerNameKey)}
                        </h6>
                    ))}
                </div>
            </div>
            
            <hr className="h-px mx-3 my-2 bg-gray-200 border-0" />
            
            <div className="flex items-center justify-center p-24">
                <div className="flex flex-col items-center">
                    {/* Ensure rectangleGrid has a valid import path and alt text for accessibility */}
                    <img src={rectangleGrid} alt="No data available icon" />
                    { showCustomButton ?
                        <button id='create_policy_group_btn' type="button" onClick={onClickButton}
                            className={`text-white font-semibold mt-8 w-[75%] bg-tory-blue rounded-md text-sm mx-8 py-3`}>
                            {t(customButtonName)}
                        </button>
                        :
                        <p className="text-[#A1A1A1] mt-3">{t("partnerList.noResultsFound")}</p>
                    }
                </div>
            </div>
        </>
    );
}

export default EmptyList;