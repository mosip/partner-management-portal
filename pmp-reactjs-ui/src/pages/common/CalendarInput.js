import { useRef, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { isLangRTL, handleMouseClickForDropdown } from '../../utils/AppUtils';
import { getUserProfile } from '../../services/UserProfileService';
import { useTranslation } from 'react-i18next';
import calendar_icon from '../../svg/calendar_icon.svg' 

function CalendarInput({ showCalendar, setShowCalender, label, onChange, value }) {

  const { t } = useTranslation();
  const isLoginLanguageRTL = isLangRTL(getUserProfile().langCode);

  const calendarRef = useRef(null);

  useEffect(() => {
    handleMouseClickForDropdown(calendarRef, () =>
      setShowCalender(false))
  }, [calendarRef]);

  const openCalendar = () => {
    setShowCalender(!showCalendar);
  };

  return (
    <div className="flex flex-col w-[48%] overflow-x-auto">
      <label className={`block text-dark-blue text-sm font-semibold mb-1 ${isLoginLanguageRTL ? "mr-1" : "ml-1"}`}>{label}</label>
      <button className={`flex items-center justify-between h-10 px-2 border border-[#707070] rounded-md text-md text-dark-blue bg-white text-start`}>
        <span className="text-start ">
          {value.toLocaleDateString() ? value.toLocaleDateString()
            : <p className={`text-gray-400`}>{t('addSbis.selectDateAndTime')}</p>
          }
        </span>
        {showCalendar &&
          <Calendar inputRef={calendarRef}
            onChange={onChange}
            value={value}
            defaultView="month"
            calendarType={`${isLoginLanguageRTL ? "islamic" : "iso8601"}`}
            locale={`${isLoginLanguageRTL ? "ar-AR" : "en-EN"}`}
            className={`absolute rounded-lg bg-white shadow-lg -mt-[24%] ${isLoginLanguageRTL ? "mr-56" : "ml-56"} w-auto h-auto`}
          />
        }
        <img onClick={openCalendar} src={calendar_icon} className={`h-[48%] mb-1 ${isLoginLanguageRTL ? "mr-3" : "ml-3"} text-blue-500 font-bold text-sm`}/>
      </button>
    </div>
  )
}

export default CalendarInput;