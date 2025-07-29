import ReactPaginate from 'react-paginate';
import { useRef, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getUserProfile } from '../../services/UserProfileService';
import { isLangRTL, handleMouseClickForDropdown, onPressEnterKey } from '../../utils/AppUtils';
import { FiChevronLeft, FiChevronRight } from 'react-icons/fi';
import PropTypes from 'prop-types';

function Pagination({ dataListLength, selectedRecordsPerPage, setSelectedRecordsPerPage, setFirstIndex, isServerSideFilter = false, getPaginationValues, isViewNotificationPage, isApplyFilterClicked, setIsApplyFilterClicked }) {
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [isItemsPerPageOpen, setIsItemsPerPageOpen] = useState(false);
    const [selectedPage, setSelectedPage] = useState(0);
    const [itemsPerPageOptions, setItemsPerPageOptions] = useState([8, 16, 24, 32]);
    const itemsCountSelectionRef = useRef(null);

    useEffect(() => {
        handleMouseClickForDropdown(itemsCountSelectionRef, () => setIsItemsPerPageOpen(false));
    }, [itemsCountSelectionRef]);

    useEffect(() => {
        if (isViewNotificationPage) {
            setItemsPerPageOptions([4, 8, 12, 16]);
        } else {
            let itemsPerPage = localStorage.getItem('itemsPerPage');
            if (itemsPerPage) {
                itemsPerPage = Number(itemsPerPage);
                setItemsPerPageOptions([itemsPerPage, itemsPerPage * 2, itemsPerPage * 3, itemsPerPage * 4]);
            }
        }
    }, []);

    useEffect(() => {
        if (isServerSideFilter) {
            getPaginationValues(selectedRecordsPerPage, selectedPage);
        }
    }, [selectedPage, selectedRecordsPerPage]);

    useEffect(() => {
        if (isApplyFilterClicked) {
            setSelectedPage(0);
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const handlePageChange = (event) => {
        setSelectedPage(event.selected);
        const newIndex = (event.selected * selectedRecordsPerPage) % dataListLength;
        if(!isServerSideFilter) {
            setFirstIndex(newIndex);
        }
    };
    const changeItemsPerPage = (num) => {
        setIsItemsPerPageOpen(false);
        setSelectedRecordsPerPage(num);
        if(!isServerSideFilter) {
            setFirstIndex(0);
        }
        setSelectedPage(0);
    };

    const isFirstPage = selectedPage === 0;
    const isLastPage = selectedPage === Math.ceil(dataListLength / selectedRecordsPerPage) - 1;

    const greyBtn = "text-[#707070] border-[#BABABA]";
    const blueBtn = "text-[#1447B2] border-[#1447B2] cursor-pointer";

    return (
        <div id='pagination_card' className="flex justify-between bg-[#FCFCFC] items-center h-9  mt-0.5 p-8 rounded-b-md shadow-md max-640:flex-col max-640:h-fit">
            <div></div>
            <ReactPaginate
                forcePage={selectedPage}
                onPageChange={handlePageChange}
                pageCount={Math.ceil(dataListLength / selectedRecordsPerPage)}
                pageRangeDisplayed={4}
                marginPagesDisplayed={1}
                breakLabel="..."
                containerClassName="flex items-center justify-center space-x-3"
                pageClassName="px-3 py-1.5 text-[#1447B2] cursor-pointer text-sm"
                activeClassName="bg-[#1447B2] text-white text-sm p-1.5 rounded-md"
                previousClassName={`p-1.5 border rounded-md ${isFirstPage ? greyBtn : blueBtn}`}
                nextClassName={`p-1.5 border rounded-md ${isLastPage ? greyBtn : blueBtn}`}
                breakClassName="px-2 py-1 text-[#1447B2]"
                previousLabel={<FiChevronLeft />}
                nextLabel={<FiChevronRight />}
            />
            <div className="flex items-center gap-x-3">
                <h6 id='items_per_page' className="text-gray-500 text-xs">{t('commons.itemsPerPage')}</h6>
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
                        <p id='selected_records_count' className='px-1'>
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

Pagination.propTypes = {
    dataListLength: PropTypes.number.isRequired,
    selectedRecordsPerPage: PropTypes.number.isRequired,
    setSelectedRecordsPerPage: PropTypes.func.isRequired,
    setFirstIndex: PropTypes.func,
    isServerSideFilter: PropTypes.bool,
    getPaginationValues: PropTypes.func,
    isViewNotificationPage: PropTypes.bool,
    isApplyFilterClicked: PropTypes.bool,
    setIsApplyFilterClicked: PropTypes.func,
};

export default Pagination;