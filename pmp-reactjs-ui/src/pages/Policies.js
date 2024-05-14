import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Policies() {

  const [activeBtn, setActiveBtn] = useState(false);
  const navigate = useNavigate();

  const moveToHome = () => {
    navigate('/partnermanagement')
  };

  return (
    <div className="flex-col h-full ml-1 bg-anti-flash-white p-5 font-inter">
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
          <h1 className="font-bold text-md text-blue-900">Request a Policy</h1>
          <p onClick={() => moveToHome()} className="font-semibold text-blue-500 text-xs cursor-pointer">
            Home</p>
        </div>
      </div>

      <div className="flex-col justify-center ml-1">
        <div className="bg-white w-full mt-3 rounded-lg shadow-lg">
          <div className="flex justify-between p-5 mr-8 pb-20">
            <p className=" font-bold text-blue-900 text-sm ml-2">List of Policies (147)</p>
            <button onClick={() => setActiveBtn(!activeBtn)} type="button" className={`flex justify-center items-center ${activeBtn ? 'bg-blue-800 text-white' : 'text-blue-700'} h-9 w-32 text-xs px-2 py-2 text-blue-700 border border-blue-700 font-semibold rounded-md text-center`}>
              Filter
              <svg
                xmlns="http://www.w3.org/2000/svg" className={`${activeBtn ? 'rotate-180 text-white' : null} ml-2`}
                width="10" height="8" viewBox="0 0 10 8">
                <path id="Polygon_8"
                  data-name="Polygon 8"
                  d="M3.982,1.628a1.2,1.2,0,0,1,2.035,0L8.853,6.164A1.2,1.2,0,0,1,7.835,8H2.165A1.2,1.2,0,0,1,1.147,6.164Z"
                  transform="translate(10 8) rotate(180)" fill={`${activeBtn ? '#ffff' : '#1447b2'}`} />
              </svg>
            </button>
          </div>
          
        </div>
      </div>





      <hr className="h-px ml-7 mt-9 bg-gray-200 border-0 " />
      <div className="flex mt-7 ml-7 justify-between text-sm text-gray-400">
        <div>
          <p>2024 © MOSIP - All rights reserved.</p>
        </div>
        <div className="flex justify-between">
          <p className="mr-7">Documentation</p>
          <p className="mr-7">MOSIP Community</p>
          <p className="mr-7">Contact Us</p>
        </div>
      </div>
    </div>
  )
}

export default Policies;