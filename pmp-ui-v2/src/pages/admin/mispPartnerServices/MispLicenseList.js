import React, { useState, useRef, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getUserProfile } from '../../../services/UserProfileService.js';
import {
    isLangRTL, handleMouseClickForDropdown, resetPageNumber, onClickApplyFilter, setPageNumberAndPageSize,
    getPartnerManagerUrl, handleServiceErrors, onResetFilter, formatDate, bgOfStatus, getStatusCode, onPressEnterKey,
    setSubmenuRef,
    createRequest
} from '../../../utils/AppUtils.js';
import ErrorMessage from '../../common/ErrorMessage.js';
import LoadingIcon from '../../common/LoadingIcon.js';
import EmptyList from '../../common/EmptyList.js';
import Title from '../../common/Title.js';
import viewIcon from "../../../svg/view_icon.svg";
import eyeIcon from "../../../svg/eye_icon.svg";
import disabledEyeIcon from "../../../svg/disable_eye_icon.svg";
import deactivateIcon from "../../../svg/deactivate_icon.svg";
import disableDeactivateIcon from "../../../svg/disable_deactivate_icon.svg";
import regenerateIcon from "../../../svg/regenerate_icon.svg";
import FilterButtons from '../../common/FilterButtons.js';
import SortingIcon from '../../common/SortingIcon.js';
import Pagination from '../../common/Pagination.js';
import CopyIdPopUp from '../../common/CopyIdPopup.js';
import { HttpService } from '../../../services/HttpService.js';
import MispLicenseFilter from './MispLicenseFilter.js';
import DeactivatePopup from '../../common/DeactivatePopup.js';

