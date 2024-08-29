import ReactPaginate from 'react-paginate';
import { useRef, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, handleMouseClickForDropdown, onPressEnterKey } from '../../utils/AppUtils';
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai"; // icons form react-icons
import { IconContext } from "react-icons"; // for customizing icons

function Pagination({ dataList, selectedRecordsPerPage, setSelectedRecordsPerPage, setFirstIndex}) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
    const itemsPerPageOptions = [8, 16, 24, 32];
    const itemsCountSelectionRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
    }, [itemsCountSelectionRef]);

    const handlePageChange = (event) => {
        const newIndex = (event.selected * selectedRecordsPerPage) % dataList.length;
        setFirstIndex(newIndex);
    };
    const changeItemsPerPage = (num) => {
        setIsItemsPerPageOpen(false);
        setSelectedRecordsPerPage(num);
        setFirstIndex(0);
    };

    return (
        <div className="flex justify-between bg-[#FCFCFC] items-center h-9  mt-0.5 p-8 rounded-b-md shadow-md">
            <div></div>
            <ReactPaginate
            containerClassName={"pagination"}
            pageClassName={"page-item"}
            activeClassName={"active"}
            onPageChange={(event) => handlePageChange(event)}
            pageCount={Math.ceil(dataList.length / selectedRecordsPerPage)}
            breakLabel="..."
            previousLabel={
                <IconContext.Provider value={{ color: "#B8C1CC", size: "25px" }}>
                {isLoginLanguageRTL ? <AiFillRightCircle /> : <AiFillLeftCircle />}
                </IconContext.Provider>
            }
            nextLabel={
                <IconContext.Provider value={{ color: "#B8C1CC", size: "25px" }}>
                {isLoginLanguageRTL ? <AiFillLeftCircle /> : <AiFillRightCircle />}
                </IconContext.Provider>
            }
            />
            <div className="flex items-center gap-x-3">
                <h6 className="text-gray-500 text-xs">{t('commons.itemsPerPage')}</h6>
                <div ref={itemsCountSelectionRef}>
                    {isItemsPerPageOpen && (
                    <div className={`absolute bg-white text-xs text-tory-blue font-semibold rounded-lg border-[2px] -mt-[130px] duration-700`}>
                        {itemsPerPageOptions.map((num, i) => {
                        return (
                            <p key={i} onClick={() => changeItemsPerPage(num)} tabIndex="0" onKeyPress={(e)=> onPressEnterKey(e, () => changeItemsPerPage(num))}
                            className={`px-3 py-2 cursor-pointer ${selectedRecordsPerPage === num ? 'bg-[#F2F5FC]' : 'hover:bg-[#F2F5FC]'}`}>
                            {num}
                            </p>
                        )
                        })
                        }
                    </div>
                    )}
                    <div className="cursor-pointer flex justify-between w-10 h-6 items-center 
                        text-xs border px-1 rounded-md border-[#1447b2] bg-white text-tory-blue font-semibold"
                        onClick={() => setIsItemsPerPageOpen(!isItemsPerPageOpen)} tabIndex="0" onKeyPress={(e)=> onPressEnterKey(e, () => setIsItemsPerPageOpen(!isItemsPerPageOpen))}>
                        <p>
                            {selectedRecordsPerPage}
                        </p>
                        <svg className={`${isItemsPerPageOpen ? "rotate-180 duration-500" : "duration-500"}`}
                            xmlns="http://www.w3.org/2000/svg"
                            width="10.359" height="5.697" viewBox="0 0 11.359 6.697">
                            <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                            d="M17.68,23.3,12,17.618,13.018,16.6l4.662,4.686,4.662-4.662,1.018,1.018Z"
                            transform="translate(-12 -16.6)" fill="#1447b2" />
                        </svg>
                    </div>
                </div>
            </div>
        </div> 
    )
}

export default Pagination;