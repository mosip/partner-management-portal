import { useNavigate } from 'react-router-dom';

function Dashboard() {

  const navigate = useNavigate();

  const partnerCertificate = () => {
    navigate('/partnermanagement/partnerCertificate')
  };

  return (
      <div className="w-screen p-5 bg-anti-flash-white h-full font-inter">
        <p className="mb-7 mt-8 ml-7 text-2xl font-semibold tracking-tight text-gray-700">
          Welcome User,
        </p>
        <div className="flex mt-2 ml-7 flex-wrap">
          <div className="w-[19rem] h-72 p-6 mr-3 mb-4 pt-20 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
            <div className="flex justify-center mb-5">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="40.618"
                height="40.618"
                viewBox="0 0 40.618 40.618"
              >
                <path
                  id="library_books_FILL0_wght200_GRAD0_opsz24"
                  d="M155.53-826.912h13.317V-833.3H155.53Zm0,12.832h16.633v-2.389H155.53Zm0-6.168h16.633v-2.389H155.53Zm-4.5,13.7a3.738,3.738,0,0,1-2.755-1.105,3.738,3.738,0,0,1-1.105-2.755V-836.14a3.738,3.738,0,0,1,1.105-2.755A3.738,3.738,0,0,1,151.028-840h25.731a3.738,3.738,0,0,1,2.755,1.105,3.739,3.739,0,0,1,1.105,2.755v25.731a3.738,3.738,0,0,1-1.105,2.755,3.738,3.738,0,0,1-2.755,1.105Zm0-2.389h25.731a1.405,1.405,0,0,0,1.011-.459,1.405,1.405,0,0,0,.459-1.011V-836.14a1.405,1.405,0,0,0-.459-1.011,1.405,1.405,0,0,0-1.011-.459H151.028a1.405,1.405,0,0,0-1.011.459,1.405,1.405,0,0,0-.459,1.011v25.731a1.405,1.405,0,0,0,.459,1.011A1.405,1.405,0,0,0,151.028-808.939Zm-7.168,9.557a3.738,3.738,0,0,1-2.755-1.105A3.738,3.738,0,0,1,140-803.241v-28.12h2.389v28.12a1.405,1.405,0,0,0,.459,1.011,1.405,1.405,0,0,0,1.011.459h28.12v2.389Zm5.7-38.229v0Z"
                  transform="translate(-140 840)"
                  fill="#1447b2"
                />
              </svg>
            </div>
            <div>
              <h5 className="mb-2 text-base font-semibold tracking-tight text-gray-600 ">
                Partner Type Selection
              </h5>
              <p className="mb-3 text-sm font-normal text-gray-400">
                Add / Manage new partner type
              </p>
            </div>
          </div>
          <div className="w-[19.625rem] h-72 p-6 mr-3 mb-4 pt-20 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
            <div className="flex justify-center mb-5">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="36.754"
                height="47.255"
                viewBox="0 0 36.754 47.255"
              >
                <path
                  id="upload_file_FILL0_wght200_GRAD0_opsz24"
                  d="M217.064-801.227h2.625V-813.55l5.513,5.513,1.858-1.873-8.684-8.684-8.684,8.684,1.873,1.858,5.5-5.5Zm-12.823,8.482a4.107,4.107,0,0,1-3.027-1.214A4.108,4.108,0,0,1,200-796.986v-38.773a4.108,4.108,0,0,1,1.214-3.027A4.108,4.108,0,0,1,204.241-840h20.7l11.814,11.814v31.2a4.108,4.108,0,0,1-1.214,3.027,4.107,4.107,0,0,1-3.027,1.214Zm19.387-34.129v-10.5H204.241a1.544,1.544,0,0,0-1.111.5,1.544,1.544,0,0,0-.5,1.111v38.773a1.544,1.544,0,0,0,.5,1.111,1.544,1.544,0,0,0,1.111.5h28.272a1.544,1.544,0,0,0,1.111-.5,1.544,1.544,0,0,0,.5-1.111v-29.888Zm-21-10.5v0Z"
                  transform="translate(-200 840)"
                  fill="#1347b2"
                />
              </svg>
            </div>
            <div>
              <h5 className="mb-2 text-base font-semibold tracking-tight text-gray-600 ">
                Organisation Users
              </h5>
              <p className="mb-3 text-sm font-normal text-gray-400">
                Upload and re-upload partner certificate, download MOSIP signed
                certificate.
              </p>
            </div>
          </div>
          <div onClick={() => partnerCertificate()} className="w-[19.625rem] h-72 p-6 mr-3 mb-4 pt-20 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
            <div className="flex justify-center mb-5">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="42.748"
                height="47.184"
                viewBox="0 0 42.748 47.184"
              >
                <path
                  id="quick_reference_FILL0_wght200_GRAD0_opsz24"
                  d="M191.254-798.059h2.016v-7.864h-2.016Zm1.008-10.032a.8.8,0,0,0,.716-.267,1.06,1.06,0,0,0,.292-.741.936.936,0,0,0-.318-.716,1,1,0,0,0-.691-.292.974.974,0,0,0-.716.292.973.973,0,0,0-.292.716,1,1,0,0,0,.292.691A.936.936,0,0,0,192.263-808.09Zm-29.641-29.288v0Zm6.553,24.9h8.575q.418-.7.89-1.356t1-1.265h-10.46Zm0,10.485h6.448a8.88,8.88,0,0,1-.1-1.285q0-.63.045-1.336h-6.4Zm-4.94,9.175a4.1,4.1,0,0,1-3.022-1.212A4.1,4.1,0,0,1,160-797.05v-38.715a4.1,4.1,0,0,1,1.212-3.022A4.1,4.1,0,0,1,164.234-840H184.9l11.8,11.8v8q-.691-.192-1.321-.313a11.441,11.441,0,0,0-1.3-.171v-6.2H183.592v-10.485H164.234a1.542,1.542,0,0,0-1.109.5,1.542,1.542,0,0,0-.5,1.109v38.715a1.542,1.542,0,0,0,.5,1.109,1.542,1.542,0,0,0,1.109.5h13.717a14.446,14.446,0,0,0,.89,1.406,9.138,9.138,0,0,0,1.046,1.215Zm28.028-21.777a10.116,10.116,0,0,1,7.433,3.052,10.116,10.116,0,0,1,3.052,7.433,10.116,10.116,0,0,1-3.052,7.433,10.116,10.116,0,0,1-7.433,3.052,10.116,10.116,0,0,1-7.433-3.052,10.116,10.116,0,0,1-3.052-7.433,10.116,10.116,0,0,1,3.052-7.433A10.116,10.116,0,0,1,192.263-814.593Z"
                  transform="translate(-160 840)"
                  fill="#1447b2"
                />
              </svg>
            </div>
            <div>
              <h5 className="mb-2 text-base font-semibold tracking-tight text-gray-600">
                Partner Certificate
              </h5>
              <p className="mb-3 text-sm font-normal text-gray-400">
                Add new SBI info, view, edit and deactivate saved SBI info.
              </p>
            </div>
          </div>
          <div className="w-[19.625rem] h-72 p-6 mr-3 mb-4 pt-20 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
            <div className="flex justify-center mb-5">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="45.695"
                height="40.618"
                viewBox="0 0 45.695 40.618"
              >
                <path
                  id="dvr_FILL0_wght200_GRAD0_opsz24"
                  d="M130.155-775.59a1.5,1.5,0,0,0,1.1-.461,1.5,1.5,0,0,0,.461-1.1,1.5,1.5,0,0,0-.461-1.1,1.5,1.5,0,0,0-1.1-.461,1.5,1.5,0,0,0-1.1.461,1.5,1.5,0,0,0-.461,1.1,1.5,1.5,0,0,0,.461,1.1A1.5,1.5,0,0,0,130.155-775.59Zm0-10.155a1.5,1.5,0,0,0,1.1-.461,1.5,1.5,0,0,0,.461-1.1,1.5,1.5,0,0,0-.461-1.1,1.5,1.5,0,0,0-1.1-.461,1.5,1.5,0,0,0-1.1.461,1.5,1.5,0,0,0-.461,1.1,1.5,1.5,0,0,0,.461,1.1A1.5,1.5,0,0,0,130.155-785.745Zm5.37,9.862H156.81v-2.539H135.525Zm0-10.155H156.81v-2.539H135.525Zm-.293,26.656v-5.077H124.1a3.972,3.972,0,0,1-2.927-1.174A3.972,3.972,0,0,1,120-768.56V-795.9a3.972,3.972,0,0,1,1.174-2.927A3.972,3.972,0,0,1,124.1-800h37.494a3.972,3.972,0,0,1,2.927,1.174A3.972,3.972,0,0,1,165.7-795.9v27.339a3.972,3.972,0,0,1-1.174,2.927,3.972,3.972,0,0,1-2.927,1.174H150.464v5.077ZM124.1-767h37.494a1.493,1.493,0,0,0,1.074-.488,1.493,1.493,0,0,0,.488-1.074V-795.9a1.493,1.493,0,0,0-.488-1.074,1.493,1.493,0,0,0-1.074-.488H124.1a1.493,1.493,0,0,0-1.074.488,1.493,1.493,0,0,0-.488,1.074v27.339a1.493,1.493,0,0,0,.488,1.074A1.493,1.493,0,0,0,124.1-767Zm-1.562,0v0Z"
                  transform="translate(-120 800)"
                  fill="#1447b2"
                />
              </svg>
            </div>
            <div>
              <h5 className="mb-2 text-base font-semibold tracking-tight text-gray-600 ">
                Policy Selection
              </h5>
              <p className="mb-3 text-sm font-normal text-gray-400">
                Add new device, view, edit and deactivate saved devices.
              </p>
            </div>
          </div>
          <div className="w-[19rem] h-72 p-6 mr-3 mb-4 pt-20 bg-white border border-gray-200 shadow cursor-pointer  text-center rounded-xl">
            <div className="flex justify-center mb-5">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="40.618"
                height="40.618"
                viewBox="0 0 40.618 40.618"
              >
                <path
                  id="library_books_FILL0_wght200_GRAD0_opsz24"
                  d="M155.53-826.912h13.317V-833.3H155.53Zm0,12.832h16.633v-2.389H155.53Zm0-6.168h16.633v-2.389H155.53Zm-4.5,13.7a3.738,3.738,0,0,1-2.755-1.105,3.738,3.738,0,0,1-1.105-2.755V-836.14a3.738,3.738,0,0,1,1.105-2.755A3.738,3.738,0,0,1,151.028-840h25.731a3.738,3.738,0,0,1,2.755,1.105,3.739,3.739,0,0,1,1.105,2.755v25.731a3.738,3.738,0,0,1-1.105,2.755,3.738,3.738,0,0,1-2.755,1.105Zm0-2.389h25.731a1.405,1.405,0,0,0,1.011-.459,1.405,1.405,0,0,0,.459-1.011V-836.14a1.405,1.405,0,0,0-.459-1.011,1.405,1.405,0,0,0-1.011-.459H151.028a1.405,1.405,0,0,0-1.011.459,1.405,1.405,0,0,0-.459,1.011v25.731a1.405,1.405,0,0,0,.459,1.011A1.405,1.405,0,0,0,151.028-808.939Zm-7.168,9.557a3.738,3.738,0,0,1-2.755-1.105A3.738,3.738,0,0,1,140-803.241v-28.12h2.389v28.12a1.405,1.405,0,0,0,.459,1.011,1.405,1.405,0,0,0,1.011.459h28.12v2.389Zm5.7-38.229v0Z"
                  transform="translate(-140 840)"
                  fill="#1447b2"
                />
              </svg>
            </div>
            <div>
              <h5 className="mb-2 text-base font-semibold tracking-tight text-gray-600 ">
                Authentication Services
              </h5>
              <p className="mb-3 text-sm font-normal text-gray-400">
                Add / Manage new partner type
              </p>
            </div>
          </div>
        </div>
        <hr class="w-[81.5rem] h-px ml-7 mt-7 bg-gray-200 border-0" />
        <div class="flex mt-7 ml-7 justify-between text-sm text-gray-400">
          <div>
            <p>2024 © MOSIP - All rights reserved.</p>
          </div>
          <div class="flex justify-between">
            <p class="mr-7">Documentation</p>
            <p class="mr-7">MOSIP Community</p>
            <p class="mr-7">Contact Us</p>
          </div>
        </div>
      </div>
  );
}

export default Dashboard;