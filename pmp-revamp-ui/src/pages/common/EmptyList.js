import { useTranslation } from "react-i18next";
import rectangleGrid from "../../svg/rectangle_grid.svg";

function EmptyList({tableHeaders, showCustomButton, customButtonName,buttonId, onClickButton, disableBtn}) {
    const { t } = useTranslation();
    return (
        <>
            <hr className="h-0.5 bg-gray-200 border-0" />
            <div className="flex justify-between mt-5">
                <div className="flex w-full justify-between text-sm font-semibold text-[#6F6E6E] px-2 m-auto overflow-x-auto no-scrollbar">
                    { tableHeaders.map((header) => (
                        <h6 id={header.id} key={header.id} className="mx-[0.7rem]">
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
                        <button id={buttonId} type="button" onClick={onClickButton} disabled={disableBtn}
                            className={`font-semibold mt-8 w-[75%] ${disableBtn ? "bg-gray-400 opacity-55" : "bg-tory-blue text-white"} rounded-md text-sm mx-8 py-3`}>
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