function MispLicenseList() {
    const navigate = useNavigate('');
    const { t } = useTranslation();
    const isLoginLanguageRTL = isLangRTL(getUserProfile().locale);
    const [errorCode, setErrorCode] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [dataLoaded, setDataLoaded] = useState(true);
    const [mispLicenseList, setMispLicenseList] = useState([]);
    const [expandFilter, setExpandFilter] = useState(false);
    const [order, setOrder] = useState("DESC");
    const [activeAscIcon, setActiveAscIcon] = useState("");
    const [activeDescIcon, setActiveDescIcon] = useState("createdDateTime");
    const [actionId, setActionId] = useState(-1);
    const [selectedRecordsPerPage, setSelectedRecordsPerPage] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
    const [sortFieldName, setSortFieldName] = useState("createdDateTime");
    const [sortType, setSortType] = useState("desc");
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState(sessionStorage.getItem('itemsPerPage') ? Number(sessionStorage.getItem('itemsPerPage')) : 8);
    const [fetchData, setFetchData] = useState(false);
    const [tableDataLoaded, setTableDataLoaded] = useState(true);
    const [totalRecords, setTotalRecords] = useState(0);
    const [resetPageNo, setResetPageNo] = useState(false);
    const [isFilterApplied, setIsFilterApplied] = useState(false);
    const [isApplyFilterClicked, setIsApplyFilterClicked] = useState(false);
    const [showActiveIndexLicenseKeyPopup, setShowActiveIndexLicenseKeyPopup] = useState(null);
    const [currentLicense, setCurrentLicense] = useState(null);
    const [selectedLicenseKey, setSelectedLicenseKey] = useState(null);
    const [deactivateLicenseRequest, setDeactivateLicenseRequest] = useState(null);
    const [showActiveIndexDeactivatePopup, setShowActiveIndexDeactivatePopup] = useState(null);
    const [filterAttributes, setFilterAttributes] = useState({
        partnerId: null,
        orgName: null,
        policyGroupName: null,
        policyName: null,
        mispLicenseKeyName: null,
        status: null,
    });
    const submenuRef = useRef([]);

    useEffect(() => {
        handleMouseClickForDropdown(submenuRef, () => setActionId(-1));
    }, [submenuRef]);

    const tableHeaders = [
        { id: "partnerId", headerNameKey: 'mispLicenseList.partnerId' },
        { id: "orgName", headerNameKey: 'mispLicenseList.orgName' },
        { id: "policyGroupName", headerNameKey: "mispLicenseList.policyGroup" },
        { id: "policyName", headerNameKey: "mispLicenseList.policyName" },
        { id: "mispLicenseKeyName", headerNameKey: "mispLicenseList.mispLicenseKeyName" },
        { id: "createdDateTime", headerNameKey: "mispLicenseList.creationDate" },
        { id: "expiryDateTime", headerNameKey: "mispLicenseList.expirationDate" },
        { id: "status", headerNameKey: "mispLicenseList.status" },
        { id: "mispLicenseKey", headerNameKey: "mispLicenseList.mispLicenseKey" },
        { id: "action", headerNameKey: 'mispLicenseList.action' }
    ];

    const fetchMispLicenseList = async () => {
        const queryParams = new URLSearchParams();
        queryParams.append('sortFieldName', sortFieldName);
        queryParams.append('sortType', sortType);
        queryParams.append('pageSize', pageSize);

        //reset page number to 0 if filter applied or page number is out of bounds
        const effectivePageNo = resetPageNumber(totalRecords, pageNo, pageSize, resetPageNo);
        queryParams.append('pageNo', effectivePageNo);
        setResetPageNo(false);

        if (filterAttributes.partnerId) queryParams.append('partnerId', filterAttributes.partnerId);
        if (filterAttributes.orgName) queryParams.append('orgName', filterAttributes.orgName);
        if (filterAttributes.policyGroupName) queryParams.append('policyGroupName', filterAttributes.policyGroupName);
        if (filterAttributes.policyName) queryParams.append('policyName', filterAttributes.policyName);
        if (filterAttributes.mispLicenseKeyName) queryParams.append('mispLicenseKeyName', filterAttributes.mispLicenseKeyName);
        if (filterAttributes.status) queryParams.append('status', filterAttributes.status);

        const url = `${getPartnerManagerUrl('/misp-licenses', process.env.NODE_ENV)}?${queryParams.toString()}`;
        try {
            fetchData ? setTableDataLoaded(false) : setDataLoaded(false);
            const response = await HttpService.get(url);
            if (response) {
                const responseData = response.data;
                if (responseData && responseData.response) {
                    const resData = responseData.response.data;
                    setTotalRecords(responseData.response.totalResults);
                    setMispLicenseList(resData);
                } else {
                    handleServiceErrors(responseData, setErrorCode, setErrorMsg);
                }
            } else {
                setErrorMsg(t('mispLicenseList.errorInMispLicenseList'));
            }
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
            setFetchData(false);
        } catch (err) {
            console.error('Error fetching data:', err);
            if (err.response?.status && err.response.status !== 401) {
                setErrorMsg(err.toString());
            }
            setFetchData(false);
            fetchData ? setTableDataLoaded(true) : setDataLoaded(true);
        }
    }

    useEffect(() => {
        fetchMispLicenseList();
    }, [sortFieldName, sortType, pageNo, pageSize]);

    useEffect(() => {
        if (isApplyFilterClicked && pageNo === 0) {
            fetchMispLicenseList();
            setIsApplyFilterClicked(false);
        }
    }, [isApplyFilterClicked]);

    const onApplyFilter = (updatedfilters) => {
        onClickApplyFilter(updatedfilters, setIsFilterApplied, setResetPageNo, setFetchData, setFilterAttributes, setIsApplyFilterClicked);
    };

    const getPaginationValues = (recordsPerPage, pageIndex) => {
        setPageNumberAndPageSize(recordsPerPage, pageIndex, pageNo, setPageNo, pageSize, setPageSize, setFetchData);
    };

    const sortAscOrder = (header) => {
        if (order !== 'ASC' || activeAscIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('ASC');
            setOrder("ASC");
            setActiveDescIcon("");
            setActiveAscIcon(header);
        }
    };

    const sortDescOrder = (header) => {
        if (order !== 'DESC' || activeDescIcon !== header) {
            setFetchData(true);
            setSortFieldName(header);
            setSortType('DESC');
            setOrder("DESC");
            setActiveDescIcon(header);
            setActiveAscIcon("");
        }
    };

    const isLicenseActivated = (status) => status?.toUpperCase() === "ACTIVE";

    const isLicenseDeactivated = (status) => status?.toUpperCase() === "INACTIVE";

    const openLicenseKeyPopUp = (license, index) => {
        if (isLicenseActivated(license.status)) {
            setCurrentLicense(license);
            setShowActiveIndexLicenseKeyPopup(index);
        }
    };

    const viewMispLicenseDetails = (license) => {
        setActionId(-1);
        sessionStorage.setItem('selectedMispLicenseKey', JSON.stringify(license));
        navigate('/partnermanagement/admin/misp-partner-services/view-misp-license-key');
    };

    const generateMispLicenseKey = () => {
        navigate('/partnermanagement/admin/misp-partner-services/generate-misp-license-key');
    };

    const regenerateMispLicenseKey = (license) => {
        setActionId(-1);
        sessionStorage.setItem('selectedMispLicenseKey', JSON.stringify(license));
        navigate('/partnermanagement/admin/misp-partner-services/regenerate-misp-license-key');
    };

    const deactivateLicenseKey = (license, index) => {
        if (isLicenseActivated(license.status)) {
            const request = createRequest({
                status: "INACTIVE"
            }, "mosip.pms.update.misp.license.patch", true);
            setActionId(-1);
            setSelectedLicenseKey(license);
            setDeactivateLicenseRequest(request);
            setShowActiveIndexDeactivatePopup(index);
            document.body.style.overflow = "hidden";
        }
    };

    const closeDeactivatePopup = () => {
        setSelectedLicenseKey({});
        setShowActiveIndexDeactivatePopup(null);
        document.body.style.overflow = "auto";
    };

    const onClickConfirmDeactivate = (deactivationResponse, licenseKey) => {
        if (deactivationResponse !== "") {
            setSelectedLicenseKey({});
            setShowActiveIndexDeactivatePopup(null);
            setMispLicenseList((prevList) =>
                prevList.map(misp =>
                    misp.mispLicenseId === licenseKey.mispLicenseId ? { ...misp, status: "INACTIVE" } : misp
                )
            );
        }
    };

    const cancelErrorMsg = () => {
        setErrorMsg("");
    };

    const styles = {
        loadingDiv: "!py-[20%]",
        outerDiv: "!bg-opacity-35"
    }

    return (
        <div className={`mt-2 w-[100%] ${isLoginLanguageRTL ? "mr-28 ml-5" : "ml-28 mr-5"} font-inter overflow-x-scroll`}>
            {!dataLoaded && (
                <LoadingIcon></LoadingIcon>
            )}
            {dataLoaded && (
                <>
                    {errorMsg && (
                        <ErrorMessage id='misp_license_list_error_msg' errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg} />
                    )}
                    <div className="flex-col mt-5">
                        <div className="flex justify-between mb-3">
                            <Title title='mispLicenseList.mispPartnerServices' backLink='/partnermanagement' />
                            {mispLicenseList.length > 0 ?
                                <button id='generate_misp_license_key_btn' onClick={() => generateMispLicenseKey()} className="h-10 text-sm font-semibold text-white px-7 rounded-md bg-tory-blue">
                                    {t('mispLicenseList.generateMispLicenseKey')}
                                </button>
                                : null
                            }
                        </div>
                        {!isFilterApplied && mispLicenseList.length === 0 ? (
                            <div className="bg-[#FCFCFC] w-full mt-3 rounded-lg shadow-lg items-center">
                                <EmptyList
                                    tableHeaders={tableHeaders}
                                    showCustomButton={true}
                                    customButtonName='mispLicenseList.generateMispLicenseKey'
                                    buttonId='generate_misp_license_key_btn'
                                    onClickButton={generateMispLicenseKey}
                                    disableBtn={false} />
                            </div>
                        ) : (
                            <div className={`bg-[#FCFCFC] w-full mt-1 rounded-t-xl shadow-lg pt-3 ${!tableDataLoaded && "py-6"}`}>
                                <FilterButtons
                                    listTitle='mispLicenseList.listOfMispLicenseKeys'
                                    dataListLength={totalRecords}
                                    filter={expandFilter}
                                    onResetFilter={onResetFilter}
                                    setFilter={setExpandFilter}
                                />
                                <hr className="h-0.5 mt-3 bg-gray-200 border-0" />
                                {expandFilter && (
                                    <MispLicenseFilter onApplyFilter={onApplyFilter} />
                                )}
                                {!tableDataLoaded ? (
                                    <LoadingIcon styleSet={styles} />
                                ) : (
                                    <>
                                        {isFilterApplied && mispLicenseList.length === 0 ?
                                            <EmptyList tableHeaders={tableHeaders} showCustomButton={false} />
                                            : (
                                                <>
                                                    <div className="mx-[1.5rem] overflow-x-scroll">
                                                        <table className="table-fixed">
                                                            <thead>
                                                                <tr>
                                                                    {tableHeaders.map((header, index) => {
                                                                        return (
                                                                            <th key={index} className={`py-4 text-sm font-semibold text-[#6F6E6E] ${header.id === "status" ? 'w-[13%] min-w-28' : 'w-[13%]'}`}>
                                                                                <div id={`${header.headerNameKey}_header`} className={`mx-2 flex gap-x-0 items-center ${isLoginLanguageRTL ? "text-right" : "text-left"}`}>
                                                                                    {t(header.headerNameKey)}
                                                                                    {(header.id !== "action") && (header.id !== "mispLicenseKey") && (
                                                                                        <SortingIcon
                                                                                            id={`${header.headerNameKey}_sorting_icon`}
                                                                                            headerId={header.id}
                                                                                            sortDescOrder={sortDescOrder}
                                                                                            sortAscOrder={sortAscOrder}
                                                                                            order={order}
                                                                                            activeSortDesc={activeDescIcon}
                                                                                            activeSortAsc={activeAscIcon}
                                                                                        />
                                                                                    )}
                                                                                </div>
                                                                            </th>
                                                                        );
                                                                    })}
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {mispLicenseList.map((license, index) => {
                                                                    const activated = isLicenseActivated(license.status);
                                                                    const deactivated = isLicenseDeactivated(license.status);
                                                                    return (
                                                                        <tr id={"misp_license_list_item" + (index + 1)} key={index}
                                                                            className={`border-t border-[#E5EBFA] ${activated ? 'cursor-pointer' : 'cursor-default'} text-[0.8rem] text-[#191919] font-semibold break-words ${deactivated ? "text-[#969696]" : "text-[#191919]"}`}>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{license.partnerId}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{license.orgName ? license.orgName : '-'}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{license.policyGroupName ? license.policyGroupName : '-'}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{license.policyName ? license.policyName : '-'}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{license.mispLicenseKeyName ? license.mispLicenseKeyName : '-'}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{formatDate(license.createdDateTime, "date")}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)} className="px-2">{formatDate(license.expiryDateTime, "date")}</td>
                                                                            <td onClick={() => activated && viewMispLicenseDetails(license)}>
                                                                                <div className={`${bgOfStatus(license.status)} flex min-w-fit w-14 justify-center py-1.5 px-2 mx-2 my-3 text-xs font-semibold rounded-md`}>
                                                                                    {getStatusCode(license.status, t)}
                                                                                </div>
                                                                            </td>
                                                                            <td className="px-2 cursor-default">
                                                                                <div className="flex justify-center">
                                                                                    {activated ?
                                                                                        <button className='cursor-pointer bg-transparent border-none p-0' id={'misp_license_show_copy_popup_btn' + (index + 1)} onClick={() => openLicenseKeyPopUp(license, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => openLicenseKeyPopUp(license, index))}>
                                                                                            <img src={eyeIcon} alt="" />
                                                                                        </button>
                                                                                        :
                                                                                        <img src={disabledEyeIcon} alt="" />
                                                                                    }
                                                                                    {showActiveIndexLicenseKeyPopup === index && (
                                                                                        <CopyIdPopUp closePopUp={() => setShowActiveIndexLicenseKeyPopup(null)} subtitle={currentLicense.partnerId} title={currentLicense.mispLicenseKeyName} id={currentLicense.mispLicenseKey} header='mispLicenseList.mispLicenseKey' styleSet={styles} removeCopyBtn={true} />
                                                                                    )}
                                                                                </div>
                                                                            </td>
                                                                            <td className="text-center cursor-default">
                                                                                <div ref={setSubmenuRef(submenuRef, index)}>
                                                                                    <button id={"misp_license_list_action_view" + (index + 1)} onClick={() => setActionId(index === actionId ? null : index)} className={`font-semibold mb-0.5 text-[#191919] cursor-pointer text-center`}>
                                                                                        ...
                                                                                    </button>
                                                                                    {actionId === index && (
                                                                                        <div className={`absolute w-[7%] z-50 bg-white text-xs font-semibold rounded-lg shadow-md border min-w-fit ${isLoginLanguageRTL ? "left-10 text-right" : "right-11 text-left"}`}>
                                                                                            <div role='button' className="flex justify-between hover:bg-gray-100" onClick={() => viewMispLicenseDetails(license)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => viewMispLicenseDetails(license))}>
                                                                                                <p id="misp_license_list_view_btn" className={`py-1.5 px-4 cursor-pointer text-[#3E3E3E] ${isLoginLanguageRTL ? "pl-10" : "pr-10"}`}>{t("partnerList.view")}</p>
                                                                                                <img src={viewIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                            </div>
                                                                                            <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                            <div role='button' className="flex justify-between hover:bg-gray-100 cursor-pointer" onClick={() => regenerateMispLicenseKey(license)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => regenerateMispLicenseKey(license))}>
                                                                                                <p id="misp_license_list_regenerate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} text-[#3E3E3E]`}>{t("mispLicenseList.regenerate")}</p>
                                                                                                <img src={regenerateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                            </div>
                                                                                            <hr className="h-px bg-gray-100 border-0 mx-1" />
                                                                                            <div role='button' className={`flex justify-between hover:bg-gray-100 ${activated ? 'cursor-pointer' : 'cursor-default'}`} onClick={() => deactivateLicenseKey(license, index)} tabIndex="0" onKeyDown={(e) => onPressEnterKey(e, () => deactivateLicenseKey(license, index))}>
                                                                                                <p id="misp_license_list_deactivate_btn" className={`py-1.5 px-4 ${isLoginLanguageRTL ? "pl-10" : "pr-10"} ${activated ? "text-[#3E3E3E]" : "text-[#A5A5A5]"}`}>{t("partnerList.deActivate")}</p>
                                                                                                <img src={activated ? deactivateIcon : disableDeactivateIcon} alt="" className={`${isLoginLanguageRTL ? "pl-2" : "pr-2"}`} />
                                                                                            </div>
                                                                                        </div>
                                                                                    )}
                                                                                    {showActiveIndexDeactivatePopup === index && (
                                                                                        <DeactivatePopup
                                                                                            closePopUp={closeDeactivatePopup}
                                                                                            onClickConfirm={(deactivationResponse) => onClickConfirmDeactivate(deactivationResponse, selectedLicenseKey)}
                                                                                            popupData={{ ...selectedLicenseKey, isDeactivateMispLicense: true }}
                                                                                            request={deactivateLicenseRequest}
                                                                                            headerMsg="deactivateMispLicense.title"
                                                                                            descriptionMsg="deactivateMispLicense.description"
                                                                                            headerKeyName={selectedLicenseKey.mispLicenseKeyName ? selectedLicenseKey.mispLicenseKeyName : '-'}
                                                                                        />
                                                                                    )}
                                                                                </div>
                                                                            </td>
                                                                        </tr>
                                                                    );
                                                                })}
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </>
                                            )}
                                    </>
                                )}
                                <Pagination
                                    dataListLength={totalRecords}
                                    selectedRecordsPerPage={selectedRecordsPerPage}
                                    setSelectedRecordsPerPage={setSelectedRecordsPerPage}
                                    isServerSideFilter={true}
                                    getPaginationValues={getPaginationValues}
                                    isApplyFilterClicked={isApplyFilterClicked}
                                    setIsApplyFilterClicked={setIsApplyFilterClicked}
                                />
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    );
}

export default MispLicenseList;
