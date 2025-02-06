import ReactPaginate from 'react-paginate';
import { useRef, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, handleMouseClickForDropdown, onPressEnterKey } from '../../utils/AppUtils';
import { AiFillLeftCircle, AiFillRightCircle } from "react-icons/ai"; // icons form react-icons
import { IconContext } from "react-icons"; // for customizing icons

function Pagination({ dataListLength, selectedRecordsPerPage, setSelectedRecordsPerPage, setFirstIndex, isServerSideFilter = false, getPaginationValues }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);
    const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
    const [selectedPage, setSelectedPage] = useState(0);
    const [itemsPerPageOptions, setItemsPerPageOptions] = useState([8, 16, 24, 32]);
    const itemsCountSelectionRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
    }, [itemsCountSelectionRef]);

    useEffect(() => {
        let itemsPerPage = localStorage.getItem('itemsPerPage');
        if (itemsPerPage) {
            itemsPerPage = Number(itemsPerPage);
            setItemsPerPageOptions([itemsPerPage, itemsPerPage * 2, itemsPerPage * 3, itemsPerPage * 4]);
        }
    }, []);

    useEffect(() => {
        if (isServerSideFilter) {
            getPaginationValues(selectedRecordsPerPage, selectedPage);
        }
    }, [selectedPage, selectedRecordsPerPage]);

    const handlePageChange = (event) => {
        setSelectedPage(event.selected);
        const newIndex = (event.selected * selectedRecordsPerPage) % dataListLength;
        setFirstIndex(newIndex);
    };
    const changeItemsPerPage = (num) => {
        setIsItemsPerPageOpen(false);
        setSelectedRecordsPerPage(num);
        setFirstIndex(0);
    };

    return (
        <div id='pagination_card' className="flex justify-between bg-[#FCFCFC] items-center h-9  mt-0.5 p-8 rounded-b-md shadow-md max-640:flex-col max-640:h-fit">
            <div></div>
            <ReactPaginate
                containerClassName={"pagination"}
                pageClassName={"page-item"}
                activeClassName={"active"}
                onPageChange={(event) => handlePageChange(event)}
                pageCount={Math.ceil(dataListLength / selectedRecordsPerPage)}
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
                <div ref={itemsCountSelectionRef} className='relative min-w-fit w-10'
                    role='button' id='pagination_select_record_per_page' onClick={() => setIsItemsPerPageOpen(!isItemsPerPageOpen)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => setIsItemsPerPageOpen(!isItemsPerPageOpen))}>
                    {isItemsPerPageOpen && (
                        <div className={`absolute bg-white text-xs text-tory-blue font-semibold rounded-lg border-[2px] bottom-6 duration-700`}>
                            {itemsPerPageOptions.map((num, i) => {
                                return (
                                    <button id={'pagination_each_num_option' + (i + 1)} key={i} onClick={() => changeItemsPerPage(num)}
                                        className={`px-3 py-2 w-full cursor-pointer ${selectedRecordsPerPage === num ? 'bg-[#F2F5FC]' : 'hover:bg-[#F2F5FC]'}`}>
                                        {num}
                                    </button>
                                )
                            })
                            }
                        </div>
                    )}
                    <div className="cursor-pointer flex justify-between w-auto h-6 items-center 
                        text-xs border px-1 rounded-md border-[#1447b2] bg-white text-tory-blue font-semibold">
                        <p className='px-1'>
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