import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import rectangleGrid from '../svg/rectangle_grid.svg';
import sortIcon from '../svg/sort_icon.svg';
import backArrow from '../svg/back_arrow.svg';
import ErrorMessage from './ErrorMessage';
import { getUrl, formatDate } from '../utils/AppUtils';
import { HttpService } from '../services/HttpService';

function Policies() {

  const { t } = useTranslation();
  const navigate = useNavigate();

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  const [isData, setIsData] = useState(true);
  const [filter, setFilter] = useState(false);
  const [viewOpt, setViewOpt] = useState(false);
  const [errorCode, setErrorCode] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [dataLoaded, setDataLoaded] = useState(false);
  const [policiesList, setPoliciesList] = useState([]);

  const table_heads = ["Partner ID", "Partner Type", "Policy Group", "Policy Name", "Create Data"];
  const filterTitles = [
    { "header": t('policies.partnerType'), "placeHolder": t('policies.selectPartnerType') },
    { "header": t('policies.policyGroup'), "placeHolder": t('policies.selectPolicyGroup') },
    { "header": t('policies.policyName'), "placeHolder": t('policies.selectPolicyName') },
    { "header": t('policies.status'), "placeHolder": t('policies.approved') }
  ];

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await HttpService.get(getUrl('/partners/getAllPolicies', process.env.NODE_ENV));
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
                    setPoliciesList(resData);
                    console.log('Response data:', resData);
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

  const cancelErrorMsg = () => {
    setErrorMsg("");
  };

  function bgOfStatus(status) {
    if (status === "approved") {
      return ("bg-[#D1FADF] text-[#155E3E]")
    }
    else if (status === "rejected") {
      return ("bg-[#FAD6D1] text-[#5E1515]")
    }
    else if (status === "InProgress") {
      return ("bg-[#FEF1C6] text-[#6D1C00]")
    }
    else if (status === "Deactivated") {
      return ("bg-[#EAECF0] text-[#525252]")
    }
  }

  const [prev, setPrev] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [next, setNext] = useState(false);                         // 
  const recordsPerPage = 8;
  const lastIndex = currentPage * recordsPerPage;                 //      This  part related to Pagination logic
  const firstIndex = lastIndex - recordsPerPage;                  //     Related functions are bottom
  const records = policiesList.slice(firstIndex, lastIndex);
  const nPage = Math.ceil(policiesList.length / recordsPerPage)
  const numbers = [...Array(nPage + 1).keys()].slice(1)          //

  return (
    <div className="flex-col w-full p-5 bg-anti-flash-white font-inter">
      {!dataLoaded && (
        <div className="flex items-center justify-center h-4/5">

            <div role="status" className="flex items-center">
                <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                </svg>
                <p className="ml-3">{t('partnerCertificatesList.loading')}</p>
            </div>

        </div>
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
                  <h1 className="font-bold text-md text-blue-900">{t('policies.requestAPolicy')}</h1>
                  <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
                    {t('policies.home')}
                  </p>
                </div>
              </div>

              {isData ?
                <button type="button" className="text-sm px-5 h-10 text-white bg-tory-blue rounded-md">
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
                <><div className="bg-white w-full mt-3 rounded-tlg shadow-lg">
                  <div className="flex justify-between pl-5 pt-5 pr-2 items-center">
                    <p className=" font-bold text-blue-900 text-md ml-4">
                      {t('policies.listOfPolicies')}
                    </p>
                    <button onClick={() => setFilter(!filter)} type="button" className={`flex justify-center items-center ${filter ? 'bg-blue-800 text-white' : 'text-blue-700'} h-9 w-32 text-xs px-2 py-2 text-blue-700 border border-blue-700 font-semibold rounded-md text-center`}>
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
                  {filter &&
                    <div className="flex flex-wrap py-3 bg-gray-50 font-semibold text-xs pl-5">
                      <div className="m-3">
                        <h3 className="text-xs mb-2 text-[#1447b2]">
                          {t('policies.partnerId')}
                        </h3>
                        <input type="text"
                          className="text-start border border-gray-800 text-gray-900 text-xs rounded-md shadow-sm w-72 h-8 p-2.5"
                          placeholder={t('policies.enterPartnerID')} />
                      </div>
                      {filterTitles.map((filter, index) => {
                        return (
                          <div key={index} className="m-2">
                            <h3 className="text-xs mb-2 text-[#1447b2]">{filter.header}</h3>
                            <select className="border border-gray-800 text-gray-400 text-xs rounded-md w-72 h-8 pl-2.5">
                              <option selected>{`${filter.placeHolder}`}</option>
                              <option></option>
                            </select>
                          </div>
                        );
                      })
                      }
                    </div>
                  }
                  <div>
                    <table className="table-auto mx-5 lg:w-auto">
                      <thead>
                        <tr>
                          {table_heads.map((head, index) => {
                            return (
                              <th key={index} className="py-3 text-sm font-medium text-gray-500">
                                <div className="flex px-9 sm:px-6 md:px-5 lg:px-14 gap-x-1 items-center">
                                  {head}
                                  <img src={sortIcon} alt="" />
                                </div>
                              </th>
                            )
                          })
                          }
                          <th className="px-9 sm:px-6 md:px-5 lg:px-14 text-sm font-medium text-gray-500">
                            <div className="flex gap-x-1 items-center">
                              {t('policies.status')}
                              <img src={sortIcon} alt="" />
                            </div>
                          </th>
                          <th className="px-9 sm:px-6 md:px-5 lg:px-1 text-sm font-medium text-gray-500">
                            {t('policies.action')}
                          </th>
                        </tr>
                      </thead>
                      <tbody className="">
                        {records.map((partner, index) => {
                          return (
                            <tr key={index} className={`border-y-2 text-xs text-[#191919] font-semibold ${partner.status === "Deactivated" ? "text-gray-400" : "text-[#191919]"}`}>
                              <td className="px-9 sm:px-6 md:px-5 lg:px-14">{partner.partnerId}</td>
                              <td className="px-9 sm:px-6 md:px-5 lg:px-14">{partner.partnerType}</td>
                              <td className="px-9 sm:px-6 md:px-5 lg:px-14">{partner.policyGroup}</td>
                              <td className="px-9 sm:px-6 md:px-5 lg:px-14">{partner.policyName}</td>
                              <td className="px-9 sm:px-6 md:px-5 lg:px-14">{formatDate(partner.createDate, 'date')}</td>
                              <td className="flex font-semibold px-9 sm:px-6 md:px-5 lg:px-14">
                                <div className={`${bgOfStatus(partner.status)} py-1 px-3 my-3 text-xs rounded-md`}>
                                  {partner.status}
                                </div>
                              </td>
                              <td className="px-9 sm:px-6 md:px-5 lg:px-4">...</td>
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
                    <nav>
                      <ul className="flex gap-x-4 items-center">
                        <li className={`cursor-pointer`}>
                          <svg onClick={prevPage}
                            xmlns="http://www.w3.org/2000/svg"
                            width="32" height="32" viewBox="0 0 32 32">
                            <g id="Group_58361" data-name="Group 58361" transform="translate(-438.213 -745)">
                              <g id="Rectangle_15" data-name="Rectangle 15" transform="translate(438.213 745)"
                                fill="#fff" stroke={prev ? "#1447b2" : "#bababa"} strokeWidth="1">
                                <rect width="32" height="32" rx="6" stroke="none" />
                                <rect x="0.5" y="0.5" width="31" height="31" rx="5.5" fill="none" />
                              </g>
                              <path id="expand_more_FILL0_wght400_GRAD0_opsz48"
                                d="M5.68,0,0,5.679,1.018,6.7,5.68,2.011l4.662,4.662,1.018-1.018Z"
                                transform="translate(450.214 766.359) rotate(-90)" fill={prev ? "#1447b2" : "#bababa"} />
                            </g>
                          </svg>
                        </li>
                        {
                          numbers.map((n, i) => (
                            <li key={i} className='cursor-pointer'>
                              <p className={`text-xs font-semibold px-2 py-1 ${currentPage === n ? "bg-tory-blue text-white rounded-md" : "text-blue-600"}`}
                                onClick={() => changeCurrPage(n)}>
                                {n} </p>
                            </li>
                          ))
                        }
                        <li className={`cursor-pointer`}>
                          <svg onClick={nextPage}
                            xmlns="http://www.w3.org/2000/svg"
                            width="32" height="32" viewBox="0 0 32 32">
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
                          </svg>
                        </li>
                      </ul>
                    </nav>
                    <div className="flex items-center gap-x-3">
                      <h6 className="text-gray-500 text-xs">{t('policies.itemsPerPage')}</h6>
                      <div className="flex justify-between w-10 h-6 items-center text-xs border-2 px-1 rounded-md border-indigo-400 text-indigo-600 font-medium">
                        <p>
                          {records.length}
                        </p>
                        <svg
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
          </>
        )}
        <hr className="h-px ml-7 mt-7 bg-gray-200 border-0" />
        <div className="flex mt-7 ml-7 justify-between text-sm text-gray-400">
          <div>
            <p>2024 © MOSIP - {t('footer.allRightsReserved')} </p>
          </div>
          <div className="flex justify-between">
            <p className="mr-7">{t('footer.documentation')}</p>
            <p className="mr-7">{t('footer.mosipCommunity')}</p>
            <p className="mr-7">{t('footer.contactUs')}</p>
          </div>
        </div>
    </div>
  )

  function prevPage() {
    if (currentPage !== 1) {
      setCurrentPage(currentPage - 1)
    }
    setPrev(true)
    setNext(false)
  }

  function changeCurrPage(id) {
    setCurrentPage(id)
    setPrev(false)
    setNext(false)
  }

  function nextPage() {
    if (currentPage !== nPage) {
      setCurrentPage(currentPage + 1)
    }
    setNext(true)
    setPrev(false)
  }
}

export default Policies;