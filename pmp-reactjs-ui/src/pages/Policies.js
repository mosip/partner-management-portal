import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

function Policies() {

  const { t } = useTranslation();
  const navigate = useNavigate();

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  const [isData, setIsData] = useState(true);
  const [filter, setFilter] = useState(false);
  const [statusBg, setStatusBg] = useState('');
  const [viewOpt, setViewOpt] = useState(false);

  const titles = ["Partner ID", "Partner Type", "Policy Group", "Policy Name", "Create Data"];
  const filterTitles = [
    { "header": t('policies.partnerType'), "placeHolder": t('policies.selectPartnerType') },
    { "header": t('policies.policyGroup'), "placeHolder": t('policies.selectPolicyGroup') },
    { "header": t('policies.partnerName'), "placeHolder": t('policies.selectPolicyName') },
    { "header": t('policies.status'), "placeHolder": t('policies.approved') }
  ];

  const tableValues = [
    { "Ptype": "Authentication", "PolicyName": "Full KYC", "status": "Approved", "Action": "..." },
    { "Ptype": "MISP Partner", "PolicyName": "KYC", "status": "Rejected", "Action": "..." },
    { "Ptype": "Authentication", "PolicyName": "Full KYC", "status": "Approved", "Action": "..." },
    { "Ptype": "Authentication", "PolicyName": "Full KYC", "status": "Pending for Approval", "Action": "..." },
    { "Ptype": "MISP Partner", "PolicyName": "KYC", "status": "Deactivated", "Action": "..." },
    { "Ptype": "MISP Partner", "PolicyName": "KYC", "status": "Approved", "Action": "..." },
    { "Ptype": "Authentication", "PolicyName": "Full KYC", "status": "Approved", "Action": "..." },
    { "Ptype": "MISP Partner", "PolicyName": "KYC", "status": "Rejected", "Action": "..." },
    { "Ptype": "Authentication", "PolicyName": "Full KYC", "status": "Approved", "Action": "..." },
  ];

  const [prev, setPrev] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [next, setNext] = useState('');                         // 
  const recordsPerPage = 8;
  const lastIndex = currentPage * recordsPerPage;               //      This  part related to Pagination logic
  const firstIndex = lastIndex - recordsPerPage;                 //     Related functions are bottom
  const records = tableValues.slice(firstIndex, lastIndex);
  const nPage = Math.ceil(tableValues.length / recordsPerPage)
  const numbers = [...Array(nPage + 1).keys()].slice(1)          //

  return (
    <div className="flex-col w-full p-5 bg-anti-flash-white font-inter">
      <div className="flex-col ml-1">
        <div className="flex justify-between mb-5">
          <div className="flex space-x-4">
            <svg onClick={() => moveToHome()} className="mt-5 cursor-pointer"
              xmlns="http://www.w3.org/2000/svg"
              width="22.765" height="14.416" viewBox="0 0 22.765 17.416">
              <path
                id="keyboard_backspace_FILL0_wght200_GRAD0_opsz24"
                d="M168-676.306l-8-8,8-8,1.067,1.067-6.18,6.18h18.671v1.507H162.887l6.18,6.18Z"
                transform="translate(-159.293 693.015)" stroke="#000" strokeWidth="1.5" />
            </svg>

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
              <div className="flex justify-between p-3 text-xs font-normal text-gray-500">
                <div className="flex gap-x-48">
                  <h6 className="ml-5">{t('policies.partnerId')}</h6>
                  <h6>{t('policies.partnerType')}</h6>
                  <h6>{t('policies.policyName')}</h6>
                </div>
                <div className='flex space-x-20 mr-6'>
                  <h6>{t('policies.status')}</h6>
                  <h6>{t('policies.action')}</h6>
                </div>
              </div>

              <hr className="h-px mx-3 bg-gray-200 border-0" />

              <div className="flex items-center justify-center p-24">
                <div className="flex-col items-center">
                  <svg xmlns="http://www.w3.org/2000/svg"
                    width="273" height="132" viewBox="0 0 273 132">
                    <g id="Group_58184" data-name="Group 58184" transform="translate(-633 -323)">
                      <g id="Group_58183" data-name="Group 58183" transform="translate(0 5.431)">
                        <g id="page_under_construction" transform="translate(595.167 316.495)">
                          <path id="Path_155" data-name="Path 155" d="M348.036,207.189H159.343c-1.754,0-3.18-1.805-3.18-4.025s1.427-4.025,3.18-4.025H348.036c1.754,0,3.18,1.805,3.18,4.025S349.79,207.189,348.036,207.189Z" transform="translate(-105.33 -114.842)" fill="#e6e6e6" />
                          <path id="Path_175" data-name="Path 175" d="M360.375,176.189H121.586a4.025,4.025,0,1,1,0-8.049H360.375a4.025,4.025,0,0,1,0,8.049Z" transform="translate(-65.868 -104.635)" fill="#e6e6e6" />
                          <path id="Path_155384" data-name="Path 155384" d="M119.084,176.189h90.358c.84,0,1.523-1.805,1.523-4.025s-.683-4.025-1.523-4.025H119.084c-.84,0-1.523,1.805-1.523,4.025S118.245,176.189,119.084,176.189Z" transform="translate(86.705 -63.735)" fill="#e6e6e6" />
                          <path id="Path_179" data-name="Path 179" d="M333.4,238.189H200.586a4.025,4.025,0,0,1,0-8.049H333.4a4.025,4.025,0,0,1,0,8.049Z" transform="translate(-145.728 -125.048)" fill="#e6e6e6" />
                          <path id="Path_155385" data-name="Path 155385" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(148.079 -83.682)" fill="#e6e6e6" />
                          <path id="Path_155386" data-name="Path 155386" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(146.535 -143.682)" fill="#d0d0d0" />
                          <path id="Path_155387" data-name="Path 155387" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(104.535 -143.682)" fill="#d0d0d0" />
                          <path id="Path_155388" data-name="Path 155388" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(62.535 -143.682)" fill="#d0d0d0" />
                          <path id="Path_155389" data-name="Path 155389" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(20.535 -143.682)" fill="#d0d0d0" />
                          <path id="Path_155390" data-name="Path 155390" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(-21.465 -143.682)" fill="#d0d0d0" />
                          <path id="Path_155391" data-name="Path 155391" d="M118.084,176.189H149.07c.288,0,.522-1.805.522-4.025s-.234-4.025-.522-4.025H118.084c-.288,0-.522,1.805-.522,4.025S117.8,176.189,118.084,176.189Z" transform="translate(-63.465 -143.682)" fill="#d0d0d0" />
                        </g>
                      </g>
                      <g id="Rectangle_7160" data-name="Rectangle 7160" transform="translate(633 323)" fill="none" stroke="#d0d0d0" stroke-width="1">
                        <rect width="273" height="132" rx="20" stroke="none" />
                        <rect x="0.5" y="0.5" width="272" height="131" rx="19.5" fill="none" />
                      </g>
                    </g>
                  </svg>
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
                    <h3 className="text-xs font-semibold text-tory-blue mb-2">{t('policies.partnerId')}</h3>
                    <input type="text"
                      className="text-start border border-gray-800 text-gray-900 text-xs font-medium rounded-sm shadow-sm w-72 h-8 p-2.5"
                      placeholder={t('policies.enterPartnerID')} />
                  </div>
                  {filterTitles.map((filter, index) => {
                    return (
                      <div key={index} className="m-2">
                        <h3 className="text-xs font-semibold text-tory-blue mb-2">{filter.header}</h3>
                        <select className="border border-gray-800 text-gray-400 text-xs font-medium rounded-sm w-72 h-8 pl-2.5 focus:none">
                          <option selected>{`${filter.placeHolder}`}</option>
                          <option></option>
                        </select>
                      </div>
                    );
                  })}
                </div>}

              <div className="flex ml-5 text-xs gap-x-10 font-normal text-gray-500 items-center">
                {titles.map((header, index) => {
                  return (
                    <div key={index} className="flex items-center gap-x-1 m-1">
                      <h6 className="p-3">{header}</h6>
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="7" height="13" viewBox="0 0 7 13">
                        <g
                          id="Group_58195" data-name="Group 58195" transform="translate(-247 -461)">
                          <path id="Polygon_3" data-name="Polygon 3"
                            d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                            transform="translate(247 461)" fill="#6f6e6e" />
                          <path id="Polygon_4" data-name="Polygon 4"
                            d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                            transform="translate(254 474) rotate(180)" fill="#6f6e6e" />
                        </g>
                      </svg>
                    </div>);
                })}

                <div className="flex gap-x-24 ml-24">
                  <div className="flex items-center gap-x-2">
                    <h6>Status</h6>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="7" height="13" viewBox="0 0 7 13">
                      <g
                        id="Group_58195" data-name="Group 58195" transform="translate(-247 -461)">
                        <path id="Polygon_3" data-name="Polygon 3"
                          d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                          transform="translate(247 461)" fill="#6f6e6e" />
                        <path id="Polygon_4" data-name="Polygon 4"
                          d="M2.636,1.481a1,1,0,0,1,1.728,0L6.123,4.5A1,1,0,0,1,5.259,6H1.741A1,1,0,0,1,.877,4.5Z"
                          transform="translate(254 474) rotate(180)" fill="#6f6e6e" />
                      </g>
                    </svg>
                  </div>
                  <h6 className="ml-28">Action</h6>
                </div>
              </div>
              <div className="flex-col ml-5 text-xs font-semibold text-gray-800 items-center justify-evenly">
                {records.map((partner, index) => {
                  return (
                    <div key={index}>
                      <hr className="h-px bg-gray-200 border-0 mr-5" />
                      <div className="flex items-center ml-3 p-1 gap-x-9">
                        <p className="w-28">P88424932</p>
                        <p className="w-32">{partner.Ptype}</p>
                        <p className="w-32">Banking</p>
                        <p className="w-28">{partner.PolicyName}</p>
                        <p className="w-28">31/05/2023</p>
                        <div className="flex">
                          <div className="flex justify-between w-80 ml-16 pl-6 items-center">
                            <div className={`bg-green-200 text-green-600 px-3 py-1 font-semibold justify-center text-xs rounded-md`}>
                              {partner.status}
                            </div>
                            <div className="flex-col pb-2">
                              <p onClick={() => setViewOpt(!viewOpt)} className="font-bold text-2xl text-gray-500 cursor-pointer">
                                ...
                              </p>
                              {viewOpt &&
                                <div className="absolute font-medium px-7 py-2 bg-white shadow-md rounded-sm text-xs">
                                  View
                                </div>}
                            </div>
                          </div>
                        </div>
                      </div>
                      <hr className="bg-gray-200 border-0 mr-5" />
                    </div>
                  );
                })}
              </div>
            </div>
              <div className="flex justify-between bg-white items-center h-9 w-full m-0.5 p-8 rounded-b-md shadow-lg">
                <div></div>
                <nav>
                  <ul className="flex gap-x-4 items-center">
                    <li className={`cursor-pointer font-bold ${prev}`}>
                      <p className='page-link'
                        onClick={prevPage}>{'<'}</p>
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
                    <li className={`cursor-pointer font-bold ${next}`}>
                      <p className='page-link'
                        onClick={nextPage}>{'>'}</p>
                    </li>
                  </ul>
                </nav>
                <div className="flex items-center gap-x-3">
                  <h6 className="text-gray-500 text-xs">{t('policies.itemsPerPage')}</h6>
                  <p className="p-1 text-xs border-2 border-indigo-400 text-indigo-600 font-bold">
                    {records.length}
                  </p>
                </div>
              </div>
            </>
          }
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
      </div>
    </div>
  )

  function prevPage() {
    if (currentPage !== 1) {
      setCurrentPage(currentPage - 1)
    }
    setPrev('border-2 border-blue-500 px-0.5 text-blue-500')
    setNext('')
  }

  function changeCurrPage(id) {
    setCurrentPage(id)
    setPrev('')
    setNext('')
  }

  function nextPage() {
    if (currentPage !== nPage) {
      setCurrentPage(currentPage + 1)
    }
    setNext('border-2 border-blue-500 px-0.5 text-blue-500')
    setPrev('')
  }
}

export default Policies;