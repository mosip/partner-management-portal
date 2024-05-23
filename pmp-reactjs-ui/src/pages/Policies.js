import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ReactPaginate from 'react-paginate';
import { useTranslation } from 'react-i18next';
import rectangleGrid from '../svg/rectangle_grid.svg';
import sortIcon from '../svg/sort_icon.svg';
import backArrow from '../svg/back_arrow.svg';
import ErrorMessage from './common/ErrorMessage';
import LoadingIcon from "./common/LoadingIcon";
import Footer from "./common/Footer";
import { getPartnerManagerUrl, formatDate } from '../utils/AppUtils';
import { HttpService } from '../services/HttpService';

function Policies() {

  const { t } = useTranslation();
  const navigate = useNavigate();

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  const [isData, setIsData] = useState(true);
  const [filter, setFilter] = useState(false);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [policiesList, setPoliciesList] = useState([]);
  const [order, setOrder] = useState("ASC");

  const tableHeaders = [
    { id: "partnerId", head: 'partnerId' },
    { id: "partnerType", head: "partnerType" },
    { id: "policyGroup", head: "policyGroup" },
    { id: "policyName", head: "policyName" },
    { id: "createDate", head: "createdDate" },
    { id: "status", head: "status" },
  ];

  const tableValues = [
    { "partnerId": "P10001", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10002", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T03:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10003", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:16:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10004", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:14:42.422+00:00", "status": "Pending for Approval", "Action": "..." },
    { "partnerId": "P10005", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:13:42.422+00:00", "status": "Deactivated", "Action": "..." },
    { "partnerId": "P10006", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC1", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10007", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10008", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10009", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10010", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10011", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10012", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10013", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Pending for Approval", "Action": "..." },
    { "partnerId": "P10014", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Deactivated", "Action": "..." },
    { "partnerId": "P10015", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10016", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10017", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10018", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10019", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10020", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10021", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10022", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Pending for Approval", "Action": "..." },
    { "partnerId": "P10023", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Deactivated", "Action": "..." },
    { "partnerId": "P10024", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10025", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10026", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10027", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10028", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10029", "partnerType": "MISP Partner", "policyGroup": "Policy Group Name", "policyName": "KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Rejected", "Action": "..." },
    { "partnerId": "P10030", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Approved", "Action": "..." },
    { "partnerId": "P10031", "partnerType": "Authentication", "policyGroup": "Policy Group Name", "policyName": "Full KYC", "createDate": "2024-05-21T02:11:42.422+00:00", "status": "Pending for Approval", "Action": "..." }
  ];

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await HttpService.get(getPartnerManagerUrl('/partners/getAllPolicies', process.env.NODE_ENV));
        if (response != null) {
          const responseData = response.data;
          if (responseData.errors && responseData.errors.length > 0) {
            const errorCode = responseData.errors[0].errorCode;
            const errorMessage = responseData.errors[0].message;
            setErrorCode(errorCode);
            setErrorMsg(errorMessage);
            console.error('Error:', errorMessage);
          } else {
            const resData = responseData.response;
            const sortedData = resData.sort((a, b) => new Date(b.createDate) - new Date(a.createDate));
            setPoliciesList(tableValues);
            console.log('Response data:', sortedData);
          }
        } else {
          setErrorMsg(t('policies.errorInPoliciesList'));
        }
        setDataLoaded(true);
      } catch (err) {
        console.error('Error fetching data:', err);
        setErrorMsg(err);
      }
    };
    fetchData();
  }, [t]);

  const toggleSortOrder = (sortItem) => {
    if (order === 'ASC') {
      const sortedPolicies = [...policiesList].sort((a, b) =>
        a[sortItem].toLowerCase() > b[sortItem].toLowerCase() ? 1 : -1
      );
      setPoliciesList(sortedPolicies);
      setOrder("DSC")
    }
    if (order === 'DSC') {
      const sortedPolicies = [...policiesList].sort((a, b) => 
        a[sortItem].toLowerCase() < b[sortItem].toLowerCase() ? 1 : -1
      );
      setPoliciesList(sortedPolicies);
      setOrder("ASC")
    }
  };

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  function bgOfStatus(status) {
    if (status === "Approved") {
      return ("bg-[#D1FADF] text-[#155E3E]")
    }
    else if (status === "Rejected") {
      return ("bg-[#FAD6D1] text-[#5E1515]")
    }
    else if (status === "Pending for Approval") {
      return ("bg-[#FEF1C6] text-[#6D1C00]")
    }
    else if (status === "Deactivated") {
      return ("bg-[#EAECF0] text-[#525252]")
    }
  }

  const [firstIndex, setFirstIndex] = useState(0);
  const recordsPerPage = 10;
  const lastIndex = firstIndex + recordsPerPage;
  const records = policiesList.slice(firstIndex, lastIndex);
  const pageCount = Math.ceil(policiesList.length / recordsPerPage);                        //   This  part related to Pagination logic
  const onHandelPageChange = (event) => {
    const newIndex = (event.selected * recordsPerPage) % policiesList.length;               //    Respective functions are at bottom
    setFirstIndex(newIndex);
  };
  const [previous, setPrevious] = useState(false);
  const [next, setNext] = useState(false);

  return (
    <div className="flex-col w-full p-5 bg-anti-flash-white h-full font-inter">
      {!dataLoaded && (
        <LoadingIcon></LoadingIcon>
      )}
      {dataLoaded && (
        <>
          {errorMsg && (
            <div className="flex justify-end max-w-7xl">
              <div className="flex justify-between items-center max-w-96 min-h-14 min-w-72 bg-[#C61818] rounded-xl p-3 mr-10">
                <ErrorMessage errorCode={errorCode} errorMessage={errorMsg} clickOnCancel={cancelErrorMsg}></ErrorMessage>
              </div>
            </div>
          )}
          <div className="flex-col ml-1">
            <div className="flex justify-between mb-5">
              <div className="flex space-x-4">
                <img src={backArrow} alt="" onClick={() => moveToHome()} className="mt-1 cursor-pointer" />
                <div className="flex-col mt-4">
                  <h1 className="font-bold text-lg text-md text-blue-900">{t('policies.requestAPolicy')}</h1>
                  <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                    {t('policies.home')}
                  </p>
                </div>
              </div>

              {isData ?
                <button type="button" className="h-[50px] text-sm font-medium px-7 text-white bg-tory-blue rounded-md">
                  {t('policies.requestPolicyBtn')}
                </button>
                : null
              }
            </div>

            <div className="flex-col justify-center ml-1 h-full">
              {!isData
                ?
                <div className="bg-white w-full mt-3 rounded-lg shadow-lg items-center">
                  <div className="flex justify-between py-2 pt-4 text-xs font-medium text-gray-500">
                    <div className="flex sm:gap-x-7 md:gap-x-16 lg:gap-x-36">
                      <h6 className="ml-5">{t('policies.partnerId')}</h6>
                      <h6>{t('policies.partnerType')}</h6>
                      <h6>{t('policies.policyName')}</h6>
                    </div>
                    <div className='flex sm:gap-x-7 md:gap-x-16 lg:gap-x-40  mr-6'>
                      <h6>{t('policies.status')}</h6>
                      <h6>{t('policies.action')}</h6>
                    </div>
                  </div>

                  <hr className="h-px mx-3 bg-gray-200 border-0" />

                  <div className="flex items-center justify-center p-24">
                    <div className="flex-col items-center">
                      <img src={rectangleGrid} alt="" />
                      <button type="button" className="text-white mt-8 ml-16 bg-tory-blue rounded-md text-xs px-5 py-3">
                        {t('policies.requestPolicyBtn')}
                      </button>
                    </div>
                  </div>
                </div>
                :
                <>
                  <div className="bg-white w-full mt-3 rounded-t-xl shadow-lg">
                    <div className="flex justify-between pl-3 items-center">
                      <p className=" font-bold text-blue-900 text-md ml-4 my-6">
                        {t('policies.listOfPolicies')}
                      </p>
                      <button onClick={() => setFilter(!filter)} type="button" className={`flex justify-center items-center w-[12%] text-sm py-3 mx-4 text-blue-700 border border-blue-700 font-semibold rounded-md text-center
                        ${filter ? 'bg-blue-800 text-white' : 'text-blue-700'}`}
                      >
                        {t('policies.filterBtn')}
                        <svg
                          xmlns="http://www.w3.org/2000/svg" className={`${filter ? 'rotate-180 text-white' : null} ml-2`}
                          width="10" height="8" viewBox="0 0 10 8">
                          <path id="Polygon_8"
                            data-name="Polygon 8"
                            d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                            transform="translate(10 8) rotate(180)" fill={`${filter ? '#ffff' : '#1447b2'}`} />
                        </svg>
                      </button>
                    </div>

                    <hr className="h-0.5 mt-3 bg-gray-200 border-0" />

                    <div>
                      <table className="table-auto mx-5 lg:w-[96%]">
                        <thead>
                          <tr>
                            {tableHeaders.map((header, index) => {
                              return (
                                <th key={index} className="py-4 text-sm font-medium text-gray-500 lg:w-[15%]">
                                  <div className="mx-2 flex gap-x-1 items-center">
                                    {t('policies.' + header.head)}
                                    <img
                                      src={sortIcon} className="cursor-pointer"
                                      onClick={() => toggleSortOrder(header.id)} alt=""
                                    />
                                  </div>
                                </th>
                              )
                            })}
                            <th className="text-sm font-medium text-gray-500">
                              {t('policies.action')}
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          {records.map((partner, index) => {
                            return (
                              <tr key={index} className={`border-y-2 text-xs text-[#191919] font-semibold ${partner.status === "Deactivated" ? "text-gray-400" : "text-[#191919]"}`}>
                                <td className="px-2">{partner.partnerId}</td>
                                <td className="px-2">{partner.partnerType}</td>
                                <td className="px-2">{partner.policyGroup}</td>
                                <td className="px-2">{partner.policyName}</td>
                                <td className="px-2">{formatDate(partner.createDate, 'dateTime')}</td>
                                <td className="">
                                  <div className={`${bgOfStatus(partner.status)}flex w-fit py-1.5 px-2 m-3 text-xs rounded-md`}>
                                    {partner.status}
                                  </div>
                                </td>
                                <td className="text-center">...</td>
                              </tr>
                            )
                          })
                          }
                        </tbody>
                      </table>
                    </div>
                  </div>
                  <div className="flex justify-between bg-white items-center h-9 w-full m-0.5 p-8 rounded-b-md shadow-lg">
                    <div></div>

                    <ReactPaginate
                      breakLabel="..."
                      previousLabel={<svg onClick={perviousPage}
                        xmlns="http://www.w3.org/2000/svg"
                        width="28" height="28" viewBox="0 0 32 32">
                        <g id="Group_58361" data-name="Group 58361" transform="translate(-438.213 -745)">
                          <g id="Rectangle_15" data-name="Rectangle 15" transform="translate(438.213 745)"
                            fill="#fff" stroke={previous ? "#1447b2" : "#bababa"} strokeWidth="1">
                            <rect width="32" height="32" rx="6" stroke="none" />
                            <rect x="0.5" y="0.5" width="31" height="31" rx="5.5" fill="none" />
                          </g>
                          <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                            d="M5.68,0,0,5.679,1.018,6.7,5.68,2.011l4.662,4.662,1.018-1.018Z"
                            transform="translate(450.214 766.359) rotate(-90)" fill={previous ? "#1447b2" : "#bababa"} />
                        </g>
                      </svg>}
                      nextLabel={<svg onClick={nextPage}
                        xmlns="http://www.w3.org/2000/svg"
                        width="28" height="28" viewBox="0 0 32 32">
                        <g id="Group_58360" data-name="Group 58360" transform="translate(-767.213 -745)">
                          <g id="Rectangle_16" data-name="Rectangle 16" transform="translate(767.213 745)"
                            fill="#fff" stroke={next ? "#1447b2" : "#bababa"} strokeWidth="1">
                            <rect width="32" height="32" rx="6" stroke="none" />
                            <rect x="0.5" y="0.5" width="31" height="31" rx="5.5" fill="none" />
                          </g>
                          <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                            d="M17.68,23.3,12,17.618,13.018,16.6l4.662,4.686,4.662-4.662,1.018,1.018Z"
                            transform="translate(763.613 778.68) rotate(-90)" fill={next ? "#1447b2" : "#bababa"} />
                        </g>
                      </svg>}
                      onPageChange={onHandelPageChange}
                      pageRangeDisplayed={3}
                      pageCount={pageCount}
                      renderOnZeroPageCount={null}
                      containerClassName="flex gap-x-4 mx-4 items-center"
                      pageLinkClassName={`text-blue-700 font-semibold text-xs`}
                      activeLinkClassName='text-gray-100 bg-blue-700 py-[18%] px-3 rounded-md'
                      breakClassName='text-blue-700 text-md'
                    />

                    <div className="flex items-center gap-x-3">
                      <h6 className="text-gray-500 text-xs">{t('policies.itemsPerPage')}</h6>
                      <div className="flex justify-between w-10 h-6 items-center text-xs border-2 px-1 rounded-md border-indigo-400 text-indigo-600 font-medium">
                        <p>
                          {records.length}
                        </p>
                        <svg className="cursor-pointer"
                          xmlns="http://www.w3.org/2000/svg"
                          width="10.359" height="5.697" viewBox="0 0 11.359 6.697">
                          <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                            d="M17.68,23.3,12,17.618,13.018,16.6l4.662,4.686,4.662-4.662,1.018,1.018Z"
                            transform="translate(-12 -16.6)" fill="#1447b2" />
                        </svg>
                      </div>
                    </div>
                  </div>
                </>
              }
            </div>
          </div>
          <Footer></Footer>
        </>
      )}

    </div>
  )

  function perviousPage() {
    setPrevious(true);                                  //   Functions related to pagination 
    setNext(false);                                      //  to handle previous & next
  }
  function nextPage() {
    setNext(true);
    setPrevious(false);
  }
}

export default Policies